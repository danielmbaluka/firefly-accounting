package com.tri.erp.spring.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by TSI Admin on 2/26/2015.
 */

@Entity
public class PageComponent implements Serializable {

    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @Column
    private String html;

    @Column
    private String description;

    @Column
    private String clazz;

    @Column
    private String domId;

    @ManyToOne
    @JoinColumn(name = "FK_pageId")
    private Page page;

    @ManyToOne
    @JoinColumn(name = "FK_viewRouteId")
    private Route viewRoute;

    @ManyToOne
    @JoinColumn(name = "FK_actionRouteId")
    private Route actionRoute;

    public PageComponent() {}

    public PageComponent(String html, String description, String clazz, String domId, Page page, Route viewRoute, Route actionRoute) {
        this.html = html;
        this.description = description;
        this.clazz = clazz;
        this.domId = domId;
        this.page = page;
        this.viewRoute = viewRoute;
        this.actionRoute = actionRoute;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getDomId() {
        return domId;
    }

    public void setDomId(String domId) {
        this.domId = domId;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Route getViewRoute() {
        return viewRoute;
    }

    public void setViewRoute(Route viewRoute) {
        this.viewRoute = viewRoute;
    }

    public Route getActionRoute() {
        return actionRoute;
    }

    public void setActionRoute(Route actionRoute) {
        this.actionRoute = actionRoute;
    }

    @Override
    public String toString() {
        return this.getHtml();
    }
}
