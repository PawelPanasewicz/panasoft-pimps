package pl.panasoft

import org.scalatest.FreeSpec
import java.io.File

class AndThenSpec extends FreeSpec {

  import Pimps._

  "An `AndThenOps`" - {
    "can be used to join execution of computations returning result of last one" in {
      val a = new {}
      val b = new {}
      val c = new {}
      assert(a andThen b andThen c === c)
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
