import java.util.Collection;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Set;
import java.util.Stack;

// A minimum spanning tree (MST) or
// minimum weight spanning tree is
// a subset of the edges of a connected,
// edge-weighted undirected graph
// that connects all the vertices together,
// without any cycles and
// with the minimum possible total edge weight.

//undirected graph only
public class Code077_Kruskal {

    // union-find set
    public static class UnionFind {
        // key 某一个节点, value key节点往上的节点
        private HashMap<Node, Node> fatherMap;
        // key 某一个集合的代表节点, value key所在集合的节点个数
        private HashMap<Node, Integer> sizeMap;

        public UnionFind() {
            fatherMap = new HashMap<Node, Node>();
            sizeMap = new HashMap<Node, Integer>();
        }

        public void makeSets(Collection<Node> nodes) {
            fatherMap.clear();
            sizeMap.clear();
            for (Node node : nodes) {
                fatherMap.put(node, node);
                sizeMap.put(node, 1);
            }
        }

        private Node findFather(Node n) {
            Stack<Node> path = new Stack<>();
            while (n != fatherMap.get(n)) {
                path.add(n);
                n = fatherMap.get(n);
            }
            while (!path.isEmpty()) {
                fatherMap.put(path.pop(), n);
            }
            return n;
        }

        public boolean isSameSet(Node a, Node b) {
            return findFather(a) == findFather(b);
        }

        public void union(Node a, Node b) {
            if (a == null || b == null) {
                return;
            }
            Node aDai = findFather(a);
            Node bDai = findFather(b);
            if (aDai != bDai) {
                int aSetSize = sizeMap.get(aDai);
                int bSetSize = sizeMap.get(bDai);
                if (aSetSize <= bSetSize) {
                    fatherMap.put(aDai, bDai);
                    sizeMap.put(bDai, aSetSize + bSetSize);
                    sizeMap.remove(aDai);
                } else {
                    fatherMap.put(bDai, aDai);
                    sizeMap.put(aDai, aSetSize + bSetSize);
                    sizeMap.remove(bDai);
                }
            }
        }
    }

    public static class EdgeComparator implements Comparator<Edge> {
        @Override
        public int compare(Edge o1, Edge o2) {
            return o1.weight - o2.weight;
        }
    }


    // 总是从权值最小的边开始考虑, 依次考察权值依次变大的边
    // 当前的边要么进入最小生成树的集合, 要么丢弃
    // 如果当前的边进入最小生成树的集合不会形成环, 就要当前边
    // 如果当前的边进入最小生成树的集合中会形成环, 就不要当前边
    // 考察完所有的边之后, 最小生成树的集合也得到了
    public static Set<Edge> kruskalMST(Graph graph){
        UnionFind unionFind = new UnionFind();
        unionFind.makeSets(graph.nodes.values());
        // 从小的边到大的边, 依次弹出, 小根堆
        PriorityQueue<Edge> priorityQueue = new PriorityQueue<>(new EdgeComparator());
        for (Edge edge: graph.edges){ // M 条边
            priorityQueue.add(edge); // O(logM)
        }
        Set<Edge> result = new HashSet<>();
        while (!priorityQueue.isEmpty()){ // M 条边
            Edge edge = priorityQueue.poll(); // O(lonM)
            if (!unionFind.isSameSet(edge.from,edge.to)){ // O(1)
                result.add(edge);
                unionFind.union(edge.from,edge.to);
            }
        }
        return result;
    }
}
