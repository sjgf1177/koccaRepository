package com.credu.common;

public class DomainUtil {

	/**
	 * url의 https 도메인을 반환환다.
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
	 * url의 http 도메인을 반환한다.
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
