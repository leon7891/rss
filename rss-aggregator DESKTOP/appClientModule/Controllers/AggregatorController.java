package Controllers;
import  java.awt.event.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import  javax.swing.*;

import Models.ActivatedFluxsModel;
import Views.AggregatorView;

public class AggregatorController   implements  ActionListener
{
    protected AggregatorView       view;
    
    public  static  void    main(String args[])
    {    
        AggregatorController controller = new AggregatorController();
        controller.instantiateWindow();
        controller.callFirstView();
    }
    
    public String contactAPI (String endpoint, String method){
    	return contactAPI(endpoint, method, false);
    }
    
    public String contactAPI(String endpoint, String method, Boolean callAsAdmin){
    	
		try {
			URL url;
			url = new URL("http://shendai.rip/rss/rest/" + endpoint);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod(method);
			conn.setRequestProperty("Accept", "application/json");
			if (callAsAdmin){
				conn.setRequestProperty("login", "admin");
				conn.setRequestProperty("password", "123456");	
			} else {
				conn.setRequestProperty("login", "maxime");
				conn.setRequestProperty("password", "maxime");	
			}

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));
			
			String output = br.readLine();
			
			conn.disconnect();

			return output;
		} catch (MalformedURLException e) {

    		e.printStackTrace();

    	  } catch (IOException e) {

    		e.printStackTrace();

    	  }
		
    	return null;
    }
    
    public void instantiateWindow(){
    	this.view = new AggregatorView();
    }
    
    public void callFirstView(){
    	//new ActivatedFluxsController(view);
    	new NewsController(view);
    }
    
    public void actionPerformed(ActionEvent e)
    {
        String  s=((JButton) e.getSource()).getText();
    }
    
}
