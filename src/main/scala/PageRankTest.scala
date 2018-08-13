import org.apache.spark.sql.SparkSession

/**
  * Created by yaojinquan on 2018/7/18 0018.
  * Author yaojinquan
  */
object PageRankTest {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("Chapter4")
      .config("spark.some.config.option", "some-value").getOrCreate()

    val sc = spark.sparkContext;
    val lines = sc.textFile("")
    val links = lines.flatMap(line=>{
      val list = line.split("\\s+")
      //丢弃第一个元素 剩下的map操作
      list.drop(1).map((list(0),_))
    }).groupByKey().cache()

    var ranks = links.mapValues(v=>1.0)

    for (i<- 1 to 100){
      val contibs = links.join(ranks).values.flatMap{case (urls,rank)=>
        val size = urls.size
         urls.map(url=>(url,rank/size))
      }.union(links.mapValues(v=>0.0))

      ranks = contibs.reduceByKey(_+_).mapValues(0.15+0.85*_)
    }
  }

}
