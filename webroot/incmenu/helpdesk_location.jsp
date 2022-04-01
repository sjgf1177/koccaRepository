<%--  
	제  목 : Help Desk의 Recursive Path 정보를 출력하기 위한 페이지
	파일명 : helpdesk_location.jsp
	작성일 : 2005.01.
	회  사 :(주)다음소프트 
	작성자 : 이도경
	설  명 : Help Desk의  Recursive Path 정보를 출력하기 위한 페이지
		   
	관련 클래스      : 
	Input Parameter  : 페이지 URL
	분기 Page        : 없음
	Output Parameter : 없음
	
	//FAQ = zu_HomePageFaq_XX
	//QNA = zu_HomePageQna_XX
	//Admin = zu_HomePageAdmin_XX
	//Manual = 온라인 매뉴얼 시작 및 교육과정 = manual_01
	//			지식충전소 = manual_02
	//			골드자료실 = manual_03
	//			커뮤니티	= manual_04_XX
	//			포인트제도	= manual_05
--%> 
<%@ page language="java" contentType="text/html; charset=euc-kr" %>

<%
	String sUrl = request.getParameter("URL");
	String sPath = "<a href='/'>HOME</a> > <a href='javascript:faqList();'>Help Desk<a/> > ";
	try {	
		if(sUrl.indexOf("zu_HomePageFaq") > 0) {
			sPath += "<a href='javascript:faqList();'>FAQ</a>";
		}else if(sUrl.indexOf("zu_HomePageQna") > 0 ) {
			sPath += "<a href='javascript:qnaList();'>Q&A</a>";
		}else if(sUrl.endsWith("manual_00.jsp")) {
			sPath += "<a href='./manual_00.jsp'>온라인 매뉴얼</a> > <a href='./manual_00.jsp'>수강신청</a>";
		}else if(sUrl.endsWith("manual_01.jsp")) {
			sPath += "<a href='./manual_01.jsp'>온라인 매뉴얼</a> > <a href='./manual_01.jsp'>나의강의실</a>";
		}else if(sUrl.endsWith("manual_01_01.jsp")) {
			sPath += "<a href='./manual_01.jsp'>온라인 매뉴얼</a> > <a href='./manual_01.jsp'>나의강의실</a>";
		}else if(sUrl.indexOf("manual_02") > 0) {
			sPath += "<a href='./manual_02.jsp'>온라인 매뉴얼</a> > <a href='./manual_02.jsp'>지식충전소</a>";
		}else if(sUrl.endsWith("manual_03.jsp")) {
			sPath += "<a href='./manual_03.jsp'>온라인 매뉴얼</a> > <a href='./manual_03.jsp'>골드자료실</a>";
		}else if(sUrl.indexOf("manual_04") > 0 ) {
			sPath += "<a href='./manual_04.jsp'>온라인 매뉴얼</a> > <a href='./manual_04_01.jsp'>커뮤니티</a>";
		}else if(sUrl.endsWith("manual_05.jsp")) {
			sPath += "<a href='./manual_05.jsp'>온라인 매뉴얼</a> > <a href='./manual_05.jsp'>포인트제도</a>";
		}else if(sUrl.indexOf("zu_HomePageAdmin") > 0) {
			sPath += "<a href='javascript:adminList();'>운영자에게</a>";
		}
	}catch(Exception e) {
		sPath = "";
	}
		
%>
<TABLE cellpadding="0" cellspacing="0" border="0" width="100%">
    <tr>
        <td align="right" class="location"><img src="/images/common/bullet_location.gif"> <%=sPath%>   </td>
    </tr>
    <tr height="3">
        <td></td>
    </tr>
</table>