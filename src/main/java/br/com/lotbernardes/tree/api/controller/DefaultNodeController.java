package br.com.lotbernardes.tree.api.controller;

import br.com.lotbernardes.tree.api.dto.NodeChildrenLinearViewTransferObject;
import br.com.lotbernardes.tree.api.dto.NodeTransferObject;
import br.com.lotbernardes.tree.api.exception.JsonParsingException;
import br.com.lotbernardes.tree.api.exception.NodeTreeApiException;
import br.com.lotbernardes.tree.api.service.NodeTreeService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import java.util.List;

public class DefaultNodeController implements NodeController {
  private NodeTreeService service;

  public DefaultNodeController(NodeTreeService service) {
    this.service = service;
  }

  @Override
  public ResponseEntity<String> all(@RequestHeader HttpHeaders headers) {
    try {
      NodeTransferObject instance = this.service.getFullTree();
      return new ResponseEntity<>(instance.serialize(), HttpStatus.OK);
    } catch (NodeTreeApiException exception) {
      return this.wrapExceptionMessageSafely(exception);
    }
  }

  @Override
  public ResponseEntity<String> get(@PathVariable Long node, @RequestHeader HttpHeaders headers) {
    try {
      NodeTransferObject instance = this.service.getNodeByIdentifier(node);
      return new ResponseEntity<>(instance.serialize(), HttpStatus.OK);
    } catch (NodeTreeApiException exception) {
      return this.wrapExceptionMessageSafely(exception);
    }
  }

  @Override
  public ResponseEntity<String> getLinearView(@PathVariable Long node, @RequestHeader HttpHeaders headers) {
    try {
      List<NodeChildrenLinearViewTransferObject> instance = this.service.getLinearViewOfNode(node);
      return new ResponseEntity<>(NodeChildrenLinearViewTransferObject.serialize(instance), HttpStatus.OK);
    } catch (NodeTreeApiException exception) {
      return this.wrapExceptionMessageSafely(exception);
    }
  }

  @Override
  public ResponseEntity<String> create(@RequestBody String body, @RequestHeader HttpHeaders headers) {
    try {
      NodeTransferObject instance = this.service.create(NodeTransferObject.deserialize(body));
      return new ResponseEntity<>(instance.serialize(), HttpStatus.CREATED);
    } catch (NodeTreeApiException exception) {
      return this.wrapExceptionMessageSafely(exception);
    }
  }

  @Override
  public ResponseEntity<String> update(@PathVariable Long node, @RequestBody String body, @RequestHeader HttpHeaders headers) {
    try {
      NodeTransferObject instance = this.service.update(NodeTransferObject.deserialize(body));
      return new ResponseEntity<>(instance.serialize(), HttpStatus.ACCEPTED);
    } catch (NodeTreeApiException exception) {
      return this.wrapExceptionMessageSafely(exception);
    }
  }

  @Override
  public ResponseEntity<String> updateWithoutIdentifierOnUrl(@RequestBody String body, @RequestHeader HttpHeaders headers) {
    try {
      NodeTransferObject instance = this.service.update(NodeTransferObject.deserialize(body));
      return new ResponseEntity<>(instance.serialize(), HttpStatus.ACCEPTED);
    } catch (NodeTreeApiException exception) {
      return this.wrapExceptionMessageSafely(exception);
    }
  }

  private ResponseEntity<String> wrapExceptionMessageSafely(NodeTreeApiException cause) {
    try {
      return new ResponseEntity<>(cause.response(), cause.code());
    } catch (JsonParsingException exception) {
      exception.cause().printStackTrace();
      return new ResponseEntity<>("Failure to send response, check back later!", HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
