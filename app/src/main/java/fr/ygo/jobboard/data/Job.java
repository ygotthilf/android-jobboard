package fr.ygo.jobboard.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Yoann on 04/10/2014.
 */
public class Job {

    private static final SimpleDateFormat ISO_FORMAT = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");

    private String id;
    private String title;
    private String description;
    private String duration;
    private String start;
    private String price;
    private String location;
    private String publisher;
    private String email;
    private Date created;

    public Job(String id, String title, String description, String duration, String start, String price, String location, String publisher, String email, Date created) {
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

    public Job(int id, String title, String description, String duration, String start, String price, String location, String publisher, String email, Date created) {
         this(Integer.toString(id), title, description,duration,start,price,location,publisher,email,created);
    }

    public Job(){

    }

    public static Job fromJson (JSONObject obj) throws JSONException{

        Job job = new Job();




        job.setId(obj.optString("_id"));
        job.setTitle(obj.optString("title"));
        job.setDescription(obj.optString("description"));
        job.setPublisher(obj.optString("publisher"));
        job.setStart(obj.optString("start"));
        job.setPrice(obj.optString("price"));
        job.setDuration(obj.optString("duration"));
        job.setLocation(obj.optString("location"));

        String isoDate =obj.optString("created");

        if(isoDate!=null) {
            try {
                job.setCreated(ISO_FORMAT.parse(isoDate));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return job;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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
