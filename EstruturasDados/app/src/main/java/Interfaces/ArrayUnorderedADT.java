package Interfaces;

public interface ArrayUnorderedADT<T> extends UnorderedListADT<T> {
    public T find(int index) throws ArrayIndexOutOfBoundsException;

}
