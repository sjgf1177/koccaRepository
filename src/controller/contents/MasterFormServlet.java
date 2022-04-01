    //**********************************************************
//1. 제      목: 마스터폼 관리 SERVLET
//2. 프로그램명: MasterFormServlet.java
//3. 개      요: 마스터폼 관리 SERVLET
//4. 환      경: JDK 1.4
//5. 버      젼: 0.1
//6. 작      성: S.W.Kang 2004. 12. 5
//7. 수      정:
//
//**********************************************************
package controller.contents;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.contents.MasterFormBean;
import com.credu.contents.MasterFormData;
import com.credu.contents.MfLessonDataSub;
import com.credu.library.AlertManager;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.contents.MasterFormServlet")
public class MasterFormServlet extends HttpServlet implements Serializable {

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
        RequestBox  box = null;
        String v_process = "";

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getStringDefault("p_process","listPage");

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }
            ///////////////////////////////////////////////////////////////////
            // 권한 check 루틴 VER 0.2 - 2003.08.10
            if (!AdminUtil.getInstance().checkRWRight("MasterFormServlet", v_process, out, box)) {
                return;
            }
            ///////////////////////////////////////////////////////////////////
            if (v_process.equals("listPage")) {                 //
                this.performListPage(request, response, box, out);
			} else if (v_process.equals("listPageDetail")) {                       //마스터폼 상세정보 리스트
                this.performListPageDetail(request, response, box, out);
            } else if (v_process.equals("updatePage")) {                           //마스터폼 정보 수정화면
                this.performUpdateMasterFormPage(request, response, box, out);
            } else if (v_process.equals("updateSave")) {                           //마스터폼 정보 저장
                this.performUpdateMasterForm(request, response, box, out);
            } else if (v_process.equals("menuListPage")) {                         //마스터폼 메뉴 리스트화면
                this.performMenuListPage(request, response, box, out);
            } else if (v_process.equals("menuImageInsert")) {                      //마스터폼 메뉴이미지 등록
                this.performMenuImageInsert(request, response, box, out);

            } else if (v_process.equals("updateModulePage")) {                     //마스터폼 모듈정보 수정화면
                this.performUpdateMasterFormModulePage(request, response, box, out);
            } else if (v_process.equals("updateModule")) {                         //마스터폼 모듈정보 저장
                this.performUpdateMasterFormModule(request, response, box, out);
            } else if (v_process.equals("updateLessonPage")) {                     //마스터폼 레슨정보 수정화면
                this.performUpdateMasterFormLessonPage(request, response, box, out);
            } else if (v_process.equals("updateLesson")) {                         //마스터폼 레슨정보 저장
                this.performUpdateMasterFormLesson(request, response, box, out);
            } else if (v_process.equals("updateDatePage")) {                     //마스터폼 날자정보 수정화면
                this.performUpdateMasterFormDatePage(request, response, box, out);
            } else if (v_process.equals("updateDate")) {                         //마스터폼 날자정보 저장
                this.performUpdateMasterFormDate(request, response, box, out);
				
            } else if (v_process.equals("subjObjectFsetPage")) {                   //OBC.Lesson에 Object 연결 FrameSet Page
                this.performSubjObjectFramesetPage(request, response, box, out);
            } else if (v_process.equals("subjObjectPage")) {                       //OBC.Lesson에 Object연결 Body Page
                this.performSubjObjectPage(request, response, box, out);
            } else if (v_process.equals("subjObjectSave")) {                       //OBC.Lesson에 Object연결 저장
                this.performSaveSubjObject(request, response, box, out);
            } else if (v_process.equals("previewObjectSave")) {                    //OBC.맛보기 Object저장
                this.performSavePreviewObject(request, response, box, out);
            } else if (v_process.equals("objectBranch")) {                         //OBC.Lesson-Branch에 Object연결 화면
                this.performSubjObjectBranchPage(request, response, box, out);
            } else if (v_process.equals("changeObjectPage")) {                     //OBC.Lesson의Object 연결속성변경화면
                this.performChangeObjectAttPage(request, response, box, out);
            } else if (v_process.equals("changeObjectSave")) {                     //OBC.Lesson의Object 연결속성저장
                this.performSaveObjectAtt(request, response, box, out);
            } else if (v_process.equals("DeleteOBCLesson")) {                      //OBC.Lesson 삭제
                this.performDeleteOBCLesson(request, response, box, out);

            } else if (v_process.equals("MasterSCOPage")) {                        //SCO. List Page
                this.performScoContent(request, response, box, out);
            } else if (v_process.equals("subjSCOObjectFsetPage")) {                //SCO.Lesson에 SCO Object 연결 FrameSet Page
                this.performSubjSCOObjectFramesetPage(request, response, box, out);
            } else if (v_process.equals("subjSCOObjectPage")) {                    //SCO.Lesson에 SCO Object연결 Body Page
                this.performSubjSCOObjectPage(request, response, box, out);
            } else if (v_process.equals("scoContentListPage")) {                   //SCO. PACKAGE, LO page
                this.performScoConentPackageList(request, response, box, out);
            } else if (v_process.equals("scoContentInsertPage")) {                 //SCO. PACKAGE, LO 과정 INSERT
                this.performScoConentInsertPackageList(request, response, box, out);
            } else if (v_process.equals("scoLOContentInsertPage")) {               //SCO. PACKAGE, LO 과정 INSERT
                this.performScoConentInsertLOList(request, response, box, out);
            } else if (v_process.equals("scoSCOMappingContentListPage")) {         //SCO. PACKAGE, LO 과정 INSERT
                this.performScoMappingConentLOList(request, response, box, out);
            } else if (v_process.equals("scoDeleteContentListPage")) {             //SCO. PACKAGE, LO 과정 삭제
                this.performScoDeleteConentLOList(request, response, box, out);
            } else if (v_process.equals("scoDeleteOneContentListPage")) {          //SCO. PACKAGE, LO 과정 삭제
                this.performScoOneDeleteConentLOList(request, response, box, out);
            } else if (v_process.equals("scogoHighContentListPage")) {             //SCO. LO 과정 상위 순서
                this.performScoGoHighConentLOList(request, response, box, out);
            } else if (v_process.equals("scogoLowContentListPage")) {              //SCO. LO 과정 하위 순서
                this.performScoGoLowConentLOList(request, response, box, out);
            } else if (v_process.equals("scoSCOSaveContentListPage")) {            //SCO. LO 저장
                this.performScoSaveConentLOList(request, response, box, out);
			} else if (v_process.equals("contentscolocateview")) {            //SCO. LO 저장
                this.performScoLocateView(request, response, box, out);
            } else if (v_process.equals("lessonFromExcel")) {                  //Lesson에서 엑셀로 콘텐츠 올리기
                this.performLessonFromExcel(request, response, box, out);
            } else if (v_process.equals("excelInsertToDB")) {                  //Lesson에서 엑셀로 콘텐츠 올리기
                this.performIExcelIsertToDB(request, response, box, out);
            }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
    마스터폼 리스트 조회
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/contents/za_MasterForm_L.jsp";
            if (box.getString("p_action").equals("go")) {
                MasterFormBean bean = new MasterFormBean();
                ArrayList list1 = bean.SelectMasterFormList(box);
                request.setAttribute("MasterFormList", list1);
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

	    /**
    마스터폼 상세정보리스트 조회
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performListPageDetail(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/contents/za_MasterFormDatail_L.jsp";
            if (box.getString("p_action").equals("go")) {
                MasterFormBean bean = new MasterFormBean();
                ArrayList list1 = bean.SelectMasterFormList(box);
                request.setAttribute("MasterFormList", list1);
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPageDetail()\r\n" + ex.getMessage());
        }
    }

    /**
    PAGE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performUpdateMasterFormPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            request.setAttribute("requestbox", box);
            String v_url = "/learn/admin/contents/za_MasterForm_U.jsp";

            MasterFormBean bean = new MasterFormBean();

            MasterFormData  data = bean.SelectMasterFormData(box);  //마스터폼 정보
            request.setAttribute("MasterFormData", data);
            ArrayList       data1= bean.SelectMfMenuList(box);      //마스터폼 마스터메뉴목록
            request.setAttribute("MfMenuData", data1);
            ArrayList       data2= bean.SelectMfSubjList(box);      //마스터폼 과정메뉴목록
            request.setAttribute("MfSubjData", data2);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdateMasterFormPage()\r\n" + ex.getMessage());
        }
    }

    /**
    Materform정보 저장
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    **/
    public void performUpdateMasterForm(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            String v_url  = "/servlet/controller.contents.MasterFormServlet";

            MasterFormBean bean = new MasterFormBean();
            int isOk = bean.UpdateMasterForm(box);

            String v_msg = "";
            box.put("p_process", "updatePage");

            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
    마스터폼 메뉴리스트 조회
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performMenuListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/contents/za_MasterFormMenu_L.jsp";

            MasterFormBean bean = new MasterFormBean();

            MasterFormData  data = bean.SelectMasterFormData(box);  //마스터폼 정보
            request.setAttribute("MasterFormData", data);

            ArrayList list1 = bean.SelectMfMenuList(box);
            request.setAttribute("MasterFormMfMenu", list1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

	
    /**
    마스터폼 메뉴이미지 등록
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performMenuImageInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url  = "/servlet/controller.contents.MasterFormServlet";

            MasterFormBean bean = new MasterFormBean();
            String result = bean.insertMenuImage(box);

            String v_msg = "";
            box.put("p_process", "menuListPage");

            AlertManager alert = new AlertManager();
            if(result.equals("OK")) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performMenuImageInsert()\r\n" + ex.getMessage());
        }
    }

    /**
    마스터폼 모듈정보 수정화면
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performUpdateMasterFormModulePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            request.setAttribute("requestbox", box);
            String v_url = "/learn/admin/contents/za_MasterFormModule_U.jsp";

            MasterFormBean bean = new MasterFormBean();

            MasterFormData  data = bean.SelectMasterFormData(box);  //마스터폼 정보
            request.setAttribute("MasterFormData", data);
            ArrayList       data1= bean.SelectMfModuleList(box);    //마스터폼 Module목록
            request.setAttribute("MfModuleData", data1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdateMasterFormModulePage()\r\n" + ex.getMessage());
        }
    }

    /**
    Materform Module정보 저장
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    **/
    public void performUpdateMasterFormModule(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            String v_url  = "/servlet/controller.contents.MasterFormServlet";

            MasterFormBean bean = new MasterFormBean();
            int isOk = bean.UpdateMasterFormModule(box);

            String v_msg = "";
            box.put("p_process", "updateModulePage");
            box.put("p_cnt_module", "0");

            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdateMasterFormModule()\r\n" + ex.getMessage());
        }
    }

    /**
    마스터폼 레슨정보 수정화면
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdateMasterFormLessonPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            request.setAttribute("requestbox", box);
            String v_url = "/learn/admin/contents/za_MasterFormLesson_U.jsp";

            MasterFormBean bean = new MasterFormBean();

            MasterFormData  data = bean.SelectMasterFormData(box);  //마스터폼 정보
            request.setAttribute("MasterFormData", data);

            box.put("temp_contenttype",data.getContenttype());      // Lesson목록생성시 사용할 값 Setting

            if(data.getContenttype().equals("N") || data.getContenttype().equals("M"))
                v_url = "/learn/admin/contents/za_MasterFormLesson_U.jsp";
            else if (data.getContenttype().equals("S")) {
                v_url = "/learn/admin/contents/za_MasterFormLessonSCO_U.jsp";
            } else {
                v_url = "/learn/admin/contents/za_MasterFormLessonOBC_U.jsp";
            }

            ArrayList       data1= bean.SelectMfModuleList(box);    //마스터폼 Module목록
            request.setAttribute("MfModuleData", data1);
            ArrayList       data2= bean.SelectMfLessonList(box);    //마스터폼 Lesson목록
            request.setAttribute("MfLessonData", data2);
            ArrayList       data3= bean.SelectMfTutorList(box);     //마스터폼 강사목록
            request.setAttribute("MfTutorData", data3);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdateMasterFormLessonPage()\r\n" + ex.getMessage());
        }
    }



    /**
    Materform Lesson정보 저장
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    **/
    @SuppressWarnings("unchecked")
	public void performUpdateMasterFormLesson(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            String v_url  = "/servlet/controller.contents.MasterFormServlet";

            MasterFormBean bean = new MasterFormBean();
            int isOk = bean.UpdateMasterFormLesson(box);

            String v_msg = "";
            box.put("p_process", "updateLessonPage");
            box.put("p_cnt_lesson", "0");

            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdateMasterFormModule()\r\n" + ex.getMessage());
        }
    }
	

    /**
    마스터폼 날자정보 수정화면
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdateMasterFormDatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            request.setAttribute("requestbox", box);
            String v_url = "/learn/admin/contents/za_MasterFormDate_U.jsp";

            MasterFormBean bean = new MasterFormBean();

            MasterFormData  data = bean.SelectMasterFormData(box);  //마스터폼 정보
            request.setAttribute("MasterFormData", data);
			
            box.put("temp_contenttype",data.getContenttype());      // Lesson목록생성시 사용할 값 Setting

            ArrayList list = bean.SelectMasterFormDate(box);        //마스터폼 날자정보
            request.setAttribute("MfDateDate", list);
			
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdateMasterFormDatePage()\r\n" + ex.getMessage());
        }
    }

    /**
    마스터폼 날자정보 저장
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    **/
    public void performUpdateMasterFormDate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            String v_url  = "/servlet/controller.contents.MasterFormServlet";

            MasterFormBean bean = new MasterFormBean();
            int isOk = bean.UpdateMasterFormDate(box);

            String v_msg = "";
            box.put("p_process", "updateDatePage");

            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdateMasterFormModule()\r\n" + ex.getMessage());
        }
    }	
	
	
    /**
    PAGE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */

    public void performUpdateMasterFormBranchPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            request.setAttribute("requestbox", box);
            String v_url = "/learn/admin/contents/za_MasterFormBranch_U.jsp";

            MasterFormBean bean = new MasterFormBean();

            MasterFormData  data = bean.SelectMasterFormData(box);  //마스터폼 정보
            request.setAttribute("MasterFormData", data);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdateMasterFormModulePage()\r\n" + ex.getMessage());
        }
    }


    /**
    OBC.Lesson에 Object 연결 FrameSet Page
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSubjObjectFramesetPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            request.setAttribute("requestbox", box);
            String v_url ="";

            box.put("p_subj", box.getString("p_subj"));
            box.put("p_lesson", box.getString("p_lesson"));



            v_url = "/learn/admin/contents/za_MasterFormSubjObject_Fset.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjObjectFramesetPage()\r\n" + ex.getMessage());
        }
    }

    /**
    SCO.Lesson에 SCO Object 연결 FrameSet Page
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSubjSCOObjectFramesetPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            request.setAttribute("requestbox", box);
            String v_url ="";

            box.put("p_subj", box.getString("p_subj"));

            v_url = "/learn/admin/contents/za_MasterFormSubjSCOObject_Fset.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjSCOObjectFramesetPage()\r\n" + ex.getMessage());
        }
    }


    /**
    Materform [OBC]Lesson별 Object정보 PAGE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSubjObjectPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            request.setAttribute("requestbox", box);
            String v_url ="";

            MasterFormBean bean = new MasterFormBean();
            MasterFormData  data = bean.SelectMasterFormData(box);  //마스터폼 정보
            request.setAttribute("MasterFormData", data);

            box.put("p_subj", box.getString("p_subj"));
            box.put("p_module", box.getString("p_module"));
            box.put("p_lesson", box.getString("p_lesson"));

            v_url = "/learn/admin/contents/za_MasterFormSubjObject_U.jsp";

            ArrayList       data1= bean.SelectSubjObjectList(box);
            request.setAttribute("MfSubjObject", data1);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjObjectPage()\r\n" + ex.getMessage());
        }
    }

     /**
    Materform [OBC]Lesson별 Object정보 PAGE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSubjSCOObjectPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            request.setAttribute("requestbox", box);
            String v_url ="";

            v_url = "/learn/admin/contents/za_MasterFormSubjSCO_U.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjObjectPage()\r\n" + ex.getMessage());
        }
    }

    /**
    Materform [OBC]Lesson별 Object정보 저장
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    **/
    public void performSaveSubjObject(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            String v_url  = "/servlet/controller.contents.MasterFormServlet";

            MasterFormBean bean = new MasterFormBean();
            int isOk = bean.SaveSubjObject(box);

            String v_msg = "";
            box.put("p_process", "subjObjectPage");

            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }else {
                if (isOk==-99)  v_msg = "학습데이터가 존재하므로 변경이 불가능합니다.";
                else            v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdateMasterFormModule()\r\n" + ex.getMessage());
        }
    }
    /**
    Materform [OBC]맛보기 Object정보 저장
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    **/
    public void performSavePreviewObject(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            String v_url  = "/servlet/controller.contents.MasterFormServlet";

            MasterFormBean bean = new MasterFormBean();
            int isOk = bean.SavePreviewObject(box);
System.out.println("isOk============="+ isOk);
            String v_msg = "";
            box.put("p_process", "updateLessonPage");

            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdateMasterFormModule()\r\n" + ex.getMessage());
        }
    }
    /**
    Materform [OBC]Lesson, Branch Object정보 PAGE
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSubjObjectBranchPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            request.setAttribute("requestbox", box);
            String v_url ="";

            MasterFormBean bean = new MasterFormBean();
            MasterFormData  data = bean.SelectMasterFormData(box);  //마스터폼 정보
            request.setAttribute("MasterFormData", data);

            box.put("p_subj", box.getString("p_subj"));
            box.put("p_module", box.getString("p_module"));
            box.put("p_lesson", box.getString("p_lesson"));
            box.put("p_branch", box.getString("p_branch"));

            v_url = "/learn/admin/contents/za_MasterFormSubjObjectBranch_U.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjObjectPage()\r\n" + ex.getMessage());
        }
    }
    /**
    Materform [OBC]Lesson의Object 연결속성변경화면
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performChangeObjectAttPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            request.setAttribute("requestbox", box);
            String v_url ="";

            MasterFormBean bean = new MasterFormBean();
            MasterFormData  data = bean.SelectMasterFormData(box);  //마스터폼 정보
            request.setAttribute("MasterFormData", data);

            box.put("p_subj", box.getString("p_subj"));
            box.put("p_module", box.getString("p_module"));
            box.put("p_lesson", box.getString("p_lesson"));
            box.put("p_branch", box.getString("p_branch"));

            v_url = "/learn/admin/contents/za_MasterFormLessonObject_U.jsp";

            MfLessonDataSub     data1= bean.SelectLessonObject(box);
            request.setAttribute("MfLessonDataSub", data1);


            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjObjectPage()\r\n" + ex.getMessage());
        }
    }
    /**Materform [OBC]Lesson의Object 연결속성저장
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    **/
    public void performSaveObjectAtt(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            String v_url  = "/servlet/controller.contents.MasterFormServlet";

            MasterFormBean bean = new MasterFormBean();
            int isOk = bean.SaveLessonObject(box);

            String v_msg = "";
            box.put("p_process", "updateLessonPage");

            AlertManager alert = new AlertManager();


            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box, true, true);
            }else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdateMasterFormModule()\r\n" + ex.getMessage());
        }
    }

    /**Materform [OBC]Lesson 삭제
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    **/
    public void performDeleteOBCLesson(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{
            String v_url  = "/servlet/controller.contents.MasterFormServlet";

            MasterFormBean bean = new MasterFormBean();
            int isOk = bean.DeleteOBCLesson(box);

            String v_msg = "";
            box.put("p_process", "updateLessonPage");

            AlertManager alert = new AlertManager();


            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDeleteOBCLesson()\r\n" + ex.getMessage());
        }
    }


    /**Materform [SCO LIST]
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    **/
    public void performScoContent(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{

            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/contents/za_MasterFormSCO_L.jsp";
            box.put("p_scolocate", "");

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDeleteOBCLesson()\r\n" + ex.getMessage());
        }
    }


    /**Materform [SCO LIST]
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    **/
    public void performScoConentPackageList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{

            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/contents/za_MasterFormSCO_L.jsp";

            System.out.println(v_return_url);

            if (box.getString("p_action").equals("go")) {
                MasterFormBean bean = new MasterFormBean();
                ArrayList list1 = bean.SelectObjectList(box);
                request.setAttribute("ObjectList", list1);
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDeleteOBCLesson()\r\n" + ex.getMessage());
        }
    }









    /**Materform [SCO Package 과정 매핑]
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    **/
    public void performScoConentInsertPackageList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{

            request.setAttribute("requestbox", box);
            String v_url = "/learn/admin/contents/za_MasterFormSCO_L.jsp";

            MasterFormBean bean = new MasterFormBean();
            int isOk = bean.SelectSCOObjectPackageList(box);

    //      ArrayList list1 = bean.SelectObjectList(box);
    //      request.setAttribute("ObjectList", list1);


            String v_msg = "";
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "등록되었습니다.";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }else {
                if (isOk==-99)  v_msg = "이미 등록된 과정입니다.";
                else            v_msg = "등록하는데 실패했습니다.";
                alert.alertFailMessage(out, v_msg);
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDeleteOBCLesson()\r\n" + ex.getMessage());
        }
    }

    /**Materform [SCO LO 과정 매핑]
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    **/
    public void performScoConentInsertLOList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{

            request.setAttribute("requestbox", box);
            String v_url = "/learn/admin/contents/za_MasterFormSCO_L.jsp";

            MasterFormBean bean = new MasterFormBean();
            int isOk = bean.SelectSCOObjectLOList(box);

    //      ArrayList list1 = bean.SelectObjectList(box);
    //      request.setAttribute("ObjectList", list1);

            String v_msg = "";
            AlertManager alert = new AlertManager();
            if(isOk > 0) {
                v_msg = "등록되었습니다.";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }else {
                if (isOk==-99)  v_msg = "이미 등록된 LO입니다.";
                else            v_msg = "등록하는데 실패했습니다.";
                alert.alertFailMessage(out, v_msg);
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDeleteOBCLesson()\r\n" + ex.getMessage());
        }
    }

    /**Materform [SCO LIST]
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    **/
    public void performScoMappingConentLOList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{

            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/contents/za_MasterFormSubjSCO_U.jsp";

            if (box.getString("p_action").equals("go")) {
                MasterFormBean bean = new MasterFormBean();

                ArrayList       data1= bean.SelectMfModuleList(box);    //마스터폼 Module목록
                request.setAttribute("MfModuleData", data1);

                ArrayList list1 = bean.SelectMappingLOList(box);
                request.setAttribute("ObjectList", list1);
            }

            box.put("p_job", "mapping");

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDeleteOBCLesson()\r\n" + ex.getMessage());
        }
    }


    /**Materform [SCO All Delete]
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    **/
    public void performScoDeleteConentLOList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{

            request.setAttribute("requestbox", box);
            String v_url = "/learn/admin/contents/za_MasterFormSubjSCO_U.jsp";
            int isOk = 0;

            if (box.getString("p_action").equals("go")) {
                MasterFormBean bean = new MasterFormBean();
                String results =  bean.DeleteMappingLOList(box);

                String v_msg = "";
                box.put("p_job", "mapping");

                AlertManager alert = new AlertManager();
                if(results.equals("OK")) {
                    v_msg = "delete.ok";
                    alert.alertOkMessage(out, v_msg, v_url, box);
                }else {
                    v_msg = results;
                    alert.alertFailMessage(out, v_msg);
                }
            }

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDeleteOBCLesson()\r\n" + ex.getMessage());
        }
    }

    /**Materform [SCO One Delete]
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    **/
    public void performScoOneDeleteConentLOList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{

            request.setAttribute("requestbox", box);
            String v_url = "/learn/admin/contents/za_MasterFormSubjSCO_U.jsp";
            int isOk = 0;

            if (box.getString("p_action").equals("go")) {
                MasterFormBean bean = new MasterFormBean();
                String results =  bean.DeleteOneMappingLOList(box);

                String v_msg = "";

                box.put("p_job", "mapping");

                AlertManager alert = new AlertManager();
                if(results.equals("OK")) {
                    v_msg = "delete.ok";
                    alert.alertOkMessage(out, v_msg, v_url, box);
                }else {
                    v_msg = results;
                    alert.alertFailMessage(out, v_msg);
                }
            }

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDeleteOBCLesson()\r\n" + ex.getMessage());
        }
    }

    /**Materform [goHigh]
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    **/
    public void performScoGoHighConentLOList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{

            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/contents/za_MasterFormSubjSCO_U.jsp";
            int isOk = 0;

            if (box.getString("p_action").equals("go")) {
                MasterFormBean bean = new MasterFormBean();

                isOk = bean.ChangeOrderHighLOList(box);

                ArrayList       data1= bean.SelectMfModuleList(box);    //마스터폼 Module목록
                request.setAttribute("MfModuleData", data1);

                ArrayList list1 = bean.SelectMappingLOList(box);
                request.setAttribute("ObjectList", list1);
            }
            box.put("p_job", "mapping");

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDeleteOBCLesson()\r\n" + ex.getMessage());
        }
    }

    /**Materform [goLow]
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    **/
    public void performScoGoLowConentLOList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{

            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/contents/za_MasterFormSubjSCO_U.jsp";
            int isOk = 0;

            if (box.getString("p_action").equals("go")) {
                MasterFormBean bean = new MasterFormBean();

                isOk = bean.ChangeOrderLowLOList(box);

                ArrayList       data1= bean.SelectMfModuleList(box);    //마스터폼 Module목록
                request.setAttribute("MfModuleData", data1);

                ArrayList list1 = bean.SelectMappingLOList(box);
                request.setAttribute("ObjectList", list1);
            }
            box.put("p_job", "mapping");

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDeleteOBCLesson()\r\n" + ex.getMessage());
        }
    }

    /**Materform 저장
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    **/
    public void performScoSaveConentLOList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{

            int isOk = 0;

            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/contents/za_MasterFormSubjSCO_U.jsp";

            if (box.getString("p_action").equals("go")) {
                MasterFormBean bean = new MasterFormBean();

                isOk = bean.SCOSaveConentList(box);

                box.put("p_job", "mappingall");

                String v_msg = "";
                AlertManager alert = new AlertManager();
                if(isOk > 0) {
                    v_msg = "등록되었습니다.";
                    alert.alertOkMessage(out, v_msg, v_return_url , box);
                }else {
                    v_msg = "등록하는데 실패했습니다.";
                    alert.alertFailMessage(out, v_msg);
                }
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDeleteOBCLesson()\r\n" + ex.getMessage());
        }
    }

	 /**Materform 저장
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    **/
    public void performScoLocateView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
        try{

            int isOk = 0;

            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/contents/za_ScoContentsLocateView.jsp";

            
       //     MasterFormBean bean = new MasterFormBean();

		//	ArrayList  data1= bean.SelectScoLocateView(box);
		//	request.setAttribute("soclocate", data1);
  

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performScoLocateView()\r\n" + ex.getMessage());
        }
    }

    public void performLessonFromExcel(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception
    {
        try{

            int isOk = 0;

            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/contents/za_LessonFromExcel.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performScoLocateView()\r\n" + ex.getMessage());
        }
    }

    public void performIExcelIsertToDB(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception
    {
        try {
            request.setAttribute("requestbox", box);
            String v_return_url = "/learn/admin/contents/za_excelFileToDB.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_return_url);
            rd.forward(request, response);
		 }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertFileToDB()\r\n" + ex.getMessage());
        }
    }
}
