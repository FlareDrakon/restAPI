package ru.flare.web.container;

import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

public class ServletContainer extends GuiceServletContextListener {

	WebServerConfig webServerConfig = new WebServerConfig();

	@Override
	protected Injector getInjector() {
		return webServerConfig.getInjector();
	}
}
