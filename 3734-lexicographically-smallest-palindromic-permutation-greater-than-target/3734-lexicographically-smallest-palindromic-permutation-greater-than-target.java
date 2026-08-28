class Solution {

    public String lexPalindromicPermutation(String s, String target) {

        int n = s.length();

        // Count characters
        int[] count = new int[26];

        for (char ch : s.toCharArray()) {
            count[ch - 'a']++;
        }

        // Check whether palindrome is possible
        int odd = 0;
        int middle = -1;

        for (int i = 0; i < 26; i++) {
            if (count[i] % 2 == 1) {
                odd++;
                middle = i;
            }
        }

        if (odd > 1) {
            return "";
        }

        // Number of characters in the first half
        int halfLen = n / 2;

        // Count characters available in first half
        int[] half = new int[26];

        for (int i = 0; i < 26; i++) {
            half[i] = count[i] / 2;
        }

        /*
         * Try to make the first half equal to target's
         * first half.
         */
        int[] remaining = half.clone();
        char[] firstHalf = new char[halfLen];

        boolean possible = true;

        for (int i = 0; i < halfLen; i++) {

            int c = target.charAt(i) - 'a';

            if (remaining[c] == 0) {
                possible = false;
                break;
            }

            firstHalf[i] = target.charAt(i);
            remaining[c]--;
        }

        /*
         * If we can copy target's first half,
         * build the palindrome and check it.
         */
        if (possible) {

            String candidate =
                    build(firstHalf, middle, n);

            if (candidate.compareTo(target) > 0) {
                return candidate;
            }
        }

        /*
         * Now we need to make the first half slightly bigger.
         *
         * Start from the RIGHT because changing the rightmost
         * possible position gives the smallest answer.
         */
        for (int pos = halfLen - 1; pos >= 0; pos--) {

            remaining = half.clone();

            boolean prefixPossible = true;

            // Match target before pos
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

            // Try the smallest character greater than target[pos]
            for (int c = targetChar + 1; c < 26; c++) {

                if (remaining[c] == 0) {
                    continue;
                }

                char[] newHalf = new char[halfLen];

                // Copy prefix
                for (int i = 0; i < pos; i++) {
                    newHalf[i] = target.charAt(i);
                }

                // Make this position greater
                newHalf[pos] = (char) ('a' + c);

                remaining[c]--;

                // Fill rest with smallest characters
                int index = pos + 1;

                for (int x = 0; x < 26; x++) {
                    while (remaining[x] > 0) {
                        newHalf[index++] = (char) ('a' + x);
                        remaining[x]--;
                    }
                }

                String candidate =
                        build(newHalf, middle, n);

                if (candidate.compareTo(target) > 0) {
                    return candidate;
                }
            }
        }

        return "";
    }

    private String build(
            char[] half,
            int middle,
            int n) {

        StringBuilder result = new StringBuilder();

        // First half
        for (char ch : half) {
            result.append(ch);
        }

        // Middle character
        if (n % 2 == 1) {
            result.append((char) ('a' + middle));
        }

        // Reverse first half
        for (int i = half.length - 1; i >= 0; i--) {
            result.append(half[i]);
        }

        return result.toString();
    }
}