package com.mvp.mobile_art.Model.Responses;

import com.mvp.mobile_art.Model.Basic.MyTask;
import com.mvp.mobile_art.lib.models.Response;

/**
 * Created by jcla123ns on 27/07/17.
 */

public class MyTaskResponse extends Response {
    private MyTask myTask;

    public MyTask getMyTask() {
        return myTask;
    }

    public void setMyTask(MyTask myTask) {
        this.myTask = myTask;
    }
}
