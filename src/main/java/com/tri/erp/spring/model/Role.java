package com.tri.erp.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by TSI Admin on 11/5/2014.
 */

@Entity
public class Role implements Serializable {

    @Id
    @GeneratedValue
    @Column
    private Integer id;

    @NotNull
    @Length(min = 3, max = 512, message = "Invalid length for username (max=512, min=3)")
    @Column(unique = true)
    private String name;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date createdAt;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    private Date updatedAt;

    @Transient
    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotFound(action = NotFoundAction.IGNORE)
    private ArrayList<Menu> menus = new ArrayList<>();

    @Transient
    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotFound(action = NotFoundAction.IGNORE)
    private ArrayList<PageComponent> pageComponents = new ArrayList<>();

    @Transient
    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotFound(action = NotFoundAction.IGNORE)
    private ArrayList<PageComponent> pageComponentsToEvict = new ArrayList<>();

    @Transient
    @JsonIgnoreProperties(ignoreUnknown = true)
    @NotFound(action = NotFoundAction.IGNORE)
    private ArrayList<Menu> menusToEvict = new ArrayList<>();

    public Role(String name, Date createdAt, Date updatedAt, ArrayList<Menu> menus, ArrayList<PageComponent> pageComponents,
                ArrayList<PageComponent> pageComponentsToEvict, ArrayList<Menu> menusToEvict) {
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.menus = menus;
        this.pageComponents = pageComponents;
        this.pageComponentsToEvict = pageComponentsToEvict;
        this.menusToEvict = menusToEvict;
    }

    public Role(String name, Date createdAt, Date updatedAt) {
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Role(String name) {
        this.name = name;
    }

    public Role() {}

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = null;
    }

    public ArrayList<Menu> getMenus() {
        return menus;
    }

    public void setMenus(ArrayList<Menu> menus) {
        this.menus = menus;
    }

    public ArrayList<PageComponent> getPageComponents() {
        return pageComponents;
    }

    public void setPageComponents(ArrayList<PageComponent> pageComponents) {
        this.pageComponents = pageComponents;
    }

    public ArrayList<PageComponent> getPageComponentsToEvict() {
        return pageComponentsToEvict;
    }

    public void setPageComponentsToEvict(ArrayList<PageComponent> pageComponentsToEvict) {
        this.pageComponentsToEvict = pageComponentsToEvict;
    }

    public ArrayList<Menu> getMenusToEvict() {
        return menusToEvict;
    }

    public void setMenusToEvict(ArrayList<Menu> menusToEvict) {
        this.menusToEvict = menusToEvict;
    }
}
