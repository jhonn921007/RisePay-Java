import java.io.*;
import java.net.*;
import java.util.*;
 
import java.util.HashMap;
import java.util.Map;

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
    String [] whitelist = {"TransType","NameOnCard", "CardNum", "ExpDate", "CVNum", "Amount", "InvNum", "Zip", "Street","City", "MagData","PNRef", "UserName", "Password"};
    String [] amountFields = {"Amount","TipAmt","TaxAmt"};
    
    public RisePay(String username, String password){
        this.username = username;
        this.password = password;
    }
    
    public void getGatewayUrl(String url){
        this.url = url;
    }
    
    public Map<String, Object> auth(Map<String, Object> data) throws Exception{
        data.put("TransType", "Auth");
        return prepareData(data);
    }
    
    public Map<String, Object> sale(Map<String, Object> data)  throws Exception{
        data.put("TransType", "Sale");
        return prepareData(data);
    }
    
    public Map<String, Object> prepareData(Map<String, Object> data) throws Exception{
        data.put("UserName", username);
        data.put("Password", password);
        return data;
    }
    public void sendPost(Map<String, Object> data) throws Exception{
        URL url = new URL(this.url);
  
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : data.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        byte[] postDataBytes = postData.toString().getBytes("UTF-8");

        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        conn.setRequestProperty("Content-Length", String.valueOf(postDataBytes.length));
        conn.setDoOutput(true);
        conn.getOutputStream().write(postDataBytes);

        Reader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
        for (int c; (c = in.read()) >= 0; System.out.print((char)c));
       
    }

    
}
