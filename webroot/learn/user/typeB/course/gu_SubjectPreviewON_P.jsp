<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page isELIgnored="false" %>
<%
	pageContext.setAttribute("CR", "\r");
	pageContext.setAttribute("LF", "\n");
	pageContext.setAttribute("CRLF", "\r\n");
	pageContext.setAttribute("SP", "&nbsp;");
	pageContext.setAttribute("BR", "<br/>");
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="euc-kr">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>한국콘텐츠아카데미</title>

    <!-- Bootstrap / Jquery UI -->
    <link href="/common/js/jquery-ui-1.12.1/jquery-ui.min.css" rel="stylesheet">
    <!--<link href="/common/js/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.css" rel="stylesheet">-->
    <link href="/common/js/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    
    <script src="/common/js/jquery-3.3.1.min.js"></script>
    <script src="/common/js/jquery-ui-1.12.1/jquery-ui.min.js"></script>
    <!--<script src="/common/js/jquery.mobile-1.4.5/jquery.mobile-1.4.5.min.js"></script>-->
    <script src="/common/js/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    
    
    <!-- Custom CSS -->
    <link href="/common/css/custom.css" rel="stylesheet">
    <link href="/common/css/header.css" rel="stylesheet">
    <link href="/common/css/footer.css" rel="stylesheet">
    
    <!-- Custom JS -->
    <script src="/common/js/custom.js"></script>
</head>
<body>
    <section>
        <div class="wrapper">
            <div class="container">
                <div class="row">
                    <div class="col-12">
                        <div class="subContainer">
                            <div class="sub_section">
                                <div class="sub_contents_header">
                                    <span>과정 소개</span>
                                </div>
                                <div class="sub_info_body">
                                    <div class="sub_info_body">
                                        <div class="sub_course_alert_box">
                                            <p><c:out value="${subjectPreview.d_subjnm }" /></p>
                                        </div>
                                        <table>
                                            <colgroup>
                                                <col width="15%">
                                                <col width="auto">
                                            </colgroup>
                                            <tbody>
                                                <tr>
                                                    <th>과정 분류명</th>
                                                    <td><c:out value="${subjectPreview.d_classname }" /></td>
                                                </tr>
                                                <tr>
                                                    <th>교육구분</th>
                                                    <td><c:out value="${subjectPreview.d_isonoff eq 'ON' ? '사이버' : '집합'}" /></td>
                                                </tr>
                                                <tr>
                                                    <th>교육목표</th>
                                                    <td>
                                                    	<c:set var="intro" value="${fn:replace(subjectPreview.d_intro, CRLF, BR)}" />
														<c:set var="intro" value="${fn:replace(intro, CR, BR)}" />
														<c:set var="intro" value="${fn:replace(intro, LF, BR)}" />
														<c:out value="${intro }" escapeXml="false" />
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <th>교육내용</th>
                                                    <td>
														<c:set var="explain" value="${fn:replace(subjectPreview.d_explain, CRLF, BR)}" />
														<c:set var="explain" value="${fn:replace(explain, CR, BR)}" />
														<c:set var="explain" value="${fn:replace(explain, LF, BR)}" />
														<c:out value="${explain }" escapeXml="false" />
													</td>
                                                </tr>
                                                <tr>
                                                    <th>교육담당자</th>
                                                    <td><c:out value="${subjectPreview.d_mastertel }" /></td>
                                                </tr>
                                            </tbody>
                                        </table> 
                                    </div>
                                </div>
                                <div class="popup_btnBox">
                                    <a href="javascript:self.close();" class="btn_courseCancel">닫기</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</body>
</html>

