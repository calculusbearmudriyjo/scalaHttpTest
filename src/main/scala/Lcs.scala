object Lcs {
  def maxSub(a: String, b: String) = {
    println("a : " + a + " b: " + b)
    var tmpB = b
    var res = ""
    var start = 0
    for{el <- a
        j <- start until tmpB.length} {
      if(tmpB.length > j && el == tmpB.charAt(j)) {
        start = if (j -1 < 0) 0 else j -1
        res += tmpB.charAt(j)
        tmpB = new StringBuilder(tmpB).deleteCharAt(j).toString()
      }
    }
    res
  }

  def lcs(a: String, b: String): String = {
    var listIterA, listIterB: List[String] = List()
    for(i <- 0 until a.length) {
      listIterA ::= a.drop(i)
    }

    for(i <- 0 until b.length) {
      listIterB ::= b.drop(i)
    }
    var pairString = listIterA.flatMap(x => listIterB.map(y => Tuple2(x,y)))
    val res = pairString.map(x => maxSub(x._1, x._2))
    println(res)
    ""
  }

  def main(args: Array[String]): Unit = {
    maxSub("nothertest", "notatest")
//    println(Lcs.lcs("anothertest", "notatest"))
    //  Lcs.lcs( "abcdef", "abc" )
    //  Lcs.lcs( "abcdef", "acf" )
    //  Lcs.lcs( "132535365", "123456789" )
    //  Lcs.lcs( "abcdefghijklmnopq", "apcdefghijklmnobq" )
  }
}
