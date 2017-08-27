package ru.flare.web.container;

import com.google.inject.Guice;
import com.google.inject.Injector;
import ru.flare.web.config.WebModule;

import javax.ws.rs.core.UriBuilder;
import java.net.URI;

public class WebServerConfig {

    private Injector injector = Guice.createInjector(new WebModule());

    public URI getBaseURI() {
        return UriBuilder.fromUri( "http://localhost/" ).port( 9998 ).build();
    }

    public Injector getInjector() {
        return injector;
    }
}
