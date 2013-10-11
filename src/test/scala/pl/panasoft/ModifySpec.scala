package pl.panasoft

import org.scalatest.FreeSpec
import java.io.File
import scala.beans.BeanProperty
import org.scalamock.scalatest.MockFactory

class ModifySpec extends FreeSpec with MockFactory{

   import Pimps._

  class JavaBean {
    @BeanProperty var x: Any = null
    @BeanProperty var y: Any = null
    @BeanProperty var z: Any = null
  }

   "A `ModifyOps`" - {
     "can be used to join execution of computations performing side effects then returning wrapped object" in {
      val jbMock = mock[JavaBean]
       inSequence{
         (jbMock.setX _).expects("X")
         (jbMock.setY _).expects("Y")
         (jbMock.setZ _).expects("Z")
       }

       //nice invocations when setupping
       jbMock
         .modify(_.setX("X"))
         .modify(_.setY("Y"))
         .modify(_.setZ("Z"))
     }
   }
 }
