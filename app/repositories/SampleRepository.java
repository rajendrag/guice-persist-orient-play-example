package repositories;

import com.google.inject.ProvidedBy;
import com.google.inject.internal.DynamicSingletonProvider;
import com.google.inject.persist.Transactional;
import model.Sample;
import ru.vyarus.guice.persist.orient.repository.command.query.Query;
import ru.vyarus.guice.persist.orient.repository.core.ext.service.result.ext.detach.DetachResult;
import ru.vyarus.guice.persist.orient.support.repository.mixin.crud.ObjectCrud;

import java.util.List;

/**
 * Sample repository. Note that @DetachResult used only for simplicity (to return simple pojos instead of proxies)
 * and it may be not a good idea in other cases (due to forced lazy references loading).
 *
 * @author Vyacheslav Rusakov
 * @since 17.06.2016
 */
@Transactional
@ProvidedBy(DynamicSingletonProvider.class)
public interface SampleRepository extends ObjectCrud<Sample> {

    @Query("select from Sample")
    @DetachResult
    List<Sample> allSamples();
}
