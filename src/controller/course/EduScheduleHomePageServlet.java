package controller.course;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.course.EduScheduleHomePageBean;
import com.credu.library.AlertManager;
import com.credu.library.BulletinManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.propose.ProposeCourseBean;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.course.EduScheduleHomePageServlet")
public class EduScheduleHomePageServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
        PrintWriter out 		= null;
        RequestBox 	box 		= null;
        String 		v_process 	= "";

        boolean     v_canRead 	= false;
        boolean     v_canAppend = false;
        boolean     v_canModify = false;
        boolean     v_canDelete = false;
        boolean     v_canReply  = false;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            if (box.getSession("tem_grcode") == "") {
	             box.setSession("tem_grcode","N000001");
	     	}

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }


            // 로긴 check 루틴 VER 0.2 - 2003.09.9
//			if (!AdminUtil.getInstance().checkLogin(out, box)) {
//				return;
//			}
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

           v_canRead   = BulletinManager.isAuthority(box,box.getString("p_canRead"));
           v_canAppend = BulletinManager.isAuthority(box,box.getString("p_canAppend"));
           v_canModify = BulletinManager.isAuthority(box,box.getString("p_canModify"));
           v_canDelete = BulletinManager.isAuthority(box,box.getString("p_canDelete"));
           v_canReply  = BulletinManager.isAuthority(box,box.getString("p_canReply"));

            if(v_process.equals("schlMonthPlanList")) {     			 		     //  온라인 월별 교육 일정
            	if(v_canRead) this.performSchlMonthPlanList(request, response, box, out);
            } else if(v_process.equals("schlYearPlanList")) {     			 		     //  온라인 연도별 교육 일정
            	if(v_canRead) this.performSchlYearPlanList(request, response, box, out);
            } else if(v_process.equals("offSchlMonthPlanList")) {     			 		     //  오프라인 월별 교육 일정
            	if(v_canRead) this.performOffSchlMonthPlanList(request, response, box, out);
            } else if(v_process.equals("offSchlYearPlanList")) {     			 		     //  오프라인 연도별 교육 일정
            	if(v_canRead) this.performOffSchlYearPlanList(request, response, box, out);
            } else if(v_process.equals("subjectInfomation")) {     			 		     	 //  온라인수강신청안내
            	if(v_canRead) this.performsubjectInfomation(request, response, box, out);
            } else if(v_process.equals("offsubjectInfomation")) {     			 		     //  오프라인수강신청안내
            	if(v_canRead) this.performOffsubjectInfomation(request, response, box, out);
            } else if(v_process.equals("subjectallinformation")) {     			 		     //  온라인 교육과정안내
            	if(v_canRead) this.performsubjectallinformation(request, response, box, out);
            } else if(v_process.equals("offsubjectallinformation")) {     			 		 //  오프라인 교육과정안내
            	if(v_canRead) this.performoffsubjectallinformation(request, response, box, out);
            }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    //  온라인 월별 교육 일정
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSchlMonthPlanList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
    	 try {
             request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

             EduScheduleHomePageBean bean = new EduScheduleHomePageBean();

             ArrayList schlMonthPlanList = bean.schlMonthPlanList(box);
             request.setAttribute("schlMonthPlanList", schlMonthPlanList);

             ServletContext sc = getServletContext();
             RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/online/zu_SubjectMonthPlan_L.jsp");
             rd.forward(request, response);

             //Log.info.println(this, box, "Dispatch to /learn/user/portal/online/zu_SubjectMonthPlan_L.jsp");

         }catch (Exception ex) {
             ErrorManager.getErrorStackTrace(ex, out);
             throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
         }
    }

    /**
    //  온라인 연간 교육 일정
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSchlYearPlanList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
    	 try {
             request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

             EduScheduleHomePageBean bean = new EduScheduleHomePageBean();

             ArrayList schlYearPlanList = bean.schlYearPlanList(box);
             request.setAttribute("schlYearPlanList", schlYearPlanList);

             ServletContext sc = getServletContext();
             RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/online/zu_SubjectYearPlan_L.jsp");
             rd.forward(request, response);

             //Log.info.println(this, box, "Dispatch to /learn/user/portal/online/zu_SubjectMonthPlan_L.jsp");

         }catch (Exception ex) {
             ErrorManager.getErrorStackTrace(ex, out);
             throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
         }
    }

    /**
    //  오프라인 월별 교육 일정
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performOffSchlMonthPlanList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
    	 try {
             request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

             String v_url  = "";
             if( box.getSession("tem_grcode").equals("N000001")) {   // B2C 2012 renewal
             	v_url = "/learn/user/2012/portal/offline/zu_OffSubjectMonthPlan_L.jsp";
             } else {
             	v_url = "/learn/user/portal/offline/zu_OffSubjectMonthPlan_L.jsp";
             }

             EduScheduleHomePageBean bean = new EduScheduleHomePageBean();

             ArrayList offSchlMonthPlanList = bean.offSchlMonthPlanList(box);
             request.setAttribute("offSchlMonthPlanList", offSchlMonthPlanList);

             ServletContext sc = getServletContext();
             RequestDispatcher rd = sc.getRequestDispatcher(v_url);
             rd.forward(request, response);

             //Log.info.println(this, box, "Dispatch to /learn/user/portal/online/zu_SubjectMonthPlan_L.jsp");

         }catch (Exception ex) {
             ErrorManager.getErrorStackTrace(ex, out);
             throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
         }
    }

    /**
    //  오프라인 연간 교육 일정
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performOffSchlYearPlanList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
    	 try {
             request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

             EduScheduleHomePageBean bean = new EduScheduleHomePageBean();

             String v_url  = "";
             if( box.getSession("tem_grcode").equals("N000001")) {   // B2C 2012 renewal
             	//v_url = "/learn/user/2012/portal/offline/zu_OffSubjectYearPlan_L.jsp";
             	v_url = "/learn/user/2013/portal/offline/zu_OffSubjectYearPlan_L.jsp";
             } else {
             	v_url = "/learn/user/portal/offline/zu_OffSubjectYearPlan_L.jsp";

             	ArrayList offSchlYearPlanList = bean.offSchlYearPlanList(box);
                request.setAttribute("offSchlYearPlanList", offSchlYearPlanList);
             }

             ServletContext sc = getServletContext();
             RequestDispatcher rd = sc.getRequestDispatcher(v_url);
             rd.forward(request, response);

             //Log.info.println(this, box, "Dispatch to /learn/user/portal/online/zu_SubjectMonthPlan_L.jsp");

         }catch (Exception ex) {
             ErrorManager.getErrorStackTrace(ex, out);
             throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
         }
    }



    /**
     * 온라인 수강신청 안내
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performsubjectInfomation(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
    	try {
    		request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

    		ServletContext sc = getServletContext();
    		RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/online/zu_subjectInfomation_R.jsp");
    		rd.forward(request, response);

    	}catch (Exception ex) {
    		ErrorManager.getErrorStackTrace(ex, out);
    		throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
    	}
    }


    /**
     * 오프라인 수강신청 안내
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performOffsubjectInfomation(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
    	try {
    		request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

    		ServletContext sc = getServletContext();
    		RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/offline/zu_OffsubjectInfomation_R.jsp");
    		rd.forward(request, response);

    	}catch (Exception ex) {
    		ErrorManager.getErrorStackTrace(ex, out);
    		throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
    	}
    }


    /**
     * 온라인 교육과정 안내
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public void performsubjectallinformation(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
    	try {
    		box.sync("tabid");

    		String v_url = "";
    		String v_path = "/learn/user/portal/online/";
    		String v_page = "";
    		String grcode=box.getSession("tem_grcode");
    		String tabid = box.getStringDefault("tabid", "0");
            if(grcode.equals("N000023")){
                tabid="1";
                box.put("tabid","1");
            }

            if(grcode.equals("N000001")){
                if( tabid.equals("3") ) {
                	v_url = "/learn/user/portal/online/zu_subjectAllInfomation1_R.jsp";
                } else if( tabid.equals("1") ) {
                	v_url = "/learn/user/portal/online/zu_subjectAllInfomation2_R.jsp";
                } else if( tabid.equals("2") ) {
                	v_url = "/learn/user/portal/online/zu_subjectAllInfomation3_R.jsp";
                } else if( tabid.equals("4") ) {
                	v_url = "/learn/user/portal/online/zu_subjectAllInfomation4_R.jsp";
                }
            }else{
                ProposeCourseBean bean = new ProposeCourseBean();
			    ArrayList list1 = bean.selectSubjectList_Asp(box);
	    		request.setAttribute("SubjectList", list1);

                if(tabid.equals("3"))
                	v_url="/learn/user/portal/online/zu_Asp_subjectAllInfomation1_R.jsp";
                else if( tabid.equals("1") )
                	v_url = "/learn/user/portal/online/zu_Asp_subjectAllInfomation2_R.jsp";
                else if( tabid.equals("2") )
                	v_url = "/learn/user/portal/online/zu_Asp_subjectAllInfomation3_R.jsp";
                else if( tabid.equals("4") )
                	v_url = "/learn/user/portal/online/zu_Asp_subjectAllInfomation4_R.jsp";
            }

            if(box.getSession("tem_type").equals("B")){
    			v_url = "/learn/user/typeB/online/zu_Asp_subjectAllInfomation1_R.jsp";
    		}
            
            box.put("tabid", tabid);
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
    		ServletContext sc = getServletContext();
    		RequestDispatcher rd = sc.getRequestDispatcher(v_url);
    		rd.forward(request, response);

    	}catch (Exception ex) {
    		ErrorManager.getErrorStackTrace(ex, out);
    		throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
    	}
    }


    /**
     * 오프라인 교육과정 안내
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performoffsubjectallinformation(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    throws Exception {
    	try {
    		request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

    		ServletContext sc = getServletContext();
    		RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/portal/offline/zu_OffsubjectAllInfomation_R.jsp");
    		rd.forward(request, response);

    	}catch (Exception ex) {
    		ErrorManager.getErrorStackTrace(ex, out);
    		throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
    	}
    }


    @SuppressWarnings("unchecked")
	public void errorPage(RequestBox box, PrintWriter out)
        throws Exception {
        try {
            box.put("p_process", "");

            AlertManager alert = new AlertManager();

            alert.alertFailMessage(out, "이 프로세스로 진행할 권한이 없습니다.");
            //  Log.sys.println();

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("errorPage()\r\n" + ex.getMessage());
        }
    }

}

