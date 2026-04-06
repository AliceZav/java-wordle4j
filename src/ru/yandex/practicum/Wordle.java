package ru.yandex.practicum;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Scanner;

public class Wordle {
    public static void main(String[] args) {
        try {
            PrintWriter log = new PrintWriter("game.log");
            WordleDictionaryLoader dictionaryLoader = new WordleDictionaryLoader();

            WordleDictionary dictionary = dictionaryLoader.LoadFromFile("words_ru.txt");
            List<String> words = dictionary.filterWordsByLength(5); // Предполагаем, что все слова в словаре длиной 5 букв
            String answer = words.get((int) (Math.random() * words.size()));

            WordleGame game = new WordleGame(answer, 6, dictionary);
            Scanner scanner = new Scanner(System.in);

            while (game.hasStepsLeft()) {
                System.out.println("Введите слово:");
                String guess = scanner.nextLine();
                if (guess.equals("")) {
                    System.out.println("Подсказка: " + game.getHint());
                } else {
                    try {
                        boolean isCorrect = game.checkWord(guess);
                        if (isCorrect) {
                            System.out.println("Вы победили!");
                            break;
                        }
                    } catch (IllegalArgumentException e) {
                        System.out.println(e.getMessage());
                    }
                }
            }

            scanner.close();

            if (!game.hasStepsLeft()) {
                System.out.println("Вы проиграли. Правильное слово было: " + game.getAnswer());
            }

            log.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
