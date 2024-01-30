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
<%
    //DEFINED class&variable START
    RequestBox box = (RequestBox)request.getAttribute("requestbox");
%>
<%@ page isELIgnored="false" %>
<!-- ���̹� ��Ʈ ���̺귯�� -->
<link href="/common/js/billboard/billboard.css" rel="stylesheet">
<script src="/common/js/billboard/billboard.js"></script>
<script src="/common/js/billboard/billboard.pkgd.js"></script>

<jsp:include page="/learn/user/typeB/include/topMainAsp.jsp"/>
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
                                            <b class="card-text">20</b>
                                        </div>
                                    </div>
                                    <div class="card">
                                        <div class="card-body">
                                            <p class="card-title">���� ���� ����</p>
                                            <b class="card-text">20</b>
                                        </div>
                                    </div>
                                    <div class="card">
                                        <div class="card-body">
                                            <p class="card-title">������ ����</p>
                                            <b class="card-text">20</b>
                                        </div>
                                    </div>
                                    <div class="card">
                                        <div class="card-body">
                                            <p class="card-title">�̼����� ����</p>
                                            <b class="card-text">20</b>
                                        </div>
                                    </div>
                                </div>

                                <div class="study-list-box mb30">
                                    <div class="top-box">
                                        <h5>�������� ����</h5>
                                        <a href="#">��ü����</a>
                                    </div>
                                    <ul class="my_card_list_box d-flex">

                                        <li class="">
                                            <div class="tnail_box">
                                                <img src="/images/2023/CB23003-ezgif.com-crop.png" alt="������ ȣ��">
                                            </div>
                                            <div class="info_text_box">
                                                <h5>
                                                    <a href="">������ Ʈ���������̼� ����Ͻ� �з������� ���� �ٲٴ� ������</a>
                                                </h5>
                                                <p>2023.10.00 ~ 2023.12.31</p>
                                                <div class="progress-box mt-2">
                                                    <div class="progress" role="progressbar" aria-label="progressbar" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100">
                                                        <div class="progress-bar" style="width: 75%; margin:0;"></div>
                                                    </div>
                                                    <span>75%</span>
                                                </div>
                                            </div>
                                        </li>
                                        <li class="">
                                            <div class="tnail_box">
                                                <img src="/images/2023/CB23003-ezgif.com-crop.png" alt="������ ȣ��">
                                            </div>
                                            <div class="info_text_box">
                                                <h5>
                                                    <a href="">������ Ʈ���������̼� ����Ͻ� �з������� ���� �ٲٴ� ������</a>
                                                </h5>
                                                <p>2023.10.00 ~ 2023.12.31</p>
                                                <div class="progress-box mt-2 red">
                                                    <div class="progress" role="progressbar" aria-label="progressbar" aria-valuenow="10" aria-valuemin="0" aria-valuemax="100">
                                                        <div class="progress-bar" style="width: 10%; margin:0;"></div>
                                                    </div>
                                                    <span>10%</span>
                                                </div>
                                            </div>
                                        </li>
                                        <li class="">
                                            <div class="tnail_box">
                                                <img src="/images/2023/CB23003-ezgif.com-crop.png" alt="������ ȣ��">
                                            </div>
                                            <div class="info_text_box">
                                                <h5>
                                                    <a href="">������ Ʈ���������̼� ����Ͻ� �з������� ���� �ٲٴ� ������</a>
                                                </h5>
                                                <p>2023.10.00 ~ 2023.12.31</p>
                                                <div class="progress-box  mt-2">
                                                    <div class="progress" role="progressbar" aria-label="progressbar" aria-valuenow="75" aria-valuemin="0" aria-valuemax="100">
                                                        <div class="progress-bar" style="width: 75%; margin:0;"></div>
                                                    </div>
                                                    <span>75%</span>
                                                </div>
                                            </div>
                                        </li>

                                        <!-- �������� ���� ���� ��-->
                                        <!--
                                        <li class="text-center">
                                            <span>�������� ������ �����ϴ�.</span>
                                        </li>
                                        -->
                                    </ul>
                                </div>
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
                                                            ["��ۿ���", 30],
                                                            ["����", 30],
                                                            ["��ȭ/�ִ�/ĳ����", 30],
                                                            ["����/����", 30],
                                                            ["�ι�����", 120]
                                                        ],
                                                        type : "pie"
                                                    },
                                                    pie: {
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
                                                    type: "line", // for ESM specify as: line()
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

                                            <tr class="left_line">
                                                <td class="text_circle"><b class="point_purple">��������</b></td>
                                                <td>
                                                    <b class="point_purple">������ Ʈ���������̼�, ����Ͻ� �з������� �ٲٴ�</b> ����<br>
                                                    - 2024.02.15
                                                </td>
                                            </tr>

                                            <tr class="left_line">
                                                <td class="text_circle"><b class="point_purple">�����Ϸ�</b></td>
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
