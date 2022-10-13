public class Code018_GetMax {
    //    用递归方法求数组中的最大值
    public static int getMax(int[] arr) {
        return process(arr, 0, arr.length - 1);
    }

    public static int process(int[] arr, int L, int R) {
        // [L...R] 只有一个值
        if (L == R) {
            return arr[L];
        }
        // [L...R] 不只一个值
        int mid = L + ((R - L) >> 1); //中点
        int leftMax = process(arr, L, mid);
        int rightMax = process(arr, mid + 1, R);
        return Math.max(leftMax, rightMax);
    }
}
