package edu.ktu.ds.lab2.utils;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Stack;

/**
 * Rikiuojamos objektų kolekcijos - aibės realizacija dvejetainiu paieškos
 * medžiu.
 *
 * @param <E> Aibės elemento tipas. Turi tenkinti interfeisą Comparable<E>, arba
 * per klasės konstruktorių turi būti paduodamas Comparator<E> interfeisą
 * tenkinantis objektas.
 *
 * @author darius.matulis@ktu.lt
 * @užduotis Peržiūrėkite ir išsiaiškinkite pateiktus metodus.
 */
public class BstSet<E extends Comparable<E>> implements SortedSet<E>, Cloneable {

    // Medžio šaknies mazgas
    protected BstNode<E> root = null;
    // Medžio dydis
    protected int size = 0;
    // Rodyklė į komparatorių
    protected Comparator<? super E> c = null;

    /**
     * Sukuriamas aibės objektas DP-medžio raktams naudojant Comparable<E>
     */
    public BstSet() {
        this.c = Comparator.naturalOrder();
    }

    /**
     * Sukuriamas aibės objektas DP-medžio raktams naudojant Comparator<E>
     *
     * @param c Komparatorius
     */
    public BstSet(Comparator<? super E> c) {
        this.c = c;
    }

    /**
     * Patikrinama ar aibė tuščia.
     *
     * @return Grąžinama true, jei aibė tuščia.
     */
    @Override
    public boolean isEmpty() {
        return root == null;
    }

    /**
     * @return Grąžinamas aibėje esančių elementų kiekis.
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Išvaloma aibė.
     */
    @Override
    public void clear() {
        root = null;
        size = 0;
    }

    /**
     * Patikrinama ar aibėje egzistuoja elementas.
     *
     * @param element - Aibės elementas.
     * @return Grąžinama true, jei aibėje egzistuoja elementas.
     */
    @Override
    public boolean contains(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in contains(E element)");
        }

        return get(element) != null;
    }

    /**
     * Aibė papildoma nauju elementu.
     *
     * @param element - Aibės elementas.
     */
    @Override
    public void add(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in add(E element)");
        }

        root = addRecursive(element, root);
    }

    private BstNode<E> addRecursive(E element, BstNode<E> node) {
        if (node == null) {
            size++;
            return new BstNode<>(element);
        }

        int cmp = c.compare(element, node.element);

        if (cmp < 0) {
            node.left = addRecursive(element, node.left);
        } else if (cmp > 0) {
            node.right = addRecursive(element, node.right);
        }

        return node;
    }

    /**
     * Pašalinamas elementas iš aibės.
     *
     * @param element - Aibės elementas.
     */
    @Override
    public void remove(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in remove(E element)");
        }

        root = removeRecursive(element, root);
    }

    private BstNode<E> removeRecursive(E element, BstNode<E> node) {
        if (node == null) {
            return node;
        }
        // Medyje ieškomas šalinamas elemento mazgas;
        int cmp = c.compare(element, node.element);

        if (cmp < 0) {
            node.left = removeRecursive(element, node.left);
        } else if (cmp > 0) {
            node.right = removeRecursive(element, node.right);
        } else if (node.left != null && node.right != null) {
            /* Atvejis kai šalinamas elemento mazgas turi abu vaikus.
             Ieškomas didžiausio rakto elemento mazgas kairiajame pomedyje.
             Galima kita realizacija kai ieškomas mažiausio rakto
             elemento mazgas dešiniajame pomedyje. Tam yra sukurtas
             metodas getMin(E element);
             */
            BstNode<E> nodeMax = getMax(node.left);
            /* Didžiausio rakto elementas (TIK DUOMENYS!) perkeliamas į šalinamo
             elemento mazgą. Pats mazgas nėra pašalinamas - tik atnaujinamas;
             */
            node.element = nodeMax.element;
            // Surandamas ir pašalinamas maksimalaus rakto elemento mazgas;
            node.left = removeMax(node.left);
            size--;
            // Kiti atvejai
        } else {
            node = (node.left != null) ? node.left : node.right;
            size--;
        }

        return node;
    }

    private E get(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in get(E element)");
        }

        BstNode<E> node = root;
        while (node != null) {
            int cmp = c.compare(element, node.element);

            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {
                return node.element;
            }
        }

        return null;
    }

    private BstNode<E> getNode(E element) {
        if (element == null) {
            throw new IllegalArgumentException("Element is null in get(E element)");
        }

        BstNode<E> node = root;
        while (node != null) {
            int cmp = c.compare(element, node.element);

            if (cmp < 0) {
                node = node.left;
            } else if (cmp > 0) {
                node = node.right;
            } else {
                return node;
            }
        }

        return null;
    }

    /**
     * Pašalina maksimalaus rakto elementą paiešką pradedant mazgu node
     *
     * @param node
     * @return
     */
    BstNode<E> removeMax(BstNode<E> node) {
        if (node == null) {
            return null;
        } else if (node.right != null) {
            node.right = removeMax(node.right);
            return node;
        } else {
            return node.left;
        }
    }

    /**
     * Grąžina maksimalaus rakto elementą paiešką pradedant mazgu node
     *
     * @param node
     * @return
     */
    BstNode<E> getMax(BstNode<E> node) {
        return get(node, true);
    }

    /**
     * Grąžina minimalaus rakto elementą paiešką pradedant mazgu node
     *
     * @param node
     * @return
     */
    BstNode<E> getMin(BstNode<E> node) {
        return get(node, false);
    }

    private BstNode<E> get(BstNode<E> node, boolean findMax) {
        BstNode<E> parent = null;
        while (node != null) {
            parent = node;
            node = (findMax) ? node.right : node.left;
        }
        return parent;
    }

    /**
     * Grąžinamas aibės elementų masyvas.
     *
     * @return Grąžinamas aibės elementų masyvas.
     */
    @Override
    public Object[] toArray() {
        int i = 0;
        Object[] array = new Object[size];
        for (Object o : this) {
            array[i++] = o;
        }
        return array;
    }

    /**
     * Aibės elementų išvedimas į String eilutę Inorder (Vidine) tvarka. Aibės
     * elementai išvedami surikiuoti didėjimo tvarka pagal raktą.
     *
     * @return elementų eilutė
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (E element : this) {
            sb.append(element.toString()).append(System.lineSeparator());
        }
        return sb.toString();
    }

    /**
     * Medžio vaizdavimas simboliais, žiūr.: unicode.org/charts/PDF/U2500.pdf
     * Tai 4 galimi terminaliniai simboliai medžio šakos gale
     */
    private static final String[] term = {"\u2500", "\u2534", "\u252C", "\u253C"};
    private static final String rightEdge = "\u250C";
    private static final String leftEdge = "\u2514";
    private static final String endEdge = "\u25CF";
    private static final String vertical = "\u2502  ";
    private String horizontal;

    /* Papildomas metodas, išvedantis aibės elementus į vieną String eilutę.
     * String eilutė formuojama atliekant elementų postūmį nuo krašto,
     * priklausomai nuo elemento lygio medyje. Galima panaudoti spausdinimui į
     * ekraną ar failą tyrinėjant medžio algoritmų veikimą.
     *
     * @author E. Karčiauskas
     */
    @Override
    public String toVisualizedString(String dataCodeDelimiter) {
        horizontal = term[0] + term[0];
        return root == null ? ">" + horizontal
                : toTreeDraw(root, ">", "", dataCodeDelimiter);
    }

    private String toTreeDraw(BstNode<E> node, String edge, String indent, String dataCodeDelimiter) {
        if (node == null) {
            return "";
        }
        String step = (edge.equals(leftEdge)) ? vertical : "   ";
        StringBuilder sb = new StringBuilder();
        sb.append(toTreeDraw(node.right, rightEdge, indent + step, dataCodeDelimiter));
        int t = (node.right != null) ? 1 : 0;
        t = (node.left != null) ? t + 2 : t;
        sb.append(indent).append(edge).append(horizontal).append(term[t]).append(endEdge).append(
                split(node.element.toString(), dataCodeDelimiter)).append(System.lineSeparator());
        step = (edge.equals(rightEdge)) ? vertical : "   ";
        sb.append(toTreeDraw(node.left, leftEdge, indent + step, dataCodeDelimiter));
        return sb.toString();
    }

    private String split(String s, String dataCodeDelimiter) {
        int k = s.indexOf(dataCodeDelimiter);
        if (k <= 0) {
            return s;
        }
        return s.substring(0, k);
    }

    /**
     * Sukuria ir grąžina aibės kopiją.
     *
     * @return Aibės kopija.
     * @throws java.lang.CloneNotSupportedException
     */
    @Override
    public Object clone() throws CloneNotSupportedException {
        BstSet<E> cl = (BstSet<E>) super.clone();
        if (root == null) {
            return cl;
        }
        cl.root = cloneRecursive(root);
        cl.size = this.size;
        return cl;
    }

    private BstNode<E> cloneRecursive(BstNode<E> node) {
        if (node == null) {
            return null;
        }

        BstNode<E> clone = new BstNode<>(node.element);
        clone.left = cloneRecursive(node.left);
        clone.right = cloneRecursive(node.right);
        return clone;
    }

    /**
     * Grąžinamas aibės poaibis iki elemento.
     *
     * @param element - Aibės elementas.
     * @return Grąžinamas aibės poaibis iki elemento.
     */
    @Override
    public Set<E> headSet(E element) {
        if (element == null) {
            throw new NullPointerException();
        }
        Set<E> hs = new BstSet(); //TailSet Poaibis
//        if (contains(element)) {
        BstNode<E> n = root;
        setRecursive(hs, n, element, true);
//        }
        return hs;

//        throw new UnsupportedOperationException("Studentams reikia realizuoti headSet()");
    }

    /**
     * Grąžinamas aibės poaibis nuo elemento element1 iki element2.
     *
     * @param element1 - pradinis aibės poaibio elementas.
     * @param element2 - galinis aibės poaibio elementas.
     * @return Grąžinamas aibės poaibis nuo elemento element1 iki element2.
     */
    @Override
    public Set<E> subSet(E element1, E element2) {
        if (element1 == null || element2 == null) {
            throw new NullPointerException();
        }
        Set<E> ss = new BstSet(); //SubSet Poaibis
//        if (contains(element1) && contains(element2)) {
//        BstNode<E> n = getNode(element1);
        BstNode<E> n = root;
        subRecursive(ss, n, element1, element2);
//        }
        return ss;
        //        throw new UnsupportedOperationException("Studentams reikia realizuoti subSet()");
    }

    /**
     * Grąžinamas aibės poaibis iki elemento.
     *
     * @param element - Aibės elementas.
     * @return Grąžinamas aibės poaibis nuo elemento.
     */
    @Override
    public Set<E> tailSet(E element) {
        if (element == null) {
            throw new NullPointerException();
        }

        Set<E> ts = new BstSet();
//        if (contains(element)) {
//        BstNode<E> n = getNode(element);
        BstNode<E> n = root;
//        tailRecursive(ts, n, element);
        setRecursive(ts, n, element, false);
//        }
        return ts;
//        throw new UnsupportedOperationException("Studentams reikia realizuoti tailSet()");
    }

//    private void tailRecursive(Set<E> hs, BstNode<E> node) 
//    private BstNode<E> tailRecursive(Set<E> hs, BstNode<E> n, E node) {
//        if (n == null) {
//            return null;
//        }
//        if (c.compare(n.element, node) > 0) {
//            hs.add(n.element);
//        }
//        tailRecursive(hs, n.left, node);
//        tailRecursive(hs, n.right, node);
//
//        return n;
//    }
    private BstNode<E> setRecursive(Set<E> Set, BstNode<E> n, E node, boolean orHeadTailSub) {
        if (n == null) {
            return null;
        }
//        if (n.element != d) {
        if (!(c.compare(n.element, node) == 0)) {
//        if (!n.element.equals(d)) {
            if (orHeadTailSub) {
                if (c.compare(n.element, node) < 0) {
                    Set.add(n.element);
                }
            } else {
                if (c.compare(n.element, node) > 0) {
                    Set.add(n.element);
                }
            }

            setRecursive(Set, n.left, node, orHeadTailSub);
            setRecursive(Set, n.right, node, orHeadTailSub);
        } else if (orHeadTailSub) {
            setRecursive(Set, n.left, node, orHeadTailSub);
        } else {
            setRecursive(Set, n.right, node, orHeadTailSub);
        }
        return n;
    }

    private BstNode<E> subRecursive(Set<E> Set, BstNode<E> n, E node1, E node2) {
        if (n == null) {
            return null;
        }
//        if (n.element != d) {
        if (!(c.compare(n.element, node2) == 0)) {
            if (c.compare(n.element, node1) > 0) {
                if (c.compare(n.element, node2) < 0) {
                    Set.add(n.element);
                }
            }
            subRecursive(Set, n.left, node1, node2);
            subRecursive(Set, n.right, node1, node2);
        } else {
            subRecursive(Set, n.left, node1, node2);
            subRecursive(Set, n.right, node1, node2);
        }
        return n;
    }

    public SortedSet<E> headSet(E toElement, boolean inclusive) {
        if (toElement == null) {
            throw new NullPointerException();
        }
        SortedSet<E> hs = new BstSet(); //TailSet Poaibis
//        if (contains(toElement)) {
        BstNode<E> n = root;
        headSortedRecursive(hs, n, toElement, false);
//        }
        return hs;
    }

    private BstNode<E> headSortedRecursive(Set<E> Set, BstNode<E> n, E node, boolean inclusive) {
        if (n == null) {
            return null;
        }
//        if (n.element != d) {
        if (!(c.compare(n.element, node) == 0)) {
//        if (!n.element.equals(d)) {

            headSortedRecursive(Set, n.left, node, inclusive);
            if (c.compare(n.element, node) < 0) {
                Set.add(n.element);
            }
            headSortedRecursive(Set, n.right, node, inclusive);
        } else {
            headSortedRecursive(Set, n.left, node, inclusive);
            if (inclusive) {
                Set.add(n.element);
            }
            headSortedRecursive(Set, n.right, node, inclusive);
        }

        return n;
    }

    public boolean containsAll(BstSet<?> c) {
        boolean ats = true;

        BstNode<E> test = (BstNode<E>) c.root;
        ats = containsAllRecursive(ats, test);

        return ats;
    }

    private boolean containsAllRecursive(boolean ats, BstNode<?> c) {

        if (c == null) {
            return ats;
        }

        if (ats) {
            if (!contains((E) c.element)) {
                ats = false;
                return ats;
            }

            containsAllRecursive(ats, c.left);
            containsAllRecursive(ats, c.right);

            return ats;
        } else {
            return ats;
        }
    }

    public E higher(E e) {

        if (e == null) {
            return null;
        }
        E ats = null;

        BstNode<E> n = root;
        ats = higherRecurisve(e, n, ats);
        return ats;
    }

    private E higherRecurisve(E e, BstNode<E> node, E ats) {
        if (node == null) {
            return ats;
        }

        int cmp1;
        int cmp2 = 0;

        cmp1 = c.compare(e, node.element);
        if (ats != null) {
            cmp2 = c.compare(ats, node.element);
        } else if (cmp1 < 0) {
            ats = node.element;
        }

        if (cmp1 < 0 && cmp2 > 0 && ats != node.element) {
            ats = node.element;
        }

        if (cmp1 < 0) {
            ats = higherRecurisve(e, node.left, ats);
        } else {
            ats = higherRecurisve(e, node.right, ats);
        }

        return ats;
    }
    //---------------------------------------------------------11-----------
    public E lower(E e){
        if(root==null) return null;
        BstNode<E> checkable = root;
        E saved = null;
        while(checkable!=null){
            if(checkable.element.compareTo(e) == 1 || checkable.element.compareTo(e)== 0){
                if(checkable.left!=null){
                    checkable = checkable.left;
                    continue;
                }
                return  saved;
            }
            else{
                saved = checkable.element;
                if(checkable.right!=null){
                    checkable = checkable.right;
                    continue;
                }
                return  saved;
            }
        }
        return saved;
    }

    public boolean addAll(BstSet<? extends E> c) {
        if (c.isEmpty()) {
            return false;
        }
        boolean ats = true;

        BstNode<E> n = (BstNode<E>) c.root;
        addAllRecursive(n, ats);
        return ats;
    }

    private void addAllRecursive(BstNode<? extends E> node, boolean ats) {
        if (node == null) {
            return;
        }
        if (get(node.element) == null) {
            add(node.element);
        } else {
            ats = false;
        }
        addAllRecursive(node.left, ats);
        addAllRecursive(node.right, ats);
    }

    public E pollFirst() {
        E ats = getMin(root).element;
        remove(ats);

        return ats;
    }

    public E pollLast() {
        E ats = getMax(root).element;
        remove(ats);

        return ats;
    }

    public int findHeight() {
        return findHeightRecursive(root);
    }

    private int findHeightRecursive(BstNode<E> node) {
        BstNode<E> n = node;
        if (root == null) {
            return 0;
        }
        int hLeft = 0;
        int hRight = 0;

        if (n.left != null) {
            hLeft = findHeightRecursive(n.left);
        }

        if (n.right != null) {
            hRight = findHeightRecursive(n.right);
        }

        int max = (hLeft > hRight) ? hLeft : hRight;

        return max + 1;
    }

    /**
     * Grąžinamas tiesioginis iteratorius.
     *
     * @return Grąžinamas tiesioginis iteratorius.
     */
    @Override
    public Iterator<E> iterator() {
        return new IteratorBst(true);
    }

    /**
     * Grąžinamas atvirkštinis iteratorius.
     *
     * @return Grąžinamas atvirkštinis iteratorius.
     */
    @Override
    public Iterator<E> descendingIterator() {
        return new IteratorBst(false);
    }

    /**
     * Vidinė objektų kolekcijos iteratoriaus klasė. Iteratoriai: didėjantis ir
     * mažėjantis. Kolekcija iteruojama kiekvieną elementą aplankant vieną kartą
     * vidine (angl. inorder) tvarka. Visi aplankyti elementai saugomi steke.
     * Stekas panaudotas iš java.util paketo, bet galima susikurti nuosavą.
     */
    private class IteratorBst implements Iterator<E> {

        private Stack<BstNode<E>> stack = new Stack<>();
        // Nurodo iteravimo kolekcija kryptį, true - didėjimo tvarka, false - mažėjimo
        private boolean ascending;
        // Nurodo einamojo medžio elemento tėvą. Reikalingas šalinimui.
        private BstNode<E> parent = root;

        IteratorBst(boolean ascendingOrder) {
            this.ascending = ascendingOrder;
            this.toStack(root);
        }

        @Override
        public boolean hasNext() {
            return !stack.empty();
        }

        @Override
        public E next() {
            if (!stack.empty()) {
                // Grąžinamas paskutinis į steką patalpintas elementas
                BstNode<E> n = stack.pop();
                // Atsimenama tėvo viršunė. Reikia remove() metodui
                parent = (!stack.empty()) ? stack.peek() : root;
                BstNode<E> node = (ascending) ? n.right : n.left;
                // Dešiniajame n pomedyje ieškoma minimalaus elemento,
                // o visi paieškos kelyje esantys elementai talpinami į steką
                toStack(node);
                return n.element;
            } else { // Jei stekas tuščias
                return null;
            }
        }

        @Override
        public void remove() {
//            if (parent.right.right.element == next() || parent.right.left.element == next()) {
//                root = removeRecursive(parent.right.element, root);
//            } else if (parent.left.right.element == next() || parent.left.left.element == next()) {
//                root = removeRecursive(parent.left.element, root);
//            }
            

//            E get = get(next());
//            root = removeRecursive(get,root);
            root = removeRecursive(parent.element,root);

//            if ((c.compare(parent.right.right.element, next()) == 0) || (c.compare(parent.right.right.element, next()) == 0)) {
//                root = removeRecursive(parent.right.element, root);
//            } else if ((c.compare(parent.right.left.element, next()) == 0) || (c.compare(parent.right.left.element, next()) == 0)) {
//                root = removeRecursive(parent.left.element, root);
//            }
//            throw new UnsupportedOperationException("Studentams reikia realizuoti remove()");
        }

        private void toStack(BstNode<E> n) {
            while (n != null) {
                stack.push(n);
                n = (ascending) ? n.left : n.right;
            }
        }

    }

    /**
     * Vidinė kolekcijos mazgo klasė
     *
     * @param <N> mazgo elemento duomenų tipas
     */
    protected class BstNode<N> {

        // Elementas
        protected N element;
        // Rodyklė į kairįjį pomedį
        protected BstNode<N> left;
        // Rodyklė į dešinįjį pomedį
        protected BstNode<N> right;

        protected BstNode() {
        }

        protected BstNode(N element) {
            this.element = element;
            this.left = null;
            this.right = null;
        }
    }
}
