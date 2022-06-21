package com.lushkov.springpractice.utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.net.ssl.HttpsURLConnection;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;

@Component
public class CaptchaValidation {

    final static Logger LOG = LogManager.getLogger(CaptchaValidation.class);

    private static String secretKey;

    @Value("${captcha.secret_key}")
    public void setSecretKey(String secretKey) {
        CaptchaValidation.secretKey = secretKey;
    }

    public static final String SITE_VERIFY_URL =
            "https://www.google.com/recaptcha/api/siteverify";

    public static boolean verify(String gRecaptchaResponse) {
        if (gRecaptchaResponse == null || gRecaptchaResponse.length() == 0) {
            return false;
        }

        try {
            URL verifyUrl = new URL(SITE_VERIFY_URL);

            HttpsURLConnection conn = (HttpsURLConnection) verifyUrl.openConnection();

            conn.setRequestMethod("POST");
            conn.setRequestProperty("User-Agent", "Mozilla/5.0");
            conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");

            String postParams = "secret=" + secretKey
                    + "&response=" + gRecaptchaResponse;
            conn.setDoOutput(true);

            OutputStream outStream = conn.getOutputStream();
            outStream.write(postParams.getBytes());

            outStream.flush();
            outStream.close();

            int responseCode = conn.getResponseCode();
            LOG.info("responseCode=" + responseCode);

            InputStream is = conn.getInputStream();

            JsonReader jsonReader = Json.createReader(is);
            JsonObject jsonObject = jsonReader.readObject();
            jsonReader.close();

            LOG.info("Response: " + jsonObject);

            return jsonObject.getBoolean("success");
        } catch (Exception e) {
            LOG.error("Captcha validation failed", e);
            return false;
        }
    }
}
