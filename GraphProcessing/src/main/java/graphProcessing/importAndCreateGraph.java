package graphProcessing;

/**
 * Created by kea on 09/02/2017.
 */
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.SparkConf;
import org.apache.spark.rdd.RDD;
import org.apache.spark.graphx.*;


public class importAndCreateGraph
{

    SparkConf conf = new SparkConf().setAppName("graph processing").setMaster("master");
    JavaSparkContext sc = new JavaSparkContext(conf);



}
