package ru.flare.web.config;

import com.google.inject.Singleton;
import ru.flare.web.dao.Dao;
import com.google.inject.TypeLiteral;
import com.google.inject.servlet.ServletModule;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;
import ru.flare.web.dao.UserDao;
import ru.flare.web.dto.User;
import ru.flare.web.webapp.controller.AppExceptionMapper;
import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import java.util.HashMap;
import java.util.Map;

public class WebModule extends ServletModule {

    @Override
    protected void configureServlets() {
        final ResourceConfig rc = new PackagesResourceConfig( "ru.flare.web.webapp.controller" );
        bind( new TypeLiteral<Dao<User>>() {
        } ).to( UserDao.class );

        for ( Class<?> resource : rc.getClasses() ) {
            System.out.println( "Binding resource: " + resource.getName() );
            bind( resource );
        }
        Map<String, String> options = new HashMap<>();
        options.put("com.sun.jersey.api.json.POJOMappingFeature", "true");
        serve( "/services/*" ).with( GuiceContainer.class, options);
        bind(AppExceptionMapper.class);
        bind(JacksonJsonProvider.class).asEagerSingleton();
        bind(ResourceConfig.class).toInstance(rc);
    }
}
