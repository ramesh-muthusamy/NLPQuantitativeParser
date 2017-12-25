package com.isb.ta.core.utils;

import com.isb.ta.core.constants.QuantitativeConstants;
import com.isb.ta.core.constants.TaggingEnum;
import com.isb.ta.core.dto.EvaluationBean;
import edu.stanford.nlp.ie.NumberNormalizer;
import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.TaggedWord;
import edu.stanford.nlp.parser.nndep.DependencyParser;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import edu.stanford.nlp.trees.GrammaticalStructure;
import edu.stanford.nlp.trees.TypedDependency;

import java.io.StringReader;
import java.util.*;

import static edu.stanford.nlp.tagger.maxent.MaxentTagger.DEFAULT_JAR_PATH;

/**
 * @Author raemshkrishnan_muthusamy
 *
 * NLP Parser wrapper to analyse using POS tagging and  Dependency mapping
 *
 */
public class NLPLexParserWarpper {
    public static Map<String, List<String>> ownerMap = new HashMap();
    public static Map<String, EvaluationBean> contentMap = new HashMap<>();

    public static final MaxentTagger tagger = new MaxentTagger(DEFAULT_JAR_PATH);

    /**
     * Utility to invoke post tagging
     * @param text
     */
    public static void posTagger(String text) {
        DocumentPreprocessor tokenizer = new DocumentPreprocessor(new StringReader(text));
        //POS Tagging
        System.out.println("***************** POST TAG START*********************");
        for (List<HasWord> sentence : tokenizer) {
            List<TaggedWord> tagged = tagger.tagSentence(sentence);
            for (TaggedWord word : tagged) {
                System.out.println(word.toString());
            }
        }
        System.out.println("***************** POST TAG COMPLETE*********************");
    }

    /**
     * Utility to perform dependency parsing
     *
     * @param text
     * @throws Exception
     */
    public static void dependencyParser(String text) throws Exception {
        DependencyParser parser = DependencyParser.loadFromModelFile(DependencyParser.DEFAULT_MODEL);
        DocumentPreprocessor tokenizer = new DocumentPreprocessor(new StringReader(text));
        for (List<HasWord> sentence : tokenizer) {
            List<TaggedWord> tagged = tagger.tagSentence(sentence);
            List<String> ownedContents = new ArrayList<>();
            String owner = null;
            for (TaggedWord word : tagged) {
                //identify the owner from POS tagging and add to owner map
                if (word.tag().contains("."))
                    continue;
                switch (TaggingEnum.valueOf(word.tag())) {
                    case NNP:
                        owner = word.value();
                        break;

                }

            }
            GrammaticalStructure gs = parser.predict(tagged);
            // Print typed dependencies
            System.out.println("***************** DEPENDENCY MAPPING START*********************");
            System.out.println(gs);
            Collection<TypedDependency> dependencyList = gs.typedDependencies();
            for (TypedDependency dependency : dependencyList) {
                if (!(dependency.toString().toLowerCase().contains("how") && dependency.toString().toLowerCase().contains("many"))) {
                     if (dependency.dep().backingLabel().tag().equalsIgnoreCase("NNP")) {
                        Double contentValue =null;
                        //use compound values
                        if(dependencyList.stream().filter(dep -> dep.reln().getShortName().equalsIgnoreCase("compound")).count()>0){
                            TypedDependency depComp=dependencyList.stream().filter(dep -> dep.reln().getShortName().equalsIgnoreCase("compound")).findFirst().get();
                            contentValue=NumberNormalizer.wordToNumber(depComp.dep().value()+" "+depComp.gov().value()).doubleValue();
                        }
                        Optional<TypedDependency> depNumber = dependencyList.stream().filter(dep -> dep.reln().getShortName().equalsIgnoreCase("nummod")).findFirst();
                        String verb = dependency.gov().value();
                        //normalize numbers
                        if(contentValue==null)
                            contentValue=NumberNormalizer.wordToNumber(depNumber.get().dep().value()).doubleValue();
                        String contentString = depNumber.get().gov().value();
                        //add the owner if not present
                        if (!contentMap.containsKey(dependency.dep().value() + "-" + contentString)) {
                            contentMap.put(dependency.dep().value() + "-" + depNumber.get().gov().value(), new EvaluationBean(dependency.dep().value()));
                            contentMap.get(dependency.dep().value() + "-" + depNumber.get().gov().value()).setContent(contentString);
                            contentMap.get(dependency.dep().value() + "-" + depNumber.get().gov().value()).setQuantity(contentValue);
                            contentMap.get(dependency.dep().value() + "-" + depNumber.get().gov().value()).setVerb(verb);
                        } else {
                            EvaluationBean existingBean = contentMap.get(dependency.dep().value() + "-" + depNumber.get().gov().value());
                            existingBean.setQuantity(arithmeticConversion(verb, existingBean.getQuantity(), contentValue));
                        }

                    }
                } else {

                    String content = dependencyList.stream().filter(dep -> dep.reln().getShortName().equalsIgnoreCase("amod")).findFirst().get().gov().value();
                    if (contentMap.containsKey(owner + "-" + content)) {
                        EvaluationBean existingBean = contentMap.get(owner + "-" + content);
                        System.out.println("Response:" + owner + " has " + (existingBean.getQuantity()<0?0:existingBean.getQuantity().intValue()) + " " + content);
                        break;
                    } else {
                        System.out.println("No response");
                        break;
                    }
                }

            }
            System.out.println("***************** DEPENDENCY MAPPING COMPLETE*********************");

        }


    }

    /**
     * Utility to perform mathematic conversion
     *
     * @param verb
     * @param oldQty
     * @param newQty
     * @return
     * @throws Exception
     */
    public static Double arithmeticConversion(String verb, Double oldQty, Double newQty) throws Exception {

        if (QuantitativeConstants.positiveQuantList.contains(verb.toLowerCase())) {
            return oldQty + newQty;
        }
        if (QuantitativeConstants.negativeQuantList.contains(verb.toLowerCase())) {
            return oldQty - newQty;
        } else {
            throw new Exception("Invalid quantitative operation seeked.");
        }

    }

}
