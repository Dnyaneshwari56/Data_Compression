/*
 * LZWInterface.java
 *
 * Created on May 7, 2009, 1:50 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package CLZWCompressor;

/**
 *
 * @author admin
 */
public interface LZWInterface {
    
    	final int MAXCHARS = 256;
	final String strExtension = ".lzw";
	final String lzwSignature = "LZW";
	final int MAXCODES = 4096;
	final int MAXBITS = 12;
    
}
