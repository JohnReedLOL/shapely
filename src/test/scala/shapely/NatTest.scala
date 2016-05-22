package shapely
import scala.trace.SDebug

object NatTest {

  System.err.println("Running NatTest")

  {
    SDebug.traceCode(Nat.fromInt(2))
  }

  {
    SDebug.traceCode{
      val fromFive = Nat.fromInt(5)
      fromFive.toInt
    }
  }
}