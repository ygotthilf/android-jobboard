package fr.ygo.jobboard.data;

import java.util.Date;

/**
 * Created by Yoann on 04/10/2014.
 */
public class Job {

    private int id;
    private String title;
    private String description;
    private String duration;
    private String start;
    private String price;
    private String location;
    private String publisher;
    private String email;
    private Date created;

    public Job(int id, String title, String description, String duration, String start, String price, String location, String publisher, String email, Date created) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.duration = duration;
        this.start = start;
        this.price = price;
        this.location = location;
        this.publisher = publisher;
        this.email = email;
        this.created = created;
    }

    public Job(){

    }

    public String getId() {
        return Integer.toString(id);
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return title;
    }
}
