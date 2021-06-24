package com.amarpreet.forkjoin;

import java.util.ArrayList;
import java.util.List;

import static com.amarpreet.util.CommonUtil.delay;
import static com.amarpreet.util.CommonUtil.stopWatch;
import static com.amarpreet.util.LoggerUtil.mylog;

public class StringTransformExample {
    public static void main(String[] args){
        stopWatch.start();
        List<String> resultList = new ArrayList<>();
        List<String> names = DataSet.namesList();
        names.forEach((name) ->
        {
            String newName = addNameLengthTransform(name);
            resultList.add(newName);
        });
        stopWatch.stop();
        mylog("final Result :\t" + resultList);
        mylog("Total time taken :\t" + stopWatch.getTime());
    }

    private static String addNameLengthTransform(String name) {
        delay(500);
        return name.length() + "-" + name;
    }

}
