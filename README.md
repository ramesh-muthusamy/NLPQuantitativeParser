# NLPQuantitativeParserSummary

NLP based Quantitative language parser is a module build with the requirements as,

Solve simple mathematical word problem using natural language processing and text analytics. Use PoS Tagging, Parsing and Typed Dependency to analyze the math query.

Example:

Jack had 15 pencils. Jack found three pencils more. How many pencils do Jack have now? Jill had 40 toys. Jill lost seventeen toys. How many toys are left with Jill? John had 20 apples. John added 6 apples. How many apples are there with John?

**Exception: **

John had 20 apples. John gave 26 apples to Jill. How many apples are there with John? - This will give 0 apples.

Features handled

Compound values. e.g) "John had twenty one apples. John decreased 10 apples. How many apples are there with John?"
Text to numeric conversion. e.g) "Jill had 40 toys. Jill lost seventeen toys. How many toys are left with Jill?"
Dependency Mapping. e.g) "John had twenty one apples. Jill added 6 apples. How many apples are there with John?"
POS tagging based response.
Handling Mathematically equivalent verbs positiveQuantList.add(HAD); positiveQuantList.add(FOUND); positiveQuantList.add(INCREASED); positiveQuantList.add(ADDED); negativeQuantList.add(GAVE); negativeQuantList.add(LOST); negativeQuantList.add(DECREASED);
