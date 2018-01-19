package br.com.lotbernardes.tree.api.exception;

import br.com.lotbernardes.tree.api.dto.RequestFailureTransferObject;
import org.springframework.http.HttpStatus;

public class NodeTreeServiceException extends NodeTreeApiException {
  private NodeTreeServiceException(HttpStatus code, RequestFailureTransferObject response) {
    super(code, response);
  }

  private static NodeTreeServiceException create(HttpStatus code, String message) {
    return new NodeTreeServiceException(code, new RequestFailureTransferObject(message, code.name()));
  }

  public static NodeTreeServiceException NO_DATA_FOUND(String message) {
    return NodeTreeServiceException.create(HttpStatus.BAD_REQUEST, message);
  }

  public static NodeTreeServiceException INVALID_REQUEST(String message) {
    return NodeTreeServiceException.create(HttpStatus.BAD_REQUEST, message);
  }
}
