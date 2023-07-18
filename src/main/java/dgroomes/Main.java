package dgroomes;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.Context;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.valves.AbstractAccessLogValve;
import org.apache.catalina.valves.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.util.logging.LogManager;

/**
 * Please see the README for more information.
 */
public class Main {

    static final Logger log = LoggerFactory.getLogger(Main.class);
    static final int PORT = 8080;

    Tomcat tomcat;
    Context ctx;

    public static void main(String[] args) throws Exception {
        log.info("Let's learn about Apache Tomcat by running an embedded server...");

        // Tomcat logs with JUL (java.util.logging). We want Tomcat to log through our preferred logging system which is
        // SLF4J (including the logging implementation 'slf4j-simple'). We can bridge JUL to SLF4J using the 'jul-to-slf4j'
        // library and the snippet below.
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.install();

        new Main().run();
    }

    void run() throws Exception {
        initTomcatBoilerplate();

        // Add our custom servlet to the container
        Tomcat.addServlet(ctx, "messageServlet", new MessageServlet());
        ctx.addServletMappingDecoded("/message", "messageServlet");

        configureAccessLogs();

        // Start the server and make the main thread wait till the server is stopped
        tomcat.start();
        log.info("Open http://[::1]:8080/message in your browser to see the message. Press Ctrl-C to stop the program and server.");
        tomcat.getServer().await();
    }

    /**
     * Enable and configure access logging to log to our preferred logging system: SLF4J.
     */
    private void configureAccessLogs() {
        var accessLogValve = new Slf4jAccessLogValve();
        accessLogValve.setPattern(Constants.AccessLog.COMMON_PATTERN);

        tomcat.getHost().getPipeline().addValve(accessLogValve);
    }

    /**
     * Initialize a {@link Tomcat} instance along with the necessary minimal boilerplate. Part of the boilerplate is
     * the creation of a {@link Context} instance.
     */
    void initTomcatBoilerplate() {
        tomcat = new Tomcat();

        // This is kind of annoying but Tomcat, even in embedded mode, requires a working directory. It does stuff with
        // unpacking WAR files and JSP compilation. But we don't use that functionality here so the directory structure
        // is just empty directories. This is called "accidental complexity".
        tomcat.setBaseDir("tomcat-work-dir");

        // As with any server, we need to define a port to listen on.
        tomcat.setPort(PORT);

        // "Context" is a concept in Tomcat. It's some kind of "container". It's a bit abstract to me. I haven't learned
        // much about it yet. Can we do something with that second parameter "docBase"?
        ctx = tomcat.addContext("", null);

        // It's easy to forget that Tomcat requires a "connector". If you miss this, you might be left perplexed/stuck
        // when Tomcat isn't responding to any traffic. See https://stackoverflow.com/a/59282431
        var connector = new Connector("HTTP/1.1");
        connector.setPort(PORT);
        tomcat.getService().addConnector(connector);
    }
}

class MessageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("Hello from 'tomcat-playground'!");
    }
}

/**
 * A custom access log {@link org.apache.catalina.Valve} that logs to SLF4J.
 */
class Slf4jAccessLogValve extends AbstractAccessLogValve {

    private static final Logger log = LoggerFactory.getLogger("access-log");

    @Override
    public void log(CharArrayWriter message) {
        log.info(message.toString());
    }
}
