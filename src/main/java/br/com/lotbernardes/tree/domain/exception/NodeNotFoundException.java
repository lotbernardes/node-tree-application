package br.com.lotbernardes.tree.domain.exception;

public class NodeNotFoundException extends Exception {
  private static final String DEFAULT_MESSAGE = "Node not found for identifier: ";

  private String message;

  public NodeNotFoundException(Long identifier) {
    this.message = new StringBuilder().append(DEFAULT_MESSAGE).append(String.valueOf(identifier)).toString();
  }

  public String message() {
    return this.message;
  }
}
