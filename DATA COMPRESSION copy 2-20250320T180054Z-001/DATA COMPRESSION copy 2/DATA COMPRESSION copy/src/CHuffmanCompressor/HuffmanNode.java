/*
 * HuffmanNode.java
 *
 * Created on May 12, 2009, 11:49 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package CHuffmanCompressor;

/**
 *
 * @author admin
 */

public class HuffmanNode{
	
	HuffmanNode left,right;
	public long freq;
	public char ch;
	
	//Code Words
	public String huffCode;
	
	
	public HuffmanNode(){
		freq = 0;
		ch = 0;
		huffCode = "";
		left = null;
		right = null;
		}
	public HuffmanNode(long xfreq,char xch,HuffmanNode lchild,HuffmanNode rchild){
		freq = xfreq;
		ch = xch;
		left = lchild;
		right = rchild;
		huffCode = "";
		}

	
}
