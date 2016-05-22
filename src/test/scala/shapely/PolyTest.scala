package shapely
import scala.trace.SDebug

object PolyTest {

  System.err.println("Running PolyTest")

  {
    object add extends PolymorphicFunction {
      implicit val i: this.Case[Int, Int] = at[Int].apply( _+1 )
      implicit val d = at[Double].apply( _+1.0 )
    }

    SDebug.traceCode(add.apply(42)) // implicit param i
    SDebug.traceCode(add.apply(3.14)(add.d))
  }
}