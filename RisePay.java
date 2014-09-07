
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

    
    public RisePay(String username, String password){
        this.username = username;
        this.password = password;
    }
    
    public void getGatewayUrl(String url){
        this.url = url;
    }
    
    public Map<String, Object> auth(Map<String, Object> data){
        data.put("TransType", "Auth");
        return prepareData(data);
    }
    
    public Map<String, Object> prepareData(Map<String, Object> data){
        data.put("UserName", username);
        data.put("Password", password);
        return post(data);
    }
    public Map<String, Object> post(Map<String, Object> data){
        return data;
    }

    
}
