import java.util.*;

/**
 * Utility class to solve Codewords puzzles
 */
public class Solver {

    private Solver() {
    }

    /**
     *
     * @param unsolved   A solvable codewords state
     * @param dictionary A HashSet like Set containing all words in the usable dictionary for this instance
     * @return The given state with an updated key, making it solved
     */
    public static State solve(State unsolved, HashSet<String> dictionary) {
        SolveState ret = new SolveState(unsolved, dictionary);
        return ret.solve();
    }

    /**
     * Stateful inner class used to iterate and solve unsolved states via a tree structure
     */
    static class SolveState {

        LinkedTree<Pair<State, Boolean>> states;
        HashSet<String> dictionary;
        static final Character[] ALPHABET = new Character[]{'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l',
                                                            'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x',
                                                            'y', 'z',};

        SolveState(State unsolved, HashSet<String> dictionary) {
            states = new LinkedTree<>(new Pair<State, Boolean>(unsolved, false));
            this.dictionary = dictionary;
        }

        public State solve() {
            //messy to use invalidWords?
            boolean invalid = false;
            boolean noUnmatched = false;
            State state = states.getCurrent().getFront();
            while (true) {
                //system.out.println("looped in solve");
                Map<Word, Integer> unmatched = unmatchedAmountMap(state);
                noUnmatched = true;
                for (Word word : unmatched.keySet()) {
                    if (unmatched.get(word) == 0) {
                        if (!validWord(word.toString(state.getKey()))) {
                            //system.out.println("invalid word found, invalidWord triggered on: "+word);
                            invalid = true;
                            break;
                        }
                    } else {
                        noUnmatched = false;
                    }
                }
                if (hasDuplicateLetters(state)) {
                    invalid = true;
                }
                if ((!invalid) && noUnmatched) {
                    return state;
                } else if (!invalid) {
                    Map<Word, Set<Map<Integer, Character>>> wordOptions = stateDictOptions(state);
                    if (wordOptions.isEmpty()) {
                        throw new RuntimeException("No words present, despite the presence of Unmatched words");
                    }
                    Word branchWord = null;
                    int possibilities = Integer.MAX_VALUE;
                    //finds word with the fewest possible solutions
                    for (Word word : wordOptions.keySet()) {
                        if (wordOptions.get(word).size() < possibilities) {
                            branchWord = word;
                            possibilities = wordOptions.get(word).size();
                        }
                    }
                    assert branchWord != null;
                    for (Map<Integer, Character> keyOptions : wordOptions.get(branchWord)) {
                        State newState = new State(state);
                        for (Integer num : keyOptions.keySet()) {
                            newState = newState.addKeyPair(num, keyOptions.get(num));
                        }
                        states.addChild(new Pair<State, Boolean>(newState, false));
                    }
                }
                states.replace(new Pair<State, Boolean>(state, true));
                while (states.getCurrent().getBack()) {
                    if (states.getChildren().isEmpty() || areChildrenVisited()) {
                        if (!states.hasParent()) {
                            throw new RuntimeException("No solution to puzzle");
                        }
                        //system.out.println("going up");
                        states.up();
                    } else {
                        for (int i = 0; i < states.getChildren().size(); i++) {
                            if (!states.getChildren().get(i).getBack()) {
                                //system.out.println("Going down");
                                states.down(i);
                                break;
                            }
                        }
                    }
                    invalid = false;
                    state = states.getCurrent().getFront();
                }
            }
        }

        boolean areChildrenVisited() {
            boolean childrenVisited = true;
            if (states.getChildren().isEmpty() && states.getCurrent().getBack()) {
                throw new IllegalStateException("Pointer has delved into an already failed branch?");
            }
            for (int i = 0; i < states.getChildren().size(); i++) {
                if (!states.getChildren().get(i).getBack()) {
                    childrenVisited = false;
                }
            }
            return childrenVisited;
        }

        boolean hasDuplicateLetters(State state) {
            Set<Character> previouslySeen = new HashSet<>();
            for (Integer num : state.getKey().keySet()) {
                if (previouslySeen.contains(state.getKey().get(num))) {
                    return true;
                } else {
                    previouslySeen.add(state.getKey().get(num));
                }
            }
            return false;
        }

        Map<Word, Integer> unmatchedAmountMap(State state) {
            Map<Word, Integer> ret = new HashMap<>();
            for (Word word : state.getWords()) {
                ret.put(word, word.unMatchedCount(state.getKey().keySet()));
            }
            return ret;
        }

        /**
         * Finds some possible options (cheaply) to branch off of based on the provided state.
         *
         * @param state state to find branching options
         * @return Maps words to all of their potential solutions, represented in key update form
         */
        Map<Word, Set<Map<Integer, Character>>> stateDictOptions(State state) {
            Map<Word, Integer> unmatchedAmount = unmatchedAmountMap(state);
            Map<Word, Set<Map<Integer, Character>>> options = new HashMap<>();
            int lowestBranches = Integer.MAX_VALUE;
            //TODO: Build out test suite and evaluate best values for constant
            for (int i = 1; lowestBranches >= (5 + (6 * (i - 1))); i++) {
                Set<Word> checks = new HashSet<>();
                for (Word word : unmatchedAmount.keySet()) {
                    int unmatched = unmatchedAmount.get(word);
                    if (unmatched == i) {
                        checks.add(word);
                    }
                }
                for (Word word : checks) {
                    StringBuilder base = new StringBuilder();
                    int count = 0;
                    List<Integer> nums = new ArrayList<>();
                    Map<Integer, Integer> seen = new HashMap<>();
                    for (int num : word.getNumbers()) {
                        if (seen.containsKey(num)) {
                            base.append(seen.get(num));
                        } else if (!state.getKey().containsKey(num)) {
                            base.append(count);
                            nums.add(num);
                            seen.put(num, count);
                            count++;
                        } else {
                            base.append(state.getKey().get(num));
                        }
                    }
                    Map<Character, Integer> ret = new HashMap<>();
                    for (int j = 0; j < nums.size(); j++) {
                        ret.put(Integer.toString(j).charAt(0), nums.get(j));
                    }
                    options.put(word, dictOptions(base.toString(), ret));
                    if (options.get(word).size() < lowestBranches) {
                        lowestBranches = options.get(word).size();
                    }
                }
            }
            return options;
        }

        boolean validWord(String str) {
            return dictionary.contains(str);
        }

        /**
         * Calculates and returns all possible key solutions for a given unfinished word with blanks provided
         *
         * @param base               unfinished word, with blanks replaced by characters defined later
         * @param placeHolderNumbers Characters representing blanks, and the numbers that character has covered
         * @return the set of all possible changes to the keyset that form valid words
         **/

        Set<Map<Integer, Character>> dictOptions(String base, Map<Character, Integer> placeHolderNumbers) {
            if (placeHolderNumbers.size() > 6) {
                //This should literally never happen if the puzzle is human solvable (and well-made)
                throw new IllegalArgumentException(base + " has too many placeholders for reasonably timed computation");
            }
            //convert to pair, Map.entry instead?
            List<Pair<Character, Integer>> pairedPlaceHolders = new ArrayList<>();
            for (Character cha : placeHolderNumbers.keySet()) {
                pairedPlaceHolders.add(new Pair<Character, Integer>(cha, placeHolderNumbers.get(cha)));
            }
            Set<Map<Integer, Character>> retSet = new HashSet<>();
            int[] iter = new int[pairedPlaceHolders.size()];
            for (int i = 0; i < pairedPlaceHolders.size(); i++) {
                iter[i] = 0;
            }
            boolean end = false;
            while (!end) {
                String active = base;
                for (int i = 0; i < pairedPlaceHolders.size(); i++) {
                    //String.replace seems to be faster than using stringBuilders here
                    active = active.replace(pairedPlaceHolders.get(i).getFront(), ALPHABET[iter[i]]);
                }
                if (dictionary.contains(active)) {
                    Map<Integer, Character> potentialSols = new HashMap<>();
                    for (int i = 0; i < pairedPlaceHolders.size(); i++) {
                        potentialSols.put(pairedPlaceHolders.get(i).getBack(), ALPHABET[iter[i]]);
                    }
                    retSet.add(potentialSols);
                }
                do {
                    for (int i = 0; i < pairedPlaceHolders.size(); i++) {
                        if (++iter[i] >= 26) {
                            iter[i] = 0;
                            if (i == pairedPlaceHolders.size() - 1) {
                                end = true;
                            }
                        } else {
                            break;
                        }
                    }
                } while (arrayRepeatsElement(iter));
            }
            return retSet;
        }

        boolean arrayRepeatsElement(int[] arr) {
            HashSet<Integer> seen = new HashSet<>();
            for (int i = 0; i < arr.length; i++) {
                if (seen.contains(arr[i])) {
                    return true;
                }
                seen.add(arr[i]);
            }
            return false;
        }
    }
}

