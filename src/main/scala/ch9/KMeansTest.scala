//package ch9
//
//import org.apache.spark.ml.clustering.KMeans
//import org.apache.spark.mllib.linalg.Vectors
//import org.apache.spark.mllib.linalg.distributed.{CoordinateMatrix, MatrixEntry}
//import org.apache.spark.sql.SparkSession
//
///**
//  * Created by yaojinquan on 2018/8/4 0004.
//  * Author yaojinquan
//  */
//object KMeansTest {
//  def main(args: Array[String]): Unit = {
//    val spark = SparkSession.builder()
//      .master("local[*]")
//      .appName("Chapter4")
//      .config("spark.some.config.option", "some-value").getOrCreate()
//
//    val sc = spark.sparkContext;
//
//    val data = sc.textFile("");
//    val parsedData = data.map(s=>Vectors.dense(s.split(' ').map(_.toDouble))).cache()
//    //设置参数 2  最大迭代次数
//    val clusters = new KMeans().setK(2).setMaxIter(20);
////    val model = clusters.transformSchema(parsedData)
//    (0 to 1)foreach(a=>{
//      println("中心点为：");
//    })
//
//    val adjMatrixEntry = data.map(_.split(" ") match {
//      case Array(id1,id2) =>adjMatrixEntry(id1.toLong-1,id2.toLong-1,-1.0)
//    })
//    //矩阵放入CoordinateMatrix
//    val adjMatrix = new CoordinateMatrix(adjMatrixEntry)
//
//    //计算社交网络的拉普拉斯矩阵
//
//    //计算矩阵的对角元素值
//    val rows = adjMatrix.toIndexedRowMatrix.rows
//    val diagMatrixEntry = rows.map{row=>MatrixEntry(row.index,row.index,row.vector.toArray.sum)}
//  }
//}
