package com.lucidchart.threadflow;

import java.util.Random;

/**
 * Creates random ids with lowercase ASCII alphanumeric characters
 */
public class AlphaNumericIdGenerator {

    private final int length;

    public AlphaNumericIdGenerator(int length) {
        this.length = length;
    }

    private static final Random random = new Random();

    public String createId() {
        char[] characters = new char[length];
        for (int i = 0; i < length; i++) {
            int x = random.nextInt(('9' - '0' + 1) + ('a' - 'z' + 1));
            characters[i] = (char)(x < 'a' ? x + '0' : x - ('9' - '0' + 1) + 'a');
        }
        return new String(characters);
    }
}