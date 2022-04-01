<%
/**
 * file name : za_Suryo_E.jsp
 * date      : 2005/2/22
 * programmer: S.W.Kang
 * function  : 수료발급대장 엑셀파일출력
 */
%>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.scorm.*" %>
<%@ page import = "com.credu.goyong.*" %>
<%@ page language="java" contentType="application/vnd.ms-excel;charset=euc-kr" %>
 
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String  v_userid    = box.getSession("userid");
    String  s_gadmin    = box.getSession("gadmin");

    String  ss_grcode      = box.getString("s_grcode");           //±³?°±×·?
    String  ss_gyear       = box.getString("s_gyear");            //³???
    String  ss_grseq       = box.getString("s_grseq");            //±³?°?÷¼?
    String  ss_upperclass  = box.getString("s_upperclass");       //°??¤´?ºÐ·?
    String  ss_middleclass = box.getString("s_middleclass");      //°??¤?ßºÐ·?
    String  ss_lowerclass  = box.getString("s_lowerclass");       //°??¤¼?ºÐ·?
    String  ss_subjcourse  = box.getString("s_subjcourse");       //°??¤&??½º
    String  ss_subjseq     = box.getString("s_subjseq");          //°??¤ ?÷¼?


	String p_searchStart = box.getString("p_searchStart"); 
	if(!p_searchStart.equals("")) p_searchStart=StringManager.replace(p_searchStart,"-","");
	String p_searchEnd = box.getString("p_searchEnd"); 	
	if(!p_searchEnd.equals("")) p_searchEnd=StringManager.replace(p_searchEnd,"-","");	
	
%>
<%
	response.setHeader("Content-Disposition", "inline; filename=StudentHuryunEXE1.xls");   
	response.setHeader("Content-Description", "JSP Generated Data");

//    RequestBox box = null;
//    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }
    
    String  v_action    = box.getString("p_action");
	String  v_subj      =box.getString("p_subj");
	ArrayList list = null;
	int listsize = 0;
	    list = (ArrayList)request.getAttribute("StudentList");
    	listsize = list.size();

		//out.println("11111====>"+listsize);
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">
  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">
	   <table cellspacing="1" cellpadding="5" border="1">
          <tr>
            <td class="table_title" width="5%">No</td>
            <td class="table_title" width="20%">수료증번호</td>
            <td class="table_title" width="20%">ID</td>
            <td class="table_title" width="20%">성명</td>
            <td class="table_title" width="10%">회원구분</td>
            <td class="table_title" width="20%">주민등록번호</td>
            <td class="table_title" width="5%"><%@ include file="/learn/admin/include/za_checkboxall.jsp" %></td>
          </tr>
<%       
        String v_resno ="";
        
		for ( int i=0; i<list.size(); i++ ) {
			DataBox dbox = (DataBox)list.get(i);
			
			v_resno = dbox.getString("d_resno");
			
			if(v_resno.length()  == 13){
			  v_resno = dbox.getString("d_resno").substring(0,6) + "-*******";
			}else{
			  v_resno = v_resno +"(<font color=red>주민번호 오류</font>)";
			}
%>

          <tr class="table_02_1">
                <td class="table_02_1"><%= (i+1) %></td>
                <td class="table_02_1"><%= dbox.getString("d_serno") %></td>
                <td class="table_02_1"><%= dbox.getString("d_userid") %></td>
                <td class="table_02_1"><%= dbox.getString("d_name") %></td>
                <td class="table_02_1"><%= dbox.getString("d_membergubunnm") %></td>
                <td class="table_02_1"><%= dbox.getString("d_resno")%></td>
                <td class="table_02_1"><input type="checkbox" name="p_checks" value="<%=dbox.getString("d_userid")%>,<%=dbox.getString("d_subj")%>,<%=dbox.getString("d_year")%>,<%=dbox.getString("d_subjseq")%>"></td>
           </tr>

<%
		}
%>
		</table>
      </td>
  </tr>
</table>
<!-- 공통파일 시작-->
<%@ include file="/learn/library/oz.jsp"%>
<!-- 공통파일 끝-->

   <param name="connection.reportname" value="goyong/Suryo.ozr">
   <param name="viewer.configmode" value="html">
   <param name="viewer.isframe" value="false">
   <param name="odi.odinames" value="Suryo">

   <param name="odi.Suryo.pcount" value="5">
   <param name="odi.Suryo.args1" value="subj=<%=ss_subjcourse%>">
   <param name="odi.Suryo.args2" value="year=<%=ss_gyear%>">
   <param name="odi.Suryo.args3" value="subjseq=<%=ss_subjseq%>">
   <param name="odi.Suryo.args4" value="edustart=<%=p_searchStart%>">
   <param name="odi.Suryo.args5" value="eduend=<%=p_searchEnd%>">   
</OBJECT>
</body>
</html>