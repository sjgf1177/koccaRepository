/**
* 프로젝트명 : kocca_java
* 패키지명 : controller.mobile.study
* 파일명 : MyClassServlet.java
* 작성날짜 : 2011. 10. 1.
* 처리업무 : 모바일 나의강의실 servlet
* 수정내용 :
 
* Copyright by CREDU.Co., LTD. ALL RIGHTS RESERVED.   
*/

package controller.mobile.study;

import java.io.PrintWriter;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.homepage.HomePageContactBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.Log;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.mobile.course.CourseBean;
import com.credu.mobile.study.MobileMyClassBean;
import com.credu.study.MyClassBean;
import com.credu.study.MyClassBillBean;
import com.credu.study.MyQnaBean;

@WebServlet("/servlet/controller.mobile.study.MyClassServlet")
public class MyClassServlet extends javax.servlet.http.HttpServlet 
{
	/* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException 
    {
        this.doPost(request, response);
    }
    
    /* (non-Javadoc)
     * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException 
    {
    	PrintWriter out = null;
        RequestBox box = null;
        String v_process = "";
        
        try 
        {
            request.setCharacterEncoding("euc-kr");
            response.setContentType("text/html;charset=euc-kr");

            out = response.getWriter();
            box = RequestManager.getBox(request);

            v_process = box.getString("p_process");         // process
            
            if(ErrorManager.isErrorMessageView()) 
            {
                box.put("errorout", out);
            }
            
            ErrorManager.isMobileReturnUrl(out, request, box, "AA");
            
            if("onLineStudyingList".equals(v_process))
            {
            	this.performOnLineStudyIngListPage(request, response, box, out);		//수강신청중인과정(온라인 과정)
            }
            else if("offLineStudyingList".equals(v_process))
            {
            	this.performOffLineStudyIngListPage(request, response, box, out);		//수강신청중인과정(오프라인 과정)
            }
            else if("onLineStudyendList".equals(v_process))
            {
            	this.performOnLineStudyEndListPage(request, response, box, out);		//나의교육이력(온라인 과정)
            }
            else if("offLineStudyendList".equals(v_process))
            {
            	this.performOffLineStudyEndListPage(request, response, box, out);		//수강완료과정(오프라인 과정)
            }
            else if("onLineProposeList".equals(v_process))
            {
            	this.performOnLineProposeListPage(request, response, box, out);			//수강신청확인(온라인 과정)
            }
            else if("offLineProposeList".equals(v_process))
            {
            	this.performOffLineProposeListPage(request, response, box, out);		//수강신청확인(오프라인 과정)
            }
            else if("onlinePayList".equals(v_process))
            {
            	this.performOnLinePayListPage(request, response, box, out);				//수강료결재 조회(온라인 과정)
            }
            else if("offlinePayList".equals(v_process))
            {
            	this.performOffLinePayListPage(request, response, box, out);			//수강료결재 조회(오프라인 과정)
            }
            else if("counselList".equals(v_process))
            {
            	this.performCounSelingListPage(request, response, box, out);			//나의 상담 목록
            }
            else if("counselWritePage".equals(v_process))
            {
            	this.performCounSelingWritePage(request, response, box, out);			//나의 상담 쓰기 페이지
            }
            else if("counselWrite".equals(v_process))
            {
            	this.performCounSelingWrite(request, response, box, out);				//나의 상담 쓰기 등록처리
            }
            //
            else if("counselRemove".equals(v_process))
            {
            	this.performCounSelingRemove(request, response, box, out);				//나의 상담 쓰기 삭제처리
            }
            else if("counselViewPage".equals(v_process))
            {
            	this.performCounSelingViewPage(request, response, box, out);				//나의 상당 상세 페이지
            }
            else if("MyQnaCounselQnaViewPage".equals(v_process))
            {
            	this.performMyQnaCounselQnaViewPage(request, response, box, out);				//나의 상당 상세 페이지
            }
            else if("onLineScoreViewPage".equals(v_process))
            {
            	this.performOnLineScoreViewPage(request, response, box, out);				//온라인 상적보기 페이지
            }
            else if("offLineScoreViewPage".equals(v_process))
            {
            	this.performOffLineScoreViewPage(request, response, box, out);				//오프라인 상적보기 페이지
            }
            else if("eduOnlineViewPage".equals(v_process))
            {
            	this.performEduOnlineViewPage(request, response, box, out);					//온라인과정 학습하기
            }
           
        }
        catch(Exception ex) 
        {
            ErrorManager.getErrorStackTrace(ex, out);  
        }
    }
 
    /**
     * 모바일 수강중인 과정 목록(온라인 과정)
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performOnLineStudyIngListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception 
    { 
    	try
    	{
    		request.setAttribute("requestbox", box);        									//      명시적으로 box 객체를 넘겨준다
    		
    		MyClassBean bean = new MyClassBean();
			request.setAttribute("_LIST_", bean.selectEducationSubjectListMobile(box));
			
    		ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/mobile/study/zu_onLinestudying_L.jsp");
            rd.forward(request, response);
            
        } 
    	catch ( Exception ex ) 
    	{ 
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performOnLineStudyIngListPage()\r\n" + ex.getMessage() );
        }
    }
   
    /**
     * 모바일 수강중인 과정 목록(오프라인 과정)
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performOffLineStudyIngListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception 
    { 
    	try
    	{
    		request.setAttribute("requestbox", box);        									//      명시적으로 box 객체를 넘겨준다
    		
    		MyClassBean bean = new MyClassBean();
			request.setAttribute("_LIST_", bean.selectEducationOffSubjectList(box));
			
    		ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/mobile/study/zu_offLinestudying_L.jsp");
            rd.forward(request, response);
            
        } 
    	catch ( Exception ex ) 
    	{ 
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performOffLineStudyIngListPage()\r\n" + ex.getMessage() );
        }
    }
    
    /**
     * 모바일 수강완료 과정 목록(온라인 과정)
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performOnLineStudyEndListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception 
    { 
    	try
    	{
    		request.setAttribute("requestbox", box);        									//      명시적으로 box 객체를 넘겨준다
    		
    		MyClassBean bean = new MyClassBean();
			request.setAttribute("_LIST_", bean.selectStudyHistoryTotList(box));
			
    		ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/mobile/study/zu_onLinestudyEnd_L.jsp");
            rd.forward(request, response);
            
        } 
    	catch ( Exception ex ) 
    	{ 
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performOnLineStudyEndListPage()\r\n" + ex.getMessage() );
        }
    }
   
    /**
     * 모바일 수강완료 과정 목록(오프라인 과정)
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performOffLineStudyEndListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception 
    { 
    	try
    	{
    		request.setAttribute("requestbox", box);        									//      명시적으로 box 객체를 넘겨준다
    		
    		MyClassBean bean = new MyClassBean();
			request.setAttribute("_LIST_", bean.selectEducationOffSubjectList(box));
			
    		ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/mobile/study/zu_offLinestudyEnd_L.jsp");
            rd.forward(request, response);
            
        } 
    	catch ( Exception ex ) 
    	{ 
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performOffLineStudyEndListPage()\r\n" + ex.getMessage() );
        }
    }
    
    
    /**
     * 수강신청확인(온라인)
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performOnLineProposeListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception 
    { 
    	try
    	{
    		request.setAttribute("requestbox", box);        									//      명시적으로 box 객체를 넘겨준다
    		
    		MyClassBean bean = new MyClassBean();
			request.setAttribute("_LIST_", bean.selectProposeHistoryList(box));
			
    		ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/mobile/study/zu_onLinepropose_L.jsp");
            rd.forward(request, response);
            
        } 
    	catch ( Exception ex ) 
    	{ 
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performOnLineProposeListPage()\r\n" + ex.getMessage() );
        }
    }
    
    /**
     * 수강신청확인(오프라인)
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performOffLineProposeListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception 
    { 
    	try
    	{
    		request.setAttribute("requestbox", box);        									//      명시적으로 box 객체를 넘겨준다
    		
    		MyClassBean bean = new MyClassBean();
			request.setAttribute("_LIST_", bean.selectProposeOffHistoryList(box));
			
    		ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/mobile/study/zu_offLinepropose_L.jsp");
            rd.forward(request, response);
            
        } 
    	catch ( Exception ex ) 
    	{ 
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performOffLineProposeListPage()\r\n" + ex.getMessage() );
        }
    } 
    
    
    /**
     * 수강료결재 조회
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performOnLinePayListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception 
    { 
    	try
    	{
    		request.setAttribute("requestbox", box);        									//      명시적으로 box 객체를 넘겨준다
    		
    		MyClassBillBean bean = new MyClassBillBean();
			request.setAttribute("_LIST_", bean.selectMyClassBillList(box));
			
    		ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/mobile/study/zu_onlinepay_L.jsp");
            rd.forward(request, response);
            
        } 
    	catch ( Exception ex ) 
    	{ 
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performOnLinePayListPage()\r\n" + ex.getMessage() );
        }
    } 
    
    /**
     * 수강료결재 조회(오프라인 과정)
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performOffLinePayListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception 
    { 
    	try
    	{
    		request.setAttribute("requestbox", box);        									//      명시적으로 box 객체를 넘겨준다
    		
    		MyClassBillBean bean = new MyClassBillBean();
			request.setAttribute("_LIST_", bean.selectMyOffClassBillList(box));
			
    		ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/mobile/study/zu_offlinepay_L.jsp");
            rd.forward(request, response);
            
        } 
    	catch ( Exception ex ) 
    	{ 
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performOffLinePayListPage()\r\n" + ex.getMessage() );
        }
    } 
    
    /**
     * 나의 상담내역 목록
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performCounSelingListPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception 
    { 
    	try
    	{
    		request.setAttribute("requestbox", box);        									//      명시적으로 box 객체를 넘겨준다
    		
    		MyQnaBean bean = new MyQnaBean();
			request.setAttribute("_LIST_", bean.SelectMyQnaCounselList(box));
			
    		ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/mobile/study/zu_counsel_L.jsp");
            rd.forward(request, response);
            
        } 
    	catch ( Exception ex ) 
    	{ 
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCounSelingListPage()\r\n" + ex.getMessage() );
        }
    }
    
    /**
     * 나의 상담 등록 페이지
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performCounSelingWritePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception 
    { 
    	try
    	{
    		request.setAttribute("requestbox", box);        									//      명시적으로 box 객체를 넘겨준다
    		
    		MyQnaBean bean = new MyQnaBean();
			request.setAttribute("_LIST_", bean.SelectMyQnaCounselList(box));
			
    		ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/mobile/study/zu_counsel_I.jsp");
            rd.forward(request, response);
            
        } 
    	catch ( Exception ex ) 
    	{ 
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCounSelingWritePage()\r\n" + ex.getMessage() );
        }
    }
    
    /**
     * 나의상담내역 등록
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performCounSelingWrite(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception 
    { 
    	try
    	{
    		HomePageContactBean bean = new HomePageContactBean();
			
            int isOk = bean.insertQnaQue(box);

            String v_msg = "";
            String v_url = "/servlet/controller.mobile.study.MyClassServlet";
            box.put("p_process", "counselList");
            box.put("p_types", "");
            box.put("p_type", "");
            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on QnaServlet");
            
        } 
    	catch ( Exception ex ) 
    	{ 
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCounSelingWrite()\r\n" + ex.getMessage() );
        }
    }
    
    /**
     * 나의 상담내역 상세 페이지
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performCounSelingViewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception 
    { 
    	try
    	{
    		request.setAttribute("requestbox", box);        									//      명시적으로 box 객체를 넘겨준다
    		
    		MyQnaBean bean = new MyQnaBean();
    		DataBox dbox = bean.selectQna(box);
			request.setAttribute("_VIEW_", dbox);
			
			if(dbox.getString("d_okyn1").equals("3")){
				DataBox dbox2 = bean.selectAns(box);
				request.setAttribute("_REVIEW_", dbox2);
			}
			
    		ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/mobile/study/zu_counsel_R.jsp");
            rd.forward(request, response);
            
        } 
    	catch ( Exception ex ) 
    	{ 
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCounSelingViewPage()\r\n" + ex.getMessage() );
        }
    }
    
    /**
     * 온라인 성적 보기
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performOnLineScoreViewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception 
    { 
    	try
    	{
    		request.setAttribute("requestbox", box);        									//      명시적으로 box 객체를 넘겨준다
    		
    		MyClassBean bean = new MyClassBean();
    		
			request.setAttribute("_VIEW_", bean.selectEducationOffTermScoreList(box));
			
			
    		ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/mobile/study/zu_onScore_R.jsp");
            rd.forward(request, response);
            
        } 
    	catch ( Exception ex ) 
    	{ 
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performOnLineScoreViewPage()\r\n" + ex.getMessage() );
        }
    }
    
    /**
     * 오프라인 점수 보기
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performOffLineScoreViewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception 
    { 
    	try
    	{
    		request.setAttribute("requestbox", box);        									//      명시적으로 box 객체를 넘겨준다
    		
    		MyClassBean bean = new MyClassBean();
    		
			request.setAttribute("_VIEW_", bean.selectEducationOffTermScoreList(box));
			
			
    		ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/mobile/study/zu_offScore_R.jsp");
            rd.forward(request, response);
            
        } 
    	catch ( Exception ex ) 
    	{ 
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performOffLineScoreViewPage()\r\n" + ex.getMessage() );
        }
    }
    
    //온라인 과정 학습하기
    public void performEduOnlineViewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception 
    { 
    	try
    	{
    		request.setAttribute("requestbox", box);        									//      명시적으로 box 객체를 넘겨준다
    		
    		CourseBean bean = new CourseBean();
    		request.setAttribute("_VIEW_", bean.getOnCouseData( box ));
    		
    		request.setAttribute("_LIST_", new MobileMyClassBean().getOnLineStudyLessonList(box));
			
			
    		ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/mobile/study/zu_onLineStudy_R.jsp");
            rd.forward(request, response);
            
        } 
    	catch ( Exception ex ) 
    	{ 
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performOffLineScoreViewPage()\r\n" + ex.getMessage() );
        }
    }
    
    /**
     * 나의상담내역 삭제
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performCounSelingRemove(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception 
    { 
    	try
    	{
    		HomePageContactBean bean = new HomePageContactBean();
			
            int isOk = bean.deleteQna(box);

            String v_msg = "";
            String v_url = "/servlet/controller.mobile.study.MyClassServlet";
            box.put("p_process", "counselList");
            
            box.put("p_types", "");
            box.put("p_type", "");
            
            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            Log.info.println(this, box, v_msg + " on QnaServlet");
            
        } 
    	catch ( Exception ex ) 
    	{ 
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performCounSelingRemove()\r\n" + ex.getMessage() );
        }
    }
    
	public void performMyQnaCounselQnaViewPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception 
	{
		try {
			request.setAttribute("requestbox", box);    //명시적으로 box 객체를 넘겨준다

			MyQnaBean bean = new MyQnaBean();
			DataBox dbox = bean.selectMyQnaCounselQna(box);
			request.setAttribute("_VIEW_", dbox);

			ArrayList list = bean.selectMyQnaCounselQnaListA(box);
			request.setAttribute("selectMyQnaCounselQnaListA", list);

			ServletContext sc = getServletContext();
			RequestDispatcher rd = sc.getRequestDispatcher("/learn/mobile/study/zu_counsel_R.jsp");
			rd.forward(request, response);

		}catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, out);
			throw new Exception("performSelectView()\r\n" + ex.getMessage());
		}
	}
	
	
}
