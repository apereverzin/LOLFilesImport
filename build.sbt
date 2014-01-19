name := "LOLFilesImport"

version := "1.0"

scalaVersion := "2.10.3"

scalacOptions += "-deprecation"

libraryDependencies += "org.scalatest" % "scalatest_2.10" % "2.0" % "test"

libraryDependencies ++=
  "org.scalaquery" % "scalaquery_2.9.0-1" % "0.9.5" ::
  "org.scalatest" % "scalatest_2.10" % "2.0" % "test" ::
  "junit" % "junit" % "4.11" % "test" ::
  "org.mockito" % "mockito-core" % "1.9.5" ::
  //"com.novocode" % "junit-interface" % "0.9" % "test->default" ::
  //"com.bca.liveonline" % "LOLDataModel" % "0.0.1-SNAPSHOT-dev03" ::
  "mysql" % "mysql-connector-java" % "5.1.17" ::
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
  "mysql" % "mysql-connector-java" % "5.1.13" ::
*/
  Nil
