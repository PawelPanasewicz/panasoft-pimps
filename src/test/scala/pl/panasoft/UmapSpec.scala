package pl.panasoft

import org.scalatest.FreeSpec

class UMapSpec extends FreeSpec {

  import Pimps._

  "An `UMapOps`" - {
    "`uMap` can be used to apply function onto called object" in {
      case class A[T](t: T)
      case class B[T](t: T)
      case class C[T1, T2](t1: T1, t2: T2)

      //ugly syntax
      val b = C(B(A("http://panasoft.pl".toUpperCase())), ":)")

      //nice syntax :)
      val a = "http://panasoft.pl"
        .uMap(_.toUpperCase)
        .uMap(A(_))
        .uMap(B(_))
        .uMap(C(_, ":)"))

      assert(a === b)
    }
  }

}
