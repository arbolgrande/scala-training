/**
  * sbt setting for this project
  */

name := "scala-training"

scalaVersion := "2.12.6"
resolvers ++= Seq(
  "Typesafe Releases"  at "http://repo.typesafe.com/typesafe/releases/",
  "Sonatype Release"   at "https://oss.sonatype.org/content/repositories/releases/",
  "Sonatype Snapshot"  at "https://oss.sonatype.org/content/repositories/snapshots/",
)
scalacOptions ++= Seq(
  "-deprecation",
  "-feature",
  "-unchecked",
  "-Ywarn-adapted-args",
  "-Ywarn-dead-code",
  "-Ywarn-inaccessible",
  "-Ywarn-nullary-override",
  "-Ywarn-numeric-widen",
  "-Ypartial-unification"
)
libraryDependencies ++= Seq(
  "org.jsoup"               % "jsoup"         % "1.7.3",
  "org.typelevel"           %% "cats-core"    % "2.0.0",
  "org.scalatest"           %% "scalatest"    % "3.0.5"    % "test",
  "org.seleniumhq.selenium" % "selenium-java" % "3.141.59" % "test",
)

