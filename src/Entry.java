public class Entry<K, V> {
    public final K key;
    public final V value;
    public boolean color;
    public Entry<K,V> left;
    public Entry<K,V> parent;

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
        this.color = false;
    }

    public V getValue(){
        if (value == null){
            return null;
        } return value;
    }

    public Entry<K, V> getLeft() {
        return left;
    }

    public void setLeft(Entry<K, V> left) {
        this.left = left;
    }

    public Entry<K, V> getRight() {
        return right;
    }

    public void setRight(Entry<K, V> right) {
        this.right = right;
    }

    public Entry<K,V> right;




    @Override
    public boolean equals(Object o) {
        if (o instanceof Entry) {
            Entry other = (Entry)o;
            return this.key.equals(other.key) && this.value.equals(other.value);
        }
        return false;
    }
}
