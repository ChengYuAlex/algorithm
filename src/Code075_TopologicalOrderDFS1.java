import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;

// OJ链接：https://www.lintcode.com/problem/topological-sorting

// 创建包含深度deep的包装类
// 编写降序的比较器
// A的深度>B的深度 --> A的拓扑序在B之前或同等
// f()得到所有点的深度, 缓存表
// Array排序 put到ans表 输出
public class Code075_TopologicalOrderDFS1 {
    // 不要提交这个类
    // No need to submit
    public static class DirectedGraphNode {
        public int label;
        public ArrayList<DirectedGraphNode> neighbors;

        public DirectedGraphNode(int x) {
            label = x;
            neighbors = new ArrayList<DirectedGraphNode>();
        }
    }

    // 提交下面的
    // Submit the following
    public static class Record {
        public DirectedGraphNode node;
        public int deep;

        public Record(DirectedGraphNode n, int o) {
            node = n;
            deep = o;
        }
    }

    public static class MyComparator implements Comparator<Record> {

        @Override
        public int compare(Record o1, Record o2) {
            return o2.deep - o1.deep;
        }
    }

    public static ArrayList<DirectedGraphNode> topSort(ArrayList<DirectedGraphNode> graph) {
        HashMap<DirectedGraphNode, Record> order = new HashMap<>();
        for (DirectedGraphNode cur : graph) {
            f(cur, order);
        }
        ArrayList<Record> recordArr = new ArrayList<>();
        for (Record r : order.values()) {
            recordArr.add(r);
        }
        recordArr.sort(new MyComparator());
        ArrayList<DirectedGraphNode> ans = new ArrayList<DirectedGraphNode>();
        for (Record r : recordArr) {
            ans.add(r.node);
        }
        return ans;
    }

    public static Record f(DirectedGraphNode cur, HashMap<DirectedGraphNode, Record> order) {
        if (order.containsKey(cur)) {
            return order.get(cur);
        }
        int follow = 0;
        for (DirectedGraphNode next : cur.neighbors) {
            follow = Math.max(follow, f(next, order).deep);
        }
        Record ans = new Record(cur, follow + 1);
        order.put(cur, ans);
        return ans;
    }


}
