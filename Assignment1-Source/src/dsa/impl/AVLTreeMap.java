//UCD Number: 21207500  Name:LiYan Tao
package dsa.impl;

import dsa.iface.*;

/**
 * Implementation of a TreeMap, using an AVL Tree implementation strategy.
 * <p>
 * The get(), put() and remove() methods should behave as they do in a normal Map ADT.
 * <p>
 * All iterator methods should return items in ascending order of their keys.
 */
public class AVLTreeMap<K extends Comparable<K>, V> extends ProperLinkedBinaryTree<IEntry<K, V>> implements IMap<K, V> {

    @Override
    public V get(K k) { // Get the value associated with the given key, or get null if the key is not included in the mapping (hint: this is similar to the AVL Tree contains(...) method).
        AVLMapPosition foundPosition = (AVLMapPosition) find(this.root, k);
        if (isExternal(foundPosition)) {
            return null;
        } else {
            return foundPosition.element().value();
        }
    }

    @Override
    public V put(K k, V v) {// Add a new key/value pair to the mapping. If the key is already included in the mapping, return the old value associated with it and store the new value in the mapping. Otherwise, return null. (Hint: this is similar to the AVL tree insert(.) method).
        V oldValue = this.get(k);
        if (oldValue == null) { // The key has not yet existed in the Map, a new Entry needs to be added
            this.avlInsert(new Entry(k, v));
            return null;
        } else { // The key already has a corresponding value, return the value v associated with k
            // Here, use find to find the node with key K, and then change its Position.element.oldValue = v
            AVLMapPosition foundPosition = (AVLMapPosition) find(this.root, k);
            ((Entry) foundPosition.element).value = v;
            return oldValue;
        }
    }

    @Override
    public V remove(K k) {// Remove the entry with the given key from the mapping. If the key is included in the mapping, return the value associated with the key, otherwise return null (Hint: this is similar to the AVL Tree remove(...) method).
        V oldValue = this.get(k);
        if (oldValue == null) { // The key has not yet existed in the Map, return null
            return null;
        } else { // The key already has a corresponding value, return the value v associated with k
            this.avlRemove(new Entry(k, oldValue));
            return oldValue;
        }
    }

    @Override
    public IIterator<K> keys() {// Return an iterator that iterates through all keys contained in the mapping.
        IList<K> keyList = new SLinkedList<K>();
        IIterator<IEntry<K, V>> entryListIterator = this.entries();
        while (entryListIterator.hasNext()) {
            keyList.insertLast(entryListIterator.next().key());
        }
        return keyList.iterator();
    }

    @Override
    public IIterator<V> values() {// Return an iterator that iterates through all values contained in the mapping.
        IList<V> valueList = new SLinkedList<V>();
        IIterator<IEntry<K, V>> entryListIterator = this.entries();
        while (entryListIterator.hasNext()) {
            valueList.insertLast(entryListIterator.next().value());
        }
        return valueList.iterator();
    }

    @Override
    public IIterator<IEntry<K, V>> entries() { // Return an iterator that iterates through all entries contained in the mapping.
        IList<IEntry<K, V>> entryList = new SLinkedList<IEntry<K, V>>();
        inorderTraversal((AVLMapPosition) root, entryList);
        return entryList.iterator();
    }

    @Override
    public int size() {// Return the number of entries contained in the mapping.
        int allNodesNumber = size;
        // The size is the sum of all external nodes and internal nodes, and in a proper binary tree, the number of external nodes is one more than the number of internal nodes
        // Therefore, only a simple calculation formula is needed to derive the number of internal nodes
        int internalNodeNumber = (allNodesNumber - 1) / 2;
        return internalNodeNumber;
    }

    @Override
    public boolean isEmpty() { //Return true if the mapping is empty, otherwise return false.
        return (this.size() == 0);
    }


    private void inorderTraversal(AVLMapPosition p, IList<IEntry<K, V>> list) { // p should be the root node in general
        if (isExternal(p)) {
            return;
        }
        if (isInternal(p.left)) {
            inorderTraversal((AVLMapPosition) p.left, list);
        }
        list.insertLast(p.element);
        if (isInternal(p.right)) {
            inorderTraversal((AVLMapPosition) p.right, list);
        }
    }

    private boolean avlInsert(IEntry<K, V> element) { // This method is ported from the insert method in AVLTree
        IPosition<IEntry<K, V>> position = find(root(), element.key()); // Find the appropriate insertion point
        if (isExternal(position)) { // The value to be inserted has not been previously stored in the tree
            expandExternal(position, element); // Assign a value to the found leaf node and expand it
            AVLMapPosition insertedPosition = (AVLMapPosition) find(root(), element.key()); // Find the position of the just-expanded leaf node again
            updateHeight(insertedPosition, false);
            AVLMapPosition checkedPosition = (AVLMapPosition) find(root(), element.key()); // Find the position of the just-expanded leaf node again
            while (checkedPosition != null) { // Loop until the checked point reaches the parent node of root, null
                if (Math.abs(((AVLMapPosition) checkedPosition.left).height - ((AVLMapPosition) checkedPosition.right).height) <= 1) { // If the height difference between the left and right subtrees of the checked point is less than or equal to 1
                    checkedPosition = (AVLMapPosition) checkedPosition.parent; // Check the parent node of the checked point
                } else { // If unbalanced, call the restructure method to restore balance:
                    restructure(obtainXPosition(checkedPosition));
                }
            }
            return true;
        } else { // The value to be inserted already exists in the tree
            return false;
        }
    }

    public boolean avlRemove(IEntry<K, V> element) {
        AVLMapPosition removedPosition = (AVLMapPosition) find(root, element.key());
        IEntry<K, V> checkedPositionElement = null;
        if (!isExternal(removedPosition)) {// Can be successfully removed
            // The if condition is to find the parent node of the removed node
            if (isInternal(removedPosition.left) && isInternal(removedPosition.right)) { // Both the left and right subtrees of the node to be removed are internal nodes, go find the smallest larger value of the removed node
                removedPosition = (AVLMapPosition) removedPosition.right;
                while (isInternal(removedPosition.left)) {
                    removedPosition = (AVLMapPosition) removedPosition.left;
                }
                checkedPositionElement = removedPosition.parent.element; // At this point, removedPosition is the smallest larger node of the removed node, and its parent node is the starting point for checking height later (but note that this starting point may be the removed node)
                if (removedPosition.parent == find(root, element.key())) {
                    checkedPositionElement = removedPosition.element; // If at this time removedPosition is the smallest larger node of the removed node, and it is a child node of the node containing the element, then the two elements will be exchanged after calling BST.remove
                }
            } else {// There is an external node in the left and right subtrees of the node to be removed
                if (isExternal(removedPosition.right) && isExternal(removedPosition.left)) { // Both the right subtree and the left subtree are leaves
                    if (removedPosition != root) {
                        checkedPositionElement = removedPosition.parent.element;
                    }
                } else if (isExternal(removedPosition.right) && isInternal(removedPosition.left)) { // Right subtree is a leaf
                    checkedPositionElement = removedPosition.left.element;
                } else if (isExternal(removedPosition.left) && isInternal(removedPosition.right)) { // Left subtree is a leaf
                    checkedPositionElement = removedPosition.right.element;
                }
            }
            bstRemove(element);
            if (checkedPositionElement != null) { // Prevent the case where there are no nodes left in the tree after calling remove
                updateHeight((AVLMapPosition) find(root, checkedPositionElement.key()), false); // Update the height from the parent node of the removed point, from bottom to top, but this will cause the checkedPosition pointer to point to root, so we need to find the point to be checked again
                AVLMapPosition checkedPosition = (AVLMapPosition) find(root(), checkedPositionElement.key());
                while (checkedPosition != null) { // Loop until the checked point reaches the parent node of root, null
                    if (Math.abs(((AVLMapPosition) checkedPosition.left).height - ((AVLMapPosition) checkedPosition.right).height) <= 1) { // If the height difference between the left and right subtrees of the checked point is less than or equal to 1
                        checkedPosition = (AVLMapPosition) checkedPosition.parent; // Check the parent node of the checked point
                    } else { // If unbalanced, call the restructure method to restore balance:
                        restructure(obtainXPosition(checkedPosition));
                    }
                }
            }
            return true;
        } else {
            return false; // The node to be deleted does not exist in the tree, removal fails
        }
    }

    public boolean bstRemove(IEntry<K, V> value) { // Since the remove method in AVLTree calls the remove method in BST, the remove method in BinarySearchTree is added and modified here
        IPosition<IEntry<K, V>> position = this.find(this.root(), value.key());
        if (!this.isInternal(position)) {
            return false;
        } else {
            if (this.isInternal(this.left(position)) && this.isInternal(this.right(position))) {
                IPosition current;
                for (current = this.right(position); this.isInternal(this.left(current)); current = this.left(current)) {
                }
                this.replace(position, ((AVLMapPosition) current).element());
                super.remove(current); // Call the remove method in ProperLinkedBinaryTree
            } else {
                super.remove(position); // Call the remove method in ProperLinkedBinaryTree
            }
            return true;
        }
    }

    public IPosition<IEntry<K, V>> obtainXPosition(IPosition<IEntry<K, V>> checkedPosition) { // According to the position of z node, obtain x node
        AVLMapPosition z = (AVLMapPosition) checkedPosition;
        AVLMapPosition y;
        AVLMapPosition x;
        // Determine y node
        if (((AVLMapPosition) z.left).height > ((AVLMapPosition) z.right).height) { // The height of the left subtree of z node is greater than the height of the right subtree
            y = (AVLMapPosition) z.left;
        } else { // The height of the right subtree of the z node is greater than the height of the left subtree
            y = (AVLMapPosition) z.right;
        }
        if (((AVLMapPosition) y.left).height > ((AVLMapPosition) y.right).height) { // The height of the left subtree of the y node is greater than the height of the right subtree
            x = (AVLMapPosition) y.left;
        } else if (((AVLMapPosition) y.left).height < ((AVLMapPosition) y.right).height) { // The height of the right subtree of the y node is greater than the height of the left subtree
            x = (AVLMapPosition) y.right;
        } else { // If the heights of the left and right subtrees of y are equal
            if (y == z.left) {
                x = (AVLMapPosition) y.left;
            } else {
                x = (AVLMapPosition) y.right;
            }
        }
        return x;
    }

    private void restructure(IPosition<IEntry<K, V>> x) {
        AVLMapPosition z = (AVLMapPosition) ((AVLMapPosition) x).parent.parent;
        if (((AVLMapPosition) z.right).height > ((AVLMapPosition) z.left).height) { // When the height of the right subtree of the unbalanced node is greater than the left subtree height
            if (((AVLMapPosition) z.right.right).height >= ((AVLMapPosition) z.right.left).height) { // RR
                AVLMapPosition newRoot = leftRotate(z);
                updateHeight(newRoot, true);
            } else { // RL
                AVLMapPosition newRoot = rightRotate((AVLMapPosition) z.right);
                newRoot = leftRotate((AVLMapPosition) newRoot.parent);
                updateHeight(newRoot, true);
            }
        } else { // When the height of the left subtree of the unbalanced node is greater than the right subtree height
            if (((AVLMapPosition) z.left.left).height >= ((AVLMapPosition) z.left.right).height) { // LL
                AVLMapPosition newRoot = rightRotate(z);
                updateHeight(newRoot, true);
            } else { // LR
                AVLMapPosition newRoot = leftRotate((AVLMapPosition) z.left);
                newRoot = rightRotate((AVLMapPosition) newRoot.parent);
                updateHeight(newRoot, true);
            }
        }
    }

    private AVLMapPosition leftRotate(AVLMapPosition unbalancedPosition) { // The parameter is the unbalanced node Z
        // Assume Z is the unbalanced node, Y is the right child of the unbalanced node, and X is the right child of Y:
        AVLMapPosition tempNode = (AVLMapPosition) newPosition(unbalancedPosition.element, unbalancedPosition.parent); // Create a new node with the same value as node Z and with subTreeRoot as the parent
        tempNode.parent = unbalancedPosition;
        tempNode.left = unbalancedPosition.left; // Set the new node's left subtree to be equal to subTreeRoot's left subtree
        (unbalancedPosition.left).parent = tempNode;
        tempNode.right = unbalancedPosition.right.left; // Set the new node's right subtree to be equal to subTreeRoot's right subtree's left subtree
        (unbalancedPosition.right.left).parent = tempNode;
        unbalancedPosition.element = unbalancedPosition.right.element; // Replace the subTreeRoot's value from node Z's value to node Y's value, achieving node Y's rotation
        (unbalancedPosition.right.right).parent = unbalancedPosition;
        unbalancedPosition.right = unbalancedPosition.right.right;// Replace subTreeRoot's right subtree with subTreeRoot's right subtree's right subtree
        unbalancedPosition.left = tempNode;// Set subTreeRoot's left subtree to the newly created node, which is the original node Z
        return unbalancedPosition;// Return the current subtree's root node
    }

    private AVLMapPosition rightRotate(AVLMapPosition unbalancedPosition) { // The parameter is the unbalanced node Z
        // Assume Z is the unbalanced node, Y is the right child of the unbalanced node, and X is the right child of Y:
        AVLMapPosition tempNode = (AVLMapPosition) newPosition(unbalancedPosition.element, unbalancedPosition.parent); // Create a new node with the same value as node Z and with subTreeRoot as the parent
        tempNode.parent = unbalancedPosition;
        tempNode.right = unbalancedPosition.right; // Set the new node's right subtree to be equal to subTreeRoot's right subtree
        (unbalancedPosition.right).parent = tempNode;
        tempNode.left = unbalancedPosition.left.right; // Set the new node's left subtree to be equal to subTreeRoot's left subtree's right subtree
        (unbalancedPosition.left.right).parent = tempNode;
        unbalancedPosition.element = unbalancedPosition.left.element; // Replace the subTreeRoot's value from node Z's value to node Y's value, achieving node Y's rotation
        (unbalancedPosition.left.left).parent = unbalancedPosition;
        unbalancedPosition.left = unbalancedPosition.left.left;// Replace subTreeRoot's left subtree with subTreeRoot's left subtree's left subtree
        unbalancedPosition.right = tempNode;// Set subTreeRoot's left subtree to the newly created node, which is the original node Z
        return unbalancedPosition;// Return the current subtree's root node
    }

    private void updateHeight(AVLMapPosition subtreeRoot, Boolean isExecuteAll) {
        // Used to update the height of the nodes within a subtree after a restructure, as well as the height of the ancestor nodes of the subtree
        if (isExecuteAll) {
            ((AVLMapPosition) subtreeRoot.left).height = Math.max(((AVLMapPosition) ((subtreeRoot).left).left).height, ((AVLMapPosition) (subtreeRoot.left).right).height) + 1;
            ((AVLMapPosition) subtreeRoot.right).height = Math.max(((AVLMapPosition) ((subtreeRoot).right).right).height, ((AVLMapPosition) (subtreeRoot.right).left).height) + 1;
            subtreeRoot.height = Math.max(((AVLMapPosition) subtreeRoot.left).height, ((AVLMapPosition) subtreeRoot.right).height) + 1;
        }
        while (subtreeRoot != null) { // After removing or inserting a node, only execute this part, the above part of the code for updating the height of nodes within a subtree is not needed
            subtreeRoot.height = Math.max(((AVLMapPosition) subtreeRoot.left).height, ((AVLMapPosition) subtreeRoot.right).height) + 1;
            subtreeRoot = (AVLMapPosition) subtreeRoot.parent;
        }
    }

    private IPosition<IEntry<K, V>> find(IPosition<IEntry<K, V>> start, K key) {
        if (this.isExternal(start)) {
            return start;
        } else {
            int result = key.compareTo(start.element().key());
            if (result < 0) {
                return this.find(this.left(start), key);
            } else {
                return result > 0 ? this.find(this.right(start), key) : start;
            }
        }
    }

    public void expandExternal(IPosition<IEntry<K, V>> p, IEntry<K, V> entry) { // Used to expand leaf nodes that have: a parent, null Entry, null left child, and null right child
        if (this.isInternal(p)) {
            throw new RuntimeException("Not an external node");
        } else {
            AVLMapPosition position = (AVLMapPosition) p; // Since the type in the parent class is BTPosition, choose to override the parent class method
            position.element = entry;
            position.left = this.newPosition(null, position); // this.newPosition(null, position): Entry is null, parent node is position, left and right nodes are (BTPosition) null
            position.right = this.newPosition(null, position);// this.newPosition(null, position): Entry is null, parent node is position, left and right nodes are (BTPosition) null
            this.size += 2;
        }
    }

    @Override
    protected AVLMapPosition newPosition(IEntry<K, V> entry, BTPosition parent) {
        return new AVLMapPosition(entry, parent);
    }

    public class Entry implements IEntry<K, V> {
        public K key; // key
        public V value; // value

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        @Override
        public K key() {
            return key;
        }

        @Override
        public V value() {
            return value;
        }
    }

    public class AVLMapPosition extends BTPosition {
        // store the height of this position, so that we can test for balance
        public int height = 0;

        // Note that the element instance variable in Position will now become Entry, and if you still want to compare element sizes in methods like find(), you need to use element.key()
        public AVLMapPosition(IEntry<K, V> entry, BTPosition parent) {
            super(entry, parent);
        }
    }
}
