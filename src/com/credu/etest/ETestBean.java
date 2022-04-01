//**********************************************************
//1. 제      목: 온라인테스트 그룹관리
//2. 프로그램명: ETestBean.java
//3. 개      요:
//4. 환      경: JDK 1.3
//5. 버      젼: 0.1
//6. 작      성: Administrator 2003-08-29
//7. 수      정:
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
    
    public final static String ETEST_TYPE            = "0013"; 	// 평가문제분류
    public final static String OBJECT_QUESTION  	= "1";  	// 객관식 
    public final static String SUBJECT_QUESTION 	= "2";  	// 주관식
    public final static String MULTI_QUESTION   	= "3";  	// 다답식
    public final static String STATEMENT_QUESTION   = "4";  	// 논술식
    
    public final static String ETEST_LEVEL	= "0014"; 	// 문제난이도
    public final static String TOP     		= "1";  	// 상 
    public final static String MIDDLE 		= "2";  	// 중
    public final static String LOW      	= "3";      // 하  
    
    public final static String PAPER_GUBUN 	= "0015"; 	// 문제지유형
    public final static String GENERAL    	= "1";  	// 일반평가 
    public final static String REALTIME		= "2";  	// 실시간평가
      
    public final static String PAPER_CREATE	= "0016"; 	// 시험지생성방법
    public final static String AUTO  		= "1";  	// 자동생성 
    public final static String BEFORE_PAPER = "2";  	// 기존차수문제지승계
    public final static String MANUAL		= "3";  	// 수동생성
    
    public final static String ETSUBJ_CLASS = "0018";   // 온라인테스트 과정분류
    public final static String ETEST_GUBUN 	= "0048"; 	// 문제분류 (2005.07.13 추가)
    public ETestBean() {}


}