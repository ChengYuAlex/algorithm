import java.util.Comparator;
import java.util.PriorityQueue;

public class Code064_IPO {
    // 做项目获得的最大钱数
    // 测试链接: https://leetcode.com/problems/ipo/description/
    /*
    输入: 正数数组costs、正数数组profits、正数K、正数W
    costs[i]表示i号项目的花费 profits[i]表示i号项目在扣除花费之后还能挣到的钱(利润)
    K表示你只能串行的最多做k个项目 W表示你初始的资金
    说明: 每做完一个项目，马上获得的收益，可以支持你去做下一个项目。
    不能并行的做项目。 输出：你最后获得的最大钱数。
     */

    public static class Program {
        public int profit;
        public int cost;

        public Program(int profit, int cost) {
            this.profit = profit;
            this.cost = cost;
        }
    }

    // 1. 现在的资金 > 项目花费
    // 2. 在能做的项目中选择利润最大的

    // 最多K个项目
    // W是初始资金
    // Profits[] Capital[] 一定等长
    // 返回最终最大的资金
    public static int findMaximizedCapital(int K, int W, int[] Profits, int[] Capital) {
        PriorityQueue<Program> minCostQ = new PriorityQueue<>(new MinCostComparator());
        PriorityQueue<Program> maxProfitQ = new PriorityQueue<>(new MaxProfitComparator());
        for (int i = 0; i < Profits.length; i++) {
            minCostQ.add(new Program(Profits[i], Capital[i]));
        }
        // 现在还有做项目的次数
        for (int i = 0; i < K; i++) {
            // 1. 现在的资金 > 项目花费
            while (!minCostQ.isEmpty() && minCostQ.peek().cost <= W) {
                maxProfitQ.add(minCostQ.poll());
            }
            if (maxProfitQ.isEmpty()) {
                return W;
            }
            // 2. 在能做的项目中选择利润最大的
            W += maxProfitQ.poll().profit;
        }
        return W;
    }

    public static class MinCostComparator implements Comparator<Program> {

        @Override
        public int compare(Program o1, Program o2) {
            return o1.cost - o2.cost;
        }

    }

    public static class MaxProfitComparator implements Comparator<Program> {

        @Override
        public int compare(Program o1, Program o2) {
            return o2.profit - o1.profit;
        }
    }
}
