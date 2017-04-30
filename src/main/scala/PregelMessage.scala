import java.io.Serializable

import org.apache.spark.graphx._

/**
  * Created by kea on 02/04/2017.
  */
class PregelMessage extends Serializable {

//  var discovered : Boolean = false
  var initialMessage : Boolean = true
  var id : VertexId = 1
  var hops : Int = 2
  var msgNum : Int = 0
  var listOfVisited : scala.collection.mutable.Map[VertexId, (Int, Boolean)] = scala.collection.mutable.Map.empty[VertexId, (Int, Boolean)]
//  var lastVisited : VertexId = id
//  var lastVisitedAttr : (Int, Boolean) = (0, false)


//  /**
//    *
//    * @return
//    */
//  def getDiscovered(): Boolean = {
//    return discovered
//  }
//
//  /**
//    *
//    * @param discover
//    */
//  def setDiscovered(discover : Boolean) : Unit = {
//    val discovered = discover
//  }

//  /**
//    * Get message type
//    * @return true if initial message, false if not
//    */
//  def getMsgType() : Boolean = {
//    return initialMessage
//  }
//
//  /**
//    * Set message type
//    * @param msgType
//    */
//  def setMsgType(msgType : Boolean ): Unit = {
//
//    var initialMessage = msgType
//  }
//
//  /**
//    * get source vertex id
//    * @return source vertex id
//    */
//  def getId() : VertexId = {
//    return id
//  }
//
//  /**
//    * set source vertex id
//    * @param vId source vertex id
//    */
//  def setId(vId : VertexId) : Unit = {
//    var id = vId
//  }
//
//  /**
//    * get number of hops
//    * @return hops
//    */
//  def getHops() : Int = {
//    return hops
//  }
//
//  /**
//    * set number of hops
//    * @param hop hops
//    */
//  def setHops(hop : Int) : Unit = {
//    var hops = hop
//  }
//
//  /**
//    * get set of all visited vertices
//    * @return set of visited vertices
//    */
//  def getList() : Set[VertexId] = {
//    return listOfVisited
//  }
//
//  /**
//    * add vertex to visited list
//    * @param id vertex id
//    */
//  def  addToList(id : VertexId) : Unit = {
//    listOfVisited + id
//  }
//
//  /**
//    * set last visited vertex
//    * @param id last visited vertex id
//    */
//  def setLastVisited(id : VertexId) : Unit = {
//    var lastVisited = id
//  }
//
//  /**
//    * Get last visited vertex
//    * @return last visited vertex id
//    */
//  def getLastVisited() : VertexId = {
//    return lastVisited
//  }
//
//  /**
//    * Get last visited attribute
//    * @return last visited vertex attribute
//    */
//  def getLastVisitedAttr() : (Int, Boolean) = {
//    return lastVisitedAttr
//  }
//
//  /**
//    * Set attribute for last visited node
//    * @param attr last visited vertex attribute
//    */
//  def setLastVisitedAttr(attr : (Int, Boolean)) : Unit = {
//    var lastVisitedAttr = attr
//  }

}
