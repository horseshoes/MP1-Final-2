/**
 * 
 */
package MP1V2;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;
//import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 * @author acer
 *
 */
public class FileIndex {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		System.out.println("This is Hashmap Implementation");
		File tf=new File("F:\\mp1\\indexes.txt");
		File pathName=new File("F:\\mp1\\name.txt");
		String sk="",di="",name=readPath(pathName);
		float start=0,end=0;
		HashMap<String,ArrayList<String>> hash=new HashMap<String,ArrayList<String>>();
		Scanner s=new Scanner(System.in);
		int choice=0;
		if(name!=null){
			System.out.println("Path index is "+name);
			System.out.print("Do you want to index another directory press 1 if so if not 2:");
			choice=s.nextInt();	
			
		}else{
			System.out.println("File Index this directory:");
			choice=1;
		}
		
	
		try{
			if(choice==1){
				System.out.print("File Path:");
				di=s.next();
				File dir=new File(di);
				 start=System.nanoTime();
				crawl(dir,hash);
				saveFile(tf,hash);
				 end=System.nanoTime();
				 writePath(pathName,di);
				 
				}else{
					hash=readFile(tf);
				}
			System.out.println("Toatal Words index "+hash.size());
			System.out.println("Finished indexing time:"+( ( (end-start)/1000000)/1000 )+" seconds");
			if (hash.isEmpty()){
				System.out.println("Empty");
			}
			while(true){
			System.out.print("Enter Search key:");
			sk=s.next();
			System.out.println("This are the files::");
			System.out.println(search(sk,hash));
			}
		}catch(Exception ex){
			System.out.println("Error WRong paht:"+di);
		}
		
	}//end of main
	public static void crawl(File dir,HashMap<String,ArrayList<String>>hash){
		File [] files=dir.listFiles();
		for(File f:files){
			if(f.isDirectory()){
				crawl(f,hash);
			}else{
				
				System.out.println(f.getName()+ " path:"+f.getAbsolutePath());
				if(f.getName().endsWith(".txt"))
					index(f.getAbsolutePath(),hash);
			}
		}

	}//end of crawl
	public static void index(String path,HashMap<String,ArrayList<String> >hash){
		BufferedReader br;
		try{
			br=new BufferedReader(new FileReader(path));
			String str ="";
			int c;
			while((c=br.read())!=-2 ){
				/*String[] t=str.split(" ");
				for(String s:t){
					ArrayList<String>temp;
					if(hash.containsKey(s.toLowerCase())){
						temp=hash.get(s.toLowerCase());
						if(!temp.contains(path))
							temp.add(path);			
					}else{
						temp=new ArrayList<String>();
						temp.add(path);
						hash.put(s.toLowerCase(),temp);
					}
					temp=null;
				}//end of for loop
				t=null;*/
				if( (c==45) || (c==39) || ( (c>=65) && (c<=90) ) || ( (c>=97)) && (c<=122) ){
					str+=(char)c;
					//System.out.println("Paht:"+path+" word:"+str);
				
				}else{
					//System.out.println("Paht:"+path+" word:"+str);
					if(str.length()>=1){
						if(hash.containsKey(str.toLowerCase())){
							
							if(!hash.get(str.toLowerCase()).contains(path)){//if hash already contains path
								hash.get(str.toLowerCase()).add(path);
								//System.out.println("Paht:"+path+" word:"+str);
							}
						}else{
							ArrayList<String>temp=new ArrayList<String>();
							temp.add(path);
							hash.put(str.toLowerCase(),temp);
							temp=null;
						}
						str=null;
						str="";
					}	
				}//end of else
				if(c==-1){
					if(str.length()>=1){
						if(hash.containsKey(str.toLowerCase())){
							
							if(!hash.get(str.toLowerCase()).contains(path)){//if hash already contains path
								hash.get(str.toLowerCase()).add(path);
								//System.out.println("Paht:"+path+" word:"+str);
							}
						}else{
							ArrayList<String>temp=new ArrayList<String>();
							temp.add(path);
							hash.put(str.toLowerCase(),temp);
							temp=null;
						}
						str=null;
						str="";
					}	
					break;
				}
			
			}
			
		}catch(Exception ex){
			System.out.println(ex);
			System.exit(0);
		}
		
	}//end of index
	public static String search(String key,HashMap<String,ArrayList<String>> hash){
		String temp="";
		float start=System.nanoTime(),end=0;
		
		//System.out.println();
		int fc=0;
		if(hash.containsKey(key.toLowerCase())){
			end=System.nanoTime();
			for(String s:hash.get(key.toLowerCase())){
				temp+=s+"\n";
				fc++;
			}
			
		}else {
			return "Empty result set";
		}
		//end=System.nanoTime();
		temp+="Number of files:"+fc+"\n";
		temp+="Time taken to search:"+( (  (end-start)/1000000)/1000)+" seconds";
		return temp;
	}//end of search
	public static HashMap<String,ArrayList<String>> readFile(File file2){
		
		HashMap<String,ArrayList<String>>y = null;
		try {
			FileInputStream f1 = new FileInputStream(file2);  
			ObjectInputStream s1 = new ObjectInputStream(f1);  
			y= (HashMap<String,ArrayList<String>>)s1.readObject();
			s1.close();
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return y;
	}//end of readfile
	public static void saveFile(File file,HashMap<String,ArrayList<String>>x){
		FileOutputStream f;
		try {
			f = new FileOutputStream(file);
			ObjectOutputStream s = new ObjectOutputStream(f);          
			s.writeObject(x);
			s.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}//end of save file
	public static void writePath(File file,String name){
		 try {
	            // Assume default encoding.
	            FileWriter fileWriter =
	                new FileWriter(file);

	            // Always wrap FileWriter in BufferedWriter.
	            BufferedWriter bufferedWriter =
	                new BufferedWriter(fileWriter);

	            // Note that write() does not automatically
	            // append a newline character.
	            bufferedWriter.write(name);
	          
	            // Always close files.
	            bufferedWriter.close();
	        }
	        catch(IOException ex) {
	          
	            // Or we could just do this:
	            // ex.printStackTrace();
	        }
	}//end of writefile
	public static String readPath(File file){
		BufferedReader br;
		String str="";
		try{
			br=new BufferedReader(new FileReader(file));
			
			while((str=br.readLine())!=null){
				return str;
				
				
			}
		}catch(Exception ex){
			System.out.println(ex);
			System.exit(0);
		}
		return str;
	}//end of read path
}//end of class
