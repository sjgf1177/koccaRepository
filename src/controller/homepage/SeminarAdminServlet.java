//**********************************************************
//  1. ��      ��:  �����ϴ� ����
//  2. ���α׷��� : ____Servlet.java
//  3. ��      ��:  ���� ���α׷�
//  4. ȯ      ��: JDK 1.5
//  5. ��      ��: 1.0
//  6. ��      ��: __����__ 2009. 10. 19
//  7. ��     ��1:
//**********************************************************
package controller.homepage;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.common.SelectEduBean;
import com.credu.homepage.SeminarBean;
import com.credu.library.AlertManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.FormatDate;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;
import com.credu.system.AdminUtil;

@SuppressWarnings({ "unchecked", "serial" })
@WebServlet("/servlet/controller.homepage.SeminarAdminServlet")
public class SeminarAdminServlet extends javax.servlet.http.HttpServlet implements Serializable {

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
//        MultipartRequest multi = null;
        RequestBox box = null;
        String v_process = "";
//        int fileupstatus = 0;

        try {
            response.setContentType("text/html;charset=euc-kr");
            out = response.getWriter();
            box = RequestManager.getBox(request);
            v_process = box.getString("p_process");

            if(ErrorManager.isErrorMessageView()) {
                box.put("errorout", out);
            }
			///////////////////////////////////////////////////////////////////
			if (!AdminUtil.getInstance().checkRWRight("SeminarAdminServlet", v_process, out, box)) {
				return; 
			}
            box.put("starttime", FormatDate.getDate("yyyyMMddHHmmssSSS"));
			///////////////////////////////////////////////////////////////////
			
            if(v_process.equals("selectView")){                      //  �󼼺���� �̵��Ҷ�
                this.performSelectView(request, response, box, out);
            } else if(v_process.equals("insertPage")) {              //  ����������� �̵��Ҷ�
                this.performInsertPage(request, response, box, out);
            } else if(v_process.equals("insert")) {                  //  ����Ҷ�
                this.performInsert(request, response, box, out);
            } else if(v_process.equals("updatePage")) {              //  ������������ �̵��Ҷ�
                this.performUpdatePage(request, response, box, out);
            } else if(v_process.equals("update")) {                  //  �����Ͽ� �����Ҷ�
                this.performUpdate(request, response, box, out);
            } else if(v_process.equals("delete")) {                  //  �����Ҷ�
                this.performDelete(request, response, box, out);
            } else if(v_process.equals("selectList")) {              //  ����Ʈ
                this.performSelectList(request, response, box, out);
            } else if(v_process.equals("insertPassPage")) {           //  ��û�� ��� ������
                this.performInsertPassPage(request, response, box, out);
            } else if(v_process.equals("updatePassPage")) {           //  ������ ����  ������
                this.performUpdatePassPage(request, response, box, out);
            } else if(v_process.equals("selectPassPage")) {           //  ������ �󼼺��� ������
                this.performSelectPass(request, response, box, out);
            } else if(v_process.equals("updatePass")) {               //  ������ ���
                this.performUpdatePass(request, response, box, out);
            } else if(v_process.equals("selectRegList")) {           //  ���̳� ��û��Ȳ
                this.performSelectRegList(request, response, box, out);
            } else if(v_process.equals("updateRegList")) {           //  ���̳� ��û ��Ȳ ��÷/��� ����
                this.performUpdateRegList(request, response, box, out);
            } else if(v_process.equals("excelDown")) {               //  �����ٿ�
                this.performExcelDown(request, response, box, out);
            }
        }catch(Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
        }
    }


    /**
     �󼼺���� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectView(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش�

            SeminarBean bean = new SeminarBean();
            DataBox dbox = bean.selectViewSeminar(box);

            request.setAttribute("selectSeminar", dbox);

            ServletContext sc = getServletContext();
            //RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_Seminar_R.jsp");
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_Seminar_U.jsp");
            rd.forward(request, response);

            //Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_Seminar_R.jsp");

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectView()\r\n" + ex.getMessage());
        }
    }

     /**
     ����������� �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsertPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
            
            SelectEduBean seBean = new SelectEduBean();
			ArrayList grcodenm = seBean.getGrcode(box);
			request.setAttribute("grcodenm", grcodenm);
			
            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_Seminar_I.jsp");
            rd.forward(request, response);

            ////Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_Seminar_I.jsp");

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsertPage()\r\n" + ex.getMessage());
        }
    }

     /**
     ����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performInsert(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            SeminarBean bean = new SeminarBean();

            int isOk = bean.insertSeminar(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.SeminarAdminServlet";
            box.put("p_process", "selectList");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "insert.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "insert.fail";
                alert.alertFailMessage(out, v_msg);
            }

            ////Log.info.println(this, box, v_msg + " on SeminarAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performInsert()\r\n" + ex.getMessage());
        }
    }


     /**
     ������������ �̵��Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdatePage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

			SeminarBean bean = new SeminarBean();
            DataBox dbox = bean.selectViewSeminar(box);
            
            request.setAttribute("selectSeminar", dbox);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_Seminar_U.jsp");
            rd.forward(request, response);

            ////Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_Seminar_U.jsp");

        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
        }
    }

     /**
    //  �����Ͽ� �����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdate(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
         try {
            SeminarBean bean = new SeminarBean();

            int isOk = bean.updateSeminar(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.SeminarAdminServlet";
            box.put("p_process", "selectList");
            //      ���� �� �ش� ����Ʈ �������� ���ư��� ����

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            ////Log.info.println(this, box, v_msg + " on SeminarAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }

     /**
    //  �����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performDelete(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
           SeminarBean bean = new SeminarBean();

            int isOk = bean.deleteSeminar(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.SeminarAdminServlet";
            box.put("p_process", "selectList");

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "delete.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "delete.fail";
                alert.alertFailMessage(out, v_msg);
            }

            ////Log.info.println(this, box, v_msg + " on SeminarAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performDelete()\r\n" + ex.getMessage());
        }
    }

    /**
     ����Ʈ
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performSelectList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
        try {
            request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش�

            SeminarBean bean = new SeminarBean();
            
            //���̳� �з� ����Ʈ 
            //ArrayList selectListSeminarType = bean.selectListSeminarType(box);
            //request.setAttribute("selectListSeminarType", selectListSeminarType);
            
            //���̳� �� ����Ʈ
            //ArrayList selectListSeminarName = bean.selectListSeminarName(box);
            //request.setAttribute("selectListSeminarName", selectListSeminarName);
            

            //�Ϲ� ����Ʈ
            ArrayList selectList = bean.selectListSeminar(box);
            request.setAttribute("selectList", selectList);

            ServletContext sc = getServletContext();
            RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_Seminar_L.jsp");
            rd.forward(request, response);

            ////Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_Seminar_L.jsp");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performSelectList()\r\n" + ex.getMessage());
        }
    }

	/**
	������ ���� ����Ʈ
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performSelectRegList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
	   try {
	       request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش�
	
	       SeminarBean bean = new SeminarBean();
	       
	       //�Ϲ� ����Ʈ
	       ArrayList List = bean.selectRegList(box);
	       request.setAttribute("selectRegList", List);
	       
	       ServletContext sc = getServletContext();
	       RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_SeminarReg_L.jsp");
	       rd.forward(request, response);
	
	       ////Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_Seminar_L.jsp");
	   }catch (Exception ex) {
	       ErrorManager.getErrorStackTrace(ex, out);
	       throw new Exception("performSelectList()\r\n" + ex.getMessage());
	   }
	}
	
    /**
        ������ ����(���)�������� �̵��Ҷ�
   @param request  encapsulates the request to the servlet
   @param response encapsulates the response from the servlet
   @param box      receive from the form object
   @param out      printwriter object
   @return void
   */
   public void performUpdatePassPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
       try {
           request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�

			SeminarBean bean = new SeminarBean();
           //SeminarData data = bean.selectViewSeminar(box);
           DataBox dbox = bean.selectViewSeminar(box);
           
           request.setAttribute("selectSeminar", dbox);

			//ArrayList compnm = bean.selectComp(box);
			//request.setAttribute("compnm", compnm);
           
           ServletContext sc = getServletContext();
           RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_SeminarPass_U.jsp");
           rd.forward(request, response);

           ////Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_Seminar_U.jsp");

       }catch (Exception ex) {
           ErrorManager.getErrorStackTrace(ex, out);
           throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
       }
   }

    /**
   //  �����Ͽ� �����Ҷ�
   @param request  encapsulates the request to the servlet
   @param response encapsulates the response from the servlet
   @param box      receive from the form object
   @param out      printwriter object
   @return void
   */
   public void performUpdatePass(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
       throws Exception {
        try {
           SeminarBean bean = new SeminarBean();

           int isOk = bean.updateSeminarPass(box);

           String v_msg = "";
           String v_url = "/servlet/controller.homepage.SeminarAdminServlet";
           box.put("p_process", "selectPassPage");
           //      ���� �� �ش� ����Ʈ �������� ���ư��� ����

           AlertManager alert = new AlertManager();

           if(isOk > 0) {
               v_msg = "update.ok";
               
               alert.alertOkMessage(out, v_msg, v_url , box);

           }
           else {
               v_msg = "update.fail";
               alert.alertFailMessage(out, v_msg);
           }

           ////Log.info.println(this, box, v_msg + " on SeminarAdminServlet");
       }catch (Exception ex) {
           ErrorManager.getErrorStackTrace(ex, out);
           throw new Exception("performUpdate()\r\n" + ex.getMessage());
       }
   }
   
	/**
	������ ���� �ٿ�ε� 
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performExcelDown(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
	   try {
	       request.setAttribute("requestbox", box);    //��������� box ��ü�� �Ѱ��ش�
	
	       SeminarBean bean = new SeminarBean();
	       
	       //�Ϲ� ����Ʈ
	       ArrayList List = bean.selectRegList(box);
	       request.setAttribute("selectRegList", List);
	
	       ServletContext sc = getServletContext();
	       RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_SeminarReg_E.jsp");
	       rd.forward(request, response);
	
	       ////Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_Seminar_L.jsp");
	   }catch (Exception ex) {
	       ErrorManager.getErrorStackTrace(ex, out);
	       throw new Exception("performSelectList()\r\n" + ex.getMessage());
	   }
	}
	
    /**
	    ������ ��� �������� �̵��Ҷ�
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performSelectPass(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
	   try {
	       request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
	
			SeminarBean bean = new SeminarBean();
	       DataBox dbox = bean.selectViewSeminar(box);
	       
	       request.setAttribute("selectSeminar", dbox);
	
	       ServletContext sc = getServletContext();
	       RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_SeminarPass_R.jsp");
	       rd.forward(request, response);
	
	       ////Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_Seminar_U.jsp");
	
	   }catch (Exception ex) {
	       ErrorManager.getErrorStackTrace(ex, out);
	       throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
	   }
	}
	
    /**
    //  �����Ͽ� �����Ҷ�
    @param request  encapsulates the request to the servlet
    @param response encapsulates the response from the servlet
    @param box      receive from the form object
    @param out      printwriter object
    @return void
    */
    public void performUpdateRegList(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out)
        throws Exception {
         try {
            SeminarBean bean = new SeminarBean();

            int isOk = bean.updateRegList(box);

            String v_msg = "";
            String v_url = "/servlet/controller.homepage.SeminarAdminServlet";
            box.put("p_process", "selectRegList");
            //      ���� �� �ش� ����Ʈ �������� ���ư��� ����

            AlertManager alert = new AlertManager();

            if(isOk > 0) {
                v_msg = "update.ok";
                alert.alertOkMessage(out, v_msg, v_url , box);
            }
            else {
                v_msg = "update.fail";
                alert.alertFailMessage(out, v_msg);
            }

            ////Log.info.println(this, box, v_msg + " on SeminarAdminServlet");
        }catch (Exception ex) {
            ErrorManager.getErrorStackTrace(ex, out);
            throw new Exception("performUpdate()\r\n" + ex.getMessage());
        }
    }
    
	    /**
	    ��û�� ������������ �̵��Ҷ�
	@param request  encapsulates the request to the servlet
	@param response encapsulates the response from the servlet
	@param box      receive from the form object
	@param out      printwriter object
	@return void
	*/
	public void performInsertPassPage(HttpServletRequest request, HttpServletResponse response, RequestBox box, PrintWriter out) throws Exception {
	   try {
	       request.setAttribute("requestbox", box);        //      ��������� box ��ü�� �Ѱ��ش�
	
			SeminarBean bean = new SeminarBean();
	       //EventData data = bean.selectViewEvent(box);
	       ArrayList selectPassList = bean.selectPassList(box);    
	       
	       request.setAttribute("selectPassList", selectPassList);
	
			//ArrayList compnm = bean.selectComp(box);
			//request.setAttribute("compnm", compnm);
	       
	       ServletContext sc = getServletContext();
	       RequestDispatcher rd = sc.getRequestDispatcher("/learn/admin/homepage/za_SeminarPass_I.jsp");
	       rd.forward(request, response);
	
	       ////Log.info.println(this, box, "Dispatch to /learn/admin/homepage/za_Event_U.jsp");
	
	   }catch (Exception ex) {
	       ErrorManager.getErrorStackTrace(ex, out);
	       throw new Exception("performUpdatePage()\r\n" + ex.getMessage());
	   }
	}
}

