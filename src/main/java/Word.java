import java.util.*;

/**
 * Represents a presolved word, or an array of numbers. Provides useful functions in the context of a codewords puzzle
 */
public class Word {
    private final int[] numbers;
    public Word(int[] numbers){
        this.numbers= new int[numbers.length];
        for(int i = 0;i<numbers.length;i++){
            this.numbers[i]=numbers[i];
        }
    }
    
    //Too smart?, Move to State?

    /**
     * @param keySet key's keys to attempt to check whether the word could be matched against
     * @return the current letters(numbers) that were not able to be matched
     */
    public Set<Integer> unMatched(Set<Integer> keySet){
        HashSet<Integer> noMatch=new HashSet<>();
        for(int i = 0;i<numbers.length;i++){
            if(!keySet.contains(numbers[i])){
                noMatch.add(numbers[i]);
            }
        }
        return noMatch;
    }

    //could be cheaper
    public Integer unMatchedCount(Set<Integer> keySet){
        return unMatched(keySet).size();
    }
    
    public int[] getNumbers(){
        int[] ret= new int[numbers.length];
        for(int i=0;i<numbers.length;i++){
            ret[i]=numbers[i];
        }
        return ret;
    }

    /**
     * @param keyset key to be used
     * @return true if able to be matched fully, otherwise false
     */
    public boolean isMatched(Set<Integer> keyset){
        //could be cheaper
         return unMatchedCount(keyset)==0;
    }

    public String toString(){
        StringBuilder ret= new StringBuilder();
        for(int i = 0;i<numbers.length;i++){
            ret.append(numbers[i]).append(" ");
        }
        return ret.toString();
    }
    public String toString(Map<Integer,Character> key){
        StringBuilder ret = new StringBuilder();
        for (int number : numbers) {
            if(key.containsKey(number)) {
                ret.append(key.get(number));
            }
            else{
                ret.append(number);
            }
        }
        return ret.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Word word)) return false;
        return Objects.deepEquals(numbers, word.numbers);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(numbers);
    }
}
