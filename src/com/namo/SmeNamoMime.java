/* =============================================================================
 CLASS : smipc.util.SmeNamoMime			Yoon, Tae-Hyun Yoon (2003-03-16)

 @modify by Lee, Do Kyoung(2003.07)
 CLASS : com.namo.SmeNamoMime
 -------------------------------------------------------------------------------
 Namo ActiveSquare를 이용해 작성된 MIME 문서를 디코딩하고, inline 첨부이미지 등을
 파일로 저장한 후 최종 완성된 본문을 반환해주는 처리를 수행하는 클래스.
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
/** Namo ActiveSquare를 이용해 작성된 MIME 문서를 디코딩하고, inline 이미지 등을
 * 파일로 저장한 후 최종 완성된 본문을 반환해주는 처리를 수행하는 클래스.
 *
 * <p><font size="-1">이 클래스는 NamoAS5로 작성된 문서를 MIMEValue로 변환해
 * 폼으로 넘어온 데이터를 서버쪽에서 디코딩하고, 첨부된 이미지 파일 등을 디스크에
 * 저장하고, 이에 대한 본문 참조(CID)를 바로잡는 역할을 수행함.
 * </font></p>
 *
 * <p><font size="-1">이 클래스는 JavaMail의 표준 MIME 처리 API를 사용하므로,
 * Namo ActiveSqure뿐만 아니라 작성데이터 및 로컬 인라인 이미지 등을 표준 MIME
 * 형식으로 넘겨주는 다른 HTML Editiing Control에도 적용할 수 있다.
 * 그러나 현재까지는 Namo ActiveSqure 상에서만 테스트 되었으므로, 다른 HTML
 * 에디팅 컨트롤과의 호환성에 대해서는 보장할 수 없다.
 * </font></p>
 *
 * <p><b>사용예 :</b></p><p><code>
 * SmeNamoMime namo = new SmeNamoMime(request.getParameter("content")); // 객체생성 <br>
 * boolean result = namo.parse(); // 실제 파싱 수행 <br>
 * if ( !result ) { // 파싱 실패시 <br>
 * &nbsp;&nbsp; out.println( namo.getDebugMsg() ); // 디버깅 메시지 출력 <br>
 * &nbsp;&nbsp; return; // 프로그램 수행 중단 <br>
 * } // if <br>
 * if ( namo.isMultipart() ) { // 문서가 멀티파트인지 판단 <br>
 * &nbsp;&nbsp; String fPath = "/home/www/userimages/"; // 파일 저장 경로 지정<br>
 * &nbsp;&nbsp; String refUrl = "/userimages/"; // 웹에서 저장된 파일을 접근하기 위한 경로<br>
 * &nbsp;&nbsp; String prefix = bbsCode + fillZero(articleNo, 6); // 파일명 접두어<br>
 * &nbsp;&nbsp; result = namo.saveFile(); // 실제 파일 저장 <br>
 * } // if <br>
 * if ( !result ) { // 파일저장 실패시 <br>
 * &nbsp;&nbsp; out.println( namo.getDebugMsg() ); // 디버깅 메시지 출력 <br>
 * &nbsp;&nbsp; return; // 프로그램 수행 중단 <br>
 * } // if <br>
 * String content = namo.getContent(); // 최종 컨텐트 얻기
 * </code></p>
 *
 * @author Yoon Tae-Hyun.
 * @version 2003.03.16.
 */
//==========================================================================

public class SmeNamoMime {

//=============================================================================
// 속성(Properties) 정의
//=============================================================================

    /** 원본 MIME 문서 (NamoAS의 MIMEValue) */
    private String mimeDoc;

    /** 원본 MIME 문서가 담길 속성 */
    private MimeBodyPart mbp;

    /** 멀티파트 문서가 담길 속성 */
    private MimeMultipart mmp;

    /** 변환이 완료된 본문이 담기는 속성 */
    private String content;

    /** Mime 문서에 포함된 파트 수 */
    private int partCount;

    /** <body>~</body>내의 컨텐츠만 사용할 것인지의 여부 */
    private boolean useBodyOnly;

    /** 디버깅 메시지 보관용 속성 */
    private StringBuffer debugMsg;

//=============================================================================
// 생성자(Constructor)
//=============================================================================


    //---------------------------------------------------------------------
    /** 생성자로서 Parsing할 Mime Document를 인자로 받는다.
     * @param mimedoc 폼을 통해 넘어온 Namo AS의 MIMEValue 데이터.
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
        debugMsg.append("[INFO] SmeNamoMime 객체가 생성됨.\n");
    } // end Contructor NamoMime

    //---------------------------------------------------------------------



//=============================================================================
// 내부(private) 처리 메소드
//=============================================================================


    //---------------------------------------------------------------------
    /** 문자열에서 &lt;body&gt;~&lt;/body&gt; 태그내의 내용만 남기고 나머지 태그는 제거.
     * @param str 원본 문자열.
     * @return &lt;body&gt;~&lt;/body&gt; 내의 내용.
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
// 속성인터페이스 메소드
//=============================================================================


    //---------------------------------------------------------------------
    /** Parsing이 완료된 본문 내용을 반환하는 메소드.
     * <p><font size="-1">
     * 이 메소드를 호출하기 전 반드시 {@link #parse} 메소드를 이용해,
     * 파싱 절차를 먼저 수행해야 하며, 멀티파트 문서일 경우에는 파싱 수행 후
     * {@link #saveFile} 메소드를 이용해 첨부된 파일을 저장하고 본문내 참조를
     * URL로 변경한 후에 이 메소드를 사용해야 올바른 본문 내용을 얻을 수 있다.
     * </font></p>
     * @return 문자열로된 본문 Content를 반환.
     */
    //------------------------------------------------------------------
    public String getContent() {
        return this.content;
    } // end getContent

    //---------------------------------------------------------------------


    //---------------------------------------------------------------------
    /** Parsing된 문서가 Multipart 문서인지 여부를 반환하는 메소드.
     * @return 반환값이 true이면 MultiPart 문서, false이면 단일파트 문서.
     */
    //------------------------------------------------------------------
    public boolean isMultipart() {
        return (this.partCount > 1) ? true : false;
    } // end isMultipart

    //---------------------------------------------------------------------


    //---------------------------------------------------------------------
    /** Mime 문서에 포함된 inline 첨부파일 수를 반환하는 메소드.
     * @return 반환값이 true이면 MultiPart 문서, false이면 단일파트 문서.
     */
    //------------------------------------------------------------------
    public int getFileCount() {
        return this.partCount - 1;
    } // end getFileCount

    //---------------------------------------------------------------------


    //---------------------------------------------------------------------
    /** 넘어온 문서 전체를 사용할 것인지, &lt;body&gt;~&lt;/body&gt;내의
     * 컨텐츠만을 사용할 것인지를 설정.
     * <p><font size="-1">
     * 설정하지 않으면 기본으로 body 태그내의 컨텐츠만 사용하게 됨.
     * 게시판 등에서는 body 내의 태그만 사용토록 하는 것이 전체 페이지 레이아웃을
     * 깨지 않으므로 안전하므로 기본값을 변경하지 않는 것이 좋으며, 메일 발송 등
     * 페이지 전체가 필요한 경우에만 사용한다.
     * </font></p>
     * @param charset 문자셋 이름.
     */
    //------------------------------------------------------------------
    public void useWholeDocument(boolean flag) {
        this.useBodyOnly = (flag) ? false : true;
    } // useWholeDocument

    //---------------------------------------------------------------------



    //---------------------------------------------------------------------
    /** 클래스 내부 처리중 발생한 디버깅 메시지를 반환하는 메소드.
     * @return 처리중 발생한 디버깅 메시지.
     */
    //------------------------------------------------------------------
    public String getDebugMsg() {
        return this.debugMsg.toString();
    } // getDebugMsg

    //---------------------------------------------------------------------



//=============================================================================
// 공용(public) 메소드 (객체 생성 후 사용가능)
//=============================================================================


    //---------------------------------------------------------------------
    /** Mime Document를 실제로 Parsing 하여, 첨부된 각 요소를 분리하고
     * 본문의 내용을 추출하는 메소드.
     * <p><font size="-1">
     * 본문이 단일파트로 되어 있으면 이 메소드 수행 후 바로 완성된 Content를
     * 얻을 수 있으나, 멀티파트 경우에는 {@link #saveFile} 메소드를 이용해
     * 파일을 저장하고, 본문내 파일 참조(CID)를 실제 URL로 변경하는 작업을
     * 해줘야 완전한 Content를 얻을 수 있다.
     * </font></p>
     * <p><font size="-1">
     * MIME 문서가 멀티파트인지 단일파트인지의 여부는 이 메소드 수행 후에
     * {@link #isMultipart} 메소드를 통해 알 수 있다.
     * </font></p>
     * @return Parsing에 성공하면 true, 실패하면 false 반환
     *                   (실패시 {@link #getDebugMsg} 메소드를 사용해 디버깅).
     */
    //------------------------------------------------------------------
    public boolean parse() {
        if (mimeDoc == null || !mimeDoc.startsWith("MIME-Version")) { // MIME 문서 검증
            debugMsg.append("[STOP] MIME 형식이 올바르지 않음.\n");
            return false;
        } // if

        // 문자열을 ByteArrayInputStream으로 --------------------------
        ByteArrayInputStream bais = null; // 문자열을 InputStream으로 변환.
        try {
            bais = new ByteArrayInputStream(mimeDoc.getBytes("euc-kr"));
        }
        catch (Exception e) {
            e.printStackTrace();
            debugMsg.append("[STOP] 문자열 변환 실패(ByteArrayInputStream).\n");
            debugMsg.append(e.getMessage());
            return false;
        } // try catch

        // InputStream을 이용해 MimeBodyPart 객체 생성 ----------------
        boolean blMulti = false;
        try {
            mbp = new MimeBodyPart(bais); // InputStream을 mbp 객체에 할당
            String conType = mbp.getContentType(); // 문서의 메인 Content-Type
            if (conType.startsWith("text")) { // Content-Type으로 멀티파트인지를 판단
                partCount = 1;
                debugMsg.append("[INFO] 단일파트 문서 : " + conType + "\n");
            }
            else if (conType.startsWith("multipart")) {
                blMulti = true;
                debugMsg.append("[INFO] 멀티파트 MIME 문서 : " + conType + "\n");
            }
            else {
                debugMsg.append("[STOP] 지원되지 않는 MIME 문서 : " + conType + "\n");
                return false;
            } // if

        }
        catch (Exception e) {
            e.printStackTrace();
            debugMsg.append("[STOP] MIME 문서의 Parsing 실패.\n");
            debugMsg.append(e.getMessage());
            return false;
        } // try catch

        // MimeBodyPart에서 본문 Content 배내기 -----------------------
        if (blMulti) { // 멀티파트 문서일 경우

            try {
                mmp = (MimeMultipart) mbp.getContent(); // 바디파트를 멀티파트로 변환
                partCount = mmp.getCount(); // 첨부파일 수 (전체 파트에서 본문을 제외한 나머지)
                MimeBodyPart bp = null;
                for (int i = 0; i < partCount; i++) { // 루프를 돌며 Content 찾기
                    bp = (MimeBodyPart) mmp.getBodyPart(i);
                    String conID = bp.getContentID(); // Content-ID
                    String conPos = bp.getDisposition(); // Content-Disposition
                    if (conID == null && conPos == null) { // 두 값이 null이면 본문임.
                        content = (String) bp.getContent();
                    } // if
                } // for
            }
            catch (Exception e) {
                e.printStackTrace();
                debugMsg.append("[STOP] 멀티파트 데이터 추출 실패.\n");
                debugMsg.append(e.getMessage());
                return false;
            } // try catch

        }
        else { // 단일파트 문서일 경우

            try {
                content = (String) mbp.getContent(); // 본문 컨텐츠 얻기
            }
            catch (Exception e) {
                e.printStackTrace(System.err);
                debugMsg.append("[STOP] 본문 추출 실패.\n");
                debugMsg.append(e.getMessage());
                return false;
            } // try catch

        } // if

        // Content 정리 -----------------------------------------------
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
    /** MIME 문서내에 포함된 inline 첨부 파일들을 디스크의 지정된 경로상에
     * 저장하고, 본문내의 CID를 이용한 파일참조를, 지정된 실제 URL로 변경시켜
     * 주는 메소드.
     *
     * <p><font size="-1">
     * 파일이 저장될 경로 지정시 반드시 플랫폼 호환성을 위해 마지막에
     * 디렉토리 구분자(유닉스는 /, 윈도는 \)를 붙여줘야 한다. <br>
     * - /home/www/images : 틀린 지정 <br>
     * - /home/www/images/ : 올바른 지정 <br>
     * - C:\home\www\images : 틀린 지정 <br>
     * - C:\home\www\images\ : 올바른 지정 <br>
     * (* 윈도의 경로명 지정시 Java의 문자열 지정 형식에 맞추기 위해
     *  "C:\\home\\www\\images\\"와 같이 \ 문자를 두개씩 넣어줘야 한다)
     * </font></p>
     *
     * <p><font size="-1">
     * 파일 접두어(prefix 인자)를 지정하지 않으면, 첨부된 원본 파일명을 그대로
     * 사용한다. 그러나 이 경우 플랫폼에 따른 파일명 형식 차이나, 웹서버나
     * 웹브라우져 상의 다국어 및 특수기호 URL 지원 문제 등으로 인해 파일저장에
     * 실패 하거나, 웹상에서 접근이 불가능할 수 있다.<br><br>
     *
     * 따라서 가급적 prefix를 지정해 파일 이름을 새로 지어주는 것이 문제발생의
     * 가능성을 차단하는데 유리하며, prefix를 글번호, 현재시간, 접근IP, 사용자
     * ID 등 의미를 갖는 값으로 지정해주면 차후 파일 검색이나 관리에 편리하게
     * 활용될 수 있다.<br><br>
     *
     * prefix 지정시 파일명은 prefix + 파일번호 + 원본파일의 확장자로 지정되며,
     * 그 예는 다음과 같다.<br>
     * - 20020315_133015(현재시간) : 20020315_133015.gif, 20020315_133015_001.gif, ...<br>
     * - 020315_yoonth(날짜+ID) : 020315_yoonth.gif, 020315_yoonth_001.gif, ...<br>
     * - free000001(게시판코드+글번호) : free000001.gif, free000001_001.gif, ...<br>
     * </font></p>
     *
     * <p><code>
     * </code></p>
     * @param bpath 파일이 저장될 디렉토리를 절대경로로 지정
     *              (예: /home3/www/userimages/).
     * @param burl  웹상에서 실제 파일을 참조할 수 있는 URL 경로 지정.
     *              (예: /userimages/).
     * @param prefix 파일이름에 사용할 접두어로서 만약 null값이나 ""값이
     *               넘어오면 첨부된 원본 파일명을 그대로 사용한다.(예: 20020315_1330).
     * @return 파일저장에 성공하면 true 반환, 실패하면 false 반환.
     */
    //------------------------------------------------------------------
    public boolean saveFile(String bpath, String burl, String prefix) {
        if (partCount < 2) {
            debugMsg.append("[STOP] No embeded file.\n");
            return false;
        } // if
        if (bpath == null || burl == null) {
            debugMsg.append("[STOP] Base path 또는 base URL이 지정되지 않음.\n");
            return false;
        } // if
        if (!bpath.endsWith("\\") && !bpath.endsWith("/")) {
            bpath += "/";
        } // if
        boolean useNewName = (chkStr(prefix)) ? true : false;

        try {
            for (int i = 0; i < partCount; i++) {

                // MIME 파트 분석 -----------------------------
                MimeBodyPart bp = (MimeBodyPart) mmp.getBodyPart(i);
                String conID = bp.getContentID(); // Content-ID
                String conPos = bp.getDisposition(); // Content-Disposition
                if (conID == null && conPos == null) {
                    continue; // 두 값이 null이면 본문임.
                }

                // 파일 이름 만들기 ---------------------------

                /////////////////////한글 파일 이름/////////////////////
                String tempName = this.decodeText(bp.getFileName());
                //////////////////////////////////////////////////////

                String fname = I2K(tempName);
                String fext = afterStr(fname, ".");
                if (!fext.equals("")) {
                    fext = "." + fext;
                }
                fname = (useNewName) ? uniqFileName(bpath, prefix + fext) :
                    urlSafeFileName(fname);

                // 실제 파일 저장 -----------------------------
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

                // CID 매핑 수정 ------------------------------
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
            debugMsg.append("[STOP] MIME 첨부파일 저장 실패.\n");
            debugMsg.append(e.getMessage());
            return false;

        } // try catch

        debugMsg.append("[INFO] 파일 저장 성공.\n");
        return true;
    } // saveFile

    //---------------------------------------------------------------------



//=============================================================================
// 유틸리티 메소드 (배포를 위해 다른 클래스에 있는 메소드들을 가져옴)
//=============================================================================


    // --------------------------------------------------------------------
    /** 인자로 주어진 문자열이 유효한(null이 아니고 값이 포함된) 문자열인지를
     * 검사해서 boolean 값을 반환.
     * <p><code>if ( chkStr(str) ) {<br>
     * &nbsp;&nbsp; out.println("문자열이 유효하지 않습니다.");<br>
     * }</code></p>
     * @param strParam 검사할 문자열
     * @return 주어진 문자열이 유효하면 true를, 유효하지 않으면 false를 반환
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
    /** ISO-8859_1 형태의 스트링을 KSC-5601로 변환하여 리턴 (한글이 깨질때 사용).
     * <p><code>String kscstr = I2K(isostr);</code></p>
     * @param in 변환할 ISO-8859_1 캐릭터셋의 문자열
     * @return KSC-5601 캐릭터셋의 문자열
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
    /** 원본 문자열에서 지정한 구분자가 마지막으로 나타나는 위치 이후의 문자열을 반환.
     * <p><code>
     * out.println(beforeStr("my.picture.gif", ".")); // my.picture 출력 <br>
     * out.println(afterStr("my.picture.gif", ".")); // gif 출력<br>
     * </code></p>
     * @param src 원본 문자열.
     * @param delim 구분자.
     * @return 마지막 구분자 뒤에 나타나는 문자열 또는 ""(찾지못했을때).
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
    /** 원본 문자열에서 지정한 구분자가 마지막으로 나타나는 위치 이전의 문자열을 반환.
     * <p><code>
     * out.println(beforeStr("my.picture.gif", ".")); // my.picture 출력 <br>
     * out.println(afterStr("my.picture.gif", ".")); // gif 출력<br>
     * </code></p>
     * @param src 원본 문자열.
     * @param delim 구분자.
     * @return 마지막 구분자 앞에 나타나는 문자열 또는 ""(찾지못했을때).
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
    /** 주어진 파일 이름을 웹을 통한 URL 접근시에도 정상적으로 인식되도록
     * 공백이나 기타 URL 기호들을 밑줄(_)기호로 바꿔주는 메소드.
     * @param fname 파일명.
     * @return 공백 및 URL 관련 기호들이 밑줄(_)로 대치된 파일명.
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
    /** 지정한 경로명과 파일명을 이용해 존재하지 않는 파일명을 만들어 반환.
     * <p>만약 동일한한 파일이 존재하면 파일명 뒤에 일련번호를
     * 붙이는 방식으로 고유한 파일명을 생성</p>
     * <p><code>
     * // 만약 log.txt가 있으면 log_001.txt가 반환됨.<br>
     * String myFile = uniqFileName("/home3/www/", "log.txt");
     * </code></p>
     * @param fpath 파일의 절대 경로 (플랫폼 호환성 유지를 위해 가장 뒤에는
     *              꼭 디렉토리 구분기호를 붙여야 함).
     * @param fname 기본 파일명 (지정되지 않으면 현재의 timestamp 값을 이용).
     * @return 고유한 파일명 또는 null(디렉토리가 올바르지 않는 등 오류 발생시).
     */
    //------------------------------------------------------------------
    private String uniqFileName(String fpath, String fname) {
        if (!chkStr(fpath)) {
            return null; // 경로가 잘못되면 null 반환
        }
        File baseDir = new File(fpath);
        if (!baseDir.exists() || !baseDir.isDirectory()) {
            return null;
        }
        if (!chkStr(fname)) { // 기본 파일명이 지정안되면 현재 timestamp 사용
            fname = String.valueOf(new Date().getTime());
        } // if

        String namepart = beforeStr(fname, "."); // 파일의 이름 부분 얻기
        String extpart = afterStr(fname, "."); // 파일의 확장자 부분 얻기
        if (!extpart.equals("")) {
            extpart = "." + extpart;
        }
        String tmpname = namepart;

        File tmpFile = new File(fpath + tmpname + extpart);
        int num = 0;
        while (tmpFile.exists()) { // 파일이 존재하는 동안 루프를 돌며 새 파일명 작성
            num++;
            tmpname = namepart + "_" + fillZero(num, 3); // 파일 일련번호
            tmpFile = new File(fpath + tmpname + extpart);
        } // while
        return tmpname + extpart;
    } // uniqFileName

    // --------------------------------------------------------------------


    // --------------------------------------------------------------------
    /** 입력된 숫자에 원하는 자리수만큼 앞에 0을 붙여 문자열로 반환하는 메소드.
     * <p><code>String strExam = fillZero(35, 4); // "0035"</code></p>
     * @param iNum 숫자(정수형).
     * @param iLen 전체 자릿수 (iNum의 자릿수가 iLen보다 적으면 그만큼 앞에 0을 붙임)
     * @return 원하는 자릿수만큼 앞에 0이 붙은 문자열
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
// 문자열을 DataSource 객체로 변환하기 위한 ByteAraayDataSource 클래스.
// (원본 소스에서 Character Set 핸들링 부분만 수정함)
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
     * String Replace 처리
     * @param original String 원본 문자열
     * @param oldstr String 대상 문자열
     * @param newstr String 대치 문자열
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

    //Added by dklee for 한글명 Decoding
    /**
     * (Base64)인코딩되어있는 문자열을 디코딩 처리
     * @param text String 디코딩 대상 문자열
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
     * HTML Tag 제거
     * @param html String 원문 HTML 문자열
     * @return String HTML Tag가 제거된 문자열
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
