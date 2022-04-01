//**********************************************************
//  1. 제      목: Poll 지문 Data
//  2. 프로그램명 : PollSelData.java
//  3. 개      요: Poll 지문 data bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7.  2
//  7. 수      정:
//**********************************************************
package com.credu.homepage;
import com.credu.library.*;

/**
 * file name : PollSelData.java
 * date      : 2003/7/13
 * programmer: 정상진 (puggjsj@hanmail.net)
 * function  : Poll 지문 DATA
 */

public class PollSelData
{

    private String seltxt;            // 보기내용

    private int seq;                // 폴일련번호
    private int selnum;             // 문제보기일련번호
    private int cnt;                // 투표수
	private int total;                // 전체투표수
	private int maxcnt;                // 최다투표수
	

    public PollSelData() {}

    public void   setSeltxt(String seltxt) { this.seltxt = seltxt; }
    public String getSeltxt()              { return seltxt;        }

    public void   setSeq(int seq)       { this.seq = seq;       }
    public int    getSeq()              { return seq;           }
    public void   setSelnum(int selnum) { this.selnum = selnum; }
    public int    getSelnum()           { return selnum;        }
    public void   setCnt(int cnt)       { this.cnt = cnt;       }
    public int    getCnt()              { return cnt;           }
    public void   setMaxcnt(int maxcnt) { this.maxcnt = maxcnt; }
    public int    getMaxcnt()           { return maxcnt;        }
    public void   setTotal(int total)   { this.total = total;   }
    public int    getTotal()            { return total;       	}


}
