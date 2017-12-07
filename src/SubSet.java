import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;

/*
//Returns a view of the portion of this set whose elements range from fromElement, inclusive, to toElement, exclusive.
//The returned set will throw an IllegalArgumentException on an attempt to insert an element outside its range.
//fromElement - low endpoint (inclusive) of the returned set
//toElement - high endpoint (exclusive) of the returned set
*/
public class SubSet<T extends Comparable<T>> extends AbstractSet<T> implements SortedSet<T> {

    private T fromElement;
    private T toElement;
    private Tree<T> delegate;

    SubSet(T fromElement,T toElement, Tree<T> delegate){
        this.fromElement = fromElement;
        this.toElement = toElement;
        this.delegate = delegate;
    }

    @Override
    public boolean remove(Object o) {
        T value = (T) o;
        if ((toElement.compareTo(value) > 0 && fromElement.compareTo(value) <= 0) && contains(value)) delegate.remove(value);
        else throw new IllegalArgumentException();

        return true;
    }

    @Override
    public boolean add(T value){
        if(toElement.compareTo(value) > 0 && fromElement.compareTo(value) <= 0) delegate.add(value);
        else throw new IllegalArgumentException();

        return true;
    }

    @Override
    public boolean contains(Object o) {
        T value = (T) o;

        Iterator iterator = this.iterator();
        while (iterator.hasNext()){
            if (value.compareTo((T) iterator.next()) == 0) return true;
        }
        return false;
    }

    @Override
    public Iterator<T> iterator() { return new SubSetIterator(); }

    public class SubSetIterator  implements Iterator<T>{

        private List<T> listOfNodes;

        private SubSetIterator(){
            listOfNodes = new ArrayList<>();
            fillListOfNodes();
        }

        private void fillListOfNodes(){
            Iterator treeIterator = delegate.iterator();
            while (treeIterator.hasNext()){
                T next = (T) treeIterator.next();
                if (next.compareTo(fromElement) >= 0 && next.compareTo(toElement) < 0) listOfNodes.add(next);
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
        int result = 0;
        Iterator iterator = delegate.iterator();
        while (iterator.hasNext()){
            T next = (T) iterator.next();
            if (next.compareTo(fromElement) >= 0 && next.compareTo(toElement) < 0){
                result++;
            }
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
        if (fromElement.compareTo(this.fromElement) >= 0 && toElement.compareTo(this.toElement) < 0)
            return new SubSet<>(fromElement,toElement,delegate);
        else throw new IndexOutOfBoundsException();
    }

    @NotNull
    @Override
    public SortedSet<T> headSet(T toElement) {
        if (toElement.compareTo(this.toElement) < 0 && toElement.compareTo(this.fromElement) >= 0){
            return new SubSet<>(this.fromElement, toElement, delegate);
        } else throw new IndexOutOfBoundsException();
    }

    @NotNull
    @Override
    public SortedSet<T> tailSet(T fromElement) {
        if (fromElement.compareTo(this.fromElement) >= 0 && fromElement.compareTo(this.toElement) < 0){
            return new SubSet<>(fromElement, this.toElement, delegate);
        } else throw new IndexOutOfBoundsException();
    }

    @Override
    public T first() {
        Iterator iterator = delegate.iterator();
        T result = null;
        while (iterator.hasNext()){
            T next = (T) iterator.next();
            if (next.compareTo(fromElement) >= 0 && next.compareTo(toElement) < 0) {
                if (result == null) result = next;
                else result = next.compareTo(result) < 0 ? next : result;
            }
        }
       return result;
    }

    @Override
    public T last() {
        Iterator iterator = delegate.iterator();
        T result = null;
        while (iterator.hasNext()){
            T next = (T) iterator.next();
            if (next.compareTo(fromElement) >= 0 && next.compareTo(toElement) < 0) {
                if (result == null) result = next;
                else result = result.compareTo(next) < 0 ? next : result;
            }
        }
        return result;
    }
}