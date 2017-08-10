package com.TA.MVP.appmobilemember;

import com.TA.MVP.appmobilemember.Model.Basic.StaticData;
import com.TA.MVP.appmobilemember.Route.Repositories.AdditionalInfoRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.EmergencycallRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.JobRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.LanguageRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.MessageRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.MyTaskRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.OfferRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.OrderRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.PlaceRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.ReportRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.ReviewOrderRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.UserRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.WTRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.WalletRepo;
import com.TA.MVP.appmobilemember.Route.Repositories.WalletTransactionRepo;
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
    public StaticData globalStaticData;

    public StaticData getGlobalStaticData() {
        return globalStaticData;
    }

    public void setGlobalStaticData(StaticData globalStaticData) {
        this.globalStaticData = globalStaticData;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        globalStaticData = new StaticData();
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

        // Ini untuk mempermudah request jika butuh JWT token
        APIManager.addInterceptor(new SessionInterceptor());

        if(Settings.isUsingRetrofitAPI())
            APIManager.SetUpRetrofit();

        //Ini tuntuk register repository request (serives)
        APIManager.registerRepository(UserRepo.class);
        APIManager.registerRepository(OrderRepo.class);
        APIManager.registerRepository(OfferRepo.class);
        APIManager.registerRepository(MessageRepo.class);
        APIManager.registerRepository(WalletRepo.class);
        APIManager.registerRepository(WalletTransactionRepo.class);
        APIManager.registerRepository(PlaceRepo.class);
        APIManager.registerRepository(MyTaskRepo.class);
        APIManager.registerRepository(LanguageRepo.class);
        APIManager.registerRepository(JobRepo.class);
        APIManager.registerRepository(WTRepo.class);
        APIManager.registerRepository(ReportRepo.class);
        APIManager.registerRepository(AdditionalInfoRepo.class);
        APIManager.registerRepository(EmergencycallRepo.class);
        APIManager.registerRepository(ReviewOrderRepo.class);
    }
}
