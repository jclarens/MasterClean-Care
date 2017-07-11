package com.TA.MVP.appmobilemember.Model.Responses;

import com.TA.MVP.appmobilemember.Model.Basic.Bahasa;
import com.TA.MVP.appmobilemember.lib.models.Response;

/**
 * Created by Zackzack on 11/07/2017.
 */

public class BahasaResponse extends Response {
    private Bahasa bahasa;

    public Bahasa getBahasa() {
        return bahasa;
    }

    public void setBahasa(Bahasa bahasa) {
        this.bahasa = bahasa;
    }
}
