package shapely
import scala.trace._

object HListTest {

  println("Running HListTest")

  {
    val xs: shapely.:::[Int, shapely.:::[Boolean, shapely.:::[String, HNil0.type]]]
      = 1 :: false :: "hi" :: HNil

    // HCons(1,HCons(false,HCons(hi,HNil0)))"
    SDebug.traceExpression(1 :: false :: "hi" :: HNil)

    val xsHead = xs.head  // 1

    SDebug.traceCode(xsHead)
  }

  {
    val xs: Int ::: Boolean ::: HNil = 1 :: false :: HNil

    xs.tail.head: Boolean
  }

  {
    val xsAppend: shapely.:::[Int, HNil0.type]#Append[shapely.:::[Boolean, HNil0.type]]
      = (1 :: HNil) ++ (false :: HNil)

    // HCons(1,HCons(false,HNil0))
    SDebug.traceExpression((1 :: HNil) ++ (false :: HNil))

    val xsAppendTailHead = xsAppend.tail.head: Boolean

    SDebug.traceCode(xsAppendTailHead) // false
  }

  {
    val xs = 1 :: false :: HNil

    val xsNoInt = xs.remove[Int].head: Boolean

    /*
    // implicit class shapely.HListSyntax
    // type shapely.:::
    shapely.this.`package`.HListSyntax[
    shapely.package.:::[Int,
    shapely.package.:::[Boolean,shapely.HNil.type]]](xs)
    .remove[Int](
      shapely.this.Remover.base[
        Int, shapely.package.:::[Boolean,shapely.HNil.type]
      ]
    )
     */
    // implicit remover of type Remover[Int, Boolean ::: HNil.type]
    SDebug.traceExpression(xs.remove[Int]) // HCons(false,HNil0)"

    val equivalent = xs.remove[Int](shapely.Remover.base[Int, Boolean ::: shapely.HNil.type ])

    /*
    "shapely.this.`package`.HListSyntax[
    shapely.package.:::[Int,shapely.package.:::[Boolean,shapely.HNil.type]]](xs)
    .remove[Boolean](
      shapely.this.Remover.corecurseRebuild[
        Boolean, Int, shapely.package.:::[
          Boolean,shapely.HNil.type
        ]
      ](shapely.this.Remover.base[Boolean, shapely.HNil.type])
    ) -> HCons(1,HNil0)"*/
    val xsNoBoolean = xs.remove[Boolean].head: Int
    SDebug.traceExpression(xs.remove[Boolean]) // HCons(1,HNil0)
  }

  {
    val xs = 1 :: false :: HNil


    object doubleFlip extends PolymorphicFunction {
      // this implicit is used for map
      implicit val i: Case[Int,Int] = at[Int].apply( (elem:Int) => elem * 2 )

      implicit val b = at[Boolean].apply( (elem) => !elem )
    }

    object toString extends PolymorphicFunction {
      // Fallback if no implicit val is defined
      implicit def default[A]: Case[A, String] = {
        at[A].apply( (elem:A) => elem.toString )
      }
    }

    /*
    "xs map doubleFlip" is equivalent to...
    shapely.this.`package`.HListSyntax[
      shapely.package.:::[
        Int,shapely.package.:::[
          Boolean,shapely.HNil.type
        ]
      ]
    ](xs).map[doubleFlip.type](doubleFlip)(
      shapely.this.Mapper.corecurse[
        Int, Int, shapely.package.:::[
          Boolean,shapely.HNil.type
        ], doubleFlip.type
      ](
        doubleFlip.i, shapely.this.Mapper.corecurse[
          Boolean, Boolean, shapely.HNil.type, doubleFlip.type
        ](doubleFlip.b, shapely.this.Mapper.base[doubleFlip.type])
      )
    )*/
    val xsFlip = xs map doubleFlip

    SDebug.traceExpression(xs map doubleFlip) // HCons(2,HCons(true,HNil0))

    val stringHead: String = (xs map toString).head
  }

  {
    val xs = 1 :: false :: true :: HNil

    /*
    "xs.nth(0)" is equivalent to...
    "shapely.this.`package`.HListSyntax[
      shapely.package.:::[
        Int,shapely.package.:::[
          Boolean,shapely.HNil.type
        ]
      ]
    ](xs).nth(shapely.`package`.Zero)(
      shapely.this.Nther.base[
        Int, shapely.package.:::[
          Boolean,shapely.HNil.type
        ]
      ]*/
    xs.nth(0)

    SDebug.traceExpression(xs.nth(0)) // 1

    /*
    "shapely.this.`package`.HListSyntax[
      shapely.package.:::[
        Int,shapely.package.:::[Boolean,shapely.HNil.type]
      ]
    ]
    (xs).nth(  shapely.Succ.apply[shapely.Zero0.type]
      (shapely.`package`.Zero)
    )
    ( shapely.this.Nther.corecurse[
        Int, shapely.HCons[Boolean,shapely.HNil.type],
        shapely.Zero0.type
      ](shapely.this.Nther.base[
        Boolean, shapely.HNil.type
      ])
    )
     */
    SDebug.traceExpression(xs.nth(1)) // false

    SDebug.traceExpression(xs.nth(2))
  }
}