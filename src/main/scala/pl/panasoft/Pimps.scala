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


}



object Pimps extends Pimps