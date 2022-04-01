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
 * 검색어 로그 정보를 조회한다.
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

            if (processType.equals("selectSearchWordList")) { // 기간단위 검색어 로그 조회
                this.performSelectSearchWordList(request, response, box, out);

            } else if (processType.equals("insertKeywordList")) { // 설정한 키워드 목록 저장
                this.performInsertKeywordList(request, response, box, out);

            } else if (processType.equals("createHTMLByRemote")) { // 설정한 키워드 목록 저장
                this.performCreateHTMLByRemote(request, response, box, out);

            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 기간내 검색어 순위를 출력한다.
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
     * 설정한 키워드 목록 저장
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
     * 이중화되어 있는 다른 WAS에서 호출하는 메서드이다.
     * 홈페이지에 노출될 인기 검색어 html 파일을 생성한다.
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
            htmlSb.append(" <h2><img src=\"/images/2013/common/msw_title2.gif\" alt=\"인기검색어\" /></h2>       \n");
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
                htmlSb.append("         <img src=\"/images/2013/common/msw_nb1r.gif\" alt=\"1\" style=\"vertical-align: sub;\" /> <a href=\"javascript:poplar_search('게임');\">게임</a>        \n");
                htmlSb.append("     </li>       \n");
                htmlSb.append("     <li style=\"margin-left: 5px; margin-bottom: 5px;\">      \n");
                htmlSb.append("         <img src=\"/images/2013/common/msw_nb2r.gif\" alt=\"2\" style=\"vertical-align: sub;\" /> <a href=\"javascript:poplar_search('문화');\">문화</a>        \n");
                htmlSb.append("     </li>       \n");
                htmlSb.append("     <li style=\"margin-left: 5px; margin-bottom: 5px;\">      \n");
                htmlSb.append("         <img src=\"/images/2013/common/msw_nb3r.gif\" alt=\"3\" style=\"vertical-align: sub;\" /> <a href=\"javascript:poplar_search('방송');\">방송</a>        \n");
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
     * 홈페이지에 노출될 인기 검색어 html 파일을 생성한다.
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
            htmlSb.append(" <h2><img src=\"/images/2013/common/msw_title2.gif\" alt=\"인기검색어\" /></h2>       \n");
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
                htmlSb.append("         <img src=\"/images/2013/common/msw_nb1r.gif\" alt=\"1\" style=\"vertical-align: sub;\" /> <a href=\"javascript:poplar_search('게임');\">게임</a>        \n");
                htmlSb.append("     </li>       \n");
                htmlSb.append("     <li style=\"margin-left: 5px; margin-bottom: 5px;\">      \n");
                htmlSb.append("         <img src=\"/images/2013/common/msw_nb2r.gif\" alt=\"2\" style=\"vertical-align: sub;\" /> <a href=\"javascript:poplar_search('문화');\">문화</a>        \n");
                htmlSb.append("     </li>       \n");
                htmlSb.append("     <li style=\"margin-left: 5px; margin-bottom: 5px;\">      \n");
                htmlSb.append("         <img src=\"/images/2013/common/msw_nb3r.gif\" alt=\"3\" style=\"vertical-align: sub;\" /> <a href=\"javascript:poplar_search('방송');\">방송</a>        \n");
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

            // html 파일로 생성하는 이유는 학습자들이 홈페이지에 접속할 때 부하를 최소화하고 페이지 로딩 속도를 올리기 위함이다.
            // 허나 생성되는 경로를 NAS로 지정하는 경우에는 문제가 없지만 WAS1 서버에서 종종 NAS와의 네트워크드라이브 연결이
            // 끊기는 현상이 발생한다. 따라서 html 파일을 해당 WAS 서버에 생성한다. 경로는 기존 jsp 파일이 있던 곳이다.
            // 그런데 이 경우에는 해당 WAS 서버에만 파일이 생성되고 이중화 되어 있는 다른 WAS에는 생기지 않는다. 이에 따라 각각의
            // WAS에 html 파일을 생성할 수 있는 페이지를 작성하고, 다른 WAS 서버에 있는 주소를 URLConnection을 이용하여 호출한다.
            
            String hostName = box.getString("hostip").toLowerCase();
            String targetURL = (hostName.equals("lms_web-was1")) ? "http://218.232.93.19/" : "http://218.232.93.18/";
            
            // 아래는 테스트 URL
            // targetURL = (hostName.equals("localhost") || hostName.equals("127.0.0.1")) ? "http://172.16.80.76/" : "http://172.16.80.75/";

            OutputStreamWriter wr = null;
            BufferedReader rd = null;
            URL url = new URL(targetURL + "servlet/controller.homepage.SearchWordAdminServlet?processType=createHTMLByRemote");

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
            throw new Exception("createSearchWordHTMLFile()\r\n" + e.getMessage());
        }

    }
}
