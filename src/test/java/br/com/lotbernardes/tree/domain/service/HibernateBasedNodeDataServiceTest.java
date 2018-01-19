package br.com.lotbernardes.tree.domain.service;

import br.com.lotbernardes.tree.domain.Node;
import br.com.lotbernardes.tree.domain.exception.EmptyNodeTreeException;
import br.com.lotbernardes.tree.domain.exception.InvalidNodeInformationException;
import br.com.lotbernardes.tree.domain.exception.NodeNotFoundException;
import br.com.lotbernardes.tree.domain.repository.NodeRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class HibernateBasedNodeDataServiceTest {

  @Rule
  public final ExpectedException exception = ExpectedException.none();

  @Test
  public void should_throw_empty_node_tree_exception_when_tree_does_not_have_any_nodes() throws EmptyNodeTreeException {
    NodeRepository repository = Mockito.mock(NodeRepository.class);
    Mockito.when(repository.findOptionalByParent_IdentifierIsNull()).thenReturn(Optional.empty());
    HibernateBasedNodeDataService service = new HibernateBasedNodeDataService(repository);

    this.exception.expect(EmptyNodeTreeException.class);
    service.getFullTree();
  }

  @Test
  public void should_return_tree_data_correctly_when_there_is_data_to_show() throws EmptyNodeTreeException {
    Node node = new Node(1L, "Code", "Description", "Detail", null, new ArrayList<>());

    NodeRepository repository = Mockito.mock(NodeRepository.class);
    Mockito.when(repository.findOptionalByParent_IdentifierIsNull()).thenReturn(Optional.of(node));

    HibernateBasedNodeDataService service = new HibernateBasedNodeDataService(repository);
    Node result = service.getFullTree();

    assertEquals("Instance returned should match the created one", node.identifier, result.identifier);
  }

  @Test
  public void should_throw_node_not_found_exception_when_node_requested_does_not_exists() throws NodeNotFoundException {
    Long identifierRequested = 1L;

    NodeRepository repository = Mockito.mock(NodeRepository.class);
    Mockito.when(repository.findOptionalByIdentifier(identifierRequested)).thenReturn(Optional.empty());
    HibernateBasedNodeDataService service = new HibernateBasedNodeDataService(repository);

    this.exception.expect(NodeNotFoundException.class);
    service.getByIdentifier(identifierRequested);
  }

  @Test
  public void should_return_single_node_data_correctly_when_there_is_data_to_show() throws NodeNotFoundException {
    Long identifierRequested = 1L;
    Node node = new Node(identifierRequested, "Code", "Description", "Detail", null, new ArrayList<>());

    NodeRepository repository = Mockito.mock(NodeRepository.class);
    Mockito.when(repository.findOptionalByIdentifier(identifierRequested)).thenReturn(Optional.of(node));

    HibernateBasedNodeDataService service = new HibernateBasedNodeDataService(repository);
    Node result = service.getByIdentifier(identifierRequested);

    assertEquals("Instance returned should match the created one", node.identifier, result.identifier);
  }

  @Test
  public void should_save_instance_correctly_when_all_data_necessary_is_present() throws InvalidNodeInformationException {
    String code = "Code";
    String description = "Description";
    String detail = "Detail";
    Node parent = null;
    List<Node> children = new ArrayList<>();
    Node node = new Node(null, code, description, detail, parent, children);

    NodeRepository repository = Mockito.mock(NodeRepository.class);
    Mockito.when(repository.save(Mockito.any(Node.class))).thenReturn(node);

    HibernateBasedNodeDataService service = new HibernateBasedNodeDataService(repository);
    Node result = service.create(code, description, detail, parent);

    assertEquals("Instance returned code should match the created one", node.code, result.code);
    assertEquals("Instance returned description should match the created one", node.description, result.description);
    assertEquals("Instance returned detail should match the created one", node.detail, result.detail);
  }

  @Test
  public void should_update_instance_correctly_when_all_data_necessary_is_present() throws InvalidNodeInformationException {
    String code = "Code";
    String description = "Description";
    String detail = "Detail";
    String codeNew = "Code Updated";
    String descriptionNew = "Description Updated";
    String detailNew = "Detail Updated";
    Node parent = null;
    List<Node> children = new ArrayList<>();
    Node node = new Node(null, code, description, detail, parent, children);
    Node nodeUpdated = new Node(null, codeNew, descriptionNew, detailNew, parent, children);

    NodeRepository repository = Mockito.mock(NodeRepository.class);
    Mockito.when(repository.save(Mockito.any(Node.class))).thenReturn(nodeUpdated);

    HibernateBasedNodeDataService service = new HibernateBasedNodeDataService(repository);
    Node result = service.create(code, description, detail, parent);

    assertEquals("Instance returned code should match the updated one", nodeUpdated.code, result.code);
    assertEquals("Instance returned description should match the updated one", nodeUpdated.description, result.description);
    assertEquals("Instance returned detail should match the updated one", nodeUpdated.detail, result.detail);
    assertNotEquals("Instance returned code should not match the old one", node.code, result.code);
    assertNotEquals("Instance returned description should not match the old one", node.description, result.description);
    assertNotEquals("Instance returned detail should not match the old one", node.detail, result.detail);
  }

  @Test
  public void should_throw_invalid_node_information_exception_when_there_is_not_a_valid_code_on_node() throws InvalidNodeInformationException {
    String code = "";
    String description = "Description";
    String detail = "Detail";
    String codeNew = "Code Updated";
    String descriptionNew = "Description Updated";
    String detailNew = "Detail Updated";
    Node parent = null;
    List<Node> children = new ArrayList<>();
    Node node = new Node(null, code, description, detail, parent, children);
    Node nodeUpdated = new Node(null, codeNew, descriptionNew, detailNew, parent, children);

    NodeRepository repository = Mockito.mock(NodeRepository.class);
    Mockito.when(repository.save(Mockito.any(Node.class))).thenReturn(nodeUpdated);

    HibernateBasedNodeDataService service = new HibernateBasedNodeDataService(repository);
    this.exception.expect(InvalidNodeInformationException.class);
    Node result = service.create(code, description, detail, parent);
  }

  @Test
  public void should_throw_invalid_node_information_exception_when_there_is_not_a_valid_description_on_node() throws InvalidNodeInformationException {
    String code = "Code";
    String description = "";
    String detail = "Detail";
    String codeNew = "Code Updated";
    String descriptionNew = "Description Updated";
    String detailNew = "Detail Updated";
    Node parent = null;
    List<Node> children = new ArrayList<>();
    Node node = new Node(null, code, description, detail, parent, children);
    Node nodeUpdated = new Node(null, codeNew, descriptionNew, detailNew, parent, children);

    NodeRepository repository = Mockito.mock(NodeRepository.class);
    Mockito.when(repository.save(Mockito.any(Node.class))).thenReturn(nodeUpdated);

    HibernateBasedNodeDataService service = new HibernateBasedNodeDataService(repository);
    this.exception.expect(InvalidNodeInformationException.class);
    Node result = service.create(code, description, detail, parent);
  }

  @Test
  public void should_throw_invalid_node_information_exception_when_there_is_not_a_valid_detail_on_node() throws InvalidNodeInformationException {
    String code = "Code";
    String description = "Description";
    String detail = "";
    String codeNew = "Code Updated";
    String descriptionNew = "Description Updated";
    String detailNew = "Detail Updated";
    Node parent = null;
    List<Node> children = new ArrayList<>();
    Node node = new Node(null, code, description, detail, parent, children);
    Node nodeUpdated = new Node(null, codeNew, descriptionNew, detailNew, parent, children);

    NodeRepository repository = Mockito.mock(NodeRepository.class);
    Mockito.when(repository.save(Mockito.any(Node.class))).thenReturn(nodeUpdated);

    HibernateBasedNodeDataService service = new HibernateBasedNodeDataService(repository);
    this.exception.expect(InvalidNodeInformationException.class);
    Node result = service.create(code, description, detail, parent);
  }

  @Test
  public void should_throw_invalid_node_information_exception_when_there_is_a_null_code_on_node() throws InvalidNodeInformationException {
    String code = null;
    String description = "Description";
    String detail = "Detail";
    String codeNew = "Code Updated";
    String descriptionNew = "Description Updated";
    String detailNew = "Detail Updated";
    Node parent = null;
    List<Node> children = new ArrayList<>();
    Node node = new Node(null, code, description, detail, parent, children);
    Node nodeUpdated = new Node(null, codeNew, descriptionNew, detailNew, parent, children);

    NodeRepository repository = Mockito.mock(NodeRepository.class);
    Mockito.when(repository.save(Mockito.any(Node.class))).thenReturn(nodeUpdated);

    HibernateBasedNodeDataService service = new HibernateBasedNodeDataService(repository);
    this.exception.expect(InvalidNodeInformationException.class);
    Node result = service.create(code, description, detail, parent);
  }

  @Test
  public void should_throw_invalid_node_information_exception_when_there_is_a_null_detail_on_node() throws InvalidNodeInformationException {
    String code = "Code";
    String description = "Description";
    String detail = null;
    String codeNew = "Code Updated";
    String descriptionNew = "Description Updated";
    String detailNew = "Detail Updated";
    Node parent = null;
    List<Node> children = new ArrayList<>();
    Node node = new Node(null, code, description, detail, parent, children);
    Node nodeUpdated = new Node(null, codeNew, descriptionNew, detailNew, parent, children);

    NodeRepository repository = Mockito.mock(NodeRepository.class);
    Mockito.when(repository.save(Mockito.any(Node.class))).thenReturn(nodeUpdated);

    HibernateBasedNodeDataService service = new HibernateBasedNodeDataService(repository);
    this.exception.expect(InvalidNodeInformationException.class);
    Node result = service.create(code, description, detail, parent);
  }

  @Test
  public void should_throw_invalid_node_information_exception_when_there_is_a_null_description_on_node() throws InvalidNodeInformationException {
    String code = "Code";
    String description = null;
    String detail = "detail";
    String codeNew = "Code Updated";
    String descriptionNew = "Description Updated";
    String detailNew = "Detail Updated";
    Node parent = null;
    List<Node> children = new ArrayList<>();
    Node node = new Node(null, code, description, detail, parent, children);
    Node nodeUpdated = new Node(null, codeNew, descriptionNew, detailNew, parent, children);

    NodeRepository repository = Mockito.mock(NodeRepository.class);
    Mockito.when(repository.save(Mockito.any(Node.class))).thenReturn(nodeUpdated);

    HibernateBasedNodeDataService service = new HibernateBasedNodeDataService(repository);
    this.exception.expect(InvalidNodeInformationException.class);
    Node result = service.create(code, description, detail, parent);
  }
}
