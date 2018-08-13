//package ch8
//
//import org.apache.spark.mllib.linalg.distributed.{CoordinateMatrix, MatrixEntry}
//import org.apache.spark.sql.SparkSession
//
///**
//  * 根据用户  协同过滤推荐
//  * Created by yaojinquan on 2018/8/4 0004.
//  * Author yaojinquan
//  */
//object AaFiter {
//  def main(args: Array[String]): Unit = {
//    val spark = SparkSession.builder()
//      .master("local[*]")
//      .appName("Chapter4")
//      .config("spark.some.config.option", "some-value").getOrCreate()
//
//    val sc = spark.sparkContext;
//    val data=sc.textFile("");
//    //将数据转化为矩阵
//    val parsedData = data.map(_.split(",") match {
//      case Array(user,item,rate) =>MatrixEntry(user.toLong-1,item.toLong-1,rate.toDouble)
//    })
//
//    val ratings = new CoordinateMatrix(parsedData)
//    //RowMatrix 只能计数列间的相识度  用户数据是行 需要先进行转置操作  将行变成列，列变成行
//    val matrix = ratings.transpose.toRowMatrix
//    val similarities = matrix.columnSimilarities(0.1)
//    val ratingsOfUser1 = ratings.toRowMatrix.rows.toArray()(0).toArray
//    val avgRatingOfUser1 = ratingsOfUser1.sum/ratingsOfUser1.size
//    // 计算其他用户对项目1 的加权 平均评分
//    val ratingsToItem1 = matrix.rows.toArray()(0).toArray.drop(1)
//    val weights = similarities.entries.filter(_.i==0).sortBy(_.j).map(_.value).collect
//    val weightedR = (0 to 2).map(t=>weights(t)*ratingsToItem1(t)).sum/weights.sum
//  }
//}
