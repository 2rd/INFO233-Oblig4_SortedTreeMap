import java.util.*;
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

    private Entry<K,V> min(Entry<K,V> rootNode){
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
    private Entry<K, V> max(Entry<K,V> rootNode) {
        if(rootNode == null){
            return null;
        }
        Entry<K,V> max = rootNode;
        while (max.getRight() != null){
            max = max.getRight();
            rootNode = rootNode.getRight();
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
        //System.out.println("legger til " + key +",   value: " + value);
        V result;
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
        V result;
        if(getRoot() == null){

            setRoot(entry);
            size++;
            return null;
        }
        if(entry == null){
            return null;
        }
        //System.out.println("---entry:"+entry.getValue() + "     root:"+getRoot().getValue());
        result = addEntry(entry, getRoot());

        return result;
    }

    private V addEntry(Entry<K,V> newEntry, Entry<K,V> rootNode){
        V result = null;

        int comparison = compare(newEntry.key, rootNode.key);

        if(comparison == 0){
            result = rootNode.getValue();
            rootNode.value = newEntry.getValue();
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
        Entry<K,V> toReplace = getEntry(key, root);
        if(toReplace == null) {
            throw new NoSuchElementException("No entry with key '" + key + "' in treemap");
        }
        add(key, value);
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
        Entry<K,V> toReplace = getEntry(key, root);
        if(toReplace == null) {
            throw new NoSuchElementException("No entry with key '" + key + "' in treemap");
        }
        toReplace.value = f.apply(toReplace.key, toReplace.getValue());
        add(toReplace);
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
        root = removeEntry((K) key, root);
        size--;
        return result;
    }

    private Entry<K,V> removeEntry(K key, Entry<K, V> toRemove){
        if(toRemove == null) {
            return null;
        }

        int comparison = compare(key, toRemove.key);

        if(comparison < 0){
            toRemove.setLeft(removeEntry(key, toRemove.getLeft()));
        }
        else if(comparison > 0){
            toRemove.setRight(removeEntry(key, toRemove.getRight()));
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
            toRemove.setRight(removeEntry(toRemove.key, toRemove.getRight()));
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

    private Entry<K,V> getEntry(K key, Entry<K, V> rootNode){
        Entry<K,V> result;
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
        return getEntry(key, root) != null;
    }

    /**
     * Checks if a value is in the map
     *
     * @param value the value to look for
     * @return True if the value is present, false otherwise
     */
    @Override
    public boolean containsValue(V value) {
        Iterable<V> values = values();

        for (V value1 : values) {
            if (value1.equals(value)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Finds all the keys in the map and returns them in order.
     *
     * @return keys in order
     */
    @Override
    public Iterable<K> keys() {
        LinkedList<K> result = new LinkedList<>();
        result = keyList(root, result);
        return result;
    }

    private LinkedList<K> keyList(Entry<K,V> currRoot, LinkedList<K> list){
        if(currRoot != null){
            keyList(currRoot.getLeft(), list);
            list.add(currRoot.key);
            keyList(currRoot.getRight(), list);
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

        LinkedList<V> result = new LinkedList<>();
        result = valueList(root, result);
        return result;
    }

    private LinkedList<V> valueList(Entry<K,V> currRoot, LinkedList<V> list){
        if(currRoot != null){
            valueList(currRoot.getLeft(), list);
            list.add(currRoot.getValue());
            valueList(currRoot.getRight(), list);
        }
        return list;
    }

    /**
     * Finds all entries in the map in order of the keys.
     *
     * @return All entries in order of the keys
     */
    @Override
    public Iterable<Entry<K, V>> entries() {
        Stack<Entry<K,V>> result = new Stack<>();
        if (root == null) {
            return result;
        }
        //keep the nodes in the path that are waiting to be visited
        Entry<K,V> entry = root;
        //first entry to be visited will be the left one
        while (entry != null) {
            result.push(entry);
            entry = entry.left;
        }

        // traverse the tree
        while (result.size() > 0) {

            // visit the top entry
            entry = result.pop();
            //System.out.print(entry.key + " ");
            if (entry.right != null) {
                entry = entry.right;

                // the next entry to be visited is the leftmost
                while (entry != null) {
                    result.push(entry);
                    entry = entry.left;
                }
            }
        }

        return result;

    }

    public Iterable<Entry<K,V>> entries1(){
        LinkedList<Entry<K,V>> result = new LinkedList<>();
        result = entryList(root, result);
        return result;
    }

    private LinkedList<Entry<K,V>> entryList(Entry<K,V> currRoot, LinkedList<Entry<K,V>> list){
        if(currRoot != null){
            entryList(currRoot.getLeft(), list);
            list.add(currRoot);
            entryList(currRoot.getRight(), list);
        }
        System.out.println("::::"+list.size());
        return list;
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
        if(root == null || compare(key, max().key) > 0){
            return null;
        }
        if (getEntry(key, root) != null){
            return getEntry(key, root);
        }
        return getHigherOrEqual(key, root);
    }
    private Entry<K,V> getHigherOrEqual(K key, Entry<K,V> rootNode) {
        if (rootNode == null) {
            return null;
        }

        int comparison = compare(rootNode.key, key);

        if (rootNode.getLeft() == null && rootNode.getRight() == null && comparison < 0) {
            return rootNode;
        } else if ((comparison >= 0 && rootNode.left == null) || (comparison >= 0 && compare(max(rootNode.getLeft()).key, key) < 0)) {
            return rootNode;
        } else if (comparison <= 0) {
            return getHigherOrEqual(key, rootNode.getRight());
        } else {
            return getHigherOrEqual(key, rootNode.getLeft());
        }
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
        if(root == null || compare(key, min().key) < 0){
            return null;
        }
        if (getEntry(key, root) != null){
            return getEntry(key, root);
        }
        return getLowerOrEqual(key, root);
    }

    private Entry<K,V> getLowerOrEqual(K key, Entry<K,V> rootNode){
        if(rootNode == null){
            return null;
        }

        int comparison = compare(rootNode.key, key);

        if(rootNode.getLeft() == null && rootNode.getRight() == null && comparison > 0){
            //System.out.println("node '"+rootNode.key + "' er den største som er mindre enn '" + key + "'");
            return rootNode;
        }
        else if((comparison <= 0 && rootNode.right == null) || (comparison <= 0 && compare(min(rootNode.getRight()).key, key) > 0)){
            //System.out.println("Node "+ rootNode.key + " <= " + key + "(key). og " + rootNode.key + " sin høyre er null.");
            //System.out.println("Eller: " + "Node "+ rootNode.key + " <= " + key + "(key). og " + rootNode.key + " sin høyre  er større enn " + key);
            //System.out.println("returnerer " + rootNode.key);
            return rootNode;
        }
        else if(comparison >= 0){
            //System.out.println(rootNode.key + " er større enn søkeresultatet av " + key + "(key) i "+ rootNode.key+" sin venstre side.");
            return getLowerOrEqual(key, rootNode.getLeft());
        }
        else{
            //System.out.println(rootNode.key + " er mindre enn søkeresultatet av " + key + "(key) i "+ rootNode.key+" sin høyre side.");
            return getLowerOrEqual(key, rootNode.getRight());
        }
    }

    @Override
    public void merge(ISortedTreeMap<K, V> other) {

        Iterable<Entry<K,V>> otherEntries = other.entries();

        for (Entry<K,V> entry : otherEntries) {
            this.add(entry);
        }

    }

    /**
     * Removes any entry for which the predicate holds true. The predicate can
     * trigger on both the key and value of each entry.
     *
     * @param p The predicate that tests which entries should be kept.
     */
    @Override
    public void removeIf(BiPredicate<K, V> p) {
        Iterable<Entry<K,V>> entries = entries1();

        for (Entry<K,V> entry : entries) {
            if (p.test(entry.key, entry.getValue())) {
                remove(entry.key);
            }
        }

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

    private int compare(K key1, K key2){
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
