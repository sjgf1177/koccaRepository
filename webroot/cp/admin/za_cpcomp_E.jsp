<%
//**********************************************************
//  1. 제      목: 업체현황 레포팅
//  2. 프로그램명: za_cpcomp_E.jsp
//  3. 개      요: 업체현황 레포팅
//  4. 환      경: JDK 1.4
//  5. 버      젼: 1.0
//  6. 작      성: 이창훈 2005. 10. 1
//  7. 수      정: 
//**********************************************************
%>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.common.*" %>

<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");

        String  v_gubun = box.getString("p_gubun"); //회사구분
                
        String	v_orderColumn	= box.getString("p_orderColumn");           	//정렬할 컬럼명
        String	v_orderType     = box.getString("p_orderType");           		//정렬할 순서

		String v_param = "";
			if (!v_gubun.equals("ALL") && !v_gubun.equals("")) v_param+= " and gubun = '" + v_gubun + "'";
			if (v_orderColumn.equals("")) {
				v_param+= " order by comp";
			} else {
			    v_param+= " order by " + v_orderColumn + v_orderType;
			}
%>
<HTML>
<HEAD>
<TITLE></TITLE>

</HEAD>

<BODY leftmargin="0" topmargin="0" bottommargin="0" marginwidth="0" marginheight="0" >
<!-- 공통파일 시작-->
<%@ include file="/learn/library/oz.jsp"%>
<!-- 공통파일 끝-->
   <param name="connection.reportname" value="cp/cpinfo_add.ozr">
   <param name="viewer.configmode" value="html">
   <param name="viewer.isframe" value="false">
   <param name="odi.odinames" value="cpinfo_add">
   <!--param name="odi.CompList.pcount" value="1">
   <param name="odi.CompList.args1" value="condition=<%=v_param%>" -->
</OBJECT>



</body>
</HTML>

