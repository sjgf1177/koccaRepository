//**********************************************************
//  1. 제      목: SCO Locate Operation Data
//  2. 프로그램명: SCOLocateData.java
//  3. 개      요: SCO Locate관리에 관련된 Data Object
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 박미복 2004. 11.17
//  7. 수      정: 박미복 2004. 11.17
//**********************************************************

package com.credu.contents;

import java.util.*;
import com.credu.library.*;

public class SCOLocateData {

	private	int	sconumber       =0;
	
	public SCOLocateData() {};
	
	/**
	 * @return
	 */
	public int getSconumber() {
		return sconumber;
	}

	/**
	 * @param i
	 */
	public void setSconumber(int i) {
		sconumber = i;
	}

}