[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://www.apache.org/licenses/LICENSE-2.0) 

# Geode Write-Behind Event Handling with Spring JPA
1. [Overview](#overview)
2. [Building From Source](#building)

## <a name="overview"></a>Overview

[Apache Geode](http://geode.apache.org/) is a data management platform that provides real-time, 
consistent access to data-intensive applications throughout widely distributed cloud architectures.

This sample project simply demonstrates how [Apache Geode](http://geode.apache.org/) write-behind 
event handling can be easily implemented with the help of [Spring Data JPA](https://spring.io/projects/spring-data-jpa) 
and [Spring Boot Data Geode](https://docs.spring.io/spring-boot-data-geode-build/1.2.7.RELEASE/reference/html5/).

## <a name="building"></a>Building From Source

All platforms require a Java installation with JDK 1.8 or more recent version. The JAVA\_HOME 
environment variable can be set as below:

| Platform | Command |
| :---: | --- |
|  Unix    | ``export JAVA_HOME=/usr/java/jdk1.8.0_121``            |
|  OSX     | ``export JAVA_HOME=/usr/libexec/java_home -v 1.8``     |
|  Windows | ``set JAVA_HOME="C:\Program Files\Java\jdk1.8.0_121"`` |

Clone the current repository in your local environment and, within the directory containing the 
source code, run gradle build:
```
$ ./gradlew build
```
