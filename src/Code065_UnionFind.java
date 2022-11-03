import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class Code065_UnionFind {
    // 并查集
    /*
    需求:
    有若干个样本a、b、c、d…类型假设是V
    在并查集中一开始认为每个样本都在单独的集合里
    用户可以在任何时候调用如下两个方法 ：
    boolean isSameSet(V x, V y) : 查询样本x和样本y是否属于一个集合
    void union(V x, V y) : 把x和y各自所在集合的所有样本合并成一个集合
    isSameSet和union方法的代价越低越好

    实现:
    1）每个节点都有一条往上指的指针
    2）节点a往上找到的头节点，叫做a所在集合的代表节点
    3）查询x和y是否属于同一个集合，就是看看找到的代表节点是不是一个
    4）把x和y各自所在集合的所有点合并成一个集合，只需要小集合的代表点挂在大集合的代表点的下方即可

    优化:
    1）节点往上找代表点的过程，把沿途的链变成扁平的
    2）小集合挂在大集合的下面
    3）如果方法调用很频繁，那么单次调用的代价为O(1)，两个方法都如此

    应用:
    1) isSameSet 连通性的问题
    2) union 解决两大块区域的合并问题
    3) 常用在图等领域中
     */

    public static class Node<V> {
        V value;

        public Node(V value) {
            this.value = value;
        }
    }

    public static class UnionFind<V> {
        public HashMap<V, Node<V>> nodes;
        public HashMap<Node<V>, Node<V>> parents;
        public HashMap<Node<V>, Integer> sizeMap;

        public UnionFind(List<V> values) {
            nodes = new HashMap<>();
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
            for (V cur : values) {
                Node<V> node = new Node<>(cur);
                nodes.put(cur, node);
                parents.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        // 输入一个节点,返回其并查集代表节点
        public Node<V> findRepresentative(Node<V> cur) {
            // 节点往上找代表点的过程，把沿途的链变成扁平的
            Stack<Node<V>> path = new Stack<>();
            while (cur != parents.get(cur)) {
                path.push(cur);
                cur = parents.get(cur);
            }
            // isEmpty是Vector类的方法,加了synchronized
            // empty是Stack类的方法,没有加synchronized
            while (!path.isEmpty()) {
                parents.put(path.pop(), cur);
            }
            return cur;
        }

        // 是否在同一并查集
        public boolean isSameSet(V a, V b) {
            return findRepresentative(nodes.get(a)) == findRepresentative(nodes.get(b));
        }

        // 合并两个并查集
        public void union(V a, V b) {
            Node<V> aRepresentative = findRepresentative(nodes.get(a));
            Node<V> bRepresentative = findRepresentative(nodes.get(a));
            if (aRepresentative != bRepresentative) {
                // 小集合挂在大集合的下面
                int aSetSize = sizeMap.get(aRepresentative);
                int bSetSize = sizeMap.get(bRepresentative);
                Node<V> big = aSetSize > bSetSize ? aRepresentative : bRepresentative;
                Node<V> small = big == bRepresentative ? aRepresentative : bRepresentative;
                parents.put(small, big);
                sizeMap.put(big, aSetSize + bSetSize);
                sizeMap.remove(small);
            }
        }

        // 有多少个并查集
        public int sets() {
            return sizeMap.size();
        }

    }

}
