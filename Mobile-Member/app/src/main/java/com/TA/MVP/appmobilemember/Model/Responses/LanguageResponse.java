package com.TA.MVP.appmobilemember.Model.Responses;

import com.TA.MVP.appmobilemember.Model.Basic.Language;
import com.TA.MVP.appmobilemember.lib.models.Response;

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
