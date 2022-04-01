<%
//**********************************************************
//  1. 제      목: 프리메일 작성
//  2. 프로그램명 : freeMailForm.jsp
//  3. 개      요: 프리메일 작성
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 강성욱 2004. 12. 13
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.homepage.*" %>
<%@ page import = "com.credu.library.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

    String v_grcode  = box.getString("s_grcode");
    Vector v_checks  = box.getVector("p_checks");
    
    String[] arrChecks;

    String v_schecks = "";
    String v_subj    = "";
    String v_year    = "";
    String v_subjseq = "";
    
    String v_touch = box.getStringDefault("p_touch","00");
    String v_msubjnm  = box.getString("p_msubjnm");
    String v_mseqgrnm = box.getString("p_mseqgrnm");
    String v_msubj    = box.getString("p_msubj");
    String v_myear    = box.getString("p_myear");
    String v_msubjseq = box.getString("p_msubjseq");
    String v_aid      = box.getString("p_aid");
    
/*  
                for(int i=0;i<v_checks.size();i++) {
                    v_schecks = (String)v_checks.elementAt(i);
                    out.println(v_schecks + "<br>");
                    if(v_schecks.indexOf(',') > 0){
                      //out.println(StringManager.substring(v_schecks, 0, v_schecks.indexOf(',')) );
                    }
                }
                


       Enumeration em      = v_checks.elements();
       String v_userid     = "";
       
       
       while(em.hasMoreElements()){
         v_userid   = (String)em.nextElement();
         out.println(v_userid + "<br>");
       }
*/
    String v_server   = conf.getProperty("kocca.url.value");
    String v_tem_url  = conf.getProperty("url.namo.template");
    String v_tem_name = conf.getProperty("name.namo.template.ini");
    String v_tem_url_name = v_server + v_tem_url + v_tem_name; 

    String v_sendhtml = "freeMailForm.html";
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<SCRIPT LANGUAGE="JavaScript">
<!--
    function insert() {
        if (blankCheck(document.form1.p_title.value)) {
            alert("제목을 입력하세요!");
            document.form1.p_title.focus();
            return;
        }
        if (realsize(document.form1.p_title.value) > 200) {
            alert("제목은 한글기준 100자를 초과하지 못합니다.");
            document.form1.p_title.focus();
            return;
        }
/*
        document.forms[0].p_content.editorApplet = DHTMLEdit.tbContentElement;
        document.forms[0].p_content.value = DHTMLEdit.getDocumentHTML();
        document.forms[0].InitHTML.value="";
        document.forms[0].addBody.value="";
*/
        document.form1.p_content.value = document.form1.Wec.BodyValue;
        document.form1.p_isMailing.value = "1";
        document.form1.action = "/servlet/controller.library.FreeMailServlet";             //credu.com.library.FreeMailBean.sendFreeMail()
        document.form1.p_process.value = "insert";
        document.form1.submit();
    }

//-->
</SCRIPT>
</head>

<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" >
<form name = "form1" method = "post">
<%
    for(int i=0;i<v_checks.size();i++) {              

      v_schecks = (String)v_checks.elementAt(i);

      //2010.02.03 userid만 넘기는 로직을 subj,year,subjseq 도 넘기도록 수정
      //if(v_schecks.indexOf(',') > 0){
      //  v_schecks = StringManager.substring(v_schecks, 0, v_schecks.indexOf(','));
      //}
      
      if(v_schecks.indexOf(',') > 0){
		arrChecks = v_schecks.split(",");
		
		if(arrChecks.length > 3) {
			v_schecks = arrChecks[0];
			v_subj    = arrChecks[1];
			v_year    = arrChecks[2];
			v_subjseq = arrChecks[3];
		} else {
			v_schecks = StringManager.substring(v_schecks, 0, v_schecks.indexOf(','));
		}
      }

%>
  <input type='hidden' name = 'p_checks'  value='<%=v_schecks%>'>
  <!-- 2010.02.03 p_subj, p_year, p_subjseq 추가 -->
  <input type='hidden' name = 'p_subj'    value='<%=v_subj%>'>
  <input type='hidden' name = 'p_year'    value='<%=v_year%>'>
  <input type='hidden' name = 'p_subjseq' value='<%=v_subjseq%>'>
<% }                                                  %>
  <input type='hidden' name = 'p_grcode'    value='<%=v_grcode%>'>
  <input type='hidden' name = 'p_process'   value=''>
  <input type='hidden' name = 'p_isMailing' value='1'>
  <input type='hidden' name = 'p_content'   value=''>
  <input type="hidden" name="p_offlinemail" value='<%= box.get("p_offlinemail") %>'>

  <!-- tz_humantouc insert parameter start-->
  <input type='hidden' name="p_touch" value='<%=v_touch%>'>
  <input type="hidden" name="p_msubjnm" value='<%=v_msubjnm%>'>
  <input type="hidden" name="p_mseqgrnm" value='<%=v_mseqgrnm%>'>
  <input type="hidden" name="p_msubj" value='<%=v_msubj%>'>
  <input type="hidden" name="p_myear" value='<%=v_myear%>'>
  <input type="hidden" name="p_msubjseq" value='<%=v_msubjseq%>'>
  <input type="hidden" name="p_aid" value='<%=v_aid%>'><%//2010.02.04 v_aid : eCare 번호 %>
  <!-- tz_humantouc insert parameter end -->
  
  
  
  
<table width="800" border="0" cellpadding="0" cellspacing="10" bgcolor="#f7f7f7">
  <tr> 
    <td> 
        <table width="100%" border="0" cellpadding="0" cellspacing="1" bgcolor="#cccccc">
          <tr> 
            <td align="center" bgcolor="#FFFFFF"> 

              <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/library/L_title02.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
           <!----------------- title 끝 ----------------->
              <br>
              <!----------------- 메일작성 시작 ----------------->
              <table cellspacing="1" cellpadding="5" class="open_table_out">
                <tr> 
                  <td colspan="2" class="table_top_line"></td>
                </tr>
                <tr> 
                  <td width="15%" class="table_title">구분</td>
                  <td width="85%" class="table_02_2">
                  <SELECT name=s_mcode> 
					 <option value=1>기술</option> 
					 <option value=2>학습</option> 
					 <option value=3>수료/미수료</option> 
					 <option value=4>교재</option> 
					 <option value=5>미수료통보</option> 
					 <option value=6>기타</option> 
				</SELECT> </td>
                </tr>
                <tr> 
                  <td width="15%" class="table_title">변수</td>
                  <td width="85%" class="table_02_2"><b>이름 : v_name, 과정명 : v_subjnm</b></td>
                </tr>
                <tr> 
                  <td width="15%" class="table_title">제목</td>
                  <td width="85%" class="table_02_2"> 
                    <input type="text" class="input" size="70" name="p_title">
                  </td>
                </tr>
                <tr> 
                  <td width="15%" class="table_title">내용</td>
                  <td class="table_02_2"> 
                <!-- 나모 액티브 스퀘어  시작 -->
                <script language="javascript" src="/script/user_patch.js"></script>
                <script language="javascript" for="Wec" event="OnInitCompleted()">namoActiveInitCompleted("p_content");document.form1.Wec.TemplateIniURL = "<%=v_tem_url_name%>";</script>
                <script language="javascript">object_namoActivepatch('650','450');</script>
                <!-- 나모 액티브 스퀘어  종료 -->
                  </td>
                </tr>
              </table>
              <!----------------- 메일작성 끝 ----------------->


            <!----------------- 전송, 닫기 버튼 시작 ----------------->
              <table cellspacing="0" cellpadding="0" >
              <tr>
              <td height=10></td>
              <td height=10></td>
              <td height=10></td>
              </tr>
                <tr> 
                  <td><a href="javascript:insert()"><img src="/images/admin/button/btn_send.gif" border="0"></a></td>
                  <td width=8></td>
                  <td><a href="javascript:self.close()"><img src="/images/admin/button/btn_close.gif" border="0"></a></td>
                </tr>
              </table>
            <!----------------- 전송, 닫기 버튼 끝 ----------------->

            <br>

          </td>
        </tr>
      </table>
    </td>
  </tr>
</table>


                                <!-- IE 6.0 이하에서 필요-->
    <SCRIPT LANGUAGE="VBScript">
    <!--
        Function Load_Content()
                document.form1.Wec.BodyValue = document.form1.p_content.value
                document.form1.Wec.focus
        End Function

        Sub wec_OnInitCompleted
            call Load_Content()
        End Sub

        Function Move_Content()
                document.form1.p_content.value = document.form1.Wec.BodyValue
        End Function
    -->
    </script>

</form>
</body>
</html>
