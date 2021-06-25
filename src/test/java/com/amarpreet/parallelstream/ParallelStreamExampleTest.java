package com.amarpreet.parallelstream;

import com.amarpreet.forkjoin.DataSet;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ParallelStreamExampleTest {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    ParallelStreamExample parallelStreamExample = new ParallelStreamExample();

    @Test
    void stringTransform() {
        //given
        List<String> inputLists = DataSet.namesList();
        //when
        List<String> resultSet = parallelStreamExample.stringTransform(inputLists);
        //then
        assertEquals(4,resultSet.size()) ;
        resultSet.forEach(name ->{
            assertTrue(name.contains("-"));
                }
        );
    }

    @ParameterizedTest
    @ValueSource(booleans = {false,true})
    void stringTransform_with_boolean_config(boolean isParallel) {
        //given
        List<String> inputLists = DataSet.namesList();
        //when
        long startTime = System.currentTimeMillis();
        List<String> resultSet = parallelStreamExample.stringTransform_with_boolean_config(inputLists,isParallel);
        long endTime = System.currentTimeMillis();
        System.out.println(ANSI_RED + "Time Taken::\t" + (endTime - startTime) + ANSI_RESET);
        //then
        assertEquals(4,resultSet.size()) ;
        resultSet.forEach(name ->{
                    assertTrue(name.contains("-"));
                }
        );
    }

    @Test
    void stringTransform_to_lowercase() {
        //given
        List<String> inputLists = DataSet.namesList();
        //when
        List<String> resultSet = parallelStreamExample.string_toLowerCase(inputLists);
        //then
        assertEquals(4,resultSet.size()) ;
        resultSet.forEach(name ->{
                    assertFalse(name.contains("-"));
                }
        );

    }
}