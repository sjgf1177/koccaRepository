<%
/**
 * file name : za_ExamPaper_U2.jsp
 * date      : 2003/09/03
 * programmer:
 * function  : 평가문제지 수정
 */
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.exam.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

	String v_end = box.getString("p_end");
	String v_onload = "";
	if(v_end.equals("0")) { v_onload = "onload='javascript:onload();'"; }

	String v_reloadlist = box.getString("p_reloadlist");

    String  ss_upperclass  = box.getStringDefault("s_upperclass", "ALL");
    String  ss_middleclass = box.getStringDefault("s_middleclass","ALL");
    String  ss_lowerclass  = box.getStringDefault("s_lowerclass", "ALL");
    String  ss_subjcourse  = box.getStringDefault("s_subjcourse","ALL");    //과정&코스

	String  v_subj       = box.getString("p_subj");
	String  v_gyear       = box.getString("p_gyear");
	String  v_subjseq       = box.getString("p_subjseq");
	String  v_subjseqgr     = box.getString("p_subjseqgr");
    String v_lesson    = box.getString("p_lesson");
	String  v_examtype       = box.getString("p_examtype");
	int  v_papernum       = box.getInt("p_papernum");

	String  v_examtime       = box.getString("p_examtime");
	String  v_isopenanswer       = box.getString("p_isopenanswer");
	String  v_isopenexp       = box.getString("p_isopenexp");
	int  v_retrycnt       = box.getInt("p_retrycnt");
	int  v_progress       = box.getInt("p_progress");

    String v_lessonstart    = box.getString("p_lessonstart");
    String v_lessonend    = box.getString("p_lessonend");
	
	String v_startdt    = box.getString("p_startdt");
    String v_enddt    = box.getString("p_enddt");
    
	boolean v_isResult    = (new Boolean(box.getString("p_isResult"))).booleanValue() ;

	int s_lesson = 0;
	int s_levels = 0;
	int s_type = 0;

	DataBox dbox0 = (DataBox)request.getAttribute("ExamPaperData");
%>
<html>
<head>
<title>평가마스터수정</title>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<script language = "javascript" src = "/script/cresys_lib.js"></script>
<script language = "javascript" src = "/script/jquery-1.3.2.min.js"></script>
<script language = "VBScript" src = "/script/cresys_lib.vbs"></script>
<script language="JavaScript">
<!--
<% if (v_reloadlist.equals("true")) { %>
opener.ReloadPage('go');
<% } %>
function onload() {
  window.self.close();
}
-->
</script>
</head>
<body bgcolor="#FFFFFF" text="#000000" topmargin="0" leftmargin="0" <%=v_onload%>>
<form name="form1" method="post" action="/servlet/controller.exam.ExamPaperServlet">
    <input type="hidden" name="p_process"     value="">
    <input type="hidden" name="p_action"      value="">
    <input type="hidden" name="s_upperclass"  value="<%=ss_upperclass%>">
    <input type="hidden" name="s_middleclass" value="<%=ss_middleclass%>">
    <input type="hidden" name="s_lowerclass"  value="<%=ss_lowerclass%>">
    <input type="hidden" name="s_subjcourse"  value="<%=ss_subjcourse%>">
    <input type="hidden" name="p_subj"        value="<%=v_subj%>">
    <input type="hidden" name="p_gyear"      value="<%=v_gyear%>">
    <input type="hidden" name="p_subjseq"      value="<%=v_subjseq%>">
	<input type="hidden" name="p_subjseqgr"      value="<%=v_subjseqgr%>">
    <input type="hidden" name="p_lesson"      value="<%=v_lesson%>">
    <input type="hidden" name="p_examtype"       value="<%=v_examtype%>">
    <input type="hidden" name="p_papernum"       value="<%=v_papernum%>">
    <input type="hidden" name="p_lessonstart"        value="<%=v_lessonstart%>">
    <input type="hidden" name="p_lessonend"        value="<%=v_lessonend%>">
	<input type="hidden" name="p_examtime"        value="<%=v_examtime%>">
    <input type="hidden" name="p_totalscore"        value="100">
    <input type="hidden" name="p_level1text"        value="">
    <input type="hidden" name="p_level2text"        value="">
    <input type="hidden" name="p_level3text"        value="">
    <input type="hidden" name="p_cntlevel1"        value="">
    <input type="hidden" name="p_cntlevel2"        value="">
    <input type="hidden" name="p_cntlevel3"        value="">
	<input type="hidden" name="p_isopenanswer"        value="<%=v_isopenanswer%>">
    <input type="hidden" name="p_isopenexp"        value="<%=v_isopenexp%>">
    <input type="hidden" name="p_retrycnt"        value="<%=v_retrycnt%>">
    <input type="hidden" name="p_progress"        value="<%=v_progress%>">
    <input type="hidden" name="p_reloadlist" value="">
    <input type="hidden" name="p_startdt" value="<%=v_startdt%>">
    <input type="hidden" name="p_enddt" value="<%=v_enddt%>">



<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
    <td align="center" valign="top"> 
      <!----------------- title 시작 ----------------->
      <table width="97%" border="0" cellspacing="0" cellpadding="0" class=page_title>
        <tr> 
          <td><img src="/images/admin/exam/e_title03.gif" ></td>
          <td align="right"><img src="/images/admin/common/sub_title_tail.gif" ></td>
        </tr>
      </table>
      <!----------------- title 끝 ----------------->
      <br>
      <!----------------- 과정명 시작 ----------------->
      <table cellspacing="0" cellpadding="0" class="table1">
        <tr> 
		  <td width="1%"><img src="/images/admin/common/icon.gif" ></td>
          <td class=sub_title>과정명 
            : <%=GetCodenm.get_subjnm(v_subj)%>(<%=v_subj%>)&nbsp;&nbsp;&nbsp;&nbsp;과정차수 : <%=StringManager.cutZero(v_subjseqgr)%>차</td>
        </tr>
        <tr> 
          <td height="3"></td>
        </tr>
      </table>
      <!----------------- 과정명 끝 ----------------->

      <!----------------- 평가마스터 수정 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
        <tr> 
          <td colspan="4" class="table_top_line"></td>
        </tr>
        <tr> 
          <td width="16%" class="table_title">문제당 배점</td>
          <td width="" class="table_02_2"><input name="p_exampoint" type="text" class="input" size="5" value="<%=dbox0.getString("d_exampoint")%>">점</td>
          <td width="16%" class="table_title">문제수</td>
          <td class="table_02_2"><input name="p_examcnt" type="text" class="input" size="5" value="<%=dbox0.getString("d_examcnt")%>"></td>
        </tr>
      </table>
      <br>
	  <table cellspacing="1" cellpadding="5" class="table_out" id="tb1">
        <tr> 
          <td colspan="4" class="table_top_line"></td>
        </tr>
        <tr> 
          <td width="5%" class="table_title">일차</td>
          <td width="" class="table_title">난이도 상</td>
          <td width="" class="table_title">난이도 중</td>
          <td width="" class="table_title">난이도 하</td>
        </tr>

<%
    ArrayList  lessonlist = (ArrayList)request.getAttribute("ExamLevelsData");
	ArrayList levelslist = null;
	ArrayList typelist = null;
	s_lesson = lessonlist.size();

    ArrayList  lessonlistM = (ArrayList)request.getAttribute("ExamMasterLevels");
	ArrayList levelslistM = null;
	ArrayList typelistM = null;

    int istlesson = Integer.parseInt(v_lessonstart)-1;  // 시작 차시
    
    for ( int i =0; i < lessonlist.size() ; i++){
     
%>
        <tr class="tb1tr">
		  <td width="5%" class="table_01" ><%=String.valueOf(i+1+istlesson)%></td>
<%
	      levelslist = (ArrayList)lessonlist.get(i);
          s_levels = levelslist.size();

	      levelslistM = (ArrayList)lessonlistM.get(i);

	      for ( int j = 0 ; j < levelslist.size() ;  j++) {

			  typelist = (ArrayList)levelslist.get(j);
			  s_type = typelist.size();

			  typelistM = (ArrayList)levelslistM.get(j);
              if(typelistM.size()==0)
                break;
%>     
		  <td width="" class="table_02_1">
		     <table cellspacing="1" cellpadding="5" class="" >
<%
			  for ( int k = 0; k < typelist.size() ; k++) {
				  DataBox dbox = (DataBox)typelist.get(k);	

				  if ( (k+1)%3 == 0 ) {
				  
%>
				<tr>
				   <td>
				       OX식
				   </td>
				   <td>
				       (<input name="p_cntlevelM<%=i+1+istlesson%>M<%=j+1%>M<%=k+1%>" type="text" class="input" size="3" value="<%=(String)typelistM.get(k)%>">&nbsp;/ <%=dbox.getInt("d_levelscount")%>)
					    <input type="hidden" name="v_cntlevelM<%=i+1+istlesson%>M<%=j+1%>M<%=k+1%>"        value="<%=dbox.getInt("d_levelscount")%>">
				   </td>
				</tr>
<%				
				  } else if ( (k+1)%2 == 0 ) {
%>
				<tr>
				   <td>
				       주관식
				   </td>
				   <td>
				       (<input name="p_cntlevelM<%=i+1+istlesson%>M<%=j+1%>M<%=k+1%>" type="text" class="input" size="3" value="<%=(String)typelistM.get(k)%>">&nbsp;/ <%=dbox.getInt("d_levelscount")%>)
					    <input type="hidden" name="v_cntlevelM<%=i+1+istlesson%>M<%=j+1%>M<%=k+1%>"        value="<%=dbox.getInt("d_levelscount")%>">
				   </td>
				</tr>
<%
                 }  else {					  
%>
			    <tr>
				   <td>
				       객관식
				   </td>
				   <td>
				       (<input name="p_cntlevelM<%=i+1+istlesson%>M<%=j+1%>M<%=k+1%>" type="text" class="input" size="3" value="<%=(String)typelistM.get(k)%>">&nbsp;/ <%=dbox.getInt("d_levelscount")%>)
					   <input type="hidden" name="v_cntlevelM<%=i+1+istlesson%>M<%=j+1%>M<%=k+1%>"        value="<%=dbox.getInt("d_levelscount")%>">
				   </td>
				</tr>
<%
                  } 

              }
%>
			 </table>
		  </td>
<%
		}
%>          
        </tr>
<%
   }
%>
      </table>
      <!----------------- 평가마스터 수정 끝 ----------------->
      <br>
      <!----------------- 수정, 리스트 버튼 시작 ----------------->
      <table  border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td><a href="javascript:history.go(-1);"><img src="/images/admin/button/btn_before.gif"  border="0"></a></td>
<%
		if(!v_isResult) {	 %>
		  <td width=8></td>
          <td><a href="javascript:UpdatePaper('<%=s_lesson%>','<%=s_levels%>','<%=s_type%>')"><img src="/images/admin/button/btn_apply.gif"  border="0"></a></td>  
<%      }else{	%>
		  <td width=8></td>
          <td><a href="javascript:alert('응시자가 있습니다. 수정할 수 없습니다.');"><img src="/images/admin/button/btn_apply.gif"  border="0"></a></td>  
<%      }%>
		  <td width=8></td>
		  <td><a href="javascript:window.close()"><img src="/images/admin/button/btn_cancel.gif"  border="0"></a></td>  
        </tr>
      </table>
      <!-----------------  수정, 리스트 버튼 끝 ----------------->
      <br>
      <br>
      <br>
    <!--  <font color= blue>(분기식과정에 해당할 경우에는 분기와 분기 아닌 일차가 같은 평가범위에 있을 수 없습니다.)</font>   -->
    </td>
  </tr>
</table>
</form>  
<script language="JavaScript">
<!--
function UpdatePaper(s_lesson, s_levels, s_type) {
  if (!chkData()) {
    return;
  }
  document.form1.p_process.value = 'ExamPaperUpdate';
  document.form1.p_action.value  = 'go';
  document.form1.p_reloadlist.value = 'true';
  document.form1.submit();
}
function chkData( ) {
  var v_examcnt = 0;

  if (!number_chk_noalert(document.form1.p_exampoint.value) || 1 > document.form1.p_exampoint.value) {
    document.form1.p_exampoint.focus();
    alert('문제당 배점이 잘못 입력되었습니다.');
    return false;
  }
  if (!number_chk_noalert(document.form1.p_examcnt.value) || 1 > document.form1.p_examcnt.value) {
    document.form1.p_examcnt.focus();
    alert('문제수가 잘못 입력되었습니다.');
    return false;
  }
  if (document.form1.p_exampoint.value * document.form1.p_examcnt.value != 100) {
    document.form1.p_exampoint.focus();
    alert('총점수는 100점입니다. 문제당 배점이나 문제수를 조정해 주십시요.');
    return false;
  }

	var s_level1text = "";
	var s_level2text = "";
	var s_level3text = ""; 
	var s_cntlevel1 = 0; 
	var s_cntlevel2 = 0; 
	var s_cntlevel3 = 0;

    var tableHwnd=$("#tb1");
    var tableRowsCount=$(".tb1tr",tableHwnd).length;
    var inputBoxVal=$("[name='p_examcnt']").val();
    var sumInput=0;

    for(i=0;i<tableRowsCount;i++)
    {
        var tableTr=$(".tb1tr:eq("+i+")",tableHwnd);
        for(j=0;j<3;j++)
        {
             var tableTd=$(".table_02_1:eq("+j+")",tableTr);
            
            $(".input",tableTd).each(function(k){
                var val=Number($(this).val());
                sumInput+=val;

                switch(j)
                {
                    case 0:
                        s_cntlevel1+=val;
                        break;
                    case 1:
                        s_cntlevel2+=val;
                        break;
                    case 2:
                        s_cntlevel3+=val;
                        break;
                }

                switch(j)
                {
                    case 0:
                        if(k==2)
                            if(i==(tableRowsCount-1))
                                s_level1text+=(k+1)+","+val;
                            else
                                s_level1text+=(k+1)+","+val+"/";
                        else
                            s_level1text+=(k+1)+","+val+"|";
                        break;
                    case 1:
                        if(k==2)
                            if(i==(tableRowsCount-1))
                                s_level2text+=(k+1)+","+val;
                            else
                                s_level2text+=(k+1)+","+val+"/";
                        else
                            s_level2text+=(k+1)+","+val+"|";
                        break;
                    case 2:
                        if(k==2)
                        {
                            if(i==(tableRowsCount-1))
                                s_level3text+=(k+1)+","+val;
                            else
                                s_level3text+=(k+1)+","+val+"/";
                        }
                        else
                            s_level3text+=(k+1)+","+val+"|";
                        break;
                }
            });
        }
    }

   if(sumInput!=0 && sumInput!=inputBoxVal)
    {
        alert('일차별 난이도 문항수의 합('+sumInput+')이 문제수('+inputBoxVal+')와 일치 하지 않습니다.');
        return;
    }

    document.form1.p_level1text.value = s_level1text;
    document.form1.p_level2text.value = s_level2text;
    document.form1.p_level3text.value = s_level3text;

    document.form1.p_cntlevel1.value = s_cntlevel1;
    document.form1.p_cntlevel2.value = s_cntlevel2;
    document.form1.p_cntlevel3.value = s_cntlevel3;

  return true;
}
-->
</script>
<%@ include file = "/learn/library/getJspName.jsp" %>
</body>
</html>

