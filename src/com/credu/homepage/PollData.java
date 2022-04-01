//**********************************************************
//  1. 제      목: Poll Data
//  2. 프로그램명 : PollData.java
//  3. 개      요: Poll data bean
//  4. 환      경: JDK 1.3
//  5. 버      젼: 1.0
//  6. 작      성: 정상진 2003. 7.  2
//  7. 수      정:
//**********************************************************
package com.credu.homepage;
import com.credu.library.*;

/**
 * file name : PollData.java
 * date      : 2003/5/28
 * programmer: 정상진 (puggjsj@hanmail.net)
 * function  : Poll DATA
 */

public class PollData
{
	
    private String title;           // 타이틀
    private String started;         // 시작일
    private String ended;           // 등록일
    private String f_use;           // 사용여부
    private String luserid;         // 최종수정자
    private String ldate;           // 최종수정일

    private int seq;                // 폴일련번호
    private int total;              // 총응답수


    public PollData() {}

    public void   setTitle(String title)       { this.title = title;     }
    public String getTitle()                   { return title;           }
    public void   setStarted(String started)   { this.started = started; }
    public String getStarted()                 { return started;         }
    public void   setEnded(String ended)       { this.ended = ended;     }
    public String getEnded()                   { return ended;           }
    public void   setF_use(String f_use)       { this.f_use = f_use;     }
    public String getF_use()                   { return f_use;           }
    public void   setLuserid(String luserid)   { this.luserid = luserid; }
    public String getLuserid()                 { return luserid;         }
    public void   setLdate(String ldate)       { this.ldate = ldate;     }
    public String getLdate()                   { return ldate;           }

    public void   setSeq(int seq)     { this.seq = seq;     }
    public int    getSeq()            { return seq;         }
    public void   setTotal(int total) { this.total = total; }
    public int    getTotal()          { return total;       }

}
