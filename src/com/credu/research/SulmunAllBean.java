//**********************************************************
//1. ��      ��:
//2. ���α׷���: SulmunAllBean.java
//3. ��      ��:
//4. ȯ      ��: JDK 1.3
//5. ��      ��: 0.1
//6. ��      ��: Administrator 2003-08-18
//7. ��      ��:
//
//**********************************************************

package com.credu.research;

import javax.servlet.http.*;
import java.io.*;
import java.util.*;
import java.sql.*;
import com.credu.library.*;
import com.credu.system.*;


/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SulmunAllBean {
    public final static String SPLIT_COMMA = ",";
    public final static String SPLIT_COLON = ":";

    public final static String DEFAULT_GRCODE = "ALL";
    public final static String DEFAULT_SUBJ   = "ALL";

    public final static String DIST_CODE        = "0054";
    public final static String SUL_TYPE         = "0011";
    public final static String OBJECT_QUESTION  = "1";
    public final static String MULTI_QUESTION   = "2";
    public final static String SUBJECT_QUESTION = "3";
	public final static String COMPLEX_QUESTION = "4";
    public final static String FSCALE_QUESTION = "5";
    public final static String SSCALE_QUESTION = "6";
    
    
    public final static String F_GUBUN         = "";
    public final static String SUBJECT_SULMUN  = "SUBJ";
    public final static String TARGET_SULMUN   = "TARGET";
    public final static String COMMON_SULMUN   = "COMMON";
    public final static String CONTENTS_SULMUN = "REGIST";
    public final static String ALL_SULMUN      = "ALL";
    
    public SulmunAllBean() {}

}