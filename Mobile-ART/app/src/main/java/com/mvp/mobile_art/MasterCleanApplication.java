package com.TA.MVP.appmobilemember;

import com.TA.MVP.appmobilemember.Model.Basic.StaticData;
import com.TA.MVP.appmobilemember.Route.Repositories.AdditionalInfoRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.JobRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.LanguageRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.PlaceRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.UserRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.WTRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.WalletRepo;
import com.TA.MVP.appmobilemember.lib.api.APIManager;
import com.TA.MVP.appmobilemember.lib.api.SessionInterceptor;
import com.TA.MVP.appmobilemember.lib.database.SharedPref;
import com.TA.MVP.appmobilemember.lib.utils.FileUtils;
import com.TA.MVP.appmobilemember.lib.utils.GsonUtils;
import com.TA.MVP.appmobilemember.lib.utils.Settings;

import android.app.Application;


/**
 * Created by Jay Clarens on 7/8/2017.
 *
 * pertama
 */

public class MasterCleanApplication extends Application {
    public StaticData globalStaticData = new StaticData();

    public StaticData getGlobalStaticData() {
        return globalStaticData;
    }

    public void setGlobalStaticData(StaticData globalStaticData) {
        this.globalStaticData = globalStaticData;
    }
    @Override
    public void onCreate() {
        super.onCreate();
//        Log.d("tmp","onCreate:" +
//                FileUtils.loadSettingsJsonFile(getApplicationContext())
//        );
        Settings.getInstance().configureAppSetting(
                GsonUtils.getObjectFromJson(
                        FileUtils.loadSettingsJsonFile(getApplicationContext()),
                        Settings.class
                ));

        if(Settings.isUsingSharedPreference())
            SharedPref.getInstance().SetUpSharedPreference(getApplicationContext());

        // Untuk simpan token jika mau pake JWT
//        SharedPref.save(SharedPref.ACCESS_TOKEN, token);
        // Untuk ambe token
//        SharedPref.getValueString(SharedPref.ACCESS_TOKEN);

        // Ini untuk mempermudah request jika butuh JWT token
        APIManager.addInterceptor(new SessionInterceptor());
        if(Settings.isUsingRetrofitAPI())
            APIManager.SetUpRetrofit();

        //Ini tuntuk register repository request (serives)
        APIManager.registerRepository(UserRepo.class);
        APIManager.registerRepository(WalletRepo.class);
        APIManager.registerRepository(PlaceRepo.class);
        APIManager.registerRepository(LanguageRepo.class);
        APIManager.registerRepository(JobRepo.class);
        APIManager.registerRepository(WTRepo.class);
        APIManager.registerRepository(AdditionalInfoRepo.class);
    }
}
