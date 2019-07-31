package com.neon.lms.ResponceModel;

public class NetForumDataResultDiscussionsDataCategoryParents {
    private String color;
    private Object updated_at;
    private int parent_id;
    private String name;
    private Object created_at;
    private int id;
    private String slug;
    private int order;
    private NetForumDataResultDiscussionsDataCategoryParentsParents[] parents;

    public String getColor() {
        return this.color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Object getUpdated_at() {
        return this.updated_at;
    }

    public void setUpdated_at(Object updated_at) {
        this.updated_at = updated_at;
    }

    public int getParent_id() {
        return this.parent_id;
    }

    public void setParent_id(int parent_id) {
        this.parent_id = parent_id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Object getCreated_at() {
        return this.created_at;
    }

    public void setCreated_at(Object created_at) {
        this.created_at = created_at;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSlug() {
        return this.slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int getOrder() {
        return this.order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public NetForumDataResultDiscussionsDataCategoryParentsParents[] getParents() {
        return this.parents;
    }

    public void setParents(NetForumDataResultDiscussionsDataCategoryParentsParents[] parents) {
        this.parents = parents;
    }
}
