package object shapely {

  implicit class HListSyntax[L <: HList](val me: L) extends AnyVal {

    def ::[H](head: H): HCons[H, L] = HCons(head, me)

    /**
      * REMOVES ALL INSTANCES OF TYPE A FROM HLIST
      * The entire implementation in in the Remover typeclass.
      */
    def remove[A](implicit R: Remover[A, L]): R.Out = R(me)

    def map[P <: PolymorphicFunction](p: P)(implicit M: Mapper[L, P]): M.Out = M(me)

    /** When an Int is passed in here ex. "list.nth(0)", an implicit
      * conversion from Int to Nat occurs via "implicit def fromInt(int: Int): Nat".
      * The resulting object of reference type Nat (ex. Successor(Zero)) is constructed with macros.
      * Its concrete type (ex. Successor[Zero0]) is passed to the second parameter block via "n.N".
      *
      * n.N is the same as N, hack to make the compiler carry
      * type of n (N <: Nat) from first parameter block to second.
      *
      * Replacing "n.N" with "n.type" causes "Error: could not find implicit value for parameter N"
      *
      * @param N "Nther" is an implicit typeclass proof that indexing is valid.
      * "L" is the type of the HList(me) that is calling "nth".
      *  This expands into a series of calls to "Nther.corecurse" and "Nther.base" (for base/zero)
      */
    def nth(n: Nat)(implicit N: Nther[L, n.N]): N.Out = N(me)
  }

  implicit class NatSyntax[N <: Nat](val me: N) extends AnyVal {

    def toInt(implicit N: ToInt[N]): Int = N.value
  }
}