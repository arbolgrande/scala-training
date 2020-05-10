/**
  * sbt setting for this project
  */

name := "scala-training"

scalaVersion := "2.12.11"
resolvers ++= Seq(
  "Typesafe Releases"  at "http://repo.typesafe.com/typesafe/releases/"
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
  "org.jsoup"               % "jsoup"                % "1.7.3",
  "org.typelevel"           %% "cats-core"           % "2.0.0",
  "org.typelevel"           %% "cats-free"           % "2.0.0",
  "com.typesafe.slick"      %% "slick"               % "3.2.1",
  "com.typesafe.slick"      % "slick-hikaricp_2.11"  % "3.2.1",
  "org.slf4j"               % "slf4j-nop"            % "1.6.4",
  "mysql"                   % "mysql-connector-java" % "5.1.39",
  guice,
  "org.scalatest"           %% "scalatest"           % "3.0.5"    % Test,
  "org.seleniumhq.selenium" % "selenium-java"        % "3.141.59" % Test
)

