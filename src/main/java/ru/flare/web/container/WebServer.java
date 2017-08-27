package ru.flare.web.container;

import com.google.inject.Injector;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.core.spi.component.ioc.IoCComponentProviderFactory;
import com.sun.jersey.guice.spi.container.GuiceComponentProviderFactory;
import org.glassfish.grizzly.http.server.HttpServer;

import java.io.IOException;
import java.net.URI;
import java.util.concurrent.*;

public class WebServer {
    HttpServer server;
    Thread shutdownTask;

    public void start(Injector injector, URI baseURI) throws IOException {
       System.out.println( "Starting grizzly..." );

       ResourceConfig rc = new PackagesResourceConfig( "ru.flare.web.webapp" );
       IoCComponentProviderFactory ioc = new GuiceComponentProviderFactory( rc, injector );
       server = GrizzlyServerFactory.createHttpServer( baseURI + "services/", rc, ioc );
       server.start();
       System.out.println(String.format( "Jersey app started with WADL available at %sservices/application.wadl", baseURI));
       shutdownTask = new Thread(() -> {
            try {
                System.in.read();
            } catch (IOException e) {
                System.exit(1);
            }
        });
        shutdownTask.start();
    }

    public void stop() {
        server.stop();
    }
}
