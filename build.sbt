name := "LOLFilesImport"

version := "1.0"

scalaVersion := "2.10.3"

scalacOptions += "-deprecation"

resolvers += "Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/"

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.0" % "test"

libraryDependencies ++=
  "org.scalaquery" % "scalaquery_2.9.0-1" % "0.9.5" ::
  "org.scalatest" % "scalatest_2.10" % "2.0" % "test" ::
  "junit" % "junit" % "4.11" % "test" ::
  "org.mockito" % "mockito-core" % "1.9.5" ::
  //"com.novocode" % "junit-interface" % "0.9" % "test->default" ::
  //"com.bca.liveonline" % "LOLDataModel" % "0.0.1-SNAPSHOT-dev03" ::
  "mysql" % "mysql-connector-java" % "5.1.17" ::
//
  "commons-dbcp" % "commons-dbcp" % "1.4" ::
  "org.springframework" % "spring-core" % "3.1+" ::
  "org.springframework" % "spring-beans" % "3.1+" ::
  "org.springframework" % "spring-jdbc" % "3.1+" ::
  "org.springframework" % "spring-tx" % "3.1+" ::
//
//  "org.scala" % "scala-actors" % "2.10.3" ::
  "com.typesafe.akka" %% "akka-actor"    % "2.3+" :: 
  "com.typesafe.akka" %% "akka-slf4j"    % "2.3+" ::
  "com.typesafe.akka" %% "akka-remote"   % "2.3+" ::
  "com.typesafe.akka" %% "akka-agent"    % "2.3+" :: 
  "com.typesafe.akka" %% "akka-testkit"  % "2.3+" % "test" ::
  "com.typesafe.akka" %% "akka-cluster"  % "2.3+" ::
//
/*
  "org.apache.derby" % "derby" % "10.6.1.0" ::
  "org.hsqldb" % "hsqldb" % "2.0.0" ::
  {
    val useJDBC4 = try { classOf[java.sql.DatabaseMetaData].getMethod("getClientInfoProperties"); true }
      catch { case _:NoSuchMethodException => false }
    "postgresql" % "postgresql" % (if(useJDBC4) "8.4-701.jdbc4" else "8.4-701.jdbc3")
  } ::
  "com.h2database" % "h2" % "1.2.140" ::
  "org.xerial" % "sqlite-jdbc" % "3.6.20" ::
*/
  Nil
