scala-pimps
==============

[![Build Status](https://panasoft.ci.cloudbees.com/job/panasoft-pimps%20test/badge/icon)](https://panasoft.ci.cloudbees.com/job/panasoft-pimps%20test/)

This is simple scala library that provides some frequent routines often used by my codes.


**The `uMap` (unboxed map)**
--------
`uMap` works as standard `List.map(f: A => B)`'s function. Difference is that you don't need a value to be wrapped into container. It works on pure value.
`def uMap[A](f: A => B): B` takes value and applies function `f` on it returning it's result.

      //Let's say this is our simple test model.
      //
      case class A[T](t: T)
      case class B[T](t: T)
      case class C[T1, T2](t1: T1, t2: T2)

      //ugly syntax,
      //read the code and realize that you recursively trying to remember that you are inside C then inside B then inside A and the first param of A when constructing it is "http://panasoft.pl/" and we must uppercase that param and then in ...
      //... Damn, where was I?
      //
      val b = C(B(A("http://panasoft.pl".toUpperCase())), ":)")

      //nicer syntax using uMap
      val a = "http://panasoft.pl"  // have some string
        .uMap(_.toUpperCase)        // I want to uppercase it
        .uMap(A(_))                 // then result of previous instruction I'm applying to create A, now I have A(...)
        .uMap(B(_))                 // then result of previous instruction I'm applying to create B, now I have B(...)
        .uMap(C(_, ":)"))           // then result of previous instruction I'm applying to create C, now I have C(..., ":)")

**The `andThen` or its alias `andThenPerform`**
--------
`andThen` is used to join executions. Result of `andThen` is the last instruction in chain. Some times you don't need to use `{` and `}` - use `andThen` to join executions.

      //before
      def myRoutine = {
        logger.debug("start of method a")
        sendEmail
        postEvent(...)
        copySomeFiles
        2 + 2
      }

      //using andThen(...)
      def myRoutine = logger.debug("start of method a")
        .andThenPerform(sendEmail)
        .andThenPerform(postEvent(...))
        .andThenPerform(copySomeFiles)
        .andThenPerform(2 + 2)

**The `modify`**
--------
`modify` performs side effects and results in contexed value.

        //standard approach
        def createAndSetupJB(x:Any): JavaBean = {
          val jb = new JavaBean()
          jb.x = x
          jb.y = "Y"
          jb.z = "Z"
          jb
        }

        //nicer syntax
        def createAndSetupJB2(x:Any): JavaBean =
          (new JavaBean()).
          .modify(_.x = x)
          .modify(_.y = "Y")
          .modify(_.z = "Z")




