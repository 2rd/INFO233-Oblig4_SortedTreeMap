import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public class SortedTreeMap<K extends Comparable<? super K>, V> implements ISortedTreeMap<K,V> {

    private Entry<K,V> root;
    private int size;
    private Comparator<K> comp;

    private static final boolean BLACK = true;
    private static final boolean RED = false;

    public SortedTreeMap(Comparator<K> kComparator){
        root = null;
        size = 0;
        comp = kComparator;
    }

    /**
     * Finds the minimum key in the map, if no value is found, returns null instead.
     *
     * @return minimum value
     */
    @Override
    public Entry<K, V> min() {
        return null;
    }

    /**
     * Finds the maximum key in the map, if no value is found returns null instead.
     *
     * @return maximum value
     */
    @Override
    public Entry<K, V> max() {
        return null;
    }

    /**
     * Inserts the specified value with the specified key as a new entry into the map.
     * If the key is already present, return the previous value, else null.
     *
     * @param key   The key to be inserted
     * @param value The value to be inserted
     * @return Previous value
     */
    @Override
    public V add(K key, V value) {
        V result = null;
        Entry newEntry = new Entry(key, value);
        if(getRoot() == null){
            newEntry.color = BLACK;
            setRoot(newEntry);
            size++;
            return null;
        }
        //newEntry.color = RED;
        result = (V) addEntry(newEntry, getRoot()).value;
        root.color = BLACK;


        return result;
    }

    /**
     * Inserts the specified entry into the map. If the key is already a part of the map,
     * return the previous value, else null.
     *
     * @param entry The new entry to be inserted into the map
     * @return Previous value
     */
    @Override
    public V add(Entry<K, V> entry) {
        V result = null;
        if(getRoot() == null){
            entry.color = BLACK;
            setRoot(entry);
            size++;
            return null;
        }
        System.out.println("---entry:"+entry.getValue() + "     root:"+getRoot().getValue());
        setRoot (addEntry(entry, getRoot()));
        root.color = BLACK;
        return result;
    }
    /*
    public Entry<K,V> addEntry(Entry<K,V> newEntry, Entry<K,V> rootNode){
        if(rootNode == null){
            rootNode = newEntry;
            reColor(rootNode);
            size++;
            return rootNode;
        }
        int comparison = compare((K) newEntry.key, (K) rootNode.key);

        if(comparison == 0){
            newEntry.parent = rootNode.parent;
            rootNode = newEntry;
        } else if(comparison < 0){
            newEntry.parent = rootNode;
            rootNode.setLeft(addEntry(newEntry, rootNode.getLeft()));
        } else if(comparison > 0){
            newEntry.parent = rootNode;
            rootNode.setRight(addEntry(newEntry, rootNode.getRight()));
        }


        return rootNode;
    }

    public void reColor(Entry<K,V> newNode){
        if(isRED(newNode) && isRED(newNode.parent) && newNode.equals(newNode.parent.getLeft())){
            if(newNode.parent.parent != null && isRED(newNode.parent.parent.getRight())){
                pushColor(newNode.parent.parent);
            }
        }
        if(isRED(newNode) && isRED(newNode.parent) && newNode.equals(newNode.parent.getRight())){
            if(newNode.parent.parent != null && isRED(newNode.parent.parent.getLeft())){
                pushColor(newNode.parent.parent);
            }
        }

    }
    public void pushColor(Entry<K,V> rootNode){
        if (rootNode.getLeft() != null){
            rootNode.getLeft().color = !rootNode.color;
        }
        if (rootNode.getRight() != null){
            rootNode.getRight().color = !rootNode.color;
        }
        rootNode.color = !rootNode.color;
    }
    */
    public void pushColor(Entry<K,V> parent){
        if (parent.getLeft() != null){
            parent.getLeft().color = parent.color;
        }
        if (parent.getRight() != null){
            parent.getRight().color = parent.color;
        }
        parent.color = !parent.color;
        System.out.println("pusha");
    }
    public Entry<K,V> addEntry(Entry newEntry, Entry rootNode){
        if (rootNode == null){
            size++;
            return new Entry<K,V> ((K) newEntry.key, (V) newEntry.getValue());
        }
        Entry result = null;
        int comparison = compare((K) newEntry.key, (K) rootNode.key);
        System.out.println("comparison:  " + comparison);
        if(comparison == 0){
            result = newEntry;
            newEntry.parent = rootNode.parent;
            rootNode = newEntry;
        } else if(comparison < 0){
            newEntry.parent = rootNode;
            rootNode.setLeft(addEntry(newEntry, rootNode.getLeft()));
        } else if(comparison > 0){
            newEntry.parent = rootNode;
            rootNode.setRight(addEntry(newEntry, rootNode.getRight()));
        }

        System.out.println("currRoot: " + rootNode.getValue());
        if(isRED(rootNode.getRight()) && isRED(rootNode.getRight().getRight())){
            reBalanceLeft(rootNode);
        }
        if (isRED(rootNode.getRight()) && !isRED(rootNode.getLeft())){
            rootNode = reBalanceLeft(rootNode);
        }
        if (isRED(rootNode.getLeft()) && isRED(rootNode.getLeft().getLeft())){
            rootNode = reBalanceRight(rootNode);
        }
        if (isRED(rootNode.getLeft()) && isRED(rootNode.getRight())){
            flipColors(rootNode);
        }
        System.out.println("currRoot: " + rootNode.getValue());

        System.out.print("Result == null:  ");
        System.out.print(result == null);
        System.out.println();
        return rootNode;
    }


    public Entry<K,V> reBalance (Entry<K,V> rootNode){
        assert rootNode != null;
        if(isRED(rootNode.getRight())){
            rootNode = reBalanceLeft(rootNode);
        }
        if(isRED(rootNode.getLeft()) && isRED(rootNode.getLeft().getLeft())){
            rootNode = reBalanceRight(rootNode);
        }
        if(isRED(rootNode.getLeft()) && isRED(rootNode.getRight())){
           flipColors(rootNode);

        }
        return rootNode;
    }

    public Entry<K, V> reBalanceLeft (Entry<K,V> rootNode){

        System.out.println("-----------Rebalanserer til venstre--------");
        assert rootNode != null && isRED(rootNode.getRight());
        Entry<K, V> result = rootNode.getRight();
        System.out.println("Setter " + rootNode.key + " sitt right child til å være " + result.getLeft() + " ("+result.key+" sin tidl. left child.");
        rootNode.setRight(result.getLeft());
        System.out.println("Setter " + rootNode.key + " til å være " + result.key + " sitt left child..");
        result.setLeft(rootNode);
        System.out.println("Setter fargen til resultatet(" + result.key + ")  lik " + result.getLeft().key + " sin tidl farge (" + rootNode.color+")");
        result.color = result.getLeft().color;
        System.out.println("setter fargen til " + result.getLeft().key + " til RED");
        result.getLeft().color = RED;
        System.out.println("---------rebalanced left-----------");
        return result;
    }

    public Entry<K, V> reBalanceRight (Entry<K,V> rootNode){
        System.out.println("-----------Rebalanserer til høyre--------");
        assert rootNode != null && isRED(rootNode.getLeft());
        Entry<K, V> result = rootNode.getLeft();
        System.out.println("Setter " + rootNode.key + " sitt left child til å være " + result.getRight() + " ("+result.key+" sin tidl. right child.");

        rootNode.setLeft(result.getRight());
        System.out.println("Setter " + rootNode.key + " til å være " + result.key + " sitt right child..");
        result.setRight(rootNode);
        System.out.println("Setter fargen til resultatet(" + result.key + ")  lik " + result.getRight().key + " sin tidl farge (" + rootNode.color+")");
        result.color = result.getRight().color;
        System.out.println("setter fargen til " + result.getRight().key + " til RED");
        result.getRight().color = RED;
        System.out.println("------------rebalanced right----------");

        return result;
    }

    public void flipColors(Entry<K,V> rootNode){
        assert (rootNode != null) && (rootNode.getLeft() != null) && (rootNode.getRight() != null);
        assert (!isRED(rootNode) && isRED(rootNode.getLeft()) && isRED(rootNode.getRight()) || (isRED(rootNode) && !isRED(rootNode.getLeft()) && !isRED(rootNode.getRight())));
        System.out.println("colors flipped");
        rootNode.color = !rootNode.color;
        rootNode.getRight().color = !rootNode.getRight().color;
        rootNode.getLeft().color = !rootNode.getLeft().color;

    }

    /**
     * Replaces the value for key in the map as long as it is already present. If they key
     * is not present, the method throws an exception.
     *
     * @param key   The key for which the value is replaced
     * @param value The new value
     * @throws NoSuchElementException When key is not in map
     */
    @Override
    public void replace(K key, V value) throws NoSuchElementException {

    }

    /**
     * Applies a function to the value at key and replaces that value. Throws an exception
     * if the key is not present in the map.
     *
     * @param key The key for which we are replacing the value
     * @param f   The function to apply to the value
     * @throws NoSuchElementException When key is not in map
     */
    @Override
    public void replace(K key, BiFunction<K, V, V> f) throws NoSuchElementException {

    }

    /**
     * Removes the entry for key in the map. Throws an exception if the key is not present
     * in the map.
     *
     * @param key The key for the entry to remove
     * @return The removed value
     * @throws NoSuchElementException When key is not in map.
     */
    @Override
    public V remove(Object key) throws NoSuchElementException {
        return null;
    }

    /**
     * Retrieves the value for the key in the map.
     *
     * @param key The key for the value to retrieve
     * @return The value for the key
     * @throws NoSuchElementException When key is not in map
     */
    @Override
    public V getValue(Object key) throws NoSuchElementException {
        return null;
    }

    /**
     * Checks if a key is in the map.
     *
     * @param key The key to check
     * @return true if the key is in the map, false otherwise
     */
    @Override
    public boolean containsKey(K key) {
        return false;
    }

    /**
     * Checks if a value is in the map
     *
     * @param value the value to look for
     * @return True if the value is present, false otherwise
     */
    @Override
    public boolean containsValue(V value) {
        return false;
    }

    /**
     * Finds all the keys in the map and returns them in order.
     *
     * @return keys in order
     */
    @Override
    public Iterable<K> keys() {
        return null;
    }

    /**
     * Finds the values in order of the keys.
     *
     * @return values in order of the keys
     */
    @Override
    public Iterable<V> values() {
        return null;
    }

    /**
     * Finds all entries in the map in order of the keys.
     *
     * @return All entries in order of the keys
     */
    @Override
    public Iterable<Entry<K, V>> entries() {
        return null;
    }

    /**
     * Finds the entry for the key, if the key is not in the map returns the next
     * highest entry if such an entry exists
     *
     * @param key The key to find
     * @return The entry for the key or the next highest
     */
    @Override
    public Entry<K, V> higherOrEqualEntry(K key) {
        return null;
    }

    /**
     * Finds the entry for the key, if the key is not in the map, returns the next
     * lower entry if such an entry exists
     *
     * @param key The key to find
     * @return The entry for the key or the next lower
     */
    @Override
    public Entry<K, V> lowerOrEqualEntry(K key) {
        return null;
    }

    /**
     * Adds all entries in the other map into the current map. If a key is present
     * in both maps, the key in the other map takes precedent.
     *
     * @param other The map to add to the current map.
     */
    @Override
    public void merge(ISortedTreeMap<K, V> other) {

    }

    /**
     * Removes any entry for which the predicate holds true. The predicate can
     * trigger on both the key and value of each entry.
     *
     * @param p The predicate that tests which entries should be kept.
     */
    @Override
    public void removeIf(BiPredicate<K, V> p) {

    }

    /**
     * Checks if the map is empty
     *
     * @return True if the map is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return size() <= 0;
    }

    /**
     * Returns the number of entries in the map
     *
     * @return Number of entries
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Clears the map of entries.
     */
    @Override
    public void clear() {
        size = 0; root = null;
    }

    public int compare(K key1, K key2){
        if(comp != null) {
            return comp.compare(key1, key2);
        }
        return key1.compareTo(key2);
    }

    public Entry<K, V> getRoot() {
        return root;
    }

    public void setRoot(Entry<K, V> root) {
        this.root = root;
    }

    public boolean isRED(Entry<K, V> node) {
        if(node == null){
            return false;
        }
        return node.color == RED;
    }
}
