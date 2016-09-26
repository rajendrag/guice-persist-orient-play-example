import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import com.orientechnologies.orient.object.db.OObjectDatabaseTx;
import model.Sample;
import ru.vyarus.guice.persist.orient.db.PersistentContext;
import ru.vyarus.guice.persist.orient.db.data.DataInitializer;

/**
 * Initialize sample data on startup.
 *
 * @author Vyacheslav Rusakov
 * @since 17.06.2016
 */
@Transactional
public class Bootstrap implements DataInitializer {

    @Inject
    private PersistentContext<OObjectDatabaseTx> context;

    @Override
    public void initializeData() {
        final OObjectDatabaseTx db = context.getConnection();

        if (db.countClass(Sample.class) > 0) {
            // already initialized
            return;
        }

        for (int i = 0; i < 10; i++) {
            /*
              Important notion on proxies:
              When you create object you can use simple pojo, but after save don't expect orient will update
              the same instance with id and version!
              Save method will return different object instance (actually proxy) which have new fields set.
             */
            db.save(new Sample("Sample" + i, (int) (Math.random() * 200)));
        }
    }
}
