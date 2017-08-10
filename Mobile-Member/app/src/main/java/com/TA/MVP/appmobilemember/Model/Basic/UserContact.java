package com.TA.MVP.appmobilemember.Model.Basic;

/**
 * Created by Zackzack on 19/07/2017.
 */

public class UserContact {
    private Integer id;
    private User user;
    private String phone;
    private String address;
    private String location;
    private Integer city;
    private String emergency_numb;

    public Integer getId() {
        return id;
    }

    public User getUser_id() {
        return user;
    }

    public void setUser_id(User user) {
        this.user = user;
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
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public String getEmergency_numb() {
        return emergency_numb;
    }

    public void setEmergency_numb(String emergency_numb) {
        this.emergency_numb = emergency_numb;
    }
}
