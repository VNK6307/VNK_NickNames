import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    private final static AtomicInteger counter3 = new AtomicInteger(0);
    private final static AtomicInteger counter4 = new AtomicInteger(0);
    private final static AtomicInteger counter5 = new AtomicInteger(0);

    public static void main(String[] args) throws InterruptedException {
        Random random = new Random();
        String[] texts = new String[100_000];
        for (int i = 0; i < texts.length; i++) {
            texts[i] = generateText("abc", 3 + random.nextInt(3));
        }

        Thread palindromeCounter = new Thread(() -> {
            for (String word : texts) {
                if (isPalindrome(word)) {
                    countersIncreasing(word.length());
                }
            }
        });

        Thread sameLettersCounter = new Thread(() -> {
            for (String word : texts) {
                if (isSameLetters(word)) {
                    countersIncreasing(word.length());
                }
            }
        });

        Thread increaseOrderCounter = new Thread(() -> {
            for (String word : texts) {
                if (isIncreaseOrder(word)) {
                    countersIncreasing(word.length());
                }
            }
        });

        palindromeCounter.start();
        sameLettersCounter.start();
        increaseOrderCounter.start();

        palindromeCounter.join();
        sameLettersCounter.join();
        increaseOrderCounter.join();

        System.out.println("Красивых слов длиной 3: " + counter3 + " шт.");
        System.out.println("Красивых слов длиной 4: " + counter4 + " шт.");
        System.out.println("Красивых слов длиной 5: " + counter5 + " шт.");

    }// main

    public static String generateText(String letters, int length) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        for (int i = 0; i < length; i++) {
            text.append(letters.charAt(random.nextInt(letters.length())));
        }
        return text.toString();
    }

    public static boolean isPalindrome(String text) {
        char[] chars = text.toCharArray();
        int left = 0;
        int right = chars.length - 1;
        while (left < right) {
            if (chars[left] != chars[right]) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static boolean isSameLetters(String text) {
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length - 1; i++) {
            if (chars[i] != chars[i + 1]) {
                return false;
            }
        }
        return true;
    }

    public static boolean isIncreaseOrder(String text) {
        StringBuilder sb = new StringBuilder();
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length - 1; i++) {
            if (chars[i] > chars[i + 1]) {
                return false;
            }
            sb.append(chars[i]);
        }
        return !isSameLetters(sb.toString());
    }

    public static void countersIncreasing(int wordLength) {
        if (wordLength == 3) {
            counter3.getAndAdd(1);
        } else if (wordLength == 4) {
            counter4.getAndAdd(1);
        } else {
            counter5.getAndAdd(1);
        }
    }
}// class
