/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.service;

import main.model.Model;
import main.util.query.QueryBuilder;
/**
 *
 * @author aldes
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
