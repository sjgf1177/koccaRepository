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

import com.credu.infomation.ContactUsBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.library.SmsBean;
import com.credu.system.AdminUtil;

@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.infomation.ContactUsAdminServlet")
public class ContactUsAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
        RequestBox box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkRWRight("ContactUsAdminServlet", v_process, out, box)) {
                return;
            }

            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if(v_process.equals("selectView")){                      //  상세보기로 이동할때
                this.performSelectView(request, response, box, out);
            } else if(v_process.equals("selectList")) {                  //  리스트
                this.performSelectList(request, response, box, out);
            } else if(v_process.equals("selectListEtc")) {                  //  리스트
                this.performSelectListEtc(request, response, box, out);
            } else if(v_process.equals("selectViewEtc")) {                  //  상세
                this.performSelectViewEtc(request, response, box, out);
            } else if(v_process.equals("selectListVocation")) {                  //  리스트
                this.performSelectListVocation(request, response, box, out);
            } else if(v_process.equals("selectViewVocation")) {                  //  상세
                this.performSelectViewVocation(request, response, box, out);
            } else if(v_process.equals("selectListWebReport")) {                  //  웹취약성 신고 리스트
                this.performSelectListWebReport(request, response, box, out);
            } else if(v_process.equals("selectViewWebReport")) {                  //  웹치약성 신고 상세
                this.performselectViewWebReport(request, response, box, out);
            } else if(v_process.equals("sendSms_target")) {
                this.performSendSms_target(request, response, box, out);            // 1인대상 sms 발송
            }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }


    /**
     상세보기로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다

            ContactUsBean bean = new ContactUsBean();
            DataBox dbox = bean.selectView(box);

            request.setAttribute("selectView", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/infomation/za_ContactUs_R.jsp");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectView()\r\n" + ex.getMessage());
        }
    }

    /**
    상세보기로 이동할때
   @param request  encapsulates the request to the servlet
   @param response encapsulates the response from the servlet
   @param box      receive from the form object
   @param out      printwriter object
   @return void
   */
   public void performSelectViewEtc(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
       try {
           request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다

           ContactUsBean bean = new ContactUsBean();
           DataBox dbox = bean.selectViewEtc(box);

           request.setAttribute("selectView", dbox);

           ServletContext sc = getServletContext();
           RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/infomation/za_ContactEtc_R.jsp");
           rd.forward(request, response);

       }catch (Exception ex) {
           ErrorManager.getErrorStackTrace(ex, out);
           throw new Exception("performSelectView()\r\n" + ex.getMessage());
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

            ContactUsBean bean = new ContactUsBean();

            //일반 리스트
            ArrayList List = bean.selectList(box);
            request.setAttribute("selectList", List);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/infomation/za_ContactUs_L.jsp");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
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
   public void performSelectListEtc(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
       try {
           request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다

           ContactUsBean bean = new ContactUsBean();

           //일반 리스트
           ArrayList List = bean.selectListEtc(box);
           request.setAttribute("selectList", List);

           ServletContext sc = getServletContext();
           RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/infomation/za_ContactEtc_L.jsp");
           rd.forward(request, response);

       }catch (Exception ex) {
           ErrorManager.getErrorStackTrace(ex, out);
           throw new Exception("performSelectList()\r\n" + ex.getMessage());
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
  public void performSelectListVocation(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
      try {
          request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다

          ContactUsBean bean = new ContactUsBean();

          //일반 리스트
          ArrayList List = bean.selectListVocation(box);
          request.setAttribute("selectList", List);

          ServletContext sc = getServletContext();
          RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/infomation/za_ContactVocation_L.jsp");
          rd.forward(request, response);

      }catch (Exception ex) {
          ErrorManager.getErrorStackTrace(ex, out);
          throw new Exception("performSelectList()\r\n" + ex.getMessage());
      }
  }
      /**
      상세보기로 이동할때
     @param request  encapsulates the request to the servlet
     @param response encapsulates the response from the servlet
     @param box      receive from the form object
     @param out      printwriter object
     @return void
     */
     public void performSelectViewVocation(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
         try {
             request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다

             ContactUsBean bean = new ContactUsBean();
             DataBox dbox = bean.selectViewVocation(box);

             request.setAttribute("selectView", dbox);

             ServletContext sc = getServletContext();
             RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/infomation/za_ContactVocation_R.jsp");
             rd.forward(request, response);

         }catch (Exception ex) {
             ErrorManager.getErrorStackTrace(ex, out);
             throw new Exception("performSelectView()\r\n" + ex.getMessage());
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
    public void performSelectListWebReport(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다

            ContactUsBean bean = new ContactUsBean();

            //일반 리스트
            ArrayList List = bean.selectListWebReport(box);
            request.setAttribute("selectList", List);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/infomation/za_Report_L.jsp");
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

    public void performSendSms_target(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_msg = "";
            String v_url = "/servlet/controller.infomation.ContactUsAdminServlet";
            box.put("p_process", "selectListWebReport");

            ContactUsBean bean = new ContactUsBean();
            AlertManager alert = new AlertManager();

            String p_seq = box.getString("p_seq");  // 선택 seq 번호
            String p_handphone = "";
            p_handphone = bean.getHandphone(p_seq);
            int ok = 0;

            if (!p_handphone.equals("")) {
                SmsBean smbean = new SmsBean();
                boolean ch = smbean.sendSMSMsg(p_handphone,"02-3219-5483","[KOCCA] 웹접근성센터 문의사항이 처리되었습니다.");

                if(ch){
                    ok = bean.update_Report_smsYn(box);
                }
            }

            if(ok == 1){v_msg = "문자발송이 완료되었습니다.";}
            else{v_msg = "문자발송에 실패하였습니다.";}
            alert.alertOkMessage(out, v_msg, v_url , box);

            Log.info.println(this, box, v_msg + " on QnaAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performReply()\r\n" + ex.getMessage());
        }
    }

     /**
      상세보기로 이동할때
     @param request  encapsulates the request to the servlet
     @param response encapsulates the response from the servlet
     @param box      receive from the form object
     @param out      printwriter object
     @return void
     */
     public void performselectViewWebReport(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
         try {
             request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다

             ContactUsBean bean = new ContactUsBean();
             DataBox dbox = bean.selectViewWebReport(box);

             request.setAttribute("selectView", dbox);

             ServletContext sc = getServletContext();
             RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/infomation/za_Report_R.jsp");
             rd.forward(request, response);

         }catch (Exception ex) {
             ErrorManager.getErrorStackTrace(ex, out);
             throw new Exception("performSelectView()\r\n" + ex.getMessage());
         }
     }


}

