import java.util.ArrayList;
public class Code052_IsBST {
    // 是否是二叉搜索树
    // 二叉搜索树: 中序遍历, 递增
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static boolean isBST1(Node head) {
        // 中序遍历进ArrayList, 循环比较
        if (head == null) {
            return true;
        }
        ArrayList<Node> arr = new ArrayList<>();
        in(head, arr);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).value <= arr.get(i - 1).value) {
                return false;
            }
        }
        return true;
    }

    public static void in(Node head, ArrayList<Node> arr) {
        if (head == null) {
            return;
        }
        in(head.left, arr);
        arr.add(head);
        in(head.right, arr);
    }

    public static boolean isBST2(Node head) {
        if (head == null) {
            return true;
        }
        return process(head).isBST;
    }

    public static class Info {
        public boolean isBST;
        public int min;
        public int max;

        public Info(boolean bst, int min, int max) {
            this.isBST = bst;
            this.min = min;
            this.max = max;
        }
    }

    public static Info process(Node x) {
        // 判断为空的情况, 如果不好赋值Info, 则赋null, 交给后面判断
        if (x == null) {
            return null;
        }

        // 拿到左右两边的Info
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);

        // 形成节点的Info
        int min = x.value;
        if (leftInfo != null) {
            min = Math.min(min, leftInfo.min);
        }
        if (rightInfo != null) {
            min = Math.min(min, rightInfo.min);
        }

        int max = x.value;
        if (leftInfo != null) {
            max = Math.max(max, leftInfo.max);
        }
        if (rightInfo != null) {
            max = Math.max(max, rightInfo.max);
        }

        boolean isBST = true;
        if (leftInfo != null
                && leftInfo.max >= x.value) {
            isBST = false;
        }
        if (rightInfo != null
                && rightInfo.min <= x.value) {
            isBST = false;
        }
        if (leftInfo != null
                && !leftInfo.isBST) {
            isBST = false;
        }
        if (rightInfo != null
                && !rightInfo.isBST) {
            isBST = false;
        }
        return new Info(isBST, min, max);
    }

    // for test
    public static Node generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static Node generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        Node head = new Node((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isBST1(head) != isBST2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
