package com.TA.MVP.appmobilemember.Model.Array;

import com.TA.MVP.appmobilemember.Model.Basic.Place;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zackzack on 11/07/2017.
 */

public class ArrayPlace {
    private List<Place> places;

    public List<Place> getPlaces() {
        return places;
    }

    public ArrayPlace() {
        places = new ArrayList<>();
        addPlace("NAD",0);
        addPlace("Sumatera Utara",0);
        addPlace("Sumatera Barat",0);
        addPlace("Riau",0);
        addPlace("Banda Aceh",1);
        addPlace("Medan",2);
        addPlace("Binjai",2);
        addPlace("Kab. Deli Serdang",2);
        addPlace("Kab. Langkat",2);
        addPlace("Kab. Asahan",2);

        addPlace("Padang",3);
        addPlace("Bukit Tinggi",3);
        addPlace("Pekanbaru",4);
    }
    public void addPlace(String name, Integer parent){
        Place place = new Place();
        place.setName(name);
        place.setParent(parent);
        places.add(place);
    }
}
