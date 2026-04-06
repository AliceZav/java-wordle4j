package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class WordleTest {

    private WordleDictionary dictionary;
    private WordleGame game;

    @BeforeEach
    void setUp() {
        dictionary = new WordleDictionary();
        dictionary.addWords(List.of("каток", "замок", "парок", "лесок", "песок"));
        game = new WordleGame("каток", 6, dictionary);
    }

    @Test
    void containsWord_shouldReturnTrue_whenWordExists() {
        assertTrue(dictionary.containsWord("каток"));
    }

    @Test
    void containsWord_shouldReturnFalse_whenWordNotExists() {
        assertFalse(dictionary.containsWord("слово"));
    }

    @Test
    void filterWordsByLength_shouldReturnOnlyMatchingLength() {
        dictionary.addWords(List.of("кот", "слон"));
        List<String> result = dictionary.filterWordsByLength(5);
        assertTrue(result.contains("каток"));
        assertFalse(result.contains("кот"));
        assertFalse(result.contains("слон"));
    }

    @Test
    void normalizeWord_shouldLowercaseAndReplaceYo() {
        assertEquals("елка", dictionary.normalizeWord("Ёлка"));
        assertEquals("кот", dictionary.normalizeWord("КОТ"));
    }

    @Test
    void checkWord_shouldReturnTrue_whenGuessIsCorrect() {
        assertTrue(game.checkWord("каток"));
    }

    @Test
    void checkWord_shouldReturnFalse_whenGuessIsWrong() {
        assertFalse(game.checkWord("замок"));
    }

    @Test
    void checkWord_shouldThrowException_whenWordNotInDictionary() {
        assertThrows(IllegalArgumentException.class, () -> game.checkWord("абвгд"));
    }

    @Test
    void checkWord_shouldDecrementSteps() {
        game.checkWord("замок");
        game.checkWord("парок");
        assertTrue(game.hasStepsLeft());
    }


    @Test
    void hasStepsLeft_shouldReturnFalse_afterSixWrongGuesses() {
        dictionary.addWords(List.of("лесок", "песок", "венок", "урок2", "поток", "моток"));
        WordleGame freshGame = new WordleGame("каток", 6, dictionary);

        freshGame.checkWord("замок");
        freshGame.checkWord("парок");
        freshGame.checkWord("лесок");
        freshGame.checkWord("песок");
        freshGame.checkWord("венок");
        freshGame.checkWord("поток");

        assertFalse(freshGame.hasStepsLeft());
    }

    @Test
    void getHint_shouldReturnWordOfCorrectLength() {
        String hint = game.getHint();
        assertEquals(5, hint.length());
    }

    @Test
    void getHint_shouldReturnWordFromDictionary() {
        String hint = game.getHint();
        assertTrue(dictionary.containsWord(hint));
    }

    @Test
    void getHint_shouldNotReturnWordWithAbsentLetter() {
        game.checkWord("замок");
        String hint = game.getHint();
        assertNotNull(hint);
        assertEquals(5, hint.length());
    }
}