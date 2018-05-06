import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;

public class SortedTreeMap<K extends Comparable<? super K>, V> implements ISortedTreeMap {

    public SortedTreeMap(){

    }
    /**
     * Finds the minimum value in the map, if no value is found, returns null instead.
     *
     * @return minimum value
     */
    @Override
    public Entry min() {
        return null;
    }

    /**
     * Finds the maximum value in the map, if no value is found returns null instead.
     *
     * @return maximum value
     */
    @Override
    public Entry max() {
        return null;
    }

    /**
     * Inserts the specified value with the specified key as a new entry into the map.
     * If the value is already present, return the previous value, else null.
     *
     * @param key   The key to be inserted
     * @param value The value to be inserted
     * @return Previous value
     */
    @Override
    public Object add(Comparable key, Object value) {
        return null;
    }

    /**
     * Inserts the specified entry into the map. If the key is already a part of the map,
     * return the previous value, else null.
     *
     * @param entry The new entry to be inserted into the map
     * @return Previous value
     */
    @Override
    public Object add(Entry entry) {
        return null;
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
    public void replace(Comparable key, Object value) throws NoSuchElementException {

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
    public void replace(Comparable key, BiFunction f) throws NoSuchElementException {

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
    public Object remove(Object key) throws NoSuchElementException {
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
    public Object getValue(Object key) throws NoSuchElementException {
        return null;
    }

    /**
     * Checks if a key is in the map.
     *
     * @param key The key to check
     * @return true if the key is in the map, false otherwise
     */
    @Override
    public boolean containsKey(Comparable key) {
        return false;
    }

    /**
     * Checks if a value is in the map
     *
     * @param value the value to look for
     * @return True if the value is present, false otherwise
     */
    @Override
    public boolean containsValue(Object value) {
        return false;
    }

    /**
     * Finds all the keys in the map and returns them in order.
     *
     * @return keys in order
     */
    @Override
    public Iterable keys() {
        return null;
    }

    /**
     * Finds the values in order of the keys.
     *
     * @return values in order of the keys
     */
    @Override
    public Iterable values() {
        return null;
    }

    /**
     * Finds all entries in the map in order of the keys.
     *
     * @return All entries in order of the keys
     */
    @Override
    public Iterable<Entry> entries() {
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
    public Entry higherOrEqualEntry(Comparable key) {
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
    public Entry lowerOrEqualEntry(Comparable key) {
        return null;
    }

    /**
     * Adds all entries in the other map into the current map. If a key is present
     * in both maps, the key in the other map takes precedent.
     *
     * @param other The map to add to the current map.
     */
    @Override
    public void merge(ISortedTreeMap other) {

    }

    /**
     * Removes any entry for which the predicate holds true. The predicate can
     * trigger on both the key and value of each entry.
     *
     * @param p The predicate that tests which entries should be kept.
     */
    @Override
    public void removeIf(BiPredicate p) {

    }

    /**
     * Checks if the map is empty
     *
     * @return True if the map is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        return false;
    }

    /**
     * Returns the number of entries in the map
     *
     * @return Number of entries
     */
    @Override
    public int size() {
        return 0;
    }

    /**
     * Clears the map of entries.
     */
    @Override
    public void clear() {

    }
}
