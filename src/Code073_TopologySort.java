import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Code073_TopologySort {
    // 有向图且无环
    public static List<Node> sortedTopology(Graph graph) {
        // k-v 节点-其入度
        HashMap<Node, Integer> inMap = new HashMap<>();
        // 剩余入度为零 进入这个队列
        Queue<Node> zeroInQueue = new LinkedList<>();
        // 这里graph.nodes 是 HashMap<Integer, Node> nodes
        for (Node node : graph.nodes.values()) {
            inMap.put(node, node.in);
            if (node.in == 0) {
                zeroInQueue.add(node);
            }
        }
        List<Node> result = new ArrayList<>();
        while (!zeroInQueue.isEmpty()) {
            Node cur = zeroInQueue.poll();
            result.add(cur);
            for (Node next : cur.nexts) {
                inMap.put(next, inMap.get(next) - 1);
                if (inMap.get(next) == 0) {
                    zeroInQueue.add(next);
                }
            }
        }
        return result;
    }
}
