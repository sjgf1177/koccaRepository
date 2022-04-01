package controller.homepage;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Serializable;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.BannerAdminBean;
import com.credu.library.AlertManager;
import com.credu.library.ConfigSet;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;
import common.CreateHTMLFile;

/**
 * 배너관리
 * 
 * @author KR
 * 
 */
@SuppressWarnings("serial")
@WebServlet("/servlet/controller.homepage.BannerAdminServlet")
public class BannerAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

    /**
     * Pass get requests through to PerformTask
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     */
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
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();

            request.setAttribute("uploadName", "banner");

            box = RequestManager.getBox(request);
            v_process = box.getStringDefault("p_process", "selectList");

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (!v_process.equals("createHTMLByRemote")) {
                if (!AdminUtil.getInstance().checkRWRight("BannerAdminServlet", v_process, out, box)) {
                    return;
                }
            }

            if (v_process.equals("selectList")) { // 목록화면 
                this.performBannerList(request, response, box, out);

            } else if (v_process.equals("listUpdate")) { // 목록수정(정렬,사용여부 수정)
                this.performListUpdate(request, response, box, out);

            } else if (v_process.equals("insertPage")) { // 등록페이지 이동
                this.performInsertPage(request, response, box, out);

            } else if (v_process.equals("insert")) { // 신규 등록
                this.performInsert(request, response, box, out);

            } else if (v_process.equals("updatePage")) { // 상세 수정페이지 이동
                this.performUpdatePage(request, response, box, out);

            } else if (v_process.equals("update")) { // 수정 저장
                this.performUpdate(request, response, box, out);

            } else if (v_process.equals("viewBanner")) { // 수정 저장
                this.performViewBanner(request, response, box, out);

            } else if (v_process.equals("createHTMLByRemote")) { // 수정 저장
                this.performCreateHTMLByRemote(request, response, box, out);

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 메인 배너리스트 화면
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performBannerList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            BannerAdminBean bean = new BannerAdminBean();
            ArrayList<DataBox> bannerList = bean.selectBannerList(box);

            request.setAttribute("bannerList", bannerList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/mainbanner/za_banner_L.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("bannerList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 메인 배너 보기 화면
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performViewBanner(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            BannerAdminBean bean = new BannerAdminBean();
            ArrayList<DataBox> viewBannerList = bean.viewBannerList(box);

            request.setAttribute("viewBannerList", viewBannerList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/mainbanner/za_banner_preview_P.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("viewBannerList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 사용여부/정렬순서 수정 처리
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void performListUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            BannerAdminBean bean = new BannerAdminBean();
            
            String retUrl = "/servlet/controller.homepage.BannerAdminServlet";
            box.put("p_process", "selectList");
            String resultMsg = "";
            int resultCnt = bean.updateBannerList(box);

            AlertManager alert = new AlertManager();

            if (resultCnt > 0) {

                ArrayList<DataBox> bannerList = bean.selectBannerList(box);
                this.createBannerHTMLFile(bannerList, box);

                resultMsg = "update.ok";
                alert.alertOkMessage(out, resultMsg, retUrl, box);

            } else {
                resultMsg = "update.fail";
                alert.alertFailMessage(out, resultMsg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 등록 화면으로 이동
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/mainbanner/za_banner_I.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 등록 처리
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            BannerAdminBean bean = new BannerAdminBean();

            String retUrl = "/servlet/controller.homepage.BannerAdminServlet";
            box.put("p_process", "selectList");
            String resultMsg = "";
            int resultCnt = bean.insertBanner(box);

            AlertManager alert = new AlertManager();

            if (resultCnt > 0) {
                resultMsg = "insert.ok";
                alert.alertOkMessage(out, resultMsg, retUrl, box);

            } else {
                resultMsg = "insert.fail";
                alert.alertFailMessage(out, resultMsg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }

    }

    /**
     * 수정 화면으로 이동
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            BannerAdminBean bean = new BannerAdminBean();
            DataBox bannerData = bean.selectBanner(box);

            request.setAttribute("bannerData", bannerData);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/mainbanner/za_banner_U.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 수정 처리
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings("unchecked")
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            BannerAdminBean bean = new BannerAdminBean();

            String retUrl = "/servlet/controller.homepage.BannerAdminServlet";
            box.put("p_process", "selectList");
            String resultMsg = "";
            int resultCnt = bean.updateBanner(box);

            AlertManager alert = new AlertManager();

            if (resultCnt > 0) {
                resultMsg = "update.ok";
                alert.alertOkMessage(out, resultMsg, retUrl, box);

            } else {
                resultMsg = "update.fail";
                alert.alertFailMessage(out, resultMsg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }

    }

    /**
     * 이중화되어 있는 다른 WAS에서 호출하는 메서드이다. 이 메서드는 메인 배너 html 파일을 생성한다.
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performCreateHTMLByRemote(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            System.out.println("performCreateHTMLByRemote start ");
            request.setAttribute("requestbox", box);

            ConfigSet conf = new ConfigSet();
            StringBuffer htmlSb = new StringBuffer();
            DataBox dbox = null, fDbox = null;

            String imageSize = "";
            String imageWidth = "";

            BannerAdminBean bean = new BannerAdminBean();
            ArrayList<DataBox> bannerList = bean.selectBannerList(box);

            htmlSb.append("<!-- mainBannerSection -->\n");
            htmlSb.append("<div class=\"mainBannerSection\">\n");
            htmlSb.append("    <!-- //mainbnr -->\n");
            htmlSb.append("    <div class=\"mainbnr\">\n");
            htmlSb.append("        <h2 class=\"blind\">메인배너</h2>\n");
            htmlSb.append("        <div class=\"viewport\">\n");
            htmlSb.append("\n");

            for (int i = 0; i < bannerList.size(); i++) {
                dbox = (DataBox) bannerList.get(i);

                imageSize = dbox.getString("d_img_size").toLowerCase();
                imageWidth = (imageSize.equals("w1")) ? "860" : (imageSize.equals("w2") ? "570" : "280");

                if (dbox.getString("d_use_yn").equals("Y") && dbox.getString("d_period_yn").equals("Y") && dbox.getString("d_fixed_flag").equals("N")) {

                    htmlSb.append("            <ul class=\"group\">\n");
                    htmlSb.append("                <li class=\"").append(imageSize).append("\">\n");
                    htmlSb.append("                    <a href=\"").append(dbox.getString("d_url")).append("\" target=\"").append(dbox.getString("d_url_target")).append("\">\n");
                    htmlSb.append("                    <img src=\"").append(dbox.getString("d_save_img_nm").replaceAll("\\\\", "/")).append("\" alt=\"").append(dbox.getString("d_explain")).append("\" width=\"").append(imageWidth).append(
                            "\" height=\"276\" />\n");
                    htmlSb.append("                    </a>\n");
                    htmlSb.append("                </li>\n");
                    htmlSb.append("            </ul>\n");
                } else if (dbox.getString("d_fixed_flag").equals("Y")) {
                    fDbox = dbox;
                }
            }

            htmlSb.append("        </div>\n");
            htmlSb.append("    </div>\n");
            htmlSb.append("    <!-- //mainbnr -->\n");
            htmlSb.append("\n");
            htmlSb.append("    <!-- mainbnrSide -->\n");
            htmlSb.append("    <div class=\"mainbnrSide\">\n");
            htmlSb.append("\n");
            htmlSb.append("            <ul>\n");
            htmlSb.append("                <li class=\"").append(fDbox.getString("d_img_size").toLowerCase()).append("\">\n");
            htmlSb.append("                    <a href=\"").append(fDbox.getString("d_url")).append("\" target=\"").append(fDbox.getString("d_url_target")).append("\">\n");
            htmlSb.append("                    <img src=\"").append(fDbox.getString("d_save_img_nm").replaceAll("\\\\", "/")).append("\" alt=\"").append(fDbox.getString("d_explain")).append("\" width=\"280\" height=\"276\" />\n");
            htmlSb.append("                    </a>\n");
            htmlSb.append("                </li>\n");
            htmlSb.append("            </ul>\n");
            htmlSb.append("\n");
            htmlSb.append("    </div>\n");
            htmlSb.append("    <!-- /mainbnrSide -->\n");
            htmlSb.append("</div>\n");
            htmlSb.append("<!-- /mainBannerSection -->\n");

            String upDir = conf.getProperty("dir.home");

            upDir += "learn\\user\\2013\\portal\\include\\";

            CreateHTMLFile html = new CreateHTMLFile(upDir, "mainbanner.html", htmlSb.toString());
            html.create();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("bannerList()\r\n" + ex.getMessage());
        }
    }

    /**
     * banner html 파일을 생성한다.
     * 
     * @param bannerList
     * @throws Exception
     */
    private void createBannerHTMLFile(ArrayList<DataBox> bannerList, RequestBox box) throws Exception {
        ConfigSet conf = new ConfigSet();
        StringBuffer htmlSb = new StringBuffer();
        DataBox dbox = null, fDbox = null;

        String imageSize = "";
        String imageWidth = "";

        try {

            htmlSb.append("<!-- mainBannerSection -->\n");
            htmlSb.append("<div class=\"mainBannerSection\">\n");
            htmlSb.append("    <!-- //mainbnr -->\n");
            htmlSb.append("    <div class=\"mainbnr\">\n");
            htmlSb.append("        <h2 class=\"blind\">메인배너</h2>\n");
            htmlSb.append("        <div class=\"viewport\">\n");
            htmlSb.append("\n");

            for (int i = 0; i < bannerList.size(); i++) {
                dbox = (DataBox) bannerList.get(i);

                imageSize = dbox.getString("d_img_size").toLowerCase();
                imageWidth = (imageSize.equals("w1")) ? "860" : (imageSize.equals("w2") ? "570" : "280");

                if (dbox.getString("d_use_yn").equals("Y") && dbox.getString("d_period_yn").equals("Y") && dbox.getString("d_fixed_flag").equals("N")) {

                    htmlSb.append("            <ul class=\"group\">\n");
                    htmlSb.append("                <li class=\"").append(imageSize).append("\">\n");
                    htmlSb.append("                    <a href=\"").append(dbox.getString("d_url")).append("\" target=\"").append(dbox.getString("d_url_target")).append("\">\n");
                    htmlSb.append("                    <img src=\"").append(dbox.getString("d_save_img_nm").replaceAll("\\\\", "/")).append("\" alt=\"").append(dbox.getString("d_explain")).append("\" width=\"").append(imageWidth).append(
                            "\" height=\"276\" />\n");
                    htmlSb.append("                    </a>\n");
                    htmlSb.append("                </li>\n");
                    htmlSb.append("            </ul>\n");
                } else if (dbox.getString("d_fixed_flag").equals("Y")) {
                    fDbox = dbox;
                }
            }

            htmlSb.append("        </div>\n");
            htmlSb.append("    </div>\n");
            htmlSb.append("    <!-- //mainbnr -->\n");
            htmlSb.append("\n");
            htmlSb.append("    <!-- mainbnrSide -->\n");
            htmlSb.append("    <div class=\"mainbnrSide\">\n");
            htmlSb.append("\n");
            htmlSb.append("            <ul>\n");
            htmlSb.append("                <li class=\"").append(fDbox.getString("d_img_size").toLowerCase()).append("\">\n");
            htmlSb.append("                    <a href=\"").append(fDbox.getString("d_url")).append("\" target=\"").append(fDbox.getString("d_url_target")).append("\">\n");
            htmlSb.append("                    <img src=\"").append(fDbox.getString("d_save_img_nm").replaceAll("\\\\", "/")).append("\" alt=\"").append(fDbox.getString("d_explain")).append("\" width=\"280\" height=\"276\" />\n");
            htmlSb.append("                    </a>\n");
            htmlSb.append("                </li>\n");
            htmlSb.append("            </ul>\n");
            htmlSb.append("\n");
            htmlSb.append("    </div>\n");
            htmlSb.append("    <!-- /mainbnrSide -->\n");
            htmlSb.append("</div>\n");
            htmlSb.append("<!-- /mainBannerSection -->\n");

            String upDir = conf.getProperty("dir.home");

            upDir += "learn\\user\\2013\\portal\\include\\";
            // upDir += "\\upload\\homepage\\mainbanner\\";

            CreateHTMLFile html = new CreateHTMLFile(upDir, "mainbanner.html", htmlSb.toString());
            html.create();

            // html 파일로 생성하는 이유는 학습자들이 홈페이지에 접속할 때 부하를 최소화하고 페이지 로딩 속도를 올리기 위함이다.
            // 허나 생성되는 경로를 NAS로 지정하는 경우에는 문제가 없지만 WAS1 서버에서 종종 NAS와의 네트워크드라이브 연결이
            // 끊기는 현상이 발생한다. 따라서 html 파일을 해당 WAS 서버에 생성한다. 경로는 기존 jsp 파일이 있던 곳이다.
            // 그런데 이 경우에는 해당 WAS 서버에만 파일이 생성되고 이중화 되어 있는 다른 WAS에는 생기지 않는다. 이에 따라 각각의
            // WAS에 html 파일을 생성할 수 있는 페이지를 작성하고, 다른 WAS 서버에 있는 주소를 URLConnection을 이용하여 호출한다.
            
            String hostName = box.getString("hostip").toLowerCase();
            String targetURL = (hostName.equals("lms_web-was1")) ? "http://218.232.93.19/" : "http://218.232.93.18/";
            
            // targetURL = (hostName.equals("localhost") || hostName.equals("127.0.0.1")) ? "http://172.16.80.76/" : "http://172.16.80.75/";

            OutputStreamWriter wr = null;
            BufferedReader rd = null;
            URL url = new URL(targetURL + "servlet/controller.homepage.BannerAdminServlet?p_process=createHTMLByRemote");

            URLConnection conn = url.openConnection();
            HttpURLConnection hurlc = (HttpURLConnection) conn;

            hurlc.setRequestMethod("GET"); //POST 방식 전송
            hurlc.setDoOutput(true);
            hurlc.setDoInput(true);
            hurlc.setUseCaches(false);
            hurlc.setDefaultUseCaches(false);

            wr = new OutputStreamWriter(hurlc.getOutputStream());
            wr.write("");
            wr.flush();
            wr.close();

            // Get the response
            rd = new BufferedReader(new InputStreamReader(hurlc.getInputStream(), "euc-kr"));

            String line = "";

            while ((line = rd.readLine()) != null) {
                System.out.println(line);
            }

        } catch (Exception e) {
            ErrorManager.getErrorStackTrace(e);
            throw new Exception("performInsertPage()\r\n" + e.getMessage());
        }

    }
}
