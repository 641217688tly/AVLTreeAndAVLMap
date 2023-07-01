//UCD Number: 21207500  Name:LiYan Tao
package dsa.Test;

import dsa.iface.IEntry;
import dsa.iface.IIterator;
import dsa.impl.AVLTreeMap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Test2 {
    public static void main(String[] args) {
        /*
        AVLMapTree Test
        AVLMapTree Test
        */
        AVLTreeMap<String, Integer> map1 = new AVLTreeMap<>(); // map1's key is String, value is Integer

        AVLTreeMap<Integer, String> map2 = new AVLTreeMap<>(); // map2's key is Integer, value is String, used for testing empty Map scenario

        AVLTreeMap<Integer, String> map3 = new AVLTreeMap<>(); // map3's key is Integer, value is String, testing Map implementation based on AVL tree that has RR, LL, LR, or RL cases

        AVLTreeMap<Integer, String> map4 = new AVLTreeMap<>(); // map3's key is Integer, value is String, testing speed

        System.out.println("""

                ----------------
                                
                map1 test:""");
        //map1 put()
        System.out.println("map1 put() test:");
        System.out.println("map1.put(\"a\", 1): " + map1.put("a", 1));
        System.out.println("map1.put(\"d\", 4): " + map1.put("d", 4));
        System.out.println("map1.put(\"c\", 3): " + map1.put("c", 3));
        System.out.println("map1.put(\"b\", 2): " + map1.put("b", 2));
        System.out.println("map1.put(\"e\", 5): " + map1.put("e", 5));
        System.out.println("map1.put(\"g\", 7): " + map1.put("g", 7));
        System.out.println("map1.put(\"f\", 6): " + map1.put("f", 6));
        System.out.println("map1.put(\"i\", 9): " + map1.put("i", 9));
        System.out.println("map1.put(\"h\", 8): " + map1.put("h", 8));
        System.out.println("map1.put(\"j\", 10): " + map1.put("j", 10));
        System.out.println("map1.put(\"j\", 11): " + map1.put("j", 11)); // Overwrite the value of an existing key-value pair

        //map1 remove()
        System.out.println("\n" + "map1 remove() test:");
        System.out.println("map1.remove(\"a\"): " + map1.remove("a"));
        System.out.println("map1.remove(\"b\"): " + map1.remove("b"));
        System.out.println("map1.remove(\"c\"): " + map1.remove("c"));
        System.out.println("map1.remove(\"d\"): " + map1.remove("d"));
        System.out.println("map1.remove(\"e\"): " + map1.remove("e"));
        System.out.println("map1.remove(\"x\"): " + map1.remove("x")); // Remove a non-existent key-value pair

        //map1 get()
        System.out.println("\n" + "map1 get() test:");
        System.out.println("map1.get(\"a\"): " + map1.get("a")); // Get a non-existent key-value pair from Map
        System.out.println("map1.get(\"b\"): " + map1.get("b")); // Get a non-existent key-value pair from Map
        System.out.println("map1.get(\"c\"): " + map1.get("c")); // Get a non-existent key-value pair from Map
        System.out.println("map1.get(\"d\"): " + map1.get("d")); // Get a non-existent key-value pair from Map
        System.out.println("map1.get(\"e\"): " + map1.get("e")); // Get a non-existent key-value pair from Map
        System.out.println("map1.get(\"f\"): " + map1.get("f")); // Get an existing key-value pair from Map
        System.out.println("map1.get(\"g\"): " + map1.get("g")); // Get an existing key-value pair from Map
        System.out.println("map1.get(\"h\"): " + map1.get("h")); // Get an existing key-value pair from Map
        System.out.println("map1.get(\"i\"): " + map1.get("i")); // Get an existing key-value pair from Map
        System.out.println("map1.get(\"j\"): " + map1.get("j")); // Get an existing key-value pair from Map

        //map1 keys()
        System.out.println("\n" + "map1 keys() test:");
        IIterator<String> keys1 = map1.keys(); // Get key Iterator
        while (keys1.hasNext()) { // Use key Iterator
            System.out.println(keys1.next());
        }

        //map1 values()
        System.out.println("\n" + "map1 values() test:");
        IIterator<Integer> values1 = map1.values();
        while (values1.hasNext()) { // Get value Iterator
            System.out.println(values1.next()); // Use value Iterator
        }

        //map1 entries()
        System.out.println("\n" + "map1 entries() test:");
        IIterator<IEntry<String, Integer>> entries1 = map1.entries(); // Get entry Iterator
        while (entries1.hasNext()) {  // Use Entry Iterator
            IEntry<String, Integer> entrie1 = entries1.next();
            System.out.println("Key:" + entrie1.key() + "; " + "Value:" + entrie1.value());
        }

        //map1 size()
        System.out.println("\n" + "map1 size() test:");
        System.out.println("The size of this map1 is: " + map1.size());

        //map1 isEmpty()
        System.out.println("\n" + "map1 isEmpty() test:");
        System.out.println("map1 is empty: " + map1.isEmpty());


        System.out.println("""

                ----------------
                                
                map2 test:""");
        //map2 put()
        System.out.println("\n" + "map2 put() test:");
        System.out.println("map2.put(1, \"a\"): " + map2.put(1, "a"));
        System.out.println("map2.put(1, \"b\"): " + map2.put(2, "b"));
        System.out.println("map2.put(1, \"c\"): " + map2.put(3, "c"));
        System.out.println("map2.put(1, \"d\"): " + map2.put(4, "d"));
        System.out.println("map2.put(1, \"e\"): " + map2.put(5, "e"));
        System.out.println("map2.put(1, \"f\"): " + map2.put(6, "f"));
        System.out.println("map2.put(1, \"g\"): " + map2.put(7, "g"));
        System.out.println("map2.put(1, \"h\"): " + map2.put(8, "h"));
        System.out.println("map2.put(1, \"i\"): " + map2.put(9, "i"));
        System.out.println("map2.put(1, \"j\"): " + map2.put(10, "j"));
        System.out.println("map2.put(1, \"j\"): " + map2.put(10, "j")); // Overwrite the value of the existing key-value pair

        //map2 remove()
        System.out.println("\n" + "map2 remove() test:");
        System.out.println("map2.remove(1): " + map2.remove(1));
        System.out.println("map2.remove(1): " + map2.remove(2));
        System.out.println("map2.remove(1): " + map2.remove(3));
        System.out.println("map2.remove(1): " + map2.remove(4));
        System.out.println("map2.remove(1): " + map2.remove(5));
        System.out.println("map2.remove(1): " + map2.remove(6));
        System.out.println("map2.remove(1): " + map2.remove(7));
        System.out.println("map2.remove(1): " + map2.remove(8));
        System.out.println("map2.remove(1): " + map2.remove(9));
        System.out.println("map2.remove(1): " + map2.remove(10));
        // At this point, all key-value pairs in the map have been removed
        System.out.println(map2.remove(20)); // Remove a non-existent key-value pair

        //map2 get()
        System.out.println("\n" + "map2 get() test:");
        // Get non-existent key-value pairs in the map
        System.out.println(map2.get(1));
        System.out.println(map2.get(2));
        System.out.println(map2.get(3));
        System.out.println(map2.get(4));
        System.out.println(map2.get(5));
        System.out.println(map2.get(6));
        System.out.println(map2.get(7));
        System.out.println(map2.get(8));
        System.out.println(map2.get(9));
        System.out.println(map2.get(10));

        //map2 keys()
        System.out.println("\n" + "map2 keys() test:");
        IIterator<Integer> keys2 = map2.keys(); // Get the key Iterator of an empty map
        while (keys2.hasNext()) {
            System.out.println(keys2.next());
        }

        //map2 values()
        System.out.println("\n" + "map2 values() test:");
        IIterator<String> values2 = map2.values(); // Get the value Iterator of an empty map
        while (values2.hasNext()) {
            System.out.println(values2.next());
        }

        //map2 entries()
        System.out.println("\n" + "map2 entries() test:");
        IIterator<IEntry<Integer, String>> entries2 = map2.entries(); // Get the entry Iterator of an empty map
        while (entries2.hasNext()) {
            IEntry<Integer, String> entry2 = entries2.next();
            System.out.println("Key:" + entry2.key() + "; " + "Value:" + entry2.value());
        }

        //map2 size()
        System.out.println("\n" + "map2 size() test:");
        System.out.println("The size of this map2 is: " + map2.size());

        //map2 isEmpty()
        System.out.println("\n" + "map2 isEmpty() test:");
        System.out.println("map2 is empty: " + map2.isEmpty());


        System.out.println("""

                ----------------
                                
                map3 test:""");
        int[] elementsToInsert = {30, 20, 10, 40, 50, 35, 5, 3, 4, 55, 53, 15, 25, 60, 54, 12, 14};
        //Insert10:LL  Insert50:RR  Insert35:RL  Insert5:LL  Insert4:LR  Insert53:RL  Insert60:RR  Insert14:LR
        for (int element : elementsToInsert) {
            System.out.println("put: " + "Entry<" + element + "," + Integer.toString(element) + ">");
            map3.put(element, Integer.toString(element));
        }

        int[] elementsToRemove = {30, 40, 50, 54, 25, 35, 60, 53};
        //Remove30:remove root  Remove50:RR  Remove54:LR  Remove25:RR
        for (int element : elementsToRemove) {
            System.out.println("remove: " + element);
            map3.remove(element);
        }

        //map3 keys()
        System.out.println("\n" + "map3 keys() test:");
        IIterator<Integer> keys3 = map3.keys(); // Get the key Iterator of an empty map
        while (keys3.hasNext()) {
            System.out.println(keys3.next());
        }

        //map3 values()
        System.out.println("\n" + "map3 values() test:");
        IIterator<String> values3 = map3.values(); // Get the value Iterator of an empty map
        while (values3.hasNext()) {
            System.out.println(values3.next());
        }

        //map3 entries()
        System.out.println("\n" + "map3 entries() test:");
        IIterator<IEntry<Integer, String>> entries3 = map3.entries(); // Get the entry Iterator of an empty map
        while (entries3.hasNext()) {
            IEntry<Integer, String> entry3 = entries3.next();
            System.out.println("Key:" + entry3.key() + "; " + "Value:" + entry3.value());
        }

        //map3 size()
        System.out.println("\n" + "map3 size() test:");
        System.out.println("The size of this map3 is: " + map3.size());

        //map3 isEmpty()
        System.out.println("\n" + "map3 isEmpty() test:");
        System.out.println("map3 is empty: " + map3.isEmpty());


        System.out.println("""

                ----------------
                                
                map4 test:""");

        int testCount = 100000;
        // Generate a list of values from 1 to testCount for random extraction
        List<Integer> numbers = new ArrayList<>();
        for (int i = 1; i <= testCount; i++) {
            numbers.add(i);
        }

        // Shuffle the order of the value list for random extraction
        Collections.shuffle(numbers);

        // Test the put method
        long insertStartTime = System.currentTimeMillis();
        for (int i = 0; i < testCount; i++) {
            map4.put(numbers.get(i), Integer.toString(numbers.get(i)));
        }
        long insertEndTime = System.currentTimeMillis();

        // Shuffle the order of the value list for random extraction
        Collections.shuffle(numbers);

        // Test the get method
        long containsStartTime = System.currentTimeMillis();
        for (int i = 0; i < testCount; i++) {
            map4.get(numbers.get(i));
        }
        long containsEndTime = System.currentTimeMillis();

        // Test the remove method
        long removeStartTime = System.currentTimeMillis();
        for (int i = 0; i < testCount; i++) {
            map4.remove(numbers.get(i));
        }
        long removeEndTime = System.currentTimeMillis();

        System.out.println("Execute the insert method " + testCount + " times, taking " + (insertEndTime - insertStartTime) + " milliseconds");
        System.out.println("Execute the contains method " + testCount + " times, taking " + (containsEndTime - containsStartTime) + " milliseconds");
        System.out.println("Execute the remove method " + testCount + " times, taking " + (removeEndTime - removeStartTime) + " milliseconds");

    }
}
