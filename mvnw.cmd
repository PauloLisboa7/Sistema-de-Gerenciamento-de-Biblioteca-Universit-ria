@echo off
setlocal

set MAVEN_PROJECTBASEDIR=%~dp0
set MAVEN_OPTS=

set JAVA_EXE=java.exe
if defined JAVA_HOME (
  set JAVA_EXE=%JAVA_HOME%\bin\java.exe
)

set MVNW_VERBOSE=
set MVNW_DEBUG=

set ARGS=%*

"%JAVA_EXE%" %MAVEN_OPTS% -classpath "%MAVEN_PROJECTBASEDIR%.mvn\wrapper\maven-wrapper.jar" org.apache.maven.wrapper.MavenWrapperMain %ARGS%
