package com.amarpreet.forkjoin;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;

import static com.amarpreet.util.CommonUtil.delay;
import static com.amarpreet.util.CommonUtil.stopWatch;
import static com.amarpreet.util.LoggerUtil.mylog;

public class ForkJoinUsingRecursion extends RecursiveTask<List<String>> {

    private List<String> inputList;

    public ForkJoinUsingRecursion(List<String> inputList) {
        this.inputList = inputList;
    }

    public static void main(String[] args){
        stopWatch.start();
        List<String> resultList = new ArrayList<>();
        List<String> names = DataSet.namesList();
        ForkJoinPool forkJoinPool = new ForkJoinPool();
        ForkJoinUsingRecursion forkJoinUsingRecursion = new ForkJoinUsingRecursion(names);
        resultList = forkJoinPool.invoke(forkJoinUsingRecursion);

        stopWatch.stop();
        mylog("final Result :\t" + resultList);
        mylog("Total time taken :\t" + stopWatch.getTime());
    }

    private static String addNameLengthTransform(String name) {
        delay(500);
        return name.length() + "-" + name;
    }

    @Override
    protected List<String> compute() {
        if(inputList.size()<=1){
            List<String> resultList = new ArrayList<>();
            inputList.forEach(name -> resultList.add(addNameLengthTransform(name)));
            return resultList;
        }
        int midSize = inputList.size()/2;
        ForkJoinTask<List<String>> leftInputList = new ForkJoinUsingRecursion(inputList.subList(0,midSize)).fork();
        inputList = inputList.subList(midSize,inputList.size());
        List<String> rightResult = compute(); // recursion
        List<String> leftResult = leftInputList.join();
        leftResult.addAll(rightResult);
        return leftResult;
    }
}
