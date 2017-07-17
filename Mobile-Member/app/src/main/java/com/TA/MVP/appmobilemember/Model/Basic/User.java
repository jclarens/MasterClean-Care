package com.TA.MVP.appmobilemember.Model.Basic;

import java.util.Date;

/**
 * Created by Zackzack on 09/07/2017.
 */

public class User {
    private Integer id;
    private String name;
    private String email;
    private String password;
    private Integer gender;
    private String born_place;
    private Date born_date;
    private String phone;
    private Integer province;
    private Integer city;
    private String address;
    private String location;
    private Integer religion;
    private String race;
    private Integer user_type;
    private String profile_img_name;
    private String profile_img_path;
    private Integer status;

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public String getBorn_place() {
        return born_place;
    }

    public void setBorn_place(String born_place) {
        this.born_place = born_place;
    }

    public Date getBorn_date() {
        return born_date;
    }

    public void setBorn_date(Date born_date) {
        this.born_date = born_date;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
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

    public Integer getReligion() {
        return religion;
    }

    public void setReligion(Integer religion) {
        this.religion = religion;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public Integer getUser_type() {
        return user_type;
    }

    public void setUser_type(Integer user_type) {
        this.user_type = user_type;
    }

    public String getProfile_img_name() {
        return profile_img_name;
    }

    public void setProfile_img_name(String profile_img_name) {
        this.profile_img_name = profile_img_name;
    }

    public String getProfile_img_path() {
        return profile_img_path;
    }

    public void setProfile_img_path(String profile_img_path) {
        this.profile_img_path = profile_img_path;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
