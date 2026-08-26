class Solution {
    public String shortestBeautifulSubstring(String s, int k) {
        String res = null;
        int minSize = Integer.MAX_VALUE;

        for(int i = 0; i < s.length(); i++) {
            int cnt = 0;
            for(int j = i; j < s.length() && j - i < minSize; j++) {
                if(s.charAt(j) == '1') cnt++;
                if(cnt == k) {
                    System.out.println(res);
                    String sub = s.substring(i, j + 1);
                    if(res == null || sub.length() < res.length() || (sub.length() == res.length() && res.compareTo(sub) > 0)) {
                        res = sub;
                        minSize = sub.length();
                        break;
                    } 
                }
            }
        }

        return res == null ? "" : res;
    }
}