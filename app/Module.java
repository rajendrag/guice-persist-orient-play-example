import com.google.inject.AbstractModule;
import model.Sample;
import play.Configuration;
import play.Environment;
import ru.vyarus.guice.persist.orient.OrientModule;
import ru.vyarus.guice.persist.orient.RepositoryModule;
import ru.vyarus.guice.persist.orient.db.data.DataInitializer;
import ru.vyarus.guice.persist.orient.support.PackageSchemeModule;

/**
 * This class is a Guice module that tells Guice how to bind several
 * different types. This Guice module is created when the Play
 * application starts.
 * <p>
 * Play will automatically use any class called `Module` that is in
 * the root package. You can create modules in other locations by
 * adding `play.modules.enabled` settings to the `application.conf`
 * configuration file.
 */
public class Module extends AbstractModule {

    private final Environment env;
    private final Configuration conf;

    public Module(Environment env, Configuration conf) {
        this.env = env;
        this.conf = conf;
    }

    @Override
    public void configure() {
        // in normal application fb config should be obtained from configuration
        install(new OrientModule("memory:test", "admin", "admin"));
        // initialize scheme from models in package
        install(new PackageSchemeModule(Sample.class.getPackage().getName()));
        // enable repositories support
        install(new RepositoryModule());

        // custom data initializer (it is not required and used only for demo purposes - to generate sample data)
        // simply remove binding if no bootstrap required
        bind(DataInitializer.class).to(Bootstrap.class);

        // bind to play lifecycle (to start / stop persistence support)
        bind(PersistenceLifecycle.class).asEagerSingleton();
    }

}
