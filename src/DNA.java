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

    private final static int RADIX = 256;
    private final static long P_VALUE = 89434549747967L;
    public static int STRCount(String sequence, String STR) {
        int strLength = STR.length();
        int seqLength = sequence.length();

        long strHash = getHashCount(STR);
        long seqHash = getHashCount(sequence.substring(0, strLength));

        long highestRadix = 1;
        for (int i = 0; i < strLength - 1; i++) {
            highestRadix = (highestRadix * RADIX) % P_VALUE;
        }

        int longest = 0;

        for (int i = 0; i <= seqLength - strLength; i++) {;
            int current = 0;
            int currentIndex = i;
            long tempHash = seqHash;

            while (strHash == tempHash && !indexError(currentIndex, strLength, seqLength)) {
                current++;
                currentIndex += STR.length();
                if (!indexError(currentIndex, STR.length(),sequence.length())) {
                    tempHash = getHashCount(sequence.substring(currentIndex, currentIndex + STR.length()));
                }
            }

            longest = compare(current, longest);

            if (!indexError(i, STR.length() + 1, sequence.length())) {
                char first = sequence.charAt(i);
                char newest = sequence.charAt(i + STR.length());
                seqHash = changeHashCount(seqHash, first, newest, highestRadix);
            }
        }
        return longest;
    }

    public static long getHashCount(String str) {
        long hashCount = 0;
        for (int i = 0; i < str.length(); i++) {
            hashCount = (hashCount * RADIX + str.charAt(i)) % P_VALUE;
        }
        return hashCount;
    }

    public static long changeHashCount(long seqHash, char first, char newest, long highestRadix) {
        seqHash = (seqHash + P_VALUE - (first * highestRadix)) % P_VALUE;
        seqHash = (seqHash * RADIX + newest) % P_VALUE;
        return seqHash;
    }

    public static int compare(int current, int longest) {
        if (current > longest) {
            return current;
        }
        return longest;
    }

    public static boolean indexError(int index, int strLength, int seqlength) {
        if (index + strLength <= seqlength) {
            return false;
        }
        return true;
    }
}