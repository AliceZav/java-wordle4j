package ru.yandex.practicum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordleGame {
    private String answer;
    private int steps;
    private WordleDictionary dictionary;
    private List<String> userInputs;

    public WordleGame(String answer, int steps, WordleDictionary dictionary) {
        this.answer = answer;
        this.steps = steps;
        this.dictionary = dictionary;
        userInputs = new ArrayList<>();
    }

    public boolean checkWord(String guess) {
        if (!dictionary.containsWord(guess)) {
            throw new IllegalArgumentException("Слово не найдено в словаре.");
        }
        StringBuilder hint = new StringBuilder();
        for (int i = 0; i < guess.length(); i++) {
            char guessChar = guess.charAt(i);
            char answerChar = answer.charAt(i);
            if (guessChar == answerChar) {
                hint.append('+');
            } else if (answer.indexOf(guessChar) != -1) {
                hint.append('^');
            } else {
                hint.append('-');
            }
        }
        System.out.println("Подсказка: " + hint);
        userInputs.add(guess);
        steps--;
        return guess.equals(answer);
    }

    public String getHint() {
        List<String> candidates = dictionary.filterWordsByLength(answer.length());

        Map<Integer, Character> correctPositions = new HashMap<>();
        Map<Character, List<Integer>> wrongPositions = new HashMap<>();
        List<Character> absentLetters = new ArrayList<>();

        for (String input : userInputs) {
            for (int i = 0; i < input.length(); i++) {
                char c = input.charAt(i);
                char answerChar = answer.charAt(i);

                if (c == answerChar) {
                    correctPositions.put(i, c);
                } else if (answer.indexOf(c) != -1) {
                    if (!wrongPositions.containsKey(c)) {
                        wrongPositions.put(c, new ArrayList<>());
                    }
                    wrongPositions.get(c).add(i);
                } else {
                    if (!absentLetters.contains(c)) {
                        absentLetters.add(c);
                    }
                }
            }
        }

        List<String> filtered = new ArrayList<>();
        for (String word : candidates) {
            if (matchesConstraints(word, correctPositions, wrongPositions, absentLetters)) {
                filtered.add(word);
            }
        }

        if (filtered.isEmpty()) {
            return candidates.get((int) (Math.random() * candidates.size()));
        }
        return filtered.get((int) (Math.random() * filtered.size()));
    }

    private boolean matchesConstraints(
            String word,
            Map<Integer, Character> correctPositions,
            Map<Character, List<Integer>> wrongPositions,
            List<Character> absentLetters) {

        for (Map.Entry<Integer, Character> entry : correctPositions.entrySet()) {
            if (word.charAt(entry.getKey()) != entry.getValue()) {
                return false;
            }
        }

        for (Map.Entry<Character, List<Integer>> entry : wrongPositions.entrySet()) {
            char c = entry.getKey();
            if (word.indexOf(c) == -1) {
                return false;
            }
            for (int pos : entry.getValue()) {
                if (word.charAt(pos) == c) {
                    return false;
                }
            }
        }

        for (char c : absentLetters) {
            if (word.indexOf(c) != -1) {
                return false;
            }
        }

        return true;
    }

    public boolean hasStepsLeft() {
        return steps > 0;
    }

    public String getAnswer() {
        return answer;
    }
}