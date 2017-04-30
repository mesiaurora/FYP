//import org.apache.spark.graphx.{EdgeDirection, EdgeTriplet, Graph, VertexId}
//
///**
//  * Created by kea on 15/04/2017.
//  */
//object NaivejSphere {
//
//  println("**CALLING NAIVE JSPHERE METHOD**")
//  // Create messages used by Pregel
//  var msg = new PregelMessage
//  var msg2 = new PregelMessage
//
//  var sourceV: VertexId = 1
//  var hops: Int = 2
//
//  // Set message fields
//  msg.initialMessage == true
//  msg.id = sourceV
//  msg.lastVisited == sourceV
//  // CHANGE THIS
//
//
//  //Vertex program function for receiving messages, used by Pregel
//  def vprog(vertexId: VertexId, attr: (Int, Boolean), msg: PregelMessage): (Int, Boolean) = {
//    // Check if message is initial message and vertexId is the source vertex. If so, return true
//    if (msg.initialMessage == true && vertexId == sourceV) {
//      msg.listOfVisited += (vertexId -> attr)
//      msg.initialMessage = false
//      println("****VPROG VERTEXID INSIDE INITIAL MESSAGE IS**** " + vertexId)
//      println(hops)
//      return (hops, true)
//      // if it's not, check if the message is the sourceV and return true. If it isn't but is connected to sourceV or
//      // other vertex already marked true, then return true
//    } else if (msg.listOfVisited.get(vertexId) == ((_, true)) && msg.listOfVisited.get(vertexId) != ((0,_))) {
//      // TO DO: remove print
//      println("OUTSIDE INITIAL MESSAGE")
//      msg.initialMessage = false
//
//      msg.listOfVisited += (vertexId -> attr)
//      println("PRINTING LIST OF VISITED")
//      println(hops)
//      println("****VPROG VERTEXID OUTSIDE INITIAL MESSAGE IS****" + vertexId)
//      return (hops, true)
//    } else {
//      return (0, false)
//    }
//  }
//
//  // Send message function that determines messages for next iteration and which vertices receive it
//  def sendMsg(triplet: EdgeTriplet[(Int, Boolean), Int]): Iterator[(VertexId, PregelMessage)] = {
//
//    //  (the vertex id of the vertex you want to send to, the message you want to send to that vertex).
//    // If message is initial message and triplet source is source vertex, return iterator
//    if (msg.initialMessage == true && triplet.srcId == sourceV) {
//      msg.initialMessage = false
//      println("SEND MESSAGE HERE WE ARE INSIDE INITIAL MESSAGE")
//
//      // If source attribute is true and number of hops is not 0, then the source has
//      return Iterator((triplet.dstId, msg))
//
//    } else if (triplet.srcAttr._2 == true && msg.listOfVisited.get(triplet.srcId) == ((_, true))) {
//      msg.initialMessage = false
//      //        // TO DO: remove print
//      println("SEND MESSAGE HERE WE ARE _OUTSIDE_ INITIAL MESSAGE")
//      return Iterator((triplet.dstId, msg))
//    } else if (hops <= 0) {
//      // In every other case return an empty iterator
//      return Iterator.empty
//    } else {
//      return Iterator.empty
//    }
//  }
//
//  def mergeMsg(msg1: PregelMessage, msg2: PregelMessage): PregelMessage = {
//    return msg
//  }
//}