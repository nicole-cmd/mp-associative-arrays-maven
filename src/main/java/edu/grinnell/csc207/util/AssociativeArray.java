package edu.grinnell.csc207.util;

import static java.lang.reflect.Array.newInstance;

/**
 * A basic implementation of Associative Arrays with keys of type K
 * and values of type V. Associative Arrays store key/value pairs
 * and permit you to look up values by key.
 *
 * @param <K> the key type
 * @param <V> the value type
 *
 * @author Nicole Gorrell
 * @author Samuel A. Rebelsky
 */
public class AssociativeArray<K, V> {
  // +-----------+---------------------------------------------------
  // | Constants |
  // +-----------+

  /**
   * The default capacity of the initial array.
   */
  static final int DEFAULT_CAPACITY = 16;

  // +--------+------------------------------------------------------
  // | Fields |
  // +--------+

  /**
   * The size of the associative array (the number of key/value pairs).
   */
  int size;

  /**
   * The array of key/value pairs.
   */
  KVPair<K, V>[] pairs;

  // +--------------+------------------------------------------------
  // | Constructors |
  // +--------------+

  /**
   * Create a new, empty associative array.
   */
  @SuppressWarnings({ "unchecked" })
  public AssociativeArray() {
    // Creating new arrays is sometimes a PITN.
    this.pairs = (KVPair<K, V>[]) newInstance((new KVPair<K, V>()).getClass(),
        DEFAULT_CAPACITY);
    this.size = 0;
  } // AssociativeArray()

  // +------------------+--------------------------------------------
  // | Standard Methods |
  // +------------------+

  /**
   * Create a copy of this AssociativeArray.
   *
   * @return a new copy of the array
   */
  public AssociativeArray<K, V> clone() {
    AssociativeArray<K, V> newArray = new AssociativeArray<>(); // create new array

    for(int i = 0; i < this.size; i ++) {
        // assign this key/val to placement in array copy
        newArray.pairs[i].key = this.pairs[i].key;
        newArray.pairs[i].val = this.pairs[i].val;
    } // for

    return newArray;
  } // clone()

  /**
   * Convert the array to a string.
   *
   * @return a string of the form "{Key0:Value0, Key1:Value1, ... KeyN:ValueN}"
   */
  public String toString() {
    String output = "{";

    for(int i = 0; i < this.size; i++) {
      output += this.pairs[i].toString();

      if(i < this.size - 1) {
      output += ", ";
      } // if
    } // for

    output += "}";
    return output;
  } // toString()

  // +----------------+----------------------------------------------
  // | Public Methods |
  // +----------------+

  /**
   * Set the value associated with key to value. Future calls to
   * get(key) will return value.
   *
   * @param key
   *   The key whose value we are seeting.
   * @param value
   *   The value of that key.
   *
   * @throws NullKeyException
   *   If the client provides a null key.
   */
  public void set(K key, V value) throws NullKeyException {
    if(key == null) { // null cannot be a valid key
      throw new NullKeyException();   
    } // if

    if (this.size == 0) { // if the array is created but uninitialized
      this.pairs[0] = new KVPair<K,V>(key, value); // create new KVPair to set key/value
      this.size++; // increment associative array by one for null space at the end
      return;
    } // if

    if (this.size != 0) {
      if (this.hasKey(key)) {
        for(int i = 0; i < this.size; i++) { // if key exists, cycle through to override current value
          this.pairs[i].val = value;
        } // for
      } // if

      // else - increment assoc. array size to make room at end for new KVPair 
      this.size++;
      this.pairs[this.size - 1] = new KVPair<K, V>(key, value); 
    } // if

    return;
  } // set(K,V)

  /**
   * Get the value associated with key.
   *
   * @param key
   *   A key
   *
   * @return
   *   The corresponding value
   *
   * @throws KeyNotFoundException
   *   when the key is null or does not appear in the associative array.
   */
  public V get(K key) throws KeyNotFoundException {
    for(int i = 0; i < this.size; i++) { // cycles through array to see if there is a matching key
      if(this.pairs[i].key == null) {
        throw new KeyNotFoundException(); // if there is a null key
      } else if(this.pairs[i].key == key) {
        return this.pairs[i].val;
      } // if...else
    } // for
    
    // when key is not found in array loop, will throw KeyNotFound exception
    throw new KeyNotFoundException();
  } // get(K)

  /**
   * Determine if key appears in the associative array. Should
   * return false for the null key, since it cannot appear.
   *
   * @param key
   *   The key we're looking for.
   *
   * @return true if the key appears and false otherwise.
   */
  public boolean hasKey(K key) {
    for(int i = 0; i < this.size; i++) { // cycles through array to see if there is a matching key
      if(this.pairs[i].key == key) {
        return true;
      }  // if
    } // for
    return false; // if key is not there/key is null
  } // hasKey(K)

  /**
   * Remove the key/value pair associated with a key. Future calls
   * to get(key) will throw an exception. If the key does not appear
   * in the associative array, does nothing.
   *
   * @param key
   *   The key to remove.
   */
  public void remove(K key) {
    if(this.hasKey(key)) { // do nothing if array doesn't have the key
      for(int i = 0; i < this.size; i++) { // looks for the key
        if(this.pairs[i].key == key) {
          // assign the key/val pairs in this position to subsequent pair (updates val, too)
          this.pairs[i].key = this.pairs[i++].key;
          System.out.println("new key: " + this.pairs[i].key);
          System.out.println("new val: " + this.pairs[i].val);
          this.pairs[i--] = this.pairs[i]; 
          // get rid of extra space by decrementing size
          this.size--;
          return;
        } // if
      } // for
    } // if

    return;
  } // remove(K)

  /**
   * Determine how many key/value pairs are in the associative array.
   *
   * @return The number of key/value pairs in the array.
   */
  public int size() {
    return this.size;
  } // size()

  // +-----------------+---------------------------------------------
  // | Private Methods |
  // +-----------------+

  /**
   * Expand the underlying array.
   */
  void expand() {
    this.pairs = java.util.Arrays.copyOf(this.pairs, this.pairs.length * 2);
  } // expand()

  /**
   * Find the index of the first entry in `pairs` that contains key.
   * If no such entry is found, throws an exception.
   *
   * @param key
   *   The key of the entry.
   *
   * @return
   *   The index of the key, if found.
   *
   * @throws KeyNotFoundException
   *   If the key does not appear in the associative array.
   */
  int find(K key) throws KeyNotFoundException {
    throw new KeyNotFoundException();   // STUB
  } // find(K)

} // class AssociativeArray
