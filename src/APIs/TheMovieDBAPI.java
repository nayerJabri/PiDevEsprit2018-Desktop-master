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
public class TheMovieDBAPI implements JSONDataSource {
    public static final String URL = "https://api.themoviedb.org/3/search/%s?api_key=2e5aee0d6e6ad27e13cf48eb3cd9b5dc&language=en-US&query=%s&page=1&include_adult=true";
    public static final String Movie = "movie";
    public static final String Serie = "tv";
    private String fURL;
    private String attr = "title";
    private URL url ;
    private HttpURLConnection httpURLConnection ;
    private BufferedReader in;
    private StringBuffer response;
    private String inputLine;
    private JSONArray items;
    private static TheMovieDBAPI theMovieDBAPI;
    
    public TheMovieDBAPI(String type){
        this.fURL = String.format(URL, type,"%s");
        if(type == TheMovieDBAPI.Serie) attr = "original_name";
    }
    

    @Override
    public List<String> fetchResult(String query) {
        List<String> resultList = new ArrayList();
        if(query.equals("")) return resultList;
        query = query.replaceAll(" ", "%20");
        try {
            url = new URL(String.format(fURL, query));
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            in = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            response = new StringBuffer();
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            items = new JSONObject(response.toString()).getJSONArray("results");
            for(int i=0;i<items.length();i++)
            {
                resultList.add(items.getJSONObject(i).getString(attr));
            }
        } catch (IOException | JSONException ex) {
            Logger.getLogger(BookAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultList;
    }
}
