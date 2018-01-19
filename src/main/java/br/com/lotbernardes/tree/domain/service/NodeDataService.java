package br.com.lotbernardes.tree.domain.service;

import br.com.lotbernardes.tree.domain.Node;
import br.com.lotbernardes.tree.domain.exception.EmptyNodeTreeException;
import br.com.lotbernardes.tree.domain.exception.InvalidNodeInformationException;
import br.com.lotbernardes.tree.domain.exception.NodeNotFoundException;

public interface NodeDataService {
  Node getFullTree() throws EmptyNodeTreeException;
  Node getByIdentifier(Long identifier) throws NodeNotFoundException;
  Node create(String code, String description, String detail, Node parent) throws InvalidNodeInformationException;
  Node update(String code, String description, String detail, Node reference) throws InvalidNodeInformationException;
}
