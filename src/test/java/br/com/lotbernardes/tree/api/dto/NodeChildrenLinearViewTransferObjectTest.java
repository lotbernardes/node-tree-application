package br.com.lotbernardes.tree.api.dto;

import br.com.lotbernardes.tree.domain.Node;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.*;

public class NodeChildrenLinearViewTransferObjectTest {


  @Test
  public void should_return_an_empty_list_when_node_is_null() {
    List<NodeChildrenLinearViewTransferObject> result = NodeChildrenLinearViewTransferObject.wrap(null);
    assertTrue("List should be empty", result.isEmpty());
  }

  @Test
  public void should_return_an_empty_list_when_node_children_is_null() {
    Node node = new Node(1L, "code", "description", "detail", null, null);
    List<NodeChildrenLinearViewTransferObject> result = NodeChildrenLinearViewTransferObject.wrap(node);
    assertTrue("List should be empty", result.isEmpty());
  }

  @Test
  public void should_return_a_list_with_size_two_when_there_is_children_of_node_children() {
    Node grandchildren = new Node(3L, "code", "description", "detail", null, null);
    Node children = new Node(2L, "code", "description", "detail", null, Collections.singletonList(grandchildren));
    Node node = new Node(1L, "code", "description", "detail", null, Collections.singletonList(children));

    List<NodeChildrenLinearViewTransferObject> result = NodeChildrenLinearViewTransferObject.wrap(node);
    assertEquals("Size should match", 2, result.size());
  }

  @Test
  public void should_return_a_list_with_size_three_when_there_is_more_than_one_children_and_grandchildren() {
    Node grandchildren = new Node(4L, "code", "description", "detail", null, null);
    Node childrenOne = new Node(2L, "code", "description", "detail", null, Collections.singletonList(grandchildren));
    Node childrenTwo = new Node(3L, "code", "description", "detail", null, null);

    List<Node> children = new ArrayList<Node>() {{
      add(childrenOne);
      add(childrenTwo);
    }};
    Node node = new Node(1L, "code", "description", "detail", null, children);

    List<NodeChildrenLinearViewTransferObject> result = NodeChildrenLinearViewTransferObject.wrap(node);
    assertEquals("Size should match", 3, result.size());
  }
}
