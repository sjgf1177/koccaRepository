/*
 *@(#)ByteArrayDataSource.java	1.1 00/01/30
 *
 * Copyright 1998-2000 Sun Microsystems, Inc. All Rights Reserved.
 * 
 * This software is the proprietary information of Sun Microsystems, Inc.  
 * Use is subject to license terms.
 * 
 */

package com.credu.library;

import java.io.*;
import javax.activation.*;

/** This class implements a DataSource from:
 * 	an InputStream
 * 	a byte array
 * 	a String
 *@author
 */
public class ByteArrayDataSource implements DataSource {
    private byte[] data;	// data
    private String type;	// content-type

    /* Create a DataSource from an input stream */
    /**
     @param is
     @param type
     */
    public ByteArrayDataSource(InputStream is, String type) {
        this.type = type;
        try { 
            ByteArrayOutputStream os = new ByteArrayOutputStream();
	    int ch;

	    while ((ch = is.read()) != -1)
                // XXX - must be made more efficient by
	        // doing buffered reads, rather than one byte reads
	        os.write(ch);
	    data = os.toByteArray();

        } catch (IOException ioex) { }
    }

    /* Create a DataSource from a byte array */
    /**
     @param data
     @param type
     */
    public ByteArrayDataSource(byte[] data, String type) {
        this.data = data;
	this.type = type;
    }

    /* Create a DataSource from a String */
    /**
     @param data
     @param type
     */
    public ByteArrayDataSource(String data, String type) {
	try {
	    // Assumption that the string contains only ASCII
	    // characters!  Otherwise just pass a charset into this
	    // constructor and use it in getBytes()
	    this.data = data.getBytes("euc-kr");
	} catch (UnsupportedEncodingException uex) { }
	this.type = type;
    }

    /**
     * Return an InputStream for the data.
     * Note - a new stream must be returned each time.
     */
    public InputStream getInputStream() throws IOException {
	if (data == null)
	    throw new IOException("no data");
	return new ByteArrayInputStream(data);
    }

    /**
     @throws IOException
     @return
     */
    public OutputStream getOutputStream() throws IOException {
	throw new IOException("cannot do this");
    }

    /**
     @return
     */
    public String getContentType() {
        return type;
    }

    /**
     @return
     */
    public String getName() {
        return "dummy";
    }
}
