import org.apache.spark.sql.SparkSession

/**
  * Created by yaojinquan on 2018/7/16 0016.
  * Author yaojinquan
  */
object SparkTest {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("Chapter4")
      .config("spark.some.config.option", "some-value").getOrCreate()

    val sc = spark.sparkContext;
    val rowData = sc.textFile("E:/ml-100k/u.data");
    print(rowData.first());
  }
}
