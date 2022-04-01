package ipin;
import KISINFO.VNO.*;

public class Interop
{	
    public String Interop(String nc_info,String flag)
	{
    	String result = "";
    	String sConnInfo = "";
    	String sDupInfo	= "";
		String sSiteCode				= "B014";			// IPIN 서비스 사이트 코드		(NICE신용평가정보에서 발급한 사이트코드)
		String sSitePw					= "20727176";			// IPIN 서비스 사이트 패스워드	(NICE신용평가정보에서 발급한 사이트패스워드)
		
		
		/*
			사용자 정보 (주민등록번호 13자리 or 안심키값 13자리)
		*/
		String sJumin		= nc_info;
//		System.out.println("nc_info=" + nc_info);
		
		/*
		┌ sFlag 변수에 대한 설명  ─────────────────────────────────────────────────────
			실명확인 서비스 구분값.
			
			JID : 일반실명확인 서비스 (주민등록번호)
			SID : 안심실명확인 서비스 (안심키값)
			
			사용중인 실명확인 서비스에 따라서 정의해 주세요.
		└──────────────────────────────────────────────────────────────────
		*/
		String sFlag		= flag;
		
		
		// 중복(가입)확인모듈 객체 생성
		VNOInterop vnoInterop = new VNOInterop();
		
		
		
		/* ──── DI 값을 추출하기 위한 부분 Start */
		// Method 결과값(iRtn)에 따라, 프로세스 진행여부를 파악합니다.
	    int iRtnDI = vnoInterop.fnRequestDupInfo(sSiteCode, sSitePw, sJumin, sFlag);
//	    System.out.println("iRtnDI=" + iRtnDI);
	    
	    // Method 결과값에 따른 처리사항
	    if (iRtnDI == 1) {
	    	
	    	// iRtn 값이 정상이므로, 귀사의 기획의도에 맞게 진행하시면 됩니다.
	    	// 아래와 같이 getDupInfo 함수를 통해 DI 값(64 Byte)을 추출할 수 있습니다.
	    	sDupInfo = vnoInterop.getDupInfo();
//	    	System.out.println("DUPINFO=[" + sDupInfo + "]");
	    	
	    } else if (iRtnDI == 3) {
	    	System.out.println("[사용자 정보와 실명확인 서비스 구분값 매핑 오류]");
	    	System.out.println("사용자 정보와 실명확인 서비스 구분값이 서로 일치하도록 매핑하여 주시기 바랍니다.");
	    } else if (iRtnDI == -9) {
	    	System.out.println("[입력값 오류]");
	    	System.out.println("fnRequestDupInfo 함수 처리시, 필요한 4개의 파라미터값의 정보를 정확하게 입력해 주시기 바랍니다.");
	    } else if (iRtnDI == -21 || iRtnDI == -31 || iRtnDI == -34) {
	    	System.out.println("iRtnDI ==== >>> "+iRtnDI);
	    	System.out.println("[통신오류]");
	    	System.out.println("서버 네트웍크 및 방확벽 관련하여 아래 IP와 Port(5개)를 오픈해 주셔야 이용 가능합니다.");
	    	System.out.println("IP : 203.234.219.72 / Port : 81, 82, 83, 84, 85");
	    } else {
	    	System.out.println("iRtnDI 값 확인 후, NICE신용평가정보 개발 담당자에게 문의해 주세요.");
	    }
	    /* ──── DI 값을 추출하기 위한 부분 End */
	    
	    
	    /* ──── CI 값을 추출하기 위한 부분 Start */
		// Method 결과값(iRtn)에 따라, 프로세스 진행여부를 파악합니다.
	    int iRtnCI = vnoInterop.fnRequestConnInfo(sSiteCode, sSitePw, sJumin, sFlag);
//	    System.out.println("iRtnCI=" + iRtnCI);
	    
	    // Method 결과값에 따른 처리사항
	    if (iRtnCI == 1) {
	    	
	    	// iRtn 값이 정상이므로, 귀사의 기획의도에 맞게 진행하시면 됩니다.
	    	// 아래와 같이 getConnInfo 함수를 통해 CI 값(88 Byte)을 추출할 수 있습니다.
	    	sConnInfo = vnoInterop.getConnInfo();
//	    	System.out.println("CONNINFO=[" + sConnInfo + "]");
	    	
	    } else if (iRtnCI == 3) {
	    	System.out.println("[사용자 정보와 실명확인 서비스 구분값 매핑 오류]");
	    	System.out.println("사용자 정보와 실명확인 서비스 구분값이 서로 일치하도록 매핑하여 주시기 바랍니다.");
	    } else if (iRtnCI == -9) {
	    	System.out.println("[입력값 오류]");
	    	System.out.println("fnRequestConnInfo 함수 처리시, 필요한 4개의 파라미터값의 정보를 정확하게 입력해 주시기 바랍니다.");
	    } else if (iRtnCI == -21 || iRtnCI == -31 || iRtnCI == -34) {
	    	System.out.println("iRtnCI ==== >>> "+iRtnCI);
	    	System.out.println("[통신오류]");
	    	System.out.println("서버 네트웍크 및 방확벽 관련하여 아래 IP와 Port(5개)를 오픈해 주셔야 이용 가능합니다.");
	    	System.out.println("IP : 203.234.219.72 / Port : 81, 82, 83, 84, 85");
	    } else {
	    	System.out.println("iRtnCI 값 확인 후, NICE신용평가정보 개발 담당자에게 문의해 주세요.");
	    }
	    /* ──── CI 값을 추출하기 위한 부분 End */
	    
	    result = sDupInfo+";"+sConnInfo;
	    
	    return result;
	}
    
	public static void main(String[] args) {
		
	}

}