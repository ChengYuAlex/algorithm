import java.sql.Array;

public class Code066_FriendCircles {
    // 省份的数量(朋友圈)
    // 有 n 个城市，其中一些彼此相连，另一些没有相连。
    // 如果城市 a 与城市 b 直接相连，
    // 且城市 b 与城市 c 直接相连，
    // 那么城市 a 与城市 c 间接相连。
    // 省份 是一组直接或间接相连的城市，组内不含其他没有相连的城市。
    // 给你一个 n x n 的矩阵 isConnected ，
    // 其中 isConnected[i][j] = 1 表示第 i 个城市和第 j 个城市直接相连，
    // 而 isConnected[i][j] = 0 表示二者不直接相连。
    // 返回矩阵中 省份 的数量。
    // 测试链接：https://leetcode.com/problems/number-of-provinces/

    public static int findCircleNum(int[][] M) {
        int N = M.length;
        UnionFind unionFind = new UnionFind(N);
        for (int i = 0; i < N; i++) {
            for (int j = i + 1; j < N; j++) {
                if (M[i][j] == 1) { // i和j直接相连
                    unionFind.union(i, j);
                }
            }
        }
        return unionFind.sets();
    }

    // 使用数组实现并查集, 复杂度一样O(1),寻址更快
    public static class UnionFind {
        // parent[i] = k ： i的父亲是k
        private int[] parent;
        // size[i] = k ： 如果i是代表节点，size[i]才有意义，否则无意义
        // i所在的集合大小是多少
        private int[] size;
        // 记录沿途节点, 压缩路径用
        private int[] help;
        // 一共几个集合
        private int sets;

        public UnionFind(int N) {
            this.parent = new int[N];
            this.size = new int[N];
            this.help = new int[N];
            this.sets = N;
            for (int i = 0; i < N; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        // 从i开始一直往上，往上到不能再往上，代表节点，返回
        // 这个过程要做路径压缩
        private int find(int i) {
            int helpIndex = 0;
            while (i != parent[i]) {
                help[helpIndex++] = i;
                i = parent[i];
            }
            // helpIndex 存了头节点所以 helpIndex--
            for (helpIndex--; helpIndex >= 0; helpIndex--) {
                parent[help[helpIndex]] = i;
            }
            return i;
        }

        public void union(int i, int j) {
            int f1 = find(i);
            int f2 = find(j);
            if (f1 != f2) {
                int big = size[f1] > size[f2] ? f1 : f2;
                int small = big == f2 ? f1 : f2;
                parent[small] = big;
                size[big] += size[small];
                sets--;
            }
        }

        public int sets() {
            return sets;
        }
    }

}
