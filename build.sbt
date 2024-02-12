name := """chat_test"""
organization := "fugaku-sat"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.12"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "7.0.0" % Test
val PekkoVersion = "1.0.2"
libraryDependencies += "org.apache.pekko" %% "pekko-actor-typed" % PekkoVersion

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "fugaku-sat.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "fugaku-sat.binders._"
