import sbt._
import Keys._
import sbt.Process
import scala.sys.process.{ProcessLogger, Process}

object Build extends Build {

  lazy val panasoftPimpsProject = Project("panasoft-pimps", file("."))
    .settings(
    version := "0.1-SNAPHOT",
    organization := "pl.panasoft",
    organizationName := "pl.panasoft",
    scalaVersion := "2.10.1",
    scalacOptions ++= List("-feature", "-language:implicitConversions"),
    resolvers ++= commonResolvers,
    libraryDependencies ++= commonDependencies,
    testOptions in Test += Tests.Argument("-oFD") //W - without color, F - show full stack traces, S - show short stack traces, D - show durations
  )

  val commonDependencies = List(
    "org.scalatest" %% "scalatest" % "1.9.1" % "test",
    "org.scalamock" %% "scalamock-scalatest-support" % "3.0.1" % "test"
  )
  val commonResolvers = List(
    "Sonatype repository releases" at "https://oss.sonatype.org/content/repositories/releases/",
    "Maven central repository" at "http://repo1.maven.org/maven2/",
    "Typesafe repository releases" at "http://repo.typesafe.com/typesafe/releases/",
    "Typesafe repository ivy-releases" at "http://repo.typesafe.com/typesafe/ivy-releases/",
    Resolver.url("Typesafe repository ivy pattern", url("http://repo.typesafe.com/typesafe/releases/"))(Resolver.ivyStylePatterns)
  )
}


