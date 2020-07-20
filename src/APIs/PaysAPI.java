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
public class PaysAPI implements JSONDataSource {

    private static PaysAPI paysAPI;
    private JSONArray items;
    private static final String URL = "https://battuta.medunes.net/api/country/all/?key=c16aba7bbd8682ff9dfebea23b8113d1";
    private URL url ;
    private HttpURLConnection httpURLConnection ;
    private BufferedReader in;
    private StringBuffer response;
    private String inputLine;
    
    private PaysAPI(){}
    
    public static PaysAPI getInstance()
    {
        if(paysAPI == null) paysAPI = new PaysAPI();
        return paysAPI;
    }
    
    @Override
    public List<String> fetchResult(String query) {
        List<String> resultList = new ArrayList();
        if(query.equals("")) return resultList;
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
                if(items.getJSONObject(i).getString("name").toLowerCase().startsWith(query.toLowerCase()))
                    resultList.add(items.getJSONObject(i).getString("name")+"-"+items.getJSONObject(i).getString("code"));
            }
        } catch (IOException | JSONException ex) {
            Logger.getLogger(BookAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultList;
    }
    
}
