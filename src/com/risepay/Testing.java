package com.risepay;

import java.io.*;
import java.net.*;
import java.util.*;
import javax.net.ssl.HttpsURLConnection;
import org.javalite.http.Get;
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
public class Testing {
    
    public void test() throws IOException{
        String content = "UserName=jhonndev&Password=U0H464z4";
Post post = Http.post("https://gateway1.risepay.com/ws/transact.asmx/ProcessCreditCard?", content).header("Content-Type", "application/x-www-form-urlencoded");
System.out.println(post.text());
System.out.println(post.headers());
System.out.println(post.responseCode());
    }
}
