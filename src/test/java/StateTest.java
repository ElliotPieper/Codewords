import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

//barebones functional test
class StateTest {
    State state = new State(new int[][]{
            {1, 2, 3, 4, 5},
            {6, 7, 8, 9, 10},
            {11, 12, 13, 14, 15}}
            , new HashMap<>());

    @Test
    void getWords() {
        List<int[]> words = new ArrayList<>();
        words.add(new int[]{1, 2, 3, 4, 5});
        words.add(new int[]{6, 7, 8, 9, 10});
        words.add(new int[]{11, 12, 13, 14, 15});
        words.add(new int[]{1, 6, 11});
        words.add(new int[]{2, 7, 12});
        words.add(new int[]{3, 8, 13});
        words.add(new int[]{4, 9, 14});
        words.add(new int[]{5, 10, 15});
        for (Word word : state.getWords()) {
            boolean bool = false;
            for (int i = 0; i < words.size(); i++) {
                if (Arrays.equals(word.getNumbers(), words.get(i))) {
                    bool = true;
                    words.remove(i);
                    i--;
                }
            }
            assertTrue(bool);
        }
    }

    @Test
    void addRemoveKeyPair() {
        state = state.addKeyPair(1, 'a');
        state = state.addKeyPair(2, 'b');
        assertEquals(Map.of(1, 'a', 2, 'b'), state.getKey());
        state = state.removeKeyPair(1);
        assertEquals(Map.of(2, 'b'), state.getKey());
    }


    @Test
    void testEquals() {
        assertEquals(new State(new int[][]{
                {1, 2, 3, 4, 5},
                {6, 7, 8, 9, 10},
                {11, 12, 13, 14, 15}}
                , new HashMap<>()), state);
        state.addKeyPair(1, 'd');
        assertEquals(new State(new int[][]{
                {1, 2, 3, 4, 5},
                {6, 7, 8, 9, 10},
                {11, 12, 13, 14, 15}}
                , new HashMap<>()), state);
        state = state.addKeyPair(1, 'd');
        assertNotEquals(new State(new int[][]{
                {1, 2, 3, 4, 5},
                {6, 7, 8, 9, 10},
                {11, 12, 13, 14, 15}}
                , new HashMap<>()), state);
    }
}