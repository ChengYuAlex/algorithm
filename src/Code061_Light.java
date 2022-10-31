import java.util.HashSet;

public class Code061_Light {
    // 点亮str中所有需要点亮的位置至少需要几盏灯
    /*
    给定一个字符串str，只由‘X’和‘.’两种字符构成。
    ‘X’表示墙，不能放灯，也不需要点亮
    ‘.’表示居民点，可以放灯，需要点亮
    如果灯放在i位置，可以让i-1，i和i+1三个位置被点亮
    返回如果点亮str中所有需要点亮的位置，至少需要几盏灯
    例如: 'X...X' 返回1; 'X.X.' 返回2; 'X....X'返回2;
     */
    public static int minLight1(String road) {
        if (road == null || road.length() == 0) {
            return 0;
        }
        return process(road.toCharArray(), 0, new HashSet<>());
    }

    // 递归序
    // str[index....]位置，自由选择放灯还是不放灯
    // str[0..index-1]位置呢？已经做完决定了，那些放了灯的位置，存在lights里
    // 要求选出能照亮所有.的方案，并且在这些有效的方案中，返回最少需要几个灯
    public static int process(char[] str, int index, HashSet<Integer> lights) {
        if (index == str.length) { // 结束的时候, 校验'.'是否全部点亮
            for (int i = 0; i < str.length; i++) {
                if (str[i] != 'X') { // 当前位置是点的话
                    if (!lights.contains(i - 1) && !lights.contains(i) && !lights.contains(i + 1)) {
                        return Integer.MAX_VALUE;
                    }
                }
            }
            return lights.size();
        } else { // str还没结束
            // i X .
            // 不放灯
            int no = process(str, index + 1, lights);
            // 想放灯
            int yes = Integer.MAX_VALUE; // 默认是'X', 不能放灯
            if (str[index] == '.') { // 是'.', 放灯
                lights.add(index);
                yes = process(str, index + 1, lights);
                lights.remove(index);
                // 例如'...'运行顺序(返回结果)是 二叉树的递归序
                //               N                             Y
                //       NN            NY              YN            YY
                // NNN(M),NNY(M)  NYN(1),NYY(2)  YNN(M),YNY(2)  YYN(2),YYY(3)
                // 先运行NYY(2), 不lights.remove(index)的话, 右边结果会全部变成YYY(3).
            }
            return Math.min(no, yes);
        }
    }

    // 从前往后推
    public static int minLight2(String road) {
        char[] str = road.toCharArray();
        int i = 0;
        int light = 0;
        while (i < str.length) {
            if (str[i] == 'X') {
                i++;
            } else { // str[i]=='.'
                light++; // 有一个点, 就必须有一盏灯照亮这个位置
                if (i + 1 == str.length) { // 空指针
                    break;
                } else { // 有i+1位置 i+1 X .
                    if (str[i + 1] == 'X') {
                        i = i + 2;
                    } else {
                        i = i + 3;
                    }
                }
            }
        }
        return light;
    }

    // 以'X'来进行分段
    // 更简洁的解法
    // 两个X之间，数一下.的数量，然后除以3，向上取整
    // 把灯数累加
    public static int minLight3(String road) {
        char[] str = road.toCharArray();
        int cur = 0;
        int light = 0;
        for (char c : str) {
            if (c == 'X') {
                light += (cur + 2) / 3;
                cur = 0;
            } else { // c == '.'
                cur++;
            }
        }
        light += (cur + 2) / 3; // 防止最后一个不是'X'漏算
        return light;
    }

    // for test
    public static String randomString(int len) {
        char[] res = new char[(int) (Math.random() * len) + 1];
        for (int i = 0; i < res.length; i++) {
            res[i] = Math.random() < 0.5 ? 'X' : '.';
        }
        return String.valueOf(res);
    }

    public static void main(String[] args) {
        int len = 20;
        int testTime = 100000;
        for (int i = 0; i < testTime; i++) {
            String test = randomString(len);
            int ans1 = minLight1(test);
            int ans2 = minLight2(test);
            int ans3 = minLight3(test);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("oops!");
            }
        }
        System.out.println("finish!");
    }
}
