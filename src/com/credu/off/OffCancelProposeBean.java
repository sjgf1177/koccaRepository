//**********************************************************
//  1. 제	  목:  관리
//  2. 프로그램명 : Bean.java
//  3. 개	  요:  관리
//  4. 환	  경: JDK 1.5
//  5. 버	  젼: 1.0
//  6. 작	  성: __누구__ 2009. 10. 19
//  7. 수	  정: __누구__ 2009. 10. 19
//**********************************************************
package com.credu.off;

import java.util.ArrayList;
import java.util.List;

import com.credu.Bill.BillBean;
import com.credu.library.DBConnectionManager;
import com.credu.library.DataBox;
import com.credu.library.ErrorManager;
import com.credu.library.ListSet;
import com.credu.library.RequestBox;

/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

public class OffCancelProposeBean {

	public OffCancelProposeBean() {
	}

	/**
	 * 설명 :
	 * @param box
	 * @return
	 * @throws Exception
	 * @author swchoi
	 */
	@SuppressWarnings("unchecked")
	public List<DataBox> listPage(RequestBox box) throws Exception {
		DBConnectionManager connMgr = null;
		ListSet ls = null;
		StringBuffer sql = new StringBuffer();
		List<DataBox>  result = null;
		String t = null;
		DataBox payMethod = BillBean.getPayMethod();

		try {
			connMgr = new DBConnectionManager();

			sql.append("SELECT	/*	**오프라인과정|신청승인관리**	*/\n");
			sql.append("A.SUBJ, A.YEAR, A.SUBJSEQ, A.SEQ, C.SUBJNM, F.STUDENTNO, A.CANCELDATE,/*과정코드, 연도, 차수, 순번, 과정명, 학번, 취소일*/\n");
			sql.append("A.USERID, D.NAME, D.MEMBERGUBUN, E.CODENM MEMBERGUBUNNM,/*ID, 이름, 회원구분, 회원구분명*/\n");
			sql.append("nvl(A.CHKFIRST || A.CHKFINAL, 'UU') chkstatus, A.CHKFIRST, A.CHKFINAL, A.APPDATE, /*1차승인, 최종승인, 신청일*/\n");
			sql.append("A.CANCELKIND, A.CANCELDATE, A.CANCELREASON, A.REFUNDDATE, /*	취소구분, 취소신청일, 취소사유, 환불일	*/\n");
			sql.append("B.TID, DECODE(B.RESULTCODE, '00', '결제완료', '99', '결제대기', B.RESULTCODE) RESULTCODE, B.PGAUTHDATE, B.PAYMETHOD, B.PRICE/*결제번호, 결제상태, 결제일, 결제방법, 결제금액*/\n");
			sql.append("FROM TZ_OFFPROPOSE A, TZ_OFFBILLINFO B, TZ_OFFSUBJ C, TZ_MEMBER D, TZ_CODE E, TZ_OFFSTUDENT F\n");
			sql.append("WHERE 1=1\n");
			sql.append("AND 1 = DECODE(NVL(:s_subjcode, 'ALL'), A.SUBJ, 1, 'ALL', 1, 0)	\n");
			sql.append("AND 1 = DECODE(NVL(:s_upperclass, UPPERCLASS), UPPERCLASS, 1, 'ALL', 1, 0)	\n");
			sql.append("AND 1 = DECODE(NVL(:s_middleclass, MIDDLECLASS), MIDDLECLASS, 1, 'ALL', 1, 0)	\n");
			sql.append("AND 1 = DECODE(NVL(:s_lowerclass, LOWERCLASS), LOWERCLASS, 1, 'ALL', 1, 0)	\n");
			sql.append("AND 1 = DECODE(NVL(:s_subjseq, A.SUBJSEQ), A.SUBJSEQ, 1, 'ALL', 1, 0)	\n");
			sql.append("AND C.SUBJNM LIKE '%' || nvl(:s_subjsearchkey, C.SUBJNM) || '%'\n");
			sql.append("AND A.TID = B.TID(+)\n");
			sql.append("AND A.CANCELDATE IS NOT NULL\n");
			sql.append("AND A.SUBJ = C.SUBJ\n");
			sql.append("AND A.USERID = D.USERID\n");
			sql.append("AND D.MEMBERGUBUN = E.CODE\n");
			sql.append("AND E.GUBUN = '0029'\n");
			sql.append("AND A.SUBJ = F.SUBJ(+)\n");
			sql.append("AND A.YEAR = F.YEAR(+)\n");
			sql.append("AND A.SUBJSEQ = F.SUBJSEQ(+)\n");
			sql.append("AND A.USERID = F.USERID(+)\n");
			if (box.getString("p_orderColumn").length()>0) {
				box.put("p_orderType", box.getStringDefault("p_orderType", " asc"));
				sql.append("ORDER BY ");
				sql.append(box.getString("p_orderColumn"));
				sql.append(box.getString("p_orderType"));
			}
			t = connMgr.replaceParam(sql.toString(), box);
			ls = connMgr.executeQuery(t);
			result = new ArrayList<DataBox>();
			while(ls.next()) {
				DataBox entity = ls.getDataBox();
				entity.put("d_paymethod", payMethod.get(entity.getString("d_paymethod")));
				result.add(entity);
			}
			//			result = ls.getAllDataList();
		}
		catch (Exception ex) {
			ErrorManager.getErrorStackTrace(ex, box, t);
			throw new Exception("sql = " + t + "\r\n" + ex.getMessage());
		}
		finally {
			if(ls != null) {try {ls.close();} catch(Exception e){}}
			if(connMgr != null) { try { connMgr.freeConnection(); }catch (Exception e10) {} }
		}
		return result;
	}
}
