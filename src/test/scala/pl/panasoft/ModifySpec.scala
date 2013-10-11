package pl.panasoft

import org.scalatest.FreeSpec
import java.io.File
import scala.beans.BeanProperty

class ModifySpec extends FreeSpec {

   import Pimps._

  class JavaBean {
    @BeanProperty var x: Any = null
    @BeanProperty var y: Any = null
    @BeanProperty var z: Any = null
  }

   "A `ModifyOps`" - {
     "can be used to join execution of computations performing side effects then returning wrapped object" in {
      val jb = new JavaBean()
      assert(jb.x === null)
      assert(jb.y === null)
      assert(jb.z === null)

       //nice invocations when setupping
       val changeJb = jb
         .modify(_.setX("X"))
         .modify(_.setY("Y"))
         .modify(_.setZ("Z"))

       assert(changeJb === jb)
       assert(jb.x === "X")
       assert(jb.y === "Y")
       assert(jb.z === "Z")
     }
   }
 }
