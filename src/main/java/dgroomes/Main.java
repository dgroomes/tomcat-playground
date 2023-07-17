package dgroomes;

import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.Context;
import org.apache.catalina.startup.Tomcat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.io.IOException;
import java.util.logging.LogManager;

/**
 * Please see the README for more information.
 */
public class Main {

    static final Logger log = LoggerFactory.getLogger(Main.class);
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

        // Start the server and make the main thread wait till the server is stopped
        tomcat.start();
        tomcat.getServer().await();
    }

    /**
     * Initialize a {@link Tomcat} instance along with the necessary minimal boilerplate. Part of the boilerplate is
     * the creation of a {@link Context} instance.
     */
    void initTomcatBoilerplate() {
        tomcat = new Tomcat();

        // As with any server, we need to define a port to listen on.
        tomcat.setPort(8080);

        // "Context" is a concept in Tomcat. It's some kind of "container". It's a bit abstract to me. I haven't learned
        // much about it yet. Can we do something with that second parameter "docBase"?
        ctx = tomcat.addContext("", null);

        // This is an odd but required step. See https://stackoverflow.com/a/59282431
        tomcat.getConnector();
    }
}

class MessageServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/plain");
        resp.getWriter().write("Hello from 'tomcat-playground'!");
    }
}
