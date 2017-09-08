organization := "com.github.wangzaixiang"

name := "scala-services-utils"

version in Global :=  "0.0.1-SNAPSHOT"

scalaVersion := "2.12.3"

lazy val binlog = project.in(file("binlog"))
    .settings(
      name := "commons-binlog",
      libraryDependencies ++= Seq(
        "com.alibaba.otter" % "canal.client" % "1.0.24" excludeAll(
          ExclusionRule(organization = "com.sun.jdmk"),
          ExclusionRule(organization = "com.sun.jmx"),
          ExclusionRule(organization = "javax.jms")
        )
      )
    )

lazy val actions = project.in(file("action"))
  .settings(
    name := "commons-action"
  )

lazy val assertion = project.in(file("assertion"))
    .settings(
      name := "commons-assertion"
    )

lazy val eventbus = project.in(file("eventbus"))
    .settings(
      name := "commons-eventbus"
    )
