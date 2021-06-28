package com.amarpreet.parallelstream;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ParallelStreamResultOrder {

    //Maintained Order
    public static List<Integer> listOrder(List<Integer> inputList){
        long startTime = System.currentTimeMillis();
        List<Integer> resultList = inputList.parallelStream()
                .map(integer -> integer * 2)
                .collect(Collectors.toList());
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken = " + (endTime - startTime));
        return resultList;
    }

    //Maintained UnOrder List
    public static Set<Integer> listUnOrder(Set<Integer> inputList){
        long startTime = System.currentTimeMillis();
        Set<Integer> integerSet = inputList.parallelStream()
                .map(integer -> integer * 2)
                .collect(Collectors.toSet());
        long endTime = System.currentTimeMillis();
        System.out.println("Time taken = " + (endTime - startTime));
        return integerSet;
    }

    public static void main(String[] args) {
        List<Integer> inputList = List.of(1,2,3,4,5,6,7,8);
        System.out.println("inputList ::" + inputList);
        List<Integer> resultList = listOrder(inputList);
        System.out.println("result ::" + resultList);

        Set<Integer> inputSet = Set.of(1,2,3,4,5,6,7,8);
        System.out.println("inputSet ::" + inputSet);
        Set<Integer> resultSet= listUnOrder(inputSet);
        System.out.println("result ::" + resultSet);
    }
}
