/**
 * DNA
 * <p>
 * A puzzle created by Zach Blick
 * for Adventures in Algorithms
 * at Menlo School in Atherton, CA
 *</p>
 * <p>
 * Completed by: Lucas Huang
 *</p>
 */

public class DNA {

    public static int STRCount(String sequence, String STR) {
        int longest = 0;

        for (int i = 0; i < sequence.length(); i++) {
            int count = 0;
            while (i + (count + 1) * STR.length() <= sequence.length() &&
                    sequence.substring(i + count * STR.length(), i + (count + 1) * STR.length()).equals(STR)) {
                count++;
            }

            if (count > longest) {
                longest = count;
            }
        }
        return longest;
    }
}
