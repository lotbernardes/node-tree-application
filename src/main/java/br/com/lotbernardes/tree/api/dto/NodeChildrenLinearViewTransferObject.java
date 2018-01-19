package br.com.lotbernardes.tree.api.dto;

import br.com.lotbernardes.tree.api.dto.utils.JsonParser;
import br.com.lotbernardes.tree.api.exception.JsonParsingException;
import br.com.lotbernardes.tree.domain.Node;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NodeChildrenLinearViewTransferObject {

  @JsonProperty("id")
  public Long identifier;

  @JsonProperty("parentId")
  public Long parent;

  @JsonProperty("code")
  public String code;

  @JsonProperty("description")
  public String description;

  @JsonProperty("detail")
  public String detail;

  @JsonProperty("hasChildren")
  public Boolean hasChildren;

  public NodeChildrenLinearViewTransferObject() { }

  private NodeChildrenLinearViewTransferObject(Long identifier, Long parent, String code, String description, String detail, Boolean hasChildren) {
    this.identifier = identifier;
    this.parent = parent;
    this.code = code;
    this.description = description;
    this.detail = detail;
    this.hasChildren = hasChildren;
  }

  public static String serialize(List<NodeChildrenLinearViewTransferObject> instance) throws JsonParsingException {
    return JsonParser.serialize(instance);
  }

  public static NodeChildrenLinearViewTransferObject deserialize(String text) throws JsonParsingException {
    return JsonParser.deserialize(text, NodeChildrenLinearViewTransferObject.class);
  }

  public static List<NodeChildrenLinearViewTransferObject> wrap(Node node) {
    if (Objects.isNull(node) || Objects.isNull(node.children)) {
      return new ArrayList<>();
    }

    return node.children.stream()
      .map(NodeChildrenLinearViewTransferObject::wrapCurrentAndAllChildren)
      .flatMap(List::stream)
      .collect(Collectors.toList());
  }

  private static List<NodeChildrenLinearViewTransferObject> wrapCurrentAndAllChildren(Node current) {
    List<NodeChildrenLinearViewTransferObject> result = new ArrayList<>();
    result.add(NodeChildrenLinearViewTransferObject.singleton(current));

    current.children.forEach(child -> result.addAll(wrapCurrentAndAllChildren(child)));
    return result;
  }

  private static NodeChildrenLinearViewTransferObject singleton(Node node) {
    Long parent = (Objects.nonNull(node.parent)) ? node.parent.identifier : null;
    Boolean hasChildren = !node.children.isEmpty();

    return new NodeChildrenLinearViewTransferObject(node.identifier, parent, node.code, node.description, node.detail, hasChildren);
  }
}
