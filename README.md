# [Play](https://www.playframework.com/) integration for [guice-persist-orient](https://github.com/xvik/guice-persist-orient) example

### About

Sample prepared with activator 1.3.10 for play 2.5.8 using guice-persist-orient 3.2.0 (orient 2.1.23).
[Official Java seed](https://github.com/playframework/playframework/tree/master/templates/play-java) was used.

[play java docs](https://www.playframework.com/documentation/2.5.x/JavaHome)

### Run

Run app either from IDE or using activator:

```
$ bin/activator run
```

Open [http://localhost:9000](http://localhost:9000) to see default home screen. Useful to check that guice context correctly started.

Next open [http://localhost:9000/samples](http://localhost:9000/samples) to see sample json output - demo of using repository for accessing db.

### Integration

guice-persist-orient dependency ([build.sbt](build.sbt)):

```scala
libraryDependencies ++= Seq(
  javaJdbc,
  cache,
  javaWs,
  "ru.vyarus" % "guice-persist-orient" % "3.2.0"
)
```

Guice module (recognized by play convention) ([Module](app/Module.java)):

```java
 @Override
    public void configure() {
        // in normal application fb config should be obtained from configuration
        install(new OrientModule("memory:test", "admin", "admin"));
        // initialize scheme from models in package
        install(new PackageSchemeModule(Sample.class.getPackage().getName()));
        // enable repositories support
        install(new RepositoryModule());

        // custom data initializer
        bind(DataInitializer.class).to(Bootstrap.class);
        // bind to play lifecycle (to start / stop persistence support)
        bind(PersistenceLifecycle.class).asEagerSingleton();
    }
```

[Sample](app/model/Sample.java) class will be registered as scheme orient.

[Bootstrap](app/Bootstrap.java) will insert 10 `Sample` records on startup (if table is empty)

[PersistentLifecycle](app/PersistentLifecycle) will start/stop orient persistence support (lifecycle hook).

Due to new play guidelines, global object usage is deprecated and now it's recommended to use 
[constructor as startup hook](https://www.playframework.com/documentation/2.5.x/GlobalSettings#Java).

```java
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
```

**IMPORTANT** pay attention to [preloadOrientEngine()](app/PersistenceLifecycle.java#L30) method, which is a hack to pre-initialize orient
and avoid NoClassDefFound errors on start.

### Sample

Actual sample is very simple: [SampleController](app/controllers/SampleController.java). It simply loads all records in `Sample` table and renders it as json.

[Repository](app/repositories/SampleRepository.java) used for accessing database:

```
    @Query("select from Sample")
    @DetachResult
    List<Sample> allSamples();
```

Note that `@DetachResult` used to convert returned orient proxies to simple pojos (which play could serialize to json).
In normal application, such logic will be in service level or even in controller (but make sure detaching occur inside transaction (@Transactional)).
