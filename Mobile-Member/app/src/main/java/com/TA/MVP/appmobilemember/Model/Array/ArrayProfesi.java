package com.TA.MVP.appmobilemember.Model.Array;

import com.TA.MVP.appmobilemember.Model.Basic.Job;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zackzack on 12/06/2017.
 */

public class ArrayProfesi {
    private List<Job> jobs;
    public ArrayProfesi() {
        jobs = new ArrayList<>();
    }
    public void addProfesi(String text){
        Job job = new Job();
        job.setText(text);
        jobs.add(job);
    }

    public List<Job> getArrayList() {
        return jobs;
    }

    public ArrayList<String> getStringArrayList(){
        ArrayList<String> result = new ArrayList<>();
        for (int i = 0;i < jobs.size();i++){
            result.add(jobs.get(i).getText());
        }
        return result;
    }
}
