//**********************************************************
//1. 제      목:
//2. 프로그램명: SoBean.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: 정은년 2005.8.22
//7. 수      정:
//
//**********************************************************

package com.credu.so;


/**
 * @author Administrator
 *
 * To change the template for this generated type comment go to
 * Window>Preferences>Java>Code Generation>Code and Comments
 */
public class SoBean {
    public final static String SPLIT_COMMA = ",";
    public final static String SPLIT_COLON = ":";

    public final static String PTYPE        = "0012"; // 평가분류
    public final static String MIDDLE_EXAM  = "M";    // 중간평가
    public final static String FINAL_EXAM   = "T";    // 최종평가
    public final static String ONLINE_EXAM  = "O";    // 온라인테스트


    public final static String OBJECT_QUESTION      = "1";      // 객관식
    public final static String SUBJECT_QUESTION     = "2";      // 주관식
    public final static String OX_QUESTION          = "3";      // OX식 ( add 2005.8.20 )
    public final static String MULTI_QUESTION       = "4";      // 다답식
    

    public final static String TOP          = "1";      // 상
    public final static String MIDDLE       = "2";      // 중
    public final static String LOW          = "3";      // 하

    public final static String PAPER_GUBUN  = "0015";   // 문제지유형
    public final static String GENERAL      = "1";      // 일반평가
    public final static String REALTIME     = "2";      // 실시간평가

    public final static String PAPER_CREATE = "0016";   // 시험지생성방법
    public final static String AUTO         = "1";      // 자동생성
    public final static String BEFORE_PAPER = "2";      // 기존차수문제지승계
    public final static String MANUAL       = "3";      // 수동생성



    // 추가할 부분 //////////////////////////////////////////////////
    public final static String SOEXAM_GUBUN = "1";      // SO평가구분
    
    public final static String EXAM_TYPE    = "0013";   // 평가문제분류
    public final static String EXAM_LEVEL   = "0014";   // 문제난이도    
    
    public final static String SULMUN_TYPE    = "0058";   // 설문문제분류
    

    public final static String SOSULMUN_GUBUN = "2";      // SO설문 구분
    
    public final static String SODIAGNOS_GUBUN = "3";      // 진단구분
        
    public final static String SOEVENT_GUBUN = "4";      // 이벤트구분
    
    public final static String SODIAGNOSEXAM_GUBUN = "5";      // 진단구분
            
    public SoBean() {}



}
