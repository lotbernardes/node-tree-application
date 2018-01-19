package br.com.lotbernardes.tree.api.service;

import br.com.lotbernardes.tree.api.dto.NodeChildrenLinearViewTransferObject;
import br.com.lotbernardes.tree.api.dto.NodeTransferObject;
import br.com.lotbernardes.tree.api.exception.NodeTreeApiException;
import br.com.lotbernardes.tree.api.exception.NodeTreeServiceException;
import br.com.lotbernardes.tree.domain.Node;
import br.com.lotbernardes.tree.domain.exception.EmptyNodeTreeException;
import br.com.lotbernardes.tree.domain.exception.InvalidNodeInformationException;
import br.com.lotbernardes.tree.domain.exception.NodeNotFoundException;
import br.com.lotbernardes.tree.domain.service.NodeDataService;
import java.util.List;
import java.util.Objects;

public class DefaultNodeTreeService implements NodeTreeService {
  private NodeDataService service;

  public DefaultNodeTreeService(NodeDataService service) {
    this.service = service;
  }

  @Override
  public NodeTransferObject getFullTree() throws NodeTreeApiException {
    try {
      return NodeTransferObject.wrap(this.service.getFullTree());
    } catch (EmptyNodeTreeException exception) {
      throw NodeTreeServiceException.NO_DATA_FOUND(exception.message());
    }
  }

  @Override
  public NodeTransferObject getNodeByIdentifier(Long identifier) throws NodeTreeApiException {
    try {
      return NodeTransferObject.wrap(this.service.getByIdentifier(identifier));
    } catch (NodeNotFoundException exception) {
      throw NodeTreeServiceException.INVALID_REQUEST(exception.message());
    }
  }

  @Override
  public List<NodeChildrenLinearViewTransferObject> getLinearViewOfNode(Long identifier) throws NodeTreeApiException {
    try {
      return NodeChildrenLinearViewTransferObject.wrap(this.service.getByIdentifier(identifier));
    } catch (NodeNotFoundException exception) {
      throw NodeTreeServiceException.INVALID_REQUEST(exception.message());
    }
  }

  @Override
  public NodeTransferObject update(NodeTransferObject dto) throws NodeTreeApiException {
    try {
      Node node = this.service.update(dto.code, dto.description, dto.detail, this.service.getByIdentifier(dto.identifier));
      return NodeTransferObject.wrap(node);
    } catch (NodeNotFoundException exception) {
      throw NodeTreeServiceException.INVALID_REQUEST(exception.message());
    } catch (InvalidNodeInformationException exception) {
      throw NodeTreeServiceException.INVALID_REQUEST(exception.message());
    }
  }

  @Override
  public NodeTransferObject create(NodeTransferObject dto) throws NodeTreeApiException {
    return (Objects.isNull(dto.parent)) ? this.createOnlyIfNodeTreeIsEmpty(dto) : this.createNodeInsideTheTree(dto);
  }

  private NodeTransferObject createNodeInsideTheTree(NodeTransferObject dto) throws NodeTreeApiException {
    try {
      return this.createUsingDtoAndParentInformation(dto, this.service.getByIdentifier(dto.parent));
    } catch (NodeNotFoundException exception) {
      throw NodeTreeServiceException.INVALID_REQUEST(exception.message());
    }
  }

  private NodeTransferObject createOnlyIfNodeTreeIsEmpty(NodeTransferObject dto) throws NodeTreeApiException {
    try {
      this.service.getFullTree();
      throw NodeTreeServiceException.INVALID_REQUEST("A node with empty parent can only be created on an empty tree!");
    } catch (EmptyNodeTreeException exception) {
      return this.createUsingDtoAndParentInformation(dto, null);
    }
  }

  private NodeTransferObject createUsingDtoAndParentInformation(NodeTransferObject dto, Node parent) throws NodeTreeApiException {
    try {
      return NodeTransferObject.wrap(this.service.create(dto.code, dto.description, dto.detail, parent));
    } catch (InvalidNodeInformationException exception) {
      throw NodeTreeServiceException.INVALID_REQUEST(exception.message());
    }
  }
}
