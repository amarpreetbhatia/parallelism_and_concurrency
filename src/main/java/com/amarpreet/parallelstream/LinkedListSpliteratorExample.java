package com.amarpreet.parallelstream;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LinkedListSpliteratorExample {
    public List<Integer> multiply_each_value(LinkedList<Integer> inputLists,
                                             Integer multipleValue,
                                             boolean isParallel){
        long startTime = System.currentTimeMillis();
        Stream<Integer> integerStream = inputLists.stream(); // sequential

        if(isParallel)
            integerStream.parallel();

        List<Integer> resultList = integerStream
                .map(integer -> integer * multipleValue)
                .collect(Collectors.toList());
        long endTime = System.currentTimeMillis();
        System.out.println("Time Taken by multiply_each_value :::\t" + (endTime - startTime) );
        return resultList;
    }
}
