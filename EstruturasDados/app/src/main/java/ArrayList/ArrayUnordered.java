package ArrayList;

import Interfaces.ArrayUnorderedADT;

public class ArrayUnordered<T> extends ArrayUnorderedList<T> implements ArrayUnorderedADT<T> {

    public ArrayUnordered() {
        super();
    }

    @Override
    public T find(int index) throws ArrayIndexOutOfBoundsException {
        if(index < 0 || index >= size()) {
            throw new ArrayIndexOutOfBoundsException("O index introduzido supera os limites do Array");
        }

        return this.list[index];
    }
}
