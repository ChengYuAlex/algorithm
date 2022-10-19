public class Code047_PrintBinaryTree {
    //打印二叉树
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static void printTree(Node head) {
        System.out.println("Binary Tree:");
        printInOrder(head, 0, "H", 17);
        System.out.println();
    }

    /**
     *
     * @param head 传入头节点
     * @param height 头节点高度
     * @param to 节点间父子关系标识
     * @param len 每个节点打印所占宽度
     */
    public static void printInOrder(Node head, int height, String to, int len) {
        if (head == null) {
            return;
        }
        // 迭代 最先打印最右的节点
        printInOrder(head.right, height + 1, "v", len);
        // 当前节点打印的标识加上节点值
        String val = to + head.value + to;
        // 有意义字符所占宽度
        int lenM = val.length();
        // 左边空白字符宽度
        int lenL = (len - lenM) / 2;
        // 右边空白字符宽度
        int lenR = len - lenM - lenL;
        // 当前节点的输出
        val = getSpace(lenL) + val + getSpace(lenR);
        // getSpace(height*len) 根据节点层级, 确定前面加上多少空格
        System.out.println(getSpace(height * len) + val);
        // 迭代 最后打印最左节点
        printInOrder(head.left, height + 1, "^", len);
    }

    public static String getSpace(int num) {
        String space = " ";
        StringBuffer buf = new StringBuffer("");
        for (int i = 0; i < num; i++) {
            buf.append(space);
        }
        return buf.toString();
    }
    public static void main(String[] args) {
        Node head = new Node(1);
        head.left = new Node(-222222222);
        head.right = new Node(3);
        head.left.left = new Node(Integer.MIN_VALUE);
        head.right.left = new Node(55555555);
        head.right.right = new Node(66);
        head.left.left.right = new Node(777);
        printTree(head);

        head = new Node(1);
        head.left = new Node(2);
        head.right = new Node(3);
        head.left.left = new Node(4);
        head.right.left = new Node(5);
        head.right.right = new Node(6);
        head.left.left.right = new Node(7);
        printTree(head);

        head = new Node(1);
        head.left = new Node(1);
        head.right = new Node(1);
        head.left.left = new Node(1);
        head.right.left = new Node(1);
        head.right.right = new Node(1);
        head.left.left.right = new Node(1);
        printTree(head);

    }

}
