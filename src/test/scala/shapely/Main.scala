package shapely

/**
  * Created by johnreed on 5/21/16.
  */
object Main {

  trait TempTrait {
    type IntT = Int
  }

  class S extends TempTrait {

  }

  def main(args: Array[String]) {
    val t: TempTrait#IntT = 5
    val t2: TempTrait = new S()
    val t3: t2.type = t2
    val v1 = HListTest
    val v2 = NatTest
    val v3 = PolyTest
  }
}
