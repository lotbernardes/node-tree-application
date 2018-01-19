package br.com.lotbernardes.tree.api.exception;

import br.com.lotbernardes.tree.api.dto.RequestFailureTransferObject;
import org.springframework.http.HttpStatus;

public class JsonParsingException extends NodeTreeApiException {
  private static final String DESERIALIZE_EXCEPTION_TYPE = "[ERROR ON DESERIALIZATION] - ";
  private static final String SERIALIZE_EXCEPTION_TYPE = "[ERROR ON SERIALIZATION] - ";

  private Throwable cause;

  private JsonParsingException(HttpStatus code, Throwable cause, RequestFailureTransferObject response) {
    super(code, response);
    this.cause = cause;
  }

  private static JsonParsingException create(HttpStatus code, String type, Throwable cause) {
    String message = new StringBuilder().append(type).append(cause.getMessage()).toString();
    return new JsonParsingException(code, cause, new RequestFailureTransferObject(message, code.name()));
  }

  public static JsonParsingException DESERIALIZATION(Throwable cause) {
    return JsonParsingException.create(HttpStatus.BAD_REQUEST, DESERIALIZE_EXCEPTION_TYPE, cause);
  }

  public static JsonParsingException SERIALIZATION(Throwable cause) {
    return JsonParsingException.create(HttpStatus.BAD_REQUEST, SERIALIZE_EXCEPTION_TYPE, cause);
  }

  public Throwable cause() {
    return this.cause;
  }
}
