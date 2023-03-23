import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class Code071_GraphBFS {
//     从node出发, 进行宽度优先遍历
    // 存起点到set
    // 入队
    // 出队
    // sout
    // nexts判断是否在set里
    // 若不在set里 若在set里, 说明已经输出过了pass
    // 存该点到set
    // 该点入队
    public static void bfs(Node start) {
        if (start == null) {
            return;
        }
        Queue<Node> queue = new LinkedList<>();
        HashSet<Node> set = new HashSet<>();
        queue.add(start);
        set.add(start);
        while (!queue.isEmpty()){
            Node cur = queue.poll();
            System.out.println(cur.value);
            for (Node next : cur.nexts){
                if (!set.contains(next)){
                    set.add(next);
                    queue.add(next);
                }
            }
        }
    }
}
