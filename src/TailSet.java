import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.AbstractSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;

/*
//Returns a view of the portion of this set whose elements are greater than or equal to fromElement
//The returned set will throw an IllegalArgumentException on an attempt to insert an element outside its range.
//fromElement - low endpoint (inclusive) of the returned set
*/
public class TailSet<T extends Comparable<T>> extends AbstractSet<T> implements SortedSet<T> {


    private T fromElement;
    private Tree<T> delegate;

    TailSet(T fromElement, Tree<T> delegate){
        this.fromElement = fromElement;
        this.delegate = delegate;
    }

    public boolean remove(T value){
        if (fromElement.compareTo(value) > 0) throw new IllegalArgumentException();
        delegate.remove(value);
        return true;
    }

    public boolean add(T value){
        if (fromElement.compareTo(value) > 0) throw new IllegalArgumentException();
        delegate.add(value);
        return true;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public int size() {
        int result = 0;
        Iterator iterator = delegate.iterator();
        while (iterator.hasNext()){
            T next = (T) iterator.next();
            if (next.compareTo(fromElement) >= 0) result++;
        }

        return result;
    }

    @Nullable
    @Override
    public Comparator<? super T> comparator() {
        return null;
    }

    @NotNull
    @Override
    public SortedSet<T> subSet(T fromElement, T toElement) {
        return null;
    }

    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        return null;
    }

    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        return null;
    }

    @Override
    public T first() {
        return null;
    }

    @Override
    public T last() {
        return null;
    }
}
