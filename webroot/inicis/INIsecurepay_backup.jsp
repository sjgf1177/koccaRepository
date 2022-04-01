<body bgcolor="#FFFFFF" text="#242424" leftmargin=0 topmargin=15 marginwidth=0 marginheight=0 bottommargin=0 rightmargin=0><center> 
<table width="632" border="0" cellspacing="0" cellpadding="0">
  <tr> 
    <td height="85" background=<% 

/*------------------------------------------------------------------------------------------------------*
 * 결제 방법에 따라 상단 이미지가 변경 된다								*
 * 	 가. 결제 실패 시에 "img/spool_top.gif" 이미지 사용						*
 *       가. 결제 방법에 따라 상단 이미지가 변경							*
 *       	ㄱ. 신용카드 	- 	"img/card.gif"							*
 *		ㄴ. ISP		-	"img/card.gif"							*
 *		ㄷ. 은행계좌	-	"img/bank.gif"							*
 *		ㄹ. 무통장입금	-	"img/bank.gif"							*
 *		ㅁ. 핸드폰	- 	"img/hpp.gif"							*
 *		ㅂ. 전화결제 (ars전화 결제)	-	"img/phone.gif"					*
 *		ㅅ. 전화결제 (받는전화결제)	-	"img/phone.gif"					*
 *		ㅇ. OK CASH BAG POINT		-	"img/okcash.gif"				*
 *		ㅈ. 문화상품권	-	"img/ticket.gif"						*
 *------------------------------------------------------------------------------------------------------*/
    					
    				if(resultCode.equals("01")){
					out.println("img/spool_top.gif");
				}
				else{
    					if(payMethod.equals("Card")){
    						out.println("img/card.gif");
    					}
    					else if(payMethod.equals("VCard")){ // ISP
						out.println("img/card.gif");
					}
    					else if(payMethod.equals("HPP")){ // 휴대폰
						out.println("img/hpp.gif");
					}
    					else if(payMethod.equals("Ars1588Bill")){ // 1588
						out.println("img/phone.gif");
					}
    					else if(payMethod.equals("PhoneBill")){ // 폰빌
						out.println("img/phone.gif");
					}
    					else if(payMethod.equals("OCBPoint")){ // OKCASHBAG
						out.println("img/okcash.gif");
					}
    					else if(payMethod.equals("DirectBank")){  // 은행계좌이체
						out.println("img/bank.gif");
					}
    					else if(payMethod.equals("VBank")){  // 무통장 입금 서비스
						out.println("img/bank.gif");
					}
    					else if(payMethod.equals("Culture")){  // 문화상품권 결제
						out.println("img/ticket.gif");
					}
						else if(payMethod.equals("TEEN")){  // 틴캐쉬 결제
						out.println("img/teen_top.gif");
					}
					else{
						out.println("img/card.gif");
					}// 기타 지불수단의 경우
				}
					
    				%> style="padding:0 0 0 64">
    				
<!-------------------------------------------------------------------------------------------------------
 *													*
 *  아래 부분은 모든 결제수단의 공통적인 결과메세지 출력 부분입니다.					*
 *  													*
 *	1. resultCode 	(결 과 코 드) 									*
 *  	2. resultMsg	(결과 메세지)									*
 *  	3. payMethod	(결 제 수 단)									*
 *  	4. tid		(거 래 번 호)									*
 *  	5. moid  	(주 문 번 호)									*
 -------------------------------------------------------------------------------------------------------->
 
      <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr> 
          <td width="3%" valign="top"><img src="img/title_01.gif" width="8" height="27" vspace="5"></td>
          <td width="97%" height="40" class="pl_03"><font color="#FFFFFF"><b>결제결과</b></font></td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td align="center" bgcolor="6095BC">
      <table width="620" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td bgcolor="#FFFFFF" style="padding:0 0 0 56">
		  <table width="510" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="7"><img src="img/life.gif" width="7" height="30"></td>
                <td background="img/center.gif"><img src="img/icon03.gif" width="12" height="10">
                
                <!-------------------------------------------------------------------------------------------------------
                 * 1. resultCode 											*	
                 *       가. 결 과 코 드: "00" 인 경우 결제 성공[무통장입금인 경우 - 고객님의 무통장입금 요청이 완료]	*
                 *       나. 결 과 코 드: "00"외의 값인 경우 결제 실패  						*
                 --------------------------------------------------------------------------------------------------------> 
                  <b><% if(resultCode.equals("00") && payMethod.equals("VBank")){ out.println("고객님의 무통장입금 요청이 완료되었습니다.");}
                  	   else if(resultCode.equals("00")){ out.println("고객님의 결제요청이 성공되었습니다.");}
                           else{ out.println("고객님의 결제요청이 실패되었습니다.");} %> </b></td>
                <td width="8"><img src="img/right.gif" width="8" height="30"></td>
              </tr>
            </table>
            <br>
            <table width="510" border="0" cellspacing="0" cellpadding="0">
              <tr> 
                <td width="407"  style="padding:0 0 0 9"><img src="img/icon.gif" width="10" height="11"> 
                  <strong><font color="433F37">결제내역</font></strong></td>
                <td width="103">&nbsp;</td>                
              </tr>
              <tr> 
                <td colspan="2"  style="padding:0 0 0 23">
		  <table width="470" border="0" cellspacing="0" cellpadding="0">
                    <tr>
                    
                <!-------------------------------------------------------------------------------------------------------
                 * 2. m_payMethod 										*	
                 *       가. 결제 방법에 대한 값									*
                 *       	ㄱ. 신용카드 	- 	Card								*
                 *		ㄴ. ISP		-	VCard								*	
                 *		ㄷ. 은행계좌	-	DirectBank							*
                 *		ㄹ. 무통장입금	-	VBank								*
                 *		ㅁ. 핸드폰	- 	HPP								*
                 *		ㅂ. 전화결제 (ars전화 결제)	-	Ars1588Bill					*
                 *		ㅅ. 전화결제 (받는전화결제)	-	PhoneBill					*
                 *		ㅇ. OK CASH BAG POINT		-	OCBPoint					*
                 *		ㅈ. 문화상품권	-	Culture								*
                 -------------------------------------------------------------------------------------------------------->
                      <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                      <td width="109" height="25">결 제 방 법</td>
                      <td width="343"><%=payMethod%></td>
                    </tr>
                    <tr> 
                      <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    </tr>
                    <tr> 
                      <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                      <td width="109" height="26">결 과 코 드</td>
                      <td width="343"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                          <tr> 
                            <td><%=resultCode%></td>
                            <td width="142" align="right">
                          
                <!-------------------------------------------------------------------------------------------------------
                 * 2. $inipay->m_resultCode 값에 따라 "영수증 보기" 또는 "실패 내역 자세히 보기" 버튼 출력		*
                 *       가. 결제 코드의 값이 "00"인 경우에는 "영수증 보기" 버튼 출력					*
                 *       나. 결제 코드의 값이 "00" 외의 값인 경우에는 "실패 내역 자세히 보기" 버튼 출력			*
                 -------------------------------------------------------------------------------------------------------->
		<!-- 실패결과 상세 내역 버튼 출력 -->
                            	<%
                            		if(resultCode.equals("00")){
                				out.println("<a href='javascript:show_receipt();'><img src='img/button_02.gif' width='94' height='24' border='0'></a>");
                			}
                			else{
                            			out.println("<a href='javascript:errhelp();'><img src='img/button_01.gif' width='142' height='24' border='0'></a>");
                            		}
                                                    	
                            	%>                    </td>
                          </tr>
                        </table></td>
                    </tr>
                
                <!-------------------------------------------------------------------------------------------------------
                 * 1. resultMsg 											*
                 *       가. 결과 내용을 보여 준다 실패시에는 "[에러코드] 실패 메세지" 형태로 보여 준다.                *
                 *		예> [9121]서명확인오류									*
                 -------------------------------------------------------------------------------------------------------->
                    <tr> 
                      <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    </tr>
                    <tr> 
                      <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                      <td width="109" height="25">결 과 내 용</td>
                      <td width="343"><%=resultMsg%></td>
                    </tr>
                    <tr> 
                      <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    </tr>
                    
                <!-------------------------------------------------------------------------------------------------------
                 * 1. $inipay->m_tid											*
                 *       가. 이니시스가 부여한 거래 번호 -모든 거래를 구분할 수 있는 키가 되는 값			*
                 -------------------------------------------------------------------------------------------------------->
                    <tr> 
                      <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                      <td width="109" height="25">거 래 번 호</td>
                      <td width="343"><%=tid%></td>
                    </tr>
                    <tr> 
                      <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    </tr>
                    
                <!-------------------------------------------------------------------------------------------------------
                 * 1. $inipay->m_moid											*
                 *       가. 상점에서 할당한 주문번호 									*
                 -------------------------------------------------------------------------------------------------------->
                    <tr> 
                      <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                      <td width="109" height="25">주 문 번 호</td>
                      <td width="343"><%=moid%></td>
                    </tr>
                    <tr> 
                      <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    </tr>


<%                    
                    

	/*-------------------------------------------------------------------------------------------------------
	 *													*
	 *  아래 부분은 결제 수단별 결과 메세지 출력 부분입니다.    						*	
	 *													*
	 *  1.  신용카드 , ISP 결제 결과 출력 (OK CASH BAG POINT 복합 결제 내역 )				*
	 -------------------------------------------------------------------------------------------------------*/

	if(payMethod.equals("Card") || payMethod.equals("VCard")){
%>
		  		<tr>
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">신용카드번호</td>
                    		  <td width="343"><%=cardNumber%>****</td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
				<tr> 
                                  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                                  <td width="109" height="25">승 인 날 짜</td>
                                  <td width="343"><%=pgAuthDate%></td>
                                </tr>
                                <tr> 
                                  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                                </tr>
                                <tr> 
                                  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                                  <td width="109" height="25">승 인 시 각</td>
                                  <td width="343"><%=pgAuthTime%></td>
                                </tr>                	    
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">승 인 번 호</td>
                    		  <td width="343"><%=authCode%></td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">할 부 기 간</td>
                    		  <td width="343"><%=cardQuota%>개월&nbsp;<b><font color="red"><%=interest%></font></b></td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">카 드 종 류</td>
                    		  <td width="343"><%=cardCode%></td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">카드발급사</td>
                    		  <td width="343"><%=cardIssuerCode%></td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3">&nbsp;</td>
                    		</tr>
                    		<tr> 
                		  <td style="padding:0 0 0 9" colspan="3"><img src="img/icon.gif" width="10" height="11"> 
        	          	  <strong><font color="433F37">OK CASHBAG 적립 및 사용내역</font></strong></td>
                		</tr>
                		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">카 드 번 호</td>
                    		  <td width="343"><%=ocbcardnumber%></td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">적립 승인번호</td>
                    		  <td width="343"><%=ocbSaveAuthCode%></td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">사용 승인번호</td>
                    		  <td width="343"><%=ocbUseAuthCode%></td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">승 인 일 시</td>
                    		  <td width="343"><%=ocbAuthDate%></td>
                    		</tr>
                		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">포인트지불금액</td>
                    		  <td width="343"><%=price2%></td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
<%
                    
          }
        
        /*-------------------------------------------------------------------------------------------------------
	 *													*
	 *  아래 부분은 결제 수단별 결과 메세지 출력 부분입니다.    						*	
	 *													*
	 *  2.  은행계좌결제 결과 출력 										*
	 -------------------------------------------------------------------------------------------------------*/
	 
          else if(payMethod.equals("DirectBank")){
%>          	
          			
          			<tr> 
                                  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                                  <td width="109" height="25">승 인 날 짜</td>
                                  <td width="343"><%=pgAuthDate%></td>
                                </tr>
                                <tr> 
                                  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                                </tr>
                                <tr> 
                                  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                                  <td width="109" height="25">승 인 시 각</td>
                                  <td width="343"><%=pgAuthTime%></td>
                                </tr>
                                <tr> 
                                  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                                </tr>

<%                            
          }
          
        /*-------------------------------------------------------------------------------------------------------
	 *													*
	 *  아래 부분은 결제 수단별 결과 메세지 출력 부분입니다.    						*	
	 *													*
	 *  3.  무통장입금 입금 예정 결과 출력 (결제 성공이 아닌 입금 예정 성공 유무)				*
	 -------------------------------------------------------------------------------------------------------*/
	 
          else if(payMethod.equals("VBank")){
 
%>         	
          	
          			<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">입금계좌번호</td>
                    		  <td width="343"><%=vacct%></td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">입금 은행코드</td>
                    		  <td width="343"><%=vcdbank%></td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">예금주 명</td>
                    		  <td width="343"><%=nmvacct%></td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">송금자 명</td>
                    		  <td width="343"><%=nminput%></td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">송금자 주민번호</td>
                    		  <td width="343"><%=perno%></td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">상품 주문번호</td>
                    		  <td width="343"><%=moid%></td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">입금 예정일자</td>
                    		  <td width="343"><%=dtinput%></td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
<%                    		
          }
          
        /*-------------------------------------------------------------------------------------------------------
	 *													*
	 *  아래 부분은 결제 수단별 결과 메세지 출력 부분입니다.    						*	
	 *													*
	 *  4.  핸드폰 결제 											*
	 -------------------------------------------------------------------------------------------------------*/
	 
          else if(payMethod.equals("HPP")){
  
%>        	
          			
          			<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">휴대폰번호</td>
                    		  <td width="343"><%=nohpp%></td>
                    		</tr>
                    		<tr> 
                                  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                                </tr>
                    		<tr> 
                                  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                                  <td width="109" height="25">승 인 날 짜</td>
                                  <td width="343"><%=pgAuthDate%></td>
                                </tr>
                                <tr> 
                                  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                                </tr>
                                <tr> 
                                  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                                  <td width="109" height="25">승 인 시 각</td>
                                  <td width="343"><%=pgAuthTime%></td>
                                </tr>
				<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
<%                    		
          }
          
        /*-------------------------------------------------------------------------------------------------------
	 *													*
	 *  아래 부분은 결제 수단별 결과 메세지 출력 부분입니다.    						*	
	 *													*
	 *  4.  전화 결제 											*
	 -------------------------------------------------------------------------------------------------------*/
	 
         else if(payMethod.equals("Ars1588Bill") || payMethod.equals("PhoneBill")){

%>                    	
  	
                		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">전 화 번 호</td>
                    		  <td width="343"><%=noars%></td>
                    		</tr>
                    		<tr> 
                                  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                                </tr>
                		<tr> 
                                  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                                  <td width="109" height="25">승 인 날 짜</td>
                                  <td width="343"><%=pgAuthDate%></td>
                                </tr>
                                <tr> 
                                  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                                </tr>
                                <tr> 
                                  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                                  <td width="109" height="25">승 인 시 각</td>
                                  <td width="343"><%=pgAuthTime%></td>
                                </tr>
                		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>

<%                    	
         }
         
        /*-------------------------------------------------------------------------------------------------------
	 *													*
	 *  아래 부분은 결제 수단별 결과 메세지 출력 부분입니다.    						*	
	 *													*
	 *  4.  OK CASH BAG POINT 적립 및 지불 									*
	 -------------------------------------------------------------------------------------------------------*/
	 
         else if(payMethod.equals("OCBPoint")){
         	
%>

                		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">카 드 번 호</td>
                    		  <td width="343"><%=ocbcardnumber%></td>
                    		</tr>
                    		<tr> 
                                  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                                </tr>
                		<tr> 
                                  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                                  <td width="109" height="25">승 인 날 짜</td>
                                  <td width="343"><%=pgAuthDate%></td>
                                </tr>
                                <tr> 
                                  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                                </tr>
                                <tr> 
                                  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                                  <td width="109" height="25">승 인 시 각</td>
                                  <td width="343"><%=pgAuthTime%></td>
                                </tr>
                                <tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">적립 승인번호</td>
                    		  <td width="343"><%=ocbSaveAuthCode%></td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">사용 승인번호</td>
                    		  <td width="343"><%=ocbUseAuthCode%></td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">승 인 일 시</td>
                    		  <td width="343"><%=ocbAuthDate%></td>
                    		</tr>
                		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		<tr> 
                    		  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                    		  <td width="109" height="25">포인트지불금액</td>
                    		  <td width="343"><%=price2%></td>
                    		</tr>
                    		<tr> 
                    		  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                    		</tr>
                    		
<%                    		
         }
         
        /*-------------------------------------------------------------------------------------------------------
	 *													*
	 *  아래 부분은 결제 수단별 결과 메세지 출력 부분입니다.    						*	
	 *													*
	 *  4.  문화 상품권						                			*
	 -------------------------------------------------------------------------------------------------------*/
	 
         else if(payMethod.equals("Culture")){
         	
%>

                		<tr> 
                                  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                                  <td width="109" height="25">컬쳐랜드 ID</td>
                                  <td width="343"><%=cultureid%></td>
                                </tr>
                                <tr> 
                                  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                                </tr>

		<%                                
         }
         
		/*-------------------------------------------------------------------------------------------------------
	 	*  5.틴캐쉬
	 	-------------------------------------------------------------------------------------------------------*/
         else if(payMethod.equals("TEEN")){
		%>
                		<tr> 
                                  <td width="18" align="center"><img src="img/icon02.gif" width="7" height="7"></td>
                                  <td width="109" height="25">틴캐쉬 결과 잔액</td>
                                  <td width="343"><%=RemainPrice%></td>
                                </tr>
                                <tr> 
                                  <td height="1" colspan="3" align="center"  background="img/line.gif"></td>
                                </tr>

		<%                                
         }
		%>


                  </table></td>
              </tr>
            </table>
            <br>
            
<!-------------------------------------------------------------------------------------------------------
 *													*
 *  결제 성공시($inipay->m_resultCode == "00"인 경우 ) "이용안내"  보여주기 부분입니다.			*	    
 *  결제 수단별로 이용고객에게 결제 수단에 대한 주의 사항을 보여 줍니다. 				*
 *  switch , case문 형태로 결제 수단별로 출력 하고 있습니다.						*
 *  아래 순서로 출력 합니다.										*
 *													*
 *  1.	신용카드 											*
 *  2.  ISP 결제 											*
 *  3.  핸드폰 												*
 *  4.  전화 결제 (1588Bill)										*
 *  5.  전화 결제 (PhoneBill)										*
 *  6.	OK CASH BAG POINT										*
 *  7.  은행계좌이체											*
 *  8.  무통장 입금 서비스										*
 *  9.  문화상품권 결제											*	
 ------------------------------------------------------------------------------------------------------->
 
<%
            	
            	if(resultCode.equals("00")){
            		
            		        /*--------------------------------------------------------------------------------------------------------
	 			*													*
	 			* 결제 성공시 이용안내 보여주기 			    						*	
				*													*
	 			*  1.  신용카드 						                			*
	 			--------------------------------------------------------------------------------------------------------*/
	
				if(payMethod.equals("Card")){ 
%>
					    <table width="510" border="0' cellspacing='0' cellpadding='0'>
         					<tr> 
         					    <td height='25'  style='padding:0 0 0 9'><img src='img/icon.gif' width='10' height='11'> 
         					      <strong><font color='433F37'>이용안내</font></strong></td>
         					  </tr>
         					  <tr> 
         					    <td  style='padding:0 0 0 23'> 
         					      <table width='470' border='0' cellspacing='0' cellpadding='0'>
         					        <tr>          					          
         					          <td height='25'>(1) 신용카드 청구서에 <b>"이니시스(inicis.com)"</b>으로 표기됩니다.<br>
         					          (2) LG카드 및 BC카드의 경우 <b>"이니시스(이용 상점명)"</b>으로 표기되고, 삼성카드의 경우 <b>"이니시스(이용상점 URL)"</b>로 표기됩니다.</td>
         					        </tr>
         					        <tr> 
         					          <td height='1' colspan='2' align='center'  background='img/line.gif'></td>
         					        </tr>
         					        
         					      </table></td>
         					  </tr>
         				      </table>
<%         				      
				}
				
			       /*--------------------------------------------------------------------------------------------------------
	 			*													*
	 			* 결제 성공시 이용안내 보여주기 			    						*	
				*													*
	 			*  2.  ISP 						                				*
	 			--------------------------------------------------------------------------------------------------------*/
	 			
				else if(payMethod.equals("VCard")){
%>				
					     <table width='510' border='0' cellspacing='0' cellpadding='0'>
         					<tr> 
         					    <td height='25'  style='padding:0 0 0 9'><img src='img/icon.gif' width='10' height='11'> 
         					      <strong><font color='433F37'>이용안내</font></strong></td>
         					  </tr>
         					  <tr> 
         					    <td  style='padding:0 0 0 23'> 
         					      <table width='470' border='0' cellspacing='0' cellpadding='0'>
         					        <tr>          					          
         					          <td height='25'>(1) 신용카드 청구서에 <b>"이니시스(inicis.com)"</b>으로 표기됩니다.<br>
         					          (2) LG카드 및 BC카드의 경우 <b>"이니시스(이용 상점명)"</b>으로 표기되고, 삼성카드의 경우 <b>"이니시스(이용상점 URL)"</b>로 표기됩니다.</td>
         					        </tr>
         					        <tr> 
         					          <td height='1' colspan='2' align='center'  background='img/line.gif'></td>
         					        </tr>
         					        
         					      </table></td>
         					  </tr>
         				      </table>
<%         				      
				}
					
			       /*--------------------------------------------------------------------------------------------------------
	 			*													*
	 			* 결제 성공시 이용안내 보여주기 			    						*	
				*													*
	 			*  3. 핸드폰 						                				*
	 			--------------------------------------------------------------------------------------------------------*/
	 			
				else if(payMethod.equals("HPP")){
%>				
					     <table width="510" border="0" cellspacing="0" cellpadding="0">
         					<tr> 
         					    <td height="25"  style="padding:0 0 0 9"><img src="img/icon.gif" width="10" height="11"> 
         					      <strong><font color="433F37">이용안내</font></strong></td>
         					  </tr>
         					  <tr> 
         					    <td  style="padding:0 0 0 23"> 
         					      <table width="470" border="0" cellspacing="0" cellpadding="0">
         					        <tr>          					          
         					          <td height="25">(1) 핸드폰 청구서에 <b>"소액결제"</b> 또는 <b>"외부정보이용료"</b>로 청구됩니다.<br>
         					          (2) 본인의 월 한도금액을 확인하시고자 할 경우 각 이동통신사의 고객센터를 이용해주십시오.
         					          </td>
         					        </tr>
         					        <tr> 
         					          <td height="1" colspan="2" align="center"  background="img/line.gif"></td>
         					        </tr>
         					        
         					      </table></td>
         					  </tr>
         				      </table>
<%         				      
				}
								
			       /*--------------------------------------------------------------------------------------------------------
	 			*													*
	 			* 결제 성공시 이용안내 보여주기 			    						*	
				*													*
	 			*  4. 전화 결제 (ARS1588Bill)				                				*
	 			--------------------------------------------------------------------------------------------------------*/
	 			
				else if(payMethod.equals("Ars1588Bill")){ 
%>				
					     <table width="510" border="0" cellspacing="0" cellpadding="0">
         					<tr> 
         					    <td height="25"  style="padding:0 0 0 9"><img src="img/icon.gif" width="10" height="11"> 
         					      <strong><font color="433F37">이용안내</font></strong></td>
         					  </tr>
         					  <tr> 
         					    <td  style="padding:0 0 0 23"> 
         					      <table width="470" border="0" cellspacing="0" cellpadding="0">
         					        <tr>          					          
         					          <td height="25">(1) 전화 청구서에 <b>"콘텐츠 이용료"</b>로 청구됩니다.<br>
                                                          (2) 월 한도금액의 경우 동일한 가입자의 경우 등록된 전화번호 기준이 아닌 주민등록번호를 기준으로 책정되어 있습니다.<br>
                                                          (3) 전화 결제취소는 당월에만 가능합니다.
         					          </td>
         					        </tr>
         					        <tr> 
         					          <td height="1" colspan="2" align="center"  background="img/line.gif"></td>
         					        </tr>
         					        
         					      </table></td>
         					  </tr>
         				      </table>
<%         				      
				}
					
			       /*--------------------------------------------------------------------------------------------------------
	 			*													*
	 			* 결제 성공시 이용안내 보여주기 			    						*	
				*													*
	 			*  5. 폰빌 결제 (PhoneBill)				                				*
	 			--------------------------------------------------------------------------------------------------------*/
				
				else if(payMethod.equals("PhoneBill")){
%>				
					      <table width="510" border="0" cellspacing="0" cellpadding="0">
         					<tr> 
         					    <td height="25"  style="padding:0 0 0 9"><img src="img/icon.gif" width="10" height="11"> 
         					      <strong><font color="433F37">이용안내</font></strong></td>
         					  </tr>
         					  <tr> 
         					    <td  style="padding:0 0 0 23"> 
         					      <table width="470" border="0" cellspacing="0" cellpadding="0">
         					        <tr>          					          
         					          <td height="25">(1) 전화 청구서에 <b>"인터넷 콘텐츠 (음성)정보이용료"</b>로 청구됩니다.<br>
                                                          (2) 월 한도금액의 경우 동일한 가입자의 경우 등록된 전화번호 기준이 아닌 주민등록번호를 기준으로 책정되어 있습니다.<br>
                                                          (3) 전화 결제취소는 당월에만 가능합니다.
         					          </td>
         					        </tr>
         					        <tr> 
         					          <td height="1" colspan="2" align="center"  background="img/line.gif"></td>
         					        </tr>
         					        
         					      </table></td>
         					  </tr>
         				      </table>
<%
				}
				
			       /*--------------------------------------------------------------------------------------------------------
	 			*													*
	 			* 결제 성공시 이용안내 보여주기 			    						*	
				*													*
	 			*  6. OK CASH BAG POINT					                				*
	 			--------------------------------------------------------------------------------------------------------*/
	 			
				else if(payMethod.equals("OCBPoint")){ 
%>				
					      <table width="510" border="0" cellspacing="0" cellpadding="0">
         					<tr> 
         					    <td height="25"  style="padding:0 0 0 9"><img src="img/icon.gif" width="10" height="11"> 
         					      <strong><font color="433F37">이용안내</font></strong></td>
         					  </tr>
         					  <tr> 
         					    <td  style="padding:0 0 0 23"> 
         					      <table width="470" border="0" cellspacing="0" cellpadding="0">
         					        <tr>          					          
         					          <td height="25">(1) OK CASH BAG 포인트 결제취소는 당월에만 가능합니다.
         					          </td>
         					        </tr>
         					        <tr> 
         					          <td height="1" colspan="2" align="center"  background="img/line.gif"></td>
         					        </tr>
         					        
         					      </table></td>
         					  </tr>
         				      </table>
<%         				      
				}
					
			       /*--------------------------------------------------------------------------------------------------------
	 			*													*
	 			* 결제 성공시 이용안내 보여주기 			    						*	
				*													*
	 			*  7. 은행계좌이체					                				*
	 			--------------------------------------------------------------------------------------------------------*/
	 			
				else if(payMethod.equals("DirectBank")){  
%>				
					     <table width="510" border="0" cellspacing="0" cellpadding="0">
         					<tr> 
         					    <td height="25"  style="padding:0 0 0 9"><img src="img/icon.gif" width="10" height="11"> 
         					      <strong><font color="433F37">이용안내</font></strong></td>
         					  </tr>
         					  <tr> 
         					    <td  style="padding:0 0 0 23"> 
         					      <table width="470" border="0" cellspacing="0" cellpadding="0">
         					        <tr>          					          
         					          <td height="25">(1) 고객님의 통장에는 <b>"이니시스"</b>로 표기됩니다.<br>
         					          </td>
         					        </tr>
         					        <tr> 
         					          <td height="1" colspan="2" align="center"  background="img/line.gif"></td>
         					        </tr>
         					        
         					      </table></td>
         					  </tr>
         				      </table>
<%         				      
				}
					
			       /*--------------------------------------------------------------------------------------------------------
	 			*													*
	 			* 결제 성공시 이용안내 보여주기 			    						*	
				*													*
	 			*  8. 무통장 입금 서비스					                				*
	 			--------------------------------------------------------------------------------------------------------*/		
				
				else if(payMethod.equals("VBank")){  
%>
                                              <table width="510" border="0" cellspacing="0" cellpadding="0">
         					<tr> 
         					    <td height="25"  style="padding:0 0 0 9"><img src="img/icon.gif" width="10" height="11"> 
         					      <strong><font color="433F37">이용안내</font></strong></td>
         					  </tr>
         					  <tr> 
         					    <td  style="padding:0 0 0 23"> 
         					      <table width="470" border="0" cellspacing="0" cellpadding="0">
         					        <tr>          					          
         					          (1) 상기 결과는 입금예약이 완료된 것일뿐 실제 입금완료가 이루어진 것이 아닙니다.<br>
         					          (2) 상기 입금계좌로 해당 상품금액을 무통장입금(창구입금)하시거나, 인터넷 뱅킹 등을 통한 온라인 송금을 하시기 바랍니다.<br>
                                                          (3) 반드시 입금기한 내에 입금하시기 바라며, 대금입금시 반드시 주문하신 금액만 입금하시기 바랍니다.
                                                          </td>
         					        </tr>
         					        <tr> 
         					          <td height="1" colspan="2" align="center"  background="img/line.gif"></td>
         					        </tr>
         					        
         					      </table></td>
         					  </tr>
         				      </table>
<%         				      
				}
					
			       /*--------------------------------------------------------------------------------------------------------
	 			*													*
	 			* 결제 성공시 이용안내 보여주기 			    						*	
				*													*
	 			*  9. 문화상품권 결제					                				*
	 			--------------------------------------------------------------------------------------------------------*/
	 			
				else if(payMethod.equals("Culture")){
%>                                         
                                             <table width="510" border="0" cellspacing="0" cellpadding="0">
         					<tr> 
         					    <td height="25"  style="padding:0 0 0 9"><img src="img/icon.gif" width="10" height="11"> 
         					      <strong><font color="433F37">이용안내</font></strong></td>
         					  </tr>
         					  <tr> 
         					    <td  style="padding:0 0 0 23"> 
         					      <table width="470" border="0" cellspacing="0" cellpadding="0">
         					        <tr>          					          
         					          <td height="25">(1) 문화상품권을 온라인에서 이용하신 경우 오프라인에서는 사용하실 수 없습니다.<br>
         					                          (2) 컬쳐캐쉬 잔액이 남아있는 경우, 고객님의 컬쳐캐쉬 잔액을 다시 사용하시려면 컬쳐랜드 ID를 기억하시기 바랍니다.
         					          </td>
         					        </tr>
         					        <tr> 
         					          <td height="1" colspan="2" align="center"  background="img/line.gif"></td>
         					        </tr>
         					        
         					      </table></td>
         					  </tr>
         				      </table>
<%         				      
				}
			
		}
		
%>		
            
            <!-- 이용안내 끝 -->
            
          </td>
        </tr>
      </table></td>
  </tr>
  <tr> 
    <td><img src="img/bottom01.gif" width="632" height="13"></td>
  </tr>
  <tr> 
    <td width=10></td>
  </tr>
  
  <tr> 
    <td> <a href="javascript:window.close();">[창 닫기]</a> <a href="javascript:myclass();">[수강신청 과정으로 이동]</a> 
    <% if (!resultCode.equals("00")) { %>
    	<a href="javascript:bill_retry();">[다시 결제하기]</a>
    <% } %>
    </td>
  </tr>
</table>
<form name="fbill" method="post" action="/inicis/INIsecurepayform.jsp">
	<input type="hidden" name="p_subjcd" value="<%=v_subjcd%>">
	<input type="hidden" name="p_term"   value="<%=v_term%>">
	<input type="hidden" name="p_process"   value="<%=v_process%>">
</form>
</center></body>
</html>

