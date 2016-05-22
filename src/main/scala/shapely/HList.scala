package shapely
import scala.language.higherKinds // Append is a type constructor

sealed trait HList {
  // "L <: HList" is there because we want to keep track of the concrete type.
  type Append[L <: HList] <: HList

  def ++[L <: HList](xs: L): Append[L]
}

final case class HCons[H, T <: HList](head: H, tail: T) extends HList {
  // Context independent type level function. If tail is HNil0, HCons[ H, L ]
  // If Tail is HCons, recurse ex. HCons[ H1, HCons[ H2, L ] ]
  override type Append[L <: HList] = HCons[ H,T#Append[L] ]
  // "HCons[ H,T#Append[L] ]" == "H ::: T#Append[L]"

  override def ++[L <: HList](xs: L): HCons.this.Append[L] = {
    // ignore "Expression of type HCons[H, HCons.this.tail.Append[L]] doesn't conform to HCons.this.Append[L]"
    HCons(head, tail ++ xs)
  }
}
// This is called "HNil0" instead of "HNil" because there is already a type alias "HNil" in package.scala.
case object HNil0 extends HList {
  override type Append[L <: HList] = L

  override def ++[L <: HList](xs: L): HNil0.this.Append[L] = {
    xs
  }
}