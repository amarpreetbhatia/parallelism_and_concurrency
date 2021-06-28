package com.amarpreet.util;

import com.amarpreet.forkjoin.DataSet;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CollectVsReduce {

    public static String collectListOfString(){
       List<String> namesList =  DataSet.namesList();
        String result = namesList.parallelStream()
               .collect(Collectors.joining());
        return result;
    }

    public static String reduceListOfString(){
        List<String> namesList =  DataSet.namesList();
        String result = namesList
                .parallelStream()
                .reduce("", (s1, s2) -> s1 + s2);
        return result;
    }

    public static void main(String[] args) {
        System.out.println("Collect List = " + collectListOfString());
        System.out.println("Reduce List = " + reduceListOfString());
    }
}
