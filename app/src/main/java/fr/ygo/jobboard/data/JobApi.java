package fr.ygo.jobboard.data;

import android.content.Context;
import android.util.Pair;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 03/11/2014.
 */
public class JobApi {

    private static final String API_BASE_URL = "https://jobboard-api.herokuapp.com/api/";

    private static final boolean LOCAL_DATA = false;

    public static List<Job> getJobs (Context context) throws IOException, JSONException {

        Pair<Integer, String> response = SimpleRestApi.get(API_BASE_URL + "jobs");

        if(response.first != 200){
            throw new IOException("jobs not found. Code = "+response.first);
        }

        JSONArray array=new JSONArray(response.second);

        ArrayList<Job> jobs = new ArrayList<Job>(array.length());

        for(int i=0; i<array.length(); i++){
            JSONObject object=array.getJSONObject(i);
            jobs.add(Job.fromJson(object));
        }

        return jobs;
    }

    public static Job getJob (Context context, String id) throws IOException, JSONException {

        Pair<Integer, String> response = SimpleRestApi.get(API_BASE_URL + "jobs/" + id);

        if(response.first != 200){
            throw new IOException("jobs not found. Code = "+response.first);
        }

        JSONObject object=new JSONObject(response.second);

        return Job.fromJson(object);
    }

    public static boolean postApply (Context context, String jobId, String email, String message) throws IOException, JSONException {

        JSONObject object=new JSONObject();
        object.put("job", jobId);
        object.put("email",email);
        object.put("message", message);
        object.put("name", "Android");

        Pair<Integer, String> response = SimpleRestApi.post(API_BASE_URL + "applies", object.toString());

        return response.first == 200;
    }
}
