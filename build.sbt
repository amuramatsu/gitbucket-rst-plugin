val Organization = "com.github.amuramatsu"
// Don't forget to also update src/main/scala/Plugin.scala
val Version = "0.4.0"
val Name = "gitbucket-rst-plugin"

val GitBucketVersion = "4.10"
val GitBucketFullVersion = s"${GitBucketVersion}.0"

val ScalaVersion = "2.12.1"

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
      "io.github.gitbucket" %% "gitbucket" % GitBucketFullVersion  % "provided",
      "com.typesafe.play"   %% "twirl-compiler"     % "1.3.0"      % "provided",
      "javax.servlet"        % "javax.servlet-api"  % "3.1.0"      % "provided",
      "org.planet42"        %% "laika-core"         % "0.7.0",
      "net.sourceforge.htmlcleaner" % "htmlcleaner" % "2.16"
    ),
    
    assemblyOption in assembly := (assemblyOption in assembly).value.copy(includeScala = false)
    
  )
