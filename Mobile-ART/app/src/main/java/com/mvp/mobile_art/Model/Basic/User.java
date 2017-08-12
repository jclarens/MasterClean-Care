package com.mvp.mobile_art.Model.Basic;

import java.util.Date;
import java.util.List;

/**
 * Created by Zackzack on 09/07/2017.
 */

public class User {
    private Integer id;
    private String name;
    private String email;
    private Integer gender;
    private String born_place;
    private Date born_date;
    private Integer religion;
    private String race;
    private Integer role_id;
    private String description;
    private String avatar;
    private Integer status;
    private UserContact contact;
    private UserWallet user_wallet;
    private List<UserAdditionalInfo> user_additional_info;
    private List<UserDocument> user_document;
    private List<UserLanguage> user_language;
    private List<UserJob> user_job;
    private List<UserWorkTime> user_work_time;
    private float rate;
    private Integer activation;

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

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public UserContact getContact() {
        return contact;
    }

    public void setContact(UserContact contact) {
        this.contact = contact;
    }

    public UserWallet getUser_wallet() {
        return user_wallet;
    }

    public void setUser_wallet(UserWallet user_wallet) {
        this.user_wallet = user_wallet;
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

    public Integer getActivation() {
        return activation;
    }

    public void setActivation(Integer activation) {
        this.activation = activation;
    }
}