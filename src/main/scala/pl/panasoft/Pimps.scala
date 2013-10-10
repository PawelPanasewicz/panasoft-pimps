package pl.panasoft

trait Pimps {

  implicit class AndThenOps[A](self: A) {

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

}



object Pimps extends Pimps