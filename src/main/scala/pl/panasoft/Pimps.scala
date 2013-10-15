package pl.panasoft

trait Pimps {

  implicit class AndThenOps[A](self: => A) {

    def andThen[X](f: => X) = {
      self;
      f
    }

    /**
     * alias for `andThen`
     */
    def andThenPerform[X] = andThen[X] _
  }

  implicit class UMapOps[A](self: => A) {
    def uMap[X](f: A => X) = f(self)
  }

  implicit class ModifyOps[A](self: => A) {
    def modify(f: A => _): A = {
      val onceCalledSelf = self
      f(onceCalledSelf) andThen onceCalledSelf
    }
  }

  implicit class AssertionOps[A](self: => A) {

    def assertThat(test: A => Boolean, errMessage: String = ""): A = {
      val onceCalledSelf = self
      if (!test(onceCalledSelf)) throw new AssertionError(errMessage)
      else onceCalledSelf
    }

    def throwIf[E <: RuntimeException](test: A => Boolean)(e: => E): A = {
      val onceCalledSelf = self
      if (test(onceCalledSelf)) throw e
      else onceCalledSelf
    }
  }

}


object Pimps extends Pimps