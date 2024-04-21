package com.bits.ml_tool.entities;

public class VendorTranslationMap {
    int id;
    String englishMessage;
    String translatedMessage;
    LuLanguage lang;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEnglishMessage() {
        return englishMessage;
    }

    public void setEnglishMessage(String englishMessage) {
        this.englishMessage = englishMessage;
    }

    public String getTranslatedMessage() {
        return translatedMessage;
    }

    public void setTranslatedMessage(String translatedMessage) {
        this.translatedMessage = translatedMessage;
    }

    public LuLanguage getLang() {
        return lang;
    }

    public void setLang(LuLanguage lang) {
        this.lang = lang;
    }

}
