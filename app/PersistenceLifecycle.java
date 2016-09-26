import com.google.inject.persist.PersistService;
import play.inject.ApplicationLifecycle;

import javax.inject.Inject;

/**
 * Starts persistence support and registers shutdown hook to stop it.
 * Both points are correct lifecycle points according to play docs (normal lifecycle binding is deprecated).
 *
 * @author Vyacheslav Rusakov
 * @since 17.06.2016
 */
public class PersistenceLifecycle {

    @Inject
    public PersistenceLifecycle(final PersistService persist,
                                final ApplicationLifecycle shutdown) {
        preloadOrientEngine();
        persist.start();

        shutdown.addStopHook(() -> {
            persist.stop();
            return null;
        });
    }

    /**
     * Pre-loads OrientDB using empty (system) TCCL so javax.script engines can be found.
     */
    private static void preloadOrientEngine() {
        ClassLoader tccl = Thread.currentThread().getContextClassLoader();
        try {
            Thread.currentThread().setContextClassLoader(null);
            com.orientechnologies.orient.core.Orient.instance();
        } finally {
            Thread.currentThread().setContextClassLoader(tccl);
        }
    }
}
