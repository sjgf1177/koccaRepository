package controller.common;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.common.CPUserStudyInfoBean;
import com.credu.library.ConfigSet;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import common.CryptCPUtil;
@WebServlet("/servlet/controller.common.CPUserStudyInfoSendServlet")
public class CPUserStudyInfoSendServlet extends javax.servlet.http.HttpServlet implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 3230193715904386395L;

    /**
     * Pass get requests through to PerformTask
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    /**
     * doPost
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     */
    @SuppressWarnings("unchecked")
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;
        String process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);

            process = box.getStringDefault("p_process", "sendCPUserStudyInfo");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (process.equals("sendCPUserStudyInfo")) {
                this.performSendCPUserStudyInfo(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * B2B 사용자 학습 정보를 전송한다.
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSendCPUserStudyInfo(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        Date dt = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");

        String encCpStr = "";
        String eduResult = "";
        StringBuffer sb = new StringBuffer();
        StringBuffer logSb = new StringBuffer();
        int userCount = 0;
        String today = "";

        DataBox dbox = null;

        String sendFlag = box.getStringDefault("sendFlag", "N");

        try {
            req.setAttribute("requestbox", box);
            today = sdf.format(dt);

            encCpStr = CryptCPUtil.encrypt(today.substring(0, 4) + "KOCCA" + today.substring(4, 8)).replaceAll("\n", "");

            logSb.append("[").append(today.substring(0, 4)).append("-").append(today.substring(4, 6)).append("-").append(today.substring(6, 8)).append("]\n");
            logSb.append("encryption key : ").append(encCpStr).append("\n");

            CPUserStudyInfoBean bean = new CPUserStudyInfoBean();
            ArrayList<DataBox> cpUserStudyInfoList = bean.selectCPUserStudyInfo("N000096");

            sb.append("<!DOCTYPE html>  \n");
            sb.append("<head>           \n");
            sb.append("<title></title>  \n");

            if (sendFlag.equals("Y")) {
                sb.append("<script> \n");
                sb.append("    window.onload = function() { \n");
                sb.append("fnSend();\n");
                // sb.append("        setTimeout(\"fnSend()\", 1000);  \n");
                sb.append("    }    \n");
                sb.append(" \n");
                sb.append("    function fnSend() {  \n");
                sb.append("        var sendForm = document.getElementById(\"oSendForm\");   \n");
                // sb.append("        sendForm.action = \"https://www.cje-academy.co.kr/back/CPResult.do?cmd=cpEduResultUpdate\"\n");
                sb.append("        sendForm.action = \"/learn/admin/b2btrans/za_test.jsp\"\n");
                sb.append("        sendForm.submit();   \n");
                sb.append("    }        \n");
                sb.append("</script>    \n");
            }

            sb.append("</head>  \n");
            sb.append("<body>   \n");
            sb.append("<form id=\"oSendForm\" name=\"sendForm\" method=\"post\">    \n");
            sb.append("    <input type=\"hidden\" name=\"p_cpCheck\" value=\"").append(encCpStr).append("\" />  \n");
            sb.append("    <input type=\"hidden\" name=\"p_cpcomp\" value=\"00016\" />  \n");

            for (int i = 0; i < cpUserStudyInfoList.size(); i++) {
                dbox = cpUserStudyInfoList.get(i);
                if (!dbox.getString("d_cs_subj").equals("")) {
                    eduResult = dbox.getString("d_eduresult");
                    sb.append("    <input type=\"hidden\" name=\"p_cpEduResultData\" value=\"").append(eduResult).append("\" />                 \n");

                    logSb.append((++userCount)).append(" : ").append(eduResult).append(" : ").append(dbox.getString("d_year")).append("/").append(dbox.getString("d_subj")).append("/").append(dbox.getString("d_subjseq")).append("\n");
                }
            }
            
            sb.append("</form>  \n");
            sb.append("</body>  \n");
            sb.append("</html>  \n");

            this.writeSendLog(today, logSb);

            System.out.println(sb.toString());
            
            out.println(sb.toString());
            out.flush();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSendCPUserStudyInfo()\r\n" + ex.getMessage());
        }
    }

    private void writeSendLog(String date, StringBuffer logSb) throws Exception {
        ConfigSet conf = new ConfigSet();
        try {
            String upDir = conf.getProperty("dir.home");

            upDir += "upload\\cp_send_log\\" + date.substring(0, 6);

            File d = new File(upDir); // 월단위로 로그 디렉토리 생성

            if (!d.exists()) {
                d.mkdirs();
            }
            
            File f = new File(upDir + "\\" + date + "_send.log");
            
            if (!f.exists()) {
                f.createNewFile();
            }

            FileWriter fw = new FileWriter(upDir + "\\" + date + "_send.log", true);
            BufferedWriter bw = new BufferedWriter(fw);
            
            bw.write(logSb.toString());
            bw.newLine();
            bw.close();

        } catch (Exception e) {

        }
    }
}
