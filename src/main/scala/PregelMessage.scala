import java.io.Serializable

import org.apache.spark.graphx._

/**
  * Created by kea on 02/04/2017.
  */
class PregelMessage extends Serializable {

  var initialMessage : Boolean = true
  var id : VertexId = 1
  var hops : Int = 2

}
