package com.managereports.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Raouf Bouklab
 * Since 26-10-2019
 */
@Service
public class ManageReportService {

    private static final String FILE_NAME = "paragraph.txt";
    private static final Logger LOGGER = LoggerFactory.getLogger(ManageReportService.class);

    /**
     * Read paragraph from file
     * Split paragraph to words
     * Sort words by occurrence number
     *
     * @return The Map contains the number of occurrences of each word sorted
     */
    public Map<String, Integer> getSortedWordsOccurrences() {

        //Read paragraph from the test.txt file
        String paragraph = readParagraphFromFile(getFileName());

        //Log the paragraph
        LOGGER.info("The paragraph equals to : {}", paragraph);

        //Split paragraph to a list of sorted words
        List<String> listWords = splitParagraphToSortedListOfWords(paragraph);

        //Calculate number of occurrences of each word and sort the result
        return getOccurrencesNumberByWord(listWords);
    }

    /**
     * Open File
     * Read file line by line
     * Concatenate lines to build the paragraph
     *
     * @return the paragraph
     */
    public String readParagraphFromFile(String filename) {
        StringBuilder resultStringBuilder = new StringBuilder();

        //load file
        ClassLoader classLoader = getClass().getClassLoader();
        InputStream inputStream = classLoader.getResourceAsStream(filename);

        //Return null if file not exist
        if(inputStream == null){
            return resultStringBuilder.toString();
        }

        //Read file
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        BufferedReader br = new BufferedReader(inputStreamReader);

        String line;
        try{
            while ((line = br.readLine()) != null) {
                resultStringBuilder.append(line).append(" ");
            }
        }catch (IOException ex){
            LOGGER.error("Error occurred while reading file {} : {}", filename, ex.getMessage());
        }
        return resultStringBuilder.toString();
    }

    /**
     *
     * Replace all numbers and special character by spaces
     * Split paragraph to an array of words
     * Remove empty words, sort alphabetically the words of the stream and collect it in a list of String
     *
     * @param paragraph
     *
     * @return Sorted list of words
     */
    protected List<String> splitParagraphToSortedListOfWords(String paragraph){

        //return empty list if pargraph is null or empty
        if(StringUtils.isEmpty(paragraph)){
            return new ArrayList<>();
        }

        //Replace all numbers and special character (like . , ; ! ? ...) by space
        paragraph = paragraph.replaceAll("[^a-zA-Z]", " ");
        //Split paragraph to an array of words
        String[] wordsArray = paragraph.split(" ");
        //Convert the array to a stream
        Stream streamWords = Arrays.stream(wordsArray);
        //Predicate used to filter empty words
        Predicate isNotEmptyWord = s -> !StringUtils.isEmpty((String) s);
        //Remove empty words, sort alphabetically the words of the stream and collect it in a list of String
        return (List<String>) streamWords.filter(isNotEmptyWord).sorted(Comparator.naturalOrder()).collect(Collectors.toList());
    }

    /**
     * Calculate the number of occurrences of each word and add it to the map
     * Sort the Map
     *
     * @param listWords
     * @return Map<String, Integer> Sorted Map<word, occurrence>
     */
    protected Map<String, Integer> getOccurrencesNumberByWord(List<String> listWords){
        Map<String, Integer> wordOccurrences = new HashMap<>();

        //if list is null or empty, return new HashMap
        if(listWords == null || listWords.isEmpty()){
            return wordOccurrences;
        }

        listWords.stream().forEach(word -> {
            //If word exist in the map, add 1 to the number of occurrences
            if(wordOccurrences.containsKey(word)){
                wordOccurrences.put(word, wordOccurrences.get(word) + 1);
            }else{
                //Insert the word in the map
                wordOccurrences.put(word, 1);
            }
        });
        //Sort the map by the number of occurrences
        return sortWordsMapByOccurrenceNumber(wordOccurrences);
    }

    /**
     * Sort the Map of words
     *
     * @param wordOccurrences
     * @return sorted Map<String, Integer>
     */
    protected Map<String, Integer> sortWordsMapByOccurrenceNumber(Map<String, Integer> wordOccurrences){
        if(wordOccurrences == null){
            return new HashMap<>();
        }

        return wordOccurrences.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (oldValue, newValue) -> oldValue, LinkedHashMap::new));
    }

    /**
     * Get the filename
     *
     * @return filename
     */
    public String getFileName() {
        return FILE_NAME;
    }
}
