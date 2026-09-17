import java.util.HashMap;
import java.util.Map;

public class Codewords {
    public static void main(String[] args) {
        System.out.println(Solver.solve(StateUI.promptForState(),JsonDictHash.get()));
    }

}
