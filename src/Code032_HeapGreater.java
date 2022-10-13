import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class Code032_HeapGreater<T> {
    //加强堆
    //系统提供的堆无法做到的事情：
    //1）已经入堆的元素，如果参与排序的指标方法变化,系统提供的堆无法做到时间复杂度O(logN)调整！都是O(N)的调整！
    //2）系统提供的堆只能弹出堆顶，做不到自由删除任何一个堆中的元素,或者说，无法在时间复杂度O(logN)内完成！一定会高于O(logN)
    //根本原因：无反向索引表

    //手动改写堆的代码讲解
    //1）建立反向索引表
    //2）建立比较器
    //3）核心在于各种结构相互配合，非常容易出错

    //T一定要是非基础类型，有基础类型需求包一层
    private ArrayList<T> heap;//堆结构数组
    private HashMap<T, Integer> indexMap;//反向索引表 a->1 b->2
    private int heapSize;//堆大小
    private Comparator<? super T> comp;//比较器

    public Code032_HeapGreater(Comparator<? super T> c) {
        heap = new ArrayList<>();
        indexMap = new HashMap<>();
        heapSize = 0;
        comp = c;
    }

    public boolean isEmpty() {
        return heapSize == 0;
    }

    public int size() {
        return heapSize;
    }

    public boolean contains(T obj) {
        return indexMap.containsKey(obj);
    }

    public T peek() {
        return heap.get(0);
    }

    public void push(T obj) {
        heap.add(obj);
        indexMap.put(obj, heapSize);
        heapInsert(heapSize++);
    }

    public T pop() {
        T ans = heap.get(0);
        swap(0, heapSize - 1);
        indexMap.remove(ans);
        heap.remove(--heapSize);
        heapify(0);
        return ans;
    }

    public void remove(T obj) {
        T replace = heap.get(heapSize - 1);
        int index = indexMap.get(obj);
        indexMap.remove(obj);
        heap.remove(--heapSize);
        if (obj != replace) {
            heap.set(index, replace);
            indexMap.put(replace, index);
            resign(replace);
        }
    }

    public void resign(T obj) {
        heapInsert(indexMap.get(obj));
        heapify(indexMap.get(obj));
    }

    //返回堆上的所有元素
    public List<T> getAllElements() {
        List<T> ans = new ArrayList<>();
        for (T c : heap) {
            ans.add(c);
        }
        return ans;
    }

    private void heapInsert(int index) {
        while (comp.compare(heap.get(index), heap.get((index - 1) / 2)) < 0) {
            swap(index, (index - 1) / 2);
            index = (index - 1) / 2;
        }
    }

    private void heapify(int index) {
        int left = index * 2 + 1;
        while (left < heapSize) {
            int best = left + 1 < heapSize &&
                    comp.compare(heap.get(left + 1), heap.get(left)) < 0 ?
                    (left + 1) : left;
            best = comp.compare(heap.get(best), heap.get(index)) < 0 ? best : index;
            if (best == index) {
                break;
            }
            swap(best, index);
            index = best;
            left = index * 2 + 1;
        }
    }

    private void swap(int i, int j) {
        T o1 = heap.get(i);
        T o2 = heap.get(j);
        heap.set(i, o2);
        heap.set(j, o1);
        indexMap.put(o2, i);
        indexMap.put(o1, j);
    }

}
