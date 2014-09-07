
import java.util.HashMap;
import java.util.Iterator;
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
    private final String url = "https://gateway1.risepay.com/ws/transact.asmx/ProcessCreditCard?";
    
    public void auth(Map<String, Object> data){
        data.put("TransType", "Auth");
        System.out.println(data);
    }
    
    public void prepareData(){
        
    }
    public void post(){
        
    }
public void test(){
    Map opt = new HashMap();
    opt.put("NameOnCard","Kamel");
    opt.put("CardNum", "5149612222222229");
    opt.put("ExpDate", "1214");
    opt.put("Amount", "10");
    opt.put("CVNum", "734");
    opt.put("InvNum", "ABD42");
    opt.put("Zip", "36124");
    opt.put("Street", "Gran Vio 25");
    sale(opt);
    }     
    
   public void sale(Map opt){ 
        Map form = new HashMap();
        form.put("UserName",username);
        form.put("Password",password);
        form.put("TransType","SALE");
        form.put("NameOnCard", opt.get(0).toString());
        form.put("CardNum", opt.get(1).toString());
        form.put("ExpDate", opt.get(2).toString());
        form.put("Amount", opt.get(3).toString());
        form.put("MagData","");
        form.put("PNRef","");
        form.put("ExtData", "");     
        form.put("CVNum",opt.get(4).toString());
        form.put("InvNum",opt.get(5).toString());
        form.put("Zip",opt.get(6).toString());
        form.put("Street",opt.get(7).toString());
        
        for (Iterator it = form.keySet().iterator(); it.hasNext();) {
            Object key = it.next();
            System.out.println("key: " + key + "|  value: " + form.get(key));
        }
    
        
   }}
