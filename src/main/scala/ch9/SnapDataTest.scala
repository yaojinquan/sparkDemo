//package ch9
//
//import org.apache.spark.graphx.{Edge, Graph, VertexId}
//import org.apache.spark.mllib.classification.LogisticRegressionWithLBFGS
//import org.apache.spark.mllib.evaluation.MulticlassMetrics
//import org.apache.spark.mllib.linalg.Vectors
//import org.apache.spark.mllib.regression.LabeledPoint
//import org.apache.spark.rdd.RDD
//import org.apache.spark.sql.SparkSession
//
///**
//  * Created by yaojinquan on 2018/8/7 0007.
//  * Author yaojinquan
//  */
//object SnapDataTest {
//  def main(args: Array[String]): Unit = {
//    val spark = SparkSession.builder()
//      .master("local[*]")
//      .appName("Chapter4")
//      .config("spark.some.config.option", "some-value").getOrCreate()
//
//    val sc = spark.sparkContext;
//    val data = sc.textFile("D:\\data\\soc-sign-epinions.txt");
//    val parsedData = data.map(_.split("\t") match {
//      case Array(id1,id2,sign) =>(id1.toLong,id2.toLong,sign.toDouble)
//    })
//
//    val nodes:RDD[(VertexId,String)]=sc.parallelize(0L until 131828L).map(id=>(id,id.toString))
//    val edges:RDD[Edge[String]]=parsedData.filter(_._3==1.0).map{case (id1,id2,sign)=>Edge(id1,id2,id1+"->"+id2)
//    }
//    val network = Graph(nodes,edges)
//    val network_ops = network.ops
//    //输出社交网络的节点数量和正边的数量
//    println("Number of nodes ="+network_ops.numVertices)
//    println("Number of edges = "+network_ops.numEdges)
//
//    val degrees = network_ops.degrees.map{case(id,degree)=>(id.toLong,degree)}
//    val inDegrees = network_ops.inDegrees.map{case (id,degree)=>(id.toLong,degree)}
//    val outDegrees = network_ops.outDegrees.map{case (id,degree)=>(id.toLong,degree)}
//
//    //度特征整合到边的信息
//    val dataset = parsedData.map{case(id1,id2,sign)=>(id1,(id2,sign))
//    }.join(degrees).join(inDegrees).join(outDegrees).map{case (id2,((((id1,sign,degree1,inDegree1,outDegree1),
//    degree2),inDegree2),outDegree2))=>(sign,degree1,inDegree1,outDegree1,degree2,inDegree2,outDegree2)
//    }
//
//    val parsedDataset = dataset.map{case(s,d1,d2,d3,d4,d5,d6)=>
//    if (s==1.0)
//      LabeledPoint(1.0,Vectors.dense(d1,d2,d3,d4,d5,d6))
//     else
//      LabeledPoint(0.0,Vectors.dense(d1,d2,d3,d4,d5,d6))
//    }
//    val positiveDataset = parsedDataset.filter(_.label==1.0)
//    val negativeDataset = parsedDataset.filter(_.label==0)
//    val positiveSplits = positiveDataset.randomSplit(Array(0.6,0.4))
//    val negativeSplits = negativeDataset.randomSplit(Array(0.6,0.4))
//    val training = sc.union(positiveSplits(0),negativeSplits(0)).cache()
//    val testing = sc.union(positiveSplits(1),negativeSplits(1))
//    val model = new LogisticRegressionWithLBFGS().setNumClasses(2).run(training)
//
//    //输出准确率与召回率
//    val predictionAndLabels = testing.map{case LabeledPoint(label,features)=>
//      val prediction = model.predict(features)
//      (prediction,label)
//    }
//    val metrics = new MulticlassMetrics(predictionAndLabels)
//    println(""+metrics.precision)
//  }
//
//}
