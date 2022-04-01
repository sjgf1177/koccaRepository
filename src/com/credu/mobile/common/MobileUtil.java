package com.credu.mobile.common;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class MobileUtil {
    private static final String reqUrl = "http://211.201.145.102:8080/servlet/controller.mobile.NotificationServlet";

    public static String CallPushURL() {
        URL url = null;
        URLConnection uc = null;
        StringBuffer sb = new StringBuffer();
        try {
            url = new URL(reqUrl);
            uc = url.openConnection();

            uc.setDoOutput(true);
            uc.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

            String headerType = uc.getContentType();

            BufferedReader in;
            if (headerType.toUpperCase().indexOf("UTF-8") != -1) {
                in = new BufferedReader(new InputStreamReader(uc.getInputStream(), "UTF-8"));
            } else {
                in = new BufferedReader(new InputStreamReader(uc.getInputStream(), "euc-kr"));
            }

            String thisLine = null;
            while ((thisLine = in.readLine()) != null) {
                sb.append(thisLine);
                sb.append("\n");
            }
            in.close();
        } catch (IOException e) {

        }

        return sb.toString();
    }
}
