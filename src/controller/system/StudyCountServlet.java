//**********************************************************
//  1. ��      ��: ������� �����ϴ� ����
//  2. ���α׷��� : StudyCountServlet.java
//  3. ��      ��: ������� �������� �����Ѵ�
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 7. 7
//  7. ��      ��:
//**********************************************************

package controller.system;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.study.MyClassBean;
import com.credu.system.StudyCountBean;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.system.StudyCountServlet")
public class StudyCountServlet extends javax.servlet.http.HttpServlet {

    /**
    * DoGet
    * Pass get requests through to PerformTask
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }
    @SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
//        MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";
//        int fileupstatus = 0;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            ///////////////////////////////////////////////////////////////////
            // selectMonthDay : ��� ȭ���̹Ƿ� üũ 
/*            if (v_process.equals("selectMonthDay")){
                if (!AdminUtil.getInstance().checkRWRight("StudyCountServlet", v_process, out, box)) {
                    return;
                }
            }*/
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
            ///////////////////////////////////////////////////////////////////

            if(v_process.equals("myActivity")) {          //  Myactivity ������
                this.performSelectActivity(request, response, box, out);
            }else if(v_process.equals("writeLog")) {          //  �н���������� ��϶�
                this.performWriteLog(request, response, box, out);
            }
            else if(v_process.equals("myActivityExcel")){          // ���
                    this.performMyActivityExcel(request, response, box, out);
            }

        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }


    /**
    MY Activity ������
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
	public void performSelectActivity(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            MyClassBean bean = new MyClassBean();

            ArrayList list1 = bean.selectEducationSubjectList(box);            
            request.setAttribute("EducationSubjectList", list1); 


            String v_subj = box.getString("subj");
            String v_year = box.getString("year");
            String v_subjseq = box.getString("subjseq");

            if(v_subj.equals("") && v_year.equals("") && v_subjseq.equals("")){ // ���� ȭ�� �ε��ÿ� ù��° ���� ������ �Է��Ѵ�.
                if(list1 != null && list1.size() > 0) {
                    DataBox dbox    = (DataBox)list1.get(0);
                    box.put("subj",     dbox.getString("d_subj"));
                    box.put("year",     dbox.getString("d_year"));
                    box.put("subjseq",  dbox.getString("d_subjseq"));
                }
            }

            StudyCountBean bean2 = new StudyCountBean();

            // �н��� ���
            ArrayList list2 = bean2.SelectActivityList(box);
            request.setAttribute("selectActivity", list2);

            // �н��ð� ����
            ArrayList list3 = bean2.selectLearningTime(box);
            request.setAttribute("selectLearningTime", list3);

            // �ֱ��н���
            String date  = bean2.selectStudyDate(box);
            request.setAttribute("studyDate", date);

            // ���ǽ� ��������
            String count  = bean2.selectStudyCount(box);
            request.setAttribute("studyCount", count);

            // ���ǽ� ��������
            String step  = bean2.selectStudyStep(box);
            request.setAttribute("studyStep", step);


            // �Խ��� ����
            ArrayList list4 = bean2.selectBoardCnt(box);
            request.setAttribute("selectBoard", list4);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/study/gu_Activity_Study.jsp");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectActivity()\r\n" + ex.getMessage());
        }
    }


    /**
    �н��� ��� ���
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performWriteLog(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            StudyCountBean bean = new StudyCountBean();
            int isOk = bean.writeLog(box);


            String v_msg = "";
            String v_url = "";
//            box.put("p_process", "select");
            //      ���� �� �ش� ����Ʈ �������� ���ư��� ����

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
//                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url, box);
            }
            else {
//                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performWriteLog()\r\n" + ex.getMessage());
        }
    }

    /**
    ���
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performMyActivityExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {

            request.setAttribute("requestbox", box);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/study/zu_Activity_Study_E.jsp");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMyActivityExcel()\r\n" + ex.getMessage());
        }
    }
}

