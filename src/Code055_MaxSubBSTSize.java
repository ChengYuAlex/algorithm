import java.util.ArrayList;

public class Code055_MaxSubBSTSize {
    // 在线测试链接 : https://leetcode.com/problems/largest-bst-subtree
    // 最大二叉搜索子树的节点数

    // 提交时不要提交这个类
    public static class TreeNode {
        public int val;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int value) {
            val = value;
        }
    }

    public static int largestBSTSubtree(TreeNode head) {
        if (head == null) {
            return 0;
        }
        return process(head).maxBSTSubtreeSize;
    }

    public static class Info {
        public int maxBSTSubtreeSize;
        public int allSize;
        public int max;
        public int min;

        public Info(int maxBSTSubtreeSize, int allSize, int max, int min) {
            this.maxBSTSubtreeSize = maxBSTSubtreeSize;
            this.allSize = allSize;
            this.max = max;
            this.min = min;
        }
    }

    public static Info process(TreeNode x) {
        if (x == null) {
            return null;
        }
        Info leftInfo = process(x.left);
        Info rightInfo = process(x.right);

        int allSize = 1;
        int max = x.val;
        int min = x.val;
        if (leftInfo != null) {
            allSize += leftInfo.allSize;
            max = Math.max(max, leftInfo.max);
            min = Math.min(min, leftInfo.min);
        }
        if (rightInfo != null) {
            allSize += rightInfo.allSize;
            max = Math.max(max, rightInfo.max);
            min = Math.min(min, rightInfo.min);
        }

        int maxBSTSubtreeSize = 1;
        // 1) 左树的 2) 右树的
        if (leftInfo != null) {
            maxBSTSubtreeSize = leftInfo.maxBSTSubtreeSize;
        }
        if (rightInfo != null) {
            maxBSTSubtreeSize = Math.max(maxBSTSubtreeSize, rightInfo.maxBSTSubtreeSize);
        }

        // 3) 左右都是BST, 左max<= x.val <= 右min
        boolean leftBST = false;
        boolean rightBST = false;
        if (leftInfo == null || leftInfo.maxBSTSubtreeSize == leftInfo.allSize) {
            leftBST = true;
        }
        if (rightInfo == null || rightInfo.maxBSTSubtreeSize == rightInfo.allSize) {
            rightBST = true;
        }
        if (leftBST && rightBST
                && (leftInfo == null || leftInfo.max < x.val)
                && (rightInfo == null || x.val < rightInfo.min)) {
            maxBSTSubtreeSize = (leftInfo == null ? 0 : leftInfo.allSize)
                    + (rightInfo == null ? 0 : rightInfo.allSize)
                    + 1;
        }

        return new Info(maxBSTSubtreeSize, allSize, max, min);
    }

    // for test
    public static int right(TreeNode head) {
        if (head == null) {
            return 0;
        }
        int h = getBSTSize(head);
        if (h != 0) {
            return h;
        }
        return Math.max(right(head.left), right(head.right));
    }

    // for test
    public static int getBSTSize(TreeNode head) {
        if (head == null) {
            return 0;
        }
        ArrayList<TreeNode> arr = new ArrayList<>();
        in(head, arr);
        for (int i = 1; i < arr.size(); i++) {
            if (arr.get(i).val <= arr.get(i - 1).val) {
                return 0;
            }
        }
        return arr.size();
    }

    // for test
    public static void in(TreeNode head, ArrayList<TreeNode> arr) {
        if (head == null) {
            return;
        }
        in(head.left, arr);
        arr.add(head);
        in(head.right, arr);
    }

    // for test
    public static TreeNode generateRandomBST(int maxLevel, int maxValue) {
        return generate(1, maxLevel, maxValue);
    }

    // for test
    public static TreeNode generate(int level, int maxLevel, int maxValue) {
        if (level > maxLevel || Math.random() < 0.5) {
            return null;
        }
        TreeNode head = new TreeNode((int) (Math.random() * maxValue));
        head.left = generate(level + 1, maxLevel, maxValue);
        head.right = generate(level + 1, maxLevel, maxValue);
        return head;
    }

    // for test
    public static void main(String[] args) {
        int maxLevel = 4;
        int maxValue = 100;
        int testTimes = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTimes; i++) {
            TreeNode head = generateRandomBST(maxLevel, maxValue);
            if (largestBSTSubtree(head) != right(head)) {
                System.out.println("出错了！");
            }
        }
        System.out.println("测试结束");
    }

}
