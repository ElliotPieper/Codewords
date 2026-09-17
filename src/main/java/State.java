import java.util.*;

/**
 * Immutable class representing the current state of a codewords puzzle.
 */
public class State {

    private final int[][] grid;
    private final Map<Integer, Character> key;
    private final List<Word> words;

    /**
     * @return the integer grid of the state
     */
    public int[][] getGrid() {
        int[][] ret = new int[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                ret[i][j] = grid[i][j];
            }
        }
        return ret;
    }

    /**
     * @return a deep copy of the current integer->character mapping for applying to the grid of the puzzle
     */
    public Map<Integer, Character> getKey() {
        Map<Integer, Character> ret = new HashMap<>();
        for (Integer keys : key.keySet()) {
            ret.put(keys, key.get(keys));
        }
        return ret;
    }

    /**
     * @return a deep copy of found words (single letter words are excluded in all manners)
     */
    public List<Word> getWords() {
        List<Word> ret = new ArrayList<>();
        for (Word word : words) {
            ret.add(new Word(word.getNumbers()));
        }
        return ret;
    }

    /**
     * @param grid Grid of the codewords puzzle, containing numbers 0-26, where 0 represents non-fillable squares in the grid.
     * @param key  Key as best known of codewords puzzle, in which the format is [Number, Assigned letter]
     */
    public State(int[][] grid, Map<Integer, Character> key) {
        try {
            int a = grid[0][0];
        } catch (ArrayIndexOutOfBoundsException e) {
            throw new IllegalArgumentException(e);
        }
        this.key = new HashMap<>();
        words = new ArrayList<>();
        this.grid = new int[grid.length][grid[0].length];
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                this.grid[i][j] = grid[i][j];
            }
        }
        for (Integer num : key.keySet()) {
            this.key.put(num, key.get(num));
        }
        updateWords();
    }

    /**
     * Creates a new, entirely independent object
     */
    public State(State state) {
        this.grid = state.getGrid();
        this.key = state.getKey();
        this.words = state.getWords();
    }

    private void updateWords() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                if (grid[i][j] == 0) {
                    continue;
                }
                if (i == 0 || (grid[i - 1][j] == 0)) {
                    Word word = new Word(ScanLine(i, j, 0, 1));
                    if (word.getNumbers().length > 1) {
                        words.add(word);
                    }
                }
                if (j == 0 || (grid[i][j - 1] == 0)) {
                    Word word = new Word(ScanLine(i, j, 1, 0));
                    if (word.getNumbers().length > 1) {
                        words.add(word);
                    }
                }
            }
        }
    }

    private int[] ScanLine(int row, int col, int colStep, int rowStep) {
        List<Integer> nums = new ArrayList<>();
        while (((row) < grid.length) && ((col) < grid[row].length)) {
            if (grid[row][col] == 0) {
                break;
            }
            nums.add(grid[row][col]);
            row += rowStep;
            col += colStep;
        }
        int[] ret = new int[nums.size()];
        for (int i = 0; i < nums.size(); i++) {
            ret[i] = nums.get(i);
        }
        return ret;
    }

    /**
     * Returns a copy with the added KeyPair
     */
    public State addKeyPair(int num, char letter) {
        Map<Integer, Character> newKey = getKey();
        newKey.put(num, letter);
        return new State(getGrid(), newKey);
    }

    public State removeKeyPair(int num) {
        Map<Integer, Character> newKey = getKey();
        newKey.remove(num);
        return new State(getGrid(), newKey);
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof State state)) {
            return false;
        }
        return Objects.deepEquals(grid, state.grid) && Objects.equals(key, state.key) && Objects.equals(words, state.words);
    }

    @Override
    public int hashCode() {
        return Objects.hash(Arrays.deepHashCode(grid), key, words);
    }

    /**
     * @return the grid with an attempted solve, where # represents blank spaces, and ? represents unknowns
     */
    public String toString() {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (key.containsKey(grid[i][j])) {
                    ret.append(key.get(grid[i][j])).append("  ");
                }
                else if (grid[i][j] == 0) {
                    ret.append("#").append("  ");
                }
                else {
                    ret.append(grid[i][j]).append(" ");
                    if (!((grid[i][j] / 10) > 0)) {
                        ret.append(" ");
                    }
                }

            }
            ret.delete(ret.length() - 2, ret.length());
            ret.append('\n');
        }
        ret.delete(ret.length() - 1, ret.length());
        return ret.toString();
    }
    
}



