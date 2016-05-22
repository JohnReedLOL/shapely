package shapely

trait Remover[A, L <: HList] {
  type Out <: HList

  def apply(xs: L): Out
}

private[shapely] trait RemoverLowPriorityImplicits {

  implicit def base[A, L <: HList]: Remover.Aux[A, HCons[A, L], L] = new Remover[A, HCons[A, L] ] {
    type Out = L

    def apply(xs: HCons[A, L] ) = xs.tail
  }
}

object Remover extends RemoverLowPriorityImplicits {
  type Aux[A, L <: HList, Out0 <: HList] = Remover[A, L] {type Out = Out0}

  implicit def corecurseRemove[A, L <: HList](implicit R: Remover[A, L])
  : Remover.Aux[A, HCons[A, L], R.Out] = new Remover[A, HCons[A, L]] {
    type Out = R.Out

    def apply(xs: HCons[A, L]) = R(xs.tail)
  }

  implicit def corecurseRebuild[A, B, L <: HList](implicit R: Remover[A, L])
  : Remover.Aux[A, HCons[B, L], HCons[B, R.Out]] = new Remover[A, HCons[B, L] ] {
    type Out = HCons[B, R.Out]

    def apply(xs: HCons[B, L] ) = xs.head :: R(xs.tail)
  }
}

trait Mapper[L <: HList, P <: PolymorphicFunction] {
  type Out <: HList

  def apply(xs: L): Out
}

object Mapper {
  type Aux[L <: HList, P <: PolymorphicFunction, Out0 <: HList] = Mapper[L, P] {type Out = Out0}

  implicit def base[P <: PolymorphicFunction]: Mapper.Aux[HNilT, P, HNilT] = new Mapper[HNilT, P] {
    type Out = HNilT

    def apply(xs: HNilT) = xs
  }

  /** @param C Case from A to B (compiler lookup)
    * @param M A mapper over L with P
    * @tparam A Type at the head of the list
    * @tparam B Output type
    * @tparam L The type of the user's HList
    * @tparam P User defines subclass of PolymorphicFunction
    * @return
    */
  implicit def corecurse[A, B, L <: HList, P <: PolymorphicFunction]
  (implicit C: P#Case[A, B], M: Mapper[L, P])
  : Mapper.Aux[ HCons[A, L], P, HCons[B, M.Out] ] = new Mapper[ HCons[A, L], P] {
    type Out = HCons[B, M.Out]

    def apply(xs: HCons[A, L]) = C(xs.head) :: M(xs.tail)
  }
}

trait Nther[L <: HList, N <: Nat] {
  type Out

  def apply(xs: L): Out
}

object Nther {
  type Aux[L <: HList, N <: Nat, Out0] = Nther[L, N] {type Out = Out0}

  implicit def base[A, L <: HList]: Nther.Aux[HCons[A, L], ZeroT, A] = new Nther[HCons[A, L], ZeroT] {
    type Out = A

    def apply(xs: HCons[A, L]) = xs.head
  }

  implicit def corecurse[A, L <: HList, N <: Nat]
  (implicit N: Nther[L, N])
  : Nther.Aux[HCons[A, L], Successor[N], N.Out] = new Nther[HCons[A, L], Successor[N]] {
    type Out = N.Out

    def apply(xs: HCons[A, L]) = N(xs.tail)
  }
}

trait ToInt[N <: Nat] {
  val value: Int
}

object ToInt {

  implicit def base: ToInt[ZeroT] = new ToInt[ZeroT] {
    val value = 0
  }

  /** Gets the successor of N
    *
    * @param N the value to get the successor of
    * @tparam N the type corresponding to that value
    * @return the value plus one (the type is wrapped in Successor[])
    */
  implicit def successor[N <: Nat](implicit N: ToInt[N])
  : ToInt[Successor[N]] = new ToInt[Successor[N]] {
    val value = 1 + N.value
  }
}