package dsprojektas;

import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @author maceina
 */
public interface IDynamicArray <E> extends Collection<E> {
    
    boolean add(E e);
    
    void add(int index, E element);
    
    boolean addAll(Collection<? extends E> c);
    
    boolean addAll(int index, Collection<? extends E>c);
    
    void clear();
    
    boolean contains(Object o);
    
    void ensureCapacity(int minCapacity);
    
    E get(int index);
    
    int indexOf(Object o);
    
    boolean isEmpty();
    
    Iterator<E> iterator();
    
    int lastIndexOf(Object o);
    
    E remove(int index);
    
    boolean remove(Object o);
    
    boolean removeAll(Collection<?> c);
    
    void removeRange(int fromIndex, int toIndex);
    
    boolean retainAll(Collection<?> c);
    
    E set(int index, E element);
    
    int size();
    
    E[] subArray(int fromIndex, int toIndex);
    
    Object[] toArray();
    
    void trimToSize();
    
}
