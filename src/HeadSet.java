import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sun.reflect.generics.tree.*;

import java.util.AbstractSet;
import java.util.Comparator;
import java.util.Iterator;
import java.util.SortedSet;

/*
/Returns a view of the portion of this set whose elements are strictly less than toElement.
/The returned set will throw an IllegalArgumentException on an attempt to insert an element outside its range.
/toElement - high endpoint (exclusive) of the returned set.
*/

public class HeadSet<T extends Comparable<T>> extends AbstractSet<T> implements SortedSet<T> {

    private Tree<T> delegate;
    private T toElement;

    HeadSet(T toElement, Tree<T> delegate){
        this.toElement = toElement;
        this.delegate = delegate;
    }

    public boolean add(T value){
        if (toElement.compareTo(value) > 0) delegate.add(value);
        else throw new IllegalArgumentException();

        return true;
    }

    public boolean remove(T value){
        if (toElement.compareTo(value) > 0) delegate.remove(value);
        else throw new IllegalArgumentException();

        return true;
    }

    @Override
    public Iterator<T> iterator() {
        return null;
    }

    @Override
    public int size() {
        int result =0;
        Iterator iterator = delegate.iterator();
        while (iterator.hasNext()){
            T next = (T) iterator.next();
            if (toElement.compareTo(next) > 0) result++;
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
        Iterator iterator = delegate.iterator();
        T result = null;
        while (iterator.hasNext()){
            T next = (T) iterator.next();
            if (next.compareTo(toElement) < 0) {
                if (result == null) result = next;
                else result = result.compareTo(next) > 0 ? next : result;
            }
        }
        return result;
    }

    @Override
    public T last() {
        Iterator iterator = delegate.iterator();
        T result = null;
        while(iterator.hasNext()){
            T next = (T) iterator.next();
            if (next.compareTo(toElement) < 0) {
                if (result == null) result = next;
                else result = result.compareTo(next) < 0 && next.compareTo(toElement) < 0 ? next : result;
            }
        }

        return result;
    }
}
