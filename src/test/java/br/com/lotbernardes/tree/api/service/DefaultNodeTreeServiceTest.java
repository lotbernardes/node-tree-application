package br.com.lotbernardes.tree.api.service;

import br.com.lotbernardes.tree.api.dto.NodeTransferObject;
import br.com.lotbernardes.tree.api.exception.NodeTreeApiException;
import br.com.lotbernardes.tree.api.exception.NodeTreeServiceException;
import br.com.lotbernardes.tree.domain.Node;
import br.com.lotbernardes.tree.domain.exception.EmptyNodeTreeException;
import br.com.lotbernardes.tree.domain.exception.InvalidNodeInformationException;
import br.com.lotbernardes.tree.domain.exception.NodeNotFoundException;
import br.com.lotbernardes.tree.domain.service.NodeDataService;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;

public class DefaultNodeTreeServiceTest {

  @Rule
  public final ExpectedException exception = ExpectedException.none();

  @Test
  public void should_throw_node_tree_exception_with_no_data_when_data_service_returned_error() throws EmptyNodeTreeException, NodeTreeApiException {
    NodeDataService dataService = Mockito.mock(NodeDataService.class);
    Mockito.when(dataService.getFullTree()).thenThrow(new EmptyNodeTreeException());

    DefaultNodeTreeService service = new DefaultNodeTreeService(dataService);
    this.exception.expect(NodeTreeServiceException.class);
    service.getFullTree();
  }

  @Test
  public void should_throw_node_tree_exception_with_invalid_request_when_data_service_returned_no_data_found() throws NodeTreeApiException, NodeNotFoundException {
    Long identifier = 1L;
    NodeDataService dataService = Mockito.mock(NodeDataService.class);
    Mockito.when(dataService.getByIdentifier(identifier)).thenThrow(new NodeNotFoundException(identifier));

    DefaultNodeTreeService service = new DefaultNodeTreeService(dataService);
    this.exception.expect(NodeTreeServiceException.class);
    service.getNodeByIdentifier(identifier);
  }

  @Test
  public void should_throw_node_tree_exception_with_invalid_request_also_on_linear_get_when_data_service_returned_no_data_found() throws NodeTreeApiException, NodeNotFoundException {
    Long identifier = 1L;
    NodeDataService dataService = Mockito.mock(NodeDataService.class);
    Mockito.when(dataService.getByIdentifier(identifier)).thenThrow(new NodeNotFoundException(identifier));

    DefaultNodeTreeService service = new DefaultNodeTreeService(dataService);
    this.exception.expect(NodeTreeServiceException.class);
    service.getLinearViewOfNode(identifier);
  }

  @Test
  public void should_throw_node_tree_exception_with_invalid_request_on_update_when_data_service_returned_no_data_found() throws InvalidNodeInformationException, NodeTreeApiException, NodeNotFoundException {
    Node node = new Node(1L, "code", "description", "detail", null, null);
    NodeTransferObject dto = NodeTransferObject.wrap(node);

    NodeDataService dataService = Mockito.mock(NodeDataService.class);
    Mockito.when(dataService.getByIdentifier(1L)).thenThrow(new NodeNotFoundException(1L));

    DefaultNodeTreeService service = new DefaultNodeTreeService(dataService);
    this.exception.expect(NodeTreeServiceException.class);
    service.update(dto);
  }

  @Test
  public void should_throw_node_tree_exception_with_invalid_request_on_update_when_data_sent_is_invalid() throws InvalidNodeInformationException, NodeTreeApiException, NodeNotFoundException {
    Node node = new Node(1L, "code", "description", "detail", null, null);
    NodeTransferObject dto = NodeTransferObject.wrap(node);

    NodeDataService dataService = Mockito.mock(NodeDataService.class);
    Mockito.when(dataService.getByIdentifier(1L)).thenReturn(node);
    Mockito.when(dataService.update(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.any())).thenThrow(new InvalidNodeInformationException("field"));

    DefaultNodeTreeService service = new DefaultNodeTreeService(dataService);
    this.exception.expect(NodeTreeServiceException.class);
    service.update(dto);
  }

  @Test
  public void should_throw_exception_when_trying_to_create_a_father_node_without_an_empty_tree() throws NodeNotFoundException, NodeTreeApiException {
    Node node = new Node(null, "code", "description", "detail", null, null);
    NodeTransferObject dto = NodeTransferObject.wrap(node);

    NodeDataService dataService = Mockito.mock(NodeDataService.class);
    Mockito.when(dataService.getByIdentifier(1L)).thenReturn(node);

    DefaultNodeTreeService service = new DefaultNodeTreeService(dataService);
    this.exception.expect(NodeTreeServiceException.class);
    service.create(dto);
  }
}
