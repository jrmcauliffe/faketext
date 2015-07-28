name := "faketext"

version := "0.0.1"

organization := "org.birchavenue"

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-deprecation", "-unchecked")

libraryDependencies ++= {
  Seq(
    "org.clapper"       %% "grizzled-slf4j"  % "1.0.2",
    "com.github.scopt"  %% "scopt"           % "3.3.0"
  )
}
