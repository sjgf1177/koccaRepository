<%
//**********************************************************
//  1. 제      목: 수강료결제 관리
//  2. 프로그램명 : za_OffBillAdmin_L.jsp
//  3. 개      요: 행정서비스 - 수강료결제 관리
//  4. 환      경: JDK 1.5
//  5. 버      젼: 1.0
//  6. 작      성: 2009.12.18
//  7. 수      정:
//***********************************************************
%>
<%@ page contentType = "text/html;charset=euc-kr" %>
<%@ page errorPage = "/learn/library/error.jsp" %>
<%@ page import = "java.util.*" %>
<%@ page import = "java.text.*" %>
<%@ page import = "com.credu.system.*" %>
<%@ page import = "com.credu.common.*" %>
<%@ page import = "com.credu.library.*" %>
<%@ page import = "com.credu.library.PageUtil" %>
<%
    RequestBox box = null;
    box = (RequestBox)request.getAttribute("requestbox");
    if (box == null) {
        box = RequestManager.getBox(request);
    }

    int array_size=1;
    int  v_gubun  = box.getInt("p_gubun");
    String v_excel= box.getString("p_excel");
    String [] last_sum={"Y"};  // 출력을 다 하고 맨 마지막줄에 합계를 표시 하는지 여부 Y 표시 N 표시 안함
    String [] head_colums_code=new String[array_size];
    String [] head_colums_name=new String[array_size];
    String [] head_colums_align=new String[array_size];
    String [] head_colums_sum=new String[array_size]; //합계
    head_colums_code[0]= "d_subjnm,d_dt,d_betweendt,d_REALPAYMENT,d_man_count,d_woman_count,d_a1,d_a2,d_a3,d_a4,d_a5,d_a6";
    head_colums_name[0]= "과정명,교육일정,기간,총수입,성별(남),성별(여),연령(10),연령(20),연령(30),연령(40),연령(50),연령(60)";
    head_colums_align[0]="1,2,2,3,2,2,2,2,2,2,2,2"; // 1:left 2:center 3:right
    head_colums_sum[0]="N,N,N,Y,Y,Y,Y,Y,Y,Y,Y,Y"; // Y:집계 N:집계안함
    if(v_excel.equals("Y"))
    {
        response.setContentType("application/vnd.ms-excel") ;       
        response.setHeader("Content-Disposition", "inlinet; filename=excel.xls");
        response.setHeader("Content-Disposition", "JSP Generated Date");            
    }
%>


<head>
<meta http-equiv="Content-Type" content="text/html; charset=euc-kr">
<link rel="stylesheet" href="/css/admin_style.css" type="text/css">
<link rel="stylesheet" type="text/css" href="/css/ui-lightness/ui.all.css" />
<script type='text/javascript' src='/script/jquery-1.3.2.min.js'></script>
<script type='text/javascript' src='/script/jquery.form.js'></script>
<script language="JavaScript">
    $(document).ready(function(){
        $("[name='p_excel']").val("N");
    });
</script>
</head>

<table width="1000" border="0" cellspacing="0" cellpadding="0" height="663">
    <tr>
        <td align="center" valign="top">
            <table cellpadding="0" cellspacing="0"  class="table_out" border="1px solid #cdcdcd">
                <thead>
                    <tr>
                        <%
                            String [] colums_name=head_colums_name[v_gubun].split(",");
                            out.print("<td  class='table_title'>No</td>");

                            for(int i=0;i<colums_name.length;i++)
                            {
                                out.print("<td class='table_title'>");
                                out.print(colums_name[i]);
                                out.print("</td>");
                            }
                        %>
                    </tr>
                </thead>
                <tbody>
                       <%
                            String [] colums_code=head_colums_code[v_gubun].split(",");
                            String [] colums_align=head_colums_align[v_gubun].split(",");
                            String [] colums_sum=head_colums_sum[v_gubun].split(",");
                            int [] colums_sum_val=new int[colums_sum.length];
                           
                            ArrayList list		= null;
                            list = (ArrayList)request.getAttribute("resultList");
                            if(list.size()!=0)
                            {
                                for(int i=0;i<list.size();i++)
                                {
                                    DataBox data  = (DataBox)list.get(i);
                                    out.print("<tr>");
                                    out.print("<td class='table_01' align='center'>"+(i+1)+"</td>");
                                    for(int j=0;j<colums_code.length;j++)
                                    {
                                            String align="style='text-align:";
                                            switch(colums_align[j].charAt(0))
                                            {
                                                case '1':
                                                    align+="left'";
                                                    break;
                                                case '2':
                                                    align+="center'";
                                                    break;
                                                case '3':
                                                    align+="right'";
                                                    break;
                                            }

                                            if(colums_sum[j].equals("Y"))
                                            {
                                                colums_sum_val[j]+=data.getInt(colums_code[j]);
                                            }

                                            out.print("<td class='table_01' "+align+">");
                                            out.print(data.getString(colums_code[j]));

                                            out.print("</td>");
                                        }
                                        out.print("</tr>");
                                    }
                                }
                                else
                                    out.print("<tr><td class='table_01' colspan='"+(colums_code.length+1)+"'>검색된 결과가 없습니다.</td></tr>");
                            %>
                    </tbody>
                    <%  //합계 구하는 루틴
                        String [] sum=last_sum[v_gubun].split(",");
                        if(sum[v_gubun].equals("Y") && list.size()!=0)
                        {
                            int colspan=0; //colspan 할 개수를 구함    1은 위에 No를 포함 하기 위해 1로 한다.
                            DecimalFormat df = new DecimalFormat("#,###");
                            
                            for(int i=0;i<colums_sum.length;i++)
                            {
                                if(colums_sum[i].equals("N"))
                                    colspan++;
                            }
                            out.print("<tr>");
                            out.print("<td colspan='"+(colspan+1)+"' align='center'>합계</td>");
                            for(int i=colspan;i<colums_sum.length;i++)
                            {
                                out.print("<td align='right'>");
                                out.print((String)df.format(colums_sum_val[i]));
                                out.print("</td>");
                            }
                            out.print("</tr>");
                        }
                    %>
                </table>
            </td>
        </tr>
    </table>
