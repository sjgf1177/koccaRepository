//**********************************************************
//  1. ��      ��: �н�ȯ�浵��̸� �����ϴ� ����
//  2. ���α׷��� : HomePageHelpServlet.java
//  3. ��      ��: �н�ȯ�浵��� �������� �����Ѵ�(HOMEPAGE)
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: 2005.7.6 �̿���
//  7. ��      ��: 
//**********************************************************

package controller.homepage;

import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.ErrorManager;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;

// import com.credu.infomation.HtmlManageBean;
// import com.credu.library.DataBox;
// import com.credu.library.Log;

@SuppressWarnings("serial")
@WebServlet("/servlet/controller.homepage.HomePageAboutUsServlet")
public class HomePageAboutUsServlet extends javax.servlet.http.HttpServlet {
    
    /**
     * DoGet Pass get requests through to PerformTask
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }
    
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        PrintWriter out = null;
        RequestBox box = null;
        String v_process = "";
        
        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            
            box = RequestManager.getBox(request);
            
            if (box.getSession("tem_grcode") == "") {
                box.setSession("tem_grcode", "N000001");
            }
            
            v_process = box.getStringDefault("p_process", "DirectorMessage");
            
            if (v_process.equals("DirectorMessage")) { // ���� �λ縻
                this.performIntroduce(request, response, box, out);
                
            } else if (v_process.equals("Vision")) { // ���� ����/����
                this.performVision(request, response, box, out);
                
            } else if (v_process.equals("Facility")) { // �ü� �ȳ�
                this.performFacility(request, response, box, out);
                
            } else if (v_process.equals("Direction")) { // ã�ƿ��ô±�
                this.performLocation(request, response, box, out);
                
            } else if (v_process.equals("COOPER")) { // ���±��
                this.performCoperate(request, response, box, out);
                
            } else if (v_process.equals("edu")) { // �������
                this.performBusiness(request, response, box, out);
                
            } else if (v_process.equals("edu2")) { // �������
                this.performBusiness(request, response, box, out);
                
            } else if (v_process.equals("PR")) { // ȫ������
                this.performPrMovie(request, response, box, out);
            }
            /*
             * if(ErrorManager.isErrorMessageView()) { box.put("errorout", out); }
             * 
             * 
             * 
             * String v_url = "/learn/user/portal/aboutus/zu_AboutUs.jsp"; String p_code = "";
             * 
             * if(v_process.equals("DirectorMessage")) { // ���� �λ縻 //v_url = "/learn/user/portal/aboutus/zu_DirectorMessage.jsp"; p_code = "ABOUTUS_MESSAGE"; }else if(v_process.equals("Vision")){ // ���� ����/���� //v_url = "/learn/user/portal/aboutus/zu_Vision.jsp"; p_code = "ABOUTUS_VISION"; }else if(v_process.equals("Facility")){ // �ü� �ȳ� //v_url = "/learn/user/portal/aboutus/zu_Facility.jsp"; p_code = "ABOUTUS_FACILITY"; }else if(v_process.equals("Direction")){ // ã�ƿ��ô±� //v_url = "/learn/user/portal/aboutus/zu_Direction.jsp"; p_code = "ABOUTUS_DIRECTION"; }else if(v_process.equals("COOPER")){ // ã�ƿ��ô±� //v_url = "/learn/user/portal/aboutus/zu_Direction.jsp"; p_code = "ABOUTUS_COOPER"; } else if( v_process.equals("edu") ) { // ��������ȳ� box.sync("tabid");
             * 
             * String tabid = box.getStringDefault("tabid", "1");
             * 
             * if( tabid.equals("1") ) { v_url = "/learn/user/portal/aboutus/zu_eduintro1.jsp"; } else if( tabid.equals("2") ) { v_url = "/learn/user/portal/aboutus/zu_eduintro2.jsp"; } else if( tabid.equals("3") ) { v_url = "/learn/user/portal/aboutus/zu_eduintro3.jsp"; } else if( tabid.equals("4") ) { v_url = "/learn/user/portal/aboutus/zu_eduintro4.jsp"; } else if( tabid.equals("5") ) { v_url = "/learn/user/portal/aboutus/zu_eduintro5.jsp"; } else if( tabid.equals("6") ) { v_url = "/learn/user/portal/aboutus/zu_eduintro6.jsp"; } else if( tabid.equals("7") ) { v_url = "/learn/user/portal/aboutus/zu_eduintro7.jsp"; } else if( tabid.equals("8") ) { v_url = "/learn/user/portal/aboutus/zu_eduintro8.jsp"; } else if( tabid.equals("9") ) { v_url = "/learn/user/portal/aboutus/zu_eduintro9.jsp"; } }
             * 
             * request.setAttribute("requestbox", box);
             * 
             * if( !v_process.equals("edu") ) { box.put("p_code", p_code);
             * 
             * HtmlManageBean bean = new HtmlManageBean();
             * 
             * DataBox dbox = bean.selectView(box);
             * 
             * request.setAttribute("selectView", dbox); }
             * 
             * 
             * ServletContext sc = getServletContext(); RequestDispatcher rd = sc.getRequestDispatcher(v_url); rd.forward(request, response);
             */
            // Log.info.println(this, box, "Dispatch to "+v_url);
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }

    /**
     * ȫ������ ȭ������ �̵��Ѵ�.
     * @param request
     * @param response
     * @param box
     * @param out
     * @throws Exception
     */
    public void performPrMovie(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url =  "/learn/user/2013/portal/aboutus/zu_HomePagePR_R.jsp";
            
            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
            
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception( this.getClass().getCanonicalName() + ".performPrMovie()\r\n" + ex.getMessage());
        }
        
    }

    /**
     * �����λ縻
     * 
     * @param request
     *            encapsulates the request to the servlet
     * @param response
     *            encapsulates the response from the servlet
     * @param box
     *            receive from the form object
     * @param out
     *            printwriter object
     * @return void
     */
    public void performIntroduce(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "";
            
            // v_url = "/learn/user/2012/portal/aboutus/zu_DirectorMessage.jsp";
            // v_url = "/learn/user/2013/portal/aboutus/zu_DirectorMessage.jsp";
            v_url = "/learn/user/portal/aboutus/zu_DirectorMessage.jsp";
            
            if(box.getSession("tem_type").equals("B")){
            	v_url = "/learn/user/typeB/aboutus/zu_DirectorMessage.jsp";
            }
            
            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
            
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performPersonal()\r\n" + ex.getMessage());
        }
    }
    
    /**
     * ����
     * 
     * @param request
     *            encapsulates the request to the servlet
     * @param response
     *            encapsulates the response from the servlet
     * @param box
     *            receive from the form object
     * @param out
     *            printwriter object
     * @return void
     */
    public void performVision(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "";
            
            // v_url = "/learn/user/2012/portal/aboutus/zu_Vision.jsp";
            v_url = "/learn/user/2013/portal/aboutus/about_Academy.jsp";
            
            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
            
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performPersonal()\r\n" + ex.getMessage());
        }
    }
    
    /**
     * �������
     * 
     * @param request
     *            encapsulates the request to the servlet
     * @param response
     *            encapsulates the response from the servlet
     * @param box
     *            receive from the form object
     * @param out
     *            printwriter object
     * @return void
     */
    public void performBusiness(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "";
            
            String gubun = box.getString("p_gubun");
            
            if (gubun.equals("1")) {
                // v_url = "/learn/user/2012/portal/aboutus/zu_Business1.jsp";
                v_url = "/learn/user/2013/portal/aboutus/zu_Business1.jsp";
            } else if (gubun.equals("2")) {
                // v_url = "/learn/user/2012/portal/aboutus/zu_Business2.jsp";
                v_url = "/learn/user/2013/portal/aboutus/zu_Business2.jsp";
            } else if (gubun.equals("3")) {
                // v_url = "/learn/user/2012/portal/aboutus/zu_Business3.jsp";
                v_url = "/learn/user/2013/portal/aboutus/zu_Business3.jsp";
            } else if (gubun.equals("4")) {
                // v_url = "/learn/user/2012/portal/aboutus/zu_Business4.jsp";
                v_url = "/learn/user/2013/portal/aboutus/zu_Business4.jsp";
            } else {
                // v_url = "/learn/user/2012/portal/aboutus/zu_Business1.jsp";
                v_url = "/learn/user/2013/portal/aboutus/zu_Business1.jsp";
            }
            /**
             * else if (gubun.equals("5")) { v_url = "/learn/user/2012/portal/aboutus/zu_Business5.jsp"; }else if (gubun.equals("6")) { v_url = "/learn/user/2012/portal/aboutus/zu_Business6.jsp"; }else if (gubun.equals("7")) { v_url = "/learn/user/2012/portal/aboutus/zu_Business7.jsp"; }else if (gubun.equals("8")) { v_url = "/learn/user/2012/portal/aboutus/zu_Business8.jsp"; }
             **/
            
            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
            
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performPersonal()\r\n" + ex.getMessage());
        }
    }
    
    /**
     * ����
     * 
     * @param request
     *            encapsulates the request to the servlet
     * @param response
     *            encapsulates the response from the servlet
     * @param box
     *            receive from the form object
     * @param out
     *            printwriter object
     * @return void
     */
    public void performCoperate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "";
            
            // v_url = "/learn/user/2012/portal/aboutus/zu_Coperate.jsp";
            v_url = "/learn/user/2013/portal/aboutus/zu_Coperate.jsp";
            
            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
            
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performPersonal()\r\n" + ex.getMessage());
        }
    }
    
    /**
     * ���ô±�
     * 
     * @param request
     *            encapsulates the request to the servlet
     * @param response
     *            encapsulates the response from the servlet
     * @param box
     *            receive from the form object
     * @param out
     *            printwriter object
     * @return void
     */
    public void performLocation(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "";
            
            // v_url = "/learn/user/2012/portal/aboutus/zu_Location.jsp";
            v_url = "/learn/user/2013/portal/aboutus/zu_Location.jsp";
            
            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
            
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performPersonal()\r\n" + ex.getMessage());
        }
    }
    
    /**
     * �ü�
     * 
     * @param request
     *            encapsulates the request to the servlet
     * @param response
     *            encapsulates the response from the servlet
     * @param box
     *            receive from the form object
     * @param out
     *            printwriter object
     * @return void
     */
    public void performFacility(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            String v_url = "";
            
            // v_url = "/learn/user/2012/portal/aboutus/zu_Facility.jsp";
            v_url = "/learn/user/2013/portal/aboutus/zu_Facility.jsp";
            
            request.setAttribute("requestbox", box); // ��������� box ��ü�� �Ѱ��ش�
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher(v_url);
            rd.forward(request, response);
            
        } catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performPersonal()\r\n" + ex.getMessage());
        }
    }
    
}
