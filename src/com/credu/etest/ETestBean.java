//**********************************************************
//1. ��      ��: �¶����׽�Ʈ �׷����
//2. ���α׷���: ETestBean.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: Administrator 2003-08-29
//7. ��      ��:
//
//**********************************************************

package com.credu.etest;


/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class ETestBean {

    public final static String SPLIT_COMMA = ",";
    public final static String SPLIT_COLON = "-";
    
    public final static String ETEST_TYPE            = "0013"; 	// �򰡹����з�
    public final static String OBJECT_QUESTION  	= "1";  	// ������ 
    public final static String SUBJECT_QUESTION 	= "2";  	// �ְ���
    public final static String MULTI_QUESTION   	= "3";  	// �ٴ��
    public final static String STATEMENT_QUESTION   = "4";  	// �����
    
    public final static String ETEST_LEVEL	= "0014"; 	// �������̵�
    public final static String TOP     		= "1";  	// �� 
    public final static String MIDDLE 		= "2";  	// ��
    public final static String LOW      	= "3";      // ��  
    
    public final static String PAPER_GUBUN 	= "0015"; 	// ����������
    public final static String GENERAL    	= "1";  	// �Ϲ��� 
    public final static String REALTIME		= "2";  	// �ǽð���
      
    public final static String PAPER_CREATE	= "0016"; 	// �������������
    public final static String AUTO  		= "1";  	// �ڵ����� 
    public final static String BEFORE_PAPER = "2";  	// ���������������°�
    public final static String MANUAL		= "3";  	// ��������
    
    public final static String ETSUBJ_CLASS = "0018";   // �¶����׽�Ʈ �����з�
    public final static String ETEST_GUBUN 	= "0048"; 	// �����з� (2005.07.13 �߰�)
    public ETestBean() {}


}