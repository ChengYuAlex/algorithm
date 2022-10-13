public class Code008_Swap {
//    如何不用额外变量交换两个数
    public static void swap(int[] arr, int i,int j){
        arr[i]=arr[i]+arr[j];
        arr[j]=arr[i]-arr[j];
        arr[i]=arr[i]-arr[j];
//        arr[i]=arr[i]^arr[j];
//        arr[j]=arr[i]^arr[j];
//        arr[i]=arr[i]^arr[j];
    }

    public static void main(String[] args) {
        int a = 16;
        int b = 603;

        System.out.println(a);
        System.out.println(b);

        a = a ^ b;
        b = a ^ b;
        a = a ^ b;

        System.out.println(a);
        System.out.println(b);
        System.out.println("==================");
        int[] arr = {3, 1, 3};

        int i = 0;
        int j = 0;

//        arr[i] = arr[i] ^ arr[j];
//        arr[j] = arr[i] ^ arr[j];
//        arr[i] = arr[i] ^ arr[j];

        System.out.println(arr[i] + " , " + arr[j]);

        System.out.println(arr[0]);
        System.out.println(arr[2]);

        swap(arr, 0, 0);

        System.out.println(arr[0]);
        System.out.println(arr[2]);
    }

}
