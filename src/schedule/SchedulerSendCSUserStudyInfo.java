package schedule;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.TimeZone;

import common.CryptCPUtil;

public class SchedulerSendCSUserStudyInfo {
    public static void main(String args[]) throws Exception {

        if (args.length == 0) {
            System.out.println("Usage: java schedule.SchedulerSendCSUserInfo GRCODE sendFlag(Y/N)");
            System.exit(0);
        }

        String grcode = args[0];
        String sendFlag = args[1];

        OutputStreamWriter wr = null;
        BufferedReader rd = null;

        ArrayList<HashMap<String, String>> resultList = null;
        HashMap<String, String> resultMap = null;

        StringBuffer logSb = new StringBuffer();
        StringBuffer paramSb = new StringBuffer();

        String csSubj = "";
        String eduResult = "";
        String encCpStr = "";
        String today = "";
        String logTime = "";
        String line = "";

        System.out.println("sendFlag : " + sendFlag);

        try {

            TimeZone tz = TimeZone.getTimeZone("Asia/Seoul");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date dt = new Date();
            
            sdf.setTimeZone(tz);
            logTime = sdf.format(dt);
            today = logTime.replaceAll("-", "").substring(0, 8);

            System.out.println("logTime : " + logTime);
            System.out.println("today : " + today);

            if (sendFlag.equals("Y")) {

                encCpStr = CryptCPUtil.encrypt(today.substring(0, 4) + "KOCCA" + today.substring(4, 8)).replaceAll("\n", "");

                CSUserStudyInfoBean bean = new CSUserStudyInfoBean();

                resultList = bean.selectCPUserStudyInfo(grcode);

                paramSb.append(URLEncoder.encode("p_cpCheck", "UTF-8")).append("=").append(URLEncoder.encode(encCpStr, "UTF-8"));
                paramSb.append("&").append(URLEncoder.encode("p_cpcomp", "UTF-8")).append("=").append(URLEncoder.encode("00016", "UTF-8"));

                logSb.append("[").append(logTime).append("]\n");
                logSb.append("encryption key : ").append(encCpStr).append("\n");

                if (resultList.size() > 0) {
                    for (int i = 0; i < resultList.size(); i++) {
                        resultMap = resultList.get(i);
                        csSubj = resultMap.get("cs_subj");
                        eduResult = resultMap.get("eduresult");
                        if (csSubj != null && !csSubj.equals("")) {
                            paramSb.append("&").append(URLEncoder.encode("p_cpEduResultData", "UTF-8")).append("=").append(eduResult);

                            logSb.append(i + 1).append(" : ").append(eduResult).append(" : ").append(resultMap.get("year")).append("/").append(resultMap.get("subj")).append("/").append(resultMap.get("subjseq")).append("\n");
                        }
                    }

                    trustAllHttpsCertificates();

                    // Send data
                    // URL url = new URL("https://edu.kocca.or.kr/learn/admin/b2btrans/za_test.jsp");
                    URL url = new URL("https://www.cje-academy.co.kr/back/CPResult.do?cmd=cpEduResultUpdate");

                    URLConnection conn = url.openConnection();
                    HttpURLConnection hurlc = (HttpURLConnection) conn;

                    hurlc.setRequestMethod("POST"); //POST 방식 전송
                    hurlc.setDoOutput(true);
                    hurlc.setDoInput(true);
                    hurlc.setUseCaches(false);
                    hurlc.setDefaultUseCaches(false);

                    wr = new OutputStreamWriter(hurlc.getOutputStream());
                    wr.write(paramSb.toString());
                    wr.flush();
                    wr.close();

                    writeLog(today, logSb.toString(), "send");

                    // Get the response
                    rd = new BufferedReader(new InputStreamReader(hurlc.getInputStream(), "UTF-8"));

                    logSb.setLength(0);
                    logTime = sdf.format(dt);
                    logSb.append("[").append(logTime).append("]\n");

                    while ((line = rd.readLine()) != null) {
                        logSb.append(line);
                    }
                    writeLog(today, logSb.toString(), "result");
                    System.out.println(logSb.toString());
                } else {
                    logSb.setLength(0);
                    logSb.append("[").append(today.substring(0, 4)).append("-").append(today.substring(4, 6)).append("-").append(today.substring(6, 8)).append("]\n");
                    logSb.append("none");
                    writeLog(today, logSb.toString(), "send");
                    writeLog(today, logSb.toString(), "result");
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());

            Object[] exObj = e.getStackTrace();
            for (int k = 0; k < exObj.length; k++) {
                System.out.println(exObj[k].toString());
            }
        } finally {
            try {
                if (wr != null) {
                    wr.close();
                }
                if (rd != null) {
                    rd.close();
                }
            } catch (IOException ioe) {
                System.out.println(ioe.toString());
            } catch (Exception e) {
                System.out.println(e.toString());
            }
        }

    }

    private static void writeLog(String date, String logStr, String logType) throws Exception {
        try {
            String upDir = "D:\\eclipse\\workspace\\KOCCA_DEV\\log\\cp_send_log\\" + date.substring(0, 6);

            File d = new File(upDir); // 월단위로 로그 디렉토리 생성

            if (!d.exists()) {
                d.mkdirs();
            }

            File f = new File(upDir + "\\" + date + "_" + logType + ".log");

            if (!f.exists()) {
                f.createNewFile();
            }

            FileWriter fw = new FileWriter(upDir + "\\" + date + "_" + logType + ".log", true);
            BufferedWriter bw = new BufferedWriter(fw);

            bw.write(logStr);
            bw.newLine();
            bw.close();

        } catch (Exception e) {

        }
    }

    private static void trustAllHttpsCertificates() throws Exception {

        //  Create a trust manager that does not validate certificate chains:

        javax.net.ssl.TrustManager[] trustAllCerts =

        new javax.net.ssl.TrustManager[1];

        javax.net.ssl.TrustManager tm = new miTM();

        trustAllCerts[0] = tm;

        javax.net.ssl.SSLContext sc = javax.net.ssl.SSLContext.getInstance("SSL");

        sc.init(null, trustAllCerts, null);

        javax.net.ssl.HttpsURLConnection.setDefaultSSLSocketFactory(

        sc.getSocketFactory());

    }

    public static class miTM implements javax.net.ssl.TrustManager, javax.net.ssl.X509TrustManager {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public boolean isServerTrusted(java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public boolean isClientTrusted(java.security.cert.X509Certificate[] certs) {
            return true;
        }

        public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) throws java.security.cert.CertificateException {
            return;
        }

        public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) throws java.security.cert.CertificateException {
            return;
        }
    }
}
