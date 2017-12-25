package com.isb.ta.core.constants;

import java.util.ArrayList;
import java.util.List;

public class QuantitativeConstants {

    private static final String HAD = "had";
    private static final String GAVE = "gave";
    private static final String FOUND = "found";
    private static final String INCREASED = "increased";
    private static final String DECREASED = "decreased";
    private static final String ADDED = "added";
    private static final String LOST = "lost";


    public static final List<String> positiveQuantList = new ArrayList<>();
    public static final List<String> negativeQuantList = new ArrayList<>();

    static {
        positiveQuantList.add(HAD);
        positiveQuantList.add(FOUND);
        positiveQuantList.add(INCREASED);
        positiveQuantList.add(ADDED);
        negativeQuantList.add(GAVE);
        negativeQuantList.add(LOST);
        negativeQuantList.add(DECREASED);


    }
}
