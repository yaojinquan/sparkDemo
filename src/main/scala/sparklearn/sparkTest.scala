package sparklearn


import org.apache.spark.mllib.recommendation.{ALS, Rating}
import org.apache.spark.sql.SparkSession

/**
  * Created by yaojinquan on 2018/7/11 0011.
  * Author yaojinquan
  */
object sparkTest {
  def main(args: Array[String]): Unit = {
    //print("hellword");
    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("Chapter4")
      .config("spark.some.config.option", "some-value").getOrCreate()

    val sc = spark.sparkContext;
    val rowData = sc.textFile("E:/ml-100k/u.data");
    print(rowData.first());

    val rawRatings = rowData.map(_.split("\t").take(3))
    rawRatings.first();

    //map 将一个RDD对象转换为另外一个RDD
    //case 满足条件 则作为参数输入 传入后面的函数
    // 先将rawRatings 转换为数组  然后经过函数  Rating 转为一个RDD
    val ratings = rawRatings.map{case Array(user,movie,rating)=>Rating(user.toInt,movie.toInt,rating.toDouble)}

    val model = ALS.train(ratings,50,10,0.01)
    model.userFeatures;
    model.productFeatures;


  }


}
