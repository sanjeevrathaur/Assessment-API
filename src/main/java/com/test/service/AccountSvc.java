package com.test.service;


import org.json.JSONArray;
import org.json.JSONObject;

public interface AccountSvc {

    public JSONArray getAll();

    public String get(int id);

    public String create(String requestData);

    public String partialUpdate(String requestData);


}
