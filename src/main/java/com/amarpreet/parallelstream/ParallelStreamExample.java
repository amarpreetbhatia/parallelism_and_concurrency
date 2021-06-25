package com.amarpreet.parallelstream;

import com.amarpreet.forkjoin.DataSet;

import javax.xml.crypto.Data;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.amarpreet.util.CommonUtil.delay;
import static com.amarpreet.util.CommonUtil.stopWatch;
import static com.amarpreet.util.LoggerUtil.mylog;

public class ParallelStreamExample {
    public List<String> stringTransform(List<String> namesList){
       return namesList
                //.stream()
                .parallelStream()
                .map(this::addNameLengthTransform)
                .collect(Collectors.toList());
    }

    public List<String> string_toLowerCase(List<String> namesList){
        return namesList
                .parallelStream()
                .map(e -> e.toLowerCase())
                .collect(Collectors.toList());
    }

    public List<String> stringTransform_with_boolean_config(List<String> namesList,boolean isParallel){
        Stream<String> nameListStream = namesList.stream();
        if(isParallel)
            nameListStream.parallel();

        return nameListStream
                .map(this::addNameLengthTransform)
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        stopWatch.start();
        ParallelStreamExample parallelStreamExample = new ParallelStreamExample();
        List<String> resultList = parallelStreamExample.stringTransform(DataSet.namesList());
        stopWatch.stop();
        mylog("final Result :\t" + resultList);
        mylog("Total time taken :\t" + stopWatch.getTime());
    }

    private String addNameLengthTransform(String name) {
        delay(500);
        return name.length() + "-" + name;
    }
}
