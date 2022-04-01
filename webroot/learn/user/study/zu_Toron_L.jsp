<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%
//**********************************************************
//  1. 제      목: TORON LIST
//  2. 프로그램명: zu_Toron_L.jsp
//  3. 개      요: 토론방 리스트
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박진희 2003. 9. 02
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.course.*" %>
<%@ page import = "com.credu.contents.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
    String  v_process   = box.getString("p_process");    
    String  v_subj      = box.getString("p_subj");
    String  v_year      = box.getString("p_year");
    String  v_subjseq   = box.getString("p_subjseq");
    String  v_tpcode    = "";
    String  v_title     = "";
    String  v_aduserid  = "";
    String  v_addate    = "";
    String  v_started   = "";
    String  v_ended     = "";
    String  v_name      = "";
    String  v_status    = "";
    int     v_cnt       =  0;
    int     i           =  0;
    ArrayList list      = null;
    //DEFINED class&variable END
        
    list = (ArrayList)request.getAttribute("topicList");  

    SubjectBean		subjbean = new SubjectBean();
	SubjectData		subjdata = new SubjectData();
					subjdata = subjbean.SelectSubjectData(box);

    EduStartBean	bean		= new EduStartBean();
    EduScoreData    scoredata	= new EduScoreData();
    				scoredata	= bean.SelectEduScore(box);

%>
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="ko">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr" />
<meta http-equiv="X-UA-Compatible" content="IE=EmulateIE7" />
<title>토론방</title>
<link rel="stylesheet" type="text/css" href="/css/portal/base.css" />
<script type="text/javascript" src="/script/portal/common.js"></script>
<script language="javascript">
<!--
    function topicInsert(){
        document.form1.p_process.value = "TopicInsertPage";
        document.form1.action = "/servlet/controller.study.ToronServlet";
        document.form1.submit();                
    }
    
    function topicView(tpcode){
        document.form1.p_process.value = "TopicSelect";
        document.form1.p_tpcode.value = tpcode;
        document.form1.action = "/servlet/controller.study.ToronServlet";
        document.form1.submit();           
    }
-->
</script>
</head>
<body id="pop_study"><!-- popup size : 890* -->
<form name="form1" method="post">
	<input type="hidden" name="p_process"/>
	<input type="hidden" name="p_subj" value="<%=v_subj%>"/>
	<input type="hidden" name="p_year" value="<%=v_year%>"/>
	<input type="hidden" name="p_subjseq" value="<%=v_subjseq%>"/>
	<input type="hidden" name="p_tpcode"/>

	<div id="pop_header">
		<h1><img src="/images/portal/myclass/study/pop_h1_tit11.gif" alt="토론방" /></h1>
	</div>
	<div id="pop_container">
		<div id="contentwrap">
			<div id="con_scoll">
			
<%	if (scoredata != null) { %>
				<div id="btn_close"><a href="javascript:top.window.close()"><img src="/images/portal/btn/btn_close.gif" alt="닫기" /></a></div>
				<div class="study_tltle study_tltle_sub"><div class="study_tltle_left"><div class="study_tltle_right">
					<dl class="class_info"> 
						<dt><span>과정명</span></dt>
						<dd><%= subjdata.getSubjnm() %></dd>
					</dl>
					<dl class="class_info">
						<dt><span>교육기간</span></dt>
						<dd class="class_day"><%= FormatDate.getFormatDate(scoredata.getEdustart(),"yyyy.MM.dd") %> ~<%= FormatDate.getFormatDate(scoredata.getEduend(),"yyyy.MM.dd") %></dd>
					</dl>
				</div></div></div>
<%	} %>
				
				<div class="content">
					<h3><img src="/images/portal/myclass/study/pop_stit13.gif" alt="토론방" /></h3>
					
					<table class="board_list">
					<colgroup><col width="8%" /><col width="33%" /><col width="10%" /><col width="22%" /><col width="9%" /><col width="10%" /><col width="8%" /></colgroup>
					<thead>
					<tr class="gray">
						<th><p>번호 </p></th>
						<th><p>주제</p></th>
						<th><p>상태</p></th>
						<th><p>토론기간</p></th>
						<th><p>발의자</p></th>
						<th><p>발의일</p></th>
						<th class="end"><p>조회수</p></th>
					</tr>
					</thead>
					<tbody>
<% 
            for(i = 0; i < list.size(); i++) {        
                ToronData data  = (ToronData)list.get(i);              
                v_tpcode    = data.getTpcode();   
                v_title     = data.getTitle();    
                v_aduserid  = data.getAduserid(); 
                v_addate    = data.getAddate();   
                v_started   = data.getStarted();  
                v_ended     = data.getEnded();
                v_name      = data.getName();     
                v_cnt       = data.getCnt();      
                if((Integer.parseInt(v_started) < Integer.parseInt(FormatDate.getDate("yyyyMMddHH")))&&
                (Integer.parseInt(FormatDate.getDate("yyyyMMddHH")) < Integer.parseInt(v_ended))){
                    v_status = "<a href=\"javascript:topicView('" + v_tpcode + "')\" class=\"board_btn2\"><span>참여하기</span></a>";
                }else{
                    v_status = "<a class=\"state\"><span class=\"finish\">완료</span></a>";
                }                                
                v_addate    = FormatDate.getFormatDate(v_addate,"yyyy.MM.dd"); 
                v_started   = FormatDate.getFormatDate(v_started,"yyyy.MM.dd HH" );    
                v_ended     = FormatDate.getFormatDate(v_ended,"yyyy.MM.dd HH");                  
%>
					<tr onmouseover="className='over'" onmouseout="className=' '">
						<td class="num"><%= list.size() - i %></td>
						<td class="ta_l"><a href="javascript:topicView('<%= v_tpcode %>')"><%= v_title %></a></td>
						<td><%= v_status %></td>
						<td class="num"><%= v_started %>시~<%= v_ended %>시</td>
						<td><%= v_name %></td>
						<td class="num"><%= v_addate %></td>
						<td class="num"><%= v_cnt %></td>
					</tr>
<%			}
			if(i == 0){ %>        
                    <tr> 
                    	<td colspan="7">등록된 내용이 없습니다</td>                  
                    </tr>
<%			} %>  

					</tbody>
					</table>
					
					<p class="list_btn"><a href="javascript:topicInsert()" class="btn_gr"><span>주제발의</span></a></p>
				</div>
			</div>
		</div>
	</div>
</form>
</body>
</html>