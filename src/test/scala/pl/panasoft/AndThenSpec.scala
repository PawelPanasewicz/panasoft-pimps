package pl.panasoft

import org.scalatest.FreeSpec
import java.io.File
import org.scalamock.scalatest.MockFactory

class AndThenSpec extends FreeSpec with MockFactory {

  import Pimps._

  "An `AndThenOps`" - {

    "can be used to join execution of computations returning result of last one" in {
      trait X { def a: Any; def b: Any; def c: Any}
      val xMock = mock[X]
      inSequence {
        (xMock.a _).expects().returning("A")
        (xMock.b _).expects().returning("B")
        (xMock.c _).expects().returning("C")
      }
      assert(xMock.a andThen xMock.b andThen xMock.c === "C")
    }

    "if `andThen` is reserved you can use `andThenPerform` alias" in {
      val andThenReserved = new {
        def andThen(a: Any) = throw new Exception("should not be called")
      }
      assert(andThenReserved andThenPerform 234 andThen 345 andThen 456 === 456)
      assert(andThenReserved andThenPerform "asd" andThenPerform new File(".") andThenPerform new java.net.URL("http://panasoft.pl/") andThenPerform 456 === 456)

      //alternative coding convention
      assert(
        andThenReserved
          .andThenPerform("asd")
          .andThenPerform(new File("."))
          .andThenPerform(new java.net.URL("http://panasoft.pl/"))
          .andThenPerform(456)
          === 456
      )
    }
  }

}
