import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

/*
TODO: return set, enforce hashed sets later 
 */
/**
 * Fragile utility class that manually parses and converts into a HashSet the specific Json dictionary in this filesystem
 */
public class JsonDictHash {
    /**
     * @return HashSet containing every word in the attached dictionary
     */
    public static HashSet<String> get(){
    HashSet<String> words= new HashSet<>();
    try(Scanner dictRead=new Scanner(new File("words_dictionary.json"))){
        int i = 0;
        while(dictRead.hasNextLine()){
            String str= dictRead.nextLine();
            if(str.contains("}")||str.contains("{")){
                continue;
            }
            int start=str.indexOf('"');
            int end=str.indexOf('"',start+1);
            if (end==-1){
                throw new RuntimeException("line "+i+" "+str);
            }
            words.add(str.substring(start+1,end));
            i++;
        }
    }
    catch(FileNotFoundException e){
        throw new RuntimeException(e);
    }
    return words;
}
}
