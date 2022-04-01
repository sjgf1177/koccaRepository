//**********************************************************
//1. 제      목: 가입경로 설문 코드정보
//2. 프로그램명: SulmunRegistBean.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: lyh
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
public class SulmunRegistBean {
    public final static String SPLIT_COMMA = ",";
    public final static String SPLIT_COLON = ":";

    public final static String DEFAULT_GRCODE = "N000001";
    public final static String DEFAULT_SUBJ   = "REGIST";

    public final static String DIST_CODE        = "ALL";
    public final static String SUL_TYPE         = "0011";

    public final static String OBJECT_QUESTION  = "1";
    public final static String MULTI_QUESTION   = "2";
    public final static String SUBJECT_QUESTION = "3";
	public final static String COMPLEX_QUESTION = "4";
    public final static String FSCALE_QUESTION = "5";
    public final static String SSCALE_QUESTION = "6";
    
    
    public final static String F_GUBUN        = "";
    public final static String SUBJECT_SULMUN  = "SUBJ";
    public final static String TARGET_SULMUN = "TARGET";
    public final static String COMMON_SULMUN  = "COMMON";
    public final static String CONTENTS_SULMUN   = "CONTENTS";
	public final static String REGIST_SULMUN   = "REGIST";
    public SulmunRegistBean() {}


}