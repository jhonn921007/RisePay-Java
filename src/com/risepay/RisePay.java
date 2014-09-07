package com.risepay;

import java.io.*;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.net.*;
import java.util.*;
 
import java.util.HashMap;
import java.util.Map;
import org.javalite.http.Http;
import org.javalite.http.Post;
import org.json.JSONObject;
import org.json.XML;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jhonn
 */
public class RisePay {
    
    private String username;
    private String password;
    private String url = "https://gateway1.risepay.com/ws/transact.asmx/ProcessCreditCard?";
    private String [] whitelist = {"TransType", "NameOnCard", "ExtData", "CardNum", "ExpDate", "CVNum", "Amount", "InvNum", "Zip", "Street","City", "MagData","PNRef", "UserName", "Password"};
    private String [] amountFields = {"Amount","TipAmt","TaxAmt"};
    
    public RisePay(String username, String password){
        this.username = username;
        this.password = password;
    }
    
    public void getGatewayUrl(String url){
        this.url = url;
    }
    
    public String auth(Map<String, Object> data) throws Exception{
        data.put("TransType", "Auth");
        return prepareData(data);
    }
    
    public String sale(Map<String, Object> data)  throws Exception{
        data.put("TransType", "Sale");
        return prepareData(data);
    }
    
    private String prepareData(Map<String, Object> data) throws Exception{
        data.put("UserName", username);
        data.put("Password", password);
        data.put("ExtData", "");
        
        // Construct ExtData
        String next = "";
        for(Map.Entry<String,Object> param : data.entrySet()){
            if(!inArray(String.valueOf(param.getKey()), whitelist)){
                 next += "<"+param.getKey()+">"+param.getValue()+"</"+param.getKey()+">";               
                 data.put("ExtData", next); 
                 System.out.println(param.getKey());
                 //data.remove(param.getKey());//Tira error al eliminar valor 
            }
        }
         
        // fix amounts
        int n = 0;
        for(Map.Entry<String,Object> param : data.entrySet()){
            if(inArray(String.valueOf(param.getKey()), amountFields)){
                n = (int)(double)parseAmount((int) param.getValue() );
                System.out.println("n:" + n);
                 data.put(param.getKey(), n); 
            }
        }
        
        // set defaults fields
        for(String w: whitelist){
            if(data.get(w)==null){
                data.put(w, "");
            }  
        }
        System.out.println(data);
        
         
        return post(data);
    }
    private String post(Map<String, Object> data) throws Exception{
       
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : data.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        
       String content = String.valueOf(postData);
       Post post = Http.post(url, content).header("Content-Type", "application/x-www-form-urlencoded");
   
        
        return XMLtoJSON(post.text());
    }
    
  private boolean inArray(String needle, String[] haystack) {    
    for (int i = 0; i < haystack.length; i++) {
        if (haystack[i] == needle) {
            return true;
        } 
    } 
    return false;
        } 
  
private String XMLtoJSON(String xml) {
    JSONObject jsonObj = XML.toJSONObject(xml);
    String json = jsonObj.toString();
    return json;
}

private double parseAmount(int amount){
      String val = amount+"";
      BigDecimal big = new BigDecimal(val);
      big = big.setScale(2, RoundingMode.HALF_UP);
      Double amt = Double.parseDouble(big.toString());
      System.out.println(amt);
    return amt;
}
    
}
