package br.com.lotbernardes.tree.api.dto;

import br.com.lotbernardes.tree.api.dto.utils.JsonParser;
import br.com.lotbernardes.tree.api.exception.JsonParsingException;

public class RequestFailureTransferObject {
  public String message;
  public String status;

  public RequestFailureTransferObject(String message, String status) {
    this.message = message;
    this.status = status;
  }

  public String serialize() throws JsonParsingException {
    return JsonParser.serialize(this);
  }
}
