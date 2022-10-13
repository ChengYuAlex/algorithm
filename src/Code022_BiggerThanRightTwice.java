public class Code022_BiggerThanRightTwice {
    //  本题测试链接 : https://leetcode.com/problems/reverse-pairs/
//    求数组中的大两倍数对的数量
//    思路：站在左组一个数，看右组有几个数的两倍小于左数
    public static int reversePairs(int[] arr) {
        if (arr == null || arr.length < 2) {
            return 0;
        }
        return process(arr, 0, arr.length - 1);
    }

    public static int process(int[] arr, int l, int r) {
        if (l == r) {
            return 0;
        }
        // l < r
        int mid = l + ((r - l) >> 1);
        return process(arr, l, mid)
                + process(arr, mid + 1, r)
                + merge(arr, l, mid, r);
    }

    public static int merge(int[] arr, int L, int m, int r) {
        // [L...M] [M+1...R]
        int ans = 0;
        //目前囊括进来的数，是从[M+1...windowR]
        int windowR = m + 1;
        // O（N）
        for (int i = L; i <= m; i++) {
            while (windowR <= r && (long) arr[i] > (long) arr[windowR] * 2) {
                windowR++;
            }
            ans += windowR - m - 1;
        }


        int[] help = new int[r - L + 1];
        int i = 0;
        int p1 = L;
        int p2 = m + 1;
        // O（N）
        while (p1 <= m && p2 <= r){
            help[i++] = arr[p1] <= arr[p2] ? arr[p1++] : arr[p2++];
        }
        while (p1<= m){
            help[i++] = arr[p1++];
        }
        while(p2 <=r){
            help[i++] = arr[p2++];
        }
        for (i = 0; i < help.length ; i++) {
            arr[L+i] = help[i];
        }
        return ans;
    }

}
