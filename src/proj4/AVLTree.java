package proj4;

import java.lang.*;

//Alec Marcum
//Comp 282
//Implementing a Binary Search Tree as a generic class
//Posted as BST.java for Project 3

/**Notes to self**/
//everything should be working but isHeightBalanced seems to make t lose it's parent which causes NullPointer problems
//you might have to make a separate isHeightBalanced recursive method that goes up the tree rather than down (probably)


class AVLNode<E> {
    E item;
    AVLNode<E> left;
    AVLNode<E> right;
    AVLNode<E> parent;
    int height;

    public AVLNode(E x) {
        item = x;
        left = null;
        right = null;
        parent = null;
        height = 0;

    }

    public AVLNode(E x, AVLNode<E> left, AVLNode<E> right, AVLNode<E> parent) {
        item = x;
        this.left = left;
        this.right = right;
        this.parent = parent;
        height = 0;
    }

    public String toString() {
        return "(i:" + item + "h:" + height + ")";
    }
}

/*----------------class proj4.AVLTree ---------------------------*/
public class AVLTree<E extends Comparable<E>> {
    private AVLNode<E> root;
    private int size;
    private AVLNode<E> badNode; //placeholder for insert and delete

    public AVLTree() {
        root = null;
        size = 0;
    }

    /*---------------- public operations --------------------*/


    public int getSize() {
        return size;
    }


    public boolean find(E x) {
        if (find(x, root) == null)
            return false;
        else
            return true;
    }


    public void preOrderTraversal() {
        preOrder(root);
        System.out.println();
    }

    public void inOrderTraversal() {
        inOrder(root);
        System.out.println();
    }


    public boolean insert(E x) {

        if (root == null) {
            root = new AVLNode(x, null, null, root);
            size++;
            return true;
        }

        AVLNode<E> parent = null;
        AVLNode<E> p = root;

        while (p != null) {
            if (x.compareTo(p.item) < 0) {
                parent = p;
                p = p.left;
            } else if (x.compareTo(p.item) > 0) {
                parent = p;
                p = p.right;
            } else  // duplicate value
                return false;
        }

        //attach new node to parent
        AVLNode<E> insertedNode = new AVLNode<E>(x, null, null, parent);
        if (x.compareTo(parent.item) < 0)
            parent.left = insertedNode;
        else
            parent.right = insertedNode;
        setHeight(insertedNode);
        size++;
        if(!isHeightBalanced())
        {
            restructure(problemNode(insertedNode), problemNode(insertedNode).parent);
        }
        return true;

    }  //insert


    public boolean remove(E x) {
        if (root == null)
            return false;  //x is not in tree

        //find x
        AVLNode<E> p = find(x, root);
        if (p == null)
            return false;  //x not in tree

        //Case: p has a right child child and no left child
        if (p.left == null && p.right != null)
            deleteNodeWithOnlyRightChild(p);

            //Case: p has a left child and has no right child
        else if (p.left != null && p.right == null)
            deleteNodeWithOnlyLeftChild(p);

            //case: p has no children
        else if (p.left == null && p.right == null)
            deleteLeaf(p);

        else //case : p has two children. Delete successor node
        {
            AVLNode<E> succ = getSuccessorNode(p);

            p.item = succ.item;

            //delete succ node
            if (succ.right == null)
                deleteLeaf(succ);
            else
                deleteNodeWithOnlyRightChild(succ);
        }
        return true;
    }   //remove

    public E findMax() {
        AVLNode<E> target = root;
        while (target.right != null) {
            target = target.right;
        }
        return target.item;
    }

    public E findMin() {
        AVLNode<E> target = root;
        while (target.left != null) {
            target = target.left;
        }
        return target.item;
    }

    public E removeMin() {
        AVLNode<E> target = root;
        while (target.left != null) {
            target = target.left;
        }
        remove(target.item);
        return target.item;
    }

    public E removeMax() {
        AVLNode<E> target = root;
        while (target.right != null) {
            target = target.right;
        }
        remove(target.item);
        return target.item;
    }

    public int getHeight() {
        return root.height;
    }

    //AVLTree Methods
    public boolean isHeightBalanced() {
        return isHeightBalanced(root);
    }

    /********************private methods ******************************/


    private AVLNode<E> find(E x, AVLNode<E> t) {
        AVLNode<E> p = t;
        while (p != null) {
            if (x.compareTo(p.item) < 0)
                p = p.left;
            else if (x.compareTo(p.item) > 0)
                p = p.right;
            else  //found x
                return p;
        }
        return null;  //x is not found
    }


//my helper methods for adding and subtracting height

    private void setHeight(AVLNode<E> x) {
        while (x.parent != null) {
            needHelp(x.parent);
            x = x.parent;
        }
    }

    private void needHelp(AVLNode<E> x) {
        int tempR = -1;
        int tempL = -1;

        if (x.right != null) {
            tempR = x.right.height;
        }
        if (x.left != null) {
            tempL = x.left.height;
        }

        x.height = Math.max(tempL, tempR) + 1;
    }

    private E leftmost(AVLNode<E> x) {
        while (x.left != null) {
            x = x.left;
        }

        return x.item;
    }

    private E rightmost(AVLNode<E> x) {
        while (x.right != null) {
            x = x.right;
        }

        return x.item;
    }

    //AVLTree Methods
    private boolean isHeightBalanced(AVLNode t) {
        int lhs = -1;
        int rhs = -1;

        if (t == null)
            return true;

        if (t.left != null && t.right != null) {
            lhs = t.left.height;
            rhs = t.right.height;
        } else if (t.left != null) {
            lhs = t.left.height;
        } else if (t.right != null) {
            rhs = t.right.height;
        }

        if (Math.abs(lhs - rhs) <= 1 && isHeightBalanced(t.left) && isHeightBalanced(t.right)) {
            return true;
        }
        return false;
    }

    private AVLNode problemNode(AVLNode t)
    {
        int lhs = -1;
        int rhs = -1;

        if (t.left != null && t.right != null) {
            lhs = t.left.height;
            rhs = t.right.height;
        } else if (t.left != null) {
            lhs = t.left.height;
        } else if (t.right != null) {
            rhs = t.right.height;
        }
        if(Math.abs(lhs - rhs)>1)
            return t;
        else
            return problemNode(t.parent);
    }

    private AVLNode<E> restructure(AVLNode<E> t, AVLNode savedParent) {
        AVLNode x = t;
        AVLNode y;
        AVLNode z;
        AVLNode top;
        int yrhs = -1;
        int ylhs= -1;
        int xrhs = -1;
        int xlhs= -1;

        //sets xyz
        if(x.left != null)
            xlhs = x.left.height;
        if(x.right!= null)
            xrhs = x.right.height;

        if (xlhs - xrhs > 0)
            y = x.left;
        else
            y = x.right;

        if(y.left != null)
            ylhs = y.left.height;
        if(y.right!= null)
            yrhs = y.right.height;

        if (ylhs - yrhs > 0) {
            z = y.left;
        } else
            z = y.right;

        //restructuring
        if (x.left == y && y.left == z)
            top = leftLeft(x, y, z);
        else if (x.right == y && y.right == z)
            top = rightRight(x, y, z);
        else if (x.left == y && y.right == z)
            top = leftRight(x, y, z);
        else
            top = rightLeft(x, y, z);

        if(t!=root)
        {
            top.parent = savedParent;

            if (savedParent.left.item == t.item)
                savedParent.left = top;
            else
                savedParent.right = top;
        }

        setHeight(top);

        return top;
    }

    //cases 1,2,3,4 respectively
    private AVLNode<E> leftLeft(AVLNode x, AVLNode y, AVLNode z) {
        AVLNode temp;
        temp = y.right;
        y.right = x;
        x.left = temp;

        setHeight(x);
        setHeight(y);

        return y;

    }

    private AVLNode<E> rightRight(AVLNode x, AVLNode y, AVLNode z) {
        AVLNode temp;
        temp = y.left;
        y.left = x;
        x.right = temp;

        setHeight(x);
        setHeight(y);
        return y;
    }

    private AVLNode<E> leftRight(AVLNode x, AVLNode y, AVLNode z) {
        AVLNode temp;
        AVLNode temp2;
        temp = z.right;
        temp2 = z.left;
        z.right = x;
        z.left = y;
        y.right = temp2;
        x.left = temp;

        setHeight(x);
        setHeight(y);
        setHeight(z);

        return z;

    }

    private AVLNode<E> rightLeft(AVLNode x, AVLNode y, AVLNode z) {
        AVLNode temp;
        AVLNode temp2;

        temp = z.left;
        temp2 = z.right;
        z.left = x;
        z.right = y;
        x.right = temp;
        y.left = temp2;

        setHeight(x);
        setHeight(y);
        setHeight(z);

        return z;
    }


    /***************** private remove helper methods ***************************************/

    private void deleteLeaf(AVLNode<E> t) {
        if (t == root)
            root = null;
        else {
            AVLNode<E> parent = t.parent;
            if (t.item.compareTo(parent.item) < 0)
                parent.left = null;
            else
                parent.right = null;
        }
        size--;
        setHeight(t);
        if(!isHeightBalanced())
        {
            restructure(problemNode(t.parent), problemNode(t.parent).parent);
        }
    }

    private void deleteNodeWithOnlyLeftChild(AVLNode<E> t) {
        if (t == root) {
            root = t.left;
            root.parent = null; //WAS WRONG t.left.parent = root;
        } else {
            AVLNode<E> parent = t.parent;
            if (t.item.compareTo(parent.item) < 0) {
                parent.left = t.left;
                t.left.parent = parent;
            } else {
                parent.right = t.left;
                t.left.parent = parent;
            }
        }
        setHeight(t);
        size--;
        if(!isHeightBalanced())
        {
            restructure(problemNode(t.parent), problemNode(t.parent).parent);
        }
    }

    private void deleteNodeWithOnlyRightChild(AVLNode<E> t) {
        if (t == root) {
            root = t.right;
            root.parent = null; // WAS WRONG t.right.parent = root;
        } else {
            AVLNode<E> parent = t.parent;
            if (t.item.compareTo(parent.item) < 0) {
                parent.left = t.right;
                t.right.parent = parent;
            } else {
                parent.right = t.right;
                t.right.parent = parent;
            }
        }
        setHeight(t);
        size--;
        if(!isHeightBalanced())
        {
            restructure(problemNode(t.parent), problemNode(t.parent).parent);
        }
    }

    private AVLNode<E> getSuccessorNode(AVLNode<E> t) {
        //only called when t.right != null
        AVLNode<E> parent = t;
        AVLNode<E> p = t.right;
        while (p.left != null) {
            parent = p;
            p = p.left;
        }
        return p;
    }


    //private traversal methods


    private void preOrder(AVLNode<E> t) {
        if (t != null) {
            System.out.print(t + " ");
            preOrder(t.left);
            preOrder(t.right);
        }
    }

    private void inOrder(AVLNode<E> t) {
        if (t != null) {

            inOrder(t.left);
            System.out.print(t + " ");
            inOrder(t.right);
        }
    }


}
