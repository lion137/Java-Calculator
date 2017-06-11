package calcul;

/**
 * Created by lion on 12/06/17.
 */
import java.util.ArrayList;

public class BinaryTree<T> {
    T item;
    BinaryTree parent;
    BinaryTree left;
    BinaryTree right;
    public BinaryTree(T elem){
        item = elem;
    }
    public void insertLeft(T elem){
        if (this.left == null){
            BinaryTree t = new BinaryTree<>(elem);
            this.left = t;
            t.parent = this;
        }
        else{
            BinaryTree t = new BinaryTree(item);
            t.parent = this;
            t.left = this.left;
            this.left.parent = t;
            this.left = t;
        }
    }
    public void insertRight(T elem){
        if (this.right == null){
            BinaryTree t = new BinaryTree(elem);
            this.right = t;
            t.parent = this;
        }
        else {
            BinaryTree t = new BinaryTree(item);
            t.parent = this;
            t.right = this.right;
            this.right.parent = t;
            this.right = t;
        }
    }
    public void inorder(BinaryTree<T> tree){
        if (tree != null){
            inorder(tree.left);
            System.out.println(tree.item);
            inorder(tree.right);
        }

    }

    public void postOrder(BinaryTree<T> tree){
        if (tree != null){
            postOrder(tree.left);
            postOrder(tree.right);
            System.out.println(tree.item);
        }
    }

    public int count(){
        BinaryTree<T> righT = this.right;
        BinaryTree<T> lefT = this.left;
        int c = 1;
        if (righT != null) {c += righT.count();}
        if (lefT != null) {c += lefT.count();}
        return c;
    }
    public ArrayList<T> treeToList(BinaryTree<T> tree, ArrayList<T> list){
        if (tree != null) {
            treeToList(tree.left, list);
            list.add(tree.item);
            treeToList(tree.right, list);
        }
        return list;
    }
}