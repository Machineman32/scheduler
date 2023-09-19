package com.example.todo.services;

import com.example.todo.exceptions.PhoneConfirmationException;
import com.example.todo.models.Users.UserPhoneConfirmationDTO;
import com.example.todo.other.Utils;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class PhoneConfirmationService {

    @Value("${TWILIO_ACCOUNT_SID}")
    private String twilio_user;

    @Value("${TWILIO_AUTH_TOKEN}")
    private String twilio_token;


    public String generateOTP (String request) {
        Twilio.init(this.twilio_user, this.twilio_token);

        String phoneNo = Utils.getPhoneNumberFromReq(request);
        String number = Utils.trimPhoneNumberFormat(phoneNo);

        Verification verification = Verification.creator(
                        "VAa8824a0f7b8b6a7f56f4b38440f24f68",
                        number,
                        "sms")
                .create();

        System.out.println(verification.getStatus());

        return "Your verification code has been generated. Please check your phone";
    }

    public boolean verifyOTP (String phoneNumber, String OTP) {
        Twilio.init(this.twilio_user, this.twilio_token);
        String number = Utils.trimPhoneNumberFormat(phoneNumber);

        try {
            VerificationCheck verificationCheck = VerificationCheck.creator("VAa8824a0f7b8b6a7f56f4b38440f24f68")
                    .setTo(number)
                    .setCode(OTP)
                    .create();
            System.out.println(verificationCheck.getStatus());
            return true;
        } catch (PhoneConfirmationException phoneConfirmationException) {
            phoneConfirmationException.getMessage();
        }

        return false;
    }

    public void sendNewPassword (String phoneNumber, String newPass) {
        Twilio.init(this.twilio_user, this.twilio_token);

        Message.creator(new PhoneNumber(phoneNumber),
                        new PhoneNumber("+19408182218"),
                   "A new password has been generated: " + newPass)
                .create();
    }
}
