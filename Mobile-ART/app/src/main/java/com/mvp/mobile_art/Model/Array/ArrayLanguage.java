package com.mvp.mobile_art.Model.Array;

import com.mvp.mobile_art.Model.Basic.Language;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zackzack on 11/07/2017.
 */

public class ArrayLanguage {
    private List<Language> languages = new ArrayList<>();

    public List<Language> getLanguages() {
        return languages;
    }

    public ArrayLanguage() {
        addLanguage("Indonesia");
        addLanguage("English");
    }
    public void addLanguage(String text){
        Language language = new Language();
        language.setLanguage(text);
        languages.add(language);
    }
}
