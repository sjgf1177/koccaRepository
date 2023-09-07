//**********************************************************
//1. 제      목: MasterForm(학습창) SERVLET
//2. 프로그램명: EduStart.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: LeeSuMin 2003. 08. 19
//7. 수      정:
//
//**********************************************************
package controller.contents;

import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.*;
import com.credu.mobile.myclass.MyClassBean;
import org.dom4j.*;
import org.dom4j.io.SAXReader;
import org.json.simple.JSONObject;

import com.credu.beta.BetaEduStartBean;
import com.credu.beta.BetaMasterFormBean;
import com.credu.beta.BetaMasterFormData;
import com.credu.complete.FinishBean;
import com.credu.contents.EduScoreData;
import com.credu.contents.EduStartBean;
import com.credu.contents.MasterFormBean;
import com.credu.contents.MasterFormData;
import com.credu.course.SubjGongAdminBean;
import com.credu.exam.ExamUserBean;
import com.credu.research.SulmunRegistUserBean;
import com.credu.research.SulmunSubjUserBean;
import com.credu.study.ProjectAdminBean;
import com.credu.study.ProjectBean;
import com.credu.study.ToronBean;
import com.credu.system.StudyCountBean;
import com.credu.system.SubjCountBean;


@SuppressWarnings( { "unchecked", "serial" })
@WebServlet("/servlet/controller.contents.EduStart")
public class EduStart extends HttpServlet {

    /**
     * Pass get requests through to PerformTask
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }

    /**
     * doPost
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     */
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;
        String v_process = "";
        String v_subj = "";
        String v_year = "";
        String v_subjseq = "";

        //2015.11.13 TOBE 사이트 개편 추가 작업: 학습창을 불러오기 위한 파라미터 추가 및 암호화 작업
        String v_lesson = "";
        String v_tobeyn = "";
        String v_contenttype = "";
        String v_userid = "";

        try {
            response.setContentType("text/html;charset=euc-kr");

            out = response.getWriter();
            box = RequestManager.getBox(request);

            v_process = box.getStringDefault("p_process", "main"); //process 가 없으면 default --> main
            v_subj = box.getString("p_subj");
            v_year = box.getString("p_year");
            v_subjseq = box.getString("p_subjseq");

            //2015.11.13 TOBE 사이트 개편 추가 작업: 학습창을 불러오기 위한 파라미터 추가 및 암호화 작업
            v_lesson = box.getString("p_lesson");	//레슨 정보
            v_contenttype = box.getString("contenttype");	//컨텐츠 유형
            v_tobeyn = box.getString("p_tobeyn");	//tobe사이트 여부

            //복호화
            v_tobeyn = KISA_SEED_CBC.CKLTREE_SEED_CBC_Decrypt(v_tobeyn);

            //TOBE 사이트에서 접속할 경우
        	if("Y".equals(v_tobeyn) || "Y".equals(box.getSession("tobeyn"))){
            	System.out.println("TOBE 사이트에서 접속할 경우 s");

            	//복호화
            	v_subj = KISA_SEED_CBC.CKLTREE_SEED_CBC_Decrypt(v_subj);
            	v_year = KISA_SEED_CBC.CKLTREE_SEED_CBC_Decrypt(v_year);
            	v_subjseq = KISA_SEED_CBC.CKLTREE_SEED_CBC_Decrypt(v_subjseq);
            	v_lesson = KISA_SEED_CBC.CKLTREE_SEED_CBC_Decrypt(v_lesson);
            	v_contenttype = KISA_SEED_CBC.CKLTREE_SEED_CBC_Decrypt(v_contenttype);
            	v_userid = KISA_SEED_CBC.CKLTREE_SEED_CBC_Decrypt(box.getString("p_userid"));

            	if (!v_subj.equals("")){
            		box.setSession("subj", v_subj);
            		box.put("p_subj", v_subj);
            	}
            	if (!v_year.equals("")){
            		box.setSession("year", v_year);
            		box.put("p_year", v_year);
            	}
            	if (!v_subjseq.equals("")){
            		box.setSession("subjseq", v_subjseq);
            		box.put("p_subjseq", v_subjseq);
            	}
            	if (!v_lesson.equals("")){
            		box.setSession("lesson", v_lesson);
            		box.put("p_lesson", v_lesson);
            	}
            	if (!v_contenttype.equals("")){
            		box.setSession("contenttype", v_contenttype);
            		box.put("contenttype", v_contenttype);
            	}
            	if (!v_tobeyn.equals("")){
            		box.setSession("tobeyn", v_tobeyn);
            		box.put("p_tobeyn", v_tobeyn);
            	}
            	if (!box.getString("p_userid").equals("")){
            		box.setSession("userid", v_userid);
            		box.put("p_userid", v_userid);
            	}
            	if (box.getSession("gadmin").equals("")){
            		box.setSession("gadmin", "ZZ");
            	}

            }else{
            	System.out.println("ASIS 사이트에서 접속할 경우 s");

            	if (!v_subj.equals("")){
            		box.setSession("subj", v_subj);
            	}
            	if (!v_year.equals("")){
            		box.setSession("year", v_year);
            	}
            	if (!v_subjseq.equals("")){
            		box.setSession("subjseq", v_subjseq);
            	}
            }

            // 맛보기 시 세션이 없을 경우
            if ((box.getString("p_year").equals("2000") || box.getString("p_year").equals("PREV")) && box.getSession("userid").equals("")) {
                box.setSession("userid", "guest1");
            }

            if (ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }

            ///////////////////////////////////////////////////////////////////
            // 권한 check 루틴 VER 0.2 - 2003.08.10
            //if (!AdminUtil.getInstance().checkLoginPopup(out, box)) {
            //    return;
            //}
            ///////////////////////////////////////////////////////////////////

            if (v_process.equals("main")) { //
                this.framesetPage(request, response, box, out); // 학습창
            } else if (v_process.equals("fsetsub")) { //
                this.fsetSubPage(request, response, box, out);
            } else if (v_process.equals("fup")) { //
                this.fupPage(request, response, box, out);
            } else if (v_process.equals("fmenu")) { //
                this.fmenuPage(request, response, box, out);
            } else if (v_process.equals("eduCheck")) { //
                this.eduCheck(request, response, box, out);
            } else if (v_process.equals("eduCheck2")) {
                //  this.eduCheck2(request,response,box,out);   //
            } else if (v_process.equals("eduList")) { // 진도보기
                this.eduListPage(request, response, box, out);
            } else if (v_process.equals("exam")) { //
                this.fmenuPage(request, response, box, out);
            } else if (v_process.equals("branchControl")) { //
                this.eduBranchPage(request, response, box, out);
            } else if (v_process.equals("tree")) { //
                this.ftreePage(request, response, box, out);
            } else if (v_process.equals("bott")) { //
                this.fbottPage(request, response, box, out);
            } else if (v_process.equals("gong")) { //
                this.fmenuPage(request, response, box, out);
            } else if (v_process.equals("sul")) { //
                this.fmenuPage(request, response, box, out);
            } else if (v_process.equals("KOCSC_Userid_Check")) { // KOCSC 회원이 맞는지 userid를 체크해 준다.
                this.performKocsc_Userid_Check(request, response, box, out);
            } else if (v_process.equals("firstSubj")) { // 학습자 유의사항 동의처리
                this.performFirstSubj(request, response, box, out);
            } else if (v_process.equals("creduSubj")) {
            	this.performCreduSubj(request, response, box, out);
            } else if (v_process.equals("subjseqPageClassInfo")) {
                this.performSubjseqPageClassInfo(request, response, box, out);
            }  else if (v_process.equals("subjPageInfo")) {
                this.performSubjPageInfo(request, response, box, out);
            } else if (v_process.equals("pageControlChk")) {
                this.performPageControlChk(request, response, box, out);
            } else if (v_process.equals("subjSeqPageChk")) {
                this.performSubjSeqPageChk(request, response, box, out);
            } else if (v_process.equals("lessonCompleteChk")) {
                this.performLessonCompleteChk(request, response, box, out);
            } else if (v_process.equals("subjseqPageSearchInput")) {
                this.performSubjseqPageSearchInput(request, response, box, out);
            } else if (v_process.equals("subjseqPageUpdate")) {
                this.performSubjseqPageUpdate(request, response, box, out);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 학습창
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    public void framesetPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);

            String v_url = "";

            String field1 = box.getString("FIELD1");
            String field2 = box.getString("FIELD2");
            String field3 = box.getString("FIELD3");
            String field4 = box.getString("FIELD4");
            String field5 = box.getString("FIELD5");
            String field99 = box.getString("FIELD99");
            String field100 = box.getString("FIELD100");
            String p_iurl = box.getString("p_iurl");

            MasterFormBean bean = new MasterFormBean();
            MasterFormData data = bean.SelectMasterFormData(box); //마스터폼 정보

            request.setAttribute("MasterFormData", data);

            // 방송통신심의위원회인 경우만 아이디 체크를 해준다..
            // 아이디 체크 시작
            if (box.getString("p_aspgubun").equals("kocsc")) {

                EduStartBean kocscbean = new EduStartBean();

                ArrayList useridCheck = kocscbean.selectKocscUseridCheck(box);
                String v_msg = "";
                AlertManager alert = new AlertManager();

                if (useridCheck.size() > 0) {

                    box.setSession("userid", box.getString("p_userid"));

                    //v_msg = "아이디가 존재 합니다.";
                    //alert.selfClose(out, v_msg);
                } else {
                    v_msg = "아이디가 존재 하지 않습니다.";
                    alert.selfClose(out, v_msg);
                    return;
                }
            }

            if (box.getString("p_aspgubun").equals("koccc")) {

                EduStartBean kocscbean = new EduStartBean();

                ArrayList useridCheck = kocscbean.selectKocscUseridCheck2(box);
                String v_msg = "";
                AlertManager alert = new AlertManager();

                if (useridCheck.size() > 0) {

                    box.setSession("userid", box.getString("p_userid"));

                    //v_msg = "아이디가 존재 합니다.";
                    //alert.selfClose(out, v_msg);
                } else {
                    v_msg = "아이디가 존재 하지 않습니다.";
                    alert.selfClose(out, v_msg);
                    return;
                }
            }

            // 아이디 체크 끝

            if (data.getIsoutsourcing().equals("Y")) {
                v_url = "/learn/admin/contents/EduStart.jsp";
                v_url += "?FIELD1=" + field1 + "&FIELD2=" + field2 + "&FIELD3=" + field3 + "&FIELD4=" + field4 + "&FIELD99=" + field99
                        + "&FIELD100=" + field100;
                box.put("isoutsourcing", data.getIsoutsourcing());
                box.put("p_iurl", p_iurl);
                box.put("eduurl", data.getEduurl());
                box.put("p_subj", box.getString("p_subj"));
                box.put("p_year", box.getString("p_year"));
                box.put("p_subjseq", box.getString("p_subjseq"));
                box.put("p_ispreview", box.getString("p_ispreview"));
                box.put("p_isinfo", "N");
            } else {
                if (data.getContenttype().equals("N")) { //Normal MasterForm(NEW)
                    v_url = "/learn/user/contents/z_EduStart_fset.jsp";
                } else if (data.getContenttype().equals("M")) { //Normal MasterForm(OLD)               	
                    v_url = "/learn/user/contents/z_EduStart_fset.jsp";
                } else if (data.getContenttype().equals("O")) { //OBC MasterForm
                    v_url = "/learn/user/contents/z_EduStart_fset_OBC.jsp";
                } else if (data.getContenttype().equals("S")) { //SCORM MasterForm
                    v_url = "/learn/user/contents/z_EduStart_fset_SCORM.jsp";
                } else if (data.getContenttype().equals("Y")) {
                    v_url = "/learn/admin/contents/EduStart.jsp";
                    v_url += "?FIELD1=" + field1 + "&FIELD2=" + field2 + "&FIELD3=" + field3 + "&FIELD4=" + field4 + "&FIELD5=" + field5
                            + "&FIELD99=" + field99 + "&FIELD100=" + field100;
                    box.put("isoutsourcing", data.getIsoutsourcing());
                    box.put("p_iurl", p_iurl);
                    box.put("p_subj", box.getString("p_subj"));
                    box.put("p_year", box.getString("p_year"));
                    box.put("p_subjseq", box.getString("p_subjseq"));
                    box.put("p_ispreview", box.getString("p_ispreview"));

                    //tobe로 인해 추가
                    if(!"".equals(box.getString("p_tobeyn")) && "Y".equals(box.getString("p_tobeyn"))){
	                    box.put("p_lesson", box.getString("p_lesson"));
	                    box.put("p_tobeyn", box.getString("p_tobeyn"));
                    }

                }
            }

            //과정년도가 2000이면 과거이력 복습이므로 통계를 작성하지 않는다. 2005.06.28
            if (!(box.getString("p_year").equals("2000") || box.getString("p_year").equals("PREV"))) {
                //과정별 접속통계 로그 작성
                SubjCountBean CountBean = new SubjCountBean();
                String v_userip = request.getRemoteAddr();
                box.put("p_userip", v_userip);
                CountBean.writeLog(box, box.getString("p_subj"), box.getString("p_year"), box.getString("p_subjseq")); // 로그 작성
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdateMasterFormPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * fsetSub
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    public void fsetSubPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "";
            box.put("p_subj", box.getSession("s_subj"));
            MasterFormBean bean = new MasterFormBean();

            MasterFormData data = bean.SelectMasterFormData(box); //마스터폼 정보
            request.setAttribute("MasterFormData", data);

            if (data.getContenttype().equals("O")) { //OBC MasterForm
                v_url = "/learn/user/contents/z_EduStart_fsetSub_OBC.jsp";
            } else if (data.getContenttype().equals("S")) { // SCORM MasterForm
                v_url = "/learn/user/contents/z_EduStart_fsetSub_SCORM.jsp";
            }
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("fsetSubPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * fup
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    public void fupPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "";

            box.put("p_subj", box.getSession("s_subj"));

            EduStartBean bean = new EduStartBean();

            ArrayList data1 = bean.SelectMfLessonList(box); //Lesson List
            request.setAttribute("MfLessonList", data1);

            String contenttype = box.getSession("s_contenttype");

            if (contenttype.equals("N")) { //Normal MasterForm (NEW)
                v_url = "/learn/user/contents/z_EduStart_fup.jsp";
            } else if (contenttype.equals("M")) { //Normal MasterForm (OLD)
                v_url = "/learn/user/contents/z_EduStart_fup.jsp";
            } else if (contenttype.equals("O")) { //OBC MasterForm
                v_url = "/learn/user/contents/z_EduStart_fup_OBC.jsp";
            } else if (contenttype.equals("S")) { //SCORM MasterForm
                v_url = "/learn/user/contents/z_EduStart_fup_SCORM.jsp";
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdateMasterFormPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * fmenu
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    public void fmenuPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "";

            box.put("p_subj", box.getSession("s_subj"));

            MasterFormBean bean = new MasterFormBean();

            ArrayList data1 = bean.SelectMfSubjList(box); //mfSubj Menu List
            request.setAttribute("MfSubjList", data1);

            String contenttype = box.getSession("s_contenttype");

            if (contenttype.equals("N")) { //Normal MasterForm (NEW)
                v_url = "/learn/user/contents/z_EduStart_fmenu.jsp";
            } else if (contenttype.equals("M")) { //Normal MasterForm (OLD)
                v_url = "/learn/user/contents/z_EduStart_fmenu.jsp";
            } else if (contenttype.equals("O")) { //OBC MasterForm
                v_url = "/learn/user/contents/z_EduStart_fmenu_OBC.jsp";
            } else if (contenttype.equals("S")) { //SCORM MasterForm
                v_url = "/learn/user/contents/z_EduStart_fmenu_SCORM.jsp";
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdateMasterFormPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * ftree
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    public void ftreePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "";
            String subjnm = "";
            int edulimit = 0;
            int oid_count = 0;
            int jindo_count = 0;
            String ispreview = "";

            box.put("p_subj", box.getSession("s_subj"));
            box.put("p_year", box.getSession("s_year"));
            box.put("p_subjseq", box.getSession("s_subjseq"));
            box.put("p_userid", box.getSession("userid"));

            ispreview = box.getStringDefault("p_ispreview", "");

            //            System.out.println("ftreePage ispreview = " + ispreview);

            MasterFormBean bean = new MasterFormBean();

            MasterFormData data = bean.SelectMasterFormData(box); //마스터폼 정보
            request.setAttribute("MasterFormData", data);

            EduStartBean eduBean = new EduStartBean();
            //ArrayList data1   = eduBean.SelectTreeDataList(box);  // Tree Datas
            //request.setAttribute("TreeData", data1);

            if (data.getContenttype().equals("O")) { //OBC MasterForm
                ArrayList data1;

                if (ispreview.equals("Y")) {
                    data1 = eduBean.SelectTreeDataListPreview(box); // Tree Datas
                } else {
                    data1 = eduBean.SelectTreeDataList(box); // Tree Datas
                }

                request.setAttribute("TreeData", data1);

                v_url = "/learn/user/contents/z_EduStart_ftree_OBC.jsp";
            } else if (data.getContenttype().equals("S")) { // SCORM MasterForm

                ArrayList data1;
                if (ispreview.equals("Y")) {
                    data1 = eduBean.SelectMappingSubjListPreview(box); // Tree Datas
                } else {
                    data1 = eduBean.SelectMappingSubjList(box); // Tree Datas
                }

                //  ArrayList   data2   = eduBean.SelectEudLimitLesson(box);    // 일일 제한 레슨
                request.setAttribute("TreeData", data1);
                //  request.setAttribute("LimitLesson", data2);

                subjnm = eduBean.SelectSubjName(box);
                edulimit = eduBean.SelectEudLimit(box);
                oid_count = eduBean.SelectOidCount(box);
                jindo_count = eduBean.SelectJindoCount(box);

                //  periodcnt = eduBean.SelectEudPeriod(box);

                box.put("subjnm", subjnm);
                box.put("edulimit", String.valueOf(edulimit));
                box.put("oid_count", String.valueOf(oid_count));
                box.put("jindo_count", String.valueOf(jindo_count));
                //  box.put("periodcnt",String.valueOf(periodcnt));

                v_url = "/learn/user/contents/z_EduStart_ftree_SCORM.jsp";
            }

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("ftreePage()\r\n" + ex.getMessage());
        }
    }

    /**
     * fbott
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    public void fbottPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "";

            box.put("p_subj", box.getSession("s_subj"));

            MasterFormBean bean = new MasterFormBean();

            MasterFormData data = bean.SelectMasterFormData(box); //마스터폼 정보
            request.setAttribute("MasterFormData", data);

            v_url = "/learn/user/contents/z_EduStart_fbott_OBC.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("fbottPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 진도체크
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     **/
    public void eduCheck(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "/learn/user/contents/z_EduChk_after.jsp";

            InetAddress address = InetAddress.getLocalHost();
            String hostAddress = address.getHostAddress();
            hostAddress = request.getRemoteAddr();
            box.put("hostAddress", hostAddress);

            EduStartBean bean = new EduStartBean();
            String results = bean.EduCheck(box);



            /**상시과정일경우 진도율 70%도달시 자동수료처리 로직추가*/
            /**START*/
            //파라미터 준비
            String s_subj = box.getSession("s_subj");
            String s_year = box.getSession("s_year");
            String s_subjseq = box.getSession("s_subjseq");
            String s_userid = box.getSession("userid");
            String s_grcode = box.getSession("tem_grcode");
            box.put("p_subj", s_subj);
            box.put("p_year", s_year);
            box.put("p_subjseq", s_subjseq);
            box.put("p_userid", s_userid);

			SubjGongAdminBean sbean = new SubjGongAdminBean();
			String isalways  = sbean.getIsalways(box);
			float progress = Float.parseFloat(sbean.getProgress(box));

			if(isalways.equals("Y")){
				//수료여부확인
				String isgrad  = sbean.getIsgrad(box);
				//if(!isgrad.equals("Y")){
					//진도율 확인
					if((progress >= 70 && s_subj != "CK20010") || (progress >= 100 && s_subj == "CK20010")){
						if(!"N000241".equals(s_grcode)){
							//진도율 70%초과시 자동수료처리
							FinishBean fbean = new FinishBean();
							int isOk = fbean.updateAllGraduated(box);
							if(isOk > 0){
								results = "OK";
							}else{
								results = "수료처리중 오류가 발생하였습니다. 운영자에게 문의 바랍니다.";
							}
						}
					}
				//}
			}

			/**END*/
			/**상시과정일경우 진도율 70%도달시 자동수료처리 로직추가*/




            //			String v_msg = "";

            AlertManager alert = new AlertManager();
            if (!results.equals("OK")) {
                //				v_msg = "update.fail";
                //alert.alertFailMessage(out, v_msg);
                //alert.alertFailMessage(out, results);
                //alert.alertOkMessage(out, v_msg, v_url , box, false, false);
                alert.alertOkMessage(out, results, v_url, box);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("eduCheck()\r\n" + ex.getMessage());
        }
    }

    /**
     * 진도보기
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    public void eduListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "";
            String v_kind = box.getString("p_kind"); // 과거이력
            String v_subj = "";

            if (box.getString("p_subj").equals(""))
                box.put("p_subj", box.getSession("s_subj"));
            if (box.getString("p_year").equals(""))
                box.put("p_year", box.getSession("s_year"));
            if (box.getString("p_subjseq").equals(""))
                box.put("p_subjseq", box.getSession("s_subjseq"));

            // 과거이력
            if (v_kind.equals("2")) { // 테이블도 없으므로 사용 안함을 유추할 수 있음. by swchoi
                v_url = "/learn/user/contents/z_EduChk_List_PAST.jsp";
                EduStartBean bean = new EduStartBean();
                ArrayList list1 = bean.getPastLog(box);
                request.setAttribute("pastlog", list1);
            } else {
                if (!box.getSession("s_subjseq").equals("0000")) {
                    MasterFormBean mfbean = new MasterFormBean();
                    MasterFormData data = mfbean.SelectMasterFormData(box); //마스터폼 정보
                    request.setAttribute("MasterFormData", data);
                    EduStartBean bean = new EduStartBean();

                    // 과정별 메뉴 접속 정보 추가
                    box.put("p_menu", "01");
                    StudyCountBean scBean = new StudyCountBean();
                    scBean.writeLog(box);

                    ArrayList data1 = null;
                    ArrayList dataTime = null;

                    EduScoreData data2 = bean.SelectEduScore(box);
                    request.setAttribute("EduScore", data2);

                    int result = bean.selectUserPage(box);

                    /****** L : 외주 , N : Normal , O : OBC ,S : SCORM , Y : YESTLEAN *****/
                    if (result == 1) {
                        if (data.getContenttype().equals("N")) { //Normal MasterForm

                            data1 = bean.SelectEduList(box); //진도데이터
                            dataTime = bean.SelectEduTimeCountOBC(box); //학습시간,최근학습일,강의접근횟수

                            v_url = "/learn/user/contents/z_EduChk_List.jsp";
                        } else if (data.getContenttype().equals("M")) { //Normal MasterForm

                            data1 = bean.SelectEduList(box); //진도데이터
                            dataTime = bean.SelectEduTimeCountOBC(box); //학습시간,최근학습일,강의접근횟수

                            v_url = "/learn/user/contents/z_EduChk_List.jsp";
                        } else if (data.getContenttype().equals("O") || data.getContenttype().equals("S")) { //OBC,SCORM MasterForm

                            data1 = bean.SelectEduListOBC(box); //진도데이터
                            dataTime = bean.SelectEduTimeCountOBC(box); //학습시간,최근학습일,강의접근횟수

                            v_url = "/learn/user/contents/z_EduChk_List_OBC.jsp";
                        } else if (data.getContenttype().equals("L")) { //외주

                            data1 = bean.SelectEduList(box); //진도데이터 - 학습진행상황
                            dataTime = bean.SelectEduTimeCountOBC(box); //학습시간,최근학습일,강의접근횟수

                            v_url = "/learn/user/contents/z_EduChk_List.jsp";
                        }

                        request.setAttribute("EduList", data1);
                        request.setAttribute("EduTime", dataTime);

                    } else {
                        String v_msg = "개인화 페이지라 입과생이 아니면 보실 수 없습니다.";
                        AlertManager alert = new AlertManager();
                        alert.selfClose(out, v_msg);
                    }

                    if (data.getContenttype().equals("L")) {
                        System.out.println("외주");
                    } else {
                        /* ========== 권장진도율, 자기진도율 시작 ========== */
                        if (box.getString("p_year").equals(""))
                            box.put("p_year", box.getSession("s_year"));
                        if (box.getString("p_subjseq").equals(""))
                            box.put("p_subjseq", box.getSession("s_subjseq"));
                        if (box.getString("p_subj").equals("")) {
                            box.put("p_subj", box.getSession("s_subj"));
                        }
                        if (box.getString("s_subj").equals(""))
                            box.put("s_subj", box.getSession("s_subj"));

                        v_subj = box.getString("p_subj");

                        SubjGongAdminBean sbean = new SubjGongAdminBean();
                        String promotion = sbean.getPromotion(box);
                        request.setAttribute("promotion", promotion);

                        String progress = sbean.getProgress(box);
                        request.setAttribute("progress", progress);
                        /* ========== 권장진도율, 자기진도율 끝 ========== */

                        box.put("p_subj", "ALL"); // 과정설문용

                        /*
                         * ========== 과정설문 (사전설문)응시여부
                         * =====SulmunSubjUserBean=====
                         */
                        SulmunSubjUserBean sulbean = new SulmunSubjUserBean();
                        int suldata = sulbean.getUserData(box);
                        box.put("p_suldata", String.valueOf(suldata));
                        int ispaper2 = sulbean.getSubjSulmunPaper(box);
                        box.put("p_ispaper2", String.valueOf(ispaper2));
                        /* ========== 과정설문 응시여부 ========== */

                        box.put("p_subj", "REGIST");
                        /* ========== 가입동기(사후설문) 응시여부 ========== */
                        //SulmunContentsUserBean contentsbean = new SulmunContentsUserBean();
                        SulmunRegistUserBean contentsbean = new SulmunRegistUserBean();
                        // 먼저 컨텐츠설문지 잇는지 확인...(2005.10.13)
                        int ispaper = contentsbean.getContentsSulmunPaper(box);
                        box.put("p_ispaper", String.valueOf(ispaper));
                        int contentsdata = contentsbean.getUserData(box);
                        box.put("p_contentsdata", String.valueOf(contentsdata));
                        /* ========== 컨텐츠평가 응시여부 ========== */

                        box.put("p_subj", v_subj);
                        /* ========== 레포트 제출개수 ============== */
                        ProjectAdminBean report = new ProjectAdminBean();
                        int reportadmin = report.getAdminData(box);
                        box.put("p_report", String.valueOf(reportadmin));
                        /* ========== 레포트 제출개수 끝 =========== */

                        /* ========== 레포트 제출여부 ============== */
                        ProjectAdminBean reportuser = new ProjectAdminBean();
                        int reportdata = reportuser.getUserData(box);
                        box.put("p_reportdata", String.valueOf(reportdata));
                        /* ========== 레포트 제출여부 끝 =========== */

                        /* ========== 평가 갯수 ========== */
                        ExamUserBean exambean = new ExamUserBean();
                        ArrayList examdata = exambean.getUserData(box);
                        request.setAttribute("ExamData", examdata);
                        /* ========== 평가 갯수 ========== */

                        /* ========== 평가 응시여부 ========== */
                        ArrayList examresultdata = exambean.getUserResultData(box);
                        request.setAttribute("ExamResultData", examresultdata);
                        /* ========== 평가 응시여부 ========== */

                        // 2008.09.25
                        ExamUserBean ExamBean = new ExamUserBean();
                        ArrayList ExamList1 = ExamBean.SelectUserList(box);
                        request.setAttribute("ExamUserList", ExamList1);

                        ArrayList ExamList2 = ExamBean.SelectUserResultList(box);
                        request.setAttribute("ExamUserResultList", ExamList2);

                        ProjectBean ProjectBean = new ProjectBean();
                        ArrayList ProjectList = ProjectBean.selectProjectList(box);
                        request.setAttribute("ProjectList", ProjectList);

                        ToronBean ToronBean = new ToronBean();
                        ArrayList ToronList = ToronBean.selectTopicList(box);
                        request.setAttribute("TopicList", ToronList);

                        SulmunSubjUserBean SulmunBean = new SulmunSubjUserBean();
                        box.put("s_subj", v_subj);
                        ArrayList SulmunList = SulmunBean.SelectUserList(box);
                        request.setAttribute("SulmunSubjUserList", SulmunList);

                    }

                } else {

                    BetaMasterFormBean mfbean = new BetaMasterFormBean();
                    BetaMasterFormData data = mfbean.SelectBetaMasterFormData(box); //마스터폼 정보
                    request.setAttribute("BetaMasterFormData", data);
                    BetaEduStartBean bean = new BetaEduStartBean();

                    /*
                     * modified by LeeSuMin 2004.02.23. for OBC ArrayList data1=
                     * bean.SelectEduList(box); //mfSubj Menu List
                     * request.setAttribute("EduList", data1); EduScoreData
                     * data2= bean.SelectEduScore(box);
                     * request.setAttribute("EduScore", data2);
                     *
                     * if (data.getContenttype().equals("N")){ //Normal
                     * MasterForm v_url =
                     * "/learn/user/contents/z_EduChk_List.jsp"; }
                     */
                    ArrayList data1 = null;

                    //BetaEduScoreData  data2= bean.SelectEduScore(box);
                    //request.setAttribute("EduScore", data2);
                    if (data.getContenttype().equals("N")) { //Normal MasterForm
                        data1 = bean.SelectEduList(box); //진도데이터

                        v_url = "/beta/admin/z_BetaEduChk_List.jsp";
                    } else if (data.getContenttype().equals("M")) { //Normal MasterForm(Old)
                        data1 = bean.SelectEduList(box); //진도데이터

                        v_url = "/beta/admin/z_BetaEduChk_List.jsp";
                    } else if (data.getContenttype().equals("O") || data.getContenttype().equals("S")) { //OBC,SCORM MasterForm
                        data1 = bean.SelectEduListOBC(box); //진도데이터

                        v_url = "/beta/admin/z_BetaEduChk_List_SCORM.jsp";
                    }
                    request.setAttribute("EduList", data1);

                    /* ========== 권장진도율, 자기진도율 시작 ========== */
                    //SubjGongAdminBean sbean = new SubjGongAdminBean();

                    //String promotion  = sbean.getPromotion(box);
                    //request.setAttribute("promotion", promotion);
                    //String progress = sbean.getProgress(box);
                    //request.setAttribute("progress", progress);
                    /* ========== 권장진도율, 자기진도율 끝 ========== */

                }

            } // 과거이력 구분 end..

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("eduListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * eduBranch
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */

    public void eduBranchPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);
            String v_url = "";

            EduStartBean bean = new EduStartBean();
            ArrayList data = bean.SelectEduBranch(box);
            request.setAttribute("BranchList", data);

            v_url = "/learn/user/contents/z_EduBranchCntl.jsp";

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("eduListPage()\r\n" + ex.getMessage());
        }
    }

    /**
     * 학습자 유의사항 동의처리
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performFirstSubj(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            EduStartBean bean = new EduStartBean();

            int isOk = bean.firstSubj(box);
            String v_msg = "";
            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "학습자 유의사항에 동의하셨습니다";
                alert.selfClose(out, v_msg);
            } else {
                v_msg = "실패했습니다.";
                alert.selfClose(out, v_msg);
            }

            //Log.info.println(this, box, v_msg + " on LoginServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performFirstSubj()\r\n" + ex.getMessage());
        }
    }

    /**
     * 방송통신심의위원회 회원이 맞는지 userid를 체크해준다.
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performKocsc_Userid_Check(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
            throws Exception {
        try {
            EduStartBean bean = new EduStartBean();

            ArrayList useridCheck = bean.selectKocscUseridCheck(box);
            String v_msg = "";
            AlertManager alert = new AlertManager();

            if (useridCheck.size() > 0) {

                box.setSession("userid", box.getString("p_userid"));

                v_msg = "";
                alert.selfClose_kocsc(out, v_msg);
            } else {
                v_msg = "아이디가 존재 하지 않습니다.";
                alert.selfClose_kocsc(out, v_msg);
            }

            //Log.info.println(this, box, v_msg + " on LoginServlet");
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performKocsc_Userid_Check()\r\n" + ex.getMessage());
        }
    }

    public void performCreduSubj(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
    		throws Exception {
    	try {
    		String dispatcherURL = "/learn/user/contents/z_Credu_url.jsp";
    		String videoURL = "";

    		String creduURL = box.getString("url");

    		Document document = parse(creduURL);
    		if(document != null){
    			XPath xpathSelector = DocumentHelper.createXPath("/ITEM/MP4NM");

    	        List results = xpathSelector.selectNodes(document);
    	        for(Iterator iter = results.iterator(); iter.hasNext();){
    	        	Element element = (Element)iter.next();
    	        	videoURL = element.getText();
    	        }
    		}


    		request.setAttribute("videoURL", videoURL);

    		ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(dispatcherURL);
            rd.forward(request, response);

    		//Log.info.println(this, box, v_msg + " on LoginServlet");
    	} catch (Exception ex) {
    		ex.printStackTrace();
    		ErrorManager.getErrorStackTrace(ex, out);
    		throw new Exception("performCreduSubj()\r\n" + ex.getMessage());
    	}
    }


    public Document parse(String url) throws DocumentException {
    	SAXReader reader = new SAXReader();
    	try
    	{
    		return reader.read(new URL(url));
    	}
    	catch(MalformedURLException e)
    	{
    		e.printStackTrace();
    	}
    	return null;
    }

    /**
     * 수강 페이지 이력 저장
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjseqPageClassInfo(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            EduStartBean bean = new EduStartBean();

            int isOk = bean.saveSubjseqPageClassInfo(box);
            String v_msg = "";
            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "";
                alert.selfClose(out, v_msg);
            } else {
                v_msg = "실패했습니다.";
                alert.selfClose(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjseqPageClassInfo()\r\n" + ex.getMessage());
        }
    }

    /**
     * 수강 페이지 정보 조회
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjPageInfo(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        JSONObject jsonObj = new JSONObject();
        ArrayList resultList = new ArrayList();
        ArrayList jsonList = new ArrayList();
        DataBox dbox = null;
        Map map = null;

        try {
            request.setAttribute("requestbox", box);

            EduStartBean bean = new EduStartBean();

            resultList = bean.selectSubjPageInfo(box);

            jsonObj.put("resList", "");

            if ( resultList.size() > 0 ) {
                for( int i = 0; i < resultList.size() ; i++ ) {
                    dbox = (DataBox)resultList.get(i);

                    map = new HashMap();
                    map.put("cp", dbox.getString("d_c_page"));
                    map.put("tp", dbox.getString("d_t_page"));
                    map.put("np", dbox.getString("d_n_page"));
                    map.put("ct", dbox.getString("d_c_time"));
                    map.put("tt", dbox.getString("d_t_time"));
                    map.put("pageChkYn", dbox.getString("d_pageChkYn"));
                    map.put("finalPageChkYn", dbox.getString("d_finalPageChkYn"));
                    map.put("nextPageChkYn", dbox.getString("d_nextPageChkYn"));

                    jsonList.add(map);
                }

                jsonObj.put("resList", jsonList);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjPageInfo()\r\n" + ex.getMessage());
        }

        out.print(jsonObj.toJSONString().replace("\\", ""));
        out.flush();
    }

    /**
     * 수강 페이지 콘트롤 제어 여부 조회
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performPageControlChk(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        JSONObject jsonObj = new JSONObject();
        ArrayList resultList = new ArrayList();
        ArrayList jsonList = new ArrayList();
        DataBox dbox = null;
        Map map = null;

        try {
            request.setAttribute("requestbox", box);

            EduStartBean bean = new EduStartBean();

            resultList = bean.selectPageControlChk(box);

            jsonObj.put("resList", "");

            if ( resultList.size() > 0 ) {
                for( int i = 0; i < resultList.size() ; i++ ) {
                    dbox = (DataBox)resultList.get(i);

                    map = new HashMap();
                    map.put("cp", dbox.getString("d_c_page"));
                    map.put("tp", dbox.getString("d_t_page"));
                    map.put("np", dbox.getString("d_n_page"));
                    map.put("ct", dbox.getString("d_c_time"));
                    map.put("tt", dbox.getString("d_t_time"));
                    map.put("pageChkYn", dbox.getString("d_page_chk_yn"));
                    map.put("finishPageYn", dbox.getString("d_finishPageYn"));
                    map.put("nextPageChkYn", dbox.getString("d_nextPageChkYn"));
                    map.put("lastChkPage", dbox.getString("d_lastChkPage"));

                    jsonList.add(map);
                }

                jsonObj.put("resList", jsonList);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performPageControlChk()\r\n" + ex.getMessage());
        }

        out.print(jsonObj.toJSONString().replace("\\", ""));
        out.flush();
    }

    /**
     * 수강 페이지 제어 여부
     *
     * @param box receive from the form object and session
     * @return String pageChkYn
     */
    public void performSubjSeqPageChk(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {
            req.setAttribute("requestbox", box);

            EduStartBean bean = new EduStartBean();

            String pageChkYn = bean.subjSeqPageChk(box);

            out.println("{\"pageChkYn\" : \"" + pageChkYn + "\"}");
            out.flush();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjSeqPageChk()\r\n" + ex.getMessage());
        }
    }

    /**
     * 수강 차시 완료 여부
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performLessonCompleteChk(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        JSONObject jsonObj = new JSONObject();
        ArrayList resultList = new ArrayList();
        ArrayList jsonList = new ArrayList();
        DataBox dbox = null;
        Map map = null;

        try {
            request.setAttribute("requestbox", box);

            EduStartBean bean = new EduStartBean();

            resultList = bean.selectLessonCompleteChk(box);

            jsonObj.put("resList", "");

            if ( resultList.size() > 0 ) {
                for( int i = 0; i < resultList.size() ; i++ ) {
                    dbox = (DataBox)resultList.get(i);

                    map = new HashMap();
                    map.put("subj", dbox.getString("d_subj"));
                    map.put("lesson", dbox.getString("d_lesson"));
                    map.put("finalYn", dbox.getString("d_finalYn"));
                    map.put("sdesc", dbox.getString("d_sdesc"));

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

    /**
     * B2C 수강 페이지 이력 저장
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjseqPageSearchInput(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            EduStartBean bean = new EduStartBean();

            //int isOk = bean.saveSubjseqPageClassInfo(box);
            int c_time = bean.subjseqPageSearchInput(box);

            out.println("{\"cTime\" : \"" + c_time + "\"}");
            out.flush();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performsubjseqPageSearchInput()\r\n" + ex.getMessage());
        }
    }

    /**
     * B2C 수강 페이지 시간 저장
     *
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSubjseqPageUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            EduStartBean bean = new EduStartBean();

            int isOk = bean.subjseqPageUpdate(box);
            String v_msg = "";
            AlertManager alert = new AlertManager();

            if (isOk > 0) {
                v_msg = "";
                alert.selfClose(out, v_msg);
            } else {
                v_msg = "실패했습니다.";
                alert.selfClose(out, v_msg);
            }

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSubjseqPageUpdate()\r\n" + ex.getMessage());
        }
    }
    
}
