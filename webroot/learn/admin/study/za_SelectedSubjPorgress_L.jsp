<%@ page contentType = "text/html;charset=MS949" %>
<!----------------- �԰�������ȸ ���� ----------------->
      <table cellspacing="1" cellpadding="5" class="table_out">
          <tr>
            <td colspan="12" class="table_top_line"></td>
          </tr>
          <tr>
            <td class="table_title">No</td>
            <td class="table_title"><a href="javascript:whenOrder('grseq')" class="e">��������</a></td>
            <td class="table_title" colspan="2"><a href="javascript:whenOrder('subj')" class="e">����</a></td>
            <td class="table_title" width="4%"><a href="javascript:whenOrder('subjseq')" class="e">����<br>����</a></td>
            <!--td class="table_title"><a href="javascript:whenOrder('isonoff')" class="e">����</td-->
            <td class="table_title">��û�Ⱓ</td>
            <td class="table_title">�����Ⱓ</td>
            <td class="table_title">����</td>
            <td class="table_title">�ѽ�û�ο�</td>
            <td class="table_title">��û����ο�</td>
            <td class="table_title">�н������ο�</td>
          </tr>
<%
if (ss_action.equals("go")) {    //go button ���ýø� list ���
                v_total = list.size();

	if (list.size() != 0 ) {	//������ �ִٸ�

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
				v_subjseqgr      = data.getSubjseqgr();
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
				v_isbelongcourse = data.getIsbelongcourse();	//��������������
				v_subjcnt = data.getSubjcnt();					//���� ������


				v_totalstulimit += v_studentlimit;
				v_personcnt     = v_procnt ;    //������û�ο�(��û��+�����)

				//if(v_isonoff.equals("ON")){     v_isonoff_value="���̹�";   }
				//else                      {     v_isonoff_value="����";     }

				if(!v_edustart.equals("")&&!v_eduend.equals("")){
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
				}

				//�������ڰ� �н��Ⱓ�϶���	 ���					
				//if( v_today >= v_edustart_value && v_today < v_eduend_value ){

					// �������������ο� ���� �޸� ���
					if(v_isbelongcourse.equals("Y")){		//�����������϶� - ������ course�� �Ͽ� �� �ȿ��� for������ ���� �������

%>
                        <tr>
                          <td class="table_01"><%= data.getDispnum() %></td>
                          <td class="table_02_1" rowspan="<%=v_subjcnt%>"><%=v_grseqnm%></td>
				          <td class="table_02_2" rowspan="<%=v_subjcnt%>"><font class="text_color04"><%=v_coursenm%></font></td>
                          <td class="table_02_2" ><%=v_subjnm%></td>
                          <td class="table_02_1"><%=StringManager.cutZero(v_subjseqgr)%></td>
                          <!--td class="table_02_1"><%//= v_isonoff_value%></td-->
                          <td class="table_02_1"><%= v_propstart %>~<%= v_propend %></td>
                          <td class="table_02_1"><%= v_edustart %>~<%= v_eduend %></td>
                          <td class="table_02_1"><%= v_studentcnt %></td>
                          <td class="table_02_1"><%= v_personcnt %></td>
                          <td class="table_02_1"><%= v_cancnt %></td>
                          <td class="table_02_1"><%= v_stucnt %></td>
                        </tr>
<%
                        v_totalprocnt=v_totalprocnt+v_personcnt;          //�Ѽ�����û�ο�
                        v_totalcancnt=v_totalcancnt+v_cancnt;             //��������ο�
                        v_totalproycnt=v_totalproycnt+v_proycnt;          //�н������ο�
                        v_totalstucnt=v_totalstucnt+v_stucnt;             //�н������ο�
                        v_totalcomcnt=v_totalcomcnt+v_comcnt;             //�н��Ϸ��ο�

                        v_count = v_count+1;

						//�������� ���� ������ �����ִ� ������ ���.
						for( int j = 0;  j < (v_subjcnt -1 ); j++ ){
							i++;
							data  = (StudentStatusData)list.get(i);

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
							v_subjseqgr      = data.getSubjseqgr();
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
							v_isbelongcourse = data.getIsbelongcourse();	//��������������
							v_subjcnt = data.getSubjcnt();					//���� ������


							v_totalstulimit += v_studentlimit;
							v_personcnt     = v_procnt ;    //������û�ο�(��û��+�����)

							if(v_isonoff.equals("ON")){     v_isonoff_value="���̹�";   }
							else                      {     v_isonoff_value="����";     }

							if(!v_edustart.equals("")&&!v_eduend.equals("")){
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
							}
%>
                        <tr>
                          <td class="table_01"><%= data.getDispnum() %></td>
                          <td class="table_02_2" ><%=v_subjnm%></td>
                          <td class="table_02_1"><%=StringManager.cutZero(v_subjseqgr)%></td>
                          <!--td class="table_02_1"><%= v_isonoff_value%></td-->
                          <td class="table_02_1"><%= v_propstart %>~<%= v_propend %></td>
                          <td class="table_02_1"><%= v_edustart %>~<%= v_eduend %></td>
                          <td class="table_02_1"><%= v_studentcnt %></td>
                          <td class="table_02_1"><%= v_personcnt %></td>
                          <td class="table_02_1"><%= v_cancnt %></td>
                          <td class="table_02_1"><%= v_stucnt %></td>
                        </tr>
<%
	                    v_totalprocnt=v_totalprocnt+v_personcnt;            //�Ѽ�����û�ο�
                        v_totalcancnt=v_totalcancnt+v_cancnt;             //��������ο�
                        v_totalproycnt=v_totalproycnt+v_proycnt;          //�н������ο�
                        v_totalstucnt=v_totalstucnt+v_stucnt;             //�н������ο�
                        v_totalcomcnt=v_totalcomcnt+v_comcnt;             //�н��Ϸ��ο�

					}

		}else{	// �Ϲݰ����̸�
%>
                        <tr>
                          <td class="table_01"><%= data.getDispnum() %></td>
                          <td class="table_02_1"><%=v_grseqnm%></td>
                          <td class="table_02_2" colspan="2"><font class="text_color04"><%=v_subjnm%></font></td>
                          <td class="table_02_1"><%=StringManager.cutZero(v_subjseqgr)%></td>
                          <!--td class="table_02_1"><%= v_isonoff_value%></td-->
                          <td class="table_02_1"><%= v_propstart %>~<%= v_propend %></td>
                          <td class="table_02_1"><%= v_edustart %>~<%= v_eduend %></td>
                          <td class="table_02_1"><%= v_studentcnt %></td>
                          <td class="table_02_1"><%= v_personcnt %></td>
                          <td class="table_02_1"><%= v_cancnt %></td>
                          <td class="table_02_1"><%= v_stucnt %></td>
                        </tr>
<%
                        v_totalprocnt=v_totalprocnt+v_personcnt;            //�Ѽ�����û�ο�
                        v_totalcancnt=v_totalcancnt+v_cancnt;             //��������ο�
                        v_totalproycnt=v_totalproycnt+v_proycnt;          //�н������ο�
                        v_totalstucnt=v_totalstucnt+v_stucnt;             //�н������ο�
                        v_totalcomcnt=v_totalcomcnt+v_comcnt;             //�н��Ϸ��ο�

		}

	}//for��
%>

                <tr>
                   <td class="table_02_1" colspan="7">��</td>
                   <td class="table_02_1"><%= v_totalcnt %></td>
                   <td class="table_02_1"><%= v_totalprocnt %></td>
                   <td class="table_02_1"><%= v_totalcancnt %></td>
                   <td class="table_02_1"><%= v_totalstucnt %></td>

                </tr>
<%
	}else{		//������ ���ٸ�
%> 
              <tr>
                <td align="center" bgcolor="#F7F7F7" height="50" colspan="12">��ϵ� ������ �����ϴ�</td>
              </tr>
<%
	}
}
%>
      </table>
      <!----------------- �԰����� ��ȸ �� ----------------->