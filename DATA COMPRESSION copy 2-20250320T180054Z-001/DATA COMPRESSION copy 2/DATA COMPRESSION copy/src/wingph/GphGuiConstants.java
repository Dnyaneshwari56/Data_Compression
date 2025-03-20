/*
 * GphGuiConstants.java
 *
 * Created on May 5, 2009, 9:54 PM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package wingph;

/**
 *
 * 
 */
public interface GphGuiConstants {
    	String[] algorithmNamesArray = {		"Huffman Compression",
											
											"GZip Compression",
											
											"RLE Compressor",
											"LZW Compressor",
											 "Huffman Extended"
											};

	String[] extensionArray = {                                                     ".lzw",
											".gz",
											".huf",
											".rle",
											".hfe"
																			
											
											};
        final int COMP_LZW = 0;
        final int COMP_GZIP = 1;
	final int COMP_HUFFMAN = 2;
	final int COMP_RLE = 3;
	final int COMP_HUFFMAN_EXTENDED = 4;
       
	final int COMPRESS = 32;
	final int DECOMPRESS = 33;
	
	
	
}
