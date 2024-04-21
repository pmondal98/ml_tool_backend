package com.bits.ml_tool.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bits.ml_tool.dao.LuLanguageRepository;
import com.bits.ml_tool.dao.LuMessageRepository;
import com.bits.ml_tool.entities.LuLanguage;
import com.bits.ml_tool.entities.VendorTranslationMap;
import org.springframework.web.bind.annotation.RequestBody;

@CrossOrigin("*")
@RestController
public class TranslationController {

    @Autowired
    private LuMessageRepository luMessageRepository;

    @Autowired
    private LuLanguageRepository luLanguageRepository;

    @Autowired
    private CSVImporter csvImporter;

    @GetMapping(value = "/getLanguage")
    public List<LuLanguage> getLanguage() {

        // vendorTranslationList.forEach((VendorTranslationMap map) ->
        // System.out.println(map.getEnglishMessage()));

        List<LuLanguage> luLanguagelist = (List<LuLanguage>) luLanguageRepository.findAll();

        return luLanguagelist;
    }

    @GetMapping(value = "/getTranslation/{langId}")
    public List<VendorTranslationMap> getAllTranslationByLanguage(@PathVariable("langId") int langId) {
        Map<String, String> vendorTranslationMap = luMessageRepository.getAllTranslation(langId);
        LuLanguage luLanguage = luLanguageRepository.findByLanguageId(langId);
        List<VendorTranslationMap> vendorTranslationList = new ArrayList<>();
        int i = 0;
        for (var entry : vendorTranslationMap.entrySet()) {
            VendorTranslationMap map = new VendorTranslationMap();
            map.setEnglishMessage(entry.getKey());
            map.setTranslatedMessage(entry.getValue());
            map.setId(i++);
            map.setLang(luLanguage);
            vendorTranslationList.add(map);
        }
        // vendorTranslationList.forEach((VendorTranslationMap map) ->
        // System.out.println(map.getEnglishMessage()));

        return vendorTranslationList;
    }

    // @GetMapping(value = "/importTranslation/{updateRecords}")
    // public String importTranslation(@PathVariable("updateRecords") Boolean
    // updateRecords) {
    // csvImporter.importFromCSV("C:\\Users\\monda\\OneDrive\\Desktop\\ML_TOOL_Translation\\Import",
    // updateRecords);
    // return "SUCCESS";
    // }

    @GetMapping(value = "/importTranslation/{updateRecords}/{file}")
    public String importTranslation(@PathVariable("updateRecords") Boolean updateRecords,
            @PathVariable("file") File file) {
        System.out.println(file);
        int rows = (file.toString().split(",").length) / 3;
        List<List<String>> records = new ArrayList<List<String>>();
        int start = 0, end = 3;
        String[] arr = file.toString().split(",");
        for (int i = 0; i < rows; i++) {

            if (arr.length != 0) {
                String[] first3ElementsRow = Arrays.copyOfRange(arr, start, end);
                records.add(Arrays.asList(first3ElementsRow));
            }
            start = start + 3;
            end = end + 3;
        }
        csvImporter.importFromCSV(records, updateRecords);
        return "SUCCESS";
    }

}
