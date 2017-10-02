/*
 * Copyright (c) 2012, Codename One and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Codename One designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *  
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 * 
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 * Please contact Codename One through http://www.codenameone.com/ if you 
 * need additional information or have any questions.
 */

package com.codename1.sms.twilio;

import com.codename1.components.ToastBar;
import com.codename1.io.Log;
import com.codename1.io.rest.Response;
import com.codename1.io.rest.Rest;
import com.codename1.sms.intercept.SMSInterceptor;
import com.codename1.ui.FontImage;
import com.codename1.util.Base64;
import com.codename1.util.Callback;
import java.util.Map;

/**
 * Sends an SMS via the Twilio REST API
 *
 * @author Shai Almog
 */
public class TwilioSMS {
    private String accountSID;
    private String authToken;
    private String fromPhone;
    
    private TwilioSMS(String accountSID, String authToken, String fromPhone) {
        this.accountSID = accountSID;
        this.authToken = authToken;
        this.fromPhone = fromPhone;
    }
    
    /**
     * Create an instance of the SMS API
     * 
     * @param accountSID the account id
     * @param authToken the authorization token
     * @param fromPhone the phone from which we are sending
     * @return twilio instance
     */
    public static TwilioSMS create(String accountSID, String authToken, String fromPhone) {
        return new TwilioSMS(accountSID, authToken, fromPhone);
    }
    
    /**
     * Sends an SMS using the twilio API asynchronously
     * @param phone the phone we are sending to
     * @param body the body of the sms
     */
    public void sendSmsAsync(String phone, String body) {
        Rest.post("https://api.twilio.com/2010-04-01/Accounts/" + accountSID + 
                    "/Messages.json").
                queryParam("To", phone).
                queryParam("From", fromPhone).
                queryParam("Body", body).
                header("Authorization", "Basic " + Base64.encodeNoNewline((accountSID + ":" + authToken).getBytes())).
                getAsJsonMapAsync(new Callback<Response<Map>>() {

            @Override
            public void onSucess(Response<Map> value) {
                if(value.getResponseData() != null) {
                    String error = (String)value.getResponseData().get("error_message");
                    if(error != null) {
                        ToastBar.showErrorMessage(error);
                    }
                } else {
                    ToastBar.showErrorMessage("Error sending SMS: " + value.getResponseCode());
                }
            }

            @Override
            public void onError(Object sender, Throwable err, int errorCode, String errorMessage) {
                ToastBar.showErrorMessage("Error sending SMS: " + errorCode);
                Log.e(err);
            }
        });
    }        
   
    /**
     * Sends an SMS using the twilio API synchronously
     * @param phone the phone we are sending to
     * @param fromPhone the phone from which we are sending
     * @param body the body of the sms
     */
    public void sendSmsSync(String phone, String fromPhone, String body) {
        Response<Map> value = Rest.post("https://api.twilio.com/2010-04-01/Accounts/" + accountSID + 
                    "/Messages.json").
                queryParam("To", phone).
                queryParam("From", fromPhone).
                queryParam("Body", body).
                header("Authorization", "Basic " + Base64.encodeNoNewline((accountSID + ":" + authToken).getBytes())).
                getAsJsonMap();

        if(value.getResponseData() != null) {
            String error = (String)value.getResponseData().get("error_message");
            if(error != null) {
                ToastBar.showErrorMessage(error);
            }
        } else {
            ToastBar.showErrorMessage("Error sending SMS: " + value.getResponseCode());
        }
    }        
   
}
