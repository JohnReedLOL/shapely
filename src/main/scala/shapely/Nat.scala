package shapely

import scala.annotation.tailrec
import scala.language.experimental.macros
import scala.reflect.macros.whitebox

trait Nat {
  type N <: Nat // this self type allows for implicit conversion with implicit N: Nther[L, n.N]
}

object Nat {

  /** Converts a number like "1" into "Successor[Zero]", for dependent types in the compiler.
    * @param int the int to convert into a Natural number in the form of (Successor(Successor(Zero)))
    * @return the subtype of Nat representing how many successors away from zero we are.
    * @note - the implicit modifier works on macros. At compile time, the compiler
    * searches for ints like "1" and replaces them with "(Successor(Zero))"
    */
  implicit def fromInt(int: Int): Nat = macro NatMacros.materialize

  def toInt[N <: Nat](implicit N: ToInt[N]): Int = N.value
}

@macrocompat.bundle
class NatMacros(val c: whitebox.Context) {
  import c.universe._

  def materialize(int: c.Tree): c.Tree = {
    @tailrec def nest(n: Int, accumulator: Tree): Tree = {
      if (n <= 0) accumulator
      else nest(n - 1, q"shapely.Successor($accumulator)") // recursively nest (Successor(Zero))
    }
    int match {
      case Literal(Constant(n: Int)) if n >= 0 => // this is what generates our natural numbers for xs.nth(0)
        nest(n, q"shapely.Zero")

      case _ =>
        c.abort(c.enclosingPosition, s"not a natural number")
    }
  }
}

object Zero extends Nat {
  // I'm pretty sure these are used to pass the concrete type from () to () in a function definition.
  override type N = Zero.type
}

case class Successor[N0 <: Nat](n: N0) extends Nat {
  override type N = Successor[N0]
}