package graphProcessing;

/**
 * Created by kea on 09/02/2017.
 *

 *
 *
 *
 */
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.SparkContext;
import org.apache.spark.SparkConf;
import org.apache.spark.graphx.impl.*;
import org.apache.spark.graphx.util.*;
import org.apache.spark.rdd.RDD;
import org.apache.spark.graphx.*;
import org.apache.spark.storage.*;

import java.io.*;


public class importAndCreateGraph
{

    String path = "";
    File file = new File(path);


    SparkConf conf;
    SparkContext sc;

    //TO DO: Figure out which way the best
    EdgeRDD edges;
    VertexRDD vertices;
    RDD edgeRDD;
    GraphImpl graph;

    StorageLevel vertexStorageLevel = new StorageLevel();
    StorageLevel edgeStorageLevel = new StorageLevel();

    //??
    ShippableVertexPartition vpart;


    public importAndCreateGraph()
    {

        conf = new SparkConf().setAppName("graph processing").setMaster("local");
        sc = new SparkContext(conf);

        //TO DO: Figure out partitionsRDD and elementClassTags
        edges = new EdgeRDDImpl(null, edgeStorageLevel, edges.elementClassTag(), edges.elementClassTag());

        //TO DO:  Partitions and vTag?
        vertices = new VertexRDDImpl(null, vertexStorageLevel, vertices.vdTag());

    }

    /**
     *
     * @throws FileNotFoundException
     */
    public void importGraph() throws FileNotFoundException {
        BufferedReader reader = new BufferedReader(new FileReader(file));
    }

    /**
     *
     * @return
     */
    public GraphImpl createGraph()
    {

        //Which way the best to create a graph?
        /* EdgeRDD.fromEdges(edges) should do the trick.

        * Scala how to create EdgeRDD from a file
        *    val edges: RDD[Edge[String]] =
        *       sc.textFile(args(0)).map { line =>
        * val fields = line.split(" ")

        This adds features in the graph
        * Edge(fields(0).toLong, fields(2).toLong, fields(1))
        * }

        * val graph : Graph[Any, String] = Graph.fromEdges(edges, "defaultProperty")
     */


        //TO DO: This or later?
//        Graph graph = GraphLoader.edgeListFile(sc, path, false,-1, vertexStorageLevel, edgeStorageLevel);

        //TO DO:  Figure this out
        graph = new GraphImpl(null, null);
        graph.triplets();

        return null;
    }

}
