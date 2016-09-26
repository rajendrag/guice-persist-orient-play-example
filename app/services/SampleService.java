package services;

import com.google.inject.Inject;
import com.google.inject.persist.Transactional;
import model.Sample;
import repositories.SampleRepository;

import java.util.List;

/**
 * Sample service.
 *
 * @author Vyacheslav Rusakov
 * @since 17.06.2016
 */
@Transactional
public class SampleService {

    @Inject
    private SampleRepository repository;

    public List<Sample> all() {
        return repository.allSamples();
    }
}
