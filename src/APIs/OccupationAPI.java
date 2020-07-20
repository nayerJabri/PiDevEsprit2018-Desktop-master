/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APIs;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONTokener;

/**
 *
 * @author hero
 */
public class OccupationAPI implements JSONDataSource {
    
    private static OccupationAPI occupationAPI;
    private JSONArray items;
    
    private OccupationAPI(){}
    public static OccupationAPI getInstance()
    {
        if(occupationAPI == null)
            occupationAPI = new OccupationAPI();
        return occupationAPI;
    }

    @Override
    public List<String> fetchResult(String query) {
        List<String> resultList = new ArrayList<>();
                if(query.equals("")) return resultList;
        try {
            items = new JSONArray(new JSONTokener(new FileInputStream("src/APIs/Emploi.json")));
            for(int i=0;i<items.length();i++)
            {
                if(items.getString(i).toLowerCase().startsWith(query.toLowerCase()))
                    resultList.add(items.getString(i));
            }
        } catch (JSONException ex) {
            Logger.getLogger(OccupationAPI.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OccupationAPI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return resultList;
    }
    
}
