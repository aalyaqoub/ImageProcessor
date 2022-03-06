package controller.commands.reader;

/**
 * A content pair is an object that can hold two different objects.
 *
 * @param <A> generic type A
 * @param <B> generic type B
 */
public class ContentPair<A, B> {
  private final A first;
  private final B second;

  /**
   * Constructs a ContentPair with the given object.
   *
   * @param first  the first object to be put in the pair
   * @param second the second object to be put in the pair
   */
  public ContentPair(A first, B second) {
    if (first == null || second == null) {
      throw new IllegalArgumentException("Can't have null inputs");
    }
    this.first = first;
    this.second = second;
  }

  /**
   * Gets the first object in the pair.
   *
   * @return the first object in the pair
   */
  public A getFirst() {
    return first;
  }

  /**
   * Gets the second object in the pair.
   *
   * @return the second object in the pair
   */
  public B getSecond() {
    return second;
  }
}
