package com.TA.MVP.appmobilemember.Model.Responses;

import com.TA.MVP.appmobilemember.Model.Basic.MyMessage;
import com.TA.MVP.appmobilemember.lib.models.Response;

/**
 * Created by Zackzack on 15/07/2017.
 */

public class MyMessageResponse extends Response{
    private MyMessage myMessage;

    public MyMessage getMyMessage() {
        return myMessage;
    }

    public void setMyMessage(MyMessage myMessage) {
        this.myMessage = myMessage;
    }
}
