package controller.homepage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.SearchKeywordBean;
import com.credu.library.AlertManager;
import com.credu.library.ConfigSet;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;
import common.CreateHTMLFile;

/**
 * �˻��� �α� ������ ��ȸ�Ѵ�.
 * 
 * @author saderaser
 * 
 */
@SuppressWarnings("serial")
@WebServlet("/servlet/controller.homepage.SearchWordAdminServlet")
public class SearchWordAdminServlet extends HttpServlet {

    /**
     * doGet
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    /**
     * doPost
     * 
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = null;
        RequestBox box = null;
        String processType = "";
        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            processType = box.getStringDefault("processType", "selectSearchWordList");

            if (!processType.equals("createHTMLByRemote")) {
                if (!AdminUtil.getInstance().checkRWRight("SearchWordAdminServlet", processType, out, box)) {
                    return;
                }
            }

            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if (processType.equals("selectSearchWordList")) { // �Ⱓ���� �˻��� �α� ��ȸ
                this.performSelectSearchWordList(request, response, box, out);

            } else if (processType.equals("insertKeywordList")) { // ������ Ű���� ��� ����
                this.performInsertKeywordList(request, response, box, out);

            } else if (processType.equals("createHTMLByRemote")) { // ������ Ű���� ��� ����
                this.performCreateHTMLByRemote(request, response, box, out);

            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * �Ⱓ�� �˻��� ������ ����Ѵ�.
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    private void performSelectSearchWordList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);
            SearchKeywordBean bean = new SearchKeywordBean();

            ArrayList<DataBox> list = new ArrayList<DataBox>();
            ArrayList<DataBox> keywordlist = new ArrayList<DataBox>();

            list = bean.searchKeywordList(box);
            keywordlist = bean.selectKeywordList(box);

            request.setAttribute("searchKeywordList", list);
            request.setAttribute("selectKeywordList", keywordlist);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_SearchKeyword_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_SearchKeyword_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������ Ű���� ��� ����
     * 
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performInsertKeywordList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            SearchKeywordBean bean = new SearchKeywordBean();

            ArrayList<DataBox> keywordlist = new ArrayList<DataBox>();

            String retUrl = "/servlet/controller.homepage.SearchWordAdminServlet?processType=selectSearchWordList";

            String resultMsg = "";

            int resultCnt = bean.insertKeyWord(box);

            AlertManager alert = new AlertManager();

            if (resultCnt > 0) {

                keywordlist = bean.selectKeywordList(box);
                this.createSearchWordHTMLFile(keywordlist, box);
                resultMsg = "insert.ok";
                alert.alertOkMessage(out, resultMsg, retUrl, box);

            } else {
                resultMsg = "insert.fail";
                alert.alertFailMessage(out, resultMsg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSaveOpenClassList()\r\n" + ex.getMessage());
        }
    }

    /**
     * ����ȭ�Ǿ� �ִ� �ٸ� WAS���� ȣ���ϴ� �޼����̴�.
     * Ȩ�������� ����� �α� �˻��� html ������ �����Ѵ�.
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
            DataBox dbox = null;

            SearchKeywordBean bean = new SearchKeywordBean();;
            ArrayList<DataBox> keywordlist = bean.selectKeywordList(box);

            htmlSb.append(" <script type=\"text/javascript\">     \n");
            htmlSb.append("     function poplar_search(t){      \n");
            htmlSb.append("         $(\"#topWarp_searchText\").val(t);        \n");
            htmlSb.append("         totalSubjSearch1();     \n");
            htmlSb.append("     }       \n");
            htmlSb.append("         \n");
            htmlSb.append(" </script>       \n");
            htmlSb.append(" <div style=\"margin-bottom: 55px;\"></div>        \n");
            htmlSb.append(" <div style=\"padding-left: 3px;\">        \n");
            htmlSb.append(" <h2><img src=\"/images/2013/common/msw_title2.gif\" alt=\"�α�˻���\" /></h2>       \n");
            htmlSb.append(" <ul style=\"background-image: url('/images/2013/common/msw_bg.gif')\">        \n");
            htmlSb.append("     <li style=\"margin-top: -5px;\">&nbsp;</li>       \n");
            htmlSb.append("  \n");

            if (keywordlist.size() > 0) {

                for (int i = 0; i < keywordlist.size(); i++) {
                    dbox = (DataBox) keywordlist.get(i);

                    htmlSb.append("     <li style=\"margin-left: 5px; padding-bottom: 5px;\">     \n");
                    htmlSb.append("         <img src=\"/images/2013/common/msw_nb").append(i + 1).append("r.gif\" alt=\"").append(i + 1).append("\" style=\"vertical-align: middle;\" />        \n");
                    htmlSb.append("         <a href=\"javascript:poplar_search('").append(dbox.getString("d_keyword")).append("');\"><strong style=\"color: e2e2e2\">").append(dbox.getString("d_keyword")).append("</strong></a> \n");
                    htmlSb.append("     </li>       \n");

                }

            } else {
                htmlSb.append("     <li style=\"margin-left: 5px; margin-bottom: 5px;\">      \n");
                htmlSb.append("         <img src=\"/images/2013/common/msw_nb1r.gif\" alt=\"1\" style=\"vertical-align: sub;\" /> <a href=\"javascript:poplar_search('����');\">����</a>        \n");
                htmlSb.append("     </li>       \n");
                htmlSb.append("     <li style=\"margin-left: 5px; margin-bottom: 5px;\">      \n");
                htmlSb.append("         <img src=\"/images/2013/common/msw_nb2r.gif\" alt=\"2\" style=\"vertical-align: sub;\" /> <a href=\"javascript:poplar_search('��ȭ');\">��ȭ</a>        \n");
                htmlSb.append("     </li>       \n");
                htmlSb.append("     <li style=\"margin-left: 5px; margin-bottom: 5px;\">      \n");
                htmlSb.append("         <img src=\"/images/2013/common/msw_nb3r.gif\" alt=\"3\" style=\"vertical-align: sub;\" /> <a href=\"javascript:poplar_search('���');\">���</a>        \n");
                htmlSb.append("     </li>       \n");
                htmlSb.append("  \n");

            }

            htmlSb.append("  \n");
            htmlSb.append(" </ul>       \n");
            htmlSb.append(" <h2><img src=\"/images/2013/common/msw_bgdown.gif\" alt=\"\" style=\"margin-top: 0px;\" /></h2>      \n");
            htmlSb.append(" </div>        \n");

            String upDir = conf.getProperty("dir.home");

            upDir += "learn\\user\\2013\\portal\\include\\";

            CreateHTMLFile html = new CreateHTMLFile(upDir, "popular_search.html", htmlSb.toString());
            html.create();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("bannerList()\r\n" + ex.getMessage());
        }
    }
    
    /**
     * Ȩ�������� ����� �α� �˻��� html ������ �����Ѵ�.
     * 
     * @param bannerList
     * @throws Exception
     */
    private void createSearchWordHTMLFile(ArrayList<DataBox> keywordlist, RequestBox box) throws Exception {
        ConfigSet conf = new ConfigSet();
        StringBuffer htmlSb = new StringBuffer();
        DataBox dbox = null;

        try {

            htmlSb.append(" <script type=\"text/javascript\">     \n");
            htmlSb.append("     function poplar_search(t){      \n");
            htmlSb.append("         $(\"#topWarp_searchText\").val(t);        \n");
            htmlSb.append("         totalSubjSearch1();     \n");
            htmlSb.append("     }       \n");
            htmlSb.append("         \n");
            htmlSb.append(" </script>       \n");
            htmlSb.append(" <div style=\"margin-bottom: 55px;\"></div>        \n");
            htmlSb.append(" <div style=\"padding-left: 3px;\">        \n");
            htmlSb.append(" <h2><img src=\"/images/2013/common/msw_title2.gif\" alt=\"�α�˻���\" /></h2>       \n");
            htmlSb.append(" <ul style=\"background-image: url('/images/2013/common/msw_bg.gif')\">        \n");
            htmlSb.append("     <li style=\"margin-top: -5px;\">&nbsp;</li>       \n");
            htmlSb.append("  \n");

            if (keywordlist.size() > 0) {

                for (int i = 0; i < keywordlist.size(); i++) {
                    dbox = (DataBox) keywordlist.get(i);

                    htmlSb.append("     <li style=\"margin-left: 5px; padding-bottom: 5px;\">     \n");
                    htmlSb.append("         <img src=\"/images/2013/common/msw_nb").append(i + 1).append("r.gif\" alt=\"").append(i + 1).append("\" style=\"vertical-align: middle;\" />        \n");
                    htmlSb.append("         <a href=\"javascript:poplar_search('").append(dbox.getString("d_keyword")).append("');\"><strong style=\"color: e2e2e2\">").append(dbox.getString("d_keyword")).append("</strong></a> \n");
                    htmlSb.append("     </li>       \n");

                }

            } else {
                htmlSb.append("     <li style=\"margin-left: 5px; margin-bottom: 5px;\">      \n");
                htmlSb.append("         <img src=\"/images/2013/common/msw_nb1r.gif\" alt=\"1\" style=\"vertical-align: sub;\" /> <a href=\"javascript:poplar_search('����');\">����</a>        \n");
                htmlSb.append("     </li>       \n");
                htmlSb.append("     <li style=\"margin-left: 5px; margin-bottom: 5px;\">      \n");
                htmlSb.append("         <img src=\"/images/2013/common/msw_nb2r.gif\" alt=\"2\" style=\"vertical-align: sub;\" /> <a href=\"javascript:poplar_search('��ȭ');\">��ȭ</a>        \n");
                htmlSb.append("     </li>       \n");
                htmlSb.append("     <li style=\"margin-left: 5px; margin-bottom: 5px;\">      \n");
                htmlSb.append("         <img src=\"/images/2013/common/msw_nb3r.gif\" alt=\"3\" style=\"vertical-align: sub;\" /> <a href=\"javascript:poplar_search('���');\">���</a>        \n");
                htmlSb.append("     </li>       \n");
                htmlSb.append("  \n");

            }

            htmlSb.append("  \n");
            htmlSb.append(" </ul>       \n");
            htmlSb.append(" <h2><img src=\"/images/2013/common/msw_bgdown.gif\" alt=\"\" style=\"margin-top: 0px;\" /></h2>      \n");
            htmlSb.append(" </div>        \n");

            String upDir = conf.getProperty("dir.home");

            upDir += "learn\\user\\2013\\portal\\include\\";

            CreateHTMLFile html = new CreateHTMLFile(upDir, "popular_search.html", htmlSb.toString());
            html.create();

            // html ���Ϸ� �����ϴ� ������ �н��ڵ��� Ȩ�������� ������ �� ���ϸ� �ּ�ȭ�ϰ� ������ �ε� �ӵ��� �ø��� �����̴�.
            // �㳪 �����Ǵ� ��θ� NAS�� �����ϴ� ��쿡�� ������ ������ WAS1 �������� ���� NAS���� ��Ʈ��ũ����̺� ������
            // ����� ������ �߻��Ѵ�. ���� html ������ �ش� WAS ������ �����Ѵ�. ��δ� ���� jsp ������ �ִ� ���̴�.
            // �׷��� �� ��쿡�� �ش� WAS �������� ������ �����ǰ� ����ȭ �Ǿ� �ִ� �ٸ� WAS���� ������ �ʴ´�. �̿� ���� ������
            // WAS�� html ������ ������ �� �ִ� �������� �ۼ��ϰ�, �ٸ� WAS ������ �ִ� �ּҸ� URLConnection�� �̿��Ͽ� ȣ���Ѵ�.
            
            String hostName = box.getString("hostip").toLowerCase();
            String targetURL = (hostName.equals("lms_web-was1")) ? "http://218.232.93.19/" : "http://218.232.93.18/";
            
            // �Ʒ��� �׽�Ʈ URL
            // targetURL = (hostName.equals("localhost") || hostName.equals("127.0.0.1")) ? "http://172.16.80.76/" : "http://172.16.80.75/";

            OutputStreamWriter wr = null;
            BufferedReader rd = null;
            URL url = new URL(targetURL + "servlet/controller.homepage.SearchWordAdminServlet?processType=createHTMLByRemote");

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
            throw new Exception("createSearchWordHTMLFile()\r\n" + e.getMessage());
        }

    }
}
