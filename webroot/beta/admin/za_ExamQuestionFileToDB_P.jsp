<%
/**
 * file name : za_ExamQuestionFileToDB_P.jsp
 * date      : 2003/09/03
 * programmer:
 * function  : 乞啊 巩力 老褒贸府
 */
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.io.*" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.sql.*" %>
<%@ page import = "jxl.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.beta.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    DBConnectionManager connMgr = null;
    PreparedStatement pstmt1 = null;
    PreparedStatement pstmt2 = null;
    ResultSet rs = null;
    String sql1 = "";
    String sql2 = "";
    int isOk = 0;
    int cnt = 0;
    int next = 0;
    int i    = 0;

    String  v_realFileName = box.getRealFileName("p_file");
    String  v_newFileName  = box.getNewFileName("p_file");
    String s_userid = box.getSession("userid");

    String  v_subjnm    = box.getString("p_subjnm");

    String  v_action     = box.getString("p_action");
    String  v_process = box.getString("p_process");

    String  v_subj      = "";
    int  v_examnum      = 0;
    String  v_lesson      = "";
    String  v_examtype      = "";
    String  v_examtext      = "";
    String  v_exptext      = "";
    String  v_levels      = "";
    int  v_selcount      = 0;

    Cell cell = null;
    Sheet sheet = null;
    Workbook workbook = null;

  //  DataBox dbox = (DataBox)request.getAttribute("ETestMasterData");

%>
<html>
<head>
<title>乞啊 巩力 - 老褒贸府</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--

    function list() {
        document.form1.action = "/servlet/controller.beta.BPExamQuestionServlet";
        document.form1.p_process.value = "ExamQuestionFileToDB";
        document.form1.submit();
    }

//-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
<!----------------- form 矫累 ----------------->
<form name="form1" method="post" >
    <input type="hidden" name="p_process"    value="">
    <input type="hidden" name="p_action"     value="<%=v_action%>">

    <input type="hidden" name="s_subj"       value="<%=v_subj%>">


<table width="770" border="0" cellspacing="0" cellpadding="0" height="580">
  <tr>
    <td align="center" valign="top">
      <!----------------- title 矫累 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/beta/tit_test.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 场 ----------------->

       <br>

        <table width="97%" border="1" cellspacing="0" cellpadding="0">
<%
        int v_count = 2;
        try {
            String v_dirKey = conf.getDir(conf.getProperty("dir.upload"), FileManager.getServletName(v_newFileName));

            workbook = Workbook.getWorkbook(new File(conf.getProperty("dir.upload." + v_dirKey), v_newFileName));

            sheet = workbook.getSheet(0);

           connMgr = new DBConnectionManager();
           connMgr.setAutoCommit(false);

            if (v_process.equals("insertFileToDB")) {

	            sql1 = "select examnum from TZ_EXAM";
	            sql1+=  " where subj = ? and examnum = ? "; 
	            pstmt1 = connMgr.prepareStatement(sql1);   
	            
	            sql2 =  "insert into TZ_EXAM(subj, examnum, lesson, examtype, examtext, exptext,";
	            sql2+=  "					 levels, selcount, luserid, ldate ) ";
	            sql2+=  " values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
	            pstmt2 = connMgr.prepareStatement(sql2);

            }

            for (i=1; i < sheet.getRows() ; i++ ) {
                v_count = i;
				cell      = sheet.getCell(0,i);      // subj
                v_subj  = cell.getContents();
                cell      = sheet.getCell(1,i);      // examnum
                v_examnum  = Integer.parseInt(cell.getContents());
                cell      = sheet.getCell(2,i);      // lesson
                v_lesson  = cell.getContents();
                cell      = sheet.getCell(3,i);      // examtype
                v_examtype  = cell.getContents();
                cell      = sheet.getCell(4,i);      // examtext
                v_examtext  = cell.getContents();
                cell      = sheet.getCell(5,i);      // exptext
                v_exptext  = cell.getContents();
                cell      = sheet.getCell(6,i);      // levels
                v_levels  = cell.getContents();
                cell      = sheet.getCell(7,i);      // selcount
                v_selcount  = Integer.parseInt(cell.getContents());

                if (v_process.equals("previewFileToDB")) { %>
			        <tr>
			          <td align="left">
			            <%= (i-1) + " 锅掳  : " + v_subj  + ", " + v_examnum  + ", " + v_lesson  + ", " + v_examtype  + ", " + v_examtext  + ", " + v_exptext  + ", " + v_levels  + ", " + v_selcount%>
			          </td>
			        </tr>
			<%  }

                if (v_process.equals("insertFileToDB")) {

                	pstmt1.setString( 1, v_subj);   
                	pstmt1.setInt( 2, v_examnum);   				
                	
                    try {
                        rs = pstmt1.executeQuery();

                        if(!rs.next()) {     //     苞芭俊 殿废等 examnum 甫 犬牢窍绊 绝阑 版快俊父 殿废          
	                        pstmt2.setString( 1, v_subj);      
	                        pstmt2.setInt( 2, v_examnum);       
	                        pstmt2.setString( 3, v_lesson);       
	                        pstmt2.setString( 4, v_examtype);
	                        pstmt2.setString( 5, v_examtext);    
	                        pstmt2.setString( 6, v_exptext);				
	                        pstmt2.setString( 7, v_levels);
	                        pstmt2.setInt( 8, v_selcount);
	                        pstmt2.setString( 9, s_userid);
	                        pstmt2.setString( 10, FormatDate.getDate("yyyyMMddHHmmss"));
                            
                            isOk = pstmt2.executeUpdate();

                            cnt += isOk;
                            next++;


		                if(isOk > 0) { %>
					          <tr> 
					            <td align="center" width=30><%= (i-1) %></td>
					            <td style="padding-left:5px;padding-top:5px"> <%=v_examtext%><br>
					            	 <%=v_exptext%>
					            <%// " 锅掳  : " + v_subj  + ", " + v_examnum  + ", " + v_lesson  + ", " + v_examtype  + ", " +   + ", " + v_exptext  + ", " + v_levels  + ", " + v_selcount %>
					            </td>
					            <td width=50 align="center"><font color='blue'>己傍</font></td>
					          </tr>
					<% } else { %>
					          <tr> 
					            <td align="center" width=30><%= (i-1) %></td>
					            <td style="padding-left:5px;padding-top:5px"> <%=v_examtext%><br>
					            	 <%=v_exptext%>
					            <%// " 锅掳  : " + v_subj  + ", " + v_examnum  + ", " + v_lesson  + ", " + v_examtype  + ", " +   + ", " + v_exptext  + ", " + v_levels  + ", " + v_selcount %>
					            </td>
					            <td width=50 align="center" ><font color='red'>俊矾</font></td>
					          </tr>
					          <!--tr> 
					            <td align="left" >俊矾 皋技瘤 : <%//=e.getMessage()%></td>
					          </tr-->
					<% }                 
			} else { %>
				  <tr> 
		            <td align="center" width=30><%= (i-1) %></td>
		            <td style="padding-left:5px;padding-top:5px"> <%=v_examtext%><br>
		            	 <%=v_exptext%>
		            <%// " 锅掳  : " + v_subj  + ", " + v_examnum  + ", " + v_lesson  + ", " + v_examtype  + ", " +   + ", " + v_exptext  + ", " + v_levels  + ", " + v_selcount %>
		            </td>
		            <td width=50 align="center"><font color='blue'>扁殿废</font></td>
		          </tr>
					          
          <!--tr> 
            <td align="left"> 
            <%= (i-1) + " 锅掳  : " + v_subj  + ", " + v_examnum  + ", " + v_lesson  + ", " + v_examtype  + ", " + v_examtext  + ", " + v_exptext  + ", " + v_levels  + ", " + v_selcount%>绰 捞固 殿废 登绢 乐嚼聪促.
            </td>
          </tr-->
<%  
						}
                    }catch(Exception e) {} 
                    finally { if (rs != null) { try { rs.close(); } catch (Exception e) {} }}           

                }
            }

            if(next == cnt) connMgr.commit();
            else connMgr.rollback();
%>

        </table>
        <!----------------- 诀肺靛 磊丰 场 ----------------->

<%

        } catch (Exception e) {
            connMgr.rollback();
%>
        <br>
        <!----------------- 俊矾皋技瘤 矫累 ----------------->
        <table class="table_out" cellpadding="0" cellspacing="0">
          <tr> 
            <td align="left" ><font color='red'><%=v_count-1%>锅掳俊辑 俊矾涝聪促.</font></td>
          </tr>
          <tr> 
            <td align="left" >俊矾 皋技瘤 : <%=e.getMessage()%></td>
          </tr>
          
        </table>
        <!----------------- 俊矾皋技瘤 场 ----------------->
<%
        } finally {
            //梅何颇老昏力
            FileManager.deleteFile(conf.getProperty("dir.upload.exam")+v_realFileName);
            if(pstmt1 != null) { try { pstmt1.close(); } catch (Exception e1) {} }
            if(pstmt2 != null) { try { pstmt2.close(); } catch (Exception e1) {} }
            if(connMgr != null) { try { connMgr.setAutoCommit(true); }catch (Exception e10) {} }
            if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
        }
%>
        <br>
        <!----------------- 殿废, 固府焊扁 滚瓢 矫累 ----------------->
        <table width="97%" border="0" cellpadding="0" cellspacing="0">
          <tr>
            <td align="center" ><a href="javascript:list()"><img src="/images/admin/button/btn_list.gif" border="0"></a></td>
          </tr>
        </table>
        <!----------------- 殿废, 固府焊扁 滚瓢 场 ----------------->

        <br>
        <br>
        <!----------------- 林狼荤亲 矫累 ----------------->
        <table width="97%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><b><font size="3">(林狼荤亲)</font></b></td>
          </tr>
          <tr>
            <td height="8"></td>
          </tr>
        <tr>
          <td height="20" class=dir_txt >1. 竣伎颇老 历厘矫 竣伎 屈侥栏肺 历厘窍咯 荤侩且 巴</td>
        </tr>
        <tr>
          <td height="20" class=dir_txt >2. 竣伎颇老 累己矫 <font color="#A661D7">[<b>巩力 颇老</b>]苞 [<b>巩力焊扁 颇老</b>]</font>阑 唱穿绢 父甸 巴.</td>
        </tr>
        <tr>
          <td height="20" class=dir_txt >3. <font color="#E60873">基敲 剧侥 汽阑 函版且矫 摹疙利牢 坷幅啊 惯积钦聪促.</font></td>
        </tr>
        <tr>
          <td height="20" class=dir_txt >4. 基敲 颇老阑 曼绊且 巴. ( <a href = '/servlet/controller.library.DownloadServlet?p_savefile=Sample_ExamQuestion.xls&p_realfile=Sample_ExamQuestion.xls'><b>巩力 基敲颇老</b></a>)</td>
        </tr>
        </table>
        <!----------------- 林狼荤亲 场 ----------------->

      <br>
    </td>
  </tr>
</table>
</form>
<!----------------- form 场 ----------------->
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>
