/**
  * Created by kea on 15/03/2017.
  */

import org.apache.spark._
import org.apache.spark.graphx._
import org.apache.spark.rdd.RDD
import org.apache.spark.graphx.{GraphLoader, PartitionStrategy}
import cn.edu.tsinghua.keg.spark.graphx.lib.LocalClusteringCoefficient


object Main {
  def main(args: Array[String]) {

    if (args.length != 1) {
      System.err.println(
        "Should be one parameter: <path/to/edges>")
      System.exit(1)
    }


    //Set this properly when more information
    val conf = new SparkConf()
      .setAppName("Graph processing")
      .setMaster("local")

    val sc = new SparkContext(conf)


    val graph = GraphProcessing.createGraph(sc, args(0))


    println("num edges = " + graph.numEdges);
    println("num vertices = " + graph.numVertices);






    sc.stop()

  }




}