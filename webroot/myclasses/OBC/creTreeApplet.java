// Decompiled by Jad v1.5.8f. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) 
// Source File Name:   creTreeApplet.java

import java.applet.Applet;
import java.awt.*;
import java.io.PrintStream;
import java.net.URL;
import java.util.StringTokenizer;
import java.util.Vector;
import netscape.javascript.JSObject;

public class creTreeApplet extends Applet
    implements Runnable
{

    public void init()
    {
        lock = unlock();
        w = size().width;
        h = size().height;
        hh = h - 3;
        ww = w - 3;
        bgi = createImage(w, h);
        bgg = bgi.getGraphics();
        mt = new MediaTracker(this);
        String s;
        if((s = getParameter("BGIMAGE")) != null)
        {
            bgp = getImage(getCodeBase(), s);
            mt.addImage(bgp, 0);
            mt.checkAll(true);
        }
        textcolor = new Color(parsenum(getParameter("TEXTCOLOR"), 16, 0xc0c000));
        activecolor = new Color(parsenum(getParameter("ACTIVECOLOR"), 16, 0x80ff00));
        scrollcolor = new Color(parsenum(getParameter("SCROLLCOLOR"), 16, 0xffffee));
        bgcolor = new Color(parsenum(getParameter("BGCOLOR"), 16, 128));
        lines = new Vector();
        levels = new Vector();
        opened = new Vector();
        fm = bgg.getFontMetrics();
        baseImgURL = getParameter("baseImgUrl");
        objCnt = parsenum(getParameter("objCnt"), 10, 10);
        try
        {
            int i = 0;
            obj = new creTreeObject[objCnt];
            for(; i < objCnt; i++)
            {
                vPara = getParameter("DATA" + i);
                try
                {
                    obj[i] = new creTreeObject(vPara);
                }
                catch(Exception exception1)
                {
                    System.out.println("when creTreeObject():" + exception1.getMessage());
                }
                if(obj[i].level > depth)
                    depth = obj[i].level;
                lines.addElement("     " + obj[i].desc + " | " + obj[i].starting);
                levels.addElement(new Integer(obj[i].level));
                maxLineStr = "     " + obj[i].desc;
                maxLineWidth = fm.stringWidth(maxLineStr);
                if(obj[i].otype.equals("MO"))
                    maxLineWidth += 66;
                else
                if(obj[i].otype.equals("LE"))
                    maxLineWidth += 82;
                else
                if(obj[i].otype.equals("SC"))
                    maxLineWidth += 82;
                obj[i].lineSize = maxLineWidth;
                opened.addElement(new Boolean(obj[i].openStatus));
            }

        }
        catch(Exception exception)
        {
            System.out.println("HEY Error:" + exception.getMessage());
        }
        if(!lock)
        {
            lines.addElement(new String("ASK to icarus@credu.com | mailto:icarus@credu.com"));
            levels.addElement(new Integer(0));
            opened.addElement(new Boolean(false));
        }
        depth++;
        levelsopened = new int[depth];
        for(int j = 0; j < depth; j++)
            levelsopened[j] = -1;

        int k = 0;
        int l = 10;
        String s1 = bgg.getFont().getName();
        s = getParameter("FONT");
        if(s != null)
        {
            StringTokenizer stringtokenizer = new StringTokenizer(s, ",");
            if(stringtokenizer.hasMoreTokens())
                s1 = stringtokenizer.nextToken();
            if(stringtokenizer.hasMoreTokens())
                k = parsenum(stringtokenizer.nextToken(), 10, 10);
            if(stringtokenizer.hasMoreTokens())
                l = parsenum(stringtokenizer.nextToken(), 10, 10);
        }
        bgg.setFont(new Font(s1, k, l));
        fh = fm.getHeight() + 5;
        xstep = fh - 8;
        JSObject jsobject = JSObject.getWindow(this);
        JSObject jsobject1 = (JSObject)jsobject.getMember("document");
        JSObject jsobject2 = (JSObject)jsobject1.getMember("location");
        String s2 = (String)jsobject2.getMember("href");
        System.out.println("ss1=" + s2);
        Integer ainteger[] = new Integer[1];
        ainteger[0] = new Integer(1);
        jsobject.call("startOK", (Object[])ainteger);
    }

    public void paint(Graphics g)
    {
        update(g);
    }

    public void update(Graphics g)
    {
        boolean flag = false;
        boolean flag1 = false;
        liney = y0;
        linex = x0;
        linecounter = 0;
        if(mt == null)
        {
            bgg.setColor(bgcolor);
            bgg.fillRect(0, 0, w, h);
        } else
        {
            bgg.setColor(bgcolor);
        }
        bgg.fillRect(0, 0, w, h);
        bgg.setColor(textcolor);
        for(int i = 0; i < obj.length; i++)
            obj[i].openStatus = false;

        drawlevel(bgg, 0, fh);
        allh = (liney - y0) + fh / 2;
        if(allh > h)
        {
            barh = (hh * hh) / allh;
            bary = ((barh - hh) * y0) / (allh - hh);
            bgg.setColor(bgcolor);
            bar = new Rectangle((w - barw) + 1, bary, barw - 2, barh);
            bgg.fillRect(w - barw, 0, barw, h);
            bgg.setColor(Color.gray);
            bgg.drawRect(w - barw, 0, barw, h);
            bgg.draw3DRect((w - barw) + 1, bary + 1, barw - 2, barh, true);
            bgg.drawRect(0, 0, w - 2, h - 2);
        } else
        {
            y0 = 0;
            gluey = 0;
            barh = 0;
            bary = 0;
            allh = h;
        }
        int j = 0;
        maxLineWidth = 0;
        for(; j < lines.size(); j++)
            if(obj[j].openStatus && maxLineWidth < obj[j].lineSize)
                maxLineWidth = obj[j].lineSize;

        allw = maxLineWidth;
        if(x0 != 0)
        {
            bar_w = (ww * ww) / allw;
            barx = (x0 * (ww - bar_w)) / (allw - ww);
        }
        if(allw > w)
        {
            bgg.setColor(bgcolor);
            rw_bar = new Rectangle((h - barw) + 1, barx, barw - 2, bar_w);
            bar_w = (ww * ww) / allw;
            bgg.setColor(bgcolor);
            bgg.fillRect(0, h - barw, w, barw);
            bgg.setColor(Color.gray);
            bgg.drawRect(0, h - barw, w, barw);
            bgg.draw3DRect(barx + 1, (h - barw) + 1, bar_w, barw - 2, true);
            si = -1 * x0 - 1;
            bgg.drawRect(0, 0, w - 2, h - 2);
        } else
        {
            si = 0;
            x0 = 0;
            gluex = 0;
            bar_w = 0;
            barx = 0;
            allw = w;
        }
        bgg.setColor(Color.gray);
        g.drawImage(bgi, 0, 0, this);
    }

    void drawlevel(Graphics g, int i, int j)
    {
        int k = 0;
        boolean flag = false;
        boolean flag1 = false;
        j += fh >> 2;
        if(i == 0)
            showStatus("");
        while(linecounter < lines.size()) 
        {
            int l = ((Integer)levels.elementAt(linecounter)).intValue();
            boolean flag2 = ((Boolean)opened.elementAt(linecounter)).booleanValue();
            String s = (String)lines.elementAt(linecounter);
            Object obj1 = null;
            int i1 = s.indexOf("|");
            String s2;
            if(i1 != -1)
                s2 = s.substring(0, s.indexOf("|"));
            else
                s2 = s;
            if(l == i)
            {
                liney += fh;
                linex = xstep * (i + 1);
                if(!s.trim().equals(""))
                {
                    byte byte0 = 2;
                    if(linecounter + 1 < lines.size())
                    {
                        int j1 = ((Integer)levels.elementAt(linecounter + 1)).intValue();
                        if(j1 > l)
                            if(!flag2)
                                byte0 = 0;
                            else
                                byte0 = 1;
                    }
                    g.setColor(Color.blue);
                    if(linex <= mx && linex + fh > mx && liney - fh <= my && liney > my)
                        g.setColor(activecolor);
                    drawbox(g, linecounter, si + linex + (fh >> 2), liney, xstep + 4 >> 1, byte0, j, linecounter == docopened);
                    j = liney - (fh >> 2);
                    g.setColor(textcolor);
                    if(liney - fh <= my && liney > my && si + linex + fh <= mx && i1 != -1)
                    {
                        g.setColor(activecolor);
                        String s1 = s.substring(i1 + 1, s.length());
                    }
                    if(i1 == -1)
                        i1 = 0;
                    String s3 = s.substring(i1, s.length());
                    drawObjDesc(g, linecounter, s2, s3, si + linex + fh, liney);
                }
                linecounter++;
                k++;
            }
            if(l > i)
                if(((Boolean)opened.elementAt(linecounter - 1)).booleanValue() && l - i == 1)
                {
                    drawlevel(g, i + 1, j);
                } else
                {
                    linecounter++;
                    if(linecounter >= levels.size())
                        return;
                    l = ((Integer)levels.elementAt(linecounter)).intValue();
                }
            if(l < i)
                return;
        }
    }

    void drawbox(Graphics g, int i, int j, int k, int l, int i1, int j1, 
            boolean flag)
    {
        int k1 = l >> 1;
        if(flag && g.getColor() != activecolor && (i1 == 0 || i1 == 1))
        {
            g.setColor(getNRMColor(i));
            g.setColor(Color.gray);
        }
        switch(i1)
        {
        case 0: // '\0'
            g.setColor(Color.gray);
            g.drawLine((j - 4) + k1, k - 2, (j - 4) + k1, (k - l) + 2);
            // fall through

        case 1: // '\001'
            g.setColor(Color.gray);
            g.drawLine((j - 4) + 2, k - k1, ((j - 4) + l) - 2, k - k1);
            g.drawRect(j - 4, k - l, l, l);
            break;

        default:
            g.setColor(getNRMColor(i));
            g.setColor(new Color(0x66cc66));
            break;
        }
        if(obj[i].otype.equals("SC"))
            drawImg(g, i, j - 4, k - l);
        else
            drawImg(g, i, j + 7, k - l);
        g.setColor(new Color(0x666666));
        g.drawLine(j - l - k1, k - k1, j - 4, k - k1);
        if(linecounter != 0)
            g.drawLine(j - l - k1, k - k1, j - l - k1, j1);
    }

    void drawImg(Graphics g, int i, int j, int k)
    {
        String s = "";
        String s1 = "obj_1001.gif";
        if(obj[i].eduStatus.equals("ING"))
            s1 = "edu.gif";
        else
        if(obj[i].eduStatus.equals("N"))
        {
            if(obj[i].otype.equals("MO"))
                s1 = "module_" + obj[i].oatt + ".gif";
            else
            if(obj[i].otype.equals("LE"))
                s1 = "lesson_" + obj[i].oatt + ".gif";
            else
            if(obj[i].omode.equals("TM"))
                s1 = "tm.gif";
            else
            if(obj[i].omode.equals("TT"))
                s1 = "tt.gif";
            else
                s1 = "obj_" + obj[i].oatt + ".gif";
        } else
        if(obj[i].otype.equals("MO"))
            s1 = "module_" + obj[i].oatt + ".gif";
        else
        if(obj[i].otype.equals("LE"))
            s1 = "lesson_" + obj[i].oatt + ".gif";
        else
            s1 = "obj_f.gif";
        try
        {
            s = baseImgURL + s1;
            Image image = getImage(new URL(s));
            mt.addImage(image, 0);
            mt.checkAll(true);
            g.drawImage(image, j, k - 2, this);
            mt.removeImage(image);
        }
        catch(Exception exception)
        {
            System.out.println(exception.getMessage() + s);
        }
    }

    void openlevel(int i)
    {
        int j = 0;
        while(openlinecounter < lines.size()) 
        {
            int k = ((Integer)levels.elementAt(openlinecounter)).intValue();
            boolean flag = ((Boolean)opened.elementAt(openlinecounter)).booleanValue();
            int l = si + xstep * (i + 1);
            if(k == i)
            {
                if(openl == (my - y0) / fh)
                    if(l + fh <= mx)
                    {
                        opendoc(openlinecounter);
                        docopened = openlinecounter;
                    } else
                    if(flag)
                        opened.setElementAt(Boolean.FALSE, openlinecounter);
                    else
                    if(openlinecounter + 1 < lines.size() && ((Integer)levels.elementAt(openlinecounter + 1)).intValue() > k)
                        opened.setElementAt(Boolean.TRUE, openlinecounter);
                openl++;
                openlinecounter++;
                j++;
            }
            if(k > i)
                if(((Boolean)opened.elementAt(openlinecounter - 1)).booleanValue() && k - i == 1)
                {
                    openlevel(i + 1);
                } else
                {
                    openlinecounter++;
                    if(openlinecounter >= levels.size())
                        return;
                    k = ((Integer)levels.elementAt(openlinecounter)).intValue();
                }
            if(k < i)
                return;
        }
    }

    int countlevel(String s)
    {
        int i = 0;
        if(s == null)
            return 0;
        for(i = 0; i < s.length(); i++)
            if(s.charAt(i) != ' ')
                break;

        return i;
    }

    public void opendoc(int i)
    {
        String s = getParameter("TARGET");
        if(s == null)
            s = "_self";
        String s1 = (String)lines.elementAt(i);
        int j = s1.indexOf("|");
        if(j == -1)
            return;
        s1 = s1.substring(j + 1, s1.length()).trim();
        if(s1.equals(""))
        {
            return;
        } else
        {
            JSObject jsobject = JSObject.getWindow(this);
            JSObject jsobject1 = (JSObject)jsobject.getMember("document");
            JSObject jsobject2 = (JSObject)jsobject1.getMember("location");
            String s2 = (String)jsobject2.getMember("href");
System.out.println("s2가자가자="+s2);            
            Integer ainteger[] = new Integer[1];
            ainteger[0] = new Integer(obj[i].idx);
            jsobject.call("whenObj", (Object[])ainteger);
            return;
        }
    }

    public void setObjDesc(int i, String s)
    {
        obj[i].desc = s;
        String s1 = (String)lines.elementAt(i);
        String s2 = null;
        int j = s1.indexOf("|");
        String s3;
        if(j != -1)
            s3 = s1.substring(0, s1.indexOf("|"));
        else
            s3 = s1;
        s2 = s1.substring(j, s1.length());
        lines.setElementAt(s + s2, i);
        repaint();
    }

    public void setObjStatus(int i, String s)
    {
        if(s.equals("ING"))
        {
            for(int j = 0; j < obj.length; j++)
                obj[j].eduStatus = obj[j].eduEnded;

            obj[i].eduStatus = s;
        } else
        if(s.equals("COMPLETE"))
        {
            obj[i].eduEnded = "Y";
            obj[i].eduStatus = "Y";
        } else
        if(s.equals("INCOMPLETE"))
        {
            obj[i].eduEnded = "N";
            obj[i].eduStatus = "N";
        }
        repaint();
    }

    public void goNext()
    {
        int i = 0;
        for(int j = 0; j < obj.length; j++)
        {
            if(!obj[j].eduStatus.equals("ING"))
                continue;
            i = j;
            break;
        }

        goNext(i);
    }

    public void goNext(int i)
    {
        int j = 0;
        if(i >= obj.length)
            j = 0;
        j = i + 1;
        setObjStatus(j, "ING");
        opendoc(j);
    }

    public void drawObjDesc(Graphics g, int i, String s, String s1, int j, int k)
    {
        int l = s.indexOf("$$");
        int i1 = 0;
        if(l == -1)
            l = s.length();
        String s2 = s.substring(0, l) + "$$" + obj[i].eduStatus;
        lines.setElementAt(s2 + s1, i);
        obj[i].openStatus = true;
        if(obj[i].eduStatus.equals("ING"))
            g.setColor(c_ing);
        else
        if(obj[i].eduStatus.equals("Y"))
            g.setColor(c_y);
        else
            g.setColor(c_n);
        if(obj[i].otype.equals("SC"))
            i1 = j - 20;
        else
            i1 = j - 10;
        g.drawString(s.substring(0, l), i1, k);
    }

    public Color getNRMColor(int i)
    {
        if(obj[i].eduStatus.equals("ING"))
            return c_ing;
        if(obj[i].eduStatus.equals("Y"))
            return c_y;
        else
            return c_n;
    }

    public void nodeOpen(int i)
    {
        if(!((Boolean)opened.elementAt(i)).booleanValue())
        {
            opened.setElementAt(Boolean.TRUE, i);
            repaint();
        }
    }

    public void nodeClose(int i)
    {
        if(((Boolean)opened.elementAt(i)).booleanValue())
        {
            opened.setElementAt(Boolean.FALSE, i);
            repaint();
        }
    }

    public void nodeStatusChange(int i)
    {
        if(((Boolean)opened.elementAt(i)).booleanValue())
            opened.setElementAt(Boolean.FALSE, i);
        else
            opened.setElementAt(Boolean.TRUE, i);
        repaint();
    }

    public boolean mouseDown(Event event, int i, int j)
    {
        if(allh > h && i > w - barw)
        {
            gluey = j - bary;
            indrag = true;
            indragx = false;
            mx = i;
            my = j;
            repaint();
            return true;
        }
        if(allw > w && j > h - barw)
        {
            gluex = i - barx;
            indrag = false;
            indragx = true;
            mx = i;
            my = j;
            repaint();
            return true;
        } else
        {
            System.out.println("in MouseDown :" + i + ", " + j);
            openl = 0;
            openlinecounter = 0;
            openlevel(0);
            mx = i;
            my = j;
            repaint();
            return true;
        }
    }

    public boolean mouseUp(Event event, int i, int j)
    {
        indrag = false;
        indragx = false;
        return true;
    }

    public boolean mouseMove(Event event, int i, int j)
    {
        if(mx / fh != i / fh || (my - y0) / fh != (j - y0) / fh)
        {
            mx = i;
            my = j;
            repaint();
        }
        return true;
    }

    public boolean mouseEnter(Event event, int i, int j)
    {
        mx = i;
        my = j;
        repaint();
        return true;
    }

    public boolean mouseOut(Event event, int i, int j)
    {
        mx = -1;
        my = -1;
        indrag = false;
        indragx = false;
        repaint();
        return true;
    }

    public boolean mouseDrag(Event event, int i, int j)
    {
        if(indrag)
        {
            int k = j - gluey;
            if(k < 1)
                k = 1;
            if(k + barh > hh)
                k = hh - barh;
            bary = k;
            y0 = ((hh - allh) * bary) / (hh - barh);
            mx = i;
            my = j;
            repaint();
        }
        if(indragx)
        {
            int l = i - gluex;
            if(l < 1)
                l = 1;
            if(l + bar_w > ww)
                l = ww - bar_w;
            barx = l;
            x0 = (barx * (allw - ww)) / (ww - bar_w);
            mx = i;
            my = j;
            repaint();
        }
        return true;
    }

    public void start()
    {
        if(thread == null)
            thread = new Thread(this);
        thread.start();
    }

    public void stop()
    {
        thread = null;
    }

    public void run()
    {
        if(mt != null)
            while(!mt.checkAll(true)) 
                try
                {
                    Thread.sleep(100L);
                }
                catch(InterruptedException interruptedexception) { }
        repaint();
    }

    private final boolean unlock()
    {
        String s = getParameter("AUTHOR");
        if(s == null)
            return false;
        if(s.equals("KangSeongWoog(Credu)"))
            return true;
        s = getCodeBase().getHost();
        if(getParameter("KEY") == null)
            return false;
        else
            return getParameter("KEY").trim().equalsIgnoreCase(encrypt(s, getClass().getName()));
    }

    private String encrypt(String s, String s1)
    {
        s1 = s1.toLowerCase().trim();
        s = s.toLowerCase().trim();
        s = s + s1;
        int i = s.length();
        if(i < 40)
        {
            for(int j = 0; j < 40 - i; j++)
                s = s + j % 10;

        }
        if(i > 40)
            s = s.substring(0, 40);
        byte abyte0[] = new byte[40];
        s.getBytes(0, 40, abyte0, 0);
        for(int k = 0; k < 20; k++)
            abyte0[k] += abyte0[39 - k];

        s = "";
        for(int l = 0; l < 10; l++)
        {
            abyte0[l] ^= (byte)((abyte0[10 + l] << 8) + abyte0[19 - l]) >> (abyte0[l] & 3);
            s = s + Integer.toString(abyte0[l], 9);
        }

        return s;
    }

    int parsenum(String s, int i, int j)
    {
        if(s == null)
            return j;
        int k = j;
        try
        {
            k = Integer.parseInt(s, i);
        }
        catch(NumberFormatException numberformatexception)
        {
            k = j;
        }
        return k;
    }

    public creTreeApplet()
    {
        maxLineStr = " ";
        maxLineWidth = 0;
        si = 0;
        vPara = "";
        objCnt = 0;
        baseImgURL = "";
        textcolor = new Color(0xc0c000);
        bgcolor = new Color(128);
        activecolor = new Color(0x80ff00);
        c_ing = new Color(255);
        c_y = new Color(26112);
        c_n = new Color(0);
        lock = false;
        docopened = -1;
        allh = 10;
        barw = 12;
        indrag = false;
    }

    public void setResize(int i, int j)
    {
        resize(i, j);
    }

    FontMetrics fm;
    Vector lines;
    Vector levels;
    Vector opened;
    Color textcolor;
    Color bgcolor;
    Color scrollcolor;
    Color activecolor;
    Color c_ing;
    Color c_y;
    Color c_n;
    Image bgi;
    Image bgp;
    Graphics bgg;
    boolean lock;
    MediaTracker mt;
    Thread thread;
    long mdtime;
    int fh;
    int xstep;
    int mx;
    int my;
    int depth;
    int levelsopened[];
    int linecounter;
    int openlinecounter;
    int liney;
    int linex;
    int openl;
    int w;
    int h;
    int docopened;
    int y0;
    int allh;
    int bary;
    int barh;
    int hh;
    int barw;
    Rectangle bar;
    int gluey;
    boolean indrag;
    String maxLineStr;
    int maxLineWidth;
    int x0;
    int allw;
    int barx;
    int bar_w;
    int ww;
    Rectangle rw_bar;
    int gluex;
    boolean indragx;
    int si;
    String vPara;
    creTreeObject obj[];
    int objCnt;
    String baseImgURL;
}
