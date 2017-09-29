package com.test.data.source;

import com.test.utils.Constant;
import jdk.nashorn.internal.parser.JSONParser;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;

/**
 * This class is used to habdle all file resources related functionality
 * @author Sanjeev
 *
 * */

public class FileDataSource {

    private File fileAsDataBase = null;

    com.fasterxml.jackson.databind.ObjectMapper objectMapper = new com.fasterxml.jackson.databind.ObjectMapper();

    public File openFile() throws Exception{

       File file = new File(Constant.DATA_SOURCE_FILE_LOCATION);

       if(!file.exists()){
           throw new Exception("The database file at path " + Constant.DATA_SOURCE_FILE_LOCATION + " does not exists.");
       }

        return file;
    }

    public void closeFile(){
        if(null != fileAsDataBase){
            fileAsDataBase = null;
        }
    }


    public synchronized String writeObject(org.json.simple.JSONObject tempObject, JSONArray jsonArray) throws Exception{

        String message = "";

        if(jsonArray == null){
            jsonArray = new org.json.JSONArray();
        }
        if(jsonArray == null){
            jsonArray = new org.json.JSONArray();
        }

        jsonArray.put(tempObject);

        try (FileWriter file = new FileWriter(Constant.DATA_SOURCE_FILE_LOCATION)) {
            file.write(jsonArray.toString());
            file.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }

        return "success";
    }

    public synchronized /*File*/ JSONArray readObjects() throws Exception{


        // convert dataJSONAsStr into JSONArray
        // iterate over array to get a record in basis of getID
        //return the Object

        org.json.JSONArray employeeList = null;
        org.json.simple.parser.JSONParser jsonParser = new org.json.simple.parser.JSONParser();
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("database.json").getFile());

        try
        {
            FileInputStream is = new FileInputStream(file);
            is = new FileInputStream(file);
            byte dataBuffer[] = new byte[is.available()];
            is.read(dataBuffer);
            is.close();
            String dataJSONAsStr = new String(dataBuffer);
            System.out.println("json data str : "+ dataJSONAsStr);


            employeeList = new org.json.JSONArray(dataJSONAsStr);
            System.out.println("jArray : "+employeeList);

            System.out.println(employeeList);

            System.out.println("Size of list is : "+employeeList.length());

            //Iterate over employee array
            //employeeList.forEach( emp -> parseEmployeeObject( (JSONObject) emp ) );

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } /*catch (ParseException e) {
            e.printStackTrace();
        } */catch (JSONException e) {
            e.printStackTrace();
        }
        return employeeList;

        /*return file;*/
    }



    public int getCount() throws Exception{

        File file = openFile();
        FileInputStream inStream = new FileInputStream(file);
        byte dataBuffer[] = new byte[inStream.available()];
        inStream.read(dataBuffer);
        String dataJSONAsStr = new String(dataBuffer);
        int count = 0;
        JSONArray allRecords = new JSONArray(new org.json.simple.parser.JSONParser().parse(dataJSONAsStr));
        for (int i=0; i<allRecords.length(); i++) {
            String item = (String)allRecords.get(i);
            org.json.simple.JSONObject json = (org.json.simple.JSONObject) new org.json.simple.parser.JSONParser().parse(item);
            String name = json.get("customerName").toString();
            System.out.println("name : "+ name);
            String amount = json.get("amount").toString();
            count++;
        }

        // convert dataJSONAsStr into JSONArray
        // iterate over array to get a record in basis of getID
        //return the Object

        return count;
    }

    public String checkRecord(int id){

        String result = Constant.NO_RECORD_MESAAGE;

        try{
            org.json.JSONArray allCustomerAccounts = readObjects();
            for (int i=0; i<=allCustomerAccounts.length(); i++){
                JSONObject jsonObject = (JSONObject) allCustomerAccounts.get(i);
                JSONObject jsonObjectNew = (JSONObject)jsonObject.get("account");
                int objectId = jsonObjectNew.getInt("id");
                if(objectId == id){
                    return jsonObject.toString();
                }

            }

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }

    public String updateRecordPartialy(JSONObject jsonObject){

    return  "";

    }

    private void parseEmployeeObject(org.json.simple.JSONObject employee)
    {
        //Get employee object within list
        org.json.simple.JSONObject employeeObject = (org.json.simple.JSONObject) employee.get("account");

        //Get employee first name
        String firstName = (String) employeeObject.get("customerName");
        System.out.println(firstName);

        //Get employee last name
        String lastName = (String) employeeObject.get("currency");
        System.out.println(lastName);

        //Get employee website name
        Double website = (Double) employeeObject.get("amount");
        System.out.println(website);
    }

}
