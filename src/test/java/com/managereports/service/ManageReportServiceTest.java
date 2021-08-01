package com.managereports.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Created by Raouf Bouklab
 * Since 27-10-2019
 */
@RunWith(SpringRunner.class)
public class ManageReportServiceTest {

    private static final String FILE_NAME = "test.txt";
    private ManageReportService service = Mockito.spy(new ManageReportService());

    private Map<String, Integer> wordOccurrences;
    private List<String> listWords;
    private String paragraph;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void givenAFile_whengetSortedWordsOccurrences_thenReturnSortedMap(){
        //given
        when(service.getFileName()).thenReturn(FILE_NAME);
        //when
        Map<String, Integer> sortedWordOccurrenceMap = service.getSortedWordsOccurrences();
        //then
        verify(service, times(1)).splitParagraphToSortedListOfWords(any());
        verify(service, times(1)).getOccurrencesNumberByWord(any());
        assertEquals(9, sortedWordOccurrenceMap.size());
    }

    @Test
    public void givenNotExistingFile_whenreadParagraphFromFile_thenReturnEmptyString(){
        //given
        String filename = "test-not-exist.txt";
        //when
        String paragraph = service.readParagraphFromFile(filename);
        //then
        assertEquals("", paragraph);
    }

    @Test
    public void givenExistingFile_whenreadParagraphFromFile_thenReturnAParagraph(){
        //given
        String filename = "test.txt";
        //when
        String paragraph = service.readParagraphFromFile(filename);
        //then
        assertEquals("You cannot end a sentence with because because because is a conjunction. ", paragraph);
    }

    @Test
    public void givenNullParagraph_whensplitParagraphToSortedListOfWords_thenReturnEmptyList(){
        //given
        paragraph = null;
        //when
        List<String> words = service.splitParagraphToSortedListOfWords(paragraph);
        //then
        assertEquals(0, words.size());
    }

    @Test
    public void givenEmptyParagraph_whensplitParagraphToSortedListOfWords_thenReturnEmptyList(){
        //given
        paragraph = "";
        //when
        List<String> words = service.splitParagraphToSortedListOfWords(paragraph);
        //then
        assertEquals(0, words.size());
    }

    @Test
    public void givenParagraphContainsSpecialChar_whensplitParagraphToSortedListOfWords_thenReplaceItBySpaces(){
        //given
        paragraph = "Hello Everybody !!!";
        //when
        List<String> words = service.splitParagraphToSortedListOfWords(paragraph);
        //then
        assertTrue(words.contains("Everybody"));
        assertFalse(words.contains("Everybody!!!"));
    }

    @Test
    public void givenParagraphContainsSpecialChar_whensplitParagraphToSortedListOfWords_thenListDontContainsEmptyWord(){
        //given
        paragraph = "Hello Everybody !!!";
        //when
        List<String> words = service.splitParagraphToSortedListOfWords(paragraph);
        //then
        assertFalse(words.contains(""));
        assertFalse(words.contains(" "));
    }

    @Test
    public void givenParagraph_whensplitParagraphToSortedListOfWords_thenreturnSortedList(){
        //given
        paragraph = "Hello Everybody";
        //when
        List<String> words = service.splitParagraphToSortedListOfWords(paragraph);
        //then
        assertEquals("Everybody", words.get(0));
        assertEquals("Hello", words.get(1));
    }

    @Test
    public void givenNullWordList_whengetOccurrencesNumberByWord_thenReturnEmptyMap(){
        //given
        listWords = null;
        //when
        Map<String, Integer> wordOccurrencesMap = service.getOccurrencesNumberByWord(listWords);
        //then
        assertEquals(0, wordOccurrencesMap.size());
        verify(service, never()).sortWordsMapByOccurrenceNumber(any());
    }

    @Test
    public void givenNotNullWordList_whengetOccurrencesNumberByWord_thenReturnSortedMap(){
        //given
        listWords = createWordsList();
        //when
        Map<String, Integer> wordOccurrencesMap = service.getOccurrencesNumberByWord(listWords);
        //then
        assertEquals(3, wordOccurrencesMap.get("because").intValue());
        assertEquals(2, wordOccurrencesMap.get("a").intValue());
        assertEquals(1, wordOccurrencesMap.get("You").intValue());
        verify(service, times(1)).sortWordsMapByOccurrenceNumber(wordOccurrencesMap);
    }

    @Test
    public void givenNullMap_whensortWordsMapByOccurrenceNumber_thenReturnNullMap(){
        //given
        wordOccurrences = null;
        //when
        Map<String, Integer> sortedWordOccurrenceMap = service.sortWordsMapByOccurrenceNumber(wordOccurrences);
        //then
        assertEquals(0, sortedWordOccurrenceMap.size());
    }

    @Test
    public void givenNotNullMap_whensortWordsMapByOccurrenceNumber_thenReturnSortedMap(){
        //given
        wordOccurrences = createWordOccurrenceMap();
        //when
        Map<String, Integer> sortedWordOccurrenceMap = service.sortWordsMapByOccurrenceNumber(wordOccurrences);
        //then
        assertEquals(3, sortedWordOccurrenceMap.entrySet().stream().findFirst().get().getValue().intValue());
    }

    @Test
    public void getFileNameTest() {
        when(service.getFileName()).thenReturn(FILE_NAME);
        assertEquals(FILE_NAME, service.getFileName());
    }

    private Map<String, Integer> createWordOccurrenceMap(){
        Map<String, Integer> wordOccurrenceMap = new HashMap<>();
        wordOccurrenceMap.put("with",1);
        wordOccurrenceMap.put("a",2);
        wordOccurrenceMap.put("because",3);
        wordOccurrenceMap.put("end",1);
        return wordOccurrenceMap;
    }

    private List<String> createWordsList(){
        return Arrays.asList("You", "cannot", "end", "a", "sentence", "with", "because", "because", "because", "is", "a", "conjunction");
    }
}