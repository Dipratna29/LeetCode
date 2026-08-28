class Solution {

    public String lexPalindromicPermutation(String s, String target) {

        int n = s.length();

        // Count characters in s
        int[] count = new int[26];

        for (char ch : s.toCharArray()) {
            count[ch - 'a']++;
        }

        // Check whether a palindrome is possible
        int odd = 0;
        int middle = -1;

        for (int i = 0; i < 26; i++) {
            if (count[i] % 2 == 1) {
                odd++;
                middle = i;
            }
        }

        // Cannot form a palindrome
        if (odd > 1) {
            return "";
        }

        int halfLen = n / 2;

        // Characters available for the first half
        int[] halfCount = new int[26];

        for (int i = 0; i < 26; i++) {
            halfCount[i] = count[i] / 2;
        }

        /*
         * STEP 1:
         * Try to make the first half exactly equal to
         * target's first half.
         */
        int[] remaining = halfCount.clone();
        char[] exactHalf = new char[halfLen];

        boolean possible = true;

        for (int i = 0; i < halfLen; i++) {

            int c = target.charAt(i) - 'a';

            if (remaining[c] == 0) {
                possible = false;
                break;
            }

            exactHalf[i] = target.charAt(i);
            remaining[c]--;
        }

        /*
         * If exact half is possible, build its palindrome.
         */
        if (possible) {

            String exactPalindrome =
                    buildPalindrome(exactHalf, middle, n);

            // This is the smallest possible palindrome
            // having this prefix.
            if (exactPalindrome.compareTo(target) > 0) {
                return exactPalindrome;
            }
        }

        /*
         * STEP 2:
         * Exact half was not enough.
         *
         * We need to make the first half greater than
         * target's first half.
         *
         * Change the rightmost possible position.
         */
        for (int pos = halfLen - 1; pos >= 0; pos--) {

            remaining = halfCount.clone();

            boolean prefixPossible = true;

            // Copy target's prefix [0 ... pos-1]
            for (int i = 0; i < pos; i++) {

                int c = target.charAt(i) - 'a';

                if (remaining[c] == 0) {
                    prefixPossible = false;
                    break;
                }

                remaining[c]--;
            }

            if (!prefixPossible) {
                continue;
            }

            int targetChar = target.charAt(pos) - 'a';

            /*
             * Choose the smallest character greater than
             * target[pos].
             */
            for (int c = targetChar + 1; c < 26; c++) {

                if (remaining[c] == 0) {
                    continue;
                }

                char[] half = new char[halfLen];

                // Copy prefix
                for (int i = 0; i < pos; i++) {
                    half[i] = target.charAt(i);
                }

                // Make this position greater
                half[pos] = (char) ('a' + c);

                remaining[c]--;

                // Fill the remaining positions with
                // the smallest possible characters.
                int index = pos + 1;

                for (int ch = 0; ch < 26; ch++) {
                    while (remaining[ch] > 0) {
                        half[index++] = (char) ('a' + ch);
                        remaining[ch]--;
                    }
                }

                String palindrome =
                        buildPalindrome(half, middle, n);

                if (palindrome.compareTo(target) > 0) {
                    return palindrome;
                }
            }
        }

        return "";
    }

    private String buildPalindrome(
            char[] half,
            int middle,
            int n) {

        StringBuilder sb = new StringBuilder();

        // First half
        for (char ch : half) {
            sb.append(ch);
        }

        // Middle character for odd length
        if (n % 2 == 1) {
            sb.append((char) ('a' + middle));
        }

        // Reverse of first half
        for (int i = half.length - 1; i >= 0; i--) {
            sb.append(half[i]);
        }

        return sb.toString();
    }
}