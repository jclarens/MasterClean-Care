package com.mvp.mobile_art.Model.Responses;

import com.mvp.mobile_art.Model.Basic.Report;
import com.mvp.mobile_art.lib.models.Response;

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
