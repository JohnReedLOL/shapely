organization := "com.codecommit"

name := "shapely"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.8"

scalacOptions in Compile += "-language:_"

scalacOptions ++= Seq("-Xfatal-warnings", "-deprecation", "-unchecked", "-feature", "-Xlint", "-Yinline-warnings", "-Ywarn-inaccessible", "-Ywarn-nullary-override", "-Ywarn-nullary-unit")

resolvers += "johnreed2 bintray" at "http://dl.bintray.com/content/johnreed2/maven"

libraryDependencies += "scala.trace" %% "scala-trace-debug" % "2.2.14"

libraryDependencies ++= Seq(
  "org.typelevel" %% "macro-compat" % "1.1.1", // https://github.com/milessabin/macro-compat
  "org.scala-lang" % "scala-reflect" % scalaVersion.value % "provided",
  "org.scala-lang" % "scala-compiler" % scalaVersion.value % "provided",
  compilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
)