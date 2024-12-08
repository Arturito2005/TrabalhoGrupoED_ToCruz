package ArrayList;

import Interfaces.ArrayUnorderedADT;

public class ArrayUnordered<T> extends ArrayUnorderedList<T> implements ArrayUnorderedADT<T> {

    @Override
    public T find(int index) throws ArrayIndexOutOfBoundsException, NullPointerException {
        if(index < 0 || index >= size()) {
            throw new ArrayIndexOutOfBoundsException("O index introduzido supera os limites do Array");
        }

        if(this.list[index] == null) {
            throw new NullPointerException("A posição introduzida corresponde a um NullPointer");
        }

        return this.list[index];
    }
}
