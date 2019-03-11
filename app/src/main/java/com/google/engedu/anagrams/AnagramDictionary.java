/* Copyright 2016 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.google.engedu.anagrams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

public class AnagramDictionary {

    private static final int MIN_NUM_ANAGRAMS = 5;
    private static final int DEFAULT_WORD_LENGTH = 3;
    private static final int MAX_WORD_LENGTH = 7;
    private Random random = new Random();
    private List<String> wordList;
    private HashSet<String> wordSet;
    private HashMap<String, ArrayList<String>> lettersToWord;

    public AnagramDictionary(Reader reader) throws IOException {
        BufferedReader in = new BufferedReader(reader);
        wordSet = new HashSet<>();
        lettersToWord = new HashMap<>();
        wordList = new ArrayList<>();
        String line;
        while((line = in.readLine()) != null) {
            String word = line.trim();
            wordList.add(word);
            wordSet.add(word);
            String letterHasSorted = sortLetters(word);
            if (lettersToWord.containsKey(letterHasSorted)) {
                lettersToWord.get(letterHasSorted).add(word);
            } else {
                ArrayList<String> temp = new ArrayList<>();
                temp.add(word);
                lettersToWord.put(letterHasSorted, temp);
            }
        }
    }

    public boolean isGoodWord(String word, String base) {
        return wordSet.contains(word) && (!word.contains(base));
    }

    public List<String> getAnagrams(String targetWord) {
        List<String> result = new ArrayList<String>();
        String sortedTargetWord = sortLetters(targetWord);
        for (int i = 0; i < wordList.size(); i++) {
            if (wordList.get(i).length() == targetWord.length()) {
                if (targetWord.equals(sortLetters(wordList.get(i)))){
                    result.add(wordList.get(i));
                }
            }
        }
        return result;
    }

    public List<String> getAnagramsWithOneMoreLetter(String word) {
        List<String> result = new ArrayList<String>();
        for (int i = 0; i < 26; i++) {
            String strToSort = word + Character.toString((char)(97 + i));
            String sortedStr = sortLetters(strToSort);
            if (lettersToWord.containsKey(sortedStr)) {
                result.addAll(lettersToWord.get(sortedStr));
            }
        }
        return result;
    }

    public String pickGoodStarterWord() {
        return "stop";
    }
    public static String sortLetters(String strToSort) {
        List<Character> letter = new ArrayList<>();
        for (int i = 0;i < strToSort.length();i++) {
            letter.add(strToSort.charAt(i));
        }
        Comparator<Character> cs = new CharacterLT();
        Collections.sort(letter, cs);
        String sortedStr = "";
        for (int i = 0; i < letter.size(); i++) {
            sortedStr += letter.get(i);
        }
        return sortedStr;
    }

    private static class CharacterLT implements Comparator<Character> {
        @Override
        public int compare(Character o1, Character o2) {
            return o1.compareTo(o2);
        }
    }
}
