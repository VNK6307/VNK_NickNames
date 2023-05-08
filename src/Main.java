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
                if (word.length() == 3 && isPalindrome(word)) {
                    counter3.getAndAdd(1);
                } else if (word.length() == 4 && isPalindrome(word)) {
                    counter4.getAndAdd(1);
                } else if (word.length() == 5 && isPalindrome(word)) {
                    counter5.getAndAdd(1);
                }
            }
        });

        Thread sameLettersCounter = new Thread(() -> {
            for (String word : texts) {
                if (word.length() == 3 && isSameLetters(word)) {
                    counter3.getAndAdd(1);
                } else if (word.length() == 4 && isSameLetters(word)) {
                    counter4.getAndAdd(1);
                } else if (word.length() == 5 && isSameLetters(word)) {
                    counter5.getAndAdd(1);
                }
            }
        });

        Thread increaseOrderCounter = new Thread(() -> {
            for (String word : texts) {
                if (word.length() == 3 && isIncreaseOrder(word)) {
                    counter3.getAndAdd(1);
                } else if (word.length() == 4 && isIncreaseOrder(word)) {
                    counter4.getAndAdd(1);
                } else if (word.length() == 5 && isIncreaseOrder(word)) {
                    counter5.getAndAdd(1);
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
        if (isSameLetters(sb.toString())) {
            return false;
        }
        return true;
    }
}// class
