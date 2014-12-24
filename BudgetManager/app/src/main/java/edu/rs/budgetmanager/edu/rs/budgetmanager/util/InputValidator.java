package edu.rs.budgetmanager.edu.rs.budgetmanager.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by ranjodh_singh on 12/24/2014.
 */
public class InputValidator {

public static List<String> validate(Map<String,String> data) {
    List<String> list = new ArrayList<>();
    String errorAmount = amountValidator(data.get("amount"));
    String errorDatAndTime = dateAndTimeValidator(data.get("date"), data.get("time"));
    if (errorAmount != null) list.add(errorAmount);
    if (errorDatAndTime != null) list.add(errorDatAndTime);
    return list;
}


    private static String dateAndTimeValidator(String date, String time) {
        //TODO: validation is pending as Editext view has to be changed to the data and time pickers.
        return null;
    }
    private static String amountValidator(String amount) {
    try{
        int amountInt = Integer.parseInt(amount);
        if (amountInt <= 0 ){
            return "Please Enter the Amount > 0 .";
        }
    }catch(NumberFormatException ex){
        return "Please select the Decimal value for the Amount. ";
    }
return null;
    }
}
