//**********************************************************
//1. ��      ��:  Q/A ��� �����ϴ� ����
//2. ���α׷��� : VocStatusticServlet
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 1.0
//6. ��      ��:
//7. ��      ��:
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

            if (v_process.equals("selectSubQna")) { //  ���� Q/A ���
                //this.performSubjQnaCnt(request, response, box, out);
                this.performVocStat(request, response, box, out);
            } else if (v_process.equals("selectVocAllStat")) { //  ��ü����
                //this.performSubjQnaCnt(request, response, box, out);
                this.performVocAllStat(request, response, box, out);
            } else if (v_process.equals("select1to1Stat")) { //1:1 New
                this.performSelect1to1Stat(request, response, box, out);
            } else if (v_process.equals("selectQnaStat")) { //Ȩ������Q/A
                this.performSelectQnaStat(request, response, box, out);
            } else if (v_process.equals("selectSubjQnaStat")) { // ����������
                this.performSelectSubjQnaStat(request, response, box, out);
            } else if (v_process.equals("selectSubjQnaBbsList")) { // ����������
                this.performSelectSubjQnaBbsList(request, response, box, out);
            } else if (v_process.equals("selectSubjQnaTutorList")) { // ���������� Ʃ�� ����
                this.performSelectSubjQnaTutorList(request, response, box, out);
            } else if (v_process.equals("selectKnowledgeStat")) { // ����������
                this.performSelectKnowledgeStat(request, response, box, out);
            } else if (v_process.equals("selectKnowledgeList")) { // ����������
                this.performSelectKnowledgeList(request, response, box, out);
            }

            else if (v_process.equals("selectQna")) { //  ���� ���ϱ�
                this.performQnaCnt(request, response, box, out);
            } else if (v_process.equals("selectVocCnt")) { //VOC
                this.performSelectVocCnt(request, response, box, out);
            } else if (v_process.equals("selectOnetoOne")) { //1:1
                this.performSelectOneCnt(request, response, box, out);
            } else if (v_process.equals("selectFactoryCnt")) { //���ɺ� ȸ�������Ȳ �������� �̵��� ��
                this.performSelectFactoryCnt(request, response, box, out);
            } else if (v_process.equals("selectgrid")) { //JqGrid Sample
                this.performGridXml(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * �Խ��� ���
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
     * ��ü���� New
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
                v_return_url = "/learn/admin/statistics/za_excel.jsp";//�ʼ�
                box.put("title", "����  �ű�ȸ�� ����");//���� ����
                box.put("tname", "������|��ü|����|����|��Ȯ��");//�÷���
                box.put("tcode", "d_indate|d_usercnt|d_man|d_woman|d_johndoe");//�������̸�
                box.put("resultListName", "selectList");//��� ���
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
     * 1:1����
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
     * Ȩ������ Q/a New
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
     * ���������� Q/a New
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
     * ���������� Q/a ����Ʈ Ajax
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
     * ���������� Q/a ����Ʈ Ajax
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
     * �������丮 �Խ��� New
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
     * �������丮 ����Ʈ Ajax
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
     * ����Q/A �����Ȳ ������
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
     * ���� ���ϱ� ���
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
     * VOC ��Ȳ ������
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
     * 1:1����
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
     * �������丮 ��� ��Ȳ ������
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
     * jqgrid xml ����
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
