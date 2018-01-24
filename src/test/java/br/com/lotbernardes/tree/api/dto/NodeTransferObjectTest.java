package br.com.lotbernardes.tree.api.dto;

import br.com.lotbernardes.tree.domain.Node;
import org.junit.Test;
import static org.junit.Assert.*;

public class NodeTransferObjectTest {

  @Test
  public void should_wrap_singleton_node_with_empty_children_list_when_children_is_null() {
    Node node = new Node(1L, "code", "description", "detail", null, null);

    NodeTransferObject result = NodeTransferObject.wrap(node);
    assertTrue("Children list should be empty", result.children.isEmpty());
  }

  @Test
  public void should_wrap_parent_identifier_as_null_when_there_is_none() {
    Node node = new Node(1L, "code", "description", "detail", null, null);

    NodeTransferObject result = NodeTransferObject.wrap(node);
    assertNull("Parent identifier should be null", result.parent);
  }
}
