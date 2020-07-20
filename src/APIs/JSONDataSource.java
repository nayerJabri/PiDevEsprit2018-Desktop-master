/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package APIs;

import java.util.List;

/**
 *
 * @author hero
 */
public interface JSONDataSource {
    public List<String> fetchResult(String query);
}
