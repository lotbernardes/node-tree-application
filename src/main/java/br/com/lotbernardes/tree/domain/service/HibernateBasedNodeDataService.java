package br.com.lotbernardes.tree.domain.service;

import br.com.lotbernardes.tree.domain.Node;
import br.com.lotbernardes.tree.domain.exception.EmptyNodeTreeException;
import br.com.lotbernardes.tree.domain.exception.InvalidNodeInformationException;
import br.com.lotbernardes.tree.domain.exception.NodeNotFoundException;
import br.com.lotbernardes.tree.domain.repository.NodeRepository;
import org.h2.util.StringUtils;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class HibernateBasedNodeDataService implements NodeDataService {
  private NodeRepository repository;

  public HibernateBasedNodeDataService(NodeRepository repository) {
    this.repository = repository;
  }

  @Override
  public Node getFullTree() throws EmptyNodeTreeException {
    Optional<Node> promise = this.repository.findOptionalByParent_IdentifierIsNull();

    if (!promise.isPresent()) {
      throw new EmptyNodeTreeException();
    }

    return promise.get();
  }

  @Override
  public Node getByIdentifier(Long identifier) throws NodeNotFoundException {
    Optional<Node> promise = this.repository.findOptionalByIdentifier(identifier);

    if (!promise.isPresent()) {
      throw new NodeNotFoundException(identifier);
    }

    return promise.get();
  }

  @Override
  public Node create(String code, String description, String detail, Node parent) throws InvalidNodeInformationException {
    return this.save(new Node(code, description, detail, parent));
  }

  @Override
  public Node update(String code, String description, String detail, Node reference) throws InvalidNodeInformationException {
    reference.code = code;
    reference.description = description;
    reference.detail = detail;
    return this.save(reference);
  }

  private Node save(Node node) throws InvalidNodeInformationException {
    if (StringUtils.isNullOrEmpty(node.code)) {
      throw new InvalidNodeInformationException("code");
    }

    if (StringUtils.isNullOrEmpty(node.description)) {
      throw new InvalidNodeInformationException("description");
    }

    if (StringUtils.isNullOrEmpty(node.detail)) {
      throw new InvalidNodeInformationException("detail");
    }

    return this.repository.save(node);
  }
}
