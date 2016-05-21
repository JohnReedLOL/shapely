package shapely

object PolyTest {

  println("Running PolyTest")

  {
    object add extends PolymorphicFunction {
      implicit val i: this.Case[Int, Int] = at[Int].apply( (elem:Int) => elem + 1 )
      implicit val d = at[Double].apply( (elem:Double) => elem + 1.0 )
    }

    add(42)
    add(3.14)
  }
}