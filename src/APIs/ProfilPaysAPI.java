/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APIs;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author hero
 */
public class ProfilPaysAPI {

    private static ProfilPaysAPI paysAPI;
    private JSONArray items;
    private JSONObject item;
    private static final String URL = "https://battuta.medunes.net/api/country/all/?key=c16aba7bbd8682ff9dfebea23b8113d1";    
    private URL url ;
    private HttpURLConnection httpURLConnection ;
    private BufferedReader in;
    private StringBuffer response;
    private String inputLine;
    
    private ProfilPaysAPI(){}
    
    public static ProfilPaysAPI getInstance()
    {
        if(paysAPI == null) paysAPI = new ProfilPaysAPI();
        return paysAPI;
    }
    
    public List<String> fetchResult() {
        List<String> resultList = new ArrayList();
        try {
            url = new URL(URL);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            items = new JSONArray(response.toString());
            for(int i=0;i<items.length();i++)
            {
                resultList.add(items.getJSONObject(i).getString("name")+"-"+items.getJSONObject(i).getString("code"));
            }
        } catch (IOException | JSONException ex) {
            Logger.getLogger(ProfilPaysAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultList;
    }

    public String getPaysName(String cod) {
        String URL2 = "https://battuta.medunes.net/api/country/code/"+cod+"/?key=c16aba7bbd8682ff9dfebea23b8113d1";
        String name = "";
        try {
            url = new URL(URL2);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            items = new JSONArray(response.toString());
            name+=items.getJSONObject(0).getString("name");
        } catch (IOException | JSONException ex) {
            Logger.getLogger(ProfilPaysAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return name;
    }
    
    public List<String> getVilleByPays(String pays) {
        String URL3 = "https://battuta.medunes.net/api/region/"+pays+"/all/?key=c16aba7bbd8682ff9dfebea23b8113d1";
        List<String> resultList = new ArrayList();
        try {
            url = new URL(URL3);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            items = new JSONArray(response.toString());
            for(int i=0;i<items.length();i++)
            {
                resultList.add(items.getJSONObject(i).getString("region"));
            }
        } catch (IOException | JSONException ex) {
            Logger.getLogger(ProfilPaysAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultList;
    }
    
    public List<String> getRegionByPaysVille(String pays, String ville) {
        String URL4 = "https://battuta.medunes.net/api/city/"+pays+"/search/?region="+ville+"&key=c16aba7bbd8682ff9dfebea23b8113d1";
        List<String> resultList = new ArrayList();
        try {
            url = new URL(URL4);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            items = new JSONArray(response.toString());
            for(int i=0;i<items.length();i++)
            {
                resultList.add(i+"-"+items.getJSONObject(i).getString("city"));
            }
        } catch (IOException | JSONException ex) {
            Logger.getLogger(ProfilPaysAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultList;
    }
    
    public String getOneRegion(String pays,String ville,String region) {
        String URL4 = "https://battuta.medunes.net/api/city/"+pays+"/search/?region="+ville+"&key=c16aba7bbd8682ff9dfebea23b8113d1";
        String name = "";
        try {
            url = new URL(URL4);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            items = new JSONArray(response.toString());
            name+=Integer.parseInt(region)+"-"+items.getJSONObject(Integer.parseInt(region)).getString("city");
            //name+=items.getJSONObject(Integer.parseInt(region)).getString("city");
        } catch (IOException | JSONException ex) {
            Logger.getLogger(ProfilPaysAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return name;
    }
    
}
