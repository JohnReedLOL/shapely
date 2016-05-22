package shapely

import scala.trace.SDebug

/**
  * Created by johnreed on 5/21/16.
  */
object Main {

  trait TempTrait {
    type IntT
  }

  class S extends TempTrait {
    override type IntT = Int
  }

  def main(args: Array[String]) {

    val one: Successor[Zero0.type] = shapely.Successor(shapely.Zero)

    val two: Int = Nat.toInt[Successor[Successor[ZeroT]]]
    SDebug.traceCode(two) // 2

    val fromInt: Nat = Nat.fromInt(2)
    val fromIntCasted: Successor[Successor[shapely.ZeroT]] =
      fromInt.asInstanceOf[Successor[Successor[ZeroT]]]

    SDebug.traceExpression(fromInt) // Successor(Successor(shapely.Zero0$@7cbd213e))
    SDebug.traceExpression(fromIntCasted) // the cast works

    val t: S#IntT = 5
    val t2: TempTrait = new S()
    val t3: t2.type = t2
    val v1 = HListTest
    val v2 = NatTest
    val v3 = PolyTest
  }
}
