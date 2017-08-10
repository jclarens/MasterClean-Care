package com.TA.MVP.appmobilemember.Model.Responses;

import com.TA.MVP.appmobilemember.Model.Basic.Report;
import com.TA.MVP.appmobilemember.lib.models.Response;

/**
 * Created by jcla123ns on 10/08/17.
 */

public class ReportResponse extends Response {
    private Report report;

    public Report getReport() {
        return report;
    }

    public void setReport(Report report) {
        this.report = report;
    }
}
