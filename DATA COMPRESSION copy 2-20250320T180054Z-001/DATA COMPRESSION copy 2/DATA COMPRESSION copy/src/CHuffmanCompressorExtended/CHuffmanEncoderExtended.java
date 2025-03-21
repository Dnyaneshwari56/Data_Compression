package CHuffmanCompressorExtended;

import java.io.*;
import FileBitIO.CFileBitWriter;
import CHuffmanCompressorExtended.huffmanSignature1;
import FileBitIO.CFileBitWriter;
import CHuffmanCompressorExtended.HuffmanNode1;
import CHuffmanCompressorExtended.CPriorityQueue1;
import CHuffmanCompressorExtended.huffmanSignature1;


public class CHuffmanEncoderExtended implements huffmanSignature1{

	private String fileName,outputFilename;
	private HuffmanNode1 root = null;
	private long[] freq  = new long[MAXCHARS];
	private String[] hCodes = new String[MAXCHARS];
	private int distinctChars = 0;
	private long fileLen=0,outputFilelen;
	private FileInputStream fin;
	private BufferedInputStream in;	
	private String gSummary;

	void resetFrequency(){
		for(int i=0; i<MAXCHARS ;i++)
		freq[i] = 0;
		
		distinctChars = 0;
		fileLen=0;
		gSummary ="";
		}
	public CHuffmanEncoderExtended(){
		System.out.println("Running.....");
		loadFile("","");

		}
	public CHuffmanEncoderExtended(String txt){
		System.out.println("Running.....");
		loadFile(txt);
		}
	public CHuffmanEncoderExtended(String txt,String txt2){
		System.out.println("Running.....");	
		loadFile(txt,txt2);
		System.out.println("Loaded.....");	
		}
		
	public void loadFile(String txt){
		fileName = txt;
		outputFilename = txt + strExtension;
		resetFrequency();
		}
	public void loadFile(String txt,String txt2){
		fileName = txt;
		outputFilename = txt2;
		resetFrequency();
		}



	public boolean encodeFile() throws Exception {
    if (fileName.length() == 0) return false;
    try {
        fin = new FileInputStream(fileName);
        in = new BufferedInputStream(fin);
        
    } catch (Exception e) {
        throw e;
    }

    // Frequency Analysis
    try {
    	System.out.println("Entered try..");
        fileLen = in.available();
        System.out.println("Entered available..");
        if (fileLen == 0) throw new Exception("File is Empty!");
        gSummary += ("Original File Size : " + fileLen + "\n");
        System.out.println("Original File Size : " + fileLen);

        long i = 0;
        in.mark((int) fileLen);
        distinctChars = 0;

        while (i < fileLen) {
            int ch = in.read();
            i++;
            if (freq[ch] == 0) distinctChars++;
            freq[ch]++;
        }
        in.reset();
		} catch (IOException e) {
		    throw e;
		}

    	gSummary += ("Distinct Chars " + distinctChars + "\n");


    	System.out.println("distinct Chars " + distinctChars);
		 //debug
		for(int i=0;i<MAXCHARS;i++){
			if(freq[i] > 0)
			System.out.println(i + ")" + (char)i + " : " + freq[i]);
		}

        CPriorityQueue1 cpq = new CPriorityQueue1(distinctChars + 1);

        int count  = 0;
		for(int i=0;i<MAXCHARS;i++){
			if(freq[i] > 0){
				count ++;
				System.out.println("ch = " + (char)i + " : freq = " + freq[i]);
				HuffmanNode1 np = new HuffmanNode1(freq[i],(char)i,null,null);
				cpq.Enqueue(np);
				}
		}

		cpq.displayQ();

        HuffmanNode1 low1, low2;
        while (cpq.totalNodes() > 1) {
            low1 = cpq.Dequeue();
            low2 = cpq.Dequeue();
            if (low1 == null || low2 == null) {
                throw new Exception("PQueue Error!");
            }
            HuffmanNode1 intermediate = new HuffmanNode1((low1.freq + low2.freq), (char) 0, low1, low2);
            if (intermediate == null) {
                throw new Exception("Not Enough Memory!");
            }
            cpq.Enqueue(intermediate);
        }
        root = cpq.Dequeue();
        buildHuffmanCodes(root, "");

        for (int i = 0; i < MAXCHARS; i++) hCodes[i] = "";
        getHuffmanCodes(root);

        for(int i=0;i<MAXCHARS;i++){
		if(hCodes[i] != ""){ 
		System.out.println(i + " : " + hCodes[i]);
		}
		}

        // Initial Huffman encoding
        StringBuilder binaryEncoded = new StringBuilder();
        while (in.available() > 0) {
            int ch = in.read();
            binaryEncoded.append(hCodes[ch]);
        }

        // Binary-to-Symbol Conversion
        String symbolSequence = convertBinaryToSymbols(binaryEncoded.toString());

        // Frequency analysis for the symbol sequence
        resetFrequency();
        for (char ch : symbolSequence.toCharArray()) {
            freq[ch]++;
            if (freq[ch] == 1) distinctChars++;
        }

        // Build Huffman Tree for the symbol sequence
        cpq = new CPriorityQueue1(distinctChars + 1);
        for (int i = 0; i < MAXCHARS; i++) {
            if (freq[i] > 0) {
                HuffmanNode1 np = new HuffmanNode1(freq[i], (char) i, null, null);
                cpq.Enqueue(np);
            }
        }

        while (cpq.totalNodes() > 1) {
            low1 = cpq.Dequeue();
            low2 = cpq.Dequeue();
            if (low1 == null || low2 == null) {
                throw new Exception("PQueue Error!");
            }
            HuffmanNode1 intermediate = new HuffmanNode1((low1.freq + low2.freq), (char) 0, low1, low2);
            if (intermediate == null) {
                throw new Exception("Not Enough Memory!");
            }
            cpq.Enqueue(intermediate);
        }

        root = cpq.Dequeue();
        buildHuffmanCodes(root, "");

        for (int i = 0; i < MAXCHARS; i++) hCodes[i] = "";
        getHuffmanCodes(root);

        // Final Huffman encoding for symbol sequence
        CFileBitWriter hFile = new CFileBitWriter(outputFilename);
        hFile.putString(hSignature);
        String buf;
        buf = leftPadder(Long.toString(fileLen, 2), 32); //fileLen
        hFile.putBits(buf);
        buf = leftPadder(Integer.toString(distinctChars - 1, 2), 8); //No of Encoded Chars
        hFile.putBits(buf);

        for (int i = 0; i < MAXCHARS; i++) {
            if (hCodes[i].length() != 0) {
                buf = leftPadder(Integer.toString(i, 2), 8);
                hFile.putBits(buf);
                buf = leftPadder(Integer.toString(hCodes[i].length(), 2), 5);
                hFile.putBits(buf);
                hFile.putBits(hCodes[i]);
            }
        }

        for (char ch : symbolSequence.toCharArray()) {
            hFile.putBits(hCodes[ch]);
        }
        hFile.closeFile();
        outputFilelen = new File(outputFilename).length();
        float cratio = (float) (((outputFilelen) * 100) / (float) fileLen);
        gSummary += ("Compressed File Size : " + outputFilelen + "\n");
        gSummary += ("Compression %  : " + cratio + "%" + "\n");
        return true;
    }

    private String convertBinaryToSymbols(String binary) {
        StringBuilder symbols = new StringBuilder();
        int[] zeroPositions = new int[binary.length()];
        int count = 0;

        for (int i = 0; i < binary.length(); i++) {
            if (binary.charAt(i) == '0') {
                zeroPositions[count++] = i;
            }
        }

        int[] positionDifferences = new int[count - 1];
        for (int i = 0; i < count - 1; i++) {
            positionDifferences[i] = (zeroPositions[i + 1] - zeroPositions[i]) + 32;
        }

        for (int i = 0; i < positionDifferences.length; i++) {
            symbols.append((char) positionDifferences[i]);
        }

        return symbols.toString();
    }

    void buildHuffmanCodes(HuffmanNode1 parentNode, String parentCode) {
        parentNode.huffCode = parentCode;
        if (parentNode.left != null)
            buildHuffmanCodes(parentNode.left, parentCode + "0");

        if (parentNode.right != null)
            buildHuffmanCodes(parentNode.right, parentCode + "1");
    }

    void getHuffmanCodes(HuffmanNode1 parentNode) {
        if (parentNode == null) return;
        int asciiCode = (int) parentNode.ch;
        if (parentNode.left == null || parentNode.right == null)
            hCodes[asciiCode] = parentNode.huffCode;

        if (parentNode.left != null) getHuffmanCodes(parentNode.left);
        if (parentNode.right != null) getHuffmanCodes(parentNode.right);
    }

    String leftPadder(String txt, int n) {
        while (txt.length() < n)
            txt = "0" + txt;
        return txt;
    }

    String rightPadder(String txt, int n) {
        while (txt.length() < n)
            txt += "0";
        return txt;
    }

    public String getSummary() {
        return gSummary;
    }

}
