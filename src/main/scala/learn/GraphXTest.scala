package learn

import org.apache.spark.graphx.Edge
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
  * Created by yaojinquan on 2018/7/18 0018.
  * Author yaojinquan
  */
object GraphXTest {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("Chapter4")
      .config("spark.some.config.option", "some-value").getOrCreate()

    val sc = spark.sparkContext;
    val lines = sc.textFile("")
    val edges:RDD[Edge[String]]=lines.flatMap(line=>{
      val list = line.split("\\s+")
      list.drop(1).map(a=>(list(0).toLong,a.toLong))
    }).map{case(k,v)=>Edge(k,v)}
  }
}
