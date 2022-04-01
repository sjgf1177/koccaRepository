<%
//**********************************************************
//  1. 제      목: 교재판매관리
//  2. 프로그램명 : za_Book_L.jsp
//  3. 개      요: 교재판매관리
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성:
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.book.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box    = (RequestBox)request.getAttribute("requestbox");
    String  v_process = box.getString("p_process");

    String ss_startday  = box.getStringDefault("s_startday", FormatDate.getDateAdd("yyyyMMdd", "month", -1));
    String ss_endday    = box.getStringDefault("s_endday",   FormatDate.getDate("yyyyMMdd"));
    String ss_paystat   = box.getString("s_paystat");
    String ss_bookcode  = box.getString("s_bookcode");
    String ss_action    = box.getString("s_action");
    String v_startday   = FormatDate.getFormatDate(ss_startday,"yyyy-MM-dd");
    String v_endday     = FormatDate.getFormatDate(ss_endday,"yyyy-MM-dd");

    int    i = 0;
    
    String v_tid				= "";     
	String v_resultcode      	= "";
	String v_paymethod       	= "";
	String v_paymethodnm     	= "";
	String v_mid             	= "";
	String v_userid          	= "";
	String v_usernm          	= "";
	String v_goodname          	= "";
	int    v_price           	= 0;
	String v_buyername       	= "";
	String v_buyertel        	= "";
	String v_buyeremail      	= "";
	String v_authcode        	= "";
	String v_pgauthdate      	= "";
	String v_pgauthtime      	= "";
	String v_luserid         	= "";
	String v_ldate           	= "";
	String v_gubun           	= "";
	String v_inputname       	= "";
	String v_inputdate       	= "";
	String v_receive         	= "";
	String v_phone           	= "";
	String v_post1           	= "";
	String v_post2           	= "";
	String v_addr1           	= "";
	String v_addr2	        	= "";
	
	String 	v_bookcode			= "";
	String 	v_bookname          = "";
	int 	v_bookprice         = 0;
	int 	v_bookunit          = 0;
	int 	v_bookamount        = 0;

    ArrayList list = null;
    if (ss_action.equals("go")) {    //go button 선택시만 list 출력
        list = (ArrayList)request.getAttribute("selectBookSellListNew");
    }
%>

<link rel="stylesheet" type="text/CSS" href="/css/admin_style.css">
<link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type="text/javascript" src="/script/ui.core.js"></script>
<script type="text/javascript" src="/script/effects.core.js"></script>
<script type="text/javascript" src="/script/effects.blind.js"></script>
<script type="text/javascript" src="/script/effects.drop.js"></script>
<script type="text/javascript" src="/script/effects.explode.js"></script>
<script type="text/javascript" src="/script/ui.datepicker.js"></script>
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
$(document).ready(function(){
	$("#p_startday").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
	$("#p_endday").datepicker({defaultDate: new Date(),showOn: "both",showAnim: "blind",showOptions: {direction: 'horizontal'},duration: 200})
});
<!--
    //검색
    function whenSelection(p_action) {
      document.form1.s_action.value = p_action;
      document.form1.s_startday.value = make_date(document.form1.p_startday.value);
      document.form1.s_endday.value   = make_date(document.form1.p_endday.value);

      document.form1.action='/servlet/controller.book.BookSellAdminServlet';
      document.form1.p_process.value = 'listPageNew';
      document.form1.submit();
    }

    function update(){

      document.form1.action='/servlet/controller.book.BookSellAdminServlet';
      document.form1.p_process.value = 'update';
      document.form1.submit();
    }


    // 회원정보
    function whenMemberInfo(userid) {
        window.self.name = "ProposeList";
        open_window("openMember","","100","100","600","260");
        document.form1.target = "openMember";
        document.form1.action='/servlet/controller.library.SearchServlet?p_userid='+userid;
        document.form1.p_process.value = 'memberInfo';
        document.form1.submit();
        document.form1.target = window.self.name;
    }
//-->
</SCRIPT>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0">

<!------------------------------------- form 시작 ------------------------------------->
<form name = "form1" method = "post">
    <input type="hidden" name="p_process"  value="<%=v_process%>">
    <input type="hidden" name="s_action"   value="<%=ss_action%>">
    <input type="hidden" name="s_startday" value="<%=ss_startday%>">
    <input type="hidden" name="s_endday"   value="<%=ss_endday%>">

    <input type="hidden" name="p_chknum" >
    <input type="hidden" name="p_chkvalue" >


  <table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top">
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class="page_title">
        <tr>
          <td><img src="/images/admin/book/tit_booksell.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
        <!----------------- title 끝 ----------------->
        <br>

      <!----------------- form 시작 ----------------->
      <table cellspacing="0" cellpadding="1" class="form_table_out">
        <tr>
          <td bgcolor="#C6C6C6" align="center">
            <table cellspacing="0" cellpadding="0" class="form_table_bg" >
              <tr>
                <td height="7"></td>
              </tr>
              <tr>
                <td align="center">
                  <table border="0" cellspacing="0" cellpadding="0" width="99%" class="form_table">
                    <tr>
                      <td width="90%" colspan="2" align="left" valign="middle">&nbsp;&nbsp;
                         <!--<%=BookBean.getBook(box, true, true)%>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-->
                         신청일&nbsp;<input name="p_startday" id="p_startday" value="<%=v_startday%>" type="text" class="datepicker_input1" size="10">&nbsp;
                          ~&nbsp;<input name="p_endday" id="p_endday" value="<%=v_endday%>" type="text" class="datepicker_input1" size="10">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                        <!-- 상태&nbsp;<%=CodeConfigBean.getCodeGubunSelect ("0074", "", 1, "s_paystat", ss_paystat, "onChange=\"javascript:whenSelection('change')\"", 2)%>-->
                      </td>
                      <td width="100" ><%@ include file="/learn/admin/include/za_GoButton.jsp" %></td>
                    </tr>
                    <tr>
                        <td height="5" colspan="4"></td>
                    </tr>
                  </table>
                </td>
              </tr>
              <tr>
                <td height="9"></td>
              </tr>
            </table>
          </td>
        </tr>       
      </table>
      <br>

      <!-----------------  버튼 시작 ----------------->
      <!--table cellpadding="0" cellspacing="0" class="table1">
        <tr>
          <td align="right" height="20"><a href="javascript:update()"><img src="/images/admin/button/btn_save.gif" border="0"></a></td>
        </tr>
        <tr>
          <td height="3"></td>
        </tr>
      </table-->
      <!-----------------  버튼 끝 ----------------->

        <!----------------- 과정기준조회 시작 ----------------->
        <table class="table_out" cellspacing="1" cellpadding="5">       
          <tr>
            <td colspan="10" class="table_top_line"></td>
          </tr>
          <tr>
            <td class="table_title" width="55"><b>신청인<br>(ID)</b></td>
            <td class="table_title" width="80"><b>결제명</b></td>
            <td class="table_title" width="60"><b>결제금액</b></td>
            <td class="table_title" width="60"><b>결제구분</b></td>
            <td class="table_title" width="62"><b>결제일</b></td>
            <td class="table_title" width="40"><b>결제<br>상태</b></td>
            <td class="table_title" width="75"><b>입금자/<br>입금예정일</b></td>
            <td class="table_title" width="50"><b>결제자</b></td>
            <td class="table_title" width="160"><b>수취인/<br>연락처/<br>우편번호/<br>주소</b></td>
            <td class="table_title" ><b>            			
					<table class="table_out" cellspacing="1" cellpadding="5">       
				          <tr>
				            <td class="table_02_1" width="80">도서명</td>
				            <td class="table_02_1" width="30">수량</td>
				            <td class="table_02_1" width="40">단가</td>
				            <td class="table_02_1" width="40">금액</td>
				          </tr>
				    </table>
</b></td>

          </tr>

<%  
    if (ss_action.equals("go")) {

        for (i=0; i<list.size(); i++) {
            DataBox dbox = (DataBox)list.get(i);
            
            v_tid		 	= dbox.getString("d_tid");		 
            v_resultcode    = dbox.getString("d_resultcode"); 
            v_paymethod     = dbox.getString("d_paymethod");  
            v_paymethodnm   = dbox.getString("d_paymethodnm");
            v_mid           = dbox.getString("d_mid");        
            v_userid        = dbox.getString("d_userid");     
            v_usernm        = dbox.getString("d_usernm");     
            v_price         = dbox.getInt("d_price");      
            v_goodname         = dbox.getString("d_goodname");      
            v_buyername     = dbox.getString("d_buyername");  
            v_buyertel      = dbox.getString("d_buyertel");   
            v_buyeremail    = dbox.getString("d_buyeremail"); 
            v_authcode      = dbox.getString("d_authcode");   
            v_pgauthdate    = dbox.getString("d_pgauthdate"); 
            v_pgauthtime    = dbox.getString("d_pgauthtime"); 
            v_luserid       = dbox.getString("d_luserid");    
            v_ldate         = dbox.getString("d_ldate");      
            v_gubun         = dbox.getString("d_gubun");      
            v_inputname     = dbox.getString("d_inputname");  
            v_inputdate     = dbox.getString("d_inputdate");  
            v_receive       = dbox.getString("d_receive");    
            v_phone         = dbox.getString("d_phone");      
            v_post1         = dbox.getString("d_post1");      
            v_post2         = dbox.getString("d_post2");      
            v_addr1         = dbox.getString("d_addr1");      
            v_addr2	        = dbox.getString("d_addr2");	     
%>          

            <tr class="table_02_1">
                <td class="table_02_1" ><%=v_usernm%><br><%=v_userid%></td>
	            <td class="table_02_1" ><%=v_goodname%></td>
	            <td class="table_02_1" ><%=new java.text.DecimalFormat("##,###,##0").format(v_price)%></td>
	            <td class="table_02_1" ><%=v_paymethodnm%></td>
	            <td class="table_02_1" ><%=FormatDate.getFormatDate(v_pgauthdate,"yyyy-MM-dd")%></td>
	            <td class="table_02_1" >
	            <% if (v_resultcode.equals("00")) { %>
	            	성공
	            <% } else { %>
	            	실패
	            <% } %>
	            </b></td>
	            <td class="table_02_1" ><%=v_inputname%> / <%=FormatDate.getFormatDate(v_inputdate,"yyyy-MM-dd")%></td>
	            <td class="table_02_1" ><%=v_buyername%></td>
	            <td  align=left><%=v_receive%><br><%=v_phone%><br>(<%=v_post1%>-<%=v_post2%>)<br><%=v_addr1%> <%=v_addr2%></td>
	            <td class="table_02_1" align=left>
	            <%
	            	 ArrayList list2 = (ArrayList)BookSellBean.getMybookBillList(v_tid);
	            	 
	            	 for (int k=0; k<list2.size(); k++) {
            			DataBox dbox2 = (DataBox)list2.get(k);
            			v_bookname = dbox2.getString("d_bookname"); 
            			v_bookunit = dbox2.getInt("d_unit"); 
            			v_bookprice = dbox2.getInt("d_price"); 
            			v_bookamount = dbox2.getInt("d_amount"); 
            			
            			%>
            			<table class="table_out" cellspacing="1" cellpadding="5">       
				          <tr>
				            <td  bgcolor="#FFFFFF" width="80" align=left><%=v_bookname%></td>
				            <td  bgcolor="#FFFFFF" width="30" align=right><%=new java.text.DecimalFormat("##,###,##0").format(v_bookunit)%></td>
				            <td  bgcolor="#FFFFFF" width="40" align=right><%=new java.text.DecimalFormat("##,###,##0").format(v_bookprice)%></td>
				            <td  bgcolor="#FFFFFF" width="40" align=right><%=new java.text.DecimalFormat("##,###,##0").format(v_bookamount)%></td>
				          </tr>
				        </table>
            	<%		
            		 }
	            %>
	            
	            </td>
            </tr>
<%
        }

        if (i == 0) {
%>
            <tr>
                <td align="center" bgcolor="#FFFFFF" height="25" colspan="10">신청된 내용이 없습니다.</td>
              </tr>
<%     
        }
    }
%>
        </table>
        <br>
      </td>
  </tr>
</table>
</form>

<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>