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
 * ��ʰ���
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

            if (v_process.equals("selectList")) { // ���ȭ�� 
                this.performBannerList(request, response, box, out);

            } else if (v_process.equals("listUpdate")) { // ��ϼ���(����,��뿩�� ����)
                this.performListUpdate(request, response, box, out);

            } else if (v_process.equals("insertPage")) { // ��������� �̵�
                this.performInsertPage(request, response, box, out);

            } else if (v_process.equals("insert")) { // �ű� ���
                this.performInsert(request, response, box, out);

            } else if (v_process.equals("updatePage")) { // �� ���������� �̵�
                this.performUpdatePage(request, response, box, out);

            } else if (v_process.equals("update")) { // ���� ����
                this.performUpdate(request, response, box, out);

            } else if (v_process.equals("viewBanner")) { // ���� ����
                this.performViewBanner(request, response, box, out);

            } else if (v_process.equals("createHTMLByRemote")) { // ���� ����
                this.performCreateHTMLByRemote(request, response, box, out);

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * ���� ��ʸ���Ʈ ȭ��
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
     * ���� ��� ���� ȭ��
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
     * ��뿩��/���ļ��� ���� ó��
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
     * ��� ȭ������ �̵�
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
     * ��� ó��
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
     * ���� ȭ������ �̵�
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
     * ���� ó��
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
     * ����ȭ�Ǿ� �ִ� �ٸ� WAS���� ȣ���ϴ� �޼����̴�. �� �޼���� ���� ��� html ������ �����Ѵ�.
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
            htmlSb.append("        <h2 class=\"blind\">���ι��</h2>\n");
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
     * banner html ������ �����Ѵ�.
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
            htmlSb.append("        <h2 class=\"blind\">���ι��</h2>\n");
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

            // html ���Ϸ� �����ϴ� ������ �н��ڵ��� Ȩ�������� ������ �� ���ϸ� �ּ�ȭ�ϰ� ������ �ε� �ӵ��� �ø��� �����̴�.
            // �㳪 �����Ǵ� ��θ� NAS�� �����ϴ� ��쿡�� ������ ������ WAS1 �������� ���� NAS���� ��Ʈ��ũ����̺� ������
            // ����� ������ �߻��Ѵ�. ���� html ������ �ش� WAS ������ �����Ѵ�. ��δ� ���� jsp ������ �ִ� ���̴�.
            // �׷��� �� ��쿡�� �ش� WAS �������� ������ �����ǰ� ����ȭ �Ǿ� �ִ� �ٸ� WAS���� ������ �ʴ´�. �̿� ���� ������
            // WAS�� html ������ ������ �� �ִ� �������� �ۼ��ϰ�, �ٸ� WAS ������ �ִ� �ּҸ� URLConnection�� �̿��Ͽ� ȣ���Ѵ�.
            
            String hostName = box.getString("hostip").toLowerCase();
            String targetURL = (hostName.equals("lms_web-was1")) ? "http://218.232.93.19/" : "http://218.232.93.18/";
            
            // targetURL = (hostName.equals("localhost") || hostName.equals("127.0.0.1")) ? "http://172.16.80.76/" : "http://172.16.80.75/";

            OutputStreamWriter wr = null;
            BufferedReader rd = null;
            URL url = new URL(targetURL + "servlet/controller.homepage.BannerAdminServlet?p_process=createHTMLByRemote");

            URLConnection conn = url.openConnection();
            HttpURLConnection hurlc = (HttpURLConnection) conn;

            hurlc.setRequestMethod("GET"); //POST ��� ����
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
