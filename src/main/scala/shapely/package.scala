package object shapely {
  type :::[H, T <: HList] = HCons[H, T]

  type HNil = HNil0.type
  val HNil = HNil0

  type Zero = Zero0.type
  val Zero = Zero0

  implicit class HListSyntax[L <: HList](val self: L) extends AnyVal {

    def ::[H](head: H): H ::: L = HCons(head, self)

    def remove[A](implicit R: Remover[A, L]): R.Out = R(self)

    def map[P <: PolymorphicFunction](p: P)(implicit M: Mapper[L, P]): M.Out = M(self)

    /** When an Int is passed in here ex. "list.nth(0)", an implicit
      * conversion from Int to Nat occurs via "implicit def fromInt(int: Int): Nat".
      * The resulting object of reference type Nat (ex. Successor(Zero)) is constructed with macros.
      * Its concrete type (ex. Successor[Zero0]) is passed to the second parameter block via "n.N".
      *
      * n.N is the same as N, hack to make the compiler carry
      * type of n (N <: Nat) from first parameter block to second.
      *
      * Replacing "n.N" with "n.type" causes "Error: could not find implicit value for parameter N"
      * @param N "Nther" is an implicit typeclass proof that indexing is valid.
      * "L" is the type of the HList (self) that is calling "nth".
      *  This expands into a series of calls to "Nther.corecurse" and "Nther.base" (for base/zero)
      */
    def nth(n: Nat)(implicit N: Nther[L, n.N]): N.Out = N(self)
  }

  implicit class NatSyntax[N <: Nat](val self: N) extends AnyVal {

    def toInt(implicit N: ToInt[N]): Int = N.value
  }
}