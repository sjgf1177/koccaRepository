//**********************************************************
//  1. ��      ��: Poll ���� Data
//  2. ���α׷��� : PollSelData.java
//  3. ��      ��: Poll ���� data bean
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 7.  2
//  7. ��      ��:
//**********************************************************
package com.credu.homepage;
import com.credu.library.*;

/**
 * file name : PollSelData.java
 * date      : 2003/7/13
 * programmer: ������ (puggjsj@hanmail.net)
 * function  : Poll ���� DATA
 */

public class PollSelData
{

    private String seltxt;            // ���⳻��

    private int seq;                // ���Ϸù�ȣ
    private int selnum;             // ���������Ϸù�ȣ
    private int cnt;                // ��ǥ��
	private int total;                // ��ü��ǥ��
	private int maxcnt;                // �ִ���ǥ��
	

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
