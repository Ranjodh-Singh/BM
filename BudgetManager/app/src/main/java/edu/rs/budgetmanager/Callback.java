package edu.rs.budgetmanager;

import java.util.Map;

/**
 * Created by ranjodh_singh on 12/23/2014.
 */
public interface Callback {
    public Map<String ,String > getData();
    public void setData(String key, String value);
}
