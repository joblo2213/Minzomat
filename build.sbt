import java.io.File

name := "de.ungefroren.discord.Minzomat"

version := "0.2"

scalaVersion := "2.12.8"

mainClass := Some("de.ungefroren.discord.minzomat.Launcher")

assemblyJarName in assembly := s"Minzomat-${version.value}.jar"

// log4j Logger
libraryDependencies += "org.slf4j" % "slf4j-log4j12" % "1.7.22"

// JDA
resolvers += "jcenter-bintray" at "http://jcenter.bintray.com"
libraryDependencies += "net.dv8tion" % "JDA" % "3.8.3_463"

//resource files
lazy val ADDITIONAL_RESOURCES: Seq[File] = Seq(
  new File("README.md"),
  new File("LICENSE"),
  new File("images/minzomat_emote.png"),
  new File("images/quote_emote.png")
)

lazy val RESOURCES_DIR = new File("src/main/resources")

//Custom tasks
lazy val include = TaskKey[Unit]("include", "Copies the resources that should be packed with the jar to the resources directory")
include := SBTUtillity.includeResources(streams.value.log, RESOURCES_DIR, ADDITIONAL_RESOURCES)