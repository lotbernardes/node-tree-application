package br.com.lotbernardes.tree.domain.exception;

public class InvalidNodeInformationException extends Exception {
  private String message;

  public InvalidNodeInformationException(String field) {
    this.message = new StringBuilder()
      .append("Field(value: ")
      .append(field).append(") provided for node creation is either null or empty")
      .toString();
  }

  public String message() {
    return this.message;
  }
}
