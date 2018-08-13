package ch5

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.ml.recommendation.ALS

/**
  * Created by yaojinquan on 2018/8/3 0003.
  * Author yaojinquan
  */
object AlsLogs {
  //模式匹配的class
  case class Rating(userId: Int, item: Int, rate: Double)
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("Chapter4")
      .config("spark.some.config.option", "some-value").getOrCreate()

    val sc = spark.sparkContext;
    val rowData = sc.textFile("/example/data.txt");

    rowData.map(_.split("\t") match {
      case Array(user,item,rate,time) =>Rating(user.toInt,item.toInt,rate.toDouble)
    })

    //ALS进行模型训练
    val model = new ALS()
      //特征数设置
      .setMaxIter(5)
      .setRegParam(0.01)
      .setUserCol("userId")
      .setItemCol("movieId")
      .setRatingCol("rating")
  }

//  private def predictMoive(params:Params,context:SparkContext,model:MatrixFoctoryModel): Unit ={
//    var remmonds = new Array[java.util.Map[String,String]](8);
//    val userData = context.textFile(params.userDataInput)
//    userData.map(_.split("\\|") match {
//      case Array(id,age,sex,job,x) =>(id)
//    }).collect().foreach(id=>{
//        var rs = model.recommendProduct(id.toInt,numRecommender)
//        var value = ""
//        var key = 0
//        rs.foreach(r=>{
//          key = r.user
//          value = value + r.product+ ":"+r.rating+","
//        })
//    })
//
//  }
}
