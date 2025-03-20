/*
 * HuffmanNode1.java
 *
 * Created on May 12, 2009, 11:49 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package CHuffmanCompressorExtended;

/**
 *
 * @author admin
 */

public class HuffmanNode1{
	
	HuffmanNode1 left,right;
	public long freq;
	public char ch;
	
	//Code Words
	public String huffCode;
	
	
	public HuffmanNode1(){
		freq = 0;
		ch = 0;
		huffCode = "";
		left = null;
		right = null;
		}
	public HuffmanNode1(long xfreq,char xch,HuffmanNode1 lchild,HuffmanNode1 rchild){
		freq = xfreq;
		ch = xch;
		left = lchild;
		right = rchild;
		huffCode = "";
		}

	
}
