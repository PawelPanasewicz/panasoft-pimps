package pl.panasoft

class AndThenOps[A](self: => A) {
  def andThen[X](f: => X) = {
    self;
    f
  }

  /**
   * alias for `andThen`
   */
  def andThenPerform[X] = andThen[X] _
}

class UMapOps[A](self: => A) {
  def uMap[X](f: A => X) = f(self)
}

class ModifyOps[A](self: => A) {
  def modify[B](f: A => B): A = {
    val onceCalledSelf = self
    f(onceCalledSelf)
    onceCalledSelf
  }
}

class AssertionOps[A](self: => A) {

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

object Pimps {
  implicit def toAndThenOps[A](a: A) = new AndThenOps(a)
  implicit def toUMapOps[A](a: A) = new UMapOps(a)
  implicit def toModifyOps[A](a: A) = new ModifyOps(a)
  implicit def toAssertionOps[A](a: A) = new AssertionOps(a)
}