class Solution {
    int[][] dp;
    int[] suffix;
    int n;

    int solve(int i, int M) {
        if (i >= n) {
            return 0;
        }

        if (2 * M >= n - i) {
            return suffix[i];
        }

        if (dp[i][M] != -1) {
            return dp[i][M];
        }

        int best = 0;

        for (int X = 1; X <= 2 * M; X++) {
            int opponent = solve(
                i + X,
                Math.max(M, X)
            );

            int current = suffix[i] - opponent;

            best = Math.max(best, current);
        }

        dp[i][M] = best;

        return best;
    }

    public int stoneGameII(int[] piles) {
        n = piles.length;

        suffix = new int[n + 1];

        for (int i = n - 1; i >= 0; i--) {
            suffix[i] = suffix[i + 1] + piles[i];
        }

        dp = new int[n][n + 1];

        for (int i = 0; i < n; i++) {
            for (int j = 0; j <= n; j++) {
                dp[i][j] = -1;
            }
        }

        return solve(0, 1);
    }
}