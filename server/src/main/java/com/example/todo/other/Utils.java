package com.example.todo.other;

import com.example.todo.exceptions.PhoneConfirmationException;
import org.json.JSONObject;

import java.util.Random;

public class Utils {
    public static String passwordGenerator () {
        StringBuilder password = new StringBuilder();

        String [] symbols = {"@", "#", "$", "%", "^", "&", "*", "(", ")", "="};
        int [] nums = {1,2,3,4,5,6,7,8,9,0};

        Random random = new Random();
        int charNum = 0;
        while (charNum < 10) {
            password.append(symbols[random.nextInt(symbols.length)]);
            password.append(nums[random.nextInt(nums.length)]);
            charNum++;
        }

        return password.toString();
    }

    public static String getPhoneNumberFromReq (String req) {
        JSONObject request = new JSONObject(req);
        return request.getString("phoneNumber");
    }

    public static String trimPhoneNumberFormat (String phoneNumber) {
        if(phoneNumber.length() == 10 || phoneNumber.length() == 12) {
            if(phoneNumber.startsWith("+4")) {
                return phoneNumber;
            }

            if(phoneNumber.startsWith("07")) {
                return "+4" + phoneNumber;
            }
        }

        throw new PhoneConfirmationException("The number isn't available in your country yet. We'll get there soon");
    }
}
