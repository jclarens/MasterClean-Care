package com.mvp.mobile_art.Model.Basic;

import java.util.List;

/**
 * Created by Zackzack on 16/07/2017.
 */

public class StaticData {
    private List<Place> places;
    private List<Language> languages;
    private List<Job> jobs;
    private List<Waktu_Kerja> waktu_kerjas;
    private List<AdditionalInfo> additionalInfos;
    private List<Wallet> wallets;
    private List<MyTask> myTasks;

    public List<Place> getPlaces() {
        return places;
    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public List<Language> getLanguages() {
        return languages;
    }

    public void setLanguages(List<Language> languages) {
        this.languages = languages;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

    public List<Waktu_Kerja> getWaktu_kerjas() {
        return waktu_kerjas;
    }

    public void setWaktu_kerjas(List<Waktu_Kerja> waktu_kerjas) {
        this.waktu_kerjas = waktu_kerjas;
    }

    public List<AdditionalInfo> getAdditionalInfos() {
        return additionalInfos;
    }

    public void setAdditionalInfos(List<AdditionalInfo> additionalInfos) {
        this.additionalInfos = additionalInfos;
    }

    public List<Wallet> getWallets() {
        return wallets;
    }

    public void setWallets(List<Wallet> wallets) {
        this.wallets = wallets;
    }

    public List<MyTask> getMyTasks() {
        return myTasks;
    }

    public void setMyTasks(List<MyTask> myTasks) {
        this.myTasks = myTasks;
    }
}
