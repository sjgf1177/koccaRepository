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
            
            if (process.equals("selectStudySubjectList")) { // ���� ��� ��ȸ 
                this.performSelectStudySubjectList(req, res, box, out);

            } else if (process.equals("selectStudySubjectInfo")) { // ���� ���� ��� ��ȸ
                this.performSelectStudySubjectInfo(req, res, box, out);

            } else if (process.equals("updateProgressStart")) { // �н�â Ȥ�� �������� �� ��, �н� �̷� ������ ���/�����Ѵ�.
                this.performUpdateProgressStart(req, res, box, out);

            } else if (process.equals("updateProgressEnd")) { // �н�Ī Ȥ�� �������� ���� ��, �н� �̷� ������ �߰� ���/�����Ѵ�.
                this.performUpdateProgressEnd(req, res, box, out);

            } else if (process.equals("selectMyProgress")) { // �н�Ī Ȥ�� �������� ���� ��, �н� �̷� ������ �߰� ���/�����Ѵ�.
                this.performSelectMyProgress(req, res, box, out);

            }
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * �н��� ������ �⺻ ������ ��ȸ�Ѵ�. ������ ������ �����Ѵ�.
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

            bean.updateProgressRateInfo(box); // �н� ȭ������ �̵��� �� ������ ������ ����

            String recommendProgress = bean.selectRecommendProgress(box);
            String myProgress = bean.selectMyProgress(box);

            ArrayList<DataBox> subjectLessonList = bean.selectLessonList(box);

            req.setAttribute("recommendProgress", recommendProgress);
            req.setAttribute("myProgress", myProgress);
            req.setAttribute("subjectLessonList", subjectLessonList);
            

            
            
			// ����н�����
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

			
			//�����н�â
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
     * ���԰������� ������û�� �� ���� ����� ��ȸ�Ѵ�.
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
     * �н�â Ȥ�� �������� �� ��, �н� �̷� ������ ���/�����Ѵ�.
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
            
            
            
            /**��ð����ϰ�� ������ 70%���޽� �ڵ�����ó�� �����߰�*/
            /**START*/

            //�Ķ���� �غ�
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
				//���Ῡ��Ȯ��
				String isgrad  = sbean.getIsgrad(box);
				
//				if(!isgrad.equals("Y")){
					//������ Ȯ��
					float progress = Float.parseFloat(sbean.getProgress(box));
					
					if((progress >= 70 && s_subj != "CK20010") || (progress >= 100 && s_subj == "CK20010")){
						//������ 70%�ʰ��� �ڵ�����ó��
						FinishBean fbean = new FinishBean();
						int isOk = fbean.updateAllGraduated(box);
						if(isOk > 0){
							results = "OK";
						}else{
							results = "����ó���� ������ �߻��Ͽ����ϴ�. ��ڿ��� ���� �ٶ��ϴ�.";
						}
					}
//				}
			}
            
			/**END*/
			/**��ð����ϰ�� ������ 70%���޽� �ڵ�����ó�� �����߰�*/
            

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
     * �н�â Ȥ�� �������� ���� ��, �н� �̷� ������ �߰� ���/�����Ѵ�.
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
            // String totalPageNum = box.getString("totalPageNum"); // ����� ���� ��ü ������ 
            // String curPage = box.getString("curPage"); // ����� ���� �н����� ���� ������. 2014�� 8�췯 27�� ���� ȭ�� ����ÿ��� null�� �Ѿ���� �ִ�.
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
     * �н��� ������ �⺻ ������ ��ȸ�Ѵ�. ������ ������ �����Ѵ�.
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