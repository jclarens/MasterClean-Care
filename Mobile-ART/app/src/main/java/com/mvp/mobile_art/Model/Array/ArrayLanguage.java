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
        addLanguage("Indonesia");
        addLanguage("English");
    }
    public void addLanguage(String text){
        Language language = new Language();
        language.setText(text);
        languages.add(language);
    }
}
