# tomcat-playground

📚 Learning and exploring Apache Tomcat.

> ### Apache Tomcat
>
> The Apache Tomcat® software is an open source implementation of the Jakarta Servlet, Jakarta Server Pages, Jakarta
> Expression Language, Jakarta WebSocket, Jakarta Annotations and Jakarta Authentication specifications. These
> specifications are part of the Jakarta EE platform.
>
> -- <cite>https://tomcat.apache.org</cite>


## Overview

While Tomcat is old and certainly has a dated appearance, it is still widely used, actively maintained and is even on
the cutting edge of the Java ecosystem with its support for the latest versions of the Jakarta EE specifications and
experimental support for Java virtual threads. I'd like to learn it by directly using its API. The `tomcat-playground`
project is a place for me to do that.

This might turn into more of a servlet playground than a Tomcat playground. We'll see.

**NOTE**: This project was developed on macOS. It is for my own personal use.


## Instructions

Follow these instructions to build and run the example program.

1. Use Java 17
2. Start the demo program and web server
    * ```shell
      ./gradlew run
      ```
3. Open the browser
    * Let's see the final effect by opening the browser to <http://[::1]:8080/message>. You should see a special
      message from the server.
    * The program will log something that looks like the following.
    * ```text
      23:39:48 [main] INFO dgroomes.Main - Let's learn about Apache Tomcat by running an embedded server...
      23:39:49 [main] INFO org.apache.coyote.http11.Http11NioProtocol - Initializing ProtocolHandler ["http-nio-8080"]
      23:39:49 [main] INFO org.apache.catalina.core.StandardService - Starting service [Tomcat]
      23:39:49 [main] INFO org.apache.catalina.core.StandardEngine - Starting Servlet engine: [Apache Tomcat/10.1.11]
      23:39:49 [main] INFO org.apache.coyote.http11.Http11NioProtocol - Starting ProtocolHandler ["http-nio-8080"]
      23:39:49 [main] INFO dgroomes.Main - Open http://[::1]:8080/message in your browser to see the message. Press Ctrl-C to stop the program and server.
      23:40:02 [http-nio-8080-exec-1] DEBUG access-log - 0:0:0:0:0:0:0:1 - - [02/Dec/2023:23:40:02 -0600] "GET /message HTTP/1.1" 200 31
      ```
4. Stop the server
    * When you're ready, stop the demo program and server with the `Ctrl+C` key combination.


## Wish List

General clean-ups, TODOs and things I wish to implement for this project:

* [x] DONE Implement the project. Do the minimum to run an embedded Tomcat server (I'm not very interested in
  a non-embedded Tomcat server at this time. I'm not sure that I'm using the right words).
* [x] DONE Clean up the logging. Is Tomcat logging via SLF4J?
* [x] DONE Access logs?
* [x] DONE What are connectors and why do we need to do `getConnector()`?
* [ ] Consider exploring the servlet API just a little bit more
* [ ] Consider exploring Tomcat's support for Java virtual threads
* [ ] Serve a static file (we love static content, let's have a working example).
* [x] DONE Set the file stuff to a temp directory or something. I'm seeing a `tomcat.8080` directory in the project root.
* [ ] What is Tomcat's (or I guess the Servlet API's) support for routing? I know there isn't really support but the
  paths to the servlets your register is a form of simple routing. Though these days we really expect a router to key
  off of path variables, query parameters, headers, etc. Can we extend the routing code? Where is it?


## Reference

* [Tomcat website](https://tomcat.apache.org/)
* [Tomcat on GitHub](https://github.com/apache/tomcat)
