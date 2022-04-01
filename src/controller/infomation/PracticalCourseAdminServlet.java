//**********************************************************
//  1. 제      목:  제어하는 서블릿
//  2. 프로그램명 : ____Servlet.java
//  3. 개      요:  제어 프로그램
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: __누구__ 2009. 10. 19
//  7. 수     정1:
//**********************************************************
package controller.infomation;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.infomation.GoldClassBean;
import com.credu.infomation.PracticalCourseBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.infomation.PracticalCourseAdminServlet")
public class PracticalCourseAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

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

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

			///////////////////////////////////////////////////////////////////
			if (!AdminUtil.getInstance().checkRWRight("PracticalCourseAdminServlet", v_process, out, box)) {
				return; 
			}
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
			///////////////////////////////////////////////////////////////////
			
            if(v_process.equals("updatePage")) {              			//  수정페이지 이동
                this.performUpdatePage(request, response, box, out);
            } else if(v_process.equals("update")) {                  	//  수정 저장
                this.performUpdate(request, response, box, out);
            } else if(v_process.equals("selectList")) {                 //  리스트
                this.performSelectList(request, response, box, out);
            } else if(v_process.equals("insertPage")) {                 //  등록 페이지 이동
                this.performInsertPage(request, response, box, out);
            } else if(v_process.equals("insert")) {                     //  등록
                this.performInsert(request, response, box, out);
            } else if(v_process.equals("delete")) {                     //  삭제
                this.performDelete(request, response, box, out);
            } else if(v_process.equals("selectView")) {                 //  상세보기
                this.performSelectView(request, response, box, out);
            } else if(v_process.equals("popUpVod")) {                 //  동영상보기
                this.performPopUpVod(request, response, box, out);
            }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
        등록페이지로 이동할때
   @param request  encapsulates the request to the servlet
   @param response encapsulates the response from the servlet
   @param box      receive from the form object
   @param out      printwriter object
   @return void
   */
   public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
       try {
           request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

		   ServletContext sc = getServletContext();
           RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/infomation/za_PracticalCourse_I.jsp");
           rd.forward(request, response);

           Log.info.println(this, box, "Dispatch to /learn/admin/infomation/za_PracticalCourse_I.jsp");

       }catch (Exception ex) {
           ErrorManager.getErrorStackTrace(ex, out);
           throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
       }
   }
   
   /**
   //  등록하여 저장할때
   @param request  encapsulates the request to the servlet
   @param response encapsulates the response from the servlet
   @param box      receive from the form object
   @param out      printwriter object
   @return void
   */
   public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
       throws Exception {
        try {
           PracticalCourseBean bean = new PracticalCourseBean();

           int isOk = bean.insertPracticalCourse(box);

           String v_msg = "";
           String v_url = "/servlet/controller.infomation.PracticalCourseAdminServlet";
           box.put("p_process", "selectList");
           //      수정 후 해당 리스트 페이지로 돌아가기 위해

           AlertManager alert = new AlertManager();

           if(isOk > 0) {
               v_msg = "insert.ok";
               alert.alertOkMessage(out, v_msg, v_url , box);
           }
           else {
               v_msg = "insert.fail";
               alert.alertFailMessage(out, v_msg);
           }

           Log.info.println(this, box, v_msg + " on PracticalCourseAdminServlet");
       }catch (Exception ex) {
           ErrorManager.getErrorStackTrace(ex, out);
           throw new Exception("performUpdate()\r\n" + ex.getMessage());
       }
   }

     /**
     수정페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

			PracticalCourseBean bean = new PracticalCourseBean();
            
            DataBox dbox = bean.selectViewPracticalCourse(box);
            request.setAttribute("selectOffExpert", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/infomation/za_PracticalCourse_U.jsp");
            rd.forward(request, response);

            ////Log.info.println(this, box, "Dispatch to /learn/admin/infomation/za_PracticalCourse_U.jsp");

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

     /**
    //  수정하여 저장할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
         try {
            PracticalCourseBean bean = new PracticalCourseBean();

            int isOk = bean.updatePracticalCourse(box);

            String v_msg = "";
            String v_url = "/servlet/controller.infomation.PracticalCourseAdminServlet";
            box.put("p_process", "selectList");
            //      수정 후 해당 리스트 페이지로 돌아가기 위해

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            ////Log.info.println(this, box, v_msg + " on OffExpertAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }
    
    /**
    //  상세보기
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
    	 try {
             request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

 			PracticalCourseBean bean = new PracticalCourseBean();
             
             DataBox dbox = bean.selectViewPracticalCourse(box);
             request.setAttribute("selectOffExpert", dbox);

             ServletContext sc = getServletContext();
             RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/infomation/za_PracticalCourse_R.jsp");
             rd.forward(request, response);

             ////Log.info.println(this, box, "Dispatch to /learn/admin/infomation/za_PracticalCourse_U.jsp");

         }catch (Exception ex) {
             ErrorManager.getErrorStackTrace(ex, out);
             throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
         }
    }


    /**
     리스트
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다

            PracticalCourseBean bean = new PracticalCourseBean();

            //일반 리스트
            ArrayList selectList = bean.selectListPracticalCourse(box);
            request.setAttribute("selectList", selectList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/infomation/za_PracticalCourse_L.jsp");
            rd.forward(request, response);

            ////Log.info.println(this, box, "Dispatch to /learn/admin/infomation/za_PracticalCourse_L.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }
    
    /**
    //  삭제할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
         try {
            PracticalCourseBean bean = new PracticalCourseBean();

            int isOk = bean.deletePracticalCourse(box);

            String v_msg = "";
            String v_url = "/servlet/controller.infomation.PracticalCourseAdminServlet";
            box.put("p_process", "selectList");
            //      수정 후 해당 리스트 페이지로 돌아가기 위해

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on PracticalCourseAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }
    
    /**
    //  상세보기
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performPopUpVod(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
    	 try {
             request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

             PracticalCourseBean bean = new PracticalCourseBean();
             
             DataBox dbox = bean.selectViewPracticalCourse(box);
             request.setAttribute("selectView", dbox);
             
             ServletContext sc = getServletContext();
             RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/infomation/za_PracticalCourse_V.jsp");
             rd.forward(request, response);

             ////Log.info.println(this, box, "Dispatch to /learn/admin/infomation/za_GoldClass_U.jsp");

         }catch (Exception ex) {
             ErrorManager.getErrorStackTrace(ex, out);
             throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
         }
    }
    
}

