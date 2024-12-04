package ArrayList;

import Exceptions.ElementNotFoundException;
import Interfaces.UnorderedListADT;

public class ArrayUnorderedList<T> extends ArrayList<T> implements UnorderedListADT<T> {

    public ArrayUnorderedList() {
        super();
    }

    public ArrayUnorderedList(int capacity) {
        super(capacity);
    }

    @Override
    public void addToFront(T element) {
        if (this.count == this.list.length) {
            expandArray();
        }

        for (int i = this.count; i > 0; i--) {
            this.list[i] = this.list[i - 1];
        }

        this.list[0] = element;
        this.count++;
        this.modCount++;
    }

    @Override
    public void addToRear(T element) {
        if (this.count == this.list.length) {
            expandArray();
        }

        this.list[this.count++] = element;
        this.modCount++;
    }

    @Override
    public void addAfter(T element, T target) throws ElementNotFoundException {
        if (!contains(target)) {
            throw new ElementNotFoundException("O elemento target não existe!");
        }

        if (this.count == this.list.length) {
            expandArray();
        }

        int pos = -1;

        do {
            pos++;
        } while (!this.list[pos].equals(target));

        for (int i = this.count; i > pos; i--) {
            this.list[i] = this.list[i - 1];
        }

        this.list[pos + 1] = element;
        this.count++;
        this.modCount++;
    }
}
