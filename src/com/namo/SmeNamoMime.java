/* =============================================================================
 CLASS : smipc.util.SmeNamoMime			Yoon, Tae-Hyun Yoon (2003-03-16)

 @modify by Lee, Do Kyoung(2003.07)
 CLASS : com.namo.SmeNamoMime
 -------------------------------------------------------------------------------
 Namo ActiveSquare�� �̿��� �ۼ��� MIME ������ ���ڵ��ϰ�, inline ÷���̹��� ����
 ���Ϸ� ������ �� ���� �ϼ��� ������ ��ȯ���ִ� ó���� �����ϴ� Ŭ����.
 =============================================================================*/

package com.namo;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.activation.DataSource;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

//=============================================================================
/** Namo ActiveSquare�� �̿��� �ۼ��� MIME ������ ���ڵ��ϰ�, inline �̹��� ����
 * ���Ϸ� ������ �� ���� �ϼ��� ������ ��ȯ���ִ� ó���� �����ϴ� Ŭ����.
 *
 * <p><font size="-1">�� Ŭ������ NamoAS5�� �ۼ��� ������ MIMEValue�� ��ȯ��
 * ������ �Ѿ�� �����͸� �����ʿ��� ���ڵ��ϰ�, ÷�ε� �̹��� ���� ���� ��ũ��
 * �����ϰ�, �̿� ���� ���� ����(CID)�� �ٷ���� ������ ������.
 * </font></p>
 *
 * <p><font size="-1">�� Ŭ������ JavaMail�� ǥ�� MIME ó�� API�� ����ϹǷ�,
 * Namo ActiveSqure�Ӹ� �ƴ϶� �ۼ������� �� ���� �ζ��� �̹��� ���� ǥ�� MIME
 * �������� �Ѱ��ִ� �ٸ� HTML Editiing Control���� ������ �� �ִ�.
 * �׷��� ��������� Namo ActiveSqure �󿡼��� �׽�Ʈ �Ǿ����Ƿ�, �ٸ� HTML
 * ������ ��Ʈ�Ѱ��� ȣȯ���� ���ؼ��� ������ �� ����.
 * </font></p>
 *
 * <p><b>��뿹 :</b></p><p><code>
 * SmeNamoMime namo = new SmeNamoMime(request.getParameter("content")); // ��ü���� <br>
 * boolean result = namo.parse(); // ���� �Ľ� ���� <br>
 * if ( !result ) { // �Ľ� ���н� <br>
 * &nbsp;&nbsp; out.println( namo.getDebugMsg() ); // ����� �޽��� ��� <br>
 * &nbsp;&nbsp; return; // ���α׷� ���� �ߴ� <br>
 * } // if <br>
 * if ( namo.isMultipart() ) { // ������ ��Ƽ��Ʈ���� �Ǵ� <br>
 * &nbsp;&nbsp; String fPath = "/home/www/userimages/"; // ���� ���� ��� ����<br>
 * &nbsp;&nbsp; String refUrl = "/userimages/"; // ������ ����� ������ �����ϱ� ���� ���<br>
 * &nbsp;&nbsp; String prefix = bbsCode + fillZero(articleNo, 6); // ���ϸ� ���ξ�<br>
 * &nbsp;&nbsp; result = namo.saveFile(); // ���� ���� ���� <br>
 * } // if <br>
 * if ( !result ) { // �������� ���н� <br>
 * &nbsp;&nbsp; out.println( namo.getDebugMsg() ); // ����� �޽��� ��� <br>
 * &nbsp;&nbsp; return; // ���α׷� ���� �ߴ� <br>
 * } // if <br>
 * String content = namo.getContent(); // ���� ����Ʈ ���
 * </code></p>
 *
 * @author Yoon Tae-Hyun.
 * @version 2003.03.16.
 */
//==========================================================================

public class SmeNamoMime {

//=============================================================================
// �Ӽ�(Properties) ����
//=============================================================================

    /** ���� MIME ���� (NamoAS�� MIMEValue) */
    private String mimeDoc;

    /** ���� MIME ������ ��� �Ӽ� */
    private MimeBodyPart mbp;

    /** ��Ƽ��Ʈ ������ ��� �Ӽ� */
    private MimeMultipart mmp;

    /** ��ȯ�� �Ϸ�� ������ ���� �Ӽ� */
    private String content;

    /** Mime ������ ���Ե� ��Ʈ �� */
    private int partCount;

    /** <body>~</body>���� �������� ����� �������� ���� */
    private boolean useBodyOnly;

    /** ����� �޽��� ������ �Ӽ� */
    private StringBuffer debugMsg;

//=============================================================================
// ������(Constructor)
//=============================================================================


    //---------------------------------------------------------------------
    /** �����ڷμ� Parsing�� Mime Document�� ���ڷ� �޴´�.
     * @param mimedoc ���� ���� �Ѿ�� Namo AS�� MIMEValue ������.
     */
    //------------------------------------------------------------------
    public SmeNamoMime(String mimedoc) {
        this.mimeDoc = mimedoc;

        this.partCount = 0;
        this.useBodyOnly = false;
        this.content = null;
        this.mbp = null;
        this.mmp = null;

        debugMsg = new StringBuffer();
        debugMsg.append("[INFO] SmeNamoMime ��ü�� ������.\n");
    } // end Contructor NamoMime

    //---------------------------------------------------------------------



//=============================================================================
// ����(private) ó�� �޼ҵ�
//=============================================================================


    //---------------------------------------------------------------------
    /** ���ڿ����� &lt;body&gt;~&lt;/body&gt; �±׳��� ���븸 ����� ������ �±״� ����.
     * @param str ���� ���ڿ�.
     * @return &lt;body&gt;~&lt;/body&gt; ���� ����.
     */
    //------------------------------------------------------------------
    private String stripHead(String str) {

        Pattern p = Pattern.compile("^.*<\\s*body[^>]*>[\\s|\\n]*",
                                    Pattern.DOTALL);
        Matcher m = p.matcher(str);
        str = m.replaceAll("");
        p = Pattern.compile("<\\s*/body[^>]*>.*$", Pattern.DOTALL);
        m = p.matcher(str);
        str = m.replaceAll("");
        /*
            try {
         */
        //RE pttrn1 = new RE("^.*<\\s*body[^>]*>[\\s|\\n]*");
        // str = pttrn1.subst(str,"",RE.MATCH_MULTILINE );
        //pttrn1  = new RE("<\\s*/body[^>]*>.*$");
       //str = pttrn1.subst(str,"", RE.MATCH_MULTILINE);
       /*
             int startIndex = str.indexOf("<body");
             int endIndex   = str.indexOf("</body>");
             str = str.substring(startIndex , (endIndex + 7) );

             RE pttrn1 = new RE("\"");
             str = pttrn1.subst(str,"'");
             pttrn1  = new RE("\r\n");
             str = pttrn1.subst(str,"");
             System.out.println("NAmo      ===============> " + useBodyOnly);



           }catch(Exception e) {
             e.printStackTrace(System.err);
           }
        */
        return str;
    } // stripHead

    //---------------------------------------------------------------------



//=============================================================================
// �Ӽ��������̽� �޼ҵ�
//=============================================================================


    //---------------------------------------------------------------------
    /** Parsing�� �Ϸ�� ���� ������ ��ȯ�ϴ� �޼ҵ�.
     * <p><font size="-1">
     * �� �޼ҵ带 ȣ���ϱ� �� �ݵ�� {@link #parse} �޼ҵ带 �̿���,
     * �Ľ� ������ ���� �����ؾ� �ϸ�, ��Ƽ��Ʈ ������ ��쿡�� �Ľ� ���� ��
     * {@link #saveFile} �޼ҵ带 �̿��� ÷�ε� ������ �����ϰ� ������ ������
     * URL�� ������ �Ŀ� �� �޼ҵ带 ����ؾ� �ùٸ� ���� ������ ���� �� �ִ�.
     * </font></p>
     * @return ���ڿ��ε� ���� Content�� ��ȯ.
     */
    //------------------------------------------------------------------
    public String getContent() {
        return this.content;
    } // end getContent

    //---------------------------------------------------------------------


    //---------------------------------------------------------------------
    /** Parsing�� ������ Multipart �������� ���θ� ��ȯ�ϴ� �޼ҵ�.
     * @return ��ȯ���� true�̸� MultiPart ����, false�̸� ������Ʈ ����.
     */
    //------------------------------------------------------------------
    public boolean isMultipart() {
        return (this.partCount > 1) ? true : false;
    } // end isMultipart

    //---------------------------------------------------------------------


    //---------------------------------------------------------------------
    /** Mime ������ ���Ե� inline ÷������ ���� ��ȯ�ϴ� �޼ҵ�.
     * @return ��ȯ���� true�̸� MultiPart ����, false�̸� ������Ʈ ����.
     */
    //------------------------------------------------------------------
    public int getFileCount() {
        return this.partCount - 1;
    } // end getFileCount

    //---------------------------------------------------------------------


    //---------------------------------------------------------------------
    /** �Ѿ�� ���� ��ü�� ����� ������, &lt;body&gt;~&lt;/body&gt;����
     * ���������� ����� �������� ����.
     * <p><font size="-1">
     * �������� ������ �⺻���� body �±׳��� �������� ����ϰ� ��.
     * �Խ��� ����� body ���� �±׸� ������ �ϴ� ���� ��ü ������ ���̾ƿ���
     * ���� �����Ƿ� �����ϹǷ� �⺻���� �������� �ʴ� ���� ������, ���� �߼� ��
     * ������ ��ü�� �ʿ��� ��쿡�� ����Ѵ�.
     * </font></p>
     * @param charset ���ڼ� �̸�.
     */
    //------------------------------------------------------------------
    public void useWholeDocument(boolean flag) {
        this.useBodyOnly = (flag) ? false : true;
    } // useWholeDocument

    //---------------------------------------------------------------------



    //---------------------------------------------------------------------
    /** Ŭ���� ���� ó���� �߻��� ����� �޽����� ��ȯ�ϴ� �޼ҵ�.
     * @return ó���� �߻��� ����� �޽���.
     */
    //------------------------------------------------------------------
    public String getDebugMsg() {
        return this.debugMsg.toString();
    } // getDebugMsg

    //---------------------------------------------------------------------



//=============================================================================
// ����(public) �޼ҵ� (��ü ���� �� ��밡��)
//=============================================================================


    //---------------------------------------------------------------------
    /** Mime Document�� ������ Parsing �Ͽ�, ÷�ε� �� ��Ҹ� �и��ϰ�
     * ������ ������ �����ϴ� �޼ҵ�.
     * <p><font size="-1">
     * ������ ������Ʈ�� �Ǿ� ������ �� �޼ҵ� ���� �� �ٷ� �ϼ��� Content��
     * ���� �� ������, ��Ƽ��Ʈ ��쿡�� {@link #saveFile} �޼ҵ带 �̿���
     * ������ �����ϰ�, ������ ���� ����(CID)�� ���� URL�� �����ϴ� �۾���
     * ����� ������ Content�� ���� �� �ִ�.
     * </font></p>
     * <p><font size="-1">
     * MIME ������ ��Ƽ��Ʈ���� ������Ʈ������ ���δ� �� �޼ҵ� ���� �Ŀ�
     * {@link #isMultipart} �޼ҵ带 ���� �� �� �ִ�.
     * </font></p>
     * @return Parsing�� �����ϸ� true, �����ϸ� false ��ȯ
     *                   (���н� {@link #getDebugMsg} �޼ҵ带 ����� �����).
     */
    //------------------------------------------------------------------
    public boolean parse() {
        if (mimeDoc == null || !mimeDoc.startsWith("MIME-Version")) { // MIME ���� ����
            debugMsg.append("[STOP] MIME ������ �ùٸ��� ����.\n");
            return false;
        } // if

        // ���ڿ��� ByteArrayInputStream���� --------------------------
        ByteArrayInputStream bais = null; // ���ڿ��� InputStream���� ��ȯ.
        try {
            bais = new ByteArrayInputStream(mimeDoc.getBytes("euc-kr"));
        }
        catch (Exception e) {
            e.printStackTrace();
            debugMsg.append("[STOP] ���ڿ� ��ȯ ����(ByteArrayInputStream).\n");
            debugMsg.append(e.getMessage());
            return false;
        } // try catch

        // InputStream�� �̿��� MimeBodyPart ��ü ���� ----------------
        boolean blMulti = false;
        try {
            mbp = new MimeBodyPart(bais); // InputStream�� mbp ��ü�� �Ҵ�
            String conType = mbp.getContentType(); // ������ ���� Content-Type
            if (conType.startsWith("text")) { // Content-Type���� ��Ƽ��Ʈ������ �Ǵ�
                partCount = 1;
                debugMsg.append("[INFO] ������Ʈ ���� : " + conType + "\n");
            }
            else if (conType.startsWith("multipart")) {
                blMulti = true;
                debugMsg.append("[INFO] ��Ƽ��Ʈ MIME ���� : " + conType + "\n");
            }
            else {
                debugMsg.append("[STOP] �������� �ʴ� MIME ���� : " + conType + "\n");
                return false;
            } // if

        }
        catch (Exception e) {
            e.printStackTrace();
            debugMsg.append("[STOP] MIME ������ Parsing ����.\n");
            debugMsg.append(e.getMessage());
            return false;
        } // try catch

        // MimeBodyPart���� ���� Content �賻�� -----------------------
        if (blMulti) { // ��Ƽ��Ʈ ������ ���

            try {
                mmp = (MimeMultipart) mbp.getContent(); // �ٵ���Ʈ�� ��Ƽ��Ʈ�� ��ȯ
                partCount = mmp.getCount(); // ÷������ �� (��ü ��Ʈ���� ������ ������ ������)
                MimeBodyPart bp = null;
                for (int i = 0; i < partCount; i++) { // ������ ���� Content ã��
                    bp = (MimeBodyPart) mmp.getBodyPart(i);
                    String conID = bp.getContentID(); // Content-ID
                    String conPos = bp.getDisposition(); // Content-Disposition
                    if (conID == null && conPos == null) { // �� ���� null�̸� ������.
                        content = (String) bp.getContent();
                    } // if
                } // for
            }
            catch (Exception e) {
                e.printStackTrace();
                debugMsg.append("[STOP] ��Ƽ��Ʈ ������ ���� ����.\n");
                debugMsg.append(e.getMessage());
                return false;
            } // try catch

        }
        else { // ������Ʈ ������ ���

            try {
                content = (String) mbp.getContent(); // ���� ������ ���
            }
            catch (Exception e) {
                e.printStackTrace(System.err);
                debugMsg.append("[STOP] ���� ���� ����.\n");
                debugMsg.append(e.getMessage());
                return false;
            } // try catch

        } // if

        // Content ���� -----------------------------------------------
        if (!chkStr(content)) {
            debugMsg.append("[STOP] No Content.\n");
            return false;
        } // if
        if (useBodyOnly) {
            content = stripHead(content);
        }
        else {
            try {
                content = replace(content, "\"", "'");
                content = replace(content, "\r\n", " ");
                /*
                                        RE pttrn1 = new RE("\"");
                                        content = pttrn1.subst(content, "'");
                                        pttrn1 = new RE("\r\n");
                                        content = pttrn1.subst(content, " ");
                 */
            }
            catch (Exception ex) {}
        }
        debugMsg.append("[INFO] MIME parse finished.\n");

        return true;
    } // parse

    //---------------------------------------------------------------------


    //---------------------------------------------------------------------
    /** MIME �������� ���Ե� inline ÷�� ���ϵ��� ��ũ�� ������ ��λ�
     * �����ϰ�, �������� CID�� �̿��� ����������, ������ ���� URL�� �������
     * �ִ� �޼ҵ�.
     *
     * <p><font size="-1">
     * ������ ����� ��� ������ �ݵ�� �÷��� ȣȯ���� ���� ��������
     * ���丮 ������(���н��� /, ������ \)�� �ٿ���� �Ѵ�. <br>
     * - /home/www/images : Ʋ�� ���� <br>
     * - /home/www/images/ : �ùٸ� ���� <br>
     * - C:\home\www\images : Ʋ�� ���� <br>
     * - C:\home\www\images\ : �ùٸ� ���� <br>
     * (* ������ ��θ� ������ Java�� ���ڿ� ���� ���Ŀ� ���߱� ����
     *  "C:\\home\\www\\images\\"�� ���� \ ���ڸ� �ΰ��� �־���� �Ѵ�)
     * </font></p>
     *
     * <p><font size="-1">
     * ���� ���ξ�(prefix ����)�� �������� ������, ÷�ε� ���� ���ϸ��� �״��
     * ����Ѵ�. �׷��� �� ��� �÷����� ���� ���ϸ� ���� ���̳�, ��������
     * �������� ���� �ٱ��� �� Ư����ȣ URL ���� ���� ������ ���� �������忡
     * ���� �ϰų�, ���󿡼� ������ �Ұ����� �� �ִ�.<br><br>
     *
     * ���� ������ prefix�� ������ ���� �̸��� ���� �����ִ� ���� �����߻���
     * ���ɼ��� �����ϴµ� �����ϸ�, prefix�� �۹�ȣ, ����ð�, ����IP, �����
     * ID �� �ǹ̸� ���� ������ �������ָ� ���� ���� �˻��̳� ������ ���ϰ�
     * Ȱ��� �� �ִ�.<br><br>
     *
     * prefix ������ ���ϸ��� prefix + ���Ϲ�ȣ + ���������� Ȯ���ڷ� �����Ǹ�,
     * �� ���� ������ ����.<br>
     * - 20020315_133015(����ð�) : 20020315_133015.gif, 20020315_133015_001.gif, ...<br>
     * - 020315_yoonth(��¥+ID) : 020315_yoonth.gif, 020315_yoonth_001.gif, ...<br>
     * - free000001(�Խ����ڵ�+�۹�ȣ) : free000001.gif, free000001_001.gif, ...<br>
     * </font></p>
     *
     * <p><code>
     * </code></p>
     * @param bpath ������ ����� ���丮�� �����η� ����
     *              (��: /home3/www/userimages/).
     * @param burl  ���󿡼� ���� ������ ������ �� �ִ� URL ��� ����.
     *              (��: /userimages/).
     * @param prefix �����̸��� ����� ���ξ�μ� ���� null���̳� ""����
     *               �Ѿ���� ÷�ε� ���� ���ϸ��� �״�� ����Ѵ�.(��: 20020315_1330).
     * @return �������忡 �����ϸ� true ��ȯ, �����ϸ� false ��ȯ.
     */
    //------------------------------------------------------------------
    public boolean saveFile(String bpath, String burl, String prefix) {
        if (partCount < 2) {
            debugMsg.append("[STOP] No embeded file.\n");
            return false;
        } // if
        if (bpath == null || burl == null) {
            debugMsg.append("[STOP] Base path �Ǵ� base URL�� �������� ����.\n");
            return false;
        } // if
        if (!bpath.endsWith("\\") && !bpath.endsWith("/")) {
            bpath += "/";
        } // if
        boolean useNewName = (chkStr(prefix)) ? true : false;

        try {
            for (int i = 0; i < partCount; i++) {

                // MIME ��Ʈ �м� -----------------------------
                MimeBodyPart bp = (MimeBodyPart) mmp.getBodyPart(i);
                String conID = bp.getContentID(); // Content-ID
                String conPos = bp.getDisposition(); // Content-Disposition
                if (conID == null && conPos == null) {
                    continue; // �� ���� null�̸� ������.
                }

                // ���� �̸� ����� ---------------------------

                /////////////////////�ѱ� ���� �̸�/////////////////////
                String tempName = this.decodeText(bp.getFileName());
                //////////////////////////////////////////////////////

                String fname = I2K(tempName);
                String fext = afterStr(fname, ".");
                if (!fext.equals("")) {
                    fext = "." + fext;
                }
                fname = (useNewName) ? uniqFileName(bpath, prefix + fext) :
                    urlSafeFileName(fname);

                // ���� ���� ���� -----------------------------
                File saveFile = new File(bpath + fname);
                BufferedInputStream bis = new BufferedInputStream(bp.
                    getInputStream(), 1024);
                BufferedOutputStream bos = new BufferedOutputStream(new
                    FileOutputStream(saveFile), 1024);
                byte buffer[] = new byte[1024];
                int rb = 0;
                while ( (rb = bis.read(buffer, 0, 1024)) >= 0) {
                    bos.write(buffer, 0, rb);
                } // while
                bis.close();
                bos.close();

                // CID ���� ���� ------------------------------
                //modify dklee

                conID = conID.replaceAll("[<>]", "");
                /*
                        RE pttrn1 = new RE("[<>]");
                        conID     = pttrn1.subst(conID, "");
                 */
                String pattern = "cid:" + conID;
                String repl = burl + fname;
                /*
                        pttrn1  = new RE(pattern);
                        content = pttrn1.subst(content, repl);
                 */
                content = content.replaceAll(pattern, repl);

                debugMsg.append("FIle saved : " + conID + "=" + bpath + fname +
                                "\n");

            } // for
        }
        catch (Exception e) {

            e.printStackTrace();
            debugMsg.append("[STOP] MIME ÷������ ���� ����.\n");
            debugMsg.append(e.getMessage());
            return false;

        } // try catch

        debugMsg.append("[INFO] ���� ���� ����.\n");
        return true;
    } // saveFile

    //---------------------------------------------------------------------



//=============================================================================
// ��ƿ��Ƽ �޼ҵ� (������ ���� �ٸ� Ŭ������ �ִ� �޼ҵ���� ������)
//=============================================================================


    // --------------------------------------------------------------------
    /** ���ڷ� �־��� ���ڿ��� ��ȿ��(null�� �ƴϰ� ���� ���Ե�) ���ڿ�������
     * �˻��ؼ� boolean ���� ��ȯ.
     * <p><code>if ( chkStr(str) ) {<br>
     * &nbsp;&nbsp; out.println("���ڿ��� ��ȿ���� �ʽ��ϴ�.");<br>
     * }</code></p>
     * @param strParam �˻��� ���ڿ�
     * @return �־��� ���ڿ��� ��ȿ�ϸ� true��, ��ȿ���� ������ false�� ��ȯ
     */
    // -----------------------------------------------------------------
    private boolean chkStr(String strParam) {
        if (strParam == null || strParam.equals("")) {
            return false;
        } // end if
        return true;
    } // chkStr

    // --------------------------------------------------------------------


    // --------------------------------------------------------------------
    /** ISO-8859_1 ������ ��Ʈ���� KSC-5601�� ��ȯ�Ͽ� ���� (�ѱ��� ������ ���).
     * <p><code>String kscstr = I2K(isostr);</code></p>
     * @param in ��ȯ�� ISO-8859_1 ĳ���ͼ��� ���ڿ�
     * @return KSC-5601 ĳ���ͼ��� ���ڿ�
     */
    // -----------------------------------------------------------------
    private String I2K(String in) {
        String out;
        try {
            //out = new String(in.getBytes("8859_1"),"KSC5601");
            out = in;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        } // try catch
        return out;
    } // I2K

    // --------------------------------------------------------------------


    // --------------------------------------------------------------------
    /** ���� ���ڿ����� ������ �����ڰ� ���������� ��Ÿ���� ��ġ ������ ���ڿ��� ��ȯ.
     * <p><code>
     * out.println(beforeStr("my.picture.gif", ".")); // my.picture ��� <br>
     * out.println(afterStr("my.picture.gif", ".")); // gif ���<br>
     * </code></p>
     * @param src ���� ���ڿ�.
     * @param delim ������.
     * @return ������ ������ �ڿ� ��Ÿ���� ���ڿ� �Ǵ� ""(ã����������).
     * @see #beforeStr(String, String)
     */
    // -----------------------------------------------------------------
    private String afterStr(String src, String delim) {
        if (src == null || delim == null) {
            return "";
        }
        int pos = src.lastIndexOf(delim);
        if (pos < 0) {
            return "";
        }
        if (pos + delim.length() >= src.length()) {
            return "";
        }
        return src.substring(pos + 1, src.length());
    } // afterStr

    // --------------------------------------------------------------------


    // --------------------------------------------------------------------
    /** ���� ���ڿ����� ������ �����ڰ� ���������� ��Ÿ���� ��ġ ������ ���ڿ��� ��ȯ.
     * <p><code>
     * out.println(beforeStr("my.picture.gif", ".")); // my.picture ��� <br>
     * out.println(afterStr("my.picture.gif", ".")); // gif ���<br>
     * </code></p>
     * @param src ���� ���ڿ�.
     * @param delim ������.
     * @return ������ ������ �տ� ��Ÿ���� ���ڿ� �Ǵ� ""(ã����������).
     * @see #afterStr(String, String)
     */
    // -----------------------------------------------------------------
    private String beforeStr(String src, String delim) {
        if (src == null || delim == null) {
            return "";
        }
        int pos = src.lastIndexOf(delim);
        if (pos < 1) {
            return "";
        }
        return src.substring(0, pos);
    } // beforeStr

    // --------------------------------------------------------------------


    // --------------------------------------------------------------------
    /** �־��� ���� �̸��� ���� ���� URL ���ٽÿ��� ���������� �νĵǵ���
     * �����̳� ��Ÿ URL ��ȣ���� ����(_)��ȣ�� �ٲ��ִ� �޼ҵ�.
     * @param fname ���ϸ�.
     * @return ���� �� URL ���� ��ȣ���� ����(_)�� ��ġ�� ���ϸ�.
     * @since 2003.03.21.
     */
    //------------------------------------------------------------------
    private String urlSafeFileName(String fname) {
        if (fname == null) {
            return null;
        }
        /*
            //modify dklee
            try {
              RE pattern  = new RE("[?/&|+= ]");
              fname = pattern.subst(fname,"_");
            }catch(Exception e) {
              e.printStackTrace(System.err);
              return null;
            }
            return fname;
         */
        return fname.replaceAll("[?/&|+= ]", "_");
    } // end urlSafeFileName

    // --------------------------------------------------------------------


    // --------------------------------------------------------------------
    /** ������ ��θ�� ���ϸ��� �̿��� �������� �ʴ� ���ϸ��� ����� ��ȯ.
     * <p>���� �������� ������ �����ϸ� ���ϸ� �ڿ� �Ϸù�ȣ��
     * ���̴� ������� ������ ���ϸ��� ����</p>
     * <p><code>
     * // ���� log.txt�� ������ log_001.txt�� ��ȯ��.<br>
     * String myFile = uniqFileName("/home3/www/", "log.txt");
     * </code></p>
     * @param fpath ������ ���� ��� (�÷��� ȣȯ�� ������ ���� ���� �ڿ���
     *              �� ���丮 ���б�ȣ�� �ٿ��� ��).
     * @param fname �⺻ ���ϸ� (�������� ������ ������ timestamp ���� �̿�).
     * @return ������ ���ϸ� �Ǵ� null(���丮�� �ùٸ��� �ʴ� �� ���� �߻���).
     */
    //------------------------------------------------------------------
    private String uniqFileName(String fpath, String fname) {
        if (!chkStr(fpath)) {
            return null; // ��ΰ� �߸��Ǹ� null ��ȯ
        }
        File baseDir = new File(fpath);
        if (!baseDir.exists() || !baseDir.isDirectory()) {
            return null;
        }
        if (!chkStr(fname)) { // �⺻ ���ϸ��� �����ȵǸ� ���� timestamp ���
            fname = String.valueOf(new Date().getTime());
        } // if

        String namepart = beforeStr(fname, "."); // ������ �̸� �κ� ���
        String extpart = afterStr(fname, "."); // ������ Ȯ���� �κ� ���
        if (!extpart.equals("")) {
            extpart = "." + extpart;
        }
        String tmpname = namepart;

        File tmpFile = new File(fpath + tmpname + extpart);
        int num = 0;
        while (tmpFile.exists()) { // ������ �����ϴ� ���� ������ ���� �� ���ϸ� �ۼ�
            num++;
            tmpname = namepart + "_" + fillZero(num, 3); // ���� �Ϸù�ȣ
            tmpFile = new File(fpath + tmpname + extpart);
        } // while
        return tmpname + extpart;
    } // uniqFileName

    // --------------------------------------------------------------------


    // --------------------------------------------------------------------
    /** �Էµ� ���ڿ� ���ϴ� �ڸ�����ŭ �տ� 0�� �ٿ� ���ڿ��� ��ȯ�ϴ� �޼ҵ�.
     * <p><code>String strExam = fillZero(35, 4); // "0035"</code></p>
     * @param iNum ����(������).
     * @param iLen ��ü �ڸ��� (iNum�� �ڸ����� iLen���� ������ �׸�ŭ �տ� 0�� ����)
     * @return ���ϴ� �ڸ�����ŭ �տ� 0�� ���� ���ڿ�
     */
    // -----------------------------------------------------------------
    public static String fillZero(int iNum, int iLen) {
        StringBuffer sbTmp = new StringBuffer(Integer.toString(iNum));
        while (sbTmp.length() < iLen) {
            sbTmp.insert(0, "0");
        } // end while
        return sbTmp.toString();
    } //end fillZero

    // --------------------------------------------------------------------



//=============================================================================
// ���ڿ��� DataSource ��ü�� ��ȯ�ϱ� ���� ByteAraayDataSource Ŭ����.
// (���� �ҽ����� Character Set �ڵ鸵 �κи� ������)
//=============================================================================


    /**
     * A simple DataSource for demonstration purposes.
     * This class implements a DataSource from:
     * 	an InputStream
     *	a byte array
     * 	a String
     * Copyright 1998-2000 Sun Microsystems, Inc. All Rights Reserved.
     * @author John Mani
     * @author Bill Shannon
     * @author Max Spivak
     */
    class ByteArrayDataSource
        implements DataSource {

        private byte[] data; // data
        private String type; // content-type

        /** Create a DataSource from an input stream */
        public ByteArrayDataSource(InputStream is, String type) {
            this.type = type;
            try {
                ByteArrayOutputStream os = new ByteArrayOutputStream();
                int ch;

                while ( (ch = is.read()) != -1) {

                    // XXX - must be made more efficient by
                    // doing buffered reads, rather than one byte reads
                    os.write(ch);
                }
                data = os.toByteArray();
            }
            catch (IOException ioex) {}
        }

        /** Create a DataSource from a byte array */
        public ByteArrayDataSource(byte[] data, String type) {
            this.data = data;
            this.type = type;
        }

        /** Create a DataSource from a String */
        public ByteArrayDataSource(String data, String type, String charset) {
            try {
                // Assumption that the string contains only ASCII
                // characters!  Otherwise just pass a charset into this
                // constructor and use it in getBytes()
                this.data = data.getBytes(charset);
            }
            catch (UnsupportedEncodingException uex) {}
            this.type = type;
        }

        /**
         * Return an InputStream for the data.
         * Note - a new stream must be returned each time.
         */
        public InputStream getInputStream() throws IOException {
            if (data == null) {
                throw new IOException("no data");
            }
            return new ByteArrayInputStream(data);
        }

        public OutputStream getOutputStream() throws IOException {
            throw new IOException("cannot do this");
        }

        public String getContentType() {
            return type;
        }

        public String getName() {
            return "dummy";
        }

    } // end class ByteArrayDataSource

    //Added by dklee for replace string
    /**
     * String Replace ó��
     * @param original String ���� ���ڿ�
     * @param oldstr String ��� ���ڿ�
     * @param newstr String ��ġ ���ڿ�
     * @return
     */
    public static String replace(String original, String oldstr, String newstr) {
        String convert = new String();
        int pos = 0;
        int begin = 0;
        pos = original.indexOf(oldstr);

        if (pos == -1) {
            return original;
        }

        while (pos != -1) {
            convert = convert + original.substring(begin, pos) + newstr;
            begin = pos + oldstr.length();
            pos = original.indexOf(oldstr, begin);
        }
        convert = convert + original.substring(begin);

        return convert;
    }

    //Added by dklee for �ѱ۸� Decoding
    /**
     * (Base64)���ڵ��Ǿ��ִ� ���ڿ��� ���ڵ� ó��
     * @param text String ���ڵ� ��� ���ڿ�
     * @return
     */
    public String decodeText(String text) {
        String result = text;
        try {
            result = MimeUtility.decodeText(text);
        }
        catch (UnsupportedEncodingException e) {
            e.printStackTrace(System.err);
        }
        return result;
    }


    /**
     * HTML Tag ����
     * @param html String ���� HTML ���ڿ�
     * @return String HTML Tag�� ���ŵ� ���ڿ�
     */
    public static String simpleHTMLTagDiscard(String html) {
        String convert = "";
        try {

            Pattern p = Pattern.compile("^.*<\\s*BODY[^>]*>[\\s|\\n]*",
                                        Pattern.DOTALL);
            Matcher m = p.matcher(html);
            html = m.replaceAll("");
            p = Pattern.compile("<\\s*/BODY[^>]*>.*$", Pattern.DOTALL);
            m = p.matcher(html);
            html = m.replaceAll("");
            convert = html.replaceAll("<[^>]+>|(&nbsp;)", "");
        }
        catch (Exception e) {
            e.printStackTrace(System.err);
        }
        return convert;
    }


    public static void main(String[] args) {

    }

} // end class SmeNamoMime
