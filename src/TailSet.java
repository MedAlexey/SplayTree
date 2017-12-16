import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

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

    @Override
    public boolean remove(Object o) {

        @SuppressWarnings("unchecked")
        T value = (T) o;

        if ((fromElement.compareTo(value) > 0 || delegate.last().compareTo(value) < 0) || !contains(value)) throw new IllegalArgumentException();
        delegate.remove(value);
        return true;
    }

    @Override
    public boolean add(T value){
        if (fromElement.compareTo(value) > 0) throw new IllegalArgumentException();
        delegate.add(value);
        return true;
    }

    @Override
    public boolean contains(Object o) {
        @SuppressWarnings("unchecked")
        T value = (T) o;

        Iterator<T> iterator = this.iterator();
        while (iterator.hasNext()){
            if (iterator.next().compareTo(value) == 0) return true;
        }

        return false;
    }

    @NotNull
    @Override
    public Iterator<T> iterator() {
        return new TailSetIterator();
    }

    public class TailSetIterator implements Iterator<T>{

        Iterator<T> iterator = delegate.iterator();
        T current = null;
        T currentNext = findNext();

        private T findNext(){
            while(iterator.hasNext()) {
                T next = iterator.next();
                if (next.compareTo(fromElement) >= 0) {
                    currentNext = next;
                    return next;
                }
            }
            return null;
        }

        @Override
        public boolean hasNext() {
            return currentNext != null;
        }

        @Override
        public T next() throws NoSuchElementException {
            if (currentNext == null) throw new NoSuchElementException();
            current = currentNext;
            currentNext = findNext();
            return current;
        }
    }

    @Override
    public int size() {
        int result = 0;
        Iterator<T> iterator = delegate.iterator();
        while (iterator.hasNext()){
            T next = iterator.next();
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
        if (fromElement.compareTo(this.fromElement) >= 0 && toElement.compareTo(this.fromElement) > 0 && toElement.compareTo(this.last()) <=0)
            return new SubSet<>(fromElement,toElement,delegate);
        else throw new IndexOutOfBoundsException();
    }

    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        if (toElement.compareTo(this.fromElement) > 0 && toElement.compareTo(this.last()) <= 0){
            return new SubSet<>(this.fromElement, toElement, delegate);
        }else throw new IndexOutOfBoundsException();
    }

    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        if (fromElement.compareTo(this.fromElement) >= 0 && fromElement.compareTo(this.last()) <= 0)
            return new TailSet<>(fromElement,delegate);
        else throw new IndexOutOfBoundsException();
    }

    @Override
    public T first() {
        Iterator<T> iterator = delegate.iterator();
        T result = null;
        while (iterator.hasNext()){
            T next = iterator.next();
            if (next.compareTo(fromElement) >= 0){
                if (result == null) result = next;
                else result = result.compareTo(next) > 0 ? next : result;
            }
        }
        return result;
    }

    @Override
    public T last() {
        Iterator<T> itrator = delegate.iterator();
        T result = null;
        while (itrator.hasNext()){
            T next = itrator.next();
            if (next.compareTo(fromElement) >= 0){
                if (result == null) result = next;
                else result = result.compareTo(next) < 0 ? next : result;
            }
        }
        return result;
    }
}