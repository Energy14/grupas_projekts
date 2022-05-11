import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;
import java.io.FileWriter;


public class Main {


	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		String choiseStr;
		String sourceFile, resultFile, firstFile, secondFile;
		
		loop: while (true) {
			
			choiseStr = sc.next();
								
			switch (choiseStr) {
			case "comp":
				System.out.print("source file name: ");
				sourceFile = sc.next();
				System.out.print("archive name: ");
				resultFile = sc.next();
				comp(sourceFile, resultFile);
				break;
			case "decomp":
				System.out.print("archive name: ");
				sourceFile = sc.next();
				System.out.print("file name: ");
				resultFile = sc.next();
				decomp(sourceFile, resultFile);
				break;
			case "size":
				System.out.print("file name: ");
				sourceFile = sc.next();
				size(sourceFile);
				break;
			case "equal":
				System.out.print("first file name: ");
				firstFile = sc.next();
				System.out.print("second file name: ");
				secondFile = sc.next();
				System.out.println(equal(firstFile, secondFile));
				break;
			case "about":
				about();
				break;
			case "exit":
				break loop;
			}
		}

		sc.close();
	}

	public static void comp(String sourceFile, String resultFile) {
		System.out.println("encoding...");
		int inchar, size=256;
		char ch;
		Map<String, Integer> table = new HashMap<>();
		BufferedReader in;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream dost = new DataOutputStream(baos);
		String add;
		for(int i=0;i<size;i++) {
			table.put(String.valueOf((char) i), i);
		}
		String found = "";
		try {
			in = new BufferedReader(new FileReader(sourceFile));
			while((inchar = in.read())!=-1) {
				ch = (char) inchar;
				add=found+ch;
				if(table.containsKey(add)) {
					found = add;
				} else {
					if(table.get(found)!=null) {
						dost.writeInt(table.get(found));
					}
					found=String.valueOf(ch);
					table.put(add, size++);
				}
			}

			if(!found.equals("")) {
				dost.writeInt(table.get(found));
			}
			FileOutputStream file = new FileOutputStream(resultFile);
			baos.writeTo(file);
			file.close();
			in.close();
			System.out.println("encoded");
		} catch (IOException e) {
			System.out.println(e.getMessage());
			return;
		}
	}

	public static void decomp(String sourceFile, String resultFile) {
		System.out.println("decoding...");
		Map<Integer, String> table = new HashMap<>();
		int size = 256, current;
		String chars, rescur;
		PrintWriter out;
		for(int i=0;i<size;i++) {
			table.put(i, String.valueOf((char) i));
		}
		try {
			DataInputStream in = new DataInputStream(new FileInputStream(sourceFile));
			out = new PrintWriter(new FileWriter(resultFile));
			try {
				chars = String.valueOf((char) in.readInt());
				out.write(chars);
				while(true) {
					current=in.readInt();
					if(table.containsKey(current)) {
						rescur=table.get(current);
					} else {
						rescur=chars+chars.charAt(0);
					}
					out.write(rescur);
					table.put(size++, chars + rescur.charAt(0));
					chars=rescur;
				}
			} catch(EOFException e) {
				System.out.println("decoded");
				in.close();
				out.close();
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return;
		}

	}
	
	public static void size(String sourceFile) {
		try {
			FileInputStream f = new FileInputStream(sourceFile);
			System.out.println("size: " + f.available());
			f.close();
		}
		catch (IOException ex) {
			System.out.println(ex.getMessage());
		}
		
	}
	
	public static boolean equal(String firstFile, String secondFile) {
		try {
			FileInputStream f1 = new FileInputStream(firstFile);
			FileInputStream f2 = new FileInputStream(secondFile);
			int k1, k2;
			byte[] buf1 = new byte[1000];
			byte[] buf2 = new byte[1000];
			do {
				k1 = f1.read(buf1);
				k2 = f2.read(buf2);
				if (k1 != k2) {
					f1.close();
					f2.close();
					return false;
				}
				for (int i=0; i<k1; i++) {
					if (buf1[i] != buf2[i]) {
						f1.close();
						f2.close();
						return false;
					}
						
				}
			} while (k1 == 0 && k2 == 0);
			f1.close();
			f2.close();
			return true;
		}
		catch (IOException ex) {
			System.out.println(ex.getMessage());
			return false;
		}
	}
	
	public static void about() {
		System.out.println("211RDB218 Roberts Simanis");
		System.out.println("211RDC003 Annija Tkacenko");
		System.out.println("211RDC017 Diana Marta Rence");
		System.out.println("211RDC010 Aksels Jaunzemis");
	}
}
