package controllers;

import model.Sample;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import services.SampleService;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

/**
 * Sample orient controller. Retrieve all data (stored by bootstrap) and returns pretty formatted json.
 *
 * @author Vyacheslav Rusakov
 * @since 17.06.2016
 */
@Singleton
public class SampleController extends Controller {

    @Inject
    private SampleService service;

    /**
     * Return all records in db table as pretty json.
     */
    public Result getAll() {
        final List<Sample> all = service.all();
        return ok(Json.prettyPrint(Json.toJson(all)));
    }

}
