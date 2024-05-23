package test;

import java.io.File;
import java.util.Scanner;

public class IOSearcher {

	public static boolean search(String word, String...fileNames) throws Exception{
		boolean found=false;
		for(int i=0;i<fileNames.length && !found; i++) {
			Scanner s=new Scanner(new File(fileNames[i]));
			while(s.hasNext() && !found)
				if(s.next().equals(word))
					found=true;
			s.close();
		}
		return found;
	}
}
