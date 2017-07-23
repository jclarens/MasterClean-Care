package com.TA.MVP.appmobilemember.Model.Basic;

import java.util.Date;
import java.util.List;

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
    private Integer religion;
    private String race;
    private Integer user_type;
    private String profile_img_name;
    private String profile_img_path;
    private Integer status;
    private List<UserContact> contact;
    private List<UserAdditionalInfo> user_additional_info;
    private List<UserDocument> user_document;
    private List<UserWallet> user_wallet;
    private List<UserLanguage> user_language;
    private List<UserJob> user_job;
    private List<UserWorkTime> user_work_time;
    private float rate;

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

    public List<UserContact> getContact() {
        return contact;
    }

    public void setContact(List<UserContact> contact) {
        this.contact = contact;
    }

    public List<UserAdditionalInfo> getUser_additional_info() {
        return user_additional_info;
    }

    public void setUser_additional_info(List<UserAdditionalInfo> user_additional_info) {
        this.user_additional_info = user_additional_info;
    }

    public List<UserDocument> getUser_document() {
        return user_document;
    }

    public void setUser_document(List<UserDocument> user_document) {
        this.user_document = user_document;
    }

    public List<UserWallet> getUser_wallet() {
        return user_wallet;
    }

    public void setUser_wallet(List<UserWallet> user_wallet) {
        this.user_wallet = user_wallet;
    }

    public List<UserLanguage> getUser_language() {
        return user_language;
    }

    public void setUser_language(List<UserLanguage> user_language) {
        this.user_language = user_language;
    }

    public List<UserJob> getUser_job() {
        return user_job;
    }

    public void setUser_job(List<UserJob> user_job) {
        this.user_job = user_job;
    }

    public List<UserWorkTime> getUser_work_time() {
        return user_work_time;
    }

    public void setUser_work_time(List<UserWorkTime> user_work_time) {
        this.user_work_time = user_work_time;
    }

    public float getRate() {
        return rate;
    }

    public void setRate(float rate) {
        this.rate = rate;
    }
}
