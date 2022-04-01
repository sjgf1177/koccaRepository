//**********************************************************
//  1. 제      목: 커뮤니티 마스타화면을 제어하는 서블릿
//  2. 프로그램명 : HomePageBoardServlet.java
//  3. 개      요: 커뮤니티의 마스타화면 페이지을 제어한다
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 2005.7.1 
//  7. 수      정: 2005.7.1 
//**********************************************************

package controller.community;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.community.CommunityCreateBean;
import com.credu.community.CommunityIndexBean;
import com.credu.community.CommunityMsCommunityCloseBean;
import com.credu.community.CommunityMsGradeBean;
import com.credu.community.CommunityMsGradeNmBean;
import com.credu.community.CommunityMsMangeBean;
import com.credu.community.CommunityMsMasterChangeBean;
import com.credu.community.CommunityMsMemberJoinBean;
import com.credu.community.CommunityMsMenuBean;
import com.credu.community.CommunityMsPrBean;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings({ "unchecked", "serial" })
public class CommunityMasterServlet extends javax.servlet.http.HttpServlet {

    /**
    * DoGet
    * Pass get requests through to PerformTask
    */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter      out          = null;
//        MultipartRequest multi        = null;
        RequestBox       box          = null;
        String           v_process    = "";
//        int              fileupstatus = 0;

        try {
            response.setContentType("text/html;charset=euc-kr");
//            String path = request.getServletPath();

            out       = response.getWriter();
            box       = RequestManager.getBox(request);
            v_process = box.getStringDefault("p_process","selectmsmainPage");
   
            if(ErrorManager.isErrorMessageView()) box.put("errorout", out);

            // 로긴 check 루틴 VER 0.2 - 2003.09.9
            if (!AdminUtil.getInstance().checkLoginPopup(out, box)) {
             return; 
            }
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));

            /////////////////////////////////////////////////////////////////////////////
            if(v_process.equals("selectmsmainPage")) {                        //  마스타메인 페이지로 이동할때
              this.performMsMainPage(request, response, box, out);
            } else if(v_process.equals("moveroomPage")) {                     //  기본정보변경 페이지로 이동할때
              this.performMsRoomPage(request, response, box, out);
            } else if(v_process.equals("updatemsroomData")) {                 //  기본정보변경할때
              this.performMsRoomUpdateData(request, response, box, out);
            } else if(v_process.equals("deletemsroomfileData")) {             // 커뮤니티 이미지 파일을 삭제할때
              this.performMsRoomFileDeleteData(request, response, box, out);
            } else if(v_process.equals("movemsmemberjoinPage")) {             //  회원가입 페이지로 이동할때
              this.performMsMemberJoinPage(request, response, box, out);
            } else if(v_process.equals("insertmsmemberjoinData")) {           //  회원가입처리
              this.performMsMemberJoinData(request, response, box, out);
            } else if(v_process.equals("movemsmembergradePage")) {            //  등급변경 페이지로 이동할때
              this.performMsMemberGradePage(request, response, box, out);
            } else if(v_process.equals("insertmsmembergradeData")) {          //  등급변경처리
              this.performMsMemberGradeData(request, response, box, out);
            } else if(v_process.equals("updatememberoutData")) {              //  회원해지처리
              this.performMsMemberOutData(request, response, box, out);

            } else if(v_process.equals("movemsmembergradenmPage")) {          //  등급명변경 페이지로 이동할때
              this.performMsMemberGradeNmPage(request, response, box, out);
            } else if(v_process.equals("insertmsmembergradenmData")) {        //  등급명변경처리
              this.performMsMemberGradeNmData(request, response, box, out);

            } else if(v_process.equals("movemasterchagnePage")) {             //  마스타변경 페이지로 이동할때
              this.performMsMasterChangePage(request, response, box, out);
            } else if(v_process.equals("updatemasterchangeData")) {           //  마스타변경처리
              this.performMsMasterChangeData(request, response, box, out);

            } else if(v_process.equals("movecommunityclosePage")) {           // 사이트 폐쇄 페이지로 이동
               this.performMsCommunityClosePage(request, response, box, out);
            } else if(v_process.equals("movecommunitycloseData")) {           // 사이트 폐쇄
               this.performMsCommunityCloseData(request, response, box, out);


            } else if(v_process.equals("movemenuPage")) {           // 메뉴등록 페이지로 이동
               this.performMsMenuPage(request, response, box, out);
            } else if(v_process.equals("insertmenuData")) {           // 메뉴추가등록
               this.performMsMenuData(request, response, box, out);
            } else if(v_process.equals("updatemenuData")) {           // 메뉴수정
               this.performMsMenuUpdateData(request, response, box, out);
            } else if(v_process.equals("deletemenuData")) {           // 메뉴삭제
               this.performMsMenuDeleteData(request, response, box, out);

            } else if(v_process.equals("movememberactivityPage")) {              //  회원활동현황
              this.performMsMemberActivityPage(request, response, box, out);

            
            } else if(v_process.equals("moveprPage")) {             // 홍보페이지로이동 페이지로 이동
               this.performMsPrPage(request, response, box, out);
            } else if(v_process.equals("insertprData")) {           // 홍보등록
               this.performMsPrData(request, response, box, out);
            } else if(v_process.equals("deleteprfileData")) {           // 홍보이미지삭제
               this.performMsPrDeleteFileData(request, response, box, out);
            } else if(v_process.equals("deleteprData")) {           // 홍보삭제
               this.performMsPrDeleteData(request, response, box, out);

            } 
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }


    /**
    메인인덱스 페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performMsMainPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);  

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_MsMain_L.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_MsMain_L.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

   /**
    기본정보변경 페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performMsRoomPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);  

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_MsRoom_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_MsRoom_I.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }



     /**
    커뮤니티등록 수정할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performMsRoomUpdateData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityCreateBean bean = new CommunityCreateBean();
            int isOk = bean.updateBaseMst(box);
            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityMasterServlet";
            box.put("p_process", "moveroomPage");
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityCreateServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsRoomUpdateData()\r\n" + ex.getMessage());
        }
    }


     /**
    커뮤니티 등록 이미지를 삭제할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performMsRoomFileDeleteData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityCreateBean bean = new CommunityCreateBean();
            int isOk = bean.deleteSingleFile(box);
            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityMasterServlet";
            box.put("p_process", "moveroomPage");
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityMasterServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsRoomFileDeleteData()\r\n" + ex.getMessage());
        }
    }



    /**
    커뮤니티명 회원가입 페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performMsMemberJoinPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

            CommunityMsMemberJoinBean bean = new CommunityMsMemberJoinBean();
            ArrayList list = bean.selectMemberList(box,"0"); //0.가입신청 1.승인 2.승인거부및 해지
            
            request.setAttribute("list", list);

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_MsMemJoin_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_MsMemJoin_I.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsMemberJoinPage()\r\n" + ex.getMessage());
        }
    }

     /**
    커뮤니티 회원가입처리
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performMsMemberJoinData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityMsMemberJoinBean bean = new CommunityMsMemberJoinBean();
            int isOk = bean.updateCmuUserCloseFg(box);
            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityMasterServlet";
            box.put("p_process", "movemsmemberjoinPage");
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityMasterServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsRoomFileDeleteData()\r\n" + ex.getMessage());
        }
    }




    /**
    커뮤니티명 등급변경 페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performMsMemberGradePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

            //등급코드조회
            CommunityIndexBean bean1 = new CommunityIndexBean();
            ArrayList gradeList = bean1.selectTz_Cmugrdcode(box); 
            
            request.setAttribute("gradeList", gradeList);


            //리스트조회
            CommunityMsMemberJoinBean bean = new CommunityMsMemberJoinBean();
            ArrayList list = bean.selectMemberList(box,"1"); //0.가입신청 1.승인 2.승인거부및 해지
            
            request.setAttribute("list", list);

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_MsMemGrade_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_MsMemGrade_I.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsMemberJoinPage()\r\n" + ex.getMessage());
        }
    }

     /**
    커뮤니티 회원 등급변경처리
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performMsMemberGradeData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityMsGradeBean bean = new CommunityMsGradeBean();
            int isOk = bean.updateUsermstGrade(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityMasterServlet";
            box.put("p_process", "movemsmembergradePage");
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityMasterServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsMemberGradeData()\r\n" + ex.getMessage());
        }
    }


     /**
    커뮤니티 회원해지처리
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performMsMemberOutData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityMsMemberJoinBean bean = new CommunityMsMemberJoinBean();
            int isOk = bean.updateCmuUserCloseFg(box);
            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityMasterServlet";
            box.put("p_process", "movemsmembergradePage");
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityMasterServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsRoomFileDeleteData()\r\n" + ex.getMessage());
        }
    }



    /**
    커뮤니티명 등급명변경 페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performMsMemberGradeNmPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

            //커뮤니티등급코드조회
            CommunityIndexBean bean1 = new CommunityIndexBean();
            ArrayList gradeList = bean1.selectTz_Cmugrdcode(box); 
            
            request.setAttribute("gradeList", gradeList);


            //리스트조회
            CommunityCreateBean bean = new CommunityCreateBean();
            ArrayList list = bean.selectCodeType_L("0053");
            
            request.setAttribute("list", list);

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_MsMemGradeNm_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_MsMemGradeNm_I.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsMemberGradeNmPage()\r\n" + ex.getMessage());
        }
    }

     /**
    커뮤니티 회원 등급명변경처리
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performMsMemberGradeNmData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityMsGradeNmBean bean = new CommunityMsGradeNmBean();
            int isOk = bean.updateCommunityGradeNm(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityMasterServlet";
            box.put("p_process", "movemsmembergradenmPage");
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityMasterServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsMemberGradeNmData()\r\n" + ex.getMessage());
        }
    }




    /**
    커뮤니티명 마스타변경 페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performMsMasterChangePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

            CommunityMsMangeBean bean = new CommunityMsMangeBean();
            ArrayList list = bean.selectMemberSingleData(box,"01"); 
            request.setAttribute("list", list);

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_MsMasterChange_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_MsMasterChange_I.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsMemberGradeNmPage()\r\n" + ex.getMessage());
        }
    }

     /**
    커뮤니티 회원 등급명변경처리
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performMsMasterChangeData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityMsMasterChangeBean bean = new CommunityMsMasterChangeBean();
            int isOk = bean.updateMasterChange(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityIndexServlet";
            box.put("p_process", "selectmyindex");
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityMasterServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsMasterChangeData()\r\n" + ex.getMessage());
        }
    }



    /**
    커뮤니티명 폐쇄 페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performMsCommunityClosePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_MsPopCmuClose_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_MsPopCmuClose_I.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsCommunityClosePage()\r\n" + ex.getMessage());
        }
    }


     /**
    커뮤니티 사이트 폐쇄처리
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performMsCommunityCloseData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityMsCommunityCloseBean bean = new CommunityMsCommunityCloseBean();
            int isOk = bean.updateCommunityClose(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityMasterServlet";
            box.put("p_process", "movecommunityclosePage");
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {

                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityMasterServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsCommunityCloseData()\r\n" + ex.getMessage());
        }
    }
    


    /**
    커뮤니티명 메뉴등록 페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performMsMenuPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다

            //등급코드조회
            CommunityIndexBean bean1 = new CommunityIndexBean();
            ArrayList gradeList = bean1.selectTz_Cmugrdcode(box); 
            
            request.setAttribute("gradeList", gradeList);

            //등급코드조회
            CommunityMsMenuBean bean  = new CommunityMsMenuBean();
            ArrayList list = bean.selectleftList(box); 
            request.setAttribute("list", list);

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_MsMenu_I.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_MsMenu_I.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsCommunityClosePage()\r\n" + ex.getMessage());
        }
    }



     /**
    커뮤니티 메뉴등록
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performMsMenuData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityMsMenuBean bean = new CommunityMsMenuBean();
            int isOk = bean.insertMenu(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityMasterServlet";
            box.put("p_process", "movemenuPage");
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {

                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityMasterServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsCommunityCloseData()\r\n" + ex.getMessage());
        }
    }
    

     /**
    메뉴 수정처리
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performMsMenuUpdateData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityMsMenuBean bean = new CommunityMsMenuBean();
            int isOk = bean.updateMenu(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityMasterServlet";
            box.put("p_process", "movemenuPage");
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {

                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityMasterServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsMenuUpdateData()\r\n" + ex.getMessage());
        }
    }
    

     /**
    메뉴 삭제처리
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performMsMenuDeleteData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityMsMenuBean bean = new CommunityMsMenuBean();
            int isOk = bean.deleteMenu(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityMasterServlet";
            box.put("p_process", "movemenuPage");
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {

                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityMasterServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsMenuDeleteData()\r\n" + ex.getMessage());
        }
    }

     /**
    커뮤니티 회원활동현황 페이지로 이동
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performMsMemberActivityPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {

            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다


            //리스트조회
            CommunityMsMangeBean bean = new CommunityMsMangeBean();
            ArrayList list = bean.selectMemberList(box); //0.가입신청 1.승인 2.승인거부및 해지
            
            request.setAttribute("list", list);

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_MsMemActivity.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_MsMemActivity.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsMemberActivityPage()\r\n" + ex.getMessage());
        }
    }






    /**
    커뮤니티명 홍보등록 페이지로 이동할때
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performMsPrPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      명시적으로 box 객체를 넘겨준다
            //등급코드조회
            CommunityMsPrBean bean  = new CommunityMsPrBean();
            ArrayList list = bean.selectQuery(box); 
            request.setAttribute("list", list);

            ServletContext    sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/user/community/zu_MsPr.jsp");
            rd.forward(request, response);

            Log.info.println(this, box, "Dispatch to /learn/user/community/zu_MsPr.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsPrPage()\r\n" + ex.getMessage());
        }
    }



     /**
    커뮤니티 홍보등록
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performMsPrData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityMsPrBean bean = new CommunityMsPrBean();
            int isOk = bean.insertHongbo(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityMasterServlet";
            box.put("p_process", "moveprPage");
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {

                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityMasterServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsPrData()\r\n" + ex.getMessage());
        }
    }
    

     /**
    홍보 파일삭제
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performMsPrDeleteFileData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityMsPrBean bean = new CommunityMsPrBean();
            int isOk = bean.deleteSingleFile(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityMasterServlet";
            box.put("p_process", "moveprPage");
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {

                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityMasterServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsPrDeleteFileData()\r\n" + ex.getMessage());
        }
    }
     /**
    홍보 삭제
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performMsPrDeleteData(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            CommunityMsPrBean bean = new CommunityMsPrBean();
            int isOk = bean.deleteHongbo(box);

            String v_msg = "";
            String v_url = "/servlet/controller.community.CommunityMasterServlet";
            box.put("p_process", "moveprPage");
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {

                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on CommunityMasterServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMsMenuDeleteData()\r\n" + ex.getMessage());
        }
    }

}
