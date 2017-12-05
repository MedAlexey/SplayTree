import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import sun.reflect.generics.tree.*;

import java.util.*;

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
        if ((toElement.compareTo(value) > 0 && delegate.first().compareTo(value) < 0) && contains(value)) delegate.remove(value);
        else throw new IllegalArgumentException();

        return true;
    }

    public boolean contains(T value){
        Iterator iterator = this.iterator();
        while(iterator.hasNext()){
            if (value.compareTo((T) iterator.next()) == 0) return true;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() {
        return new HeadSetIterator();
    }

    public class HeadSetIterator implements Iterator<T>{

        private List<T> listOfNodes;

        private HeadSetIterator(){
            listOfNodes = new ArrayList<>();
            fillListOfNodes();
        }

        private void fillListOfNodes(){
            Iterator iterator = delegate.iterator();
            while (iterator.hasNext()){
                T next = (T) iterator.next();
                if (next.compareTo(toElement) < 0) listOfNodes.add(next);
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
            }
            else throw new NoSuchElementException();
        }
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
        if (fromElement.compareTo(this.toElement) < 0 && toElement.compareTo(this.toElement) < 0 && fromElement.compareTo(this.first()) >= 0)
            return new SubSet<>(fromElement,toElement,delegate);
        else throw new IndexOutOfBoundsException();
    }

    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        if (toElement.compareTo(this.toElement) < 0 && toElement.compareTo(this.last()) <= 0 && toElement.compareTo(this.first()) >= 0)
            return new HeadSet<>(toElement,delegate);
        else throw new IndexOutOfBoundsException();
    }

    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) { return null; }

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