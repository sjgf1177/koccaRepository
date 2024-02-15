<%
    //**********************************************************
//  1. ��      ��: EDUCATION STUDYING SUBJECT PAGE
//  2. ���α׷���: gu_DashboardPage_L.jsp
//  3. ��      ��: �뽬����
//  4. ȯ      ��: JDK 1.5
//  5. ��      ��: 1.0
//  6. ��      ��: 2024/01/18
//  7. ��      ��:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.course.*" %>
<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%@ taglib uri="/tags/KoccaSelectTaglib" prefix="kocca_select" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
%>
<%@ page isELIgnored="false" %>
<!-- ���̹� ��Ʈ ���̺귯�� -->
<link href="/common/js/billboard/billboard.css" rel="stylesheet">
<script src="/common/js/billboard/billboard.js"></script>
<script src="/common/js/billboard/billboard.pkgd.js"></script>
<script type="text/javascript">
    //���������� POPUP
    function whenSubjInfoPopup(subj, subjnm, isonoff) {
        window.self.name = "SubjList";
        window.open("", "openSubjInfo", "scrollbars=no,width=720,height=800,scrollbars=yes,resizable=yes'");

        document.form1.target = "openSubjInfo"
        document.form1.p_subj.value = subj;
        document.form1.p_subjnm.value = subjnm;
        document.form1.p_isonoff.value = isonoff;
        document.form1.p_process.value = 'SubjectPreviewPopup';
        document.form1.action = '/servlet/controller.propose.ProposeCourseServlet';
        document.form1.submit();

        document.form1.target = window.self.name;
    }
</script>

<jsp:include page="/learn/user/typeB/include/topMainAsp.jsp"/>
<form name="form1" method="post">
    <input type='hidden' name='p_process'>
    <input type='hidden' name='p_subj' value="">
    <input type='hidden' name='p_year' value="">
    <input type='hidden' name='p_subjseq' value="">
    <input type='hidden' name='p_scsubjseq' value="">
    <input type='hidden' name='p_subjnm' value="">
    <input type='hidden' name='p_userid' value="">
    <input type='hidden' name='p_gubun' value="">
    <input type='hidden' name='p_rejectedreason' value="">
    <input type='hidden' name='p_isonoff' value="">
    <input type='hidden' name='p_kind' value="">
    <input type='hidden' name='p_grcode' value="${sessionScope.tem_grcode }">
    <input type='hidden' name='s_grcode' value="${sessionScope.tem_grcode }">
    <input type="hidden" name="p_pageno" value="<c:out value="${param.p_pageno }" />">
    <input type="hidden" name="p_pagesize" value="<c:out value="${param.p_pagesize }" />"/>
    <!-- �н�â �ٽö���� 1:�ٽ� �ȶ���  2:�ٽö���(�н����������� �ݾ������) -->
    <input type='hidden' name='lessonRepopup' value="1">
    <input type='hidden' name='lessonReurl' value="">
    <input type='hidden' name='lessonReiurl' value="">
    <!-- <input type='hidden' name='wj_classkey' value="">-->
    <input type="hidden" name="gubun" value="${param.gubun }"/>
    <input type="hidden" name="menuid" value="${param.menuid }"/>
</form>
<section class="container d-flex myclass01">
    <div class=""></div>
    <div class="subContainer">
        <div class="sub_section">
            <div class="sub_contents_body">
                <div class="sub_board_header">
                    <jsp:include page="/learn/user/typeB/include_left/left_3.jsp">
                        <jsp:param value="${param.menuid }" name="left_active"/>
                    </jsp:include>
                </div>
                <div class="subContainer">
                    <div class="sub_section">
                        <div class="sub_contents_body">
                            <!-- ��� ���� ���� start -->
                            <div class="dashboard_contents_box">
                                <div class="card-list-box d-flex overflow-auto mb30">
                                    <div class="card">
                                        <div class="card-body">
                                            <p class="card-title">��û�� ����</p>
                                            <b class="card-text">${cntList[0].d_proposecnt}</b>
                                        </div>
                                    </div>
                                    <div class="card">
                                        <div class="card-body">
                                            <p class="card-title">���� ���� ����</p>
                                            <b class="card-text">${dashBoardStudyListCnt}</b>
                                        </div>
                                    </div>
                                    <div class="card">
                                        <div class="card-body">
                                            <p class="card-title">������ ����</p>
                                            <b class="card-text">${cntList[0].d_graduatedcnt}</b>
                                        </div>
                                    </div>
                                    <div class="card">
                                        <div class="card-body">
                                            <p class="card-title">�̼����� ����</p>
                                            <b class="card-text">${cntList[0].d_ungraduatedcnt}</b>
                                        </div>
                                    </div>
                                </div>

                                <div class="study-list-box mb30">
                                    <div class="top-box">
                                        <h5>�������� ����</h5>
                                        <a href="javascript:menuForward('3', '11');">��ü����</a>
                                    </div>
                                    <ul class="my_card_list_box d-flex">
                                        <c:forEach items="${dashBoardStudyList }" var="list" varStatus="status">
                                            <c:if test="${status.count lt 4}">
                                                <fmt:parseDate value="${list.d_edustart}" var="sttDt" pattern="yyyyMMddHH"/>
                                                <fmt:parseDate value="${list.d_eduend}" var="endDt" pattern="yyyyMMddHH"/>

                                                <li class="">
                                                    <div class="tnail_box">
                                                        <img src="${list.d_introducefilenamenew}" alt="${list.d_subjnm}">
                                                        <%--<img src="https://edu.kocca.kr/upload/renewsnail/1080.png">--%>

                                                        <%--<img src="https://test.edukocca.or.kr${list.d_introducefilenamenew}" alt="${list.d_subjnm}">--%>
                                                    </div>
                                                    <div class="info_text_box">
                                                        <h5>
                                                            <a href="javascript:whenSubjInfoPopup('<c:out value="${list.d_subj }" />','<c:out value="${list.d_subjnm }" />','<c:out value="${list.d_isonoff }" />');" class="textline2">${list.d_subjnm}</a>
                                                        </h5>
                                                        <p><fmt:formatDate value="${sttDt}" pattern="yyyy.MM.dd"/> ~ <fmt:formatDate value="${sttDt}" pattern="yyyy.MM.dd"/></p>
                                                        <div class="progress-box">
                                                            <div class="progress" role="progressbar" aria-label="progressbar" aria-valuenow="${list.d_score}" aria-valuemin="0" aria-valuemax="100">
                                                                <div class="progress-bar" style="width: ${list.d_score}%; margin:0;"></div>
                                                            </div>
                                                            <span>${list.d_score}%</span>
                                                        </div>
                                                    </div>
                                                </li>
                                            </c:if>
                                        </c:forEach>

                                        <!-- �������� ���� ���� ��-->
                                        <!--
                                        <li class="text-center">
                                            <span>�������� ������ �����ϴ�.</span>
                                        </li>
                                        -->
                                    </ul>
                                </div>
                                <c:set var="b_cnt" value="0" />
                                <c:set var="g_cnt" value="0" />
                                <c:set var="k_cnt" value="0" />
                                <c:set var="x_cnt" value="0" />
                                <c:forEach items="${cateList }" var="list" varStatus="status">
                                    <c:if test="${list.d_area eq 'B0' and list.d_cnt gt 0}">
                                        <c:set var="b_cnt" value="${list.d_cnt}" />
                                    </c:if>

                                    <c:if test="${list.d_area eq 'G0' and list.d_cnt gt 0}">
                                        <c:set var="g_cnt" value="${list.d_cnt}" />
                                    </c:if>

                                    <c:if test="${list.d_area eq 'K0' and list.d_cnt gt 0}">
                                        <c:set var="k_cnt" value="${list.d_cnt}" />
                                    </c:if>

                                    <c:if test="${list.d_area eq 'XX' and list.d_cnt gt 0}">
                                        <c:set var="x_cnt" value="${list.d_cnt}" />
                                    </c:if>
                                </c:forEach>
                                <div class="d-flex row-chart-box mb30">
                                    <div class="category-chart-box">
                                        <div class="top-box">
                                            <h5>�оߺ� ���Ǽ�����Ȳ<small>(ȸ)</small></h5>
                                        </div>
                                        <div class="chart-box">
                                            <div id="expandRate"></div>

                                            <script type="text/javascript">
                                                var expandRate = bb.generate({
                                                    bindto: "#expandRate",
                                                    data: {
                                                        columns: [
                                                            ["��ۿ���", ${b_cnt}],
                                                            ["����", ${g_cnt}],
                                                            ["��ȭ", ${k_cnt}],
                                                            ["��Ÿ", ${x_cnt}]
                                                        ],
                                                        type : "donut"
                                                    },
                                                    arc: {
                                                        cornerRadius: {
                                                            ratio: 0.2
                                                        }
                                                    },
                                                    donut: {
                                                        label: {
                                                            format: function(value, ratio, id) {
                                                                return value +"\ȸ";
                                                            }
                                                        }
                                                    },
                                                });
                                            </script>
                                        </div>
                                    </div>

                                    <div class="timechart-box">
                                        <div class="top-box">
                                            <h5>���� �н� ��Ȳ<small>(�ð�)</small></h5>
                                        </div>
                                        <div class="chart-box">
                                            <div id="xAxisTickPosition01"></div>
                                            <script type="text/javascript">
                                                var xAxisTickPositionchart01 = bb.generate({
                                                    bindto: "#xAxisTickPosition01",
                                                    data: {
                                                        x: "x",
                                                        columns: [
                                                            ["x", "ȸ�����", "��"],
                                                            ["�ð�", 1, 5],
                                                        ],
                                                        type: "bar",
                                                    },
                                                    axis: {
                                                        rotated: true,
                                                        x: {
                                                            type: "category",
                                                            clipPath: false,
                                                            inner: false,
                                                        },
                                                        y: {
                                                            show: false
                                                        }
                                                    },
                                                    bar: { //�� �ʺ�
                                                        width: {
                                                            ratio: 0.9,
                                                            max: 40
                                                        }
                                                    },
                                                });
                                            </script>
                                        </div>
                                    </div>
                                </div>


                                <div class="mouthchart-box mb30">
                                    <div class="top-box">
                                        <h5>���� �н� ����<small>(�ð�)</small></h5>
                                    </div>
                                    <div class="chart-box">
                                        <div id="lineChart"></div>
                                        <script type="text/javascript">
                                            var chart = bb.generate({
                                                bindto: "#lineChart",
                                                data: {
                                                    x: "x",
                                                    columns: [
                                                        ["x", "1��", "2��", "3��", "4��", "5��", "6��", "7��", "8��", "9��", "10��", "11��", "12��"],
                                                        ["ȸ�����", 30, 200, 100, 100, 150, 250, 200, 180, 220, 280, 310, 330],
                                                        ["��", 15, 100, 70, 54, 90, 200, 100, 160, 170, 170, 220, 300],
                                                    ],
                                                    type: "spline", // for ESM specify as: line()
                                                },
                                                axis: {
                                                    x: {
                                                        type: "category"
                                                    }
                                                },
                                                grid: {
                                                    x: {
                                                        show: true
                                                    },
                                                    y: {
                                                        show: true
                                                    }
                                                },

                                            });
                                        </script>
                                    </div>
                                </div>

                                <div class="my-activi-box">
                                    <div class="top-box">
                                        <h5>���� �н� Ȱ��</h5>
                                    </div>
                                    <div class="chart-box sub_boarder_body">
                                        <table class="td_align_left">
                                            <colgroup>
                                                <col width="13%">
                                                <col width="auto">
                                            </colgroup>
                                            <tbody>

                                            <tr class="left_line">
                                                <td class="text_circle"><b class="point_purple">��û�Ϸ�</b></td>
                                                <td>
                                                    <b class="point_purple">������ Ʈ���������̼�, ����Ͻ� �з������� �ٲٴ�</b> ����<br>
                                                    - 15����
                                                </td>
                                            </tr>

                                            <tr class="left_line">
                                                <td class="text_circle"><b class="point_purple">�н�����</b></td>
                                                <td>
                                                    <b class="point_purple">������ Ʈ���������̼�, ����Ͻ� �з������� �ٲٴ�</b> ����<br>
                                                    - 2024.02.15
                                                </td>
                                            </tr>

                                            <tr class="left_line">
                                                <td class="text_circle"><b class="point_purple">�н��Ϸ�</b></td>
                                                <td>
                                                    <b class="point_purple">������ Ʈ���������̼�, ����Ͻ� �з������� �ٲٴ�</b> ����<br>
                                                    - 2024.02.15
                                                </td>
                                            </tr>

                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                            <!-- ��� ���� ���� end-->
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</section>

<!-- footer -->
<jsp:include page="/learn/user/typeB/include/newFooterB.jsp"/>
<!-- footer -->

</body>
</html>
