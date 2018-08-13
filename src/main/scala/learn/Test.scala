package learn

/**
  * Created by yaojinquan on 2018/8/3 0003.
  * Author yaojinquan
  */
object Test {
  def main(args: Array[String]): Unit = {
    //zip拉链操作
    //    val list1: List[Int] =List(1,2,3,4,12)
    //    val list2: List[Int] =List(5,6,7,8,9)
    //    val ends: List[(Int, Int)] =list1.zip(list2)
    //    println("元素相同，进行zip拉链操作结果："+ends)
    //    //List((1,5), (2,6), (3,7), (4,8), (12,9))

//    val s = "llkdfdf koooooo dfdfdf llllll #ooodddd";
//    val aa = s.split(" ").filter(_.startsWith("#")).toList;
//    print(aa)
    //List(#ooodddd)

//    val aa = s.split(" ").filter(_.startsWith("#")).toArray
//    aa.foreach(a=>{
//      print(a)
//    })
//    //#ooodddd
//
//    val n = "1 2 3 4 5";
//    val array = n.split(" ").toArray;
//    val bb = array.map((_,1))
//    (1,1)
//    (2,1)
//    (3,1)
//    (4,1)
//    (5,1)

//    val cc = array.map((_,1)).reduce(_ + _);
//
//    cc.foreach(a=>{
//      println(a);
//    })

//    reduce 归约操作
    val list=List(1,2,3,4,5)
//    val result=list.reduce(_+_)
//    println("结果1为:"+result)
//    println("结果2为:"+list.reduceLeft(_+_))
//    println("结果3为:"+list.reduceRight(_+_))
//    println("结果4为:"+list.reduce(_-_))     //reduce默认相当于使用reduceLeft
//    println("结果5为:"+list.reduceLeft(_-_))
//    println("结果6为:"+list.reduceRight(_-_))

//    结果1为:15
//    结果2为:15
//    结果3为:15
//    结果4为:-13
//    结果5为:-13
//    结果6为:3

//    val result = list.drop(1).map((list(0),_))
//    println("结果1为:"+result)
    //结果1为:List((1,2), (1,3), (1,4), (1,5))

    //slice 集合截取  从第3个元素到最后
    val result2 = list.slice(2,list.length);
    println("结果1为:"+result2)
    //结果1为:List(3, 4, 5)


  }
}
