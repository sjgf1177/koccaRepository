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
    private int retval = 1; // ����ó��(������ �� �ö� ������ ����)
    private String servletName;
    private String userid;

    /**
     * �Ķ������ 'name', 'value' ���� �����ϰ� �ִ� hashtable ��ü
     */
    public Hashtable parameters = new Hashtable(); // name - value

    /**
     * ���ε�Ǵ� file �� 'name', 'UploadedFile' ���� �����ϰ� �ִ� hashtable ��ü
     */
    public Hashtable files = new Hashtable(); // name - UploadedFile

    /**
     * ���ε�Ǵ� file �� 'name', 'realFileName'(Ŭ���̾�Ʈ���� ���ε�Ǵ� ���ϸ�) ���� �����ϰ� �ִ�
     * hashtable ��ü
     */
    public Hashtable realFileParameters = new Hashtable(); //     Ŭ���̾�Ʈ���� ���ε�Ǵ� ���ϸ��

    /**
     * ���ε�Ǵ� file �� 'name', 'newFileName'(������ ����� ���ο� ���ϸ�) ���� �����ϰ� �ִ� hashtable
     * ��ü
     */
    public Hashtable newFileParameters = new Hashtable(); //      ������ ����� ���ο� ���ϸ��

    /**
     * ������ ������ ���ε尡 ����� �̷�����°��� �����ϴ� hashtable, '1' �̸� ���������� ���ε� ��, '0' �̸� ���ϻ�����
     * �ʰ�(������ ����ȵ�)
     */
    public Hashtable fileupstatus = new Hashtable(); //      ������ ������ ���ε尡 ����� �̷�����°��� �����ϴ� hashtable
    //      '1' �̸� ���������� ���ε� ��, '0' �̸� ���ϻ����� �ʰ�(������ ����ȵ�)

    public Vector realFileVector = new Vector();
    public Vector newFileVector = new Vector();

    /**
     * request ��ü�� ���ڷ� �޴� ������
     * 
     * @param request ServletRequest�� request ��ü�� �������ڷ� ����
     */
    public MultipartRequest(ServletRequest request) throws Exception {
        this(request, ".", DEFAULT_MAX_POST_SIZE);
    }

    /**
     * request ��ü�� ���ε�� ���丮���� ���ڷ� �޴� ������
     * 
     * @param request ServletRequest�� request ��ü�� ���ڷ� ����
     * @param saveDirectory ���Ͼ��ε�� ���丮���� ���ڷ� ����
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
     * request ��ü, ���ε�� ���丮��, ���ε�� ���ϻ������� �ִ� ��밪�� ���ڷ� �޴� ������
     * 
     * @param request ServletRequest�� request ��ü�� ���ڷ� ����
     * @param saveDirectory ���Ͼ��ε�� ���丮���� ���ڷ� ����
     * @param maxPostSize ���ε�� ���ϻ������� �ִ� ��밪�� ���ڷ� ����
     */
    public MultipartRequest(ServletRequest request, String saveDirectory, int maxPostSize) throws Exception {
        if (request == null) {
            throw new Exception("MultipartRequest.MultipartRequest(ServletRequest,\"" + saveDirectory + "\"," + maxPostSize + ")\r\nrequest cannot be null");
        }
        req = request;

        if (saveDirectory == null) {
            throw new Exception("MultipartRequest.MultipartRequest(ServletRequest," + null + "," + maxPostSize + ")\r\nsaveDirectory cannot be null");
        } else if (!saveDirectory.equals("")) {
            this.setUploadDir(saveDirectory); // ���ε� ���丮 ����
        }

        this.setMaxPostSize(maxPostSize); // ���ε� ���ѻ����� ����
    }

    /**
     * ���ε��� ���丮 ��ü�� �����Ѵ�
     * 
     * @param saveDirectory ���ε��� ���丮��
     */
    public void setUploadDir(String saveDirectory) throws Exception {
        dir = new File(saveDirectory);

        if (!dir.isDirectory()) { // ������ ���丮�� ���翩�� �׸��� �������� üũ
            if (!dir.mkdirs()) { // ���丮�� ������ ���� �����Ѵ�.
                throw new Exception("MultipartRequest.setUploadDir(\"" + saveDirectory + "\")\r\nCannot make directory");
            }
        }
        if (!dir.canWrite()) { // ���丮�� ��������� �ִ������� üũ
            throw new Exception("MultipartRequest.setUploadDir(\"" + saveDirectory + "\")\r\nNot writable");
        }
    }

    /**
     * ���ε�� ���丮���� ��´�
     * 
     * @return ���ε�� ���丮��
     */
    public String getUploadDir() {
        return (dir != null) ? dir.toString() : "";
    }

    /**
     * ���ε��� ������ ���� ������ ����
     * 
     * @param maxPostSize ���ϻ�����, 0 �̸� ���ϻ����� ������
     */
    public void setMaxPostSize(int maxPostSize) throws Exception {
        if (maxPostSize < 0) {
            throw new Exception("MultipartRequest.setMaxPostSize(" + maxPostSize + ")\r\nmaxPostSize cannot be negative");
        }
        this.maxSize = maxPostSize * 1000000;
    }

    /**
     * multipart �������� �Ѿ�� �Ķ������ 'name' ���� ��´�
     * 
     * @return Enumeration �Ķ������ 'name'�� String ���� Enum ���·� ��ȯ�Ѵ�
     */
    public Enumeration getParameterNames() {
        return parameters.keys();
    }

    /**
     * multipart �������� �Ѿ�� �Ķ������ 'name' �� �ش��ϴ� 'value' ���� ��´�
     * 
     * @return String �Ķ������ 'name'�� �ش�Ǵ� String ���� 'value' ���� ��ȯ�Ѵ�
     */
    public String getParameter(String name) {
        return (String) parameters.get(name);
    }

    /**
     * ������ ���ε�Ǿ� ����� ���ο� ���ϸ����� ��ü�Ͽ� 'newFileParameters' hashtable �� �����Ѵ�
     * 
     * @param paramName ���ε�Ǵ� �Ķ������ name �� ���ڷ� ����
     * @param newFileName ������ ����� ���ο� ���ϸ�
     */
    public void setNewFileName(String paramName, String newFileName) throws Exception {
        newFileParameters.put(paramName, newFileName);
    }

    /**
     * ������ ���ε�Ǿ� ����� ���ο� ���ϸ��� ��´�
     * 
     * @param paramName ���ε�Ǵ� �Ķ������ name �� ���ڷ� ����
     * @return ���ο� ���ϸ��� ��ȯ�Ѵ�.
     */
    public String getNewFileName(String paramName) {
        String s = this.getFilesystemName(paramName);
        if (s == null)
            s = "";
        return s;
    }

    /**
     * Ŭ���̾�Ʈ���� ���ε�� ���� ���ϸ��� ��´�
     * 
     * @param paramName ���ε�Ǵ� �Ķ������ name �� ���ڷ� ����
     * @return ���� ���ϸ��� ��ȯ�Ѵ�.
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
     * ���ε� �� ������ ����� ������ ���¸� ��´�. '1' �̸� ���������� ���ε� ��, '0' �̸� ���ϻ����� �ʰ�(������ ����ȵ�)
     * 
     * @return Hashtable ������ ���°� ����� hashtable ��ü�� ��ȯ�Ѵ�
     */
    public Hashtable getFileUploadStatus() {
        return fileupstatus;
    }

    /**
     * ���ε� �� ������ ����� ������ ���¸� ��´�.
     * 
     * @return int ������ ���·� ����ó���� ��� 1, �������� ��� 0 �� ��ȯ�Ѵ�
     */
    public int getFileUploadStatus(String name) {
        int retval = 0; // ���ε� �ȵ� ����.

        Integer fstatus = (Integer) fileupstatus.get(name);
        if (fstatus != null) {
            retval = fstatus.intValue();
        }
        return retval; // 0:���ε� �ȵ�(���� ������ �ʰ�), 1:���ε� ��.
    }

    /**
     * multipart �������� �Ѿ�� ���� �Ķ������ 'name' ���� ��´�
     * 
     * @return Enumeration ���� �Ķ������ 'name'�� String ���� Enum ���·� ��ȯ�Ѵ�
     */
    public Enumeration getFileNames() {
        return files.keys();
    }

    /**
     * ���ε� �� ������ ����� ���ο� ���ϸ�(Ȯ���� ����)�� ��´�.
     * 
     * @param name ���ε�Ǵ� ������ �±׸�
     * @return filename ���ε�� ���ο� ���ϸ��� ��ȯ�Ѵ�
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
     * ���ε� ��ÿ� Ŭ���̾�Ʈ ���������� ������ ������ ContentType �� ��´�
     * 
     * @param name ���ε�Ǵ� ������ �±׸�
     * @return content type ���ε�� ������ ContentType�� ��ȯ�Ѵ�
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
     * ���ε� �� ������ ����� ���ϰ�ü�� ��´�
     * 
     * @param name ���ε�Ǵ� ������ �±׸�
     * @return file ���ε�� ������ ��ü�� ��ȯ�Ѵ�
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
     *request ��ü�� �޾� box hashtable �� parameters �� �����ϸ�, ���ο����ϸ��� ��� ��ȯ�Ѵ�.
     * 
     * @param request HttpServletRequest �� request ��ü�� ���ڷ� ����
     * @return RequestBox request ��ü���� ���� �Ķ���� name �� value, realfilename,
     *         newfilename �� ���� hashtable ��ü�� ��ȯ��
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
     * ������ ���ε�ȴ�
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
     * ���ε嶧 �Ѿ���� �Ķ���͵��� 'parameters' �� name ���� �����Ͽ� value�� �����ϰ� ������ 'files' ��
     * file�� ���� �����Ͽ� UploadedFile ��ü�� �����Ѵ�
     * 
     * @param in MultipartInputStreamHandler ��ü�� ���ڷ� �޴´�
     * @param boundary �и��� �κ��� �������ִ� ǥ���ڸ� �޴´�
     * @return ������ �κ����� ���θ� ��ȯ�Ѵ�
     * @exception Exception ���� read�ϰų� parsing �Ҷ� ������ �߻��ϸ� ����ó���Ѵ�. request
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
            filename = this.getSaveNewFileName(name, filename, realFileVector.size()); //      ������ ������ ���ϸ��� �ٲ۴�
            newFileVector.addElement(name + "|" + filename); //System.out.println("new:"+filename);

            realFileParameters.put(name, realFileVector); //      Ŭ���̾�Ʈ�� ���ϸ��� 'realFileParameters' hashtable �� �ִ´�.
            newFileParameters.put(name, newFileVector); //      Ŭ���̾�Ʈ�� ���ϸ��� 'realFileParameters' hashtable �� �ִ´�.

            fileupstatus.put(name, new Integer(readAndSaveFile(in, boundary, filename)));
            //      ���ε�� ������ ����(����ũ��� ����� ������ִ��� ����)�� 'fileupstatus' hashtable �� �ִ´�.

            if (!filename.equals("")) {
                files.put(name, new UploadedFile(dir.toString(), filename, contentType));
                //  ���ο� ���ϸ����� ��üȭ�� UploadedFile �� 'files' hashtable �� �ִ´�.
            }
        }
        return false; // there's more to read
    }

    /**
     * ���ε� �� ������ ������ ���ϸ��� 'realFileName' ���� 'newFileName' ���� ��ü�Ѵ�.
     * 
     * @param paramName ���ε�Ǵ� �Ķ������ name �� ���ڷ� ����
     * @param realFileName Ŭ���̾�Ʈ���� ���ε�Ǵ� ���ϸ��� ���ڷ� ����
     * @return ������ ����� ���ο� ���ϸ�(Ȯ���ڰ� ���Ե�)�� ��ȯ�Ѵ�
     */
    /*
     * protected String getSaveFileName(String paramName, String realFileName) {
     * String newFileName = (String)newFileParameters.get(paramName);
     * 
     * if ( !(newFileName == null || newFileName.equals("")) ||
     * realFileName.equals("")) { // �����̸��� "" �� �ƴϰ� ������ ���̸��� null �̰ų� "" �� �ƴϸ�
     * String fileExtension = ""; int index = realFileName.lastIndexOf('.');
     * 
     * if ( index >= 0) { // Ȯ���ڰ� ������ fileExtension =
     * realFileName.substring(index+1); // Ȯ���ڸ� ���� }
     * 
     * if (!fileExtension.equals("")) { newFileName = newFileName + "." +
     * fileExtension; // ���ο� ���ϸ��� ����� } } else { // ���̸��� null �̰ų� �����ϰ�� / ���ε�������
     * �̸��� �����ϰ�� (null �ϼ��� ����) �� ������ ���ϸ��� �״�� ����Ѵ� newFileName = realFileName; }
     * return newFileName; }
     */

    protected String getSaveNewFileName(String paramName, String realFileName, int k) {
        String newFileName = "";
        try {
            String fileExtension = "";
            String v_currentDate = FormatDate.getDate("yyyyMMddHHmmss");

            int index = realFileName.lastIndexOf('.');

            if (index >= 0) { // Ȯ���ڰ� ������
                fileExtension = realFileName.substring(index + 1); // Ȯ���ڸ� ����
            }

            if (!fileExtension.equals("")) {
                newFileName = servletName + "_" + paramName.substring(2) + "_" + v_currentDate + k + "_" + userid + "." + fileExtension; // ���ο� ���ϸ��� �����
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return newFileName;
    }

    /**
     * ���ε�Ǵ� ���� �Ķ������ 'value' �� String ������ ��ȯ�Ѵ�
     * 
     * @param in MultipartInputStreamHandler ��ü�� ���ڷ� �޴´�
     * @param boundary �и��� �κ��� �������ִ� ǥ���ڸ� �޴´�
     * @return sbuf �о�帰 ������ String ������ ��ȯ�Ѵ�.
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
     * ���ε�Ǵ� ������ �а� ���ο� ���ϸ����� �����Ѵ�.
     * 
     * @param in MultipartInputStreamHandler ��ü�� ���ڷ� �޴´�
     * @param boundary �и��� �κ��� �������ִ� ǥ���ڸ� �޴´�
     * @param filename ������ ������ ���ο� ���ϸ��� ���ڷ� �޴´�
     * @return retval ������ �ִ� ���ϻ������ ���Ͽ� �ִ����ϻ������ ũ�� �����Ѵ�. 0 �� ��ȯ�Ѵ�. ����ó���Ǹ� 1 ��
     *         ��ȯ�Ѵ�.
     */
    protected int readAndSaveFile(MultipartInputStreamHandler in, String boundary, String filename) throws Exception {

        try {
            boolean save = !filename.equals(""); // ���忩�� ����

            File f = null;
            FileOutputStream fos = null;
            BufferedOutputStream out = null;

            if (save) {
                f = new File(dir + File.separator + filename); //  ������ ���ο� ���ϸ��� �����Ѵ�
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

            if (save) { // ���� ������ üũ..
                f = new File(dir + File.separator + filename);
                //          System.out.println("f.length() : " +  f.length());
                if ((maxSize > 0 && f.length() > maxSize) || retval == 0) {
                    f.delete();
                    retval = 0; // ���� ������ �ʰ�
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
    // Exception ó���߰�
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
