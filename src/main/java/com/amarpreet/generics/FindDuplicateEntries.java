package com.amarpreet.generics;

import java.util.*;
import java.util.stream.Collectors;

public class FindDuplicateEntries<T extends List> {
    T list;

    public FindDuplicateEntries(T list) {
        this.list = list;
    }

    void printDuplicateValues(){
        Set uniqueValues = (Set) list.stream()
                .filter(e -> Collections.frequency(list,e) > 1)
                .collect(Collectors.toSet());
               // .forEach(System.out::println);
        uniqueValues.forEach(System.out::println);

    }

    public static void main(String[] args) {
        List<String> listOfStrings = List.of("One","Two","One","three", "one", "five", "Three");
        List<Integer> listOfNumbers = List.of(5,2,5,1,6,7,90,1);
        FindDuplicateEntries fde = new FindDuplicateEntries(listOfStrings);
        fde.printDuplicateValues();
        FindDuplicateEntries fde2 = new FindDuplicateEntries(listOfNumbers);
        fde2.printDuplicateValues();
    }

}
