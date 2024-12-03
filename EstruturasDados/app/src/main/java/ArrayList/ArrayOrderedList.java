package ArrayList;

import Interfaces.OrderedListADT;

public class ArrayOrderedList<T> extends ArrayList<T> implements OrderedListADT<T> {

    public ArrayOrderedList() {
        super();
    }

    public ArrayOrderedList(int capacity) {
        super(capacity);
    }

    @Override
    public void add(T element) {
        if (list.length == this.count) {
            expandArray();
        }

        Comparable<T> temp = (Comparable<T>) element;
        int pos = 0;

        while ((this.list[pos] == null || temp.compareTo(this.list[pos]) > 0) && pos != this.count) {
            pos++;
        }

        if (this.count != pos) {
            for (int y = this.count; y > pos; y--) {
                list[y] = list[y - 1];
            }
        }

        this.list[pos] = element;
        this.count++;
        this.modCount++;
    }
}
