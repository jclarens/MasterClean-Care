package com.mvp.mobile_art.Model.Basic;

/**
 * Created by Zackzack on 09/07/2017.
 */

public class Job {
    private String job;

    public String getText() {
        return job;
    }

    public void setText(String text) {
        this.job = text;
    }

    public String toString(){
        return (job);
    }
}
