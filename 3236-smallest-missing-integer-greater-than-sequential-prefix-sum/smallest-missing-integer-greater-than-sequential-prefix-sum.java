class Solution {
    public int missingInteger(int[] nums) {

        // Step 1: Find the sum of the longest sequential prefix
        int sum = nums[0];

        for (int i = 1; i < nums.length; i++) {

            // Check if current number is previous number + 1
            if (nums[i] == nums[i - 1] + 1) {
                sum += nums[i];
            } else {
                break;
            }
        }

        // Step 2: Find the smallest missing number >= sum
        int answer = sum;

        while (true) {

            boolean found = false;

            // Check whether answer exists in nums
            for (int num : nums) {
                if (num == answer) {
                    found = true;
                    break;
                }
            }

            // If answer is not present, we found our answer
            if (!found) {
                return answer;
            }

            // Otherwise check the next number
            answer++;
        }
    }
}