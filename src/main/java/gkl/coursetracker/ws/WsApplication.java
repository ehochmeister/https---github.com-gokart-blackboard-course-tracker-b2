package gkl.coursetracker.ws;

import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;
import gkl.coursetracker.security.UserValueFactoryProvider;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletContext;

/**
 * Created by shane on 21/09/2016.
 */
public class WsApplication extends ResourceConfig {
    final Logger logger = LoggerFactory.getLogger(WsApplication.class);

    @Inject
    public WsApplication(ServiceLocator serviceLocator, ServletContext servletContext) {

        register(CurrentUserResource.class);
        register(JacksonJsonProvider.class);
        register(UserValueFactoryProvider.class);

        logger.info("Started web service application.");
    }

}
