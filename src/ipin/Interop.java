package ipin;
import KISINFO.VNO.*;

public class Interop
{	
    public String Interop(String nc_info,String flag)
	{
    	String result = "";
    	String sConnInfo = "";
    	String sDupInfo	= "";
		String sSiteCode				= "B014";			// IPIN ���� ����Ʈ �ڵ�		(NICE�ſ����������� �߱��� ����Ʈ�ڵ�)
		String sSitePw					= "20727176";			// IPIN ���� ����Ʈ �н�����	(NICE�ſ����������� �߱��� ����Ʈ�н�����)
		
		
		/*
			����� ���� (�ֹε�Ϲ�ȣ 13�ڸ� or �Ƚ�Ű�� 13�ڸ�)
		*/
		String sJumin		= nc_info;
//		System.out.println("nc_info=" + nc_info);
		
		/*
		�� sFlag ������ ���� ����  ����������������������������������������������������������������������������������������������������������
			�Ǹ�Ȯ�� ���� ���а�.
			
			JID : �ϹݽǸ�Ȯ�� ���� (�ֹε�Ϲ�ȣ)
			SID : �ȽɽǸ�Ȯ�� ���� (�Ƚ�Ű��)
			
			������� �Ǹ�Ȯ�� ���񽺿� ���� ������ �ּ���.
		��������������������������������������������������������������������������������������������������������������������������������������
		*/
		String sFlag		= flag;
		
		
		// �ߺ�(����)Ȯ�θ�� ��ü ����
		VNOInterop vnoInterop = new VNOInterop();
		
		
		
		/* �������� DI ���� �����ϱ� ���� �κ� Start */
		// Method �����(iRtn)�� ����, ���μ��� ���࿩�θ� �ľ��մϴ�.
	    int iRtnDI = vnoInterop.fnRequestDupInfo(sSiteCode, sSitePw, sJumin, sFlag);
//	    System.out.println("iRtnDI=" + iRtnDI);
	    
	    // Method ������� ���� ó������
	    if (iRtnDI == 1) {
	    	
	    	// iRtn ���� �����̹Ƿ�, �ͻ��� ��ȹ�ǵ��� �°� �����Ͻø� �˴ϴ�.
	    	// �Ʒ��� ���� getDupInfo �Լ��� ���� DI ��(64 Byte)�� ������ �� �ֽ��ϴ�.
	    	sDupInfo = vnoInterop.getDupInfo();
//	    	System.out.println("DUPINFO=[" + sDupInfo + "]");
	    	
	    } else if (iRtnDI == 3) {
	    	System.out.println("[����� ������ �Ǹ�Ȯ�� ���� ���а� ���� ����]");
	    	System.out.println("����� ������ �Ǹ�Ȯ�� ���� ���а��� ���� ��ġ�ϵ��� �����Ͽ� �ֽñ� �ٶ��ϴ�.");
	    } else if (iRtnDI == -9) {
	    	System.out.println("[�Է°� ����]");
	    	System.out.println("fnRequestDupInfo �Լ� ó����, �ʿ��� 4���� �Ķ���Ͱ��� ������ ��Ȯ�ϰ� �Է��� �ֽñ� �ٶ��ϴ�.");
	    } else if (iRtnDI == -21 || iRtnDI == -31 || iRtnDI == -34) {
	    	System.out.println("iRtnDI ==== >>> "+iRtnDI);
	    	System.out.println("[��ſ���]");
	    	System.out.println("���� ��Ʈ��ũ �� ��Ȯ�� �����Ͽ� �Ʒ� IP�� Port(5��)�� ������ �ּž� �̿� �����մϴ�.");
	    	System.out.println("IP : 203.234.219.72 / Port : 81, 82, 83, 84, 85");
	    } else {
	    	System.out.println("iRtnDI �� Ȯ�� ��, NICE�ſ������� ���� ����ڿ��� ������ �ּ���.");
	    }
	    /* �������� DI ���� �����ϱ� ���� �κ� End */
	    
	    
	    /* �������� CI ���� �����ϱ� ���� �κ� Start */
		// Method �����(iRtn)�� ����, ���μ��� ���࿩�θ� �ľ��մϴ�.
	    int iRtnCI = vnoInterop.fnRequestConnInfo(sSiteCode, sSitePw, sJumin, sFlag);
//	    System.out.println("iRtnCI=" + iRtnCI);
	    
	    // Method ������� ���� ó������
	    if (iRtnCI == 1) {
	    	
	    	// iRtn ���� �����̹Ƿ�, �ͻ��� ��ȹ�ǵ��� �°� �����Ͻø� �˴ϴ�.
	    	// �Ʒ��� ���� getConnInfo �Լ��� ���� CI ��(88 Byte)�� ������ �� �ֽ��ϴ�.
	    	sConnInfo = vnoInterop.getConnInfo();
//	    	System.out.println("CONNINFO=[" + sConnInfo + "]");
	    	
	    } else if (iRtnCI == 3) {
	    	System.out.println("[����� ������ �Ǹ�Ȯ�� ���� ���а� ���� ����]");
	    	System.out.println("����� ������ �Ǹ�Ȯ�� ���� ���а��� ���� ��ġ�ϵ��� �����Ͽ� �ֽñ� �ٶ��ϴ�.");
	    } else if (iRtnCI == -9) {
	    	System.out.println("[�Է°� ����]");
	    	System.out.println("fnRequestConnInfo �Լ� ó����, �ʿ��� 4���� �Ķ���Ͱ��� ������ ��Ȯ�ϰ� �Է��� �ֽñ� �ٶ��ϴ�.");
	    } else if (iRtnCI == -21 || iRtnCI == -31 || iRtnCI == -34) {
	    	System.out.println("iRtnCI ==== >>> "+iRtnCI);
	    	System.out.println("[��ſ���]");
	    	System.out.println("���� ��Ʈ��ũ �� ��Ȯ�� �����Ͽ� �Ʒ� IP�� Port(5��)�� ������ �ּž� �̿� �����մϴ�.");
	    	System.out.println("IP : 203.234.219.72 / Port : 81, 82, 83, 84, 85");
	    } else {
	    	System.out.println("iRtnCI �� Ȯ�� ��, NICE�ſ������� ���� ����ڿ��� ������ �ּ���.");
	    }
	    /* �������� CI ���� �����ϱ� ���� �κ� End */
	    
	    result = sDupInfo+";"+sConnInfo;
	    
	    return result;
	}
    
	public static void main(String[] args) {
		
	}

}