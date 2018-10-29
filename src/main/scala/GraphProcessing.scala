import java.util.concurrent.atomic.LongAccumulator

import GraphProcessing.hops
import org.apache.spark.SparkContext
import org.apache.spark._
import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD
import org.apache.spark.graphx.{GraphLoader, PartitionStrategy}

/**
  * Created by kea on 16/03/2017.
  */
object  GraphProcessing {

  // Set up Spark Configuration and SparkContext to initialise a Spark Session
  val conf = new SparkConf()
    .setAppName("Graph processing")
    .setMaster("local")

  val sc = new SparkContext(conf)

  // Class variables
  var edges: EdgeRDD[Int] = null
  var vertices: VertexRDD[(Int, Boolean)] = null
  var degreeRDD: VertexRDD[Int] = null
  var edgeTripletRDD: RDD[EdgeTriplet[(Int, Boolean), Int]] = null
  var graph: Graph[(Int, Boolean), Int] = null
  var neighbors: VertexRDD[Array[(VertexId)]] = null
  var initialMsg: PregelMessage = new PregelMessage
  //  var msg2 : PregelMessage = null

  var sourceV: VertexId = 1
  var hops: Int = 2
  //  var numOfHops : Int = 2
  //  sc.broadcast(hops)
  //  msg.listOfVisited += (sourceV -> (2, true))

  var hopAccum = sc.doubleAccumulator("hop accumulator")
  hopAccum.reset()
  var listOfVisited : scala.collection.mutable.Map[VertexId, (Int, Boolean)] = scala.collection.mutable.Map.empty[VertexId, (Int, Boolean)]



  def main(args: Array[String]) {

    // Check if enough arguments
    if (args.length != 2) {
      System.err.println(
        "Needs parameters, arg(0): <path/to/edges>")
      System.err.println(
        "Needs parameters, args(1): vertexID")
      //          System.err.println(
      //            "Needs parameters, args(2): Number of Hops")
      System.exit(1)
    }

    //TO DO: Set this properly when more information
    //Create a graph
    val graph = createGraph(sc, args(0))

    //Use JSphere method to create a JSphere
    val jSphere = naiveJSphere(graph, args(1).toInt)
    val jEdges: EdgeRDD[Int] = jSphere.edges
    jEdges.collect().foreach(println(_))

    //TO DO: WHAT ALGORITHMS TO USE, plus need to be implemented


    // Stop Spark context
    sc.stop()

  }

  /**
    * Method creates a graph by first loading the graph from an edge list, and creates edge and vertex RDD's from the graph, and finally
    * creates an edgeTriplet RDD from the graph.
    *
    * @param sc   SparkContext that creates Spark Connection
    * @param path the path to the file of edges
    * @return Graph with vertices and edges from the file and added vertex properties
    */
  def createGraph(sc: SparkContext, path: String): Graph[(Int, Boolean), Int] = {
    //TO DO: PartitionStategy, needed? What do we want? RandomVertexCut colocates same direction vertices, Canonical all edges
    val graphFromFile: Graph[Int, Int] = GraphLoader.edgeListFile(sc, path).partitionBy(PartitionStrategy.CanonicalRandomVertexCut)
    edges = graphFromFile.edges
    // CHANGE THIS TO INT.MINVALUE
    var reverseEdges: EdgeRDD[Int] = edges.reverse
//        edges = edges.innerJoin(reverseEdges)
    vertices = VertexRDD.fromEdges(edges, 4, (0, false))
    graph = Graph.apply(vertices, edges, (0, false))
    graph.cache()
    println("--Printing vertices--")
    println("Number of vertices in a graph " + graph.numVertices)
    vertices.collect().foreach(println(_))
    println("--Printing edges--")
    println("Number of edges in a graph " + graph.numEdges)
    edges.collect().foreach(println(_))


    //Create an RDD for degrees
    degreeRDD = graph.degrees
    println("--Printing degrees--")
    degreeRDD.collect().foreach(println(_))

    neighbors = graph.ops.collectNeighborIds(EdgeDirection.Either)

    // Create RDD for neighbor ID's
    //        neighbors: VertexRDD[Array[(VertexId)]] = graph.collectNeighborIds(EdgeDirection.Either)

    edgeTripletRDD = graph.triplets

    println("GRAPH CREATED")
    println("num edges = " + graph.numEdges);
    println("num vertices = " + graph.numVertices);

    return graph
  }


  /**
    * A method to create a j-sphere for one vertex
    *
    * @param graph   original graph
    * @param sourceV starting vertex for j-sphere
    * @return A new graph j-sphere
    */
  def naiveJSphere(graph: Graph[(Int, Boolean), Int], sourceV: VertexId): Graph[(Int, Boolean), Int] = {println("CALLING PREGEL")

    initialMsg.initialMessage = true

    val returngraph: Graph[(Int, Boolean), Int] = graph.pregel[PregelMessage](initialMsg,
      hops,
      EdgeDirection.Either)(
      naivevprog,
      naivesendMsg,
      naivemergeMsg)

    // Create JSphere subgraph for start vertex by only adding vertices and edges that are part of JSphere
    val jSphere: Graph[(Int, Boolean), Int] = returngraph.subgraph(epred = e => e.dstAttr._2 == true && e.srcAttr._2 == true,
      vpred = (id, VD) => VD._2 == true)
    jSphere.cache()
    println("JSPHERE CREATED")
    println("JSPHERE num edges = " + jSphere.numEdges)
    println("JSPHERE num vertices = " + jSphere.numVertices)

    // Print all vertices in JSphere
    jSphere.vertices.collect().foreach(println(_))
    jSphere.cache()

    return jSphere
  }

  /**
    * Vertex program function for receiving messages, used by Pregel
    * @param vertexId current vertex id
    * @param attr current vertex attribute
    * @param msg received message
    * @return new attribute to vertex
    */
  def naivevprog(vertexId: VertexId, attr: (Int, Boolean), msg: PregelMessage): (Int, Boolean) = {
    if (msg.initialMessage == true && vertexId == sourceV) {
      return (0, true)
    } else if (msg.id == vertexId && msg.id != sourceV) {
      return (msg.hops, true)
    } else {
      return attr
    }
  }

  /**
    * Send message function that determines messages for next iteration and which vertices receive it
    * @param triplet a triplet through which messages are sent
    * @return Iterator that sends the message through right
    */
  def naivesendMsg(triplet: EdgeTriplet[(Int, Boolean), Int]): Iterator[(VertexId, PregelMessage)] = {
    var msg: PregelMessage = new PregelMessage
    msg.initialMessage = false

    if (triplet.srcAttr._2 && !triplet.dstAttr._2) {
      msg.id = triplet.dstId
      msg.hops = triplet.srcAttr._1 + 1
      return Iterator((triplet.dstId, msg))
    } else if (triplet.dstAttr._2 && !triplet.srcAttr._2)  {
      msg.id = triplet.srcId
      msg.hops = triplet.dstAttr._1 + 1
      return Iterator((triplet.srcId, msg))
    }
      else {
      Iterator.empty
    }
  }

  /**
    * Merge message for naive j-sphere, takes two messages and returns one
    * @param msg1 first received message
    * @param msg2 second received message
    * @return message with bigger hop cunt
    */
  def naivemergeMsg(msg1: PregelMessage, msg2: PregelMessage): PregelMessage = {
    if (msg1.hops >= msg2.hops) {
      println("Returning message 2")
      return msg2
    } else {
      println("Returning message 1")
      return msg1
    }
  }

  /**
    *
    */
  def jSphere(hops: Int, graph: Graph[(Int, Boolean), Int]): Unit = {

    initialMsg.initialMessage = true

    var jspherecollection : Set[(VertexRDD[(Int, Boolean)], EdgeRDD[Int])] = null


    val returngraph: Graph[(Int, Boolean), Int] = graph.pregel[PregelMessage](initialMsg,
      hops,
      EdgeDirection.Either)(
      vprog,
      sendMsg,
      mergeMsg)

    // Create JSphere subgraph for start vertex by only adding vertices and edges that are part of JSphere
    val jSphere: Graph[(Int, Boolean), Int] = returngraph.subgraph(epred = e => e.dstAttr._2 == true && e.srcAttr._2 == true,
      vpred = (id, VD) => VD._2 == true)
    jSphere.cache()
    println("JSPHERE CREATED")
    println("JSPHERE num edges = " + jSphere.numEdges)
    println("JSPHERE num vertices = " + jSphere.numVertices)

    // Print all vertices in JSphere
    jSphere.vertices.collect().foreach(println(_))
    jSphere.cache()

    return jSphere

  }

  /**
    * Vertex program function for receiving messages, used by Pregel
    * @param vertexId current vertex id
    * @param attr current vertex attribute
    * @param msg received message
    * @return new attribute to vertex
    */
  def vprog(vertexId: VertexId, attr: (Int, Boolean), msg: PregelMessage): (Int, Boolean) = {
    if (msg.initialMessage == true && vertexId == sourceV) {
      return (0, true)
    } else if (msg.id == vertexId && msg.id != sourceV) {
      return (msg.hops, true)
    } else {
      return attr
    }
  }

  /**
    * Send message function that determines messages for next iteration and which vertices receive it
    * @param triplet a triplet through which messages are sent
    * @return Iterator that sends the message through right
    */
  def sendMsg(triplet: EdgeTriplet[(Int, Boolean), Int]): Iterator[(VertexId, PregelMessage)] = {
    var msg: PregelMessage = new PregelMessage
    msg.initialMessage = false

    if (triplet.srcAttr._2 && !triplet.dstAttr._2) {
      msg.id = triplet.dstId
      msg.hops = triplet.srcAttr._1 + 1
      return Iterator((triplet.dstId, msg))
    } else if (triplet.dstAttr._2 && !triplet.srcAttr._2)  {
      msg.id = triplet.srcId
      msg.hops = triplet.dstAttr._1 + 1
      return Iterator((triplet.srcId, msg))
    }
    else {
      Iterator.empty
    }
  }

  /**
    * Merge message for naive j-sphere, takes two messages and returns one
    * @param msg1 first received message
    * @param msg2 second received message
    * @return message with bigger hop cunt
    */
  def mergeMsg(msg1: PregelMessage, msg2: PregelMessage): PregelMessage = {
    if (msg1.hops >= msg2.hops) {
      println("Returning message 2")
      return msg2
    } else {
      println("Returning message 1")
      return msg1
    }
  }

  // DecimalType data type? Double? BigDecimal? Look into this more
  /**
    * Clustering coefficient for an input graph
    * @param inGraph
    * @return
    */
  def clusteringCoefficient(inGraph: Graph[(Int, Boolean), Int], inVertexId: VertexId): Long = {
    var edgeNum : Long = inGraph.numEdges
    var degreeOfVertex : Long = neighbors.lookup(inVertexId).length.toLong

    return ((2 * edgeNum) / degreeOfVertex * (degreeOfVertex + 1))
  }


//
//  def InverseDegree(inGraph: Graph[(Int, Boolean), Int], inVertexId : VertexId) : Long = {
//    var degreeOfVertex : Long = neighbors.lookup(inVertexId).length.toLong
//    var pivi : Long = 1 / degreeOfVertex
//
//    return null
//  }


  /**
    *
    * @return
    */
  def dijkstraShortestPath() : Graph[Int, Boolean] ={
    return null
  }

  /**
    *
    * @param inGraph
    * @return
    */
  def normalisedDegreeEntropyForANode(inGraph : Graph[(Int, Boolean), Int], id : VertexId) : BigDecimal = {
    var CC : Long = clusteringCoefficient(inGraph, id)


    return null
  }



}
