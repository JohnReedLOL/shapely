package shapely

trait PolymorphicFunction {

  final def at[A]: Caser[A] = {
    new Caser[A]
  }

  /** This is what does the polymorphic mapping
    * @param a the thing to be mapped
    * @param C the Case, provided as an implicit val or def in the user-instantiated subclass
    * @tparam A the type of A, the thing to be mapped from
    * @tparam B the type of B, the thing to be mapped to
    * @return the result of applying the Case function on the thing to be mapped.
    * @note "this.Case" not only limits C to Case in PolymorphicFunction,
    * it also tells the compiler to search the current object (user-instantiated subclass)
    * for the implicit value of C.
    */
  def apply[A, B](a: A)(implicit C: this.Case[A, B]): B = C(a)

  final class Caser[A] {
    def apply[B](f: A => B): Case[A, B] = new Case[A, B] {
      override def apply(a: A) = f(a)
    }
  }

  sealed trait Case[A, B] {
    def apply(a: A): B
  }
}