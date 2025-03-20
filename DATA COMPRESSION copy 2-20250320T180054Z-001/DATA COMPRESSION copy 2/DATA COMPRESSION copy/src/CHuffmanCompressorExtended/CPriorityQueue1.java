/*
 * CPriorityQueue1.java
 *
 * Created on May 12, 2009, 11:48 AM
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

package CHuffmanCompressorExtended;

/**
 *
 * @author admin
 */
class CPriorityQueue1{
	final int DEFAULTMAX = 256;
	
	HuffmanNode1 [] nodes;
	int capacity,total;
	
	public CPriorityQueue1(){
		capacity = DEFAULTMAX;
		total = 0;
		nodes = new HuffmanNode1[capacity];
	}
	public CPriorityQueue1(int max){
		capacity = max;
		total = 0;
		nodes = new HuffmanNode1[capacity];
		
	}
	
	public boolean Enqueue(HuffmanNode1 np){
		
		if(isFull()) return false;
		
		if(isEmpty()){ //first element?
			nodes[total++] = np;
			return true;
		}
		int i = total-1,pos;
		while(i >= 0){
			if(nodes[i].freq < np.freq){
				break;
				}
			i--;
			}
		pos = total-1;
		while(pos >= i+1){
			nodes[pos+1] = nodes[pos];
			pos--;
			}
		nodes[i+1] = np;
		total++;
		return true;
	}
	
	public HuffmanNode1 Dequeue(){
		
		if(isEmpty()) return null;
		HuffmanNode1 ret = nodes[0];
		total--;
		for(int i = 0;i<total;i++)
		nodes[i] = nodes[i+1];
		return ret;
		}
	
	public boolean isEmpty(){
		return (total == 0);
		}
	
	public boolean isFull(){
		return (total == capacity);
		}
		
	public int totalNodes(){
		return total;
		}
		
	//debug
	public void displayQ(){
	for(int i=0;i<total;i++){
		System.out.println("Q" + i + ") " + nodes[i].ch + " : " + nodes[i].freq);
		}	
		
		}
	
}

