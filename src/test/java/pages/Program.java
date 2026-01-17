package pages;

import java.util.Arrays;
import java.util.HashSet;

public class Program {
    public static void main(String [] args){
        HashSet<Integer> set1 =  new HashSet<>(Arrays.asList(1,2,3,4));
        HashSet<Integer> set2 = new HashSet<>(Arrays.asList(3,4,5,6));

        /*
        HashSet<Integer> commonInSet = new HashSet<>();
        for(int i : set1){
            if(set2.add(i)){
                commonInSet.add(i);
            }
        }

        System.out.println(commonInSet.toString());
        */

        set1.removeAll(set2);
        System.out.println(set1);
    }
}
