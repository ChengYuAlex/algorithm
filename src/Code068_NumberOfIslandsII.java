import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Code068_NumberOfIslandsII {
    // 岛的数量2
    // 测试链接：https://leetcode.com/problems/number-of-islands-ii/
    /*
    假设你设计一个游戏，用一个 m 行 n 列的 2D 网格来存储你的游戏地图。
    起始的时候，每个格子的地形都被默认标记为「水」。
    我们可以通过使用 addLand 进行操作，将位置 (row, col) 的「水」变成「陆地」。
    你将会被给定一个列表，来记录所有需要被操作的位置，然后你需要返回计算出来 每次 addLand 操作后岛屿的数量。
    注意：一个岛的定义是被「水」包围的「陆地」，通过水平方向或者垂直方向上相邻的陆地连接而成。
    你可以假设地图网格的四边均被无边无际的「水」所包围。
    请仔细阅读下方示例与解析，更加深入了解岛屿的判定。
    示例:
    输入: m = 3, n = 3, positions = [[0,0], [0,1], [1,2], [2,1]]
    输出: [1,1,2,3]
    解析:
    起初，二维网格 grid 被全部注入「水」。（0 代表「水」，1 代表「陆地」）
    0 0 0
    0 0 0
    0 0 0
    操作 #1：addLand(0, 0) 将 grid[0][0] 的水变为陆地。
    1 0 0
    0 0 0   Number of islands = 1
    0 0 0
    操作 #2：addLand(0, 1) 将 grid[0][1] 的水变为陆地。
    1 1 0
    0 0 0   岛屿的数量为 1
    0 0 0
    操作 #3：addLand(1, 2) 将 grid[1][2] 的水变为陆地。
    1 1 0
    0 0 1   岛屿的数量为 2
    0 0 0
    操作 #4：addLand(2, 1) 将 grid[2][1] 的水变为陆地。
    1 1 0
    0 0 1   岛屿的数量为 3
    0 1 0
    拓展：
    你是否能在 O(k log mn) 的时间复杂度程度内完成每次的计算？（k 表示 positions 的长度）
     */

    public static List<Integer> numIslands21(int m, int n, int[][] positions) {
        UnionFind1 uf = new UnionFind1(m, n);
        List<Integer> ans = new ArrayList<>();
        for (int[] position : positions) {
            ans.add(uf.connect(position[0], position[1]));
        }
        return ans;
    }

    public static class UnionFind1 {
        private int[] parent;
        private int[] size;
        private int[] help;
        private final int row;
        private final int col;
        private int sets;

        public UnionFind1(int m, int n) {
            row = m;
            col = n;
            sets = 0;
            int len = row * col;
            parent = new int[len];
            size = new int[len];
            help = new int[len];
        }

        private int index(int r, int c) {
            return r * col + c;
        }

        private int find(int i) {
            int hi = 0;
            while (i != parent[i]) {
                help[hi++] = i;
                i = parent[i];
            }
            for (hi--; hi >= 0; hi--) {
                parent[help[hi]] = i;
            }
            return i;
        }

        private void union(int r1, int c1, int r2, int c2) {
            if (r1 < 0 || r1 == row || r2 < 0 || r2 == row || c1 < 0 || c1 == col || c2 < 0 || c2 == col) {
                return;
            }
            int i1 = index(r1, c1);
            int i2 = index(r2, c2);
            if (size[i1] == 0 || size[i2] == 0) {
                return;
            }
            int f1 = find(i1);
            int f2 = find(i2);
            if (f1 != f2) {
                if (size[f1] >= size[f2]) {
                    size[f1] += size[f2];
                    parent[f2] = f1;
                } else {
                    size[f2] += size[f1];
                    parent[f1] = f2;
                }
                sets--;
            }
        }

        public int connect(int r, int c) {
            int index = index(r, c);
            if (size[index] == 0) {
                parent[index] = index;
                size[index] = 1;
                sets++;
                union(r - 1, c, r, c);
                union(r + 1, c, r, c);
                union(r, c - 1, r, c);
                union(r, c + 1, r, c);
            }
            return sets;
        }

    }

    // 如果m*n比较大, 会经历很重的初始化, 而k比较小, 优化方法
    public static List<Integer> numIslands22(int m, int n, int[][] positions) {
        UnionFind2 uf = new UnionFind2();
        List<Integer> ans = new ArrayList<>();
        for (int[] position : positions) {
            ans.add(uf.connect(position[0], position[1]));
        }
        return ans;
    }

    public static class UnionFind2 {
        private HashMap<String, String> parent;
        private HashMap<String, Integer> size;
        private ArrayList<String> help;
        private int sets;

        public UnionFind2() {
            parent = new HashMap<>();
            size = new HashMap<>();
            help = new ArrayList<>();
            sets = 0;
        }

        private String find(String cur) {
            while (!cur.equals(parent.get(cur))) {
                help.add(cur);
                cur = parent.get(cur);
            }
            for (String str : help) {
                parent.put(str, cur);
            }
            help.clear();
            return cur;
        }

        private void union(String s1, String s2) {
            if (parent.containsKey(s1) && parent.containsKey(s2)) {
                String f1 = find(s1);
                String f2 = find(s2);
                if (!f1.equals(f2)) {
                    int size1 = size.get(f1);
                    int size2 = size.get(f2);
                    String big = size1 >= size2 ? f1 : f2;
                    String small = big == f1 ? f2 : f1;
                    parent.put(small, big);
                    size.put(big, size1 + size2);
                    sets--;
                }
            }
        }

        public int connect(int r, int c) {
            String key = String.valueOf(r) + "_" + String.valueOf(c);
            if (!parent.containsKey(key)) {
                parent.put(key, key);
                size.put(key, 1);
                sets++;
                String up = String.valueOf(r - 1) + "_" + String.valueOf(c);
                String down = String.valueOf(r + 1) + "_" + String.valueOf(c);
                String left = String.valueOf(r) + "_" + String.valueOf(c - 1);
                String right = String.valueOf(r) + "_" + String.valueOf(c + 1);
                union(up, key);
                union(down, key);
                union(left, key);
                union(right, key);
            }
            return sets;
        }

    }
}
