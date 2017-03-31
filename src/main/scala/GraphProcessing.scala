import org.apache.spark.SparkContext
import org.apache.spark.graphx._
import org.apache.spark._
import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD
import org.apache.spark.graphx.{GraphLoader, PartitionStrategy}
import cn.edu.tsinghua.keg.spark.graphx.lib.LocalClusteringCoefficient

/**
  * Created by kea on 16/03/2017.
  */
object GraphProcessing {


  val graph : Graph[Int, Int] = null
  val edges : EdgeRDD[Int] = null
  val vertices : VertexRDD[Int] = null
  val degreeRDD : VertexRDD[Int] = null
  val edgeTriplet : EdgeTriplet[Int, Int] = null


  //TO DO: FIGURE OUT EDGECONTEXT
  //  val edgeContext = new EdgeContext[Int, Float, Int] {}
  //  val edgeTriplet = edgeContext.toEdgeTriplet







  def createGraph(sc: SparkContext, path: String): Graph[Int, Int] = {
    //PartitionStategy, needed? What do we want? RandomVertexCut colocates same direction vertices, Canonical all edges
    val graph = GraphLoader.edgeListFile(sc, path).partitionBy(PartitionStrategy.CanonicalRandomVertexCut)
    val edges = graph.edges
    val vertices = graph.vertices
    val degreeRDD = graph.degrees
    degreeRDD.collect().foreach(println(_))

    return graph
  }


  //How do?? In the book!
  def clusteringCoefficient(graph: Graph[Int, Int]): Long = {


    graph
    val edgeNum: Long = graph.numEdges
    return edgeNum
  }


  //Figure out a way to access two hops away
  def jSphere(graph:Graph[Int,Int], j:Int, source:VertexId): Graph[Int, Int] = {

    val verticesWithSuccessors: VertexRDD[Array[VertexId]] =
      graph.ops.collectNeighborIds(EdgeDirection.Either)

    val successorSetGraph = Graph(verticesWithSuccessors, edges)


// http://stackoverflow.com/questions/25147768/spark-graphx-how-to-travers-a-graph-to-create-a-graph-of-second-degree-neighbor


  // mapReduceTriplets deprecated, use aggregateMessages?
//    val ngVertices: VertexRDD[Set[VertexId]] =
//      successorSetGraph.mapReduceTriplets[Set[VertexId]] (
//        triplet => {
//          Iterator((triplet.dstId, triplet.srcAttr.toSet))
//        },
//        (s1, s2) => s1 ++ s2
//      ).mapValues[Set[VertexId]](
//        (id: VertexId, neighbors: Set[VertexId]) => neighbors - id
//      )


  // This is to create edges for each neighbor relationship
//    val ngEdges = ngVertices.flatMap[Edge[String]](
//      {
//        case (source: VertexId, allDests: Set[VertexId]) => {
//          allDests.map((dest: VertexId) => Edge(source, dest, ""))
//        }
//      }
//    )


    //create subgraph
    return graph
  }


}
