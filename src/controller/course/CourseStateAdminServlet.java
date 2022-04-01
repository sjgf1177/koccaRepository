//**********************************************************
//  1. ��      ��: ��������� �����ϴ� ����
//  2. ���α׷��� : CourseStateAdminServlet.java
//  3. ��      ��: ��������� �������� �����Ѵ�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 8. 13
//  7. ��      ��:
//**********************************************************

package controller.course;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.course.CourseStateAdminBean;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.research.SulmunQuestionExampleData;
import com.credu.system.AdminUtil;
@WebServlet("/servlet/controller.course.CourseStateAdminServlet")
public class CourseStateAdminServlet extends javax.servlet.http.HttpServlet {

    /**
     * 
     */
    private static final long serialVersionUID = -7250147305085305268L;

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

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkRWRight("CourseStateAdminServlet", v_process, out, box)) {
                return;
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if (v_process.equals("select")) { // ��ȸ�Ҷ�
                this.performSelectList(request, response, box, out);

            } else if (v_process.equals("selectPre")) { // ���� �˻� ��
                this.performSelectPre(request, response, box, out);

            } else if (v_process.equals("CourseStateExcel")) { // ����
                this.performCourseStateExcel(request, response, box, out);

            } else if (v_process.equals("Reporting")) {
                this.performReporting(request, response, box, out);
            }

            // 2009.12.07 �������� �޴� ����
            if (v_process.equals("selectSaleList")) { // ������Ȳ ��ȸ�Ҷ�
                this.performSelectSaleList(request, response, box, out);

            } else if (v_process.equals("selectPreTotalStat")) { // ���հ˻� ���� �˻� ��
                this.performSelectPreTotalStat(request, response, box, out);

            } else if (v_process.equals("selectTotalStat")) { // �󼼺��� ���� �˻� ��
                this.performSelectTotalStat(request, response, box, out);

            } else if (v_process.equals("selectPreGubunStat")) { // �оߺ� ���� �˻� ��
                this.performSelectPreGubunStat(request, response, box, out);

            } else if (v_process.equals("selectGubunStat")) { // �оߺ� ���� �˻� ��
                this.performSelectGubunStat(request, response, box, out);

            } else if (v_process.equals("selectPreSeqStat")) { // ������ ���� �˻� �� new
                this.performSelectPreSeqStat(request, response, box, out);

            } else if (v_process.equals("selectSeqStat")) { // ������ ���� �˻� �� new
                this.performSelectSeqStat(request, response, box, out);

            } else if (v_process.equals("selectPreCourseStat")) { // ������ ���� �˻� �� new
                this.performSelectPreCourseStat(request, response, box, out);

            } else if (v_process.equals("selectCourseStat")) { // ���������� �˻� �� new
                this.performSelectCourseStat(request, response, box, out);

            } else if (v_process.equals("selectPreSatisfyStat")) { // ������ ���� �˻� �� new
                this.performSelectPreSatisfyStat(request, response, box, out);

            } else if (v_process.equals("selectSatisfyStat")) { // ������ �˻� �� new
                this.performSelectSatisfyStat(request, response, box, out);

            } else if (v_process.equals("selectAgeStat")) { // ���ɺ� �˻� �� new
                this.performSelectAgeStat(request, response, box, out);

            } else if (v_process.equals("selectGenderStat")) { // ���� �˻� �� new
                this.performSelectGenderStat(request, response, box, out);

            } else if (v_process.equals("selectLocationStat")) { // ������ �˻� �� new
                this.performSelectLocationStat(request, response, box, out);

            } else if (v_process.equals("selectVocationStat")) { // ������ �˻� �� new
                this.performSelectVocationStat(request, response, box, out);

            }  else if (v_process.equals("selectGroupStat")) { // �����׷캰���� �˻� �� new
                this.performSelectGroupStat(request, response, box, out);

            } else if (v_process.equals("selectSaleListPre")) { // ������Ȳ ���� �˻� ��
                this.performSelectSaleListPre(request, response, box, out);

            } else if (v_process.equals("selectSaleListExcel")) { // ����
                this.performSelectSaleListExcel(request, response, box, out);

            } else if (v_process.equals("selectEnterStudentSaleList")) { // �԰���Ȳ - �������� - �����׺� ��ȸ�Ҷ�
                //this.performSelectEnterStudentSaleList(request, response, box, out);
                performSelectEnterStudentGubunListPre(request, response, box, out);

            } else if (v_process.equals("selectEnterStudentSaleListPre")) { // �԰���Ȳ - �������� - �����׺� ���� �˻� ��
                //this.performSelectEnterStudentSaleListPre(request, response, box, out);
                this.performSelectEnterStudentGubunListPre(request, response, box, out);

            } else if (v_process.equals("selectEnterStudentSaleListExcel")) { // �԰���Ȳ - �������� - �����׺� ����
                this.performSelectEnterStudentSaleListExcel(request, response, box, out);

            } else if (v_process.equals("selectEnterStudentGubunList")) { // �԰���Ȳ - �������� - ȸ�����к�, ����, ���ɴ뺰, �зº�, ��ȭ��������ں�, ��ȭ��������ں�  ��ȸ�Ҷ�
                this.performSelectEnterStudentGubunList(request, response, box, out);

            } else if (v_process.equals("selectEnterStudentGubunListPre")) { // �԰���Ȳ - �������� - ȸ�����к�, ����, ���ɴ뺰, �зº�, ��ȭ��������ں�, ��ȭ��������ں�  ���� �˻� ��
                this.performSelectEnterStudentGubunListPre(request, response, box, out);

            } else if (v_process.equals("selectEnterStudentGubunListExcel")) { // �԰���Ȳ - �������� - ȸ�����к�, ����, ���ɴ뺰, �зº�, ��ȭ��������ں�, ��ȭ��������ں�  ����
                this.performSelectEnterStudentGubunListExcel(request, response, box, out);

            } else if (v_process.equals("selectEnterFieldList")) { // �԰���Ȳ - �оߺ� - �Ϲ�  ��ȸ�Ҷ�
                this.performSelectEnterFieldList(request, response, box, out);

            } else if (v_process.equals("selectEnterFieldListPre")) { // �԰���Ȳ - �оߺ� - �Ϲ�  ���� �˻� ��
                this.performSelectEnterFieldListPre(request, response, box, out);

            } else if (v_process.equals("selectEnterFieldListExcel")) { // �԰���Ȳ - �оߺ� - �Ϲ� ����
                this.performSelectEnterFieldListExcel(request, response, box, out);

            } else if (v_process.equals("selectEnterFieldGubunList")) { // �԰���Ȳ - �оߺ� - �Ϲ�, ȸ�����к�, ����, ���ɴ뺰, �зº�, ��ȭ��������ں�, ��ȭ��������ں� ��ȸ�Ҷ�
                this.performSelectEnterFieldGubunList(request, response, box, out);

            } else if (v_process.equals("selectEnterFieldGubunListPre")) { // �԰���Ȳ - �оߺ� - �Ϲ�, ȸ�����к�, ����, ���ɴ뺰, �зº�, ��ȭ��������ں�, ��ȭ��������ں� ���� �˻� ��
                this.performSelectEnterFieldGubunListPre(request, response, box, out);

            } else if (v_process.equals("selectEnterFieldGubunListExcel")) { // �԰���Ȳ - �оߺ� - �Ϲ�, ȸ�����к�, ����, ���ɴ뺰, �зº�, ��ȭ��������ں�, ��ȭ��������ں� ����
                this.performSelectEnterFieldGubunListExcel(request, response, box, out);

            } else if (v_process.equals("selectEnterCourseList")) { // �԰���Ȳ - ������ - �Ϲ� - ��ȸ�Ҷ�
                this.performSelectEnterCourseList(request, response, box, out);

            } else if (v_process.equals("selectEnterCourseListPre")) { // �԰���Ȳ - ������ - �Ϲ� - ���� �˻� ��
                this.performSelectEnterCourseListPre(request, response, box, out);

            } else if (v_process.equals("selectEnterCourseListExcel")) { // �԰���Ȳ - ������ - �Ϲ� - ����
                this.performSelectEnterCourseListExcel(request, response, box, out);

            } else if (v_process.equals("selectEnterCourseGubunList")) { // �԰���Ȳ - ������ - ȸ�����к�, ����, ���ɴ뺰, �зº�, ��ȭ��������ں�, ��ȭ��������ں� ��ȸ�Ҷ�
                this.performSelectEnterCourseGubunList(request, response, box, out);

            } else if (v_process.equals("selectEnterCourseGubunListPre")) { // �԰���Ȳ - ������ - ȸ�����к�, ����, ���ɴ뺰, �зº�, ��ȭ��������ں�, ��ȭ��������ں� ���� �˻� ��
                this.performSelectEnterCourseGubunListPre(request, response, box, out);

            } else if (v_process.equals("selectEnterCourseGubunListExcel")) { // �԰���Ȳ - ������ - ȸ�����к�, ����, ���ɴ뺰, �зº�, ��ȭ��������ں�, ��ȭ��������ں� ����
                this.performSelectEnterCourseGubunListExcel(request, response, box, out);

            } else if (v_process.equals("selectEnterSeqList")) { // �԰���Ȳ - ������ - �Ϲ� ��ȸ�Ҷ�
                this.performSelectEnterSeqList(request, response, box, out);

            } else if (v_process.equals("selectEnterSeqListPre")) { //  �԰���Ȳ - ������ - �Ϲ�  ���� �˻� ��
                this.performSelectEnterSeqListPre(request, response, box, out);

            } else if (v_process.equals("selectEnterSeqListExcel")) { // �԰���Ȳ - ������ - �Ϲ�  ����
                this.performSelectEnterSeqListExcel(request, response, box, out);

            } else if (v_process.equals("selectEnterSeqGubunList")) { // �԰���Ȳ - ������ - ȸ�����к�, ����, ���ɴ뺰, �зº�, ��ȭ��������ں�, ��ȭ��������ں� ��ȸ�Ҷ�
                this.performSelectEnterSeqGubunList(request, response, box, out);

            } else if (v_process.equals("selectEnterSeqGubunListPre")) { //  �԰���Ȳ - ������ - ȸ�����к�, ����, ���ɴ뺰, �зº�, ��ȭ��������ں�, ��ȭ��������ں�  ���� �˻� ��
                this.performSelectEnterSeqGubunListPre(request, response, box, out);

            } else if (v_process.equals("selectEnterSeqGubunListExcel")) { // �԰���Ȳ - ������ - ȸ�����к�, ����, ���ɴ뺰, �зº�, ��ȭ��������ں�, ��ȭ��������ں�  ����
                this.performSelectEnterSeqGubunListExcel(request, response, box, out);

            } else if (v_process.equals("selectPassAllList")) { // ������Ȳ ��ü ��ȸ�Ҷ�
                this.performSelectPassAllList(request, response, box, out);

            } else if (v_process.equals("selectPassAllListPre")) { // ������Ȳ ��ü ���� �˻� ��
                this.performSelectPassAllListPre(request, response, box, out);

            } else if (v_process.equals("selectPassAllListExcel")) { // ������Ȳ ��ü ����
                this.performSelectPassAllListExcel(request, response, box, out);

            } else if (v_process.equals("selectPassCourseList")) { // ������Ȳ ������ ��ȸ�Ҷ�
                this.performSelectPassCourseList(request, response, box, out);

            } else if (v_process.equals("selectPassCourseListPre")) { // ������Ȳ ������ ���� �˻� ��
                this.performSelectPassCourseListPre(request, response, box, out);

            } else if (v_process.equals("selectPassCourseListExcel")) { // ����Ȳ ������ ����
                this.performSelectPassCourseListExcel(request, response, box, out);

            } else if (v_process.equals("selectPassPersonList")) { // ��ȸ�Ҷ�
                this.performSelectPassPersonList(request, response, box, out);

            } else if (v_process.equals("selectPassPersonListPre")) { // ���� �˻� ��
                this.performSelectPassPersonListPre(request, response, box, out);

            } else if (v_process.equals("selectPassPersonListExcel")) { // ����
                this.performSelectPassPersonListExcel(request, response, box, out);

            } else if (v_process.equals("selectStudyList")) { // �н���Ȳ ��ȸ�Ҷ�
                this.performSelectStudyList(request, response, box, out);

            } else if (v_process.equals("selectStudyListPre")) { //  �н���Ȳ ���� �˻� ��
                this.performSelectStudyListPre(request, response, box, out);

            } else if (v_process.equals("selectStudyListExcel")) { // ����
                this.performSelectStudyListExcel(request, response, box, out);

            } else if (v_process.equals("selectPreSulmunList")) { // ��ȸ�Ҷ�
                this.performSelectPreSulmunList(request, response, box, out);

            } else if (v_process.equals("selectPreSulmunListPre")) { // ���� �˻� ��
                this.performSelectPreSulmunListPre(request, response, box, out);

            } else if (v_process.equals("selectPreSulmunListExcel")) { // ����
                this.performSelectPreSulmunListExcel(request, response, box, out);

            } else if (v_process.equals("selectSatiSulmunList")) { // ��ȸ�Ҷ�
                this.performSelectSatiSulmunList(request, response, box, out);

            } else if (v_process.equals("selectSatiSulmunListPre")) { // ���� �˻� ��
                this.performSelectSatiSulmunListPre(request, response, box, out);

            } else if (v_process.equals("selectSatiSulmunListExcel")) { // ����
                this.performSelectSatiSulmunListExcel(request, response, box, out);

            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * ������Ȳ ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPreTotalStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            //CourseStateAdminBean bean = new CourseStateAdminBean();
            //ArrayList list = bean.selectTotalList(box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_TotalStat_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_TotalStat_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������Ȳ ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectTotalStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            String v_return_url = "/learn/admin/statistics/za_TotalStatajax_L.jsp";

            CourseStateAdminBean bean = new CourseStateAdminBean();
            ArrayList<DataBox> list = bean.selectTotalList(box);

            //ArrayList list = new ArrayList();
            request.setAttribute("selectList", list);

            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/off/za_excel.jsp";//�ʼ�
                box.put("title", "���� �˻�");//���� ����
                box.put("tname", "�о�|����|������|ID|����|�̸�|������|����");//�÷���
                box.put("tcode", "d_areaname|d_gubun|d_grseqnm|d_userid|d_sex|d_name|d_subjnm|d_graduate");//�������̸�
                box.put("resultListName", "selectList");//��� ���
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_TotalStat_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * �оߺ� ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPreGubunStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            //CourseStateAdminBean bean = new CourseStateAdminBean();
            //ArrayList list = bean.selectTotalList(box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_GubunStat_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_GubunStat_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * �оߺ� ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectGubunStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            CourseStateAdminBean bean = new CourseStateAdminBean();
            ArrayList<DataBox> list = bean.selectCategorylList(box);

            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_GubunStatAjax_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_GubunStatAjax_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������ ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPreSeqStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_SeqStat_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_GubunStat_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������ ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectSeqStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            String v_return_url = "/learn/admin/statistics/za_SeqStatAjax_L.jsp";

            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectSeqList(box);
            request.setAttribute("selectList", list);

            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/statistics/za_excel.jsp";//�ʼ�
                box.put("title", "���� �˻�");//���� ����
                box.put("tname", "����|�з�|�о�|�����|�����ο�|�����ο�|������|������");//�÷���
                box.put("tcode", "d_grseqnm|d_cate|d_areaname|d_subj_cnt|d_user_cnt|d_grad_cnt|d_grad_rate|d_sul_rate");//�������̸�
                box.put("resultListName", "selectList");//��� ���
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_GubunStatAjax_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������ ����Ʈ(�˻���) new
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPreCourseStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // String v_return_url = "/learn/admin/statistics/za_CourseStat_L.jsp";

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseStat_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseStat_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������ ����Ʈ(�˻���) new
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectCourseStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/statistics/za_CourseStatAjax_L.jsp";

            CourseStateAdminBean bean = new CourseStateAdminBean();
            ArrayList<DataBox> list = bean.selectCourseList(box);

            request.setAttribute("selectList", list);

            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/statistics/za_excel.jsp";//�ʼ�
                box.put("title", "������ �˻�");//���� ����
                box.put("tname", "�о�|������|�����ο�|����|�̼���|������");//�÷���
                box.put("tcode", "d_area|d_subjnm|d_user_cnt|d_grad_cnt|d_ngrad_cnt|d_sul_rate");//�������̸�
                box.put("resultListName", "selectList");//��� ���
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CousreStatAjax_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������ ���(�˻���) new
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPreSatisfyStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_SatisfyStat_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_SatisfyStat_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������ ���(�˻���) new
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectSatisfyStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            String v_return_url = "/learn/admin/statistics/za_SatisfyStatAjax_L.jsp";

            CourseStateAdminBean bean = new CourseStateAdminBean();
            ArrayList<DataBox> list = bean.selectSatisfyList(box);

            request.setAttribute("selectList", list);

            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/statistics/za_excel.jsp";//�ʼ�
                box.put("title", "������ �˻�");//���� ����
                box.put("tname", "�о�|������|�����ο�|�����ο�|������");//�÷���
                box.put("tcode", "d_area|d_subjnm|d_user_cnt|d_sul_cnt|d_sul_rate");//�������̸�
                box.put("resultListName", "selectList");//��� ���
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_SatisfyStatAjax_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���ɺ� ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectAgeStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            String v_return_url = "/learn/admin/statistics/za_AgeStatAjax_L.jsp";

            CourseStateAdminBean bean = new CourseStateAdminBean();
            ArrayList<DataBox> list = bean.selectAgeList(box);
            request.setAttribute("selectList", list);

            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/statistics/za_excel.jsp";//�ʼ�
                box.put("title", "���ɺ� �˻�");//���� ����
                box.put("tname", "������|�з�|�о�|��ü|10��|20��|30��|40��|50��|60��|70��|��Ȯ��");//�÷���
                box.put("tcode", "d_grseqnm|d_areaname|d_cate|d_tot|d_teens|d_twenty|d_thirty|d_fourty|d_fifty|d_sixty|d_seventy|d_johndoe");//�������̸�
                box.put("resultListName", "selectList");//��� ���
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_AgeStatAjax_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * ���� ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectGenderStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            String v_return_url = "/learn/admin/statistics/za_GenderStatAjax_L.jsp";

            CourseStateAdminBean bean = new CourseStateAdminBean();
            ArrayList<DataBox> list = bean.selectGenderList(box);

            request.setAttribute("selectList", list);

            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/statistics/za_excel.jsp";//�ʼ�
                box.put("title", "���� �˻�");//���� ����
                box.put("tname", "������|�з�|�о�|��ü|����|����|��Ȯ��");//�÷���
                box.put("tcode", "d_grseqnm|d_areaname|d_cate|d_tot|d_man|d_woman|d_johndoe");//�������̸�
                box.put("resultListName", "selectList");//��� ���
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_GenderStatAjax_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������ ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectLocationStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/statistics/za_LocationStatAjax_L.jsp";

            CourseStateAdminBean bean = new CourseStateAdminBean();
            ArrayList<DataBox> list = bean.selectLocationList(box);

            request.setAttribute("selectList", list);

            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/statistics/za_excel.jsp";//�ʼ�
                box.put("title", "���� �˻�"); //���� ����
                box.put("tname", "������|�з�|�о�|��ü|����|���|�λ�|��õ|�뱸|����|����|����|����|���|�泲|����|���|�泲|���|����|��Ÿ ");//�÷���
                box.put("tcode", "d_grseqnm|d_areaname|d_cate|d_tot|d_seoul|d_kyunggi|d_busan|d_incheon|d_daegu|d_daejeon|d_kwangju|d_jeonbook|d_jeonnam|d_chungbook|d_choongnam|d_gangwon|d_gyungbook|d_gyungnam|d_woolsan|d_jejoo|d_etc_cnt");//�������̸�
                box.put("resultListName", "selectList");//��� ���
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_LocationStatAjax_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������ ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectVocationStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/statistics/za_VocationStatAjax_L.jsp";
            CourseStateAdminBean bean = new CourseStateAdminBean();
            ArrayList<DataBox> list = bean.selectVocationList(box);

            request.setAttribute("selectList", list);

            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/statistics/za_excel.jsp";//�ʼ�
                box.put("title", "������ �˻�"); //���� ����
                box.put("tname", "������|�з�|�о�|��ü|����л�|���л�|������|�������|��������|���������|ȸ���|�ֺ�|����|������|�Ƿ���|������|�����|������|������|ü����|����|����|�ڿ���|��Ÿ  ");//�÷���
                box.put("tcode",
                        "d_grseqnm|d_areaname|d_cate|d_tot|d_high|d_univ|d_country|d_gonggong|d_freelancer|d_preoffice|d_office|d_jubu|d_miltiary|d_univoffice|d_hospital|d_lawyer|d_broad|d_jongkyo|d_entertain|d_sports|d_proffesor|d_lecter|d_owner|d_etc");//�������̸�
                box.put("resultListName", "selectList");//��� ���
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_LocationStatAjax_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectListCourseState(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_CourseState_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/course/za_CourseState_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_CourseState_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/course/za_CourseState_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * ��������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performCourseStateExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectListCourseState(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_CourseState_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * ����Ʈ����
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performReporting(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/course/za_Reporting_L.jsp");
            rd.forward(request, response);

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performReporting()\r\n" + ex.getMessage());
        }
    }

    //**************************************************************
    //2009.12.07 ����� ��� ����
    //**************************************************************//
    /**
     * ������Ȳ ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectSaleList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectSaleList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseSale_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseSale_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSaleList()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������Ȳ ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectSaleListPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseSale_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseSale_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������Ȳ ��������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectSaleListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectSaleList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseSale_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSaleListExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * �԰���Ȳ - �������� - �����׺� ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterStudentSaleList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectEnterStudentSaleList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterStudentSale_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseEnterStudentSale_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterStudentSaleList()\r\n" + ex.getMessage());
        }
    }

    /**
     * �԰���Ȳ - �������� - �����׺� ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterStudentSaleListPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterStudentSale_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseEnterStudentSale_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterStudentSaleListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * �԰���Ȳ - �������� - �����׺� ��������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterStudentSaleListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectEnterStudentSaleList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterStudentSale_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterStudentSaleListExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * �԰���Ȳ - �������� - ȸ�����к�, ����, ���ɴ뺰, �зº�, ��ȭ��������ں�, ��ȭ��������ں� ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterStudentGubunList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectEnterStudentGubunList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterStudentGubun_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseEnterStudentGubun_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterStudentGubunList()\r\n" + ex.getMessage());
        }
    }

    /**
     * �԰���Ȳ - �������� - ȸ�����к�, ����, ���ɴ뺰, �зº�, ��ȭ��������ں�, ��ȭ��������ں� ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterStudentGubunListPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterStudentGubun_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseEnterStudentGubun_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterStudentGubunListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * �԰���Ȳ - �������� - ȸ�����к�, ����, ���ɴ뺰, �зº�, ��ȭ��������ں�, ��ȭ��������ں� ��������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterStudentGubunListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectEnterStudentGubunList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterStudentGubun_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterStudentGubunListExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * �԰���Ȳ - �оߺ� - �Ϲ� ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterFieldList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectEnterFieldList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterField_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseEnterField_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterFieldList()\r\n" + ex.getMessage());
        }
    }

    /**
     * �԰���Ȳ - �оߺ� - �Ϲ� ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterFieldListPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterField_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseEnterField_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterFieldListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * �԰���Ȳ - �оߺ� - �Ϲ� ��������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterFieldListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectEnterFieldList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterField_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterFieldListExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * �԰���Ȳ - �оߺ� - ȸ�����к�, ����, ���ɴ뺰, �зº�, ��ȭ��������ں�, ��ȭ��������ں� ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterFieldGubunList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectEnterFieldGubunList(box);
            request.setAttribute("selectList", list);

            ArrayList<DataBox> list2 = bean.selectGubunList(box);
            request.setAttribute("selectGubunList", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterFieldGubun_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseEnterFieldGubun_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterFieldGubunList()\r\n" + ex.getMessage());
        }
    }

    /**
     * �԰���Ȳ - �оߺ� - �Ϲ�, ȸ�����к�, ����, ���ɴ뺰, �зº�, ��ȭ��������ں�, ��ȭ��������ں� ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterFieldGubunListPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ArrayList<DataBox> list2 = bean.selectGubunList(box);
            request.setAttribute("selectGubunList", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterFieldGubun_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseEnterFieldGubun_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterFieldGubunListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * �԰���Ȳ - �оߺ� - �Ϲ�, ȸ�����к�, ����, ���ɴ뺰, �зº�, ��ȭ��������ں�, ��ȭ��������ں� ��������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterFieldGubunListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectEnterFieldGubunList(box);
            request.setAttribute("selectList", list);

            ArrayList<DataBox> list2 = bean.selectGubunList(box);
            request.setAttribute("selectGubunList", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterFieldGubun_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterFieldGubunListExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * �԰���Ȳ - ������ - �Ϲ� ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterCourseList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectEnterCourseList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterCourse_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseEnterCourse_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterCourseList()\r\n" + ex.getMessage());
        }
    }

    /**
     * �԰���Ȳ - ������ - �Ϲ� ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterCourseListPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterCourse_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseEnterCourse_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterCourseListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * �԰���Ȳ - ������ - �Ϲ� ��������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterCourseListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectEnterCourseList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterCourse_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterCourseListExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * �԰���Ȳ - ������ - ȸ�����к�, ����, ���ɴ뺰, �зº�, ��ȭ��������ں�, ��ȭ��������ں� ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterCourseGubunList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectEnterCourseGubunList(box);
            request.setAttribute("selectList", list);

            ArrayList<DataBox> list2 = bean.selectGubunList(box);
            request.setAttribute("selectGubunList", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterCourseGubun_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseEnterCourseGubun_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterCourseGubunList()\r\n" + ex.getMessage());
        }
    }

    /**
     * �԰���Ȳ - ������ - �Ϲ�, ȸ�����к�, ����, ���ɴ뺰, �зº�, ��ȭ��������ں�, ��ȭ��������ں� ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterCourseGubunListPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ArrayList<DataBox> list2 = bean.selectGubunList(box);
            request.setAttribute("selectGubunList", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterCourseGubun_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseEnterCourseGubun_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterCourseGubunListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * �԰���Ȳ - ������ - �Ϲ�, ȸ�����к�, ����, ���ɴ뺰, �зº�, ��ȭ��������ں�, ��ȭ��������ں� ��������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterCourseGubunListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectEnterCourseGubunList(box);
            request.setAttribute("selectList", list);

            ArrayList<DataBox> list2 = bean.selectGubunList(box);
            request.setAttribute("selectGubunList", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterCourseGubun_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterCourseGubunListExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * �԰���Ȳ - ������ - �Ϲ� ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterSeqList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectEnterSeqList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterSeq_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseEnterSeq_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterSeqList()\r\n" + ex.getMessage());
        }
    }

    /**
     * �԰���Ȳ - ������ - �Ϲ� ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterSeqListPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterSeq_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseEnterSeq_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterSeqListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * �԰���Ȳ - ������ - �Ϲ� ��������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterSeqListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectEnterSeqList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterSeq_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterSeqListExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * �԰���Ȳ - ������ - ȸ�����к�, ����, ���ɴ뺰, �зº�, ��ȭ��������ں�, ��ȭ��������ں� ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterSeqGubunList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectEnterSeqGubunList(box);
            request.setAttribute("selectList", list);

            ArrayList<DataBox> list2 = bean.selectGubunList(box);
            request.setAttribute("selectGubunList", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterSeqGubun_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseEnterSeqGubun_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterSeqGubunList()\r\n" + ex.getMessage());
        }
    }

    /**
     * �԰���Ȳ - ������ - �Ϲ�, ȸ�����к�, ����, ���ɴ뺰, �зº�, ��ȭ��������ں�, ��ȭ��������ں� ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterSeqGubunListPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ArrayList<DataBox> list2 = bean.selectGubunList(box);
            request.setAttribute("selectGubunList", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterSeqGubun_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseEnterSeqGubun_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterSeqGubunListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * �԰���Ȳ - ������ - �Ϲ�, ȸ�����к�, ����, ���ɴ뺰, �зº�, ��ȭ��������ں�, ��ȭ��������ں� ��������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectEnterSeqGubunListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectEnterSeqGubunList(box);
            request.setAttribute("selectList", list);

            ArrayList<DataBox> list2 = bean.selectGubunList(box);
            request.setAttribute("selectGubunList", list2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseEnterSeqGubun_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectEnterSeqGubunListExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������Ȳ - ��ü ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPassAllList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectPassAllList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CoursePassAll_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CoursePassAll_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectPassAllList()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������Ȳ - ��ü ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPassAllListPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CoursePassAll_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CoursePassAll_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectPassAllListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������Ȳ - ��ü ��������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPassAllListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectPassAllList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CoursePassAll_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectPassAllListExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������Ȳ - ������ ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPassCourseList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectPassCourseList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CoursePassCourse_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CoursePassCourse_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectPassCourseList()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������Ȳ - ������ ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPassCourseListPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CoursePassCourse_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CoursePassCourse_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectPassCourseListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������Ȳ - ������ ��������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPassCourseListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectPassCourseList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CoursePassCourse_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectPassCourseListExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������Ȳ - ���κ� ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPassPersonList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectPassPersonList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CoursePassPerson_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CoursePassPerson_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectPassPersonList()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������Ȳ - ���κ� ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPassPersonListPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CoursePassPerson_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CoursePassPerson_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectPassPersonListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������Ȳ - ���κ� ��������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPassPersonListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectPassPersonList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CoursePassPerson_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectPassPersonListExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * �н���Ȳ ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectStudyList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectStudyList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseStudy_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseStudy_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectStudyList()\r\n" + ex.getMessage());
        }
    }

    /**
     * �н���Ȳ ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectStudyListPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);
            
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseStudy_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseStudy_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectStudyListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * �н���Ȳ ��������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectStudyListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectStudyList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseStudy_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectStudyListExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������ �������� ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPreSulmunList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<SulmunQuestionExampleData> list = bean.selectPreSulmunList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CoursePreSulmun_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CoursePreSulmun_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectStudyList()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������ �������� ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPreSulmunListPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CoursePreSulmun_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CoursePreSulmun_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectStudyListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������ �������� ��������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectPreSulmunListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<SulmunQuestionExampleData> list = bean.selectPreSulmunList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CoursePreSulmun_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectStudyListExcel()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������ ������ ���� ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectSatiSulmunList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectSatiSulmunList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseSatiSulmun_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseSatiSulmun_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSatiSulmunList()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������ ������ ���� ����Ʈ(�˻���)
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectSatiSulmunListPre(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            // ArrayList list = new ArrayList();
            // request.setAttribute("selectList", list);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseSatiSulmun_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_CourseSatiSulmun_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSatiSulmunListPre()\r\n" + ex.getMessage());
        }
    }

    /**
     * ������ ������ ���� ��������
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectSatiSulmunListExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            CourseStateAdminBean bean = new CourseStateAdminBean();

            ArrayList<DataBox> list = bean.selectSatiSulmunList(box);
            request.setAttribute("selectList", list);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/statistics/za_CourseSatiSulmun_E.jsp");
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSatiSulmunListExcel()\r\n" + ex.getMessage());
        }
    }
    
    /**
     * �����׷캰 ����Ʈ(�˻���) new
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performSelectGroupStat(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/statistics/za_GroupStatAjax_L.jsp";

            CourseStateAdminBean bean = new CourseStateAdminBean();
            ArrayList<DataBox> list = bean.selectGroupList(box);

            request.setAttribute("selectList", list);

            if (box.getBoolean("isExcel")) {
                v_return_url = "/learn/admin/statistics/za_excel.jsp";//�ʼ�
                box.put("title", "�����׷캰 �˻�");//���� ����
                box.put("tname", "�����׷�|�����ο�|����|�̼���|������");//�÷���
                box.put("tcode", "d_grnm|d_user_cnt|d_grad_cnt|d_ngrad_cnt|d_sul_rate");//�������̸�
                box.put("resultListName", "selectList");//��� ���
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/admin/statistics/za_GroupStatAjax_L.jsp");

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectSaleListPre()\r\n" + ex.getMessage());
        }
    }

}