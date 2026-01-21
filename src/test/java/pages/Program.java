package pages;

import java.util.ArrayList;

public class Program {
    public static void main(String[] args) {
        String test = "tesT@38219$";

        ArrayList<Character> symbols = new ArrayList<>();
        ArrayList<Character> chars = new ArrayList<>();
        ArrayList<Character> integers = new ArrayList<>();

        for (char c : test.toCharArray()) {
            if (Character.isDigit(c)) {
                integers.add(c);
            } else if (Character.isAlphabetic(c)) {
                chars.add(c);
            } else {
                symbols.add(c);
            }
        }

        System.out.println("Symbols from string are: " + symbols);
        System.out.println("Characters from string are: " + chars);
        System.out.println("Integers from string are: " + integers);
    }
}
