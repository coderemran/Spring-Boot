package com.gulam.Entity;

public class Contentful {
    //entry counter
    private int Counter;
    //title
    private String id, title, slug, imageURL, shortDescription, Description, link;

    //constructor
    public Contentful(int Counter, String id, String title, String slug, String shortDescription, String Description, String imageURL, String link){
        this.Counter            = Counter;
        this.id                 = id;
        this.title              = title;
        this.slug               = slug;
        this.shortDescription   = shortDescription;
        this.Description        = Description;
        this.imageURL           = imageURL;
        this.link               = link;
    }

    public Contentful(){}

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setShortDescription(String shortDescription) {
        this.shortDescription = shortDescription;
    }

    public void setCounter(int counter) {
        Counter = counter;
    }

    public void setImageURL(String imageId) {
        this.imageURL = imageId;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return Description;
    }

    public String getShortDescription() {
        return shortDescription;
    }

    public int getCounter() {
        return Counter;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getSlug() {
        return slug;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }
}
