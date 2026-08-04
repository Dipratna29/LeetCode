class Solution {
    public List<Integer> findMissingElements(int[] nums) {
        List<Integer> ans = new ArrayList<>();

        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for(int num : nums){
            min = Math.min(min, num);
            max = Math.max(max, num);
        }

        for(int i= min; i<=max; i++){
            boolean found = false;

            for (int num : nums){
                if(num == i){
                    found = true;
                    break;
                }
            }

            if(!found){
                ans.add(i);
            }
        }

        return ans;
        
    }
}