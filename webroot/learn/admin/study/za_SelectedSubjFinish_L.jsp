<%@ page contentType = "text/html;charset=MS949" %>
<!----------------- 입과명단조회 시작 ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
          <tr>
            <td colspan="16" class="table_top_line"></td>
          </tr>
          <tr>
            <td class="table_title">No</td>
            <td class="table_title"><a href="javascript:whenOrder('grseq')" class="e">교육차수</a></td>
            <td class="table_title"><a href="javascript:whenOrder('subj')" class="e">과정</a></td>
            <td class="table_title" width="4%"><a href="javascript:whenOrder('subjseq')" class="e">과정<br>차수</a></td>
            <!--td class="table_title"><a href="javascript:whenOrder('isonoff')" class="e">구분</td-->
            <td class="table_title">신청기간</td>
            <td class="table_title">교육기간</td>
            <td class="table_title">학습완료</td>
            <td class="table_title">정원</td>
            <td class="table_title">총신청인원</td>
            <td class="table_title">신청취소<br>인원</td>
            <td class="table_title">미수료<br>인원</td>
            <td class="table_title">수료<br>인원</td>
            <!--td class="table_title">구분</td-->
            <!--td class="table_title">선택</td-->
          </tr>
          <%
            if (ss_action.equals("go")) {    //go button 선택시만 list 출력
                v_total = list.size();
                for(i = 0; i < v_total; i++) {
                    StudentStatusData data  = (StudentStatusData)list.get(i);
                    v_grseq         = data.getGrseq();
                    v_grseqnm       = data.getGrseqnm();
                    v_course        = data.getCourse();
                    v_cyear         = data.getCyear();
                    v_courseseq     = data.getCourseseq();
                    v_coursenm      = data.getCoursenm();
                    v_subj          = data.getSubj();
                    v_year          = data.getYear();
                    v_subjnm        = data.getSubjnm();
                    v_subjseq       = data.getSubjseq();
                    v_subjseqgr     = data.getSubjseqgr();
                    v_propstart     = data.getPropstart();
                    v_propend       = data.getPropend();
                    v_edustart      = data.getEdustart();
                    v_eduend        = data.getEduend();
                    v_studentlimit  = data.getStudentlimit();
                    v_procnt        = data.getProcnt();
                    v_stucnt        = data.getStucnt();
                    v_comcnt        = data.getComcnt();
                    v_cancnt        = data.getCancnt();
                    v_isnewcourse   = data.getIsnewcourse();
                    v_rowspan       = data.getRowspan();
                    v_totalpage     = data.getTotalPageCount();
                    v_rowcount      = data.getRowCount();
                    v_isonoff       = data.getIsonoff();


                    v_totalstulimit += v_studentlimit;

                    //out.println("v_eduend="+v_eduend);
                    //if(v_isonoff.equals("ON")){     v_isonoff_value="사이버";   }
                    //else                      {     v_isonoff_value="집합";     }

                    v_personcnt     = v_procnt + v_cancnt;    //수강신청인원(신청자+취소자)

                    if(!v_eduend.equals("")&&v_eduend.length() == 10){

                        v_today = Integer.parseInt(FormatDate.getDate("yyyyMMddHH"));
                        v_edustart_value = Integer.parseInt(v_edustart);
                        v_eduend_value   = Integer.parseInt(v_eduend);
                        v_propstart      = FormatDate.getFormatDate(v_propstart,"yyyy/MM/dd");
                        v_propend        = FormatDate.getFormatDate(v_propend,"yyyy/MM/dd");
                        v_edustart       = FormatDate.getFormatDate(v_edustart,"yyyy/MM/dd");
                        v_eduend         = FormatDate.getFormatDate(v_eduend,"yyyy/MM/dd");
												
                    		if(v_studentlimit == 0) {
                    			v_studentcnt = "-";
                    		} else {
                    			v_studentcnt = String.valueOf(v_studentlimit);
                    		}
                    		
                    		if(v_totalstulimit == 0) {
                    			v_totalcnt = "-";
                    		} else {
                    			v_totalcnt = String.valueOf(v_totalstulimit);
                    		}
												
                        if(v_today>v_eduend_value){
                            v_dday = FormatDate.datediff("date",data.getEduend(),FormatDate.getDate("yyyyMMddHH"));
%>
                        <tr>
                          <td class="table_01"><%= data.getDispnum() %></td>
                          <td class="table_02_1"><%=v_grseqnm%></td>
                          <td class="table_02_2" ><font class="text_color04"><%=v_subjnm%></font></td>
                          <td class="table_02_1"><%=StringManager.cutZero(v_subjseqgr)%></td>
                          <!--td class="table_02_1"><%//=v_isonoff_value%></td-->
                          <td class="table_02_1"><%= v_propstart %>~<%= v_propend %></td>
                          <td class="table_02_1"><%= v_edustart %>~<%= v_eduend %></td>
                          <td class="table_02_1">D+<%=v_dday%> </td>
                          <td class="table_02_1"><%= v_studentcnt %></td>
                          <td class="table_02_1"><%= v_personcnt %></td>
                          <td class="table_02_1"><%= v_cancnt %></td>
                          <td class="table_02_1"><%= v_stucnt %></td>
                          <td class="table_02_1"><%= v_comcnt %></td>
                          <!--td class="table_02_1"></td-->
                          <!--td class="table_02_1">
                            <input type="checkbox" name="p_checks" value="<%=v_subj%>,<%=v_subjseq%>,<%=v_year%>">
                          </td-->
                        </tr>
                        <%
                        v_totalprocnt =v_totalprocnt+v_personcnt;            //총수강신청인원
                        v_totalcancnt =v_totalcancnt+v_cancnt;             //수강취소인원
                        v_totalproycnt=v_totalproycnt+v_proycnt;          //학습예정인원
			            v_totalstucnt =v_totalstucnt+v_stucnt;             //학습진행인원
			            v_totalcomcnt =v_totalcomcnt+v_comcnt;             //학습완료인원

                        v_count = v_count+1;
                        }
                    }
                }


                if(v_count != 0){
                %>
                <tr>
                   <td class="table_02_1" colspan="7">계</td>
                   <td class="table_02_1"><%= v_totalcnt %></td>
                   <td class="table_02_1"><%= v_totalprocnt %></td>
                   <td class="table_02_1"><%= v_totalcancnt %></td>
                   <td class="table_02_1"><%= v_totalstucnt %></td>
                   <td class="table_02_1"><%= v_totalcomcnt %></td>
                   <!--td class="table_02_1"></td-->
                   <!--td class="table_02_1"></td-->
                </tr>
                <%
                 }
                 ///else{%>
                 <!--tr>
                   <td align="center" bgcolor="#F7F7F7" height="50" colspan="12">현재 학습종료한 과정이 없습니다.</td>
                 </tr-->
                 <%
                 ///}
            }
            if(i == 0 && ss_action.equals("go")){ %>
              <tr>
                <td align="center" bgcolor="#F7F7F7" height="50" colspan="17">등록된 내용이 없습니다</td>
              </tr>
            <%
            }
            %>
      </table>
      <!----------------- 입과명단 조회 끝 ----------------->