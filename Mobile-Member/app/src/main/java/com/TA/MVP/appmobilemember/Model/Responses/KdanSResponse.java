package com.TA.MVP.appmobilemember.Model.Responses;

import com.TA.MVP.appmobilemember.Model.Basic.KdanS;
import com.TA.MVP.appmobilemember.lib.models.Response;

/**
 * Created by jcla123ns on 11/08/17.
 */

public class KdanSResponse extends Response{
    private KdanS kdanS;

    public KdanS getKdanS() {
        return kdanS;
    }

    public void setKdanS(KdanS kdanS) {
        this.kdanS = kdanS;
    }
}
