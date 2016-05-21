package shapely

trait HList {
  type Append[L <: HList] <: HList

  def ++[L <: HList](xs: L): Append[L]
}

final case class HCons[H, T <: HList](head: H, tail: T) extends HList {
  override type Append[L <: HList] = H ::: T#Append[L]

  override def ++[L <: HList](xs: L): HCons.this.Append[L] = {
    HCons(head, tail ++ xs)
  }
}

case object HNil0 extends HList {
  override type Append[L <: HList] = L

  override def ++[L <: HList](xs: L): HNil0.this.Append[L] = {
    xs
  }
}