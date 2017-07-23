package com.mvp.mobile_art.Model.Responses;


import com.mvp.mobile_art.Model.Basic.Job;
import com.mvp.mobile_art.lib.models.Response;

/**
 * Created by Zackzack on 11/07/2017.
 */

public class JobResponse extends Response {
    private Job job;

    public Job getProfesi() {
        return job;
    }

    public void setProfesi(Job job) {
        this.job = job;
    }
}
