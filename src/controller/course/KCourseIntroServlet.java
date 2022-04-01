//**********************************************************
//1. ��      ��: �����Ұ� SERVLET
//2. ���α׷���: KCourseIntroServlet.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: anonymous 2003. 07. 15
//7. ��      ��: 
//                 
//**********************************************************
package controller.course;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.course.CourseIntroBean;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.propose.ProposeCourseBean;
import com.credu.study.KRecommandBean;
import com.credu.system.AdminUtil;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.course.KCourseIntroServlet")
public class KCourseIntroServlet extends HttpServlet implements Serializable {

    /**
    Pass get requests through to PerformTask
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet    
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    /**
    doPost
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet    
    */    
    @SuppressWarnings("unchecked")
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox  box = null;
        String v_process = "";
        
        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);            
            v_process = box.getStringDefault("p_process","SubjectList");

            box.put("p_grgubun","K01");		// ���� ��з��ڵ�

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            ///////////////////////////////////////////////////////////////////
            // �α� check ��ƾ VER 0.2 - 2003.09.9
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
            ///////////////////////////////////////////////////////////////////

            if(v_process.equals("SubjectListAlway")){                      // ��ð�������Ʈ
                    this.performSubjectListAlway(request, response, box, out);
            }
            else if(v_process.equals("SubjectListSometime")){              // ���ð�������Ʈ
                this.performSubjectListSometime(request, response, box, out);
            }
            else if(v_process.equals("SubjectListProf")){                  // ��������������Ʈ
                    this.performSubjectListProf(request, response, box, out);
            }
            else if(v_process.equals("SubjectListWorkshop")){              // ��ũ���������Ʈ
                this.performSubjectListWorkshop(request, response, box, out);
			}
            else if(v_process.equals("SubjectListRecognition")){           // ����������������Ʈ
                this.performSubjectListRecognition(request, response, box, out);
			}
            else if(v_process.equals("SubjectPreviewPage")){               // ���� �̸�����
	            if (!AdminUtil.getInstance().checkLogin(out, box)) {
	                return; 
	            }
                this.performSubjectPreviewPage(request, response, box, out);
            }

        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    ���� ����Ʈ (��ð���)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
	public void performSubjectListAlway(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            request.setAttribute("requestbox", box);

			CourseIntroBean bean = new CourseIntroBean();
            ArrayList list1 = null;
            String v_url = "";
            String sphere = box.getString("p_sphere");
			box.put("p_subjtype","alway");		// ��ð���            			

            if (sphere.equals("")) {
                v_url = "/learn/user/kocca/course/ku_CourseIntroAlway2_L.jsp";
            } else {
	            //��������Ʈ
	            list1 = bean.SelectSubjectList(box);
	            request.setAttribute("SubjectList", list1);				
                v_url = "/learn/user/kocca/course/ku_CourseIntroAlway_L.jsp";
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjectList()\r\n" + ex.getMessage());
        }
    }
	
	   /**
    ���� ����Ʈ (���ð���)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
	public void performSubjectListSometime(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            request.setAttribute("requestbox", box);

			CourseIntroBean bean = new CourseIntroBean();
            ArrayList list1 = null;
            String v_url = "";
            String sphere = box.getString("p_sphere");
			box.put("p_subjtype","sometime");		// ���ð���            			

            if (sphere.equals("")) {
                v_url = "/learn/user/kocca/course/ku_CourseIntroSometime2_L.jsp";
            } else {
	            //��������Ʈ
	            list1 = bean.SelectSubjectList(box);
	            request.setAttribute("SubjectList", list1);
                v_url = "/learn/user/kocca/course/ku_CourseIntroSometime_L.jsp";
            }
			
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjectList()\r\n" + ex.getMessage());
        }
    }
	
    /**
    ���� ����Ʈ (����������)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performSubjectListProf(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            request.setAttribute("requestbox", box);

//            ProposeCourseBean bean = new ProposeCourseBean();
//            ArrayList list1 = null;
            String v_url = "";
            String sphere = box.getString("p_sphere");
			box.put("p_subjtype","prof");		// ����������           
			
            if (sphere.equals("001")) {
                v_url = "/learn/user/kocca/course/ku_CourseIntroProcourse1_L.jsp";
            } else if (sphere.equals("002")) {
                v_url = "/learn/user/kocca/course/ku_CourseIntroProcourse2_L.jsp";
            } else if (sphere.equals("003")) {
                v_url = "/learn/user/kocca/course/ku_CourseIntroProcourse3_L.jsp";
            } else if (sphere.equals("004")) {
                v_url = "/learn/user/kocca/course/ku_CourseIntroProcourse4_L.jsp";
            } else if (sphere.equals("006")) {
                v_url = "/learn/user/kocca/course/ku_CourseIntroProcourse6_L.jsp";
            } else if (sphere.equals("007")) {
                v_url = "/learn/user/kocca/course/ku_CourseIntroProcourse7_L.jsp";
            } else if (sphere.equals("008")) {
                v_url = "/learn/user/kocca/course/ku_CourseIntroProcourse8_L.jsp";
            } else {
                v_url = "/learn/user/kocca/course/ku_CourseIntroProcourse5_L.jsp";
            }

            //��������Ʈ
            //list1 = bean.selectCourseIntroList(box);
            //request.setAttribute("SubjectList", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjectList()\r\n" + ex.getMessage());
        }
    }

	/**
    ���� ����Ʈ (��ũ�����)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performSubjectListWorkshop(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            request.setAttribute("requestbox", box);

			CourseIntroBean bean = new CourseIntroBean();
            ArrayList list1 = null;
            String v_url = "";
            String sphere = box.getString("p_sphere");
			box.put("p_subjtype","workshop");		// ��ũ�����            			

            if (sphere.equals("")) {
                v_url = "/learn/user/kocca/course/ku_CourseIntroWorkshop2_L.jsp";
            } else {
	            //��������Ʈ
	            list1 = bean.SelectSubjectList(box);
	            request.setAttribute("SubjectList", list1);
                v_url = "/learn/user/kocca/course/ku_CourseIntroWorkshop_L.jsp";
            }
			
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjectList()\r\n" + ex.getMessage());
        }
    }

	/**
    ���� ����Ʈ (������������)
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
    public void performSubjectListRecognition(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            request.setAttribute("requestbox", box);

			CourseIntroBean bean = new CourseIntroBean();
            ArrayList list1 = null;
            String v_url = "";
            String sphere = box.getString("p_sphere");
			box.put("p_subjtype","recognition");		// ������������            			

            if (sphere.equals("")) {
                v_url = "/learn/user/kocca/course/ku_CourseIntroRecognition2_L.jsp";
            } else {
	            //��������Ʈ
	            list1 = bean.SelectSubjectList(box);
	            request.setAttribute("SubjectList", list1);
                v_url = "/learn/user/kocca/course/ku_CourseIntroRecognition_L.jsp";
            }
			
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjectList()\r\n" + ex.getMessage());
        }
    }



    /**
    ���� �̸�����
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    @SuppressWarnings("unchecked")
   public void performSubjectPreviewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);
            ProposeCourseBean bean  = new ProposeCourseBean();

            // �������� ����Ʈ
            //ArrayList list1 = bean.selectListPre(box);
            //request.setAttribute("subjectPre", list1);
            // �ļ����� ����Ʈ
            //ArrayList list2 = bean.selectListNext(box);
            //request.setAttribute("subjectNext", list2);

            String  v_select  = box.getStringDefault("p_select","ON");
		    String  v_disposition = box.getStringDefault("p_disposition","year");  // ����			
            String v_url = "";

            // ���������� ����Ʈ
            DataBox dbox  = bean.selectSubjectPreview(box);
            request.setAttribute("subjectPreview", dbox);
            //String place = dbox.getString("d_place");

            // �������� ����Ʈ
            ArrayList list1 = bean.selectSubjSeqList(box);
            request.setAttribute("subjseqList", list1);
            
            if(v_select.equals("ON")){         //���̹� ������ ��� ��������Ʈ
                ArrayList list2 = bean.selectLessonList(box);
                request.setAttribute("lessonList", list2);

				KRecommandBean recomBean = new KRecommandBean();
                ArrayList list3 = null;				
			    if (v_disposition.equals("year")) {
					list3 = recomBean.selectAgeRecomList(box);
			    } else if (v_disposition.equals("sex")) {
					list3 = recomBean.selectSexRecomList(box);					
			    } else {
					list3 = recomBean.selectJikupRecomList(box);					
			    }
                request.setAttribute("dispositionList", list3);
				
				ArrayList list4 = recomBean.selectProposeRecomList(box);
                request.setAttribute("recomList", list4);
                v_url= "/learn/user/kocca/course/ku_CourseIntroPreviewON.jsp";				
            }
//System.out.print("v_url=============>"+v_url);
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);

            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("SubjectPreviewPage()\r\n" + ex.getMessage());
        }
    }

}
