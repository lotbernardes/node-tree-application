package br.com.lotbernardes.tree.domain.exception;

public class EmptyNodeTreeException extends Exception {
  private static final String DEFAULT_MESSAGE = "Tree was not initialized yet, there are no nodes on it.";

  private String message;

  public EmptyNodeTreeException() {
    super(DEFAULT_MESSAGE);
    this.message = DEFAULT_MESSAGE;
  }

  public String message() {
    return this.message;
  }
}
