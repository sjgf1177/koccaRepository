package com.credu.library;

// Copyright (C) 1998 by Jason Hunter <jhunter@acm.org>.  All rights reserved.
// Use of this class is limited.  Please see the LICENSE for more information.

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Array;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import javax.servlet.ServletInputStream;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

/**
 * A utility class to handle <tt>multipart/form-data</tt> requests, the kind of
 * requests that support file uploads. This class can receive arbitrarily large
 * files (up to an artificial limit you can set), and fairly efficiently too. It
 * cannot handle nested data (multipart content within multipart content) or
 * internationalized content (such as non Latin-1 filenames).
 * <p>
 * It's used like this: <blockquote>
 * 
 * <pre>
 * MultipartRequest multi = new MultipartRequest(req, &quot;.&quot;);
 * 
 * out.println(&quot;Params:&quot;);
 * Enumeration params = multi.getParameterNames();
 * while (params.hasMoreElements()) {
 *     String name = (String) params.nextElement();
 *     String value = multi.getParameter(name);
 *     out.println(name + &quot; = &quot; + value);
 * }
 * out.println();
 * 
 * out.println(&quot;Files:&quot;);
 * Enumeration files = multi.getFileNames();
 * while (files.hasMoreElements()) {
 *     String name = (String) files.nextElement();
 *     String filename = multi.getFilesystemName(name);
 *     String type = multi.getContentType(name);
 *     File f = multi.getFile(name);
 *     out.println(&quot;name: &quot; + name);
 *     out.println(&quot;filename: &quot; + filename);
 *     out.println(&quot;type: &quot; + type);
 *     if (f != null) {
 *         out.println(&quot;f.toString(): &quot; + f.toString());
 *         out.println(&quot;f.getName(): &quot; + f.getName());
 *         out.println(&quot;f.exists(): &quot; + f.exists());
 *         out.println(&quot;f.length(): &quot; + f.length());
 *         out.println();
 *     }
 * }
 * </pre>
 * 
 * </blockquote>
 * 
 * A client can upload files using an HTML form with the following structure.
 * Note that not all browsers support file uploads. <blockquote>
 * 
 * <pre>
 * &lt;FORM ACTION="/servlet/Handler" METHOD=POST
 *          ENCTYPE="multipart/form-data"&gt;
 * What is your name? &lt;INPUT TYPE=TEXT NAME=submitter&gt; &lt;BR&gt;
 * Which file to upload? &lt;INPUT TYPE=FILE NAME=file&gt; &lt;BR&gt;
 * &lt;INPUT TYPE=SUBMIT&GT;
 * &lt;/FORM&gt;
 * </pre>
 * 
 * </blockquote>
 * <p>
 * The full file upload specification is contained in experimental RFC 1867,
 * available at <a href="http://ds.internic.net/rfc/rfc1867.txt">
 * http://ds.internic.net/rfc/rfc1867.txt</a>.
 * 
 *@author <b>Jason Hunter</b>, Copyright &#169; 1998-1999
 *@version 1.1, 99/01/15, JSDK readLine() bug workaround
 *@version 1.0, 98/09/18
 */
@SuppressWarnings("unchecked")
public class MultipartRequest {

    private static final int DEFAULT_MAX_POST_SIZE = 0;

    private final ServletRequest req;
    private File dir;
    private int maxSize;
    private int retval = 1; // 정상처리(서버에 잘 올라간 것으로 간주)
    private String servletName;
    private String userid;

    /**
     * 파라미터의 'name', 'value' 값을 저장하고 있는 hashtable 객체
     */
    public Hashtable parameters = new Hashtable(); // name - value

    /**
     * 업로드되는 file 의 'name', 'UploadedFile' 값을 저장하고 있는 hashtable 객체
     */
    public Hashtable files = new Hashtable(); // name - UploadedFile

    /**
     * 업로드되는 file 의 'name', 'realFileName'(클라이언트에서 업로드되는 파일명) 값을 저장하고 있는
     * hashtable 객체
     */
    public Hashtable realFileParameters = new Hashtable(); //     클라이언트에서 업로드되는 파일명들

    /**
     * 업로드되는 file 의 'name', 'newFileName'(서버에 저장될 새로운 파일명) 값을 저장하고 있는 hashtable
     * 객체
     */
    public Hashtable newFileParameters = new Hashtable(); //      서버에 저장될 새로운 파일명들

    /**
     * 파일이 서버에 업로드가 제대로 이루어졌는가를 저장하는 hashtable, '1' 이면 정상적으로 업로드 됨, '0' 이면 파일사이즈
     * 초과(서버에 저장안됨)
     */
    public Hashtable fileupstatus = new Hashtable(); //      파일이 서버에 업로드가 제대로 이루어졌는가를 저장하는 hashtable
    //      '1' 이면 정상적으로 업로드 됨, '0' 이면 파일사이즈 초과(서버에 저장안됨)

    public Vector realFileVector = new Vector();
    public Vector newFileVector = new Vector();

    /**
     * request 객체를 인자로 받는 생성자
     * 
     * @param request ServletRequest의 request 객체를 단일인자로 받음
     */
    public MultipartRequest(ServletRequest request) throws Exception {
        this(request, ".", DEFAULT_MAX_POST_SIZE);
    }

    /**
     * request 객체와 업로드될 디렉토리명을 인자로 받는 생성자
     * 
     * @param request ServletRequest의 request 객체를 인자로 받음
     * @param saveDirectory 파일업로드될 디렉토리명을 인자로 받음
     */
    public MultipartRequest(ServletRequest request, String saveDirectory) throws Exception {
        this(request, saveDirectory, DEFAULT_MAX_POST_SIZE);
    }

    public MultipartRequest(ServletRequest request, String saveDirectory, String servletName, String userid) throws Exception {
        this(request, saveDirectory, DEFAULT_MAX_POST_SIZE);
        this.servletName = servletName;
        this.userid = userid;
        //System.out.println(" saveDirectory=================>" + saveDirectory );
        //System.out.println(" servletName=================>" + servletName );
    }

    /**
     * request 객체, 업로드될 디렉토리명, 업로드될 파일사이즈의 최대 허용값을 인자로 받는 생성자
     * 
     * @param request ServletRequest의 request 객체를 인자로 받음
     * @param saveDirectory 파일업로드될 디렉토리명을 인자로 받음
     * @param maxPostSize 업로드될 파일사이즈의 최대 허용값을 인자로 받음
     */
    public MultipartRequest(ServletRequest request, String saveDirectory, int maxPostSize) throws Exception {
        if (request == null) {
            throw new Exception("MultipartRequest.MultipartRequest(ServletRequest,\"" + saveDirectory + "\"," + maxPostSize + ")\r\nrequest cannot be null");
        }
        req = request;

        if (saveDirectory == null) {
            throw new Exception("MultipartRequest.MultipartRequest(ServletRequest," + null + "," + maxPostSize + ")\r\nsaveDirectory cannot be null");
        } else if (!saveDirectory.equals("")) {
            this.setUploadDir(saveDirectory); // 업로드 디렉토리 지정
        }

        this.setMaxPostSize(maxPostSize); // 업로드 제한사이즈 지정
    }

    /**
     * 업로드할 디렉토리 객체를 생성한다
     * 
     * @param saveDirectory 업로드할 디렉토리명
     */
    public void setUploadDir(String saveDirectory) throws Exception {
        dir = new File(saveDirectory);

        if (!dir.isDirectory()) { // 지정된 디렉토리가 존재여부 그리고 파일인지 체크
            if (!dir.mkdirs()) { // 디렉토리가 없으면 새로 생성한다.
                throw new Exception("MultipartRequest.setUploadDir(\"" + saveDirectory + "\")\r\nCannot make directory");
            }
        }
        if (!dir.canWrite()) { // 디렉토리에 쓰기권한이 있는지여부 체크
            throw new Exception("MultipartRequest.setUploadDir(\"" + saveDirectory + "\")\r\nNot writable");
        }
    }

    /**
     * 업로드된 디렉토리명을 얻는다
     * 
     * @return 업로드된 디렉토리명
     */
    public String getUploadDir() {
        return (dir != null) ? dir.toString() : "";
    }

    /**
     * 업로드할 파일의 제한 사이즈 지정
     * 
     * @param maxPostSize 파일사이즈, 0 이면 파일사이즈 무제한
     */
    public void setMaxPostSize(int maxPostSize) throws Exception {
        if (maxPostSize < 0) {
            throw new Exception("MultipartRequest.setMaxPostSize(" + maxPostSize + ")\r\nmaxPostSize cannot be negative");
        }
        this.maxSize = maxPostSize * 1000000;
    }

    /**
     * multipart 형식으로 넘어온 파라미터의 'name' 들을 얻는다
     * 
     * @return Enumeration 파라미터의 'name'을 String 형인 Enum 형태로 반환한다
     */
    public Enumeration getParameterNames() {
        return parameters.keys();
    }

    /**
     * multipart 형식으로 넘어온 파라미터의 'name' 에 해당하는 'value' 값을 얻는다
     * 
     * @return String 파라미터의 'name'에 해당되는 String 형인 'value' 값을 반환한다
     */
    public String getParameter(String name) {
        return (String) parameters.get(name);
    }

    /**
     * 서버에 업로드되어 저장될 새로운 파일명으로 교체하여 'newFileParameters' hashtable 에 저장한다
     * 
     * @param paramName 업로드되는 파라미터의 name 을 인자로 받음
     * @param newFileName 서버에 저장된 새로운 파일명
     */
    public void setNewFileName(String paramName, String newFileName) throws Exception {
        newFileParameters.put(paramName, newFileName);
    }

    /**
     * 서버에 업로드되어 저장된 새로운 파일명을 얻는다
     * 
     * @param paramName 업로드되는 파라미터의 name 을 인자로 받음
     * @return 새로운 파일명을 반환한다.
     */
    public String getNewFileName(String paramName) {
        String s = this.getFilesystemName(paramName);
        if (s == null)
            s = "";
        return s;
    }

    /**
     * 클라이언트에서 업로드된 실제 파일명을 얻는다
     * 
     * @param paramName 업로드되는 파라미터의 name 을 인자로 받음
     * @return 실제 파일명을 반환한다.
     */
    public String getRealFileName(String paramName) {
        try {
            String s = (String) realFileParameters.get(paramName);
            if (s == null)
                s = "";
            return s;
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * 업로드 후 서버에 저장된 파일의 상태를 얻는다. '1' 이면 정상적으로 업로드 됨, '0' 이면 파일사이즈 초과(서버에 저장안됨)
     * 
     * @return Hashtable 파일의 상태가 저장된 hashtable 객체를 반환한다
     */
    public Hashtable getFileUploadStatus() {
        return fileupstatus;
    }

    /**
     * 업로드 후 서버에 저장된 파일의 상태를 얻는다.
     * 
     * @return int 파일의 상태룰 정상처리인 경우 1, 비정상인 경우 0 을 반환한다
     */
    public int getFileUploadStatus(String name) {
        int retval = 0; // 업로드 안된 상태.

        Integer fstatus = (Integer) fileupstatus.get(name);
        if (fstatus != null) {
            retval = fstatus.intValue();
        }
        return retval; // 0:업로드 안됨(파일 사이즈 초과), 1:업로드 됨.
    }

    /**
     * multipart 형식으로 넘어온 파일 파라미터의 'name' 들을 얻는다
     * 
     * @return Enumeration 파일 파라미터의 'name'을 String 형인 Enum 형태로 반환한다
     */
    public Enumeration getFileNames() {
        return files.keys();
    }

    /**
     * 업로드 후 서버에 저장된 새로운 파일명(확장자 포함)을 얻는다.
     * 
     * @param name 업로드되는 파일의 태그명
     * @return filename 업로드된 새로운 파일명을 반환한다
     */
    public String getFilesystemName(String name) {
        try {
            UploadedFile upfile = (UploadedFile) files.get(name);
            return upfile.getFilesystemName(); // may be null
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 업로드 당시에 클라이언트 브라우저에서 지원된 파일의 ContentType 을 얻는다
     * 
     * @param name 업로드되는 파일의 태그명
     * @return content type 업로드된 파일의 ContentType을 반환한다
     */
    public String getContentType(String name) {
        try {
            UploadedFile upfile = (UploadedFile) files.get(name);
            return upfile.getContentType(); // may be null
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 업로드 후 서버에 저장된 파일객체를 얻는다
     * 
     * @param name 업로드되는 파일의 태그명
     * @return file 업로드된 파일의 객체를 반환한다
     */
    public File getFile(String name) {
        try {
            UploadedFile upfile = (UploadedFile) files.get(name);
            return upfile.getFile(); // may be null
        } catch (Exception e) {
            return null;
        }
    }

    /**
     *request 객체를 받아 box hashtable 에 parameters 와 원파일명, 새로운파일명을 담아 반환한다.
     * 
     * @param request HttpServletRequest 의 request 객체를 인자로 받음
     * @return RequestBox request 객체에서 받은 파라미터 name 과 value, realfilename,
     *         newfilename 을 담은 hashtable 객체를 반환함
     */
    public RequestBox getBox(HttpServletRequest request) {
        RequestBox box = new RequestBox("requestbox");

        Enumeration e = parameters.keys();

        while (e.hasMoreElements()) {
            String key = (String) e.nextElement();

            Object o = parameters.get(key);
            Class c = o.getClass();
            if (o != null) {
                if (c.isArray()) {
                    box.put(key, parameters.get(key));
                } else {
                    String value = (String) parameters.get(key);
                    if (value == null)
                        value = "";
                    box.put(key, value);
                }
            }

        }
        Enumeration e1 = realFileParameters.keys();//System.out.println("realFileParameters.size() : " + realFileParameters.size());

        while (e1.hasMoreElements()) {
            String key = (String) e1.nextElement();

            Object o1 = realFileParameters.get(key);
            if (o1 != null) {
                box.put(key + "_real", realFileParameters.get(key));
            }

            Object o2 = newFileParameters.get(key);
            if (o2 != null) {
                box.put(key + "_new", newFileParameters.get(key));
            }
        }
        return box;
    }

    /**
     * 파일이 업로드된다
     */
    public void readRequest() throws Exception {
        // Check the content type to make sure it's "multipart/form-data"
        String type = req.getContentType();

        if (type == null || !type.toLowerCase().startsWith("multipart/form-data")) {
            throw new Exception("MultipartRequest.readRequest()\r\nPosted content type isn't multipart/form-data");
        }

        // Check the content length to prevent denial of service attacks
        int length = req.getContentLength();

        // Get the boundary string; it's included in the content type.
        // Should look something like "------------------------12012133613061"
        String boundary = this.extractBoundary(type);
        if (boundary == null) {
            throw new Exception("MultipartRequest.readRequest()\r\nSeparation boundary was not specified");
        }

        // Construct the special input stream we'll read from
        MultipartInputStreamHandler in = new MultipartInputStreamHandler(req.getInputStream(), boundary, length);

        // Read the first line, should be the first boundary
        String line = in.readLine();

        if (line == null) {
            throw new Exception("MultipartRequest.readRequest()\r\nCorrupt form data: premature ending");
        }

        // Verify that the line is the boundary
        if (!line.startsWith(boundary)) {
            throw new Exception("MultipartRequest.readRequest()\r\nCorrupt form data: no leading boundary");
        }

        // Now that we're just beyond the first boundary, loop over each part
        boolean done = false;
        while (!done) {
            done = this.readNextPart(in, boundary);
        }
    }

    /**
     * 업로드때 넘어오는 파라미터들은 'parameters' 에 name 별로 구분하여 value를 저장하고 파일은 'files' 에
     * file명 별로 구분하여 UploadedFile 객체를 저장한다
     * 
     * @param in MultipartInputStreamHandler 객체를 인자로 받는다
     * @param boundary 분리할 부분을 나누어주는 표식자를 받는다
     * @return 마지막 부분인지 여부를 반환한다
     * @exception Exception 만일 read하거나 parsing 할때 문제가 발생하면 예외처리한다. request
     * @see readParameter
     * @see readAndSaveFile
     */
    protected boolean readNextPart(MultipartInputStreamHandler in, String boundary) throws Exception {
        // Read the first line, should look like this:
        // content-disposition: form-data; name="field1"; filename="file1.txt"
        String line = in.readLine();
        if (line == null) { // No parts left, we're done
            return true;
        }

        // Parse the content-disposition line
        String[] dispInfo = this.extractDispositionInfo(line);
        // String disposition = dispInfo[0];
        String name = dispInfo[1];
        String filename = dispInfo[2];

        //       System.out.println("disposition : " + disposition);
        //     System.out.println("name : " + name);
        //   System.out.println("filename : " + filename);
        // Now onto the next line.  This will either be empty
        // or contain a Content-Type and then an empty line.
        line = in.readLine();
        if (line == null) {
            // No parts left, we're done
            return true;
        }

        // Get the content type, or null if none specified
        String contentType = extractContentType(line);
        if (contentType != null) { // Eat the empty line
            line = in.readLine();
            if (line == null || line.length() > 0) { // line should be empty
                throw new Exception("MultipartRequest.readNextPartFile(MultipartInputStreamHandler,\"" + boundary + "\")\r\nMalformed line after content type: " + line);
            }
        } else { // Assume a default content type
            contentType = "application/octet-stream";
        }

        // Now, finally, we read the content (end after reading the boundary)
        if (filename == null || filename.equals("")) { // This is a param
            //parameters.put(name, value);
            String value = this.readParameter(in, boundary); //System.out.println("this.readParameter() : " + value);
            boolean blChk = parameters.containsKey(name);
            // Modified by ICARUS.. 2003.10.27
            if (blChk) {
                //existArray = new String [20];
                Object o = parameters.get(name);
                Class c = o.getClass();
                if (c.isArray()) {
                    int length = Array.getLength(o);
                    String[] existArray = new String[length];
                    String[] newArray = new String[(length + 1)];
                    existArray = (String[]) parameters.get(name);
                    for (int k = 0; k < existArray.length; k++) {
                        newArray[k] = existArray[k];
                    }
                    newArray[length] = value;

                    parameters.remove(name);
                    parameters.put(name, newArray);

                } else {
                    String existValue = (String) parameters.get(name);
                    String[] newArray = { existValue, value };

                    parameters.remove(name);
                    parameters.put(name, newArray);
                }
            } else {
                parameters.put(name, value.toString());
            }
        } else { //     This is a file param
            realFileVector.addElement(name + "|" + filename);//System.out.println("real:"+filename);
            filename = this.getSaveNewFileName(name, filename, realFileVector.size()); //      서버에 저장할 파일명을 바꾼다
            newFileVector.addElement(name + "|" + filename); //System.out.println("new:"+filename);

            realFileParameters.put(name, realFileVector); //      클라이언트의 파일명을 'realFileParameters' hashtable 에 넣는다.
            newFileParameters.put(name, newFileVector); //      클라이언트의 파일명을 'realFileParameters' hashtable 에 넣는다.

            fileupstatus.put(name, new Integer(readAndSaveFile(in, boundary, filename)));
            //      업로드된 파일의 상태(제한크기로 제대로 저장되있는지 여부)를 'fileupstatus' hashtable 에 넣는다.

            if (!filename.equals("")) {
                files.put(name, new UploadedFile(dir.toString(), filename, contentType));
                //  새로운 파일명으로 객체화된 UploadedFile 을 'files' hashtable 에 넣는다.
            }
        }
        return false; // there's more to read
    }

    /**
     * 업로드 후 서버에 저장할 파일명을 'realFileName' 에서 'newFileName' 으로 교체한다.
     * 
     * @param paramName 업로드되는 파라미터의 name 을 인자로 받음
     * @param realFileName 클라이언트에서 업로드되는 파일명을 인자로 받음
     * @return 서버로 저장될 새로운 파일명(확장자가 포함된)을 반환한다
     */
    /*
     * protected String getSaveFileName(String paramName, String realFileName) {
     * String newFileName = (String)newFileParameters.get(paramName);
     * 
     * if ( !(newFileName == null || newFileName.equals("")) ||
     * realFileName.equals("")) { // 파일이름이 "" 이 아니고 지정된 새이름이 null 이거나 "" 이 아니면
     * String fileExtension = ""; int index = realFileName.lastIndexOf('.');
     * 
     * if ( index >= 0) { // 확장자가 있으면 fileExtension =
     * realFileName.substring(index+1); // 확장자를 저장 }
     * 
     * if (!fileExtension.equals("")) { newFileName = newFileName + "." +
     * fileExtension; // 새로운 파일명을 만든다 } } else { // 새이름이 null 이거나 공백일경우 / 업로드파일의
     * 이름이 공백일경우 (null 일수는 없다) 는 기존의 파일명을 그대로 사용한다 newFileName = realFileName; }
     * return newFileName; }
     */

    protected String getSaveNewFileName(String paramName, String realFileName, int k) {
        String newFileName = "";
        try {
            String fileExtension = "";
            String v_currentDate = FormatDate.getDate("yyyyMMddHHmmss");

            int index = realFileName.lastIndexOf('.');

            if (index >= 0) { // 확장자가 있으면
                fileExtension = realFileName.substring(index + 1); // 확장자를 저장
            }

            if (!fileExtension.equals("")) {
                newFileName = servletName + "_" + paramName.substring(2) + "_" + v_currentDate + k + "_" + userid + "." + fileExtension; // 새로운 파일명을 만든다
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newFileName;
    }

    /**
     * 업로드되는 각각 파라미터의 'value' 를 String 형으로 반환한다
     * 
     * @param in MultipartInputStreamHandler 객체를 인자로 받는다
     * @param boundary 분리할 부분을 나누어주는 표식자를 받는다
     * @return sbuf 읽어드린 파일을 String 형으로 반환한다.
     */
    protected String readParameter(MultipartInputStreamHandler in, String boundary) throws Exception {
        StringBuffer sbuf = new StringBuffer();
        String line;
        try {
            while ((line = in.readLine()) != null) {
                if (line.startsWith(boundary))
                    break;
                sbuf.append(line + "\r\n"); // add the \r\n in case there are many lines
            }
            if (sbuf.length() == 0) {
                return null; // nothing read
            }
            sbuf.setLength(sbuf.length() - 2); // cut off the last line's \r\n
        } catch (Exception ex) {
            throw new Exception("MultipartRequest.readParameter(MultipartInputStreamHandler,\"" + boundary + "\")");
        }
        return sbuf.toString(); // no URL decoding needed
    }

    /**
     * 업로드되는 파일을 읽고 새로운 파일명으로 저장한다.
     * 
     * @param in MultipartInputStreamHandler 객체를 인자로 받는다
     * @param boundary 분리할 부분을 나누어주는 표식자를 받는다
     * @param filename 서버에 저장할 새로운 파일명을 인자로 받는다
     * @return retval 설정한 최대 파일사이즈와 비교하여 최대파일사이즈보다 크면 삭제한다. 0 을 반환한다. 정상처리되면 1 을
     *         반환한다.
     */
    protected int readAndSaveFile(MultipartInputStreamHandler in, String boundary, String filename) throws Exception {

        try {
            boolean save = !filename.equals(""); // 저장여부 설정

            File f = null;
            FileOutputStream fos = null;
            BufferedOutputStream out = null;

            if (save) {
                f = new File(dir + File.separator + filename); //  서버에 새로운 파일명을 생성한다
                fos = new FileOutputStream(f);
                out = new BufferedOutputStream(fos, 8 * 1024); //   8K
            }
            byte[] bbuf = new byte[100 * 1024]; // 100K
            int result;
            String line;

            // ServletInputStream.readLine() has the annoying habit of
            // adding a \r\n to the end of the last line.
            // Since we want a byte-for-byte transfer, we have to cut those chars.
            boolean rnflag = false;
            while ((result = in.readLine(bbuf, 0, bbuf.length)) != -1) {
                if (save) { // Check for boundary
                    if (result > 2 && bbuf[0] == '-' && bbuf[1] == '-') { // quick pre-check
                        line = new String(bbuf, 0, result, "KSC5601");
                        if (line.startsWith(boundary))
                            break;
                    }

                    // Are we supposed to write \r\n for the last iteration?
                    if (rnflag) {
                        out.write('\r');
                        out.write('\n');
                        rnflag = false;
                    }
                    // Write the buffer, postpone any ending \r\n
                    if (result >= 2 && bbuf[result - 2] == '\r' && bbuf[result - 1] == '\n') {
                        out.write(bbuf, 0, result - 2); // skip the last 2 chars
                        rnflag = true; // make a note to write them on the next iteration
                    } else {
                        out.write(bbuf, 0, result);
                    }
                }
            }

            if (save) {
                out.flush();
                out.close();
                fos.close();
            }

            if (save) { // 파일 사이즈 체크..
                f = new File(dir + File.separator + filename);
                //          System.out.println("f.length() : " +  f.length());
                if ((maxSize > 0 && f.length() > maxSize) || retval == 0) {
                    f.delete();
                    retval = 0; // 파일 사이즈 초과
                }
            }
        } catch (Exception ex) {
            throw new Exception("MultipartRequest.readAndSaveFile(MultipartInputStreamHandler,\"" + boundary + "\",\"" + filename + "\")");
        }
        return retval;
    }

    // Extracts and returns the boundary token from a line.
    //
    private String extractBoundary(String line) {
        int index = line.indexOf("boundary=");
        if (index == -1) {
            return null;
        }
        String boundary = line.substring(index + 9); // 9 for "boundary="

        // The real boundary is always preceeded by an extra "--"
        boundary = "--" + boundary;

        return boundary;
    }

    // Extracts and returns disposition info from a line, as a String array
    // with elements: disposition, name, filename.  Throws an Exception
    // if the line is malformatted.
    //
    private String[] extractDispositionInfo(String line) throws Exception { // Return the line's data as an array: disposition, name, filename
        String[] retval = new String[3];

        // Convert the line to a lowercase string without the ending \r\n
        // Keep the original line for error messages and for variable names.
        String origline = line;
        line = origline.toLowerCase();

        // Get the content disposition, should be "form-data"
        int start = line.indexOf("content-disposition: ");
        int end = line.indexOf(";");
        if (start == -1 || end == -1) {
            throw new Exception("MultipartRequest.extractDispositionInfo(\"" + origline + "\")\r\nContent disposition corrupt: " + origline);
        }
        String disposition = line.substring(start + 21, end);
        if (!disposition.equals("form-data")) {
            throw new Exception("MultipartRequest.extractDispositionInfo(\"" + origline + "\")\r\nInvalid content disposition: " + disposition);
        }

        // Get the field name
        start = line.indexOf("name=\"", end); // start at last semicolon
        end = line.indexOf("\"", start + 7); // skip name=\"
        if (start == -1 || end == -1) {
            throw new Exception("MultipartRequest.extractDispositionInfo(\"" + origline + "\")\r\nContent disposition corrupt: " + origline);
        }
        String name = origline.substring(start + 6, end);

        // Get the filename, if given
        String filename = null;
        start = line.indexOf("filename=\"", end + 2); // start after name
        end = line.indexOf("\"", start + 10); // skip filename=\"
        if (start != -1 && end != -1) { // note the !=
            filename = origline.substring(start + 10, end);
            // The filename may contain a full path.  Cut to just the filename.
            int slash = Math.max(filename.lastIndexOf('/'), filename.lastIndexOf('\\'));
            if (slash > -1) {
                filename = filename.substring(slash + 1); // past last slash
            }
        }

        // Return a String array: disposition, name, filename
        retval[0] = disposition;
        retval[1] = name;
        retval[2] = filename;
        return retval;
    }

    // Extracts and returns the content type from a line, or null if the
    // line was empty.  Throws an Exception if the line is malformatted.
    //
    private String extractContentType(String line) throws Exception {
        String contentType = null;

        // Convert the line to a lowercase string
        String origline = line;
        line = origline.toLowerCase();

        // Get the content type, if any
        if (line.startsWith("content-type")) {
            int start = line.indexOf(" ");
            if (start == -1) {
                throw new Exception("MultipartRequest.extractContentType(\"" + origline + "\")\r\nContent type corrupt: " + origline);
            }
            contentType = line.substring(start + 1);
        } else if (line.length() != 0) { // no content type, so should be empty
            throw new Exception("MultipartRequest.extractContentType(\"" + origline + "\")\r\nMalformed line after disposition: " + origline);
        }
        return contentType;
    }
}

// A class to hold information about an uploaded file.
//
class UploadedFile {
    private final String dir;
    private final String filename;
    private final String type;

    UploadedFile(String dir, String filename, String type) {
        this.dir = dir;
        this.filename = filename;
        this.type = type;
    }

    public String getContentType() {
        return type;
    }

    public String getFilesystemName() {
        return filename;
    }

    public File getFile() {
        if (dir == null || filename == null) {
            return null;
        } else {
            return new File(dir + File.separator + filename);
        }
    }
}

// A class to aid in reading multipart/form-data from a ServletInputStream.
// It keeps track of how many bytes have been read and detects when the
// Content-Length limit has been reached.  This is necessary since some
// servlet engines are slow to notice the end of stream.
//
class MultipartInputStreamHandler {
    ServletInputStream in;
    String boundary;
    int totalExpected;
    int totalRead = 0;
    byte[] buf = new byte[8 * 1024];

    public MultipartInputStreamHandler(ServletInputStream in, String boundary, int totalExpected) {
        this.in = in;
        this.boundary = boundary;
        this.totalExpected = totalExpected;
    }

    // Reads the next line of input.  Returns null to indicate the end
    // of stream.
    //
    public String readLine() throws Exception {
        StringBuffer sbuf = null;
        try {
            sbuf = new StringBuffer();
            int result;
            // String line;

            do {
                result = this.readLine(buf, 0, buf.length); // this.readLine() does +=
                if (result != -1) {
                    sbuf.append(new String(buf, 0, result, "KSC5601"));
                }
            } while (result == buf.length); // loop only if the buffer was filled

            if (sbuf.length() == 0) {
                return null; // nothing read, must be at the end of stream
            }
            sbuf.setLength(sbuf.length() - 2); // cut off the trailing \r\n
        } catch (Exception ex) {
            throw new Exception("MultipartInputStreamHandler.readLine()");
        }
        return sbuf.toString();
    }

    // A pass-through to ServletInputStream.readLine() that keeps track
    // of how many bytes have been read and stops reading when the
    // Content-Length limit has been reached.
    //

    //-----------------------------------------------------------------------------------------------------
    // Exception 처리추가
    //-----------------------------------------------------------------------------------------------------
    public int readLine(byte b[], int off, int len) throws Exception {
        if (totalRead >= totalExpected) {
            return -1;
        } else {
            int result = 0;
            try {
                result = in.readLine(b, off, len);
                if (result > 0) {
                    totalRead += result;
                }
            } catch (Exception ex) {
                throw new Exception("MultipartInputStreamHandler.readLine(\"" + b + "\"," + off + "," + len + ")");
            }
            return result;
        }
    }
}
