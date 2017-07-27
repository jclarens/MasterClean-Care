package com.TA.MVP.appmobilemember.Model.Responses;

import com.TA.MVP.appmobilemember.Model.Basic.MyTask;
import com.TA.MVP.appmobilemember.lib.models.Response;

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
