import java.util.Comparator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public class SortedTreeMap<K extends Comparable<? super K>, V> implements ISortedTreeMap<K,V> {

    private Entry<K,V> root;
    private int size;
    private Comparator<K> comp;

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
        if(root == null){
            return null;
        }
        Entry<K,V> min = root;
        while (min.getLeft() != null){
            min = min.getLeft();
        }
        return min;
    }

    public Entry<K,V> min(Entry<K,V> rootNode){
        if(rootNode == null){
            return null;
        }
        Entry<K,V> min = rootNode;
        while (rootNode.getLeft() != null){
            min = rootNode.getLeft();
            rootNode = rootNode.getLeft();
        }
        return min;
    }

    /**
     * Finds the maximum key in the map, if no value is found returns null instead.
     *
     * @return maximum value
     */
    @Override
    public Entry<K, V> max() {
        if(root == null){
            return null;
        }
        Entry<K,V> max = root;
        while (max.getRight() != null){
            max = max.getRight();
        }
        return max;
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
        Entry<K,V> newEntry = new Entry(key, value);
        if(getRoot() == null){
            setRoot(newEntry);
            size++;
            return null;
        }
        result = addEntry(newEntry, getRoot());

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

            setRoot(entry);
            size++;
            return null;
        }
        System.out.println("---entry:"+entry.getValue() + "     root:"+getRoot().getValue());
        result = addEntry(entry, getRoot());

        return result;
    }

    public V addEntry(Entry<K,V> newEntry, Entry<K,V> rootNode){
        V result = null;

        int comparison = compare(newEntry.key, rootNode.key);

        if(comparison == 0){
            result = rootNode.getValue();
            rootNode = newEntry;
        } else if(comparison < 0){
            if (rootNode.getLeft() != null) {
                 result = addEntry(newEntry, rootNode.getLeft());
            } else {
                rootNode.setLeft(newEntry);
                size++;
            }
        } else if(comparison > 0){
            if(rootNode.getRight() != null) {
                result = addEntry(newEntry, rootNode.getRight());
            } else{
                rootNode.setRight(newEntry);
                size++;
            }
        }

        return result;
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
        V result = getValue(key);
        root = removeEntry((K) key, root, root);
        size--;
        return result;
    }

    public Entry<K,V> removeEntry(K key, Entry<K,V> toRemove, Entry<K,V> rootNode){

        if(toRemove == null) {
            return null;
        }

        int comparison = compare(key, toRemove.key);
        if(comparison < 0){
            rootNode = toRemove;
            rootNode.setLeft(removeEntry(key, toRemove.getLeft(), rootNode));

        }
        else if(comparison > 0){
            rootNode = toRemove;
            rootNode.setRight(removeEntry(key, toRemove.getRight(), rootNode));

        }
        else {
            if(toRemove.getLeft() == null){
                return toRemove.getRight();
            }
            else if(toRemove.getRight() == null){
                return toRemove.getLeft();
            }

            toRemove.key = min(toRemove.getRight()).key;
            toRemove.value = min(toRemove.getRight()).getValue();
            toRemove.setRight(removeEntry(toRemove.key, toRemove.getRight(), toRemove.getRight()));
        }

        return toRemove;
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
        Entry<K,V> resultNode = getEntry((K) key, root);
        if (resultNode == null){
            throw new NoSuchElementException("No Entry with key '" + key + "' exists in the map.");
        }
        return resultNode.getValue();
    }

    public Entry<K,V> getEntry(K key, Entry<K,V> rootNode){
        Entry<K,V> result = null;
        if(rootNode == null){
            return null;
        }
        int comparison = compare(key, rootNode.key);

        if(comparison < 0){
            result = getEntry(key, rootNode.getLeft());
        }
        else if(comparison > 0){
            result = getEntry(key, rootNode.getRight());
        }
        else {
            result = rootNode;
        }

        return result;
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
        if(root == null){
            return null;
        }
        LinkedList<K> result = new LinkedList<>();
        result = toList(root, result);
        return result;
    }

    public LinkedList<K> toList(Entry<K,V> currRoot, LinkedList<K> list){
        if(currRoot != null){
            toList(currRoot.getLeft(), list);
            list.add(currRoot.key);
            toList(currRoot.getRight(), list);
        }
        return list;
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
}
