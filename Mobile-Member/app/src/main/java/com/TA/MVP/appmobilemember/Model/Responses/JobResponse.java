package com.TA.MVP.appmobilemember.Model.Responses;

import com.TA.MVP.appmobilemember.Model.Basic.Job;
import com.TA.MVP.appmobilemember.lib.models.Response;

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
