name := "faketext"

version := "0.0.1"

organization := "org.birchavenue"

scalaVersion := "2.11.7"

mainClass in assembly := Some("org.birchavenue.faketext.Main")

assemblyJarName in assembly := "faketext.jar"

scalacOptions ++= Seq("-deprecation", "-unchecked")

scalacOptions in Test ++= Seq("-Yrangepos")

resolvers += Resolver.sonatypeRepo("public")

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

libraryDependencies ++= {
  Seq(
    "com.github.scopt"  %% "scopt"           % "3.3.0",
    "org.specs2"        %% "specs2-core"     % "3.6.3"  % "test"
  )
}
