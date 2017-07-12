package com.TA.MVP.appmobilemember.Model.Array;

import com.TA.MVP.appmobilemember.Model.Basic.Language;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zackzack on 11/07/2017.
 */

public class ArrayLanguage {
    private List<Language> languages;

    public List<Language> getLanguages() {
        return languages;
    }

    public ArrayLanguage() {
        this.languages = new ArrayList<>();
        addLanguage(0, "Indonesia");
        addLanguage(0, "English");
    }
    public void addLanguage(Integer value, String text){
        Language language = new Language();
        language.setValue(value);
        language.setText(text);
        languages.add(language);
    }
}
