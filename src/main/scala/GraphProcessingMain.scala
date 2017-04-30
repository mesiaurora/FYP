///**
//  * Created by kea on 15/03/2017.
//  */
//
//import org.apache.spark._
//import org.apache.spark.graphx._
//import org.apache.spark.rdd.RDD
//import org.apache.spark.graphx.{GraphLoader, PartitionStrategy}
//import cn.edu.tsinghua.keg.spark.graphx.lib.LocalClusteringCoefficient
//import org.apache.spark.util.LongAccumulator
//
//
//object GraphProcessingMain {
//  def main(args: Array[String]) {
//
////    // Check if enough arguments
////    if (args.length != 3) {
////      System.err.println(
////        "Needs parameters, arg(0): <path/to/edges>")
////      System.err.println(
////        "Needs parameters, args(1): vertexID")
////      System.err.println(
////        "Needs parameters, args(2): Number of Hops")
////      System.exit(1)
////    }
//
//    //TO DO: Set this properly when more information
//    //Create SparkConf and Spark Context to initialise a Spark Session
//    val conf = new SparkConf()
//      .setAppName("Graph processing")
//      .setMaster("local")
//
//    val sc = new SparkContext(conf)
//    var accum = sc.longAccumulator("hops")
//    accum.reset()
//
//
//    //Create a graph
//    val graph = GraphProcessing.createGraph(sc, args(0))
//
//    //Use JSphere method to create a JSphere
//    val jSphere = GraphProcessing.naiveJSphere(graph, args(1).toInt, NaiveSphere.msg)
//    val jEdges : EdgeRDD[Int] = jSphere.edges
//    jEdges.collect().foreach(println(_))
//
//    //TO DO: WHAT ALGORITHMS TO USE, plus need to be implemented
//
//
//
//    // Stop Spark context
//    sc.stop()
//
//  }
//
//
//
//
//}