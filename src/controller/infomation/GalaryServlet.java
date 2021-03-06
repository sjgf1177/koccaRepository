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

import com.credu.common.SelectEduBean;
import com.credu.homepage.NoticeAdminBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.library.StringManager;
import com.credu.system.AdminUtil;

@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.infomation.GalaryServlet")
public class GalaryServlet extends javax.servlet.http.HttpServlet implements Serializable {

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

            if (!AdminUtil.getInstance().checkRWRight("NoticeAdminServlet", v_process, out, box)) {
                return;
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            if(v_process.equals("selectView")){                      //  상세보기로 이동할때
                this.performSelectView(request, response, box, out);
            } else if(v_process.equals("insertPage")) {              //  등록페이지로 이동할때
                this.performInsertPage(request, response, box, out);
            } else if(v_process.equals("insert")) {                  //  등록할때
                this.performInsert(request, response, box, out);
            } else if(v_process.equals("updatePage")) {              //  수정페이지로 이동할때
                this.performUpdatePage(request, response, box, out);
            } else if(v_process.equals("update")) {                  //  수정하여 저장할때
                this.performUpdate(request, response, box, out);
            } else if(v_process.equals("delete")) {                  //  삭제할때
                this.performDelete(request, response, box, out);
            } else if(v_process.equals("select")) {                  //  리스트
                this.performSelectList(request, response, box, out);
            } else if(v_process.equals("complist")) {                  // 회사 리스트
                this.performCompList(request, response, box, out);
            } else if(v_process.equals("grcodelist")) {                  // 회사 리스트
                this.performGrcodeList(request, response, box, out);
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

            NoticeAdminBean bean = new NoticeAdminBean();
            //NoticeData data = bean.selectViewNotice(box);
            DataBox dbox = bean.selectViewNotice(box);

            request.setAttribute("selectNotice", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_Notice_R.jsp");
            rd.forward(request, response);

            ////Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_Notice_R.jsp");

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectView()\r\n" + ex.getMessage());
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

            SelectEduBean seBean = new SelectEduBean();
            ArrayList grcodenm = seBean.getGrcode(box);
            request.setAttribute("grcodenm", grcodenm);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_Notice_I.jsp");
            rd.forward(request, response);

            ////Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_Notice_I.jsp");

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

     /**
     등록할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            NoticeAdminBean bean = new NoticeAdminBean();

            int isOk = bean.insertNotice(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.NoticeAdminServlet";
            box.put("p_process", "select");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            ////Log.info.println(this, box, v_msg + " on NoticeAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
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

            NoticeAdminBean bean = new NoticeAdminBean();
            //NoticeData data = bean.selectViewNotice(box);
            DataBox dbox = bean.selectViewNotice(box);

            request.setAttribute("selectNotice", dbox);

            //ArrayList compnm = bean.selectComp(box);
            //request.setAttribute("compnm", compnm);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_Notice_U.jsp");
            rd.forward(request, response);

            ////Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_Notice_U.jsp");

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
            NoticeAdminBean bean = new NoticeAdminBean();

            int isOk = bean.updateNotice(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.NoticeAdminServlet";
            box.put("p_process", "select");
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

            ////Log.info.println(this, box, v_msg + " on NoticeAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
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
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
           NoticeAdminBean bean = new NoticeAdminBean();

            int isOk = bean.deleteNotice(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.NoticeAdminServlet";
            box.put("p_process", "select");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            ////Log.info.println(this, box, v_msg + " on NoticeAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
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

            NoticeAdminBean bean = new NoticeAdminBean();

            int tabseq = StringManager.toInt(box.getStringDefault("p_tabseq",""));

            if (tabseq == 0) {
                /*------- 게시판 분류에 대한 부분 세팅 -----*/
                box.put("p_type", "HN");
                box.put("p_grcode", "0000000");
                box.put("p_comp", "0000000000");
                box.put("p_subj", "0000000000");
                box.put("p_year", "0000");
                box.put("p_subjseq", "0000");
                /*----------------------------------------*/

                tabseq = bean.selectTableseq(box);

                if (tabseq == 0) {
                  String msg = "게시판정보가 없습니다.";
                  AlertManager.historyBack(out, msg);
                }

                box.put("p_tabseq", String.valueOf(tabseq));
            }

            //교육그룹 선택박스
            ArrayList EduGroup = bean.selectEduGroup(box);
            request.setAttribute("selectGrcode", EduGroup);

            //전체 리스트
            ArrayList List1 = bean.selectListNoticeAll(box);
            request.setAttribute("selectList1", List1);

            //일반 리스트
            ArrayList List2 = bean.selectListNotice(box);
            request.setAttribute("selectList2", List2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_Notice_L.jsp");
            rd.forward(request, response);

            ////Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_Notice_L.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }


     /**
    회사페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performCompList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

            NoticeAdminBean bean = new NoticeAdminBean();
            ArrayList compnm = bean.selectComp(box);
            request.setAttribute("compnm", compnm);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_NoticeComp_L.jsp");
            rd.forward(request, response);

            ////Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_NoticeComp_L.jsp");

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }


    /**
      교육그룹리스트 페이지로 이동할때
      @param request  encapsulates the request to the servlet
      @param response encapsulates the response from the servlet
      @param box      receive from the form object
      @param out      printwriter object
      @return void
    */

    public void performGrcodeList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

            SelectEduBean seBean = new SelectEduBean();
            ArrayList grcodenm = seBean.getGrcode(box);
            request.setAttribute("grcodenm", grcodenm);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_NoticeGrcode_L.jsp");
            rd.forward(request, response);

            ////Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_NoticeGrcode_L.jsp");

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }



}

