import java.io.*;
import java.util.*;
import java.util.Map.Entry;


public class NGram{

    // Read all corpus file 
    static File f1 = new File("bilim-is-basinda.txt");
	static File f2 = new File("bozkirda.txt");
	static File f3 = new File("degisim.txt");
	static File f4 = new File("denemeler.txt");
	static File f5 = new File("grimms-fairy-tales-p1.txt");


    // Lists that hold the 1,2 and 3-gram values of words
    static List<String> allContentList = new ArrayList<>();
    static List<String> uniGramList = new ArrayList<>();
    static List<String> biGramList = new ArrayList<>();
    static List<String> triGramList = new ArrayList<>();

    // Map that holds 1,2 and 3-gram values and their corresponding frequency values
    static HashMap<String, Integer> frequencyMap;



    // Function that reads the file line by line and adds the words to the allContentList
	public static void readFile(File file) {     
        Scanner scanner;
        String[] words;
		try {
		    scanner = new Scanner(file);
			while (scanner.hasNextLine()) {  
				words = scanner.nextLine().toLowerCase().trim().replaceAll("\\p{Punct}", "").split(" ");
				for (int i = 0; i < words.length; i++) {
					words[i] = words[i].strip();
                    if(words[i] != ""){
                        allContentList.add(words[i]);	
                    }      			
				}
			}
			scanner.close();
		}catch (FileNotFoundException e) {
			System.out.println("File not found");
		}
	}


    // A function that takes the list of all words as a parameter and adds the words to the related list according to their n-gram values.
    public static void nGramCreator(List<String> data, int n){
        if(n == 1){
            for (int i = 0; i <= data.size() - n; i++) {
               uniGramList.add(data.get(i));
            }
        }
        if(n == 2){
            for (int i = 0; i <= data.size() - n; i++) {
                for (int j = 0; j < n-1; j++) {
                    biGramList.add(data.get(i+j) + " " + data.get(i+n-1));
                }
            }
        }
        else if(n == 3){
            for (int i = 0; i <= data.size() - n; i++) {
                for (int j = 0; j < n-2; j++) {
                    triGramList.add(data.get(i+j) + " " + data.get(i+n-2) + " " + data.get(i+n-1));
                }
            }
        }      
    }

    
    // Function that adds 1,2 and 3-grams and their frequency values to the map.
    public static void frequencyCount(List<String> nGramList){
        frequencyMap = new HashMap<>();
        for(int i = 0; i < nGramList.size(); i++){
            if(frequencyMap.containsKey(nGramList.get(i)))
                frequencyMap.put(nGramList.get(i), frequencyMap.get(nGramList.get(i))+1);
            else
                frequencyMap.put(nGramList.get(i), 1);
        }
        /*for(Map.Entry<String,Integer> entry: mp.entrySet()){
            System.out.println(entry.getKey()+ " - " + entry.getValue());}*/
    }


    // Function that sorts 1,2 and 3-grams according to their frequency values and prints the 50 highest ones
    public static void printTop50(HashMap<String, Integer> frequencyMap){
        List<Entry<String, Integer>> sorted = new ArrayList<>(frequencyMap.entrySet());
        Collections.sort(sorted, new Comparator<Entry<String, Integer>>() {

        @Override
        public int compare(Entry<String, Integer> o1, Entry<String, Integer> o2) {
            int comp = Integer.compare(o2.getValue(), o1.getValue());
            if (comp != 0) {
                return comp;
            }
            return o2.getKey().compareTo(o1.getKey());
            }
        });

        int i = 0;
        System.out.println("---------- Top 50 Items ----------\n");
        for (Entry<String, Integer> entry : sorted) {
            System.out.println("Item: " + (i+1) + " -> (\'"  + entry.getKey() + "\', " + entry.getValue() +")");
            if(i++==49){
                break;
            }
        }
    }

    public static void main( String args[] ) {
        
        readFile(f1);
        readFile(f2);
        readFile(f3);
        readFile(f4);
        readFile(f5);

        Scanner scanner = new Scanner(System.in); 
        System.out.print("Enter a n-gram number(n) between <1,2,3> : ");  
        int nGramNumber = scanner.nextInt();  
        System.out.println("\n");

        if(nGramNumber == 1){
            System.out.println("------------ UNIGRAM ------------\n");
            long startTime = System.nanoTime();
            nGramCreator(allContentList, 1);
            frequencyCount(uniGramList);
            long endTime = System.nanoTime();
            System.out.println("Total running time: " + (endTime - startTime)/1000000 + " ms. \n\n");
            printTop50(frequencyMap);
        }
        else if(nGramNumber == 2){
            System.out.println("------------ BIGRAM ------------\n");
            long startTime = System.nanoTime();
            nGramCreator(allContentList, 2);
            frequencyCount(biGramList);
            long endTime = System.nanoTime();
            System.out.println("Total running time: " + (endTime - startTime)/1000000 + " ms. \n\n");
            printTop50(frequencyMap);
        }
        else if(nGramNumber == 3){
            System.out.println("------------ TRIGRAM ------------\n");
            long startTime = System.nanoTime();
            nGramCreator(allContentList, 3);
            frequencyCount(triGramList);
            long endTime = System.nanoTime();
            System.out.println("Total running time: " + (endTime - startTime)/1000000 + " ms. \n\n");
            printTop50(frequencyMap);
        }




        scanner.close();
    }
}
