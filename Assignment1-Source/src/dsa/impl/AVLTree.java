//UCD Number: 21207500  Name:LiYan Tao
package dsa.impl;

import dsa.iface.IPosition;

public class AVLTree<T extends Comparable<T>> extends BinarySearchTree<T> {

    @Override
    public boolean insert(T element) {
        IPosition<T> position = find(root(), element); // Find the appropriate insertion point
        if (isExternal(position)) { // The inserted value was not previously stored in the tree
            expandExternal(position, element); // Assign a value to the found leaf node and expand it
            AVLPosition insertedPosition = (AVLPosition) find(root(), element); // Find the location of the newly expanded leaf again
            updateHeight(insertedPosition, false);
            AVLPosition checkedPosition = (AVLPosition) find(root(), element); // Find the location of the newly expanded leaf again
            while (checkedPosition != null) { // Loop until the detected point reaches root's parent null
                if (Math.abs(((AVLPosition) checkedPosition.left).height - ((AVLPosition) checkedPosition.right).height) <= 1) {// If the height difference between the left and right subtrees of the detected point is less than or equal to 1
                    checkedPosition = (AVLPosition) checkedPosition.parent; // Detects the parent node of the detected point
                } else {// If the balance is not balanced, then the restructure method is called to restore the balance:
                    restructure(obtainXPosition(checkedPosition));
                }
            }
            return true;
        } else { // The inserted value already exists in the tree
            return false;
        }
    }

    @Override
    public boolean remove(T element) {
        AVLPosition removedPosition = (AVLPosition) find(root, element);
        T checkedPositionElement = null;
        if (!isExternal(removedPosition)) {// Can be removed successfully
            // The if condition is used to find the parent of the removed node
            if (isInternal(removedPosition.left) && isInternal(removedPosition.right)) { // The left and right subtrees of the node to be removed are internal nodes. Find the maximum value of the minimum node to be removed
                removedPosition = (AVLPosition) removedPosition.right;
                while (isInternal(removedPosition.left)) {
                    removedPosition = (AVLPosition) removedPosition.left;
                }
                checkedPositionElement = removedPosition.parent.element; // removedPosition is the smallest larger node of the removed node, whose parent is the starting point from which we will later check the height (note, however, that the starting point may be the removed node).
                if (removedPosition.parent == find(root, element)) {
                    checkedPositionElement = removedPosition.element; // If removedPosition is the smallest larger node of the removed node, and it is a child of the node containing the element, then the elements of the removed node and removedPosition are swapped after BST.remove is called
                }
            } else {// external nodes exist in the left and right subtrees of the node to be removed
                if (isExternal(removedPosition.right) && isExternal(removedPosition.left)) { // The right subtree is a leaf and the left subtree is a leaf
                    if (removedPosition != root) {
                        checkedPositionElement = removedPosition.parent.element;
                    }
                } else if (isExternal(removedPosition.right) && isInternal(removedPosition.left)) { // The right subtree is a leaf
                    checkedPositionElement = removedPosition.left.element;
                } else if (isExternal(removedPosition.left) && isInternal(removedPosition.right)) { // The left subtree is a leaf
                    checkedPositionElement = removedPosition.right.element;
                }
            }
            super.remove(element);
            if (checkedPositionElement != null) { // To prevent the tree from having no nodes after the call to remove
                updateHeight((AVLPosition) find(root, checkedPositionElement), false); // The height is updated bottom-up from the parent of the removed point, but this causes the pointer to the checkedPosition to point to root, so you need to look again for the point that needs to be checked
                AVLPosition checkedPosition = (AVLPosition) find(root(), checkedPositionElement);
                while (checkedPosition != null) { // Loop until the detected point reaches root's parent null
                    if (Math.abs(((AVLPosition) checkedPosition.left).height - ((AVLPosition) checkedPosition.right).height) <= 1) {// If the height difference between the left and right subtrees of the detected point is less than or equal to 1
                        checkedPosition = (AVLPosition) checkedPosition.parent; // Detects the parent of the detected point
                    } else {// If not, then the restructure method is called to restore the balance:
                        restructure(obtainXPosition(checkedPosition));
                    }
                }
            }
            return true;
        } else {
            return false;// The node to be deleted does not exist in the tree. Therefore, deleting the node fails
        }
    }

    @Override
    public boolean contains(T element) {
        IPosition<T> position = this.find(this.root(), element);
        return this.isInternal(position);
    }

    public IPosition<T> obtainXPosition(IPosition<T> checkedPosition) { // According to the position of z node, obtain x node
        AVLPosition z = (AVLPosition) checkedPosition;
        AVLPosition y;
        AVLPosition x;
        // Determine the y node
        if (((AVLPosition) z.left).height > ((AVLPosition) z.right).height) { // The left subtree height of z node is greater than the right subtree height
            y = (AVLPosition) z.left;
        } else { // The right subtree height of z node is greater than the left subtree height
            y = (AVLPosition) z.right;
        }
        if (((AVLPosition) y.left).height > ((AVLPosition) y.right).height) { // The height of the left subtree of the y node is greater than that of the right subtree
            x = (AVLPosition) y.left;
        } else if (((AVLPosition) y.left).height < ((AVLPosition) y.right).height) { // The right subtree height of the y node is greater than the left subtree height
            x = (AVLPosition) y.right;
        } else { // If the subtrees of y are the same height
            if (y == z.left) {
                x = (AVLPosition) y.left;
            } else {
                x = (AVLPosition) y.right;
            }
        }
        return x;
    }

    private void restructure(IPosition<T> x) {
        AVLPosition z = (AVLPosition) ((AVLPosition) x).parent.parent;
        if (((AVLPosition) z.right).height > ((AVLPosition) z.left).height) { // When the right subtree height of the unbalanced node is greater than the left subtree height
            if (((AVLPosition) z.right.right).height >= ((AVLPosition) z.right.left).height) { //RR
                AVLPosition newRoot = leftRotate(z);
                updateHeight(newRoot, true);
            } else { //RL
                AVLPosition newRoot = rightRotate((AVLPosition) z.right);
                newRoot = leftRotate((AVLPosition) newRoot.parent);
                updateHeight(newRoot, true);
            }
        } else { // When the left subtree height of the unbalanced node is greater than the right subtree height
            if (((AVLPosition) z.left.left).height >= ((AVLPosition) z.left.right).height) { //LL
                AVLPosition newRoot = rightRotate(z);
                updateHeight(newRoot, true);
            } else { //LR
                AVLPosition newRoot = leftRotate((AVLPosition) z.left);
                newRoot = rightRotate((AVLPosition) newRoot.parent);
                updateHeight(newRoot, true);
            }
        }
    }

    private AVLPosition leftRotate(AVLPosition unbalancedPosition) { // This parameter is an unbalanced Z
        // Suppose Z is the unbalanced point, Y is the right child of the unbalanced point, and X is the right child of Y:
        AVLPosition tempNode = (AVLPosition) newPosition(unbalancedPosition.element, unbalancedPosition.parent); // Create a new node with a value equal to node Z with subTreeRoot as the parent node
        tempNode.parent = unbalancedPosition;
        tempNode.left = unbalancedPosition.left; // Let the left subtree of the new node be equal to the left subtree of subTreeRoot
        (unbalancedPosition.left).parent = tempNode;
        tempNode.right = unbalancedPosition.right.left; // Let the right subtree of the new node be equal to the left subtree of subTreeRoot's right subtree
        (unbalancedPosition.right.left).parent = tempNode;
        unbalancedPosition.element = unbalancedPosition.right.element; // Replace the value of subTreeRoot from node Z's value to node Y's value, achieving node Y's rotation
        (unbalancedPosition.right.right).parent = unbalancedPosition;
        unbalancedPosition.right = unbalancedPosition.right.right; // Replace subTreeRoot's right subtree with subTreeRoot's right subtree's right subtree
        unbalancedPosition.left = tempNode; // Set subTreeRoot's left subtree as the newly created node, which is the original node Z
        return unbalancedPosition; // Return the root node of the current subtree
    }

    private AVLPosition rightRotate(AVLPosition unbalancedPosition) { // This parameter is an unbalanced point Z
        // Suppose Z is the unbalanced point, Y is the right child of the unbalanced point, and X is Y's right child:
        AVLPosition tempNode = (AVLPosition) newPosition(unbalancedPosition.element, unbalancedPosition.parent); // Create a new node with a value equal to node Z with subTreeRoot as the parent node
        tempNode.parent = unbalancedPosition;
        tempNode.right = unbalancedPosition.right; // Let the right subtree of the new node be equal to the right subtree of subTreeRoot
        (unbalancedPosition.right).parent = tempNode;
        tempNode.left = unbalancedPosition.left.right;  // Let the left subtree of the new node be equal to the right subtree of subTreeRoot's left subtree
        (unbalancedPosition.left.right).parent = tempNode;
        unbalancedPosition.element = unbalancedPosition.left.element; // Replace the value of subTreeRoot from node Z's value to node Y's value, achieving node Y's rotation
        (unbalancedPosition.left.left).parent = unbalancedPosition;
        unbalancedPosition.left = unbalancedPosition.left.left; // Replace subTreeRoot's left subtree with subTreeRoot's left subtree's left subtree
        unbalancedPosition.right = tempNode; // Set subTreeRoot's left subtree as the newly created node, which is the original node Z
        return unbalancedPosition; // Return the root node of the current subtree
    }

    private void updateHeight(AVLPosition subtreeRoot, Boolean isExecuteAll) {
        //Used for updating the height of the nodes within a subtree and the height of the ancestor nodes of the subtree after a restructure has occurred
        if (isExecuteAll) {
            ((AVLPosition) subtreeRoot.left).height = Math.max(((AVLPosition) ((subtreeRoot).left).left).height, ((AVLPosition) (subtreeRoot.left).right).height) + 1;
            ((AVLPosition) subtreeRoot.right).height = Math.max(((AVLPosition) ((subtreeRoot).right).right).height, ((AVLPosition) (subtreeRoot.right).left).height) + 1;
            subtreeRoot.height = Math.max(((AVLPosition) subtreeRoot.left).height, ((AVLPosition) subtreeRoot.right).height) + 1;
        }
        while (subtreeRoot != null) { //After removing or inserting a node, only execute this part, and the above code for updating the height of the nodes within the subtree is not needed
            subtreeRoot.height = Math.max(((AVLPosition) subtreeRoot.left).height, ((AVLPosition) subtreeRoot.right).height) + 1;
            subtreeRoot = (AVLPosition) subtreeRoot.parent;
        }
    }

    @Override
    public void expandExternal(IPosition<T> p, T e) { //Used to expand those nodes: having a parent, value as null, left child as null, right child as null
        if (this.isInternal(p)) {
            throw new RuntimeException("Not an external node");
        } else {
            AVLPosition position = (AVLPosition) p; // Since the type in the parent class is BTPosition, choose to override the method in the parent class
            position.element = e;
            position.left = this.newPosition(null, position); //this.newPosition(null, position): value as null, parent node as position, left and right nodes as (BTPosition)null
            position.right = this.newPosition(null, position);//this.newPosition(null, position): value as null, parent node as position, left and right nodes as (BTPosition)null
            this.size += 2;
        }
    }

    @Override
    protected AVLPosition newPosition(T element, BTPosition parent) { //The original return type is BTPosition
        return new AVLPosition(element, parent);
    }

    /**
     * Define a subclass of BTPosition so that we can also store the height
     * of each position in its object.
     * <p>
     * This will be more efficient than calculating the height every time
     * we need it, but we will need to update heights whenever we change
     * the structure of the tree.
     */
    public class AVLPosition extends BTPosition {
        // store the height of this position, so that we can test for balance
        public int height = 0;

        /**
         * Constructor - create a new AVL node
         *
         * @param element The element to store in the node.
         * @param parent  The parent node of this node (or {@code null} if this is the root)
         */
        public AVLPosition(T element, BTPosition parent) {
            super(element, parent);
        }
    }
}
