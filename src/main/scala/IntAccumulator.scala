//import org.apache.spark.SparkContext
//import org.apache.spark._
//import org.apache.spark.graphx._
//import org.apache.spark.rdd.RDD
//import org.apache.spark.graphx.{GraphLoader, PartitionStrategy}
//import org.apache.spark.util.AccumulatorV2
//
///**
//  * Created by kea on 29/04/2017.
//  */
//class IntAccumulator extends AccumulatorV2 {
//
//  private val intAccumulator : Int = new IntAccumulator
//
//
//  def reset(): Unit = {
//    IntAccumulator.reset()
//  }
//
//  def add(i : Int): Unit = {
//    intAccumulator = intAccumulator + 1
//  }
//}
