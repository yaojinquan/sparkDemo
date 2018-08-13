/**
  * Created by yaojinquan on 2018/8/3 0003.
  * Author yaojinquan
  */
package object ch6 {
  def main(args: Array[String]): Unit = {
    //zip拉链操作
    val list1: List[Int] =List(1,2,3,4,12)
    val list2: List[Int] =List(5,6,7,8,9)
    val ends: List[(Int, Int)] =list1.zip(list2)
    println("元素相同，进行zip拉链操作结果："+ends)


    // slice 取第5个到最后的元素
//    val (master,filter) = (args(0),args.slice(5,args.length))
//    //证书信息
//    val configKeys = List("key","add");
//    val map = configKeys.zip(args.slice(1,5).toList)
  }
}
