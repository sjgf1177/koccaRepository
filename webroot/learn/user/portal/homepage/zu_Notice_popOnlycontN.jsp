<%
//**********************************************************
//  1. 제      목: Notice
//  2. 프로그램명 : za_Notice_R.jsp
//  3. 개      요: 공지사항 상세보기
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2005. 8. 10
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />

<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process  = box.getString("p_process");
    int     v_pageno   = box.getInt("p_pageno");
    String  v_selcomp  = box.getString("p_selcomp");

    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");
    String  v_popUpPreview = box.getString("p_popUpPreview");
    
    String ss_userid =  box.getSession("userid");

    int v_tabseq   = box.getInt("p_tabseq");
    int     v_seq          = box.getInt("p_seq");

    int    v_cnt       = 0;            // 조회수
    String v_gubun     = "";           // 전체구분
    String v_startdate = "";           // 팝업공지시작일
    String v_enddate   = "";           // 팝업공지종료일
    String v_adtitle   = "";           // 제목
    String v_adcontent = "";           // 내용
    String v_addate    = "";           // 등록일
    String v_adname    = "";           // 등록자

    String v_gubun_view = "";
    String v_loginyn    = "";			// 로그인유무
    String v_useyn   	= "";			// 사용유무
    String v_compcd	    = "";           // 대상회사
	int v_popwidth	 = 0;               // 팝업가로
    int v_popheight  = 0;               // 팝업세로
    int v_popxpos    = 0;               // 팝업x위치
    int v_popypos    = 0;               // 팝업y위치
    
    String v_popup   = "";               // 팝업여부
    String v_uselist = "";               // 리스트사용
    String v_useframe= "";               // 프레임사용
    //String v_realfile = "";
    String v_upfile = "";
    
    Vector v_realfileVector = null;
    Vector v_savefileVector = null;
    Vector v_fileseqVector  = null;
    

    //NoticeData data = (NoticeData)request.getAttribute("selectNotice");
    
    DataBox dbox = (DataBox)request.getAttribute("selectNotice");
    
    if (dbox != null) {
        v_gubun     = dbox.getString("d_gubun");            
        v_startdate = dbox.getString("d_startdate"); 
        v_enddate   = dbox.getString("d_enddate");   
        v_adtitle   = dbox.getString("d_adtitle");  
        v_adcontent = dbox.getString("d_adcontent");
        v_addate    = dbox.getString("d_addate");   
        v_adname    = dbox.getString("d_adname");   
        v_cnt       = dbox.getInt("d_cnt");      
		v_loginyn   = dbox.getString("d_loginyn");   
		v_useyn   	= dbox.getString("d_useyn");     
		v_compcd	= dbox.getString("d_compcd");    
		v_popwidth	= dbox.getInt("d_popwidth");     
		v_popheight = dbox.getInt("d_popheight");    
		v_popxpos   = dbox.getInt("d_popxpos");      
		v_popypos   = dbox.getInt("d_popypos");      
		v_popup     = dbox.getString("d_popup");     
		v_upfile    = dbox.getString("d_upfile");    
		//v_realfile  = dbox.getString("d_realfile");  
		v_uselist   = dbox.getString("d_uselist");   
		v_useframe  = dbox.getString("d_useframe");  
		
        v_addate     = FormatDate.getFormatDate(v_addate, "yyyy/MM/dd");
        //v_adcontent  = StringManager.replace(data.getAdcontent(),"\n","<br>");
        
        v_realfileVector    = (Vector)dbox.getObject("d_realfile");
        v_savefileVector    = (Vector)dbox.getObject("d_savefile");
        v_fileseqVector  = (Vector)dbox.getObject("d_fileseq");
     }  
     
%>
<html>
<head>
<title>공지사항</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link href="/css/user_style1.css" rel="stylesheet" type="text/css">
<link href="/css/user_style_study.css" rel="stylesheet" type="text/css">
<script language="javascript">
function notice_setCookie( name, value, expiredays )
    {
        var todayDate = new Date();
        todayDate.setDate( todayDate.getDate() + expiredays );
        document.cookie = name + "=" + escape( value ) + "; path=/; expires=" + todayDate.toGMTString() + ";"
     }


function notice_closeWin() 
{ 
        notice_setCookie( "<%=v_popUpPreview%>", "done" , 1); // 1=하룻동안 공지창 열지 않음
        self.close(); 
}

function winBlur()
	{
		 //alert('111');
		 self.focus();
	}
    <%if(ss_userid.equals("") || ss_userid == null){%>
     setTimeout("winBlur()", 2000); 
    <%}%>

</script>
</head>
<body leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >
<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
  <tr height="50">
    <td valign="top">
	<br>  
    <!----------------- 타이틀 시작 ----------------->
      <table cellspacing="0" cellpadding="0" class="open_table_out" align="center">
        <tr> 
          <td><img src="/images/user/common/tit_notice.gif" alt="공지사항"  width="200" height="37"></td>
          <td background="/images/user/study/gongi_title_bg.gif" width=100%></td>
          <td><img src="/images/user/study/gongi_tail.gif"></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
    <td align="center" valign="top">
    <!-- qna table -->
      <table width="640" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td colspan="6"><img src="/images/user/common/search_line-1.gif"></td>
        </tr>
        <tr> 
          <td width="94"><img src="/images/user/common/text_title.gif"></td>
          <td colspan="5" class="tbl_btit"><%=v_adtitle%></td>
        </tr>
        <tr> 
          <td colspan="6"><img src="/images/user/common/search_line-1.gif"></td>
        </tr>
        <tr> 
          <td><img src="/images/user/common/text_pe.gif"></td>
          <td width="149" class="tbl_gleft"><%=v_adname%></td>
          <td width="95"><img src="/images/user/common/text_day.gif"></td>
          <td width="93" class="tbl_gleft"><%=v_addate%> </td>
          <td width="95"><img src="/images/user/common/text_view.gif"></td>
          <td width="149" class="tbl_gleft"><%=v_cnt%></td>
        </tr>
        <tr> 
          <td colspan="6"><img src="/images/user/common/search_line-1.gif"></td>
        </tr>
        <tr> 
          <td>
            <table width="94" height="100%" border="0" cellpadding="0" cellspacing="0" background="/images/user/support/text_bg_head1.gif">
              <tr> 
                <td height="9"  valign="top"><img src="/images/user/support/text_top_head1.gif"></td>
              </tr>
              <tr> 
                <td align="center"><img src="/images/user/support/text_addfile_a1.gif"></td>
              </tr>
              <tr> 
                <td height="8" valign="bottom"><img src="/images/user/support/text_bo_head1.gif" ></td>
              </tr>
            </table>
          </td>
          <td colspan="5" class="tbl_bdata">
          <%		
      	    if( v_realfileVector != null ) { 
      		for(int i = 0; i < v_realfileVector.size(); i++) {      //     첨부파일 갯수에 따라
      		  String v_realfile = (String)v_realfileVector.elementAt(i);
      		  String v_savefile = (String)v_savefileVector.elementAt(i);  
      		  String v_fileseq = (String)v_fileseqVector.elementAt(i);  	
      
      			
      			if(v_realfile != null && !v_realfile.equals("")) {  %>
      				<a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'>
                      <%= v_realfile%></a> &nbsp;
      	<%		}  
              }
      		}
      %>
          </td>
        </tr>
        <tr> 
          <td colspan="6"><img src="/images/user/common/search_line-1.gif"></td>
        </tr>
        <tr> 
          <td colspan="6" class="tbl_contents">
          <%=v_adcontent%>
          </td>
        </tr>
        <tr > 
          <td height="5" colspan="6"><img src="/images/user/common/com_tb_bo-1.gif"></td>
        </tr>
        <tr> 
          <td height="10" colspan="6"></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr>
  	 <td valign="bottom">
      <table width="640" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td width="487" align="right">
		  <!-- 창그만보기 -->
		  	<table width="290" border="0" cellspacing="0" cellpadding="0"	>
              <tr>
                <td valign="bottom">
                    <input type="checkbox" name="checkbox" onclick="javascript:notice_closeWin()">오늘하루 이 창을 그만 봅니다.</td>
              </tr>
            </table> </td>
          <td width="153" align="right"> <a href="javascript:self.close()"><img src="/images/user/button/btn_close.gif"  alt="닫기"  border="0"></a></td>
        </tr>
      </table>
    </td>
  </tr>
  <tr height="10">
	<td>&nbsp;</td>
  </tr>
</table>

</body>
</html>

