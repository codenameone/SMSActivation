# SMSActivation

A common system for user verification requests the phone number at which point an SMS is sent. On Android this can be intercepted automatically if a permission is granted. Elsewhere or if the permission isn't granted the user can input the number manually and effectively verify the phone number is indeed correct.

![Initial UI](https://www.codenameone.com/img/blog/sms-activation-signup.png)

This whole process is encapsulated in this library which uses the Twilio API to send the verification SMS. Intercepts it on Android and effectively makes the UI workflow work properly on iOS, Android, Windows etc.

To launch this tool you can use:

````java
TwilioSMS smsAPI = TwilioSMS.create(accountSID, authToken, fromPhone);
ActivationForm.create("Signup").
        show(s -> Log.p(s), smsAPI);
````

There are additional options you can use in `ActivationForm` such as:

- `codeDigits(int)` - Number of digits in the activation code sent via SMS
- `enterNumberLabel(String)` - Text of the label above the number input
- `includeFab(boolean)` - True if a fab should be shown, by default a fab button will appear in Android only
- `includeTitleBarNext(boolean)` - True if a next arrow should appear in the title, by default this would appear in non-Android platforms

![Country code popup](https://www.codenameone.com/img/blog/sms-activation-flags.png)

