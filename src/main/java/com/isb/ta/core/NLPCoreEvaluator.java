package com.isb.ta.core;

public class NLPCoreEvaluator {
   public static void main(String[] args) throws Exception {
       NLPCore.quantsEvaluator("Jack had 15 pencils. Jack found three pencils more. How many pencils do Jack have now?");
       NLPCore.quantsEvaluator("Jill had 40 toys. Jill lost seventeen toys. How many toys are left with Jill?");
       NLPCore.quantsEvaluator("John had 20 apples. John added 6 apples. How many apples are there with John?");
       NLPCore.quantsEvaluator("John had twenty one apples. Jill added 6 apples. How many apples are there with John?");
       NLPCore.quantsEvaluator("John had twenty one apples. John decreased 21 apples. How many apples are there with John?");

   }
}
