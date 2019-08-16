val Organization = "com.github.amuramatsu"
// Don't forget to also update src/main/scala/Plugin.scala
val Version = "0.4.0"
val Name = "gitbucket-rst-plugin"

gitbucketVersion := "4.32.0"

val ScalaVersion = "2.13.0"

resolvers ++= Seq(
  Resolver.jcenterRepo
)
lazy val root = (project in file(".")).
  settings(
    sourcesInBase := false,
    organization := Organization,
    name := Name,
    version := Version,
    scalaVersion := ScalaVersion,
    scalacOptions := Seq("-deprecation", "-language:postfixOps"),
    libraryDependencies ++= Seq(
      "org.planet42"        %% "laika-core"         % "0.11.0",
      "net.sourceforge.htmlcleaner" % "htmlcleaner" % "2.16"
    ),
    
    assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)
    
  )
