package com.mvp.mobile_art.Model.Responses;


import com.mvp.mobile_art.Model.Basic.Language;
import com.mvp.mobile_art.lib.models.Response;

/**
 * Created by Zackzack on 11/07/2017.
 */

public class LanguageResponse extends Response {
    private Language Language;

    public Language getLanguage() {
        return Language;
    }

    public void setLanguage(Language Language) {
        this.Language = Language;
    }
}
