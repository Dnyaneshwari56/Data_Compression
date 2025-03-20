/*
 * RLEInterface.java
 *
 * Created on May 12, 2009, 12:03 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package CRLECompressor;

/**
 *
 * @author admin
 */
public interface RLEInterface {
    	final int MAXCHARS = 256;
	final int ESCAPECHAR = 255;
	final String strExtension = ".rle";
	final String rleSignature = "RLE";
	final int toleranceFrequency = 4;
    
}
