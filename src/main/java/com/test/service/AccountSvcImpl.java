package com.test.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.data.source.FileDataSource;
import com.test.model.Account;
import com.test.utils.Constant;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class AccountSvcImpl implements AccountSvc {

    FileDataSource fileDataSource = new FileDataSource();

    @Override
    public JSONArray getAll() {

        JSONArray jsonArray  = new JSONArray();
        try {
            jsonArray = fileDataSource.readObjects();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonArray;
    }

    @Override
    public String get(int id) {
        return fileDataSource.checkRecord(id);
    }

    @Override
    public String create(String accountData) {

        String message = "record created successfully";
        com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();

        try{
            Account account = objectMapper.readValue(accountData, Account.class);

            String strRecord = fileDataSource.checkRecord(account.getId());
            if(strRecord != null && !strRecord.trim().equals("") && strRecord != Constant.NO_RECORD_MESAAGE){
                message = "Record already exits for this id";
                return message;
            }
            org.json.simple.JSONObject jsonObject = new org.json.simple.JSONObject();
            jsonObject.put("id", account.getId());
            jsonObject.put("customerName", account.getCustomerName());
            jsonObject.put("currency", account.getCurrency());
            jsonObject.put("amount", account.getAmount());
            org.json.simple.JSONObject tempObject = new org.json.simple.JSONObject();
            tempObject.put("account", jsonObject);

            org.json.JSONArray allCustomerAccounts = fileDataSource.readObjects();

            String status = fileDataSource.writeObject(tempObject, allCustomerAccounts);

            Optional<String> opt = Optional.of("success");
            opt.ifPresent(name -> System.out.println("Record created successfully"));

        }catch (IOException e){
            e.printStackTrace();
        }catch (Exception e) {
            e.printStackTrace();
        }

        return message;
    }

    @Override
    public String partialUpdate(String requestData) {

        String message = "";
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Account account = objectMapper.readValue(requestData, Account.class);
            if(account.getId() == 0){
                message = "Customer account id is required";
                return message;
            }
            String record = fileDataSource.checkRecord(account.getId());
            if(record == null || record.trim().equals("")){
                message = Constant.NO_RECORD_MESAAGE;
            }
            JSONArray allRecords = fileDataSource.readObjects();
            JSONArray updatedRecords = removeRecord(allRecords, account.getId());

            org.json.simple.JSONObject jsonObject = new org.json.simple.JSONObject();
            jsonObject.put("id", account.getId());
            jsonObject.put("customerName", account.getCustomerName());
            jsonObject.put("currency", account.getCurrency());
            jsonObject.put("amount", account.getAmount());
            org.json.simple.JSONObject tempObject = new org.json.simple.JSONObject();
            tempObject.put("account", jsonObject);
            String status = fileDataSource.writeObject(tempObject, allRecords);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public JSONArray removeRecord(JSONArray allRecords, int id ){

        JSONArray jsonArray = null;
        try {
            int position = 0;
            List<String> strList = new ArrayList<String>();
            for (int j = 0; j <= allRecords.length(); j++) {
                JSONObject jObject = (JSONObject) allRecords.get(j);
                JSONObject jsonObjectNew = (JSONObject)jObject.get("account");
                int objectId = jsonObjectNew.getInt("id");
                if(objectId == id){
                    strList.add(jsonObjectNew.toString());
                    position = strList.size();

                }else{
                    strList.add(jsonObjectNew.toString());
                }

            }
            if(strList.size() >= position){
                strList.remove(position);
            }
            jsonArray = new JSONArray(strList);
            return jsonArray;


        }catch (JSONException je){
            je.printStackTrace();
        }

        return jsonArray;
    }

}
