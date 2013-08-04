import java.io.*;
import java.util.*;

/**Description: program created to take the place of manually dividing a district csv file at work. Each district csv file has a list of sitecodes and data for the sitecode. 
 * For each unique sitecode, a new csv file is created with the sitecode being the name of the file. The lines for the data are stored in and ArrayList.
 * 
 * @author kerenkinner
 * 8/4/2013
 */
public class ImportSite {
  /**
* Writes output to a csv file
* @param out has lines 2 to eof
* @param sitecode is name of file
* @param firstLine has header
*/
	
	public static void writeOutput (ArrayList<String> out, String sitecode, String firstLine) throws IOException {		
		String path="C:/Users/pkinner/Documents/Input/"+sitecode+".csv";
		FileWriter fw = new FileWriter(path);
        PrintWriter pw = new PrintWriter(fw);
        pw.print(firstLine);
        int x=out.size();
		for (int i=0;i<x;i++){
			 pw.print(out.get(i));
		     pw.append('\n');
		}
		//close file when done		
        pw.flush();        
        pw.close();
        fw.close(); 
	}
	
	public static void main (String[]args) throws IOException{
		String csvFile = "C:/Users/pkinner/My Documents/Input/StudentList.csv";
		String oneLine;
		String delimiter=",";
		String header=null;
		FileReader inputFile=null;
		BufferedReader fileIn=null;		
		String sitecode=null;
		String outputFile="Sitecode";
		ArrayList<String> output=new ArrayList<String>(3);
		try{
			
		inputFile= new FileReader (csvFile);
		}
		catch (FileNotFoundException e0){
			System.out.println(csvFile+ " could not be found");
		}
		
		fileIn=new BufferedReader (inputFile);
		oneLine=fileIn.readLine();
		int iterator=0;
		while (oneLine!=null){
			StringTokenizer parser= new StringTokenizer (oneLine, delimiter);
		    sitecode=parser.nextToken();
		    //validate that the sitecode is not blank. If it is, write the file and break.
			if (sitecode.equals("")) {				
				output.add(oneLine);
				writeOutput(output, outputFile, header);
				break; 
			}		    
			//distinguish between header line,and data lines. First is header line
			if (iterator==0){				
				header=oneLine;
				oneLine=fileIn.readLine();				
				iterator++;
			}
			else if (iterator==1){
				outputFile=sitecode;
				output.add(oneLine);
				oneLine=fileIn.readLine();
				iterator++;
			}
			else{
				//if a new sitecode presents itself, write output, initialize vars to begin a new file and a new sitecode
				if ((!sitecode.equals(outputFile))){
					writeOutput(output, outputFile, header);
					outputFile=sitecode;
				//clear all items in the arrayList and initialize a new one
					output.clear();									
					output.add(oneLine); //stores the lines in the file
					oneLine=fileIn.readLine();
					iterator=0;
				}//end if
				else {						
					output.add(oneLine); //stores the lines in the file
					oneLine=fileIn.readLine();
					iterator++;
					}//end else
			}//end else
		}//end while
		
		writeOutput(output, outputFile, header);
		
	}
}
