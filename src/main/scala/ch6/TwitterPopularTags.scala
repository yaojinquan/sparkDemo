package ch6

import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
  * Created by yaojinquan on 2018/8/3 0003.
  * Author yaojinquan
  */
object TwitterPopularTags {
  def main(args: Array[String]): Unit = {
        //slice 取第5个到最后的元素
        val (master,filter) = (args(0),args.slice(5,args.length))
        //证书信息
        val configKeys = List("key","add");

        val map = configKeys.zip(args.slice(1,5).toList).toMap
        configKeys.foreach(key=>{
          if(!map.contains(key)){

          }
          val fullKey = "twitter4f.au"+key
        })
        //初始化spark streaming
        val ssc = new StreamingContext(master,"TwitterPopularTags",Seconds(2))
        //从Kafka 队列中获取流数据
        val stream = KafkaUtils.createStream(ssc,null,null,null);
        //切分出语句  取出#开头的字符串数据
        val hashTags = stream.flatMap(status=>status.toString().split("   ").filter(_.startsWith("#")))
        //统计热点话题并排序 () 里面是参数与标的式  {} 里面是代码块 可以是多行 如果是case 则必须是{}
        // 统计 _ + _ 累加操作
        val topCounts60 = hashTags.map((_,1)).reduceByKeyAndWindow(_ + _,Seconds(60)).map{case (topic,count)=>(topic,count)}
          .transform(_.sortByKey(false))

        val topCounts10 = hashTags.map((_,1)).reduceByKeyAndWindow(_ + _,Seconds(10)).map{case (topic,count)=>(topic,count)}
        .transform(_.sortByKey(false))

        topCounts60.foreachRDD(rdd=>{
          val topList = rdd.take(5)
          println("".format(rdd.count()))
          topList.foreach{case (count,tag)=>println("%s (%s tweets)".format(tag,count))}
        })
    ssc.start()
    ssc.awaitTermination()

  }

}
