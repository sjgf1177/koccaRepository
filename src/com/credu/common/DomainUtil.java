package com.credu.common;

public class DomainUtil {

	/**
	 * url�� https �������� ��ȯȯ��.
	 * @param url
	 * @return
	 */
	public static String getHttpsDomain(String url){
		if(null == url) {
			return "";
		}
		url = url.toLowerCase().replaceAll("https://", "").replaceAll("http://", "").split(":")[0] ;
		
		
		if((url.indexOf("edukocca.or.kr") > -1) ||(url.indexOf("kocca.or.kr") > -1)) {
			return "http://"+url;
		} else {
			return "http://"+url;
		}
	}
	
	/**
	 * url�� http �������� ��ȯ�Ѵ�.
	 * @param url
	 * @return
	 */
	public static String getHttpDomain(String url){
		if(null == url) {
			return "";
		}
		url = url.toLowerCase().replaceAll("https://", "").replaceAll("http://", "").split(":")[0] ;
		
		return "http://"+url;

	}

}
