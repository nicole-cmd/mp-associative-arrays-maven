package edu.grinnell.csc207.experiments;

import edu.grinnell.csc207.util.AssociativeArray;

import java.io.PrintWriter;

import java.lang.Integer;

/**
 * Experiments with Associative Arrays.
 *
 * @author Samuel A. Rebelsky
 * @author Your Name Here
 */
public class AAExperiments {

  // +---------+-----------------------------------------------------
  // | Globals |
  // +---------+

  /**
   * Log and conduct a call to `set`.
   *
   * @param pen
   *   Where to log the message.
   * @param aa
   *   The associative array we're using.
   * @param key
   *   The key to set.
   * @param val
   *   The value to set.
   */
  public static void set(PrintWriter pen, AssociativeArray<String, String> aa,
      String key, String val) {
    pen.printf("set(\"%s\", \"%s\") -> ", key, val);
    try {
      aa.set(key, val);
      pen.println("OK");
    } catch (Exception e) {
      pen.println("FAILED because " + e.toString());
    } // try/catch
  } // set(PrintWriter, AssociativeArray<String, String>, String, String)

  /**
   * Log and conduct a call to `get`.
   *
   * @param pen
   *   Where to log the message.
   * @param aa
   *   The associative array.
   * @param key
   *   The key.
   */
  public static void get(PrintWriter pen, AssociativeArray<String, String> aa,
      String key) {
    pen.printf("get(\"%s\") -> ", key);
    try {
      pen.println(aa.get(key));
    } catch (Exception e) {
      pen.println("[FAILED because " + e.toString() + "]");
    } // try/catch
  } // get(PrintWriter, AssociativeArray<String, String>, STring)

  /**
   * Log and conduct a call to `hasKey`.
   *
   * @param pen
   *   Where to log the message.
   * @param aa
   *   The associative array.
   * @param key
   *   The key.
   */
  public static void hasKey(PrintWriter pen,
      AssociativeArray<String, String> aa, String key) {
    pen.printf("hasKey(\"%s\") -> ", key);
    try {
      pen.println(aa.hasKey(key));
    } catch (Exception e) {
      pen.println("[FAILED because " + e.toString() + "]");
    } // try/catch
  } // hasKey(PrintWriter, AssociativeArray<String, String>, STring)

  // +------+--------------------------------------------------------
  // | Main |
  // +------+

  /**
   * Run our expereiments.
   *
   * @param args
   *   Command-line parameters. (Ignored.)
   *
   * @throws Exception
   *   When something goes wrong. Usually an I/O issue or an unexpected
   *   Associative Array hiccup.
   */
  public static void main(String[] args) throws Exception {
    PrintWriter pen = new PrintWriter(System.out, true);

    AssociativeArray strings2strings = new AssociativeArray<String, String>();

    // The empty array should not have any key. We'll try one.
    hasKey(pen, strings2strings, "k");

    // However, after setting that key, we should be able to get it.
    set(pen, strings2strings, "k", "key");
    hasKey(pen, strings2strings, "k");
    get(pen, strings2strings, "k");

    // What happens if we try a different key?
    hasKey(pen, strings2strings, "q");
    get(pen, strings2strings, "q");

    // What happens if we try the null key?
    set(pen, strings2strings, null, "nothing");
    hasKey(pen, strings2strings, null);
    get(pen, strings2strings, null);

    /** Additional experiments */ 
    set(pen, strings2strings, "2", "hey"); // set another key
    pen.println(".toString test: " + strings2strings.toString()); // ensure .toString() can facilitate printing of whole array

    // testing clone
    AssociativeArray stringsClone = new AssociativeArray<String, String>(); 
    stringsClone = strings2strings.clone(); // assign the clone to the new, uninitialized array
    pen.println(".clone() test: " + stringsClone.toString());
    stringsClone.remove("k");
    pen.println(stringsClone.toString()); // see if the previous line successfully removed key "k" from cloned array

    // testing different types - Integers
    AssociativeArray intArray = new AssociativeArray<Integer, Integer>();
    intArray.set(1, 2);
    intArray.set(3, 4);

    pen.println(intArray.toString()); // utilize .toString to print out type <Integer, Integer> array
    pen.println(intArray.get(1).toString()); // same idea to see what value get(1) returns (global print methods only work for String types)

    intArray.remove(1);
    pen.println(intArray.toString()); // see what happens after removing key 1

    // And we're done.
    pen.close();
  } // main(String[])
} // class AAExperiments
