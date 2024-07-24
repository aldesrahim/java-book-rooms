package main.service;

import main.model.Model;
import main.util.query.QueryBuilder;
/**
 *
 */
public abstract class BaseService {
    protected Model model;
    protected QueryBuilder queryBuilder;

    public BaseService(Model model) {
        this.model = model;
    }

    public abstract Model getModel();

    public QueryBuilder getQueryBuilder() {
        return getModel().query();
    }
}
