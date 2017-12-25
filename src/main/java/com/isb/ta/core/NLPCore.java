package com.isb.ta.core;

import com.isb.ta.core.utils.NLPLexParserWarpper;

public class NLPCore {
    public static void main(String[] args) throws Exception {
        if (args.length > 1) {
            System.out.println("Atmost one argument can be expected , Please enclose the string in double quotes");
            System.exit(1);
        }
        if (args.length == 0) {
            System.out.println("Invalid list of arguments, refer to the usage");
            System.out.println("Usage: NLPCore \"<user input>\"");
            System.exit(1);

        }
        String userString = args[0];
        quantsEvaluator(userString);


    }

    public static void quantsEvaluator(String userString) throws Exception {
        System.out.println(String.format("User input %s", userString));
        NLPLexParserWarpper.posTagger(userString);
        NLPLexParserWarpper.dependencyParser(userString);
    }
}
