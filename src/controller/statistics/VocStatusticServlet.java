//**********************************************************
//1. 제      목:  Q/A 통계 제어하는 서블릿
//2. 프로그램명 : VocStatusticServlet
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 1.0
//6. 작      성:
//7. 수      정:
//**********************************************************

package controller.statistics;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.QnaAdminBean;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.statistics.UserCountBean;

@WebServlet("/servlet/controller.statistics.VocStatusticServlet")
public class VocStatusticServlet extends javax.servlet.http.HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -7638715723104468051L;

    /**
     * DoGet Pass get requests through to PerformTask
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    @SuppressWarnings("unchecked")
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            ///////////////////////////////////////////////////////////////////
            /*
             * if (!AdminUtil.getInstance().checkRWRight("VocStatusticServlet",
             * v_process, out, box)) { return; }
             */
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
            ///////////////////////////////////////////////////////////////////

            if (v_process.equals("selectSubQna")) { //  과정 Q/A 통계
                //this.performSubjQnaCnt(request, response, box, out);
                this.performVocStat(request, response, box, out);
            } else if (v_process.equals("selectVocAllStat")) { //  전체보기
                //this.performSubjQnaCnt(request, response, box, out);
                this.performVocAllStat(request, response, box, out);
            } else if (v_process.equals("select1to1Stat")) { //1:1 New
                this.performSelect1to1Stat(request, response, box, out);
            } else if (v_process.equals("selectQnaStat")) { //홈페이지Q/A
                this.performSelectQnaStat(request, response, box, out);
            } else if (v_process.equals("selectSubjQnaStat")) { // 과정질문방
                this.performSelectSubjQnaStat(request, response, box, out);
            } else if (v_process.equals("selectSubjQnaBbsList")) { // 과정질문방
                this.performSelectSubjQnaBbsList(request, response, box, out);
            } else if (v_process.equals("selectSubjQnaTutorList")) { // 과정질문방 튜터 갯수
                this.performSelectSubjQnaTutorList(request, response, box, out);
            } else if (v_process.equals("selectKnowledgeStat")) { // 과정질문방
                this.performSelectKnowledgeStat(request, response, box, out);
            } else if (v_process.equals("selectKnowledgeList")) { // 과정질문방
                this.performSelectKnowledgeList(request, response, box, out);
            }

            else if (v_process.equals("selectQna")) { //  묻고 답하기
                this.performQnaCnt(request, response, box, out);
            } else if (v_process.equals("selectVocCnt")) { //VOC
                this.performSelectVocCnt(request, response, box, out);
            } else if (v_process.equals("selectOnetoOne")) { //1:1
                this.performSelectOneCnt(request, response, box, out);
            } else if (v_process.equals("selectFactoryCnt")) { //연령별 회원등록현황 페이지로 이동할 때
                this.performSelectFactoryCnt(request, response, box, out);
            } else if (v_process.equals("selectgrid")) { //JqGrid Sample
                this.performGridXml(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 게시판 통계
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performVocStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_VocEntryListNew_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_VocEntryListNew_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * 전체보기 New
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performVocAllStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/statistics/za_Vocjqgrid1_L.jsp";
            QnaAdminBean bean = new QnaAdminBean();
            ArrayList<DataBox> list = bean.selectVocListAll(box);
            request.setAttribute("selectList", list);

            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/statistics/za_excel.jsp";//필수
                box.put("title", "월별  신규회원 가입");//엑셀 제목
                box.put("tname", "연도월|전체|남자|여자|미확인");//컬럼명
                box.put("tcode", "d_indate|d_usercnt|d_man|d_woman|d_johndoe");//데이터이름
                box.put("resultListName", "selectList");//결과 목록
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_UserEntryList_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * 1:1문의
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelect1to1Stat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            UserCountBean bean = new UserCountBean();

            ArrayList<DataBox> list = bean.Select1to1Stat(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_1to1StatList_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_UserEntryListAge_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * 홈페이지 Q/a New
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectQnaStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            UserCountBean bean = new UserCountBean();

            ArrayList<DataBox> list = bean.SelectQnaStat(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_QnaStatList_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_UserEntryListAge_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정질문방 Q/a New
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectSubjQnaStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            UserCountBean bean = new UserCountBean();

            ArrayList<DataBox> list = bean.SelectSubjQnaStat(box);
            request.setAttribute("selectList", list);

            list = bean.SelectSubjQnaList(box);
            request.setAttribute("selectList2", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_SubjQnaStatList_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_UserEntryListAge_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정질문방 Q/a 리스트 Ajax
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectSubjQnaBbsList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            UserCountBean bean = new UserCountBean();

            ArrayList<DataBox> list = bean.SelectSubjQnaBbsList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_SubjQnaBbsList_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_UserEntryListAge_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정질문방 Q/a 리스트 Ajax
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectSubjQnaTutorList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            UserCountBean bean = new UserCountBean();

            ArrayList<DataBox> list = bean.SelectSubjQnaTutorList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_SubjQnaTutorList_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_UserEntryListAge_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * 지식팩토리 게시판 New
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectKnowledgeStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            UserCountBean bean = new UserCountBean();

            ArrayList<DataBox> list = bean.SelectKnowledgeStat(box);
            request.setAttribute("selectList", list);

            list = bean.SelectKnowledgeList(box);
            request.setAttribute("selectList2", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_KnowledgeStatList_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_KnowledgeStatList_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * 지식팩토리 리스트 Ajax
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectKnowledgeList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            UserCountBean bean = new UserCountBean();

            ArrayList<DataBox> list = bean.SelectKnowledgeBbsList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_KnowledgeBbsList_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_KnowledgeBbsList_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정Q/A 등록현황 페이지
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjQnaCnt(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            UserCountBean bean = new UserCountBean();

            ArrayList<DataBox> list = bean.SelectSubjQnaCnt(box);
            request.setAttribute("SubjQnaCnt", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_SubjQnaList_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_SubjQnaList_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * 묻고 답하기 통계
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performQnaCnt(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            UserCountBean bean = new UserCountBean();

            ArrayList<DataBox> list = bean.SelectQnaCnt(box);
            request.setAttribute("QnaCnt", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_QnaList_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_QnaList_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * VOC 현황 페이지
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectVocCnt(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            UserCountBean bean = new UserCountBean();

            ArrayList<DataBox> list = bean.SelectVocCnt(box);
            request.setAttribute("VocCnt", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_VocList_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_VocList_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * 1:1문의
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectOneCnt(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            UserCountBean bean = new UserCountBean();

            ArrayList<DataBox> list = bean.SelectOneCnt(box);
            request.setAttribute("OneCnt", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_OnetoOneList_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_UserEntryListAge_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * 지식팩토리 등록 현황 페이지
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectFactoryCnt(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            UserCountBean bean = new UserCountBean();

            ArrayList<DataBox> list = bean.SelectFactoryCnt(box);
            request.setAttribute("FactoryCnt", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_FactoryCntList_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_FactoryCntList_L.jsp");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

    /**
     * jqgrid xml 생성
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performGridXml(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            QnaAdminBean bean = new QnaAdminBean();
            ArrayList<DataBox> list = bean.selectVocTotalList(box);

            response.setContentType("text/xml;charset=UTF-8");
            //String status = request.getParameter("status");
            String rows = "10";
            // String page = "1";
            int totalPages = 0;
            int totalCount = 15;
            if (totalCount > 0) {
                if (totalCount % Integer.parseInt(rows) == 0) {
                    totalPages = totalCount / Integer.parseInt(rows);
                } else {
                    totalPages = (totalCount / Integer.parseInt(rows)) + 1;
                }

            } else {
                totalPages = 0;
            }
            out.print("<?xml version='1.0' encoding='euc-kr'?>\n");
            out.print("<rows>");
            out.print("<page>1</page>");

            out.print("<total>" + totalPages + "</total>");
            out.print("<records>" + 15 + "</records>");
            int srNo = 1;

            for (int i = 0; i < list.size(); i++) {
                DataBox dbox = (DataBox) list.get(i);
                String userid = dbox.getString("d_inuserid");
                // String title = dbox.getString("d_title").replace("<", "-").replace(">", "-");

                out.print("<row id='" + i + "'>");
                out.print("<cell>" + dbox.getString("d_tabseq") + "</cell>");
                out.print("<cell>" + dbox.getString("d_indate") + "</cell>");
                out.print("<cell>" + userid + "</cell>");
                out.print("<cell>" + dbox.getString("d_name") + "</cell>");
                out.print("<cell><![CDATA[<a href='ViewStd.jsp'>View</a>]]></cell>");
                out.print("<cell>" + dbox.getString("d_title") + "</cell>");
                out.print("</row>");
                srNo++;
            }
            out.print("</rows>");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectMonthDay()\r\n" + ex.getMessage());
        }
    }

}
