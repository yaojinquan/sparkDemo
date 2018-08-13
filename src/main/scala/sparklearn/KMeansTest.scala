import org.apache.hadoop.conf.Configuration
import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.mllib.feature.HashingTF
import org.apache.spark.rdd.RDD
import com.mongodb.hadoop.MongoInputFormat
import org.apache.spark.mllib.linalg
import org.bson.BSONObject
import org.apache.spark.mllib.clustering.KMeans
import org.apache.spark.SparkConf
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.streaming.kafka.KafkaUtils


/**
  * Created by yaojinquan on 2018/7/13 0013.
  * Author yaojinquan
  */

object KMeansTest {

  def GetDocumentsFromMongoDB(sc: SparkContext):RDD[Seq[String]] = {
    val config = new Configuration();
    config.set("mongo.input.uri","mongodb://127.0.0.1:27017/NYtimes.news")
    val mongoRDD = sc.newAPIHadoopRDD(config,classOf[com.mongodb.hadoop.MongoInputFormat],classOf[Object],classOf[BSONObject]);

    val textRDD = mongoRDD.flatMap(arg => {
      val docID = arg._2.get("id");
      val text = arg._2.get("text").toString;
      text = text.toLowerCase().replaceAll("[.,!?\n]", "")
      (docID, text)
    })
      return null;
  };

  def main(args: Array[String]): Unit = {
    //print("hellword");
    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("Chapter4")
      .config("spark.some.config.option", "some-value").getOrCreate()

    val sc = spark.sparkContext;
    val documents:RDD[Seq[String]] = GetDocumentsFromMongoDB(sc);

    val hashingTF = new HashingTF();
    val tf:RDD[linalg.Vector]=hashingTF.transform(documents);

    import org.apache.spark.mllib.feature.IDF

    tf.cache()
    val idf = new IDF().fit(tf);

    val tfidf:RDD[linalg.Vector] = idf.transform(tf);

    val parseData = tfidf;
    val clusters = KMeans.train(parseData,10,10,10);
    val clusterCenters = clusters.clusterCenters

    val labels = clusters.predict(parseData);
    labels.saveAsObjectFile("result.txt")


    //--------------实时分析
    print("Initializing Streaming Spark ")
    import org.apache.spark.streaming.Seconds
    val conf = new SparkConf().setAppName(this.getClass.getSimpleName)
    val ssc = new StreamingContext(conf,Seconds(5))

//    val news = KafkaUtils.createStream(ssc,
//      "", "", []);

    //-----------检索
    val conf1 = new SparkConf().setAppName(this.getClass.getSimpleName)
    .set("es.nodes", "127.0.0.1")
    .set("es.port", "8200")


    val sc1 = new SparkContext(conf)
    val resource = null;
    val query = null;
    import org.elasticsearch.spark._

    val documents  = sc1.esRDD(resource,query)

  }
}
