public class Code023_CountOfRangeSum {
//    区间和达标的子数组数量
//    给定一个数组arr，和两个整数a和b（a<=b）求arr中有多少个子数组，
//    累加和在[a,b]这个范围上，返回达标的子数组数量
//    https://leetcode.com/problems/count-of-range-sum/
//    思路： 问题转换， 先以原数组造前缀和数组，
//    把问题满足条件[lower,upper]的子数组[i,j]，转换成满足新条件[x-upper,x-lower]的前缀和数组值sum[j]

    public static int countRangeSum(int[] nums, int lower, int upper) {
        if (nums == null || nums.length == 0) {
            return 0;
        }
        long[] sum = new long[nums.length];
        sum[0] = nums[0];
        for (int i = 1; i < nums.length; i++) {
            sum[i] = sum[i - 1] + nums[i];
        }
        return process(sum, 0, sum.length - 1, lower, upper);
    }

    public static int process(long[] sum, int L, int R, int lower, int upper) {
        if (L == R) {
            return sum[L] >= lower && sum[L] <= upper ? 1 : 0;
        }
        int M = L + ((R - L) >> 1);
        return process(sum, L, M, lower, upper)
                + process(sum, M + 1, R, lower, upper)
                + merge(sum, L, M, R, lower, upper);
    }

    public static int merge(long[] arr, int L, int M, int R, int lower, int upper) {
        int ans = 0;
        int windowL = L;
        int windowR = L;
        // [windowL, windowR)
        // 站在右组的一个数，看左组符合条件的。
        for (int i = M + 1; i <= R; i++) {
            long min = arr[i] - upper;
            long max = arr[i] - lower;
            while (windowR <= M && arr[windowR] <= max) {
                windowR++;
            }
            while (windowL <= M && arr[windowL] < min) {
                windowL++;
            }
            ans += windowR - windowL;
        }

        long[] help = new long[R - L + 1];
        int i = 0;
        int p1 = L;
        int p2 = M + 1;
        while (p1 <= M && p2 <= R){
            help[i++] = arr[p1]<=arr[p2]?arr[p1++] :arr[p2++];
        }
        while (p1<=M){
            help[i++] = arr[p1++];
        }
        while (p2<=R){
            help[i++] = arr[p2++];
        }
        for (i = 0;  i< help.length ; i++) {
            arr[L+i] = help[i];
        }
        return ans;

    }

}
