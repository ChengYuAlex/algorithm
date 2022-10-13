public class Code012_DeleteGivenValue {
    //    把链表中的给定值都删除
    public static class Node {
        public int value;
        public Node Next;

        public Node(int data) {
            this.value = data;
        }
    }

    public static Node removeValue(Node head, int num) {
        //判断头节点是不是要删除,确定返回的头节点
        while (head != null) {
            if (head.value != num) {
                break;
            }
            head=head.Next;
        }

        Node pre = head;
        Node cur = head;
        //遍历链表搜寻要删除的值

        while (cur != null) {
            if (cur.value == num) {
                pre.Next = cur.Next;
            } else {
                pre = cur;
            }
            cur = cur.Next;
        }
        return head;
    }

}
