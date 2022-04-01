<%//@ page language="java" contentType="text/html; charset=euc-kr" pageEncoding="euc-kr"%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page import="java.io.File"       %>
<%@ page import="java.util.Iterator" %>
<%@ page import="java.util.List"     %>
<%@ page import="java.lang.Runtime"  %>
<%@ page import="java.lang.Process"  %>

<%@ page import="org.apache.commons.fileupload.FileItem"                                  %>
<%@ page import="org.apache.commons.fileupload.FileUploadException"                       %>
<%@ page import="org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException" %>
<%@ page import="org.apache.commons.fileupload.disk.DiskFileItemFactory"                  %>
<%@ page import="org.apache.commons.fileupload.servlet.ServletFileUpload"                 %>
<%//@ page import="com.dunet.common.util.UploadProgressListener"                            %>
<%@ page import="com.credu.library.FormatDate"                                            %>
<%@ page import="com.credu.library.ConfigSet"                                             %>
<%@ page import="com.dunet.common.util.UploadUtil"                                        %>
<%@ page import="com.credu.system.CodeAdminBean"                                          %>
<%@ page import="com.credu.library.DataBox"                                               %>
<%@ page import="com.dunet.common.util.StringUtil"                                        %>

<%
    ConfigSet conf = new ConfigSet();    
    int iMegaByte = 10;  // MByte 단위의 용량 제한

    int nMaxFileLen = iMegaByte * 1024 * 1024;

    //전달인자 받기 (일반 Request)
    String  sServletName    = (String)request.getParameter("p_servletnm");
    String  sParamName      = (String)request.getParameter("p_paramnm");
    String  sRealFileName   = (String)request.getParameter("p_real_filename");
    int     iFileSeq        = Integer.parseInt(request.getParameter("p_fileseq"));
    String  sInputParamName = (String)request.getParameter("p_inputFileParamName");
    
    String  sYear           = (String)request.getParameter("p_year");
    String  sSubj           = (String)request.getParameter("p_subj");
    
    String  sUserId         = (String)session.getAttribute("userid");
    
    System.out.println("sServletName : "+sServletName);
    System.out.println("sParamName : "+sParamName);
    System.out.println("sRealFileName : "+sRealFileName);
    System.out.println("iFileSeq : "+iFileSeq);
    System.out.println("sUserId : "+sUserId);
    
    System.out.println("sYear : "+sYear);
    System.out.println("sSubj : "+sSubj);
    
    //임시경로 설정
    String UploadFileTempPATH   = "";       // 임시 업로드 경로
    
    UploadFileTempPATH = conf.getDir(UploadFileTempPATH, sServletName);
    
    System.out.println("UploadFileTempPATH : "+UploadFileTempPATH);

    //전달인자 받기 (uploadForm)
    String  sAttachName         = "";       // 업로드 파일명
    String  sFileExt            = "";       // 확장자 명
    long    nFileSize           = 0;        // 업로드 파일 사이즈
    String  sAttachFullPath     = "";
    File    uploadedFile        = null;
    

    //업로드파일명
    UploadUtil uploadUtil = new UploadUtil();
    
    String  sUploadNm       = uploadUtil.getSaveNewFileName(sServletName, sParamName, sRealFileName, iFileSeq, sUserId);
    String  sUploadTempPath = uploadUtil.getTempUploadDir(sServletName, sYear, sSubj);
    String  sWebUploadTempPath = uploadUtil.getWebTempUploadDir(sServletName, sYear, sSubj);
    
    System.out.println("sUploadNm : "+sUploadNm);
    System.out.println("sUploadTempPath : "+sUploadTempPath);
    
    //업로드화일허용 코드
    List sExtArr = (List)CodeAdminBean.getUploadCodeNameToList();
    System.out.println("sExtArr : "+sExtArr);
     //String sExtArr[]       = {".XLS", ".XLSX", ".DOC", ".DOCX", ".PPT", ".PPTX", ".JPG", ".GIF", ".BMP", ".ZIP", ".PDF" , ".PNG"};

    //첨부파일 임시 폴더 생성 (연월 폴더)
    File    file    = new File(sUploadTempPath);
    System.out.println("file.exists() : "+file.exists());
    if (!file.exists()) {
        try{
        file.mkdirs();
        } catch (Exception e){
            System.out.println(e.toString());
        }
    }
    System.out.println(file.toString());
    System.out.println("file.exists() : "+file.exists());
    //첨부파일 저장
    //UploadProgressListener  progressListener    = new UploadProgressListener(request);

    try {
        int     myMaxMemorySize     = 1024 * 10;                // threshold  값 설정 (10KB)
        long    myMaxRequestSize    = 1024 * 1024 * 10;         // 업로드 최대 사이즈 설정 (10MB)

        File    uploadDirectory     = new File(sUploadTempPath);
        
        DiskFileItemFactory factory = new DiskFileItemFactory();
        
        factory.setSizeThreshold(myMaxMemorySize);
        
        factory.setRepository(uploadDirectory);                 // 임시 업로드 디렉토리 설정
        
        ServletFileUpload   upload  = new ServletFileUpload(factory);
        
        upload.setSizeMax(myMaxRequestSize);
        upload.setHeaderEncoding("euc-kr");                      // 인코딩 설정
        //upload.setProgressListener(progressListener);           // 진행상태 리스너 추가

        List        items   = upload.parseRequest(request);     // 업로드 시작

        Iterator    iter    = items.iterator();

        while (iter.hasNext())
        {
            FileItem    item    = (FileItem)iter.next();
            
            if (!item.isFormField())
            {
                String  fileName    = item.getName();
                System.out.println("fileName : "+fileName);
                if ( (null == fileName) || "".equals(fileName))     continue;

                if (fileName.lastIndexOf("\\") > -1)
                {
                    sAttachName     = fileName.substring(fileName.lastIndexOf("\\") + 1, fileName.length());
                }
                else if (fileName.lastIndexOf("/") > -1)
                {
                    sAttachName     = fileName.substring(fileName.lastIndexOf("/") + 1, fileName.length());
                }
                else
                {
                    sAttachName     = fileName;
                }
                System.out.println("sAttachName : "+sAttachName);

                sAttachFullPath = sUploadTempPath;
                nFileSize       = item.getSize();
                
                System.out.println("sAttachFullPath : "+sAttachFullPath);
                System.out.println("nFileSize : "+nFileSize);

                if (sAttachName.lastIndexOf(".") > -1)
                {
                    sFileExt    = sAttachName.substring(sAttachName.lastIndexOf(".")+1);
                }
                else
                {
                    sFileExt    = "";
                }

                uploadedFile    = new File(sUploadTempPath + "/" + sUploadNm);
                System.out.println("uploadedFile.exists() : "+uploadedFile.exists());
                if (uploadedFile.exists()) {
                    uploadedFile.delete();
                }
                System.out.println("uploadedFile.exists() : "+uploadedFile.exists());
                item.write(uploadedFile);
                System.out.println("item.write(uploadedFile)");
                boolean     bResultExt      = false;
                String      sExt            = sFileExt.toLowerCase();
                System.out.println("sExt : "+sExt);
                for (int i = 0; i < sExtArr.size(); i++)
                {
                    if ( ((String)sExtArr.get(i)).equals(sExt) )
                    {
                        bResultExt  = true;
                        break;
                    }
                }

                //파일형식 제한(gif, jpg, bmp, png)
                if ( !bResultExt )
                {
                    uploadedFile.delete();

                    out.println("<script language='javascript'>");
                    out.println("   alert('"+sExt+" 형식의 파일은 업로드 하실수 없습니다.');");
                    out.println("   parent.fnFileUploadError();");
                    out.println("</script>");
                    return;
                }

                //파일용량 제한
                if (nFileSize > nMaxFileLen)
                {
                    uploadedFile.delete();

                    out.println("<script language='javascript'>");
                    out.println("   alert('"+iMegaByte+"MByte 이상의 파일은 첨부하실 수 없습니다.');");
                    out.println("   parent.fnFileUploadError();");
                    out.println("</script>");
                    return;
                }

                //파일용량 제한
                if (nFileSize <= 0)
                {
                    uploadedFile.delete();

                    out.println("<script language='javascript'>");
                    out.println("   alert('0 MByte 파일은 첨부하실 수 없습니다.');");  
                    out.println("   parent.fnFileUploadError();");
                    out.println("</script>");
                    return;
                }
            }
        }

    } catch (SizeLimitExceededException e) {        // 설정한 업로드 사이즈 초과시 exception 처리
        //파일용량 제한
        out.println("<Script Language='JavaScript'>");
        out.println("   alert('0 Byte 초과, "+iMegaByte+"MByte 미만의 파일만 첨부하실 수 있습니다.');");
        out.println("   parent.fnFileUploadError();");
        out.println("</script>");
        return;
    } catch (FileUploadException e) {               // 업로드시 IO등 이상 exception 처리
        out.println("<Script Language='JavaScript'>");
        out.println("   alert('업로드시 문제가 발생하였습니다.(error 01)');");
        out.println("   parent.fnFileUploadError();");
        out.println("</script>");
        return;
    } catch (Exception e) {                         // 기타 exception 처리
        out.println("<Script Language='JavaScript'>");
        out.println("   alert('업로드시 문제가 발생하였습니다.(error 02)');");
        out.println("   parent.fnFileUploadError();");
        out.println("</script>");
        System.out.println(e.toString());
        return;
    }
    response.flushBuffer();

%>

  <script language="javascript">
  <!--
  
    var attach_obj  = {};
    
    attach_obj.sUploadNm           = "<%= sUploadNm %>";
    attach_obj.sRealFileName       = "<%= sAttachName %>";
    //attach_obj.sFileExt            = "<%= sFileExt %>";
    attach_obj.sUploadTempPath     = "<%= StringUtil.replaceAll(sAttachFullPath, '\\', '/') %>";
    attach_obj.nFileSize           = "<%= nFileSize %>";
    attach_obj.sWebUploadTempPath  = "<%= StringUtil.replaceAll(sWebUploadTempPath, '\\', '/') %>";
    parent.<%=sInputParamName%>_setUploadFile(attach_obj);

  //-->
  </script>
