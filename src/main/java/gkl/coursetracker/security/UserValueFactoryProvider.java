package gkl.coursetracker.security;

import blackboard.data.user.User;
import blackboard.platform.context.Context;
import blackboard.platform.context.ContextManager;
import blackboard.platform.context.ContextManagerFactory;
import org.glassfish.hk2.api.Factory;
import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.internal.inject.AbstractValueFactoryProvider;
import org.glassfish.jersey.server.internal.inject.MultivaluedParameterExtractorProvider;
import org.glassfish.jersey.server.model.Parameter;

import javax.inject.Inject;

/**
 * Created by shane on 21/09/2016.
 */
public class UserValueFactoryProvider extends AbstractValueFactoryProvider {

    final private ContextManager contextManager;

    @Inject
    public UserValueFactoryProvider(MultivaluedParameterExtractorProvider mpep, ServiceLocator injector) {
        super(mpep, injector, Parameter.Source.UNKNOWN);
        this.contextManager = ContextManagerFactory.getInstance();
    }

    @Override
    protected Factory<?> createValueFactory(Parameter parameter) {
        if (!parameter.isAnnotationPresent(CurrentUser.class) || !User.class.equals(parameter.getRawType())) {
            return null;
        }

        return new Factory<User>() {
            @Override
            public User provide() {
                final Context context = contextManager.getContext();
                if(context == null) {
                    throw new IllegalStateException("No context found.");
                }

                final User user = context.getUser();
                if(user == null) {
                    throw new IllegalStateException("No user in the context.");
                }
                return user;
            }

            @Override
            public void dispose(User instance) {
            }
        };
    }

}
