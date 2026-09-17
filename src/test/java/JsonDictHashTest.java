import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
//barebones functional test
class JsonDictHashTest {

    @org.junit.jupiter.api.Test
    void hash() {
        HashSet<String> dictionary=JsonDictHash.get();
        String[] wordsArray = new String[]{
                "ossify", "jangling", "equity", "biennials", "talk", "zoom", "entry", "ridge", "satirical", "wallpaper", "basin", "scamp", "crux", "asps", "allusive", "alkali", "motherly", "bronze", "overthrow", "spasm", "squalid", "leaflet", "fate", "jeep", "paste", "gossip", "civil", "axiom", "torque", "genre", "ruby", "blur", "idiotic", "sustain", "gully", "landslide"
        };
        Set<String> words = new HashSet<>(List.of(wordsArray));
        for(String word:words){
            assertTrue(dictionary.contains(word));
        }
    }
}