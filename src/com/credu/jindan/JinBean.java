//**********************************************************
//1. ��      ��: �����׽�Ʈ
//2. ���α׷���: ExamBean.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: Administrator 2003-08-29
//7. ��      ��:
//
//**********************************************************

package com.credu.jindan;


/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class JinBean {
    public final static String SPLIT_COMMA = ",";
    public final static String SPLIT_COLON = ":";

    public final static String PTYPE        = "0062"; // ���ܺз�
    public final static String MIDDLE_EXAM  = "M";    // �߰�����
    public final static String FINAL_EXAM   = "T";    // ��������
    public final static String ONLINE_EXAM  = "O";    // �¶����׽�Ʈ

    public final static String JIN_TYPE            = "0063";   // ���ܹ����з�
    public final static String OBJECT_QUESTION      = "1";      // ������
    public final static String SUBJECT_QUESTION     = "2";      // �ְ���
    public final static String OX_QUESTION          = "3";      // OX�� ( add 2005.8.20 )
    public final static String MULTI_QUESTION       = "4";      // �ٴ��
    
    public final static String JIN_LEVEL   = "0064";   // �������̵�
    public final static String TOP          = "1";      // ��
    public final static String MIDDLE       = "2";      // ��
    public final static String LOW          = "3";      // ��

    public final static String PAPER_GUBUN  = "0065";   // ����������
    public final static String GENERAL      = "1";      // �Ϲ�����
    public final static String REALTIME     = "2";      // �ǽð�����

	//�ڵ� �̻���
    public final static String PAPER_CREATE = "0016";   // �������������
    public final static String AUTO         = "1";      // �ڵ�����
    public final static String BEFORE_PAPER = "2";      // ���������������°�
    public final static String MANUAL       = "3";      // ��������

    public JinBean() {}



}