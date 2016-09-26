name := """play-java"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "ru.vyarus" % "guice-persist-orient" % "3.2.0"
)

//routesGenerator := InjectedRoutesGenerator

fork in run := true