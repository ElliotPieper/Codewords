import java.util.HashMap;
import java.util.Scanner;

public class StateUI {
    public static State promptForState(){
        //add loop and user confirmation
        Scanner in = new Scanner(System.in);
        System.out.print("How many rows is your puzzle?: ");
        int rows= in.nextInt();
        in.nextLine();
        System.out.print("How many columns is your puzzle?: ");
        int columns= in.nextInt();
        in.nextLine();
        int[][] grid = new int[rows][columns];
        for(int i=0;i<rows;i++){
            System.out.println("Please enter all numbers in row number "+i+" separated by spaces, where 0 indicates a block:");
            for(int j = 0;j<columns;j++){
                grid[i][j]= in.nextInt();
            }
            in.nextLine();
        }
        System.out.println("Grid built");
        HashMap<Integer, Character> key = new HashMap<>();
        while(true){
            System.out.println("Please enter your next key pairing, a number and character, in that order, separated by a space, or a non integer entry once finished: ");
            int num;
            char cha;
            try{
                num=in.nextInt();
                cha = in.nextLine().toCharArray()[1];//messy?
                key.put(num,cha);
            } catch (RuntimeException e){
                break;
            }
        }
        in.nextLine();
        System.out.println("Thank you for your entry");
        return new State(grid,key);
    }
}
