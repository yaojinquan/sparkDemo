package sparklearn

import org.apache.spark.sql.SparkSession

/**
  * Created by yaojinquan on 2018/7/12 0012.
  * Author yaojinquan
  */
object Stumbleupon {
  def main(args: Array[String]): Unit = {
    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("Chapter4")
      .config("spark.some.config.option", "some-value").getOrCreate()

    val sc = spark.sparkContext
    //读取文件
    val rawData = sc.textFile("");
    //从rawData里面取出数据 一行行取  参数line输入表达式 切分
    val records =  rawData.map(line=>line.split("\t"));
    import org.apache.spark.mllib.linalg.Vectors
    import org.apache.spark.mllib.regression.LabeledPoint
    val data = records.map{r=>
      val trimmed = r.map(_.replaceAll("\"",""));
      //取出最后一列的标记数据
      var label = trimmed(r.size-1).toInt
      //提取特征  将位置4 到最后的元素提取出来  然后替换 其中的?为0.0
      val features =  trimmed.slice(4,r.size-1).map(d=>if(d=='?')0.0 else d.toDouble).map(d=>if(d<0) 0.0 else d)

      LabeledPoint(label,Vectors.dense(features))
    }

    import org.apache.spark.mllib.classification.{LogisticRegressionWithSGD, NaiveBayes, SVMWithSGD}
    import org.apache.spark.mllib.tree.DecisionTree
    import org.apache.spark.mllib.tree.configuration.Algo
    import org.apache.spark.mllib.tree.impurity.Entropy

    val numIterations = 10;
    val stepSize = 10.00;
    val regParam = 12.00;
    val maxTreeDepth = 10;
    //逻辑回归模型训练
    val logModel = LogisticRegressionWithSGD.train(data,numIterations);
    //支持向量机模型训练
    val svmModel = SVMWithSGD.train(data,numIterations);

    NaiveBayes.train(data);

    DecisionTree.train(data,Algo.Classification,Entropy,maxTreeDepth)
  }
}
