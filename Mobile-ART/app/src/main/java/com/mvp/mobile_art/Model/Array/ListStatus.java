package com.mvp.mobile_art.Model.Array;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jcla123ns on 22/07/17.
 */

public class ListStatus {
    private List<String> status;
    public ListStatus(){
        status = new ArrayList<>();
        status.add("Inactive");
        status.add("Active");
    }

    public List<String> getStatus() {
        return status;
    }
}
