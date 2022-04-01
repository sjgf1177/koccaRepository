<!DOCTYPE html>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "com.credu.study.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.contents.*" %>
<%@ page import = "com.credu.course.*" %>

<jsp:useBean id = "conf" class = "com.credu.library.ConfigSet"  scope = "page" />
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");

    String v_promotion  = (String)request.getAttribute("promotion");       //권장진도율
    String v_progress   = (String)request.getAttribute("progress");       // 자기진도율
    
	SubjectBean		subjbean = new SubjectBean();
	SubjectData		subjdata = new SubjectData();
					subjdata = subjbean.SelectSubjectData(box);
	
	EduStartBean	bean		= new EduStartBean();
	EduScoreData    scoredata	= new EduScoreData();
					scoredata	= bean.SelectEduScore(box);

%>
<html>
<head>
    <meta charset="euc-kr">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>한국콘텐츠아카데미</title>

    <!-- Bootstrap / Jquery UI -->
    <link href="/common/js/jquery-ui-1.12.1/jquery-ui.min.css" rel="stylesheet">
    <link href="/common/js/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    
    <script src="/common/js/jquery-3.3.1.min.js"></script>
    <script src="/common/js/jquery-ui-1.12.1/jquery-ui.min.js"></script>
    <script src="/common/js/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    
    
    <!-- Custom CSS -->
    <link href="/common/css/custom.css" rel="stylesheet">
    <link href="/common/css/header.css" rel="stylesheet">
    <link href="/common/css/footer.css" rel="stylesheet">
    
    <!-- Custom JS -->
    <script src="/common/js/custom.js"></script>
    
    <script type="text/javascript" src="/script/portal/common.js"></script>
	<script type="text/javascript">
	<!--
	function subjWright(){
		document.form1.p_subj.value = "ALL";
	    document.form1.p_process.value = 'SulmunUserPaperListPage';
	    document.form1.action = "/servlet/controller.research.SulmunSubjUserServlet";
	    document.form1.submit();
	
	}
	function contentsWright(){
		document.form1.p_subj.value = "REGIST";
	    document.form2.p_process.value = 'SulmunUserPaperListPage';
	    document.form2.action = "/servlet/controller.research.SulmunRegistUserServlet";
	    document.form2.submit();
	}
	
	// 과정 설문응시
	function contentsWright1(p_subjnm, p_subj, p_grcode, p_gyear, p_subjseq, p_sulpapernum, p_sulpapernm, p_sulstart, p_sulend, p_sulnums){
	        document.form1.p_subj.value = "ALL";
	        document.form1.s_subj.value = p_subj;   // tz_suleach 들어갈 과정코드
	        document.form1.p_subjnm.value = p_subjnm;
	        document.form1.p_grcode.value = "ALL";
	        document.form1.s_grcode.value = p_grcode;    // tz_suleach 들어갈 교육그룹코드       
	        document.form1.p_gyear.value = p_gyear;
	        document.form1.p_subjseq.value = p_subjseq;
	        document.form1.p_sulpapernum.value = p_sulpapernum;
	        document.form1.p_sulpapernm.value = p_sulpapernm;
			document.form1.p_sulstart.value = p_sulstart;
	        document.form1.p_sulend.value = p_sulend;
	        document.form1.p_sulnums.value = p_sulnums;    // 문제번호    
	        document.form1.p_listok.value = '0';
	
	        //window.self.name = "winSelectView";
	        //farwindow = window.open("", "openSubjChange", "toolbar=no,location=no,directories=no,status=no,menubar=no,scrollbars=yes,resizable=no,copyhistory=no, width = 680, height = 538, top=0, left=0");
	        document.form1.target = "_self";
	        document.form1.action = "/servlet/controller.research.SulmunSubjUserServlet";
	        document.form1.p_process.value = "SulmunUserPaperListPage";
	        document.form1.submit();
	
	        //farwindow.window.focus();
	        //document.form1.target = window.self.name;
	}
	
	//-->
	</script>
</head>
<%
	ArrayList blist = (ArrayList)request.getAttribute("SulmunSubjUserList");
	DataBox dbox01 = null;
	if(blist.size() > 0){
		dbox01 = (DataBox)blist.get(0);
	}else{
		dbox01 = new DataBox("resoponsebox");
	}
	
	int count = 0;
	
	ArrayList blist1 = (ArrayList)request.getAttribute("SulmunContentsUserList");
	DataBox dbox0 = null;
	if(blist1.size() > 0){
		dbox0 = (DataBox)blist1.get(0);
	}else{
		dbox0 = new DataBox("resoponsebox");
	}
%>
<form name="form1" method="post" action="/servlet/controller.research.SulmunSubjUserServlet">
	<input type="hidden" name="p_subjnm"    value=""/>            
	<input type="hidden" name="s_subj"    value="<%=dbox01.getString("d_subj")%>"/>    
	<input type="hidden" name="s_grcode"  value=""/>        
	<input type="hidden" name="p_sulstart"  value=""/>
	<input type="hidden" name="p_sulend"  value=""/>
	<input type="hidden" name="p_sulnums"  value=""/>        
	<input type="hidden" name="p_listok"  value=""/>      
	 
	<input type="hidden" name="p_process" value=""/>
	<input type="hidden" name="p_action"  value=""/>
	<input type="hidden" name="p_subj"    value=""/>
	<input type="hidden" name="p_gubun"    value=""/>
	<input type="hidden" name="p_grcode"  value="<%=dbox01.getString("d_grcode")%>"/>
	<input type="hidden" name="p_gyear"  value="<%=dbox01.getString("d_year")%>"/>
	<input type="hidden" name="p_subjseq"  value="<%=dbox01.getString("d_subjseq")%>"/>
	<input type="hidden" name="p_sulpapernum"  value="<%=dbox01.getString("d_sulpapernum")%>"/>
	<input type="hidden" name="p_sulpapernm"  value="<%=dbox01.getString("d_sulpapernm")%>"/>
	<input type="hidden" name="p_sulstart"  value="<%=dbox01.getString("d_sulstart")%>"/>
	<input type="hidden" name="p_sulend"  value="<%=dbox01.getString("d_sulend")%>"/>
	<input type="hidden" name="p_isalways"  value="<%=box.getString("p_isalways")%>"/>
</form>

<form name="form2" method="post" action="/servlet/controller.research.SulmunRegistUserServlet">
	<input type="hidden" name="p_process" value=""/>
	<input type="hidden" name="p_action"  value=""/>
	<input type="hidden" name="p_subj"    value="<%=dbox0.getString("d_subj")%>"/>
	<input type="hidden" name="s_subj"    value="<%=box.getString("p_subj")%>"/>
	<input type="hidden" name="p_grcode"  value="ALL"/>
	<input type="hidden" name="p_gyear"  value="<%=box.getString("p_year")%>"/>
	<input type="hidden" name="p_subjseq"  value="<%=box.getString("p_subjseq")%>"/>
	<input type="hidden" name="p_sulpapernum"  value="<%=dbox0.getString("d_sulpapernum")%>"/>
	<input type="hidden" name="p_sulpapernm"  value="<%=dbox0.getString("d_sulpapernm")%>"/>
	<input type="hidden" name="p_sulstart"  value="<%=dbox0.getString("d_sulstart")%>"/>
	<input type="hidden" name="p_sulend"  value="<%=dbox0.getString("d_sulend")%>"/>
	<input type="hidden" name="p_isalways"  value="<%=box.getString("p_isalways")%>"/>
</form>
<body id="pop_study">
    <section>
        <div class="wrapper">
            <div class="container">
                <div class="row">
                    <div class="col-12">
                        <div class="subContainer_pop">
                            <div class="sub_section">
                                <div class="sub_contents_header">
                                    <span>설문</span>
                                </div>
                                <div class="sub_contents_body">
<%	if (scoredata != null) { %>
                                    <div class="sub_info_body">
                                        <div class="sub_course_alert_box">
                                            <p><%= subjdata.getSubjnm() %></p>
                                        </div>
                                        <div class="sub_course_view_wrap" style="margin-bottom:50px;">
                                            <ul class="myCourseDetail"  style="width:100%;">
                                                <li>
                                                    <span>교육기간</span> : <%= FormatDate.getFormatDate(scoredata.getEdustart(),"yyyy.MM.dd") %> ~ <%= FormatDate.getFormatDate(scoredata.getEduend(),"yyyy.MM.dd") %>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
<%	} %>
                                    <div class="sub_boarder_body">
                                        <table>
                                            <colgroup>
                                                <col width="8%">
                                                <col width="12%">
                                                <col width="auto">
                                                <col width="12%">
                                                <col width="25%">
                                            </colgroup>
                                            <thead>
                                                <tr>
                                                    <th>번호</th>
                                                    <th>설문유형</th>
                                                    <th>제목</th>
                                                    <th>참여여부</th>
                                                    <th>현재진도/응시진도</th>
                                                </tr>
                                            </thead>
                                            <tbody>
<%

        for (int i=0; i<blist.size(); i++) {
            DataBox dbox = (DataBox)blist.get(i);   
    
				count ++; 
%>
                                                <tr>
                                                    <td><%= String.valueOf(count) %></td>
                                                    <td>과정설문</td>
                                                    <td>
<%
			if(dbox.getString("d_eachcnt").equals("0") && (Double.parseDouble(v_progress)>=Double.parseDouble(dbox.getString("d_progress"))) && dbox.getString("d_dispyn").equals("Y")){ %>
														<a href="javascript:contentsWright1('<%=dbox.getString("d_subjnm")%>', '<%=dbox.getString("d_subj")%>', '<%=dbox.getString("d_grcode")%>', '<%=dbox.getString("d_year")%>', '<%=dbox.getString("d_subjseq")%>', '<%=dbox.getInt("d_sulpapernum")%>', '<%=dbox.getString("d_sulpapernm")%>', '', '','<%=dbox.getString("d_sulnums")%>');"><%=dbox.getString("d_sulpapernm")%></a>  
<%			}else{%>
														<%= String.valueOf(dbox.getString("d_sulpapernm")) %>
<%			}%>
                                                    </td>
                                                    <td>
<%			if(dbox.getString("d_eachcnt").equals("0") && (Double.parseDouble(v_progress)>=Double.parseDouble(dbox.getString("d_progress")))){%>
<%				if(dbox.getString("d_dispyn").equals("Y")) { %>
														<a href="javascript:contentsWright1('<%=dbox.getString("d_subjnm")%>', '<%=dbox.getString("d_subj")%>', '<%=dbox.getString("d_grcode")%>', '<%=dbox.getString("d_year")%>', '<%=dbox.getString("d_subjseq")%>', '<%=dbox.getInt("d_sulpapernum")%>', '<%=dbox.getString("d_sulpapernm")%>', '', '','<%=dbox.getString("d_sulnums")%>');">참여하기</a>
<%				} else { %>
                      									설문기간아님
<%				} %>
<%			}else if(dbox.getString("d_eachcnt").equals("0") && (Double.parseDouble(v_progress)<Double.parseDouble(dbox.getString("d_progress")))){%>
<%				if(dbox.getString("d_dispyn").equals("Y")) { %>
                      									진도미달
<%				} else { %>
                      									설문기간아님
<%				} %>
<%			}else if(dbox.getString("d_eachcnt").equals("1")){%>
                      									참여완료
<%			}%>
                                                    </td>
                                                    <td><%= v_progress %> / <%= dbox.getString("d_progress") %></td>
                                                </tr>
<%    
        } 
%>

<%
        for (int i=0; i<blist1.size(); i++) {
            DataBox dbox1 = (DataBox)blist1.get(i);   

			count ++; 
    
%>
												<tr>
													<td><%= String.valueOf(count) %></td>
													<td>가입경로 설문</td>
													<td>
<%			if(box.getString("p_contentsdata").equals("0")){%>
														<a href="javascript:contentsWright()"><%=String.valueOf(dbox1.getString("d_sulpapernm"))%></a>
<%			}else{%>
					  									<%=String.valueOf(dbox1.getString("d_sulpapernm"))%>
<%			}%>
													</td>
													<td>
<%			if(box.getString("p_contentsdata").equals("0")){%>
														<a href="javascript:contentsWright()" class="board_btn2">참여하기</a>
<%			}else if(box.getString("p_contentsdata").equals("1")){%>
                      									참여완료
<%			}%>
													</td>
													<td></td>
												</tr>
<%
        }
%>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <div class="popup_btnBox">
                                    <a href="javascript:top.window.close();" class="btn_courseCancel">닫기</a>
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