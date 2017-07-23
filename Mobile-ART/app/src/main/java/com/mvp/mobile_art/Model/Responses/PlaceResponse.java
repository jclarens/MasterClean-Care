package com.mvp.mobile_art.Model.Responses;

import com.mvp.mobile_art.Model.Basic.Place;
import com.mvp.mobile_art.lib.models.Response;

/**
 * Created by Zackzack on 11/07/2017.
 */

public class PlaceResponse extends Response {
    private Place place;

    public Place getPlace() {
        return place;
    }

    public void setPlace(Place place) {
        this.place = place;
    }
}
