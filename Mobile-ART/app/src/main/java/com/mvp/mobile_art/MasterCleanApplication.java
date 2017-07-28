package com.mvp.mobile_art;


import android.app.Application;

import com.mvp.mobile_art.Model.Basic.StaticData;
import com.mvp.mobile_art.Route.Repositories.AdditionalInfoRepo;
import com.mvp.mobile_art.Route.Repositories.EmergencycallRepo;
import com.mvp.mobile_art.Route.Repositories.JobRepo;
import com.mvp.mobile_art.Route.Repositories.LanguageRepo;
import com.mvp.mobile_art.Route.Repositories.MessageRepo;
import com.mvp.mobile_art.Route.Repositories.MyTaskRepo;
import com.mvp.mobile_art.Route.Repositories.OrderRepo;
import com.mvp.mobile_art.Route.Repositories.PlaceRepo;
import com.mvp.mobile_art.Route.Repositories.UserRepo;
import com.mvp.mobile_art.Route.Repositories.WTRepo;
import com.mvp.mobile_art.Route.Repositories.WalletRepo;
import com.mvp.mobile_art.Route.Repositories.WalletTransactionRepo;
import com.mvp.mobile_art.lib.api.APIManager;
import com.mvp.mobile_art.lib.api.SessionInterceptor;
import com.mvp.mobile_art.lib.database.SharedPref;
import com.mvp.mobile_art.lib.utils.FileUtils;
import com.mvp.mobile_art.lib.utils.GsonUtils;
import com.mvp.mobile_art.lib.utils.Settings;


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
        Settings.getInstance().configureAppSetting(
                GsonUtils.getObjectFromJson(
                        FileUtils.loadSettingsJsonFile(getApplicationContext()),
                        Settings.class
                ));

        if(Settings.isUsingSharedPreference())
            SharedPref.getInstance().SetUpSharedPreference(getApplicationContext());

        APIManager.addInterceptor(new SessionInterceptor());
        if(Settings.isUsingRetrofitAPI())
            APIManager.SetUpRetrofit();

        APIManager.registerRepository(UserRepo.class);
        APIManager.registerRepository(OrderRepo.class);
        APIManager.registerRepository(MessageRepo.class);
        APIManager.registerRepository(WalletRepo.class);
        APIManager.registerRepository(WalletTransactionRepo.class);
        APIManager.registerRepository(PlaceRepo.class);
        APIManager.registerRepository(MyTaskRepo.class);
        APIManager.registerRepository(LanguageRepo.class);
        APIManager.registerRepository(JobRepo.class);
        APIManager.registerRepository(WTRepo.class);
        APIManager.registerRepository(AdditionalInfoRepo.class);
        APIManager.registerRepository(EmergencycallRepo.class);
    }
}
