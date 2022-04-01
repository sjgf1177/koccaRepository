
package com.credu.library;

import com.credu.complete.*;

/**
 * <p>����: ����ó�� ������ ���̺귯��</p>
 * <p>����: </p>
 * <p>Copyright: Copyright (c) 2004</p>
 * <p>Company: Credu </p>
 *@author ������
 *@date 2003. 12
 *@version 1.0
 */
public class CalcUtil {
	private static FinishBean  instance= null;
	
	public static int STEP     =  1;
	public static int EXAM     =  2;
	public static int REPORT   =  4;
	public static int ACTIVITY =  8;
	public static int ETC      = 16;
	public static int ALL      = 32;
	public static int EXC	   = 77;
	
	public static synchronized FinishBean getInstance() throws Exception {
		if (instance == null) {
			instance = new FinishBean();
		}
	    return instance;
	}
}
