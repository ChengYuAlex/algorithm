import java.util.Stack;

public class Code016_TwoStacksImplementQueue {
    //    如何用栈结构实现队列结构
    public static class TwoStacksQueue {
        public Stack<Integer> stackPush;
        public Stack<Integer> stackPop;

        public TwoStacksQueue() {
            stackPush = new Stack<Integer>();
            stackPop = new Stack<Integer>();
        }

        //push栈向Pop栈倒入数据
        private void pushToPop() {
            if (stackPop.empty()){
                while (!stackPush.empty()){
                    stackPop.add(stackPush.pop());
                }
            }
        }

        public void add(int PushInt) {
            stackPush.add(PushInt);
            pushToPop();
        }

        public int poll() {
            if (stackPop.empty()&&stackPush.empty()){
                throw new RuntimeException("Queue is empty!");
            }
            pushToPop();
            return stackPop.pop();
        }

        public int peek() {
            if (stackPop.empty()&&stackPush.empty()){
                throw new RuntimeException("Queue is empty!");
            }
            pushToPop();
            return stackPop.peek();
        }
    }

    public static void main(String[] args) {
        TwoStacksQueue test = new TwoStacksQueue();
        test.add(1);
        test.add(2);
        test.add(3);
        System.out.println(test.peek());
        System.out.println(test.poll());
        System.out.println(test.peek());
        System.out.println(test.poll());
        System.out.println(test.peek());
        System.out.println(test.poll());
    }

}
