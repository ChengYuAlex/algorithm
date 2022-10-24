public class Code053_IsBalanced {
    // 二叉树是否平衡
    // 左右子树高度相差不超过 1;
    public static class Node {
        public int value;
        public Node left;
        public Node right;

        public Node(int data) {
            this.value = data;
        }
    }

    public static boolean isBalanced1(Node head) {
//      为什么不能 boolean isBalanced = true; ?
//      因为使用boolean类型的话, process1方法里面的isBalanced有效范围只在方法内,
//      无法影响到isBalanced1方法里的isBalanced.
        boolean[] isBalanced = new  boolean[1];
        isBalanced[0]=true;
        process1(head, isBalanced);
        return isBalanced[0];
    }

    public static int process1(Node head, boolean[] isBalanced) {
        if (head == null) {
            return 0;
        }
        int leftHeight = process1(head.left, isBalanced);
        int rightHeight = process1(head.right, isBalanced);
        if (Math.abs(leftHeight - rightHeight) > 1) {
            isBalanced[0] = false;
        }
        return Math.max(leftHeight, rightHeight) + 1;
    }

    public static boolean isBalanced2(Node head) {
        return process2(head).isBalanced;
    }

    public static class Info {
        boolean isBalanced;
        int height;

        public Info(boolean isBalanced, int height) {
            this.isBalanced = isBalanced;
            this.height = height;
        }
    }

    public static Info process2(Node x) {
        if (x == null) {
            return new Info(true, 0);
        }

        Info leftInfo = process2(x.left);
        Info rightInfo = process2(x.right);

        boolean isBalanced = true;
        if (!leftInfo.isBalanced
                || !rightInfo.isBalanced) {
            isBalanced = false;
        }

        if (Math.abs(leftInfo.height-rightInfo.height) > 1) {
            isBalanced = false;
        }

        int height = Math.max(leftInfo.height, rightInfo.height) + 1;

        return new Info(isBalanced, height);
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
        int maxLevel = 5;
        int maxValue = 100;
        int testTimes = 10000;
        for (int i = 0; i < testTimes; i++) {
            Node head = generateRandomBST(maxLevel, maxValue);
            if (isBalanced1(head) != isBalanced2(head)) {
                System.out.println("Oops!");
            }
        }
        System.out.println("finish!");
    }
}
