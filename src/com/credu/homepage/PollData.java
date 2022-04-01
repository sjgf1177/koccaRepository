//**********************************************************
//  1. ��      ��: Poll Data
//  2. ���α׷��� : PollData.java
//  3. ��      ��: Poll data bean
//  4. ȯ      ��: JDK 1.3
//  5. ��      ��: 1.0
//  6. ��      ��: ������ 2003. 7.  2
//  7. ��      ��:
//**********************************************************
package com.credu.homepage;
import com.credu.library.*;

/**
 * file name : PollData.java
 * date      : 2003/5/28
 * programmer: ������ (puggjsj@hanmail.net)
 * function  : Poll DATA
 */

public class PollData
{
	
    private String title;           // Ÿ��Ʋ
    private String started;         // ������
    private String ended;           // �����
    private String f_use;           // ��뿩��
    private String luserid;         // ����������
    private String ldate;           // ����������

    private int seq;                // ���Ϸù�ȣ
    private int total;              // �������


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
