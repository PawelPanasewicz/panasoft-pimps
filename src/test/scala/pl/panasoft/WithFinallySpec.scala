package pl.panasoft

import org.scalatest.FreeSpec
import java.io.File
import org.scalamock.scalatest.MockFactory

class WithFinallySpec extends FreeSpec with MockFactory {

  import Pimps._
  trait Res {
    @throws[RuntimeException]
    def read(): String
    def close(): Unit
  }

  "A `WithFinallyOps`" - {
    "can be used to ensure that `finallyF` block will be performed after performing `block` (the loan pattern)" in {

      val res = mock[Res]
      inSequence {
        (res.read _).expects()
        (res.read _).expects()
        (res.read _).expects()
        (res.close _).expects()
      }

      res.withFinally(_.close) {
        res =>
          res.read()
          res.read()
          res.read()
      }
    }

    "`finallyF` block will be performed even if exception is thrown" in {
      val res = mock[Res]
      inSequence {
        (res.read _).expects()
        (res.read _).expects()
        (res.read _).expects().onCall( () => throw new RuntimeException("OMG!"))
        (res.close _).expects()
      }

      intercept[RuntimeException] {
        res.withFinally(_.close) {
          res =>
            res.read()
            res.read()
            res.read() //here throws OMG!
        }
      }
    }
  }

}
