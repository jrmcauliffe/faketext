name := "faketext"

version := "0.0.1"

organization := "org.birchavenue"

scalaVersion := "2.11.7"

mainClass in assembly := Some("org.birchavenue.faketext.Main")

assemblyJarName in assembly := "faketext.jar"

scalacOptions ++= Seq("-deprecation", "-unchecked")

resolvers += Resolver.sonatypeRepo("public")

libraryDependencies ++= {
  Seq(
    "org.clapper"       %% "grizzled-slf4j"  % "1.0.2",
    "com.github.scopt"  %% "scopt"           % "3.3.0"
  )
}
