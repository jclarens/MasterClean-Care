package com.TA.MVP.appmobilemember.Model.Basic;

/**
 * Created by Zackzack on 20/07/2017.
 */

public class OfferContact {
    private Integer id;
    private Offer offer;
    private String phone;
    private String address;
    private String location;
    private Integer City;

    public Integer getId() {
        return id;
    }

    public Offer getOffer() {
        return offer;
    }

    public void setOffer(Offer offer) {
        this.offer = offer;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getCity() {
        return City;
    }

    public void setCity(Integer city) {
        City = city;
    }
}
