package calcul;

/**
 * Created by lion on 12/06/17.
 */
import jdk.nashorn.internal.runtime.regexp.joni.exception.ValueException;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class parseTree<T> {
    public int cntrl = 0;

    public BinaryTree buildParseTree(String exp) {
        Pattern pattern1 = Pattern.compile("[tf]");
        ArrayList<String> operators = new ArrayList<>();
        operators.add("+");
        operators.add("-");
        operators.add("/");
        operators.add("*");
        ArrayList<String> operators_ex = new ArrayList<>();
        operators_ex.add("+");
        operators_ex.add("-");
        operators_ex.add("/");
        operators_ex.add("*");
        operators_ex.add("(");
        operators_ex.add(")");
        exp = exp.replace(" ", "");
        exp = exp.replace("+", " + ").replace("-", " - ").replace("/", " / ");
        exp = exp.replace("*", " * ");
        exp = exp.replace("(", " ( ").replace(")", " ) ").trim();
        List<String> exp_list = Arrays.asList(exp.split("\\s+"));
        for (String s: exp_list){
            Matcher matcher = pattern1.matcher(s);
            if (matcher.find())
            {return buildParseTreeLogical(exp);}
        }

        BinaryTree e_tree = new BinaryTree("");
        BinaryTree current = e_tree;
        for (String token : exp_list) {
            if (token.equals("(")) {
                current.insertLeft("");
                current = current.left;

            } else if (operators.contains(token)) {
                current.item = token;
                current.insertRight("");
                current = current.right;
            } else if (!(operators_ex.contains(token))) {
                current.item = token;
                current = current.parent;
            } else if (token.equals(")")) {
                current = current.parent;
            } else {
                throw new ValueException("Value Error");
            }

        }
        return e_tree;
    }

    public BinaryTree<T> buildParseTreeLogical(String exp) {
        ArrayList<String> operators = new ArrayList<>();
        operators.add("||");
        operators.add("&&");
        operators.add("->");
        ArrayList<String> values = new ArrayList<>();
        values.add("t");
        values.add("f");
        exp = exp.replace(" ", "");
        exp = exp.replace("||", " || ").replace("&&", " && ").replace("->", " -> ");
        exp = exp.replace("~", " ~ ");
        exp = exp.replace("(", " ( ").replace(")", " ) ").trim();
        List<String> exp_list = Arrays.asList(exp.split("\\s+"));
        BinaryTree e_tree = new BinaryTree("");
        BinaryTree current = e_tree;
        for (String token : exp_list) {
            if (token.equals("(")) {
                current.insertLeft("");
                current = current.left;
            }
            else if (operators.contains(token)) {
                if (current.item.equals("~")) {
                    current.parent.item = token;
                    current.insertRight("");
                    current = current.right;
                } else {
                    current.item = token;
                    current.insertRight("");
                    current = current.right;
                }
            }
            else if (values.contains(token)) {
                current.item = token;
                current = current.parent;
                if (current != null) {
                    if (current.item.equals("~")) {
                        current = current.parent;
                    }
                }
            }
            else if (token.equals("~")){
                current.item = token;
                current.insertLeft("");
                current = current.left;
            }
            else if (token.equals(")")){
                current = current.parent;
            }
            else {
                throw new ValueException("Value Error");
            }
        }
        return e_tree;
    }

    private void prepareTree(BinaryTree tree){
        Pattern pattern = Pattern.compile("[\\.]");
        Pattern pattern1 = Pattern.compile("[tf]");
        if (tree != null){
            prepareTree(tree.left);
            Matcher matcher = pattern.matcher(tree.item.toString());
            Matcher matcher1 = pattern1.matcher(tree.item.toString());
            if (matcher.find()){cntrl = 1;}
            if (matcher1.find()){cntrl = 2;}
            prepareTree(tree.right);
        }
    }

    public void prepareTreeLogical(BinaryTree tree){
        Pattern pattern = Pattern.compile("[tf]");
        if (tree != null){
            prepareTreeLogical(tree.left);
            Matcher matcher = pattern.matcher(tree.item.toString());
            if (matcher.find()){
                if (tree.item.equals("t")){
                    boolean b = Boolean.valueOf(true);
                    tree.item = b;
                }
                else {
                    tree.item = Boolean.valueOf(false);
                }
            }
            prepareTreeLogical(tree.right);
        }
    }
    public void prepareTreeBigDecimal(BinaryTree tree){
        Pattern pattern2 = Pattern.compile("[0-9]+");// to be finished...
        if (tree != null){
            prepareTreeBigDecimal(tree.left);
            Matcher matcher = pattern2.matcher(tree.item.toString());
            if (matcher.find()){
                BigDecimal bd1 = new BigDecimal(tree.item.toString());
                tree.item = bd1;
            }
            prepareTreeBigDecimal(tree.right);
        }
    }
    public void prepareTreeBigInt(BinaryTree tree){
        Pattern pattern2 = Pattern.compile("[0-9]+");
        if (tree != null){
            prepareTreeBigInt(tree.left);
            Matcher matcher = pattern2.matcher(tree.item.toString());
            if (matcher.find()){
                BigInteger bI1 = new BigInteger(tree.item.toString());
                tree.item = bI1;
            }
            prepareTreeBigInt(tree.right);
        }
    }
    public BigInteger evaluateParseTreeBigInt(BinaryTree tree){
        BinaryTree<T> leftT = tree.left;
        BinaryTree<T> righT = tree.right;
        boolean p = (leftT != null) && (righT != null);
        //System.out.println("tree in first: "+tree.item+": :");
        //System.out.println(p);
        if (p){
            if (tree.item.equals("+")){
                //System.out.println("tree in first: "+tree.item);
                return Functions.add(evaluateParseTreeBigInt(leftT), evaluateParseTreeBigInt(righT));
            }
            else if (tree.item.equals("-")){
                return Functions.sub(evaluateParseTreeBigInt(leftT), evaluateParseTreeBigInt(righT));
            }
            else if (tree.item.equals("*")){
                return Functions.mul(evaluateParseTreeBigInt(leftT), evaluateParseTreeBigInt(righT));
            }
            else if (tree.item.equals("/")){
                return Functions.div(evaluateParseTreeBigInt(leftT), evaluateParseTreeBigInt(righT));
            }
            else {
                throw new ValueException("Value Error");}
        }
        else {
            BigInteger bI1 = new BigInteger(tree.item.toString());
            return bI1;
        }
    }
    public BigDecimal evalBigdecimal(BinaryTree tree){
        BinaryTree<T> leftT = tree.left;
        BinaryTree<T> righT = tree.right;
        boolean p = (leftT != null) && (righT != null);
        if (p) {
            if (tree.item.equals("+")){
                return Functions.add(evalBigdecimal(leftT), evalBigdecimal(righT));
            }
            else if (tree.item.equals("-")){
                return Functions.sub(evalBigdecimal(leftT), evalBigdecimal(righT));
            }
            else if (tree.item.equals("*")){
                return Functions.mul(evalBigdecimal(leftT), evalBigdecimal(righT));
            }
            else if (tree.item.equals("/")){
                return Functions.div(evalBigdecimal(leftT), evalBigdecimal(righT));
            }
            else {
                throw new ValueException("Value Error");
            }
        }
        else {
            BigDecimal bI1 = new BigDecimal(tree.item.toString());
            return bI1;
        }
    }
    public Object evalLogical(BinaryTree tree){
        BinaryTree<T> leftT = tree.left;
        BinaryTree<T> righT = tree.right;
        boolean p = (leftT != null) && (righT != null);
        if ( (leftT != null) && (righT == null) ){
            if (tree.item.equals("~")){
                return Functions.not(evalLogical(tree.left));
            }
        }
        else if (p){
            if (tree.item.equals("||")){
                return Functions.or(evalLogical(tree.left), evalLogical(tree.right));
            }
            else if (tree.item.equals("&&")){
                return Functions.and(evalLogical(tree.left), evalLogical(tree.right));
            }
            else if (tree.item.equals("->")){
                return Functions.imp(evalLogical(tree.left), evalLogical(tree.right));
            }
            else throw new ValueException("Value Error in Tree");
        }
        return tree.item;
    }

    public Object eval(BinaryTree tree){
        prepareTree(tree);
        if (cntrl == 1) { // 1 means bigdecimals
            prepareTreeBigDecimal(tree);
            return evalBigdecimal(tree);
        }
        else if (cntrl == 2) {   //2 means logical
            prepareTreeLogical(tree);
            return evalLogical(tree);
        }
        else {
            prepareTreeBigInt(tree);//0 (default) means biginteger
            return evaluateParseTreeBigInt(tree);
        }
    }

}
