package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.List;

public class WordleDictionary {

    private List<String> wordleDictionary;

    public WordleDictionary() {
        wordleDictionary = new ArrayList<>();
    }

    //метод для добавления слов в словарь
    public void addWords(List<String> words) {
        wordleDictionary.addAll(words);
    }

    // Метод для проверки наличия слова в словаре
    public boolean containsWord(String word) {
        return wordleDictionary.contains(word);
    }

    // Метод для фильтрации слов по длине
    public List<String> filterWordsByLength(int length) {
        List<String> filteredWords = new ArrayList<>();
        for (String word : wordleDictionary) {
            if (word.length() == length) {
                filteredWords.add(word);
            }
        }
        return filteredWords;
    }

    // Метод для нормализации слова (приведение к нижнему регистру и замена ё на е)
    public String normalizeWord(String word) {
        return word.toLowerCase().replace('ё', 'е');
    }

}
