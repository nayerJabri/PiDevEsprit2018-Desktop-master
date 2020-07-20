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
public class BookAPI implements JSONDataSource {
    private static final String URL = "https://www.googleapis.com/books/v1/volumes?q=";
    private URL url ;
    private HttpURLConnection httpURLConnection ;
    private BufferedReader in;
    private StringBuffer response;
    private String inputLine;
    private JSONArray items;
    private static BookAPI bookAPI;
    private BookAPI()
    {
        
    }
    
    public static BookAPI getInstance()
    {
        if(bookAPI == null) bookAPI = new BookAPI();
        return bookAPI;
    }
    
    @Override
    public List<String> fetchResult(String query)
    {
        List<String> resultList = new ArrayList();
        if(query.equals("")) return resultList;
        query = query.replaceAll(" ", "%20");
        try {
            url = new URL(URL+query);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            items = new JSONObject(response.toString()).getJSONArray("items");
            for(int i=0;i<items.length();i++)
            {
                resultList.add(items.getJSONObject(i).getJSONObject("volumeInfo").getString("title"));
            }
        } catch (IOException | JSONException ex) {
            Logger.getLogger(BookAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultList;
    }
    
}
