//UCD Number: 21207500  Name:LiYan Tao
package dsa.Test;

import dsa.impl.AVLTree;
import dsa.impl.TreePrinter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test1 {
    public static void main(String[] args) {
        /*
        AVLTree Test
        AVLTree Test
        */

        AVLTree t1 = new AVLTree(); //test RR and RL

        AVLTree t2 = new AVLTree(); //test LL and LR

        AVLTree t3 = new AVLTree(); //test LR and RL

        AVLTree t4 = new AVLTree(); //test RR and LL and RL and LR

        AVLTree t5 = new AVLTree(); //test speed

        System.out.println("""

                ----------------
                                
                t1 test:""");
        t1.insert(50); //insert()
        t1.insert(45); //insert()
        t1.insert(40); //execute RR
        System.out.println("t1 conducts RR:");
        TreePrinter.printTree(t1);
        t1.insert(48); //insert()
        t1.remove(40); //execute RL
        System.out.println("t1 conducts RL:");
        TreePrinter.printTree(t1);
        t1.remove(48); //remove Root
        System.out.println("t1 conducts removing root:");
        TreePrinter.printTree(t1);
        t1.remove(50); //remove()
        t1.remove(45); //remove()
        System.out.println("t1 conducts removing all nodes:");
        TreePrinter.printTree(t1);
        System.out.println("t1 contains(45) test: " + t1.contains(45));


        System.out.println("""

                ----------------
                                
                t2 test:""");
        t2.insert(50); //insert()
        t2.insert(55); //insert()
        t2.insert(60); //execute LL
        System.out.println("t2 conducts LL:");
        TreePrinter.printTree(t2);
        t2.insert(53); //insert()
        t2.remove(60); //execute LR
        System.out.println("t2 conducts LR:");
        TreePrinter.printTree(t2);
        t2.remove(53); //remove Root
        System.out.println("t2 conducts removing root:");
        TreePrinter.printTree(t2);
        t2.remove(50); //remove()
        t2.remove(55); //remove()
        System.out.println("t2 conducts removing all nodes:");
        TreePrinter.printTree(t2);
        System.out.println("t2 contains(45) test: " + t2.contains(45));


        System.out.println("""

                ----------------
                                
                t3 test:""");
        t3.insert(50); //insert()
        t3.insert(45); //insert()
        t3.insert(48); //execute LR
        System.out.println("t3 conducts LR:");
        TreePrinter.printTree(t3);
        t3.remove(48); //remove()
        t3.insert(49); //execute RL
        System.out.println("t3 conducts RL:");
        TreePrinter.printTree(t3);
        System.out.println("t3 contains(45) test: " + t3.contains(45));


        System.out.println("""

                ----------------
                                
                t4 test:""");

        int[] elementsToInsert = {30, 20, 10, 40, 50, 35, 5, 3, 4, 55, 53, 15, 25, 60, 54, 12, 14};
        //Insert10:LL  Insert50:RR  Insert35:RL  Insert5:LL  Insert4:LR  Insert53:RL  Insert60:RR  Insert14:LR
        for (int element : elementsToInsert) {
            System.out.println("insert: " + element);
            t4.insert(element);
        }

        int[] elementsToRemove = {30, 40, 50, 54, 25, 35, 60, 53};
        //Remove30:remove root  Remove50:RR  Remove54:LR  Remove25:RR
        for (int element : elementsToRemove) {
            System.out.println("remove: " + element);
            t4.remove(element);
        }
        TreePrinter.printTree(t4);


        System.out.println("""

                ----------------
                                
                t5 test:""");

        int testCount = 100000;
        // Generate a list of values from 1 to testCount for random extraction
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= testCount; i++) {
            numbers.add(i);
        }

        // Shuffle the order of the value list for random extraction
        Collections.shuffle(numbers);

        // Test the insert method
        long insertStartTime = System.currentTimeMillis();
        for (int i = 0; i < testCount; i++) {
            t5.insert(numbers.get(i));
        }
        long insertEndTime = System.currentTimeMillis();

        // Shuffle the order of the value list for random extraction
        Collections.shuffle(numbers);

        // Test the contains method
        long containsStartTime = System.currentTimeMillis();
        for (int i = 0; i < testCount; i++) {
            t5.contains(numbers.get(i));
        }
        long containsEndTime = System.currentTimeMillis();

        // Test the remove method
        long removeStartTime = System.currentTimeMillis();
        for (int i = 0; i < testCount; i++) {
            t5.remove(numbers.get(i));
        }
        long removeEndTime = System.currentTimeMillis();

        System.out.println("Execute the insert method " + testCount + " times, taking " + (insertEndTime - insertStartTime) + " milliseconds");
        System.out.println("Execute the contains method " + testCount + " times, taking " + (containsEndTime - containsStartTime) + " milliseconds");
        System.out.println("Execute the remove method " + testCount + " times, taking " + (removeEndTime - removeStartTime) + " milliseconds");

    }
}
