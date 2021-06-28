package com.amarpreet.parallelstream;

import com.amarpreet.forkjoin.DataSet;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ArrayListSpliteratorExampleTest {

    ArrayListSpliteratorExample arrayListSpliteratorExample = new ArrayListSpliteratorExample();

   // @Test
    @RepeatedTest(5)
    void multiply_each_value() {
        int size = 1000000;
        ArrayList<Integer> inputList = DataSet.generateArrayList(size);
        List<Integer> resultList = arrayListSpliteratorExample.multiply_each_value(inputList, 2, false);
        assertEquals(resultList.size(),1000000);
    }

    // @Test
    @RepeatedTest(5)
    void multiply_each_value_when_isParallel_TRUE() {
        int size = 1000000;
        ArrayList<Integer> inputList = DataSet.generateArrayList(size);
        List<Integer> resultList = arrayListSpliteratorExample.multiply_each_value(inputList, 2, true);
        assertEquals(resultList.size(),1000000);
    }
}