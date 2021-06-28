package com.amarpreet.parallelstream;

import com.amarpreet.forkjoin.DataSet;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListSpliteratorExampleTest {

    LinkedListSpliteratorExample linkedListSpliteratorExample = new LinkedListSpliteratorExample();

    @RepeatedTest(5)
    void multiply_each_value() {
        int size = 1000000;
        LinkedList<Integer> inputList = DataSet.generateIntegerLinkedList(size);
        List<Integer> resultList = linkedListSpliteratorExample.multiply_each_value(inputList, 2, false);
        assertEquals(resultList.size(),1000000);
    }

    @RepeatedTest(5)
    void multiply_each_value_when_is_Parallel_TRUE() {
        int size = 1000000;
        LinkedList<Integer> inputList = DataSet.generateIntegerLinkedList(size);
        List<Integer> resultList = linkedListSpliteratorExample.multiply_each_value(inputList, 2, true);
        assertEquals(resultList.size(),1000000);
    }
}