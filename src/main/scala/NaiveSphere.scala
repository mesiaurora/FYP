//import java.util.concurrent.atomic.LongAccumulator
//
//import org.apache.spark.graphx.{EdgeDirection, EdgeTriplet, Graph, VertexId, VertexRDD}
//
///**
//  * Created by kea on 15/04/2017.
//  */
//object NaiveSphere {
//
//  println("**CALLING NAIVE JSPHERE METHOD**")
//  // Create messages used by Pregel
//  var msg = new PregelMessage
//  var msg2 = new PregelMessage
//
//  var sourceV: VertexId = 1
//  var hops: Int = 2
//  msg.listOfVisited += (sourceV -> (2, true))
//
////  var accum = LongAccumulator
//
//
////  var vAttr = msg.listOfVisited.get(sourceV).get
////    var sourceNeighbors : Array[(VertexId, (Int, Boolean))] =
////  var sourceNeighbors: Seq[Array[(VertexId)]] = GraphProcessing.neighbors.lookup(sourceV)
////  var sourceneighborArray: Array[(VertexId)] = sourceNeighbors.head
//
//  // Set message fields
//  msg.initialMessage == true
////  msg.id = sourceV
////  msg.lastVisited == sourceV
//
//
//  //Vertex program function for receiving messages, used by Pregel
//  def vprog(vertexId: VertexId, attr: (Int, Boolean), msg: PregelMessage): (Int, Boolean) = {
//    msg.listOfVisited.foreach(println(_))
//
//    // Check if message is initial message and vertexId is the source vertex. If so, return true
//    if (msg.initialMessage == true && vertexId == sourceV) {
//      msg.listOfVisited += (vertexId -> attr)
////      msg.listOfVisited += (vertexId -> (2, true))
//      msg.initialMessage = false
//      println("****VPROG VERTEXID INSIDE INITIAL MESSAGE IS**** " + vertexId + attr)
//      return (hops, true)
//      // if it's not, check if the message is the sourceV and return true. If it isn't but is connected to sourceV or
//      // other vertex already marked true, then return true
//    } else if (msg.initialMessage != true) {
////      msg.listOfVisited += (vertexId -> (hops - 1, true))
//      msg.initialMessage = false
//      println("****VPROG VERTEXID OUTSIDE INITIAL MESSAGE IS**** " + vertexId + attr)
//      return (hops - 1, true)
//    } else {
//      return (Int.MaxValue, false)
//    }
//  }
//
//  // NOTE SHARED VARIABLES!!!! UNDERSTAND PARALLELLIZATION
//  // WRITE ABOUT PARALLELIZATION!!!111
//  // DON'T NEED BOOLEAN
//  //
//  // Send message function that determines messages for next iteration and which vertices receive it
//  def sendMsg(triplet: EdgeTriplet[(Int, Boolean), Int]): Iterator[(VertexId, PregelMessage)] = {
//    println("JUHUUU MMOI SENGMESSAGE")
//
//    var msg1 : PregelMessage = new PregelMessage
//    msg1.initialMessage = false
//    msg1.listOfVisited = msg.listOfVisited
//    msg1.hops = msg.hops -1
////    println("PRINTING BEHIND NEIGHBORS")
////    var neighbors : Boolean = GraphProcessing.neighbors.lookup(triplet.srcId).contains(triplet.dstId)
////    var sourceNeighbors = GraphProcessing.neighbors.lookup(triplet.srcId)
//
//
//    //  (the vertex id of the vertex you want to send to, the message you want to send to that vertex).
//    // If message is initial message and triplet source is source vertex, return iterator
//    if (triplet.srcAttr._2 && msg.listOfVisited.contains(triplet.srcId) && triplet.dstAttr._2 != true) {
//      msg.listOfVisited += (triplet.dstId -> (2, true))
//      msg.initialMessage = false
////      msg.lastVisitedAttr = (2, true)
////      sourceNeighbors = GraphProcessing.neighbors.lookup(triplet.srcId)
//
//
//      println("SENDING MESSAGE TO NODE " + triplet.dstId)
//      return Iterator((triplet.dstId, msg1))
//    } else {
//      return Iterator.empty
//    }
//  }
//
//  def mergeMsg(msg1: PregelMessage, msg2: PregelMessage): PregelMessage = {
//    return msg1
//  }
//}