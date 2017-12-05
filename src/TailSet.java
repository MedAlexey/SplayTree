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

    public boolean remove(T value){
        if ((fromElement.compareTo(value) > 0 || delegate.last().compareTo(value) < 0) || !contains(value)) throw new IllegalArgumentException();
        delegate.remove(value);
        return true;
    }

    public boolean add(T value){
        if (fromElement.compareTo(value) > 0) throw new IllegalArgumentException();
        delegate.add(value);
        return true;
    }

    public boolean contains(T value){
        Iterator iterator = this.iterator();
        while (iterator.hasNext()){
            if ( ((T) iterator.next()).compareTo(value) == 0) return true;
        }

        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new TailSetIterator();
    }

    public class TailSetIterator implements Iterator<T>{

        private List<T> listOfNodes;

        private TailSetIterator(){
            listOfNodes = new ArrayList<>();
            fillListOfNodes();
        }

        private void fillListOfNodes(){
            Iterator iterator = delegate.iterator();
            while (iterator.hasNext()){
                T next = (T) iterator.next();
                if (next.compareTo(fromElement) >= 0) listOfNodes.add(next);
            }
        }

        @Override
        public boolean hasNext() {
            return !listOfNodes.isEmpty();
        }

        @Override
        public T next() {
            if (hasNext()){
                T result = listOfNodes.get(0);
                listOfNodes.remove(0);
                return result;
            }else throw new NoSuchElementException();
        }
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
        if (fromElement.compareTo(this.fromElement) >= 0 && toElement.compareTo(this.fromElement) > 0 && toElement.compareTo(this.last()) <=0)
            return new SubSet<>(fromElement,toElement,delegate);
        else throw new IndexOutOfBoundsException();
    }

    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {return null; }

    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        if (fromElement.compareTo(this.fromElement) >= 0 && fromElement.compareTo(this.last()) <= 0)
            return new TailSet<>(fromElement,delegate);
        else throw new IndexOutOfBoundsException();
    }

    @Override
    public T first() {
        Iterator iterator = delegate.iterator();
        T result = null;
        while (iterator.hasNext()){
            T next = (T) iterator.next();
            if (next.compareTo(fromElement) >= 0){
                if (result == null) result = next;
                else result = result.compareTo(next) > 0 ? next : result;
            }
        }
        return result;
    }

    @Override
    public T last() {
        Iterator itrator = delegate.iterator();
        T result = null;
        while (itrator.hasNext()){
            T next = (T) itrator.next();
            if (next.compareTo(fromElement) >= 0){
                if (result == null) result = next;
                else result = result.compareTo(next) < 0 ? next : result;
            }
        }
        return result;
    }
}