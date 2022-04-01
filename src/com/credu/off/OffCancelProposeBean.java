//**********************************************************
//  1. ��	  ��:  ����
//  2. ���α׷��� : Bean.java
//  3. ��	  ��:  ����
//  4. ȯ	  ��: JDK 1.5
//  5. ��	  ��: 1.0
//  6. ��	  ��: __����__ 2009. 10. 19
//  7. ��	  ��: __����__ 2009. 10. 19
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
	 * ���� :
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

			sql.append("SELECT	/*	**�������ΰ���|��û���ΰ���**	*/\n");
			sql.append("A.SUBJ, A.YEAR, A.SUBJSEQ, A.SEQ, C.SUBJNM, F.STUDENTNO, A.CANCELDATE,/*�����ڵ�, ����, ����, ����, ������, �й�, �����*/\n");
			sql.append("A.USERID, D.NAME, D.MEMBERGUBUN, E.CODENM MEMBERGUBUNNM,/*ID, �̸�, ȸ������, ȸ�����и�*/\n");
			sql.append("nvl(A.CHKFIRST || A.CHKFINAL, 'UU') chkstatus, A.CHKFIRST, A.CHKFINAL, A.APPDATE, /*1������, ��������, ��û��*/\n");
			sql.append("A.CANCELKIND, A.CANCELDATE, A.CANCELREASON, A.REFUNDDATE, /*	��ұ���, ��ҽ�û��, ��һ���, ȯ����	*/\n");
			sql.append("B.TID, DECODE(B.RESULTCODE, '00', '�����Ϸ�', '99', '�������', B.RESULTCODE) RESULTCODE, B.PGAUTHDATE, B.PAYMETHOD, B.PRICE/*������ȣ, ��������, ������, �������, �����ݾ�*/\n");
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
