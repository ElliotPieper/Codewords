import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
//barebones functional test
class WordTest {
    Map<Integer,Character> smallKey=Map.of(1,'a',2,'b',26,'z');
    Map<Integer,Character> largeKey=Map.of(1,'a',2,'b',3,'c',4,'d',5,'e');
    Word word= new Word(new int[]{1,2,3,4,5});
    @Test
    void unMatched() {
        assertEquals(Set.of(3,4,5),word.unMatched(smallKey.keySet()));
        assertEquals(Set.of(),word.unMatched(largeKey.keySet()));
    }

    @Test
    void unMatchedCount() {
        assertEquals(3,word.unMatchedCount(smallKey.keySet()));
        assertEquals(0,word.unMatchedCount(largeKey.keySet()));
    }

    @Test
    void isMatched() {
        assertFalse(word.isMatched(smallKey.keySet()));
        assertTrue(word.isMatched(largeKey.keySet()));
    }
}