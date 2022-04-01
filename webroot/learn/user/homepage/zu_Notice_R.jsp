<%
//**********************************************************
//  1. 제      목: Notice
//  2. 프로그램명 : za_Notice_R.jsp
//  3. 개      요: 공지사항 상세보기
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2005. 7. 13
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
	box.put("leftmenu","4");    
    String  v_process  = box.getString("p_process");
    int     v_pageno   = box.getInt("p_pageno");
    String  v_selcomp  = box.getString("p_selcomp");

    String  v_searchtext = box.getString("p_searchtext");
    String  v_search     = box.getString("p_search");

    int v_tabseq   = box.getInt("p_tabseq");
    int v_seq      = box.getInt("p_seq");

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
	int v_popwidth	 = 0;               // 팝업가로
    int v_popheight  = 0;               // 팝업세로
    int v_popxpos    = 0;               // 팝업x위치
    int v_popypos    = 0;               // 팝업y위치
    
    String v_popup   = "";               // 팝업여부
    String v_uselist = "";               // 리스트사용
    String v_useframe= "";               // 프레임사용
    
    String v_upfile    ="";
    //String v_realfile  ="";
    
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
     
    if (v_gubun.equals("Y")) v_gubun_view = "전체";
    else if (v_gubun.equals("N")) v_gubun_view = "일반";
    else if (v_gubun.equals("P")) v_gubun_view = "팝업";
    
    if (v_uselist.equals("")) v_uselist = "N";
    if (v_useframe.equals("")) v_useframe = "N";
    
    if(v_loginyn.equals("Y")) v_loginyn = "로그인후"; 
    else if(v_loginyn.equals("N")) v_loginyn = "로그인전";
    else v_loginyn = "전체";
    
    
    if(v_useyn.equals("Y")) v_useyn = "사용";
    else if(v_useyn.equals("N")) v_useyn = "사용안함";
     
//대상회사
	int tmplen= 0;
	String compcd="'1'";
	if(!v_compcd.equals("")) {
		compcd=v_compcd.substring(0, v_compcd.length()-1);
		compcd=StringManager.replace(compcd, ",", "','");
		compcd="'"+compcd+"'";
	}
	
	///out.println("v_compcd===>>>>"+compcd);
	
	NoticeAdminBean bean = new NoticeAdminBean();
	ArrayList compList = bean.selectCompany(box, compcd);
	
%>


<!------- 상단 로고,메뉴,유저정보 파일 시작 ------------>
<%@ include file="/learn/user/include/topHome.jsp"%>
<!------- 상단 로고,메뉴,유저정보 파일 끝  ------------->




		  <!-- center start -->
		    <table width="730" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td height="20" align="center" valign="top" class="location">HOME 
                  &gt; 학습지원센터 &gt; 공지사항</td>
              </tr>
              <tr> 
                <td align="center" valign="top"> 
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                      <td><img src="/images/user/common/<%=tem_subimgpath%>/tit_notice.jpg"></td>
                    </tr>
                    <tr>
                      <td height="20"></td>
                    </tr>
                  </table>
                  <!-- qna table -->
                  
      <table width="675" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td colspan="6"><img src="/images/user/support/search_line.gif"></td>
        </tr>
        <tr> 
          <td width="94"><img src="/images/user/common/text_title.gif"></td>
          <td colspan="5" class="tbl_btit"><%=v_adtitle%></td>
        </tr>
        <tr> 
          <td colspan="6"><img src="/images/user/support/search_line.gif"></td>
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
          <td colspan="6"><img src="/images/user/support/search_line.gif"></td>
        </tr>
        <tr> 
          <td width="94" valign="top" background="/images/user/support/text_bg_head1.gif"><img src="/images/user/support/text_top_head1.gif"></td>
          <td colspan="5" rowspan="3" class="tbl_bdata"> <%		
		          		if( v_realfileVector != null ) { 
		          			for(int i = 0; i < v_realfileVector.size(); i++) {      //     첨부파일 갯수에 따라
		          			String v_realfile = (String)v_realfileVector.elementAt(i);
		          			String v_savefile = (String)v_savefileVector.elementAt(i);  
		          			String v_fileseq = (String)v_fileseqVector.elementAt(i);  	
                  
		          			
		          			if(v_realfile != null && !v_realfile.equals("")) {  %> <a href = '/servlet/controller.library.DownloadServlet?p_savefile=<%= v_savefile%>&p_realfile=<%= v_realfile%>'> 
            <%= v_realfile%></a> &nbsp;<br> <%		}  
		                  }
		          		}
		          %> </td>
        </tr>
        <tr>
          <td align="center" valign="top" background="/images/user/support/text_bg_head1.gif"><img src="/images/user/support/text_addfile_a1.gif"></td>
        </tr>
        <tr>
          <td valign="bottom" background="/images/user/support/text_bg_head1.gif"><img src="/images/user/support/text_bo_head1.gif" ></td>
        </tr>
        <tr> 
          <td colspan="6"><img src="/images/user/support/search_line.gif"></td>
        </tr>
        <tr> 
          <td colspan="6" class="tbl_contents"> <%=v_adcontent%> </td>
        </tr>
        <tr bgcolor="B8D5E5"> 
          <td height="5" colspan="6"><img src="/images/user/common/com_tb_bo.gif"></td>
        </tr>
        <tr> 
          <td height="10" colspan="6"></td>
        </tr>
      </table>
                  <table width="675" border="0" cellspacing="0" cellpadding="0">
                    <tr> 
                      <td width="418" align="right">&nbsp; </td>
                      <td width="257" align="right"> <a href="javascript:history.back()"><img src="/images/user/button/btn_list.gif" border="0"></a></td>
                    </tr>
                  </table>
                  
                </td>
              </tr>
              <tr>
                <td align="center" valign="top">&nbsp;</td>
              </tr>
              <tr> 
                <td width="470" height="2" align="center" valign="top" bgcolor="#999999"></td>
              </tr>
            </table>

<!---------- copyright start ------------------->
<%@ include file="/learn/user/include/bottom.jsp"%>
<!---------- copyright end  -------------------->            