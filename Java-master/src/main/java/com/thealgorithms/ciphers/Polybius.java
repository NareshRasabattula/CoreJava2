package com.thealgorithms.ciphers;

/**
 * A Java implementation of Polybius Cipher
 * Polybius is a substitution cipher method
 * It was invented by a greek philosopher that name is Polybius
 * Letters in alphabet takes place to two dimension table.
 * Encrypted text is created according to row and column in two dimension table
 * Decrypted text is generated by looking at the row and column respectively
 * Additionally, some letters in english alphabet deliberately throws such as U because U is very
 * similar with V
 *
 * @author Hikmet ÇAKIR
 * @since 08-07-2022+03:00
 */
public final class Polybius {
    private Polybius() {
    }

    private static final char[][] KEY = {
        //         0    1    2    3    4
        /* 0 */ {'A', 'B', 'C', 'D', 'E'},
        /* 1 */ {'F', 'G', 'H', 'I', 'J'},
        /* 2 */ {'K', 'L', 'M', 'N', 'O'},
        /* 3 */ {'P', 'Q', 'R', 'S', 'T'},
        /* 4 */ {'V', 'W', 'X', 'Y', 'Z'},
    };

    private static String findLocationByCharacter(final char character) {
        final StringBuilder location = new StringBuilder();
        for (int i = 0; i < KEY.length; i++) {
            for (int j = 0; j < KEY[i].length; j++) {
                if (character == KEY[i][j]) {
                    location.append(i).append(j);
                    break;
                }
            }
        }
        return location.toString();
    }

    public static String encrypt(final String plaintext) {
        final char[] chars = plaintext.toUpperCase().toCharArray();
        final StringBuilder ciphertext = new StringBuilder();
        for (char aChar : chars) {
            String location = findLocationByCharacter(aChar);
            ciphertext.append(location);
        }
        return ciphertext.toString();
    }

    public static String decrypt(final String ciphertext) {
        final char[] chars = ciphertext.toCharArray();
        final StringBuilder plaintext = new StringBuilder();
        for (int i = 0; i < chars.length; i += 2) {
            int pozitionX = Character.getNumericValue(chars[i]);
            int pozitionY = Character.getNumericValue(chars[i + 1]);
            plaintext.append(KEY[pozitionX][pozitionY]);
        }
        return plaintext.toString();
    }
}
