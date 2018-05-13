public class Entry<K, V> {
    public K key;
    public V value;
    public Entry<K,V> left;
    public Entry<K,V> right;

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
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
        if (left == null){
            this.left = null;
        } else this.left = left;
    }

    public Entry<K, V> getRight() {
        return right;
    }

    public void setRight(Entry<K, V> right) {
        if (right == null){
            this.right = null;
        } else this.right = right;
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof Entry) {
            Entry other = (Entry)o;
            return this.key.equals(other.key) && this.value.equals(other.value);
        }
        return false;
    }
}
