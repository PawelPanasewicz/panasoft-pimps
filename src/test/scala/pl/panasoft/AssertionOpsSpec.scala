package pl.panasoft

import org.scalatest.FreeSpec
import java.io.File
import org.scalamock.scalatest.MockFactory

class AssertionOpsSpec extends FreeSpec with MockFactory {

  import Pimps._

  "An `AssertionOps`" - {

    "can be used perform verification" in {

      assert(55
        .assertThat(_ % 5 == 0)
        .assertThat(_ % 10 == 5)
        .assertThat(_ > 0)
        .assertThat(_ < 100)
         === 55
      )

      intercept[AssertionError] {
        51
          .assertThat(_ % 5 == 0)
          .assertThat(_ % 10 == 5)
          .assertThat(_ > 0)
          .assertThat(_ < 100)
      }
    }

    "executes contexted object only once" in {
      trait X {def x:Int}
      val xMock = mock[X]
      inSequence{
        (xMock.x _).expects().returning(55)
      }

      xMock.x
        .assertThat(_ % 5 == 0)
        .assertThat(_ % 10 == 5)
        .assertThat(_ > 0)
        .assertThat(_ < 100)
    }

    class Ex extends RuntimeException
    "can throw custom exception if you provide factory for it" in intercept[Ex] {
      55.throwIf(_ > 0)(new Ex)
    }
  }

}
