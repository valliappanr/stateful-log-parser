name := "CustomerCardProcessor"

version := "1.0"

scalaVersion := "2.11.8"

val monocleVersion = "1.4.0"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.1"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"
libraryDependencies += "org.typelevel" %% "cats" % "0.9.0"
libraryDependencies += "com.danielasfregola" %% "random-data-generator" % "2.0"
libraryDependencies ++= Seq(
  "com.github.julien-truffaut" %% "monocle-core" % monocleVersion,
  "com.github.julien-truffaut" %% "monocle-macro" % monocleVersion,
  "com.github.julien-truffaut" %% "monocle-law" % monocleVersion % "test"
)