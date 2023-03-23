import java.util.HashSet;
import java.util.Stack;

public class Code072_GraphDFS {
    // 一条路没走完就走到尽头
    // 走到尽头了就往上看看那个位置还没走完
    public static void dfs(Node start){
        if (start==null){
            return;
        }
        Stack<Node> stack = new Stack<>();
        HashSet<Node> set= new HashSet<>();
        stack.add(start);
        set.add(start);
        // 进栈打印
        System.out.println(start.value);
        while (!stack.isEmpty()){
            Node cur = stack.pop();
            for (Node next:cur.nexts){
                if (!set.contains(next)){
                    // 栈里面永远保存着一条路径, 所以要存cur
                    // 例如a→b, a→c. 一开始next到b就会栈, 然后继续找b的next.
                    stack.push(cur);
                    stack.push(next);
                    set.add(next);
                    System.out.println(next.value);
                    break;
                }
            }
        }
    }
}
