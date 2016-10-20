package com.acuo.collateral.learning;

import static com.google.inject.Guice.createInjector;
import static com.google.inject.name.Names.named;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import javax.inject.Inject;
import javax.inject.Named;

import org.junit.Test;

import com.google.inject.AbstractModule;
import com.google.inject.Provider;

public class NamedProviderTest extends AbstractModule {

    public static interface Foo {

        String foo();
    }

    public static class FooProvider implements Provider<Foo> {

        @Override
        public Foo get() {
            return new Foo() {

                @Override
                public String foo() {
                    return "foo";
                }
            };
        }

    }

    public static class FooPrimeProvider implements Provider<Foo> {

        @Override
        public Foo get() {
            return new Foo() {

                @Override
                public String foo() {
                    return "prime";
                }
            };
        }

    }

    @Inject
    public Provider<Foo> fooProvider;

    @Inject
    @Named("prime")
    public Provider<Foo> fooPrimeProvider;

    @Test
    public void shouldInjectFooAndPrime() {
        createInjector(this).injectMembers(this);

        assertThat(fooPrimeProvider.get().foo(), is("prime"));
        assertThat(fooProvider.get().foo(), is("foo"));
    }

    @Override
    protected void configure() {
        bind(Foo.class).toProvider(FooProvider.class);
        bind(Foo.class).annotatedWith(named("prime")).toProvider(FooPrimeProvider.class);
    }

}