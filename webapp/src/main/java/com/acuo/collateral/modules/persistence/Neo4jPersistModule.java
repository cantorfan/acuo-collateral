package com.acuo.collateral.modules.persistence;

import com.acuo.collateral.modules.configuration.PropertiesHelper;
import com.acuo.collateral.services.Neo4jPersistService;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.persist.PersistModule;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.Transactional;
import com.google.inject.persist.UnitOfWork;
import org.aopalliance.intercept.MethodInterceptor;
import org.neo4j.ogm.session.Session;

import javax.inject.Inject;
import javax.inject.Named;
import java.lang.reflect.Method;
import java.util.Arrays;

public class Neo4jPersistModule extends PersistModule {

    private MethodInterceptor transactionInterceptor;

    private static final class TransactionalMethodMatcher extends AbstractMatcher<Method> {

        @Override
        public boolean matches(final Method method) {
            return method.isAnnotationPresent(Transactional.class) && !method.isSynthetic();
        }
    }

    private static final class TransactionalClassMethodMatcher extends AbstractMatcher<Method> {

        @Override
        public boolean matches(final Method method) {
            return !method.isSynthetic();
        }
    }


    @Override
    protected void configurePersistence() {

        bind(Packages.class).toProvider(PackagesProvider.class);
        bind(Neo4jPersistService.class).in(Singleton.class);
        bind(PersistService.class).to(Neo4jPersistService.class);
        bind(UnitOfWork.class).to(Neo4jPersistService.class);
        bind(Session.class).toProvider(Neo4jPersistService.class);

        transactionInterceptor = new Neo4jLocalTxnInterceptor();
        requestInjection(transactionInterceptor);
    }

    @Override
    protected MethodInterceptor getTransactionInterceptor() {
        return this.transactionInterceptor;
    }

    public static interface Packages {

        String[] value();
    }

    public static class PackagesProvider implements Provider<Packages> {

        private final String[] packages;

        @Inject
        PackagesProvider(@Named(PropertiesHelper.NEO4J_OGM_PACKAGES) String packages) {
            this.packages = Arrays.stream(packages.split(",")).map(x -> x.trim()).toArray(size -> new String[size]);
        }

        @Override
        public Packages get() {
            return new Packages() {
                @Override
                public String[] value() {
                    return packages;
                }
            };
        }

    }

}
