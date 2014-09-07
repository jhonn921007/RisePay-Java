import java.io.*;
import java.net.*;
import java.util.*;
 
import java.util.HashMap;
import java.util.Map;
import org.javalite.http.Http;
import org.javalite.http.Post;

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
        post(data);
         
        return data;
    }
    public void post(Map<String, Object> data) throws Exception{
       
        StringBuilder postData = new StringBuilder();
        for (Map.Entry<String,Object> param : data.entrySet()) {
            if (postData.length() != 0) postData.append('&');
            postData.append(URLEncoder.encode(param.getKey(), "UTF-8"));
            postData.append('=');
            postData.append(URLEncoder.encode(String.valueOf(param.getValue()), "UTF-8"));
        }
        
       String content = String.valueOf(postData);
       Post post = Http.post(url, content).header("Content-Type", "application/x-www-form-urlencoded");
   
        System.out.println(post.text());

    }

    
}
