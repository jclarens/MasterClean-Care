package com.mvp.mobile_art.Model.Array;

import com.mvp.mobile_art.Model.Basic.Job;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zackzack on 12/06/2017.
 */

public class ArrayProfesi {
    private List<Job> jobs = new ArrayList<>();
    public ArrayProfesi() {

    }
    public void addProfesi(String text){
        Job job = new Job();
        job.setJob(text);
        jobs.add(job);
    }

    public List<Job> getArrayList() {
        return jobs;
    }

    public ArrayList<String> getStringArrayList(){
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0;i < jobs.size();i++){
            result.add(jobs.get(i).getJob());
        }
        return result;
    }
}
