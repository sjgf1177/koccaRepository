//**********************************************************
//  1. 제      목: 다운로드 관리
//  2. 프로그램명: SessionTable.java
//  3. 개      요: 다운로드를 관리한다
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이정한 2003. 4. 26
//  7. 수      정: 이정한 2003. 4. 26
//**********************************************************

package controller.library;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;

import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.credu.library.ConfigSet;
import com.credu.library.RequestBox;
import com.credu.library.RequestManager;


/**
 * Download과련 Servlet Class
 *
 * @date   : 2003. 5
 * @author : j.h. lee
 */
@WebServlet("/servlet/controller.library.DownloadServlet")
public class DownloadServlet extends HttpServlet {
    /**
     * 
     */
    private static final long serialVersionUID = 5176431904177527781L;
    /**
     * DoGet
     * Pass get requests through to PerformTask
     */
    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {
        this.doPost(request, response);
    }
    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws javax.servlet.ServletException, java.io.IOException {

        RequestBox box = null;
        //PrintWriter out = null;
        File file=null;
        FileInputStream fin=null;
        ServletOutputStream sout = null;
        String v_savefile = "";
        String v_realfile = "";
        String v_year = "";
        String v_subj = "";

        boolean isFound = false;

        try {
            response.setContentType("text/html;charset=euc-kr");
            box = RequestManager.getBox(request);

            v_year     = box.getStringDefault("p_year","");
            v_subj     = box.getStringDefault("p_subj","");
            v_savefile = box.getString("p_savefile");
            v_realfile = box.getString("p_realfile");

            ConfigSet conf = new ConfigSet();
            String v_updir = "";

            String v_defaultUrl = conf.getProperty("dir.home");
            
            System.out.println("file path :::: " + v_savefile);
            if(v_savefile.indexOf("/upload/") != 0 && v_savefile.indexOf("\\upload\\") != 0){
            	v_savefile = v_savefile.replaceAll("\\\\", "");
            	v_savefile = v_savefile.replaceAll("/", "");
    			v_savefile = v_savefile.replaceAll("%2F", "");
            	v_savefile = v_savefile.replaceAll("%", "");
            }
            v_savefile = v_savefile.replaceAll("\\\\", "/");

            //리포트 경로(년도 + 과정코드)
            if (!v_year.equals("")) {
                v_updir += "/" + v_year + "/" + v_subj;
            }
            file = new File(v_defaultUrl+v_savefile);

            byte b[] = new byte [1024];
            fin = new FileInputStream(file);

            isFound = true; // 파일을 일단 읽어들였음

            if ( request.getHeader("User-Agent").indexOf("MSIE 5.5") != -1 ) {
                //RESIN에서
                response.setHeader("Content-Disposition", "filename=\"" + new String(v_realfile.getBytes("UTF-8"), "ISO-8859-1")  + "\";");
                //JEUS에서
                // response.setHeader("Content-Disposition", "filename=\"" + new String(v_realfile.getBytes("EUC-KR"))  + "\";");
                response.setHeader("Content-Type", "doesn/matter;");
            }
            else {
                response.setHeader("Content-Type", "application/octet-stream;");
                //RESIN에서
                response.setHeader("Content-Disposition", "attachment;filename=\""+ new String(v_realfile.getBytes("UTF-8"), "ISO-8859-1") + "\";");
                //JEUS에서
                //response.setHeader("Content-Disposition", "attachment;filename=\""+ new String(v_realfile.getBytes("EUC-KR")) + "\";");


                //response.setHeader("Content-Disposition", "attachment;filename=\"" + new String(v_realfile.getBytes(),"8859_1")  + "\";");
                //response.setHeader("Content-Disposition", "attachment;filename=\""+ v_savefile + "\";");
                //response.setHeader("Content-Type", "application/octet-stream;");
                //response.setHeader("Content-Disposition", "attachment;filename="+ new String(v_realfile.getBytes("EUC-KR"), "ISO-8859-1") + ";");
                //response.setHeader("Content-Disposition", "filename=" + new String(v_realfile.getBytes(),"8859_1")  + ";");
                //response.setHeader("Content-Disposition", "attachment;filename="+ v_realfile + ";");
            }
            response.setHeader("Content-Transfer-Encoding", "binary;");
            response.setHeader("Content-Length", ""+file.length());
            response.setHeader("Pragma", "no-cache;");
            response.setHeader("Expires", "-1;");

            sout = response.getOutputStream();

            int numRead = fin.read(b);
            while (numRead != -1) {
                sout.write(b, 0, numRead);
                numRead = fin.read(b);
            }
            sout.flush();
        }
        catch (Exception e) {
        	e.printStackTrace();
        	response.setContentType("text/html;charset=UTF-8");
        	PrintWriter out = response.getWriter();
        	out.println("<script language=javascript>");
	        if(isFound) {
	            out.println("   alert(\"파일을 불러오는 중에 에러가 발생하였습니다.\\n\\n운영자에게 문의 바랍니다.\");");
	        }
	        else {
	            out.println("   alert(\"파일을 읽는 중에 에러가 발생하였습니다.\\n\\n운영자에게 문의 바랍니다.\");");
	        }
	        out.println("history.back(-1)");
	        out.println("</script>");
	        out.close();
        }
        finally {
            try { sout.close(); } catch (Exception e1) {}
            try { fin.close(); }  catch (Exception e2) {}
        }
    }
}
