package org.hsweb.ezorm.core.dsl;

import org.hsweb.ezorm.core.ConditionalFromBean;
import org.hsweb.ezorm.core.NestConditionalFromBean;
import org.hsweb.ezorm.core.TermTypeConditionalSupport;
import org.hsweb.ezorm.core.param.QueryParam;

import java.util.List;

/**
 * @author zhouhao
 */
public final class AbstractConditionalFromBean<T, Q extends QueryParam> implements ConditionalFromBean<AbstractConditionalFromBean<T, Q>> {
    private TermTypeConditionalSupport.Accepter accepter = this::and;
    private Query<T, Q>                         proxy    = null;

    public AbstractConditionalFromBean(Query<T, Q> proxy) {
        this.proxy = proxy;
    }

    @Override
    public Object getBean() {
        return proxy.getBean();
    }

    @Override
    public NestConditionalFromBean<AbstractConditionalFromBean<T, Q>> nest() {
        return new SimpleNestConditionalForBean<>(this, proxy.getParam().nest());
    }

    @Override
    public NestConditionalFromBean<AbstractConditionalFromBean<T, Q>> nest(String column) {
        return new SimpleNestConditionalForBean<>(this, proxy.getParam().nest(column, getValue(column)));
    }

    @Override
    public NestConditionalFromBean<AbstractConditionalFromBean<T, Q>> orNest() {
        return new SimpleNestConditionalForBean<>(this, proxy.getParam().orNest());
    }

    @Override
    public NestConditionalFromBean<AbstractConditionalFromBean<T, Q>> orNest(String column) {
        return new SimpleNestConditionalForBean<>(this, proxy.getParam().orNest(column, getValue(column)));
    }

    @Override
    public AbstractConditionalFromBean<T, Q> and() {
        accepter = this::and;
        return this;
    }

    @Override
    public AbstractConditionalFromBean<T, Q> or() {
        accepter = this::or;
        return this;
    }

    public AbstractConditionalFromBean<T, Q> and(String column, String termType, Object value) {
        and();
        proxy.and(column, termType, value);
        return this;
    }

    public AbstractConditionalFromBean<T, Q> or(String column, String termType, Object value) {
        or();
        proxy.or(column, termType, value);
        return this;
    }

    @Override
    public AbstractConditionalFromBean<T, Q> and(String column, String termType) {
        and(column, termType, getValue(column));
        return this;
    }

    @Override
    public AbstractConditionalFromBean<T, Q> or(String column, String termType) {
        or(column, termType, getValue(column));
        return this;
    }

    @Override
    public TermTypeConditionalSupport.Accepter<AbstractConditionalFromBean<T, Q>> getAccepter() {
        return accepter;
    }

    public AbstractConditionalFromBean<T, Q> selectExcludes(String... columns) {
        proxy.selectExcludes(columns);
        return this;
    }

    public AbstractConditionalFromBean<T, Q> select(String... columns) {
        proxy.select(columns);
        return this;
    }

    public List<T> list() {
        return proxy.list();
    }

    public List<T> listNoPaging() {
        return proxy.noPaging().list();
    }

    public <Q> List<Q> list(Query.ListExecutor<Q, QueryParam> executor) {
        return proxy.noPaging().list(executor);
    }

    public T single() {
        return proxy.single();
    }

    public int total() {
        return proxy.total();
    }

    public <Q> Q single(Query.SingleExecutor<Q, QueryParam> executor) {
        return proxy.single(executor);
    }

    public int total(Query.TotalExecutor<QueryParam> executor) {
        return proxy.total(executor);
    }

    public Q getParam() {
        return proxy.getParam();
    }

}
