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
        System.out.println("Welcome to calculator, type h to help,\ntype q/quit to exit");
        String help = "When do arithmetic use fully parenthesized expressions, i.e\n" +
                "for every binary operator there is one pair of parenthesis (left and right).\n" +
                "Ex.: (((123.9999919191919191919191 - 999)*999999999999999999999999999999999999)/2)" +
                "The same rule obeys when evaluate propositional calculus expressions, also as a\n" +
                "logical value use: t - for true; f - for false. Use: -> for implication,\n" +
                "&& for and, || for or, ex.: ((t -> f) && (f || f))    ";
        Scanner scan = new Scanner(System.in);
        while (true) {
            System.out.print("java-simple-calc> ");
            String input = scan.nextLine();
            t1 = p1.buildParseTree(input);
            if (input.equals("quit") || (input.equals("q"))) break;
            if (input.equals("h")) {System.out.println(help); continue;}
            System.out.println(p1.eval(t1));
        }

    }
}

