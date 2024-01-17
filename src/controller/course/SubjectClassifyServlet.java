package controller.course;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.contents.EduStartBean;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.credu.course.EduSystemChartBean;
import com.credu.course.SubjectClassifyBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings( { "unchecked", "serial" })
@WebServlet("/servlet/controller.course.SubjectClassifyServlet")
public class SubjectClassifyServlet extends javax.servlet.http.HttpServlet implements Serializable {

    /**
     * Pass get requests through to PerformTask
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     */
    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(req, res);
    }

    /**
     * doPost
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     */
    @Override
    public void doPost(HttpServletRequest req, HttpServletResponse res) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;
        String process = "";

        try {
            res.setContentType("text/html;charset=euc-kr");
            out = res.getWriter();
            box = RequestManager.getBox(req);
            process = box.getStringDefault("process", "listPage");

            System.out.println("process : " + process);
            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            if (!AdminUtil.getInstance().checkRWRight("SubjectClassifyServlet", process, out, box)) {
                return;
            }

            if (process.equals("listPage")) { // 목록 화면. 기본페이지
                this.performListPage(req, res, box, out);
            } else if (process.equals("registerPage")) { // 분류 등록 화면
                this.performRegisterPage(req, res, box, out);
            } else if (process.equals("register")) { // 분류 등록
                this.performRegister(req, res, box, out);
            } else if (process.equals("updatePage")) { // 분류 수정 화면
                this.performUpdatePage(req, res, box, out);
            } else if (process.equals("update")) { // 분류 수정
                this.performUpdate(req, res, box, out);
            } else if (process.equals("delete")) { // 삭제
                this.performDelete(req, res, box, out);
            } else if (process.equals("manageSubjectPage")) { // 분류별 과정 관리 화면
                this.performManageSubjectPage(req, res, box, out);
            } else if (process.equals("saveSubjectList")) { // 분류별 과정 관리 갱신
                this.performSaveSubjectList(req, res, box, out);
            } else if (process.equals("levelCodeList")) { // 하위코드 목록 갱신
                this.performLevelCodeList(req, box, out);
            } else if (process.equals("levelCodeUpdate")) { // 하위코드 수정
                this.performLevelCodeUpdate(req, res, box, out);
            } else if (process.equals("courseLevelPage")) { // 난이도 설정 화면
                this.performCourseLevelPage(req, res, box, out);
            } else if (process.equals("saveCourseLevel")) { // 난이도 저장
                this.performSaveCourseLevel(req, res, box, out);
            } else if (process.equals("nextCourseList")) { // 연계과정 및 과정 정보 조회
                this.performNextCoruseList(req, res, box, out);
            }


        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * SUBJECT CLASSIFICATION VIEW
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performListPage(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {
            req.setAttribute("requestbox", box);
            String dispatcherUrl = "/learn/admin/course/za_SubjectClassify_L.jsp";

            SubjectClassifyBean bean = new SubjectClassifyBean();
            ArrayList onlineList = bean.selectOnlineClassifyList();
            ArrayList categoryList = bean.selectSubjClassifyList("1000");
            ArrayList jobList = bean.selectSubjClassifyList("2000");
            ArrayList loadMapList1 = bean.selectLoadMapClassifyList1(box);
            ArrayList loadMapList2 = bean.selectLoadMapClassifyList2(box);
            ArrayList loadMapList3 = bean.selectLoadMapClassifyList3(box);
            ArrayList lvCdList = bean.selectCodeList("0121", 1, "");

            req.setAttribute("onlineList", onlineList);
            req.setAttribute("categoryList", categoryList);
            req.setAttribute("jobList", jobList);
            req.setAttribute("loadMapList1", loadMapList1);
            req.setAttribute("loadMapList2", loadMapList2);
            req.setAttribute("loadMapList3", loadMapList3);
            req.setAttribute("lvCdList", lvCdList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(dispatcherUrl);
            rd.forward(req, res);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * NEW SUBJECT CLASSIFICATION CREATE PAGE
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performRegisterPage(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {
            req.setAttribute("requestbox", box);
            String dispatcherUrl = "/learn/admin/course/za_SubjectClassify_I.jsp";

            if(box.getString("upperClsCd").equals("0000")){
                dispatcherUrl = "/learn/admin/course/za_OnlineClassify_I.jsp";

                SubjectClassifyBean bean = new SubjectClassifyBean();

                ArrayList gubunList1 = bean.selectCodeList("0110", 1, "");

                req.setAttribute("gubunList1", gubunList1);
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(dispatcherUrl);
            rd.forward(req, res);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performRegisterPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 분류 등록
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performRegister(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {
            String dispatcherURL = "/servlet/controller.course.SubjectClassifyServlet";

            SubjectClassifyBean bean = new SubjectClassifyBean();
            int resultCnt = 0;

            if(box.getString("upperClsCd").equals("0000")){
                resultCnt = bean.registerOnlineClassify(box);
            }else{
                resultCnt = bean.registerSubjectClassify(box);
            }

            String msg = "";
            box.put("process", "listPage");

            AlertManager alert = new AlertManager();

            if (resultCnt > 0) {
                msg = "insert.ok";
                alert.alertOkMessage(out, msg, dispatcherURL, box, false, false);
            } else if (resultCnt == -1) {
                msg = "insert.failDupe";
                alert.alertFailMessage(out, msg);
            } else {
                msg = "insert.fail";
                alert.alertFailMessage(out, msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performRegister()\r\n" + ex.getMessage());
        }
    }

    /**
     * 분류 수정 화면으로 이동한다.
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performUpdatePage(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {
            req.setAttribute("requestbox", box);
            String dispatcherURL = "/learn/admin/course/za_SubjectClassify_U.jsp";

            SubjectClassifyBean bean = new SubjectClassifyBean();

            if(box.getString("upperClsCd").equals("0000")){
                dispatcherURL = "/learn/admin/course/za_OnlineClassify_U.jsp";

                ArrayList gubunList1 = bean.selectCodeList("0110", 1, "");
                ArrayList gubunNmList = bean.selectGubunNmList(box);

                req.setAttribute("gubunList1", gubunList1);
                req.setAttribute("gubunNmList", gubunNmList);
            }else{
                DataBox subjClassify = bean.selectSubjClassify(box);
                req.setAttribute("subjClassify", subjClassify);
            }


            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(dispatcherURL);
            rd.forward(req, res);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 분류 내용을 수정한다.
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performUpdate(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {
            String dispatcherURL = "/servlet/controller.course.SubjectClassifyServlet";

            SubjectClassifyBean bean = new SubjectClassifyBean();
            int resultCnt = 0;

            if(box.getString("upperClsCd").equals("0000")){
                resultCnt = bean.updateOnlineClassify(box);
            }else{
                resultCnt = bean.updateSubjectClassify(box);
            }

            String msg = "";
            box.put("process", "listPage");

            AlertManager alert = new AlertManager();
            if (resultCnt > 0) {
                msg = "update.ok";
                alert.alertOkMessage(out, msg, dispatcherURL, box, false, false);
            } else {
                msg = "update.fail";
                alert.alertFailMessage(out, msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정 분류 삭제
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performDelete(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {
            String dispatcherURL = "/servlet/controller.course.SubjectClassifyServlet";

            SubjectClassifyBean bean = new SubjectClassifyBean();
            int resultCnt = 0;

            if(box.getString("upperClsCd").equals("0000")){
                resultCnt = bean.deleteOnlineClassify(box);
            }else{
                resultCnt = bean.deleteSubjectClassify(box);
            }

            String msg = "";
            box.put("process", "listPage");

            AlertManager alert = new AlertManager();
            if (resultCnt > 0) {
                msg = "delete.ok";
                alert.alertOkMessage(out, msg, dispatcherURL, box, false, false);
            } else {
                msg = "삭제도중 오류가 발생하였습니다.";
                alert.alertFailMessage(out, msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }

    /**
     * 분류별 과정 관리 화면으로 이동
     *
     * @param req
     * @param res
     * @param box
     * @param out
     * @throws Exception
     */
    public void performManageSubjectPage(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {
            String dispatcherURL = "/learn/admin/course/za_SubjectClassify_M.jsp";

            SubjectClassifyBean bean = new SubjectClassifyBean();

            ArrayList allSubjectList = null;
            ArrayList classifiedSubjectList = null;
            ArrayList allGoldClassList = null;
            ArrayList classifiedGoldClassList = null;

            if(box.getString("upperClsCd").equals("0000")){
                classifiedSubjectList = bean.onlineSubjectList(box);
                allSubjectList = bean.onlineAllSubjectList(box);
                dispatcherURL = "/learn/admin/course/za_OnlineClassify_M.jsp";
            }else{
                classifiedSubjectList = bean.classifiedSubjectList(box);
                allSubjectList = bean.selectAllSubjectList(box);
            }

            ArrayList gubunNmList = bean.selectGubunNmList(box);
            allGoldClassList = bean.onlineAllGoldClassList(box);
            classifiedGoldClassList = bean.onlineGoldClassList(box);

            req.setAttribute("gubunNmList", gubunNmList);
            req.setAttribute("allSubjectList", allSubjectList);
            req.setAttribute("classifiedSubjectList", classifiedSubjectList);
            req.setAttribute("allGoldClassList", allGoldClassList);
            req.setAttribute("classifiedGoldClassList", classifiedGoldClassList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(dispatcherURL);
            rd.forward(req, res);
        } catch (Exception e) {
            e.printStackTrace();
            ErrorManager.getErrorStackTrace(e, out);
            throw new Exception("performManageSubjectPage" + e.getMessage());
        }
    }

    /**
     * 분류별 과정목록 저장
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSaveSubjectList(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {

            SubjectClassifyBean bean = new SubjectClassifyBean();
            int resultCnt = 0;
            if(box.getString("upperClsCd").equals("0000")){
                resultCnt = bean.saveOnlineList(box);
            }else{
                resultCnt = bean.saveSubjectList(box);
            }

            StringBuilder sb = new StringBuilder();

            sb.append("{\"resultCnt\": " + resultCnt + "}");

            out.write(sb.toString());
            out.flush();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSaveSubjectList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 하위 분류코드 목록
     * @param request
     * @param response
     * @param box
     * @param out
     */
    public void performLevelCodeList(HttpServletRequest request, RequestBox box, PrintWriter out){
        try {
            request.setAttribute("requestbox", box);
            SubjectClassifyBean bean = new SubjectClassifyBean();

            if(box.getInt("p_ordr") > 3){
                return;
            }

            //하위 코드 목록
            ArrayList levelCodeList = bean.selectCodeList("0110", box.getInt("p_ordr"), box.getString("p_hcd"));

            String jsonString = "";
            if(levelCodeList != null && levelCodeList.size() > 0){
                jsonString = JSONValue.toJSONString(levelCodeList);
            }
            out.write(jsonString);

        } catch (Exception e) {
            ErrorManager.getErrorStackTrace(e, out);
        }
    }

    /**
     * 난이도 유형 코드를 수정한다.
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performLevelCodeUpdate(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {
            String dispatcherURL = "/servlet/controller.course.SubjectClassifyServlet";

            SubjectClassifyBean bean = new SubjectClassifyBean();

            int resultCnt = bean.updateLevelCode(box);

            String msg = "";
            box.put("process", "listPage");

            AlertManager alert = new AlertManager();
            if (resultCnt > 0) {
                msg = "update.ok";
                alert.alertOkMessage(out, msg, dispatcherURL, box, false, false);
            } else {
                msg = "update.fail";
                alert.alertFailMessage(out, msg);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performLevelCodeUpdate()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정별 난이도 설정 화면으로 이동
     *
     * @param req
     * @param res
     * @param box
     * @param out
     * @throws Exception
     */
    public void performCourseLevelPage(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {
            String dispatcherURL = "/learn/admin/course/za_OnlineClassifyLv_L.jsp";

            SubjectClassifyBean bean = new SubjectClassifyBean();

            //난이도 코드 목록
            ArrayList levelCodeList = bean.selectCodeList("0121", 2, box.getString("hcd"));

            //연결된 정규과정 목록
            ArrayList classifiedSubjectList = bean.onlineSubjectList(box);

            //연결된 열린강좌 목록
            ArrayList classifiedGoldClassList = bean.onlineGoldClassList(box);

            //과정유형
            ArrayList gubunNmList = bean.selectGubunNmList(box);

            req.setAttribute("levelCodeList", levelCodeList);
            req.setAttribute("classifiedSubjectList", classifiedSubjectList);
            req.setAttribute("classifiedGoldClassList", classifiedGoldClassList);
            req.setAttribute("gubunNmList", gubunNmList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(dispatcherURL);
            rd.forward(req, res);

        } catch (Exception e) {
            e.printStackTrace();
            ErrorManager.getErrorStackTrace(e, out);
            throw new Exception("performCourseLevelPage" + e.getMessage());
        }
    }

    /**
     * 과정 난이도를 저장한다.
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSaveCourseLevel(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {
            String dispatcherURL = "/servlet/controller.course.SubjectClassifyServlet";

            SubjectClassifyBean bean = new SubjectClassifyBean();

            int sResult = bean.courseSLvSave(box);
            int gResult = bean.courseGLvSave(box);
            int result = 0;

            if((sResult + gResult) > 0){
                result = 1;
            }else{
                result = 0;
            }

            StringBuilder sb = new StringBuilder();

            sb.append("{\"result\": " + result + "}");

            out.write(sb.toString());
            out.flush();

        } catch (Exception ex) {
            ex.printStackTrace();
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSaveCourseLevel()\r\n" + ex.getMessage());
        }
    }

    /**
     * 과정 정보 및 연계과정 조회
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performNextCoruseList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        JSONObject jsonObj = new JSONObject();
        ArrayList resultInfo = new ArrayList();
        ArrayList resultList = new ArrayList();
        ArrayList jsonInfo = new ArrayList();
        ArrayList jsonList = new ArrayList();
        DataBox dbox = null;
        Map map = null;

        try {
            request.setAttribute("requestbox", box);

            SubjectClassifyBean bean = new SubjectClassifyBean();

            resultInfo = bean.selectCourseInfo(box);
            resultList = bean.selectCourseNextList(box);

            jsonObj.put("resInfo", "");
            jsonObj.put("resList", "");

            if ( resultInfo.size() > 0 ) {
                for( int i = 0; i < resultInfo.size() ; i++ ) {
                    dbox = (DataBox)resultInfo.get(i);

                    map = new HashMap();
                    map.put("courseId", dbox.getString("d_course_id"));
                    map.put("year", dbox.getString("d_year"));
                    map.put("subjseq", dbox.getString("d_subjseq"));
                    map.put("grcode", dbox.getString("d_grcode"));
                    map.put("gyear", dbox.getString("d_gyear"));
                    map.put("grseq", dbox.getString("d_grseq"));
                    map.put("courseNm", dbox.getString("d_course_nm"));
                    map.put("crdate", dbox.getString("d_crdate"));
                    map.put("d1", dbox.getString("d_d1"));
                    map.put("d2", dbox.getString("d_d2"));
                    map.put("lv", dbox.getString("d_lv"));
                    map.put("lvCd", dbox.getString("d_lv_cd"));
                    map.put("img", dbox.getString("d_img"));
                    map.put("vodUrl", dbox.getString("d_vod_url"));
                    map.put("widthS", dbox.getString("d_width_s"));
                    map.put("heightS", dbox.getString("d_height_s"));

                    jsonInfo.add(map);
                }

                jsonObj.put("resInfo", jsonInfo);
            }

            if ( resultList.size() > 0 ) {
                for( int i = 0; i < resultList.size() ; i++ ) {
                    dbox = (DataBox)resultList.get(i);

                    map = new HashMap();
                    map.put("type", dbox.getString("d_type"));
                    map.put("courseId", dbox.getString("d_course_id"));
                    map.put("year", dbox.getString("d_year"));
                    map.put("courseNm", dbox.getString("d_course_name"));
                    map.put("imgPath", dbox.getString("d_img_path"));
                    map.put("g2Cd", dbox.getString("d_g2cd"));
                    map.put("g2Nm", dbox.getString("d_g2nm"));
                    map.put("g3Cd", dbox.getString("d_g3cd"));
                    map.put("d3Nm", dbox.getString("d_g3nm"));
                    map.put("lvCd", dbox.getString("d_lvcd"));
                    map.put("lvNm", dbox.getString("d_lvnm"));
                    map.put("vodUrl", dbox.getString("d_vod_url"));
                    map.put("widthS", dbox.getString("d_width_s"));
                    map.put("heightS", dbox.getString("d_height_s"));
                    map.put("subjSeq", dbox.getString("d_subjseq"));
                    map.put("gYear", dbox.getString("d_gyear"));
                    map.put("grSeq", dbox.getString("d_grseq"));
                    map.put("crDate", dbox.getString("d_crdate"));

                    jsonList.add(map);
                }

                jsonObj.put("resList", jsonList);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performLessonCompleteChk()\r\n" + ex.getMessage());
        }

        out.print(jsonObj.toJSONString().replace("\\", ""));
        out.flush();
    }

}