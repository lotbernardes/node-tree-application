package br.com.lotbernardes.tree.api.service;

import br.com.lotbernardes.tree.api.dto.NodeChildrenLinearViewTransferObject;
import br.com.lotbernardes.tree.api.dto.NodeTransferObject;
import br.com.lotbernardes.tree.api.exception.NodeTreeApiException;
import java.util.List;

public interface NodeTreeService {
  NodeTransferObject getFullTree() throws NodeTreeApiException;
  NodeTransferObject getNodeByIdentifier(Long identifier) throws NodeTreeApiException;
  List<NodeChildrenLinearViewTransferObject> getLinearViewOfNode(Long identifier) throws NodeTreeApiException;
  NodeTransferObject create(NodeTransferObject dto) throws NodeTreeApiException;
  NodeTransferObject update(NodeTransferObject dto) throws NodeTreeApiException;
}
