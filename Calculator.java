package calcul;

/**
 * Created by lion on 12/06/17.
 */
import java.io.IOException;
import java.util.Scanner;

public class Calculator {
    public static void main(String[] args) throws IOException {
        BinaryTree t1 = new BinaryTree("");
        parseTree p1= new parseTree();
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.print("java-simple-calc> ");
            String input = scan.nextLine();
            t1 = p1.buildParseTree(input);
            if (input.equals("quit") || (input.equals("q"))) break;
            System.out.println(p1.eval(t1));
        }

    }
}
