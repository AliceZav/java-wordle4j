package ru.yandex.practicum;

import java.io.*;
import static java.nio.charset.StandardCharsets.UTF_8;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WordleDictionaryLoader {
    public WordleDictionary loadFromFile(String path) throws IOException {
        List<String> words = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(path), UTF_8)) {
            String line;
            while (br.ready()) {
                words.add(br.readLine());
            }
        }
        WordleDictionary wordleDictionary = new WordleDictionary();
        wordleDictionary.addWords(words);
        return wordleDictionary;
        }
}
