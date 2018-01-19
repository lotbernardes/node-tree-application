package br.com.lotbernardes.tree.api.exception;

import br.com.lotbernardes.tree.api.dto.RequestFailureTransferObject;
import org.springframework.http.HttpStatus;

public class NodeTreeApiException extends Exception {
  private HttpStatus code;
  private RequestFailureTransferObject response;

  protected NodeTreeApiException(HttpStatus code, RequestFailureTransferObject response) {
    this.code = code;
    this.response = response;
  }

  public HttpStatus code() {
    return this.code;
  }

  public String response() throws JsonParsingException {
    return this.response.serialize();
  }
}
