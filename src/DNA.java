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

    // Used 4 as radix because only 4 letters in this problem set, otherwise would be 256
    private final static int RADIX = 4;
    private final static long P_VALUE = 89434549747967L;
    public static int STRCount(String sequence, String STR) {

        int strLength = STR.length();
        int seqLength = sequence.length();

        // Gets the hash count of the string and the first string of the sequence
        long strHash = getHashCount(STR);
        long seqHash = getHashCount(sequence.substring(0, strLength));

        // Calculates the highestRadix for the shifts by 1 later on
        long highestRadix = 1;
        for (int i = 0; i < strLength - 1; i++) {
            highestRadix = (highestRadix * RADIX) % P_VALUE;
        }

        int longest = 0;
        int current = 0;

        // Fixed iteration for loop that goes until last possible beginning index
        for (int i = 0; i <= seqLength - strLength;) {

            // If hash counts are equal add to the current count, using Monte Carlo algorithm
            if (strHash == seqHash) {
                current++;
                // Iterate by length of str to move onto potential second match next loop
                i += strLength;
                // Makes sure that there won't be an index out of bounds and then gets the hash count of the next sequence
                if (!indexError(i, strLength,sequence.length())) {
                    seqHash = getHashCount(sequence.substring(i, i + strLength));
                }
            }
            else {
                // Checks to see if the matching just ended
                if (current > 0) {
                    // If so compare the match length to the longest and take the biggest number
                    longest = compare(current, longest);
                    // Reset current count
                    current = 0;
                    // Move index to second char in the last matching string to check if there is a match there
                    i = i - strLength + 1;
                    seqHash = getHashCount(sequence.substring(i, i + strLength));
                }
                // If not shift string by one index
                else {
                    i++;
                    if (!indexError(i, STR.length(), sequence.length())) {
                        // First letter of the sequence string
                        char first = sequence.charAt(i - 1);
                        // Letter that you would add on
                        char newest = sequence.charAt(i + STR.length() - 1);
                        seqHash = changeHashCount(seqHash, first, newest, highestRadix);
                    }
                }
            }
        }
        // Checks to see if longest run is at end of the sequence
        longest = compare(current, longest);
        return longest;
    }

    // Gets the hash count of a string
    public static long getHashCount(String str) {
        long hashCount = 0;
        // Uses Horner's method to calculate
        for (int i = 0; i < str.length(); i++) {
            // Multiplies previous value by radix and adds the next char and divides by a p value to prevent overflow
            hashCount = (hashCount * RADIX + str.charAt(i)) % P_VALUE;
        }
        return hashCount;
    }

    // Shifts the hash count by one char to the right
    public static long changeHashCount(long seqHash, char first, char newest, long highestRadix) {
        // Subtracts the beginning char and then adds the new one in its place using Horner's method
        seqHash = (seqHash + P_VALUE - ((first * highestRadix) % P_VALUE)) % P_VALUE;
        seqHash = (seqHash * RADIX + newest) % P_VALUE;
        return seqHash;
    }

    // Compares the two values and takes the largest one
    public static int compare(int current, int longest) {
        if (current > longest) {
            return current;
        }
        return longest;
    }

    // Checks to make sure there won't be an index out of bounds error
    public static boolean indexError(int index, int strLength, int seqLength) {
        if (index + strLength <= seqLength) {
            return false;
        }
        return true;
    }
}