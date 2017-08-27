package ru.flare.web;

import com.google.inject.Injector;
import ru.flare.web.container.WebServer;
import ru.flare.web.container.WebServerConfig;

import java.io.IOException;
import java.net.URI;

public class App {


    public static void main(String[] arg) throws IOException {
        WebServerConfig webServerConfig = new WebServerConfig();
        URI baseURI = webServerConfig.getBaseURI();
        Injector injector = webServerConfig.getInjector();
        final WebServer webServer = new WebServer();
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                webServer.stop();
            }
        });
        webServer.start(injector, baseURI);

    }
}
