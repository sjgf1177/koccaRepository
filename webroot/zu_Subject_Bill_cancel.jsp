<%
//**********************************************************
//  1. 제	  목: zu_Subject_Bill_Result.jsp
//  2. 프로그램명: zu_Subject_Bill_Result.jsp
//  3. 개	  요: 과정조회
//  4. 환	  경: JDK 1.3
//  5. 버	  젼: 1.0
//  6. 작	  성: 2004.01.14
//  7. 수	  정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=MS949" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.propose.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<jsp:useBean id = "getCodenm" class = "com.credu.common.GetCodenm"  scope = "page" />
<%
ConfigSet cs = new ConfigSet();
com.inicis.inipay.INIpay50 inipay = new com.inicis.inipay.INIpay50();
inipay.SetField("inipayhome", cs.getProperty("inipay.inipayHome")); // INIpay Home (절대경로로 적절히 수정) - C:/INIpay41_JAVA
inipay.SetField("admin", cs.getProperty("inipay.keyPw")); // 키패스워드(상점아이디에 따라 변경) - 1111
inipay.SetField("keypw", cs.getProperty("inipay.keyPw")); // 키패스워드(상점아이디에 따라 변경) - 1111
inipay.SetField("subpgip", cs.getProperty("inipay.subPgIp")); // 고정 - 203.238.3.10
inipay.SetField("debug", cs.getProperty("inipay.debug")); // 로그모드("true"로 설정하면 상세한 로그가 생성됨)
inipay.SetField("url", cs.getProperty("inipay.url")); // 실제 서비스되는 상점 SITE URL로 변경할것 -- 기존 버그.. 잘못된 정보로 저장중이였음.("http://www.your_domain.co.kr")
inipay.SetField("tid","INIjspISP_INIpayTest20100112210401619154"); // 고정
inipay.SetField("type","cancel"); // 고정
inipay.SetField("cancelMsg", "DB Success"); // 취소사유
inipay.startAction();

%>
