package com.TA.MVP.appmobilemember.Model.Responses;

import com.TA.MVP.appmobilemember.Model.Basic.Waktu_Kerja;
import com.TA.MVP.appmobilemember.lib.models.Response;

/**
 * Created by Zackzack on 11/07/2017.
 */

public class WTResponse extends Response {
    private Waktu_Kerja waktu_kerja;

    public Waktu_Kerja getWaktu_kerja() {
        return waktu_kerja;
    }

    public void setWaktu_kerja(Waktu_Kerja waktu_kerja) {
        this.waktu_kerja = waktu_kerja;
    }
}
