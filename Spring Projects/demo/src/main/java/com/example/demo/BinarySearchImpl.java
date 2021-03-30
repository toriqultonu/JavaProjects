package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)
public class BinarySearchImpl {

    @Autowired
    @Qualifier("bubble")
    private SortAlgo sortAlgo;
    private SortAlgo sortAlgo1;


    public int binarySearch(int[] numbers, int numberToSearchFor){

      int[] sortedNumber = sortAlgo.sort(numbers);
      System.out.println(sortAlgo);

        return 3;
    }
}
