/**
  * sbt setting for this project
  */

name := "scala-training"

lazy val commonSettings = Seq(

  scalaVersion := "2.12.6",
  resolvers ++= Seq(
    "Typesafe Releases"  at "http://repo.typesafe.com/typesafe/releases/",
    "Sonatype Release"   at "https://oss.sonatype.org/content/repositories/releases/",
    "Sonatype Snapshot"  at "https://oss.sonatype.org/content/repositories/snapshots/",

  ),
  scalacOptions ++= Seq(
    "-deprecation",            
    "-feature",                
    "-unchecked",              
    "-Xfatal-warnings",        
    "-Xlint",                  
    "-Ywarn-adapted-args",     
    "-Ywarn-dead-code",        
    "-Ywarn-inaccessible",     
    "-Ywarn-nullary-override", 
    "-Ywarn-numeric-widen"
  ),
  libraryDependencies ++= Seq(
    "org.jsoup" % "jsoup" % "1.7.3"
  )
)
