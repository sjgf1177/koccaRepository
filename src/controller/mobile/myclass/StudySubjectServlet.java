package controller.mobile.myclass;

import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.complete.FinishBean;
import com.credu.course.SubjGongAdminBean;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.mobile.myclass.MyClassBean;
@WebServlet("/servlet/controller.mobile.myclass.StudySubjectServlet")
public class StudySubjectServlet extends javax.servlet.http.HttpServlet implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;

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
            process = box.getStringDefault("process", "selectStudySubjectList");
            
            if (process.equals("selectStudySubjectList")) { // 과정 목록 조회 
                this.performSelectStudySubjectList(req, res, box, out);

            } else if (process.equals("selectStudySubjectInfo")) { // 과정 차시 목록 조회
                this.performSelectStudySubjectInfo(req, res, box, out);

            } else if (process.equals("updateProgressStart")) { // 학습창 혹은 콘텐츠를 열 때, 학습 이력 정보를 등록/갱신한다.
                this.performUpdateProgressStart(req, res, box, out);

            } else if (process.equals("updateProgressEnd")) { // 학습칭 혹은 콘텐츠를 닫을 때, 학습 이력 정보를 추가 등록/갱신한다.
                this.performUpdateProgressEnd(req, res, box, out);

            } else if (process.equals("selectMyProgress")) { // 학습칭 혹은 콘텐츠를 닫을 때, 학습 이력 정보를 추가 등록/갱신한다.
                this.performSelectMyProgress(req, res, box, out);

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * 학습할 과정의 기본 정보를 조회한다. 진도율 정보도 갱신한다.
     * 
     * @param req
     * @param res
     * @param box
     * @param out
     * @throws Exception
     */
    public void performSelectStudySubjectInfo(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {
            req.setAttribute("requestbox", box);


            MyClassBean bean = new MyClassBean();

            bean.updateProgressRateInfo(box); // 학습 화면으로 이동할 때 진도율 정보를 갱신

            String recommendProgress = bean.selectRecommendProgress(box);
            String myProgress = bean.selectMyProgress(box);

            ArrayList<DataBox> subjectLessonList = bean.selectLessonList(box);

            req.setAttribute("recommendProgress", recommendProgress);
            req.setAttribute("myProgress", myProgress);
            req.setAttribute("subjectLessonList", subjectLessonList);
            

            
            
			// 상시학습여부
            String subj = box.getString("subj");
            String year = box.getString("year");
            String subjseq = box.getString("subjseq");
            String userid = box.getSession("userid");
            box.put("p_subj", subj);
            box.put("p_year", year);
            box.put("p_subjseq", subjseq);
            
            
			SubjGongAdminBean gbean = new SubjGongAdminBean();
			String isalways  = gbean.getIsalways(box);
			req.setAttribute("isalways", isalways);

			
			//정규학습창
			String dispatcherUrl = "/mobile/jsp/myclass/myclassStudySubjectInfo.jsp";
			if("Y".equals(isalways)){
				dispatcherUrl = "/mobile/jsp/myclass/myclassStudySubjectInfoAll.jsp";
			}
			
			
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(dispatcherUrl);
            rd.forward(req, res);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectCategoryList()\r\n" + ex.getMessage());
        }

    }

    /**
     * 정규과정에서 수강신청을 한 과정 목록을 조회한다.
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performSelectStudySubjectList(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {
            req.setAttribute("requestbox", box);

            String dispatcherUrl = "/mobile/jsp/myclass/myclassStudySubjectList.jsp";

            MyClassBean bean = new MyClassBean();
            ArrayList<DataBox> studySubjectList = bean.selectStudySubjectList(box);

            req.setAttribute("studySubjectList", studySubjectList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(dispatcherUrl);
            rd.forward(req, res);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectCategoryList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 학습창 혹은 콘텐츠를 열 때, 학습 이력 정보를 등록/갱신한다.
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    public void performUpdateProgressStart(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {
            req.setAttribute("requestbox", box);

            String dispatcherUrl = "/mobile/jsp/myclass/myclassUpdateProgressInfoAjaxResult.jsp";

            MyClassBean bean = new MyClassBean();
            int resultCnt = bean.updateProgressStart(box);
            
            
            
            /**상시과정일경우 진도율 70%도달시 자동수료처리 로직추가*/
            /**START*/

            //파라미터 준비
            String results = "";
            String s_subj = box.getString("subj");
            String s_year = box.getString("year");
            String s_subjseq = box.getString("subjseq");
            String s_userid = box.getSession("userid");
            box.put("p_subj", s_subj);
            box.put("p_year", s_year);
            box.put("p_subjseq", s_subjseq);
            box.put("p_userid", s_userid);
            
			SubjGongAdminBean sbean = new SubjGongAdminBean();
			String isalways  = sbean.getIsalways(box);
			  
			if(isalways.equals("Y")){
				//수료여부확인
				String isgrad  = sbean.getIsgrad(box);
				
//				if(!isgrad.equals("Y")){
					//진도율 확인
					float progress = Float.parseFloat(sbean.getProgress(box));
					
					if((progress >= 70 && s_subj != "CK20010") || (progress >= 100 && s_subj == "CK20010")){
						//진도율 70%초과시 자동수료처리
						FinishBean fbean = new FinishBean();
						int isOk = fbean.updateAllGraduated(box);
						if(isOk > 0){
							results = "OK";
						}else{
							results = "수료처리중 오류가 발생하였습니다. 운영자에게 문의 바랍니다.";
						}
					}
//				}
			}
            
			/**END*/
			/**상시과정일경우 진도율 70%도달시 자동수료처리 로직추가*/
            

            req.setAttribute("resultCnt", resultCnt);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(dispatcherUrl);
            rd.forward(req, res);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectCategoryList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 학습창 혹은 콘텐츠를 닫을 때, 학습 이력 정보를 추가 등록/갱신한다.
     * 
     * @param request encapsulates the request to the servlet
     * @param response encapsulates the response from the servlet
     * @param box receive from the form object
     * @param out printwriter object
     * @return void
     */
    @SuppressWarnings("unchecked")
    public void performUpdateProgressEnd(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {
            req.setAttribute("requestbox", box);

            String customInfo = box.getString("customInfo");
            // String totalPageNum = box.getString("totalPageNum"); // 모바일 과정 전체 페이지 
            // String curPage = box.getString("curPage"); // 모바일 과정 학습중인 현재 페이지. 2014년 8우러 27일 현재 화면 종료시에는 null이 넘어오고 있다.
            // String contentID = box.getString("contentID");

            if (customInfo.equals("")) {
                System.out.println("Return parameter is null");
                throw new Exception("Return parameter is null");
            } else {
                String[] paramArr = customInfo.split("&");
                String[] keyValueArr = null;

                for (int i = 0; i < paramArr.length; i++) {
                    keyValueArr = paramArr[i].split("=");

                    box.put(keyValueArr[0], keyValueArr[1]);
                    System.out.println(keyValueArr[0] + " : " + box.getString(keyValueArr[0]));
                }

                String dispatcherUrl = "/mobile/jsp/myclass/myclassUpdateProgressInfoAjaxResult.jsp";

                MyClassBean bean = new MyClassBean();
                int resultCnt = bean.updateProgressEnd(box);

                req.setAttribute("resultCnt", resultCnt);

                ServletContext sc = getServletContext();
                RequestDispatcher rd = sc.getRequestDispatcher(dispatcherUrl);
                rd.forward(req, res);
            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectCategoryList()\r\n" + ex.getMessage());
        }
    }

    /**
     * 학습할 과정의 기본 정보를 조회한다. 진도율 정보도 갱신한다.
     * 
     * @param req
     * @param res
     * @param box
     * @param out
     * @throws Exception
     */
    public void performSelectMyProgress(HttpServletRequest req, HttpServletResponse res, RequestBox box, PrintWriter out) throws Exception {
        try {
            req.setAttribute("requestbox", box);

            MyClassBean bean = new MyClassBean();

            bean.updateProgressRateInfo(box);

            String myProgress = bean.selectMyProgress(box);
            System.out.println("{\"myProgress\" : \"" + myProgress + "\"}");

            out.println("{\"myProgress\" : \"" + myProgress + "\"}");
            out.flush();

        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectCategoryList()\r\n" + ex.getMessage());
        }

    }

}