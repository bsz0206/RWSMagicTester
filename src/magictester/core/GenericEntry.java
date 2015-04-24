package magictester.core;

public class GenericEntry<KeyType, ValueType> {
	 
    private final KeyType key;
    private final ValueType value;
 
    public GenericEntry(KeyType key, ValueType value) {  
        this.key = key;
        this.value = value;
    }
 
    public KeyType getKey() {
        return key;
    }
 
    public ValueType getValue() {
        return value;
    }
 
    @Override
	public String toString() { 
        return "(" + key + ", " + value + ")";  
    }
 
}
