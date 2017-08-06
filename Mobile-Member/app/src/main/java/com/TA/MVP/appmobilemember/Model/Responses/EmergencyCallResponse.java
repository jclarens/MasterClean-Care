package com.TA.MVP.appmobilemember.Model.Responses;

import com.TA.MVP.appmobilemember.Model.Basic.Emergencycall;

/**
 * Created by Zackzack on 15/07/2017.
 */

public class EmergencyCallResponse {
    private Emergencycall data;

    public Emergencycall getEmergencycall() {
        return data;
    }

    public void setEmergencycall(Emergencycall emergencycall) {
        this.data = emergencycall;
    }
}
