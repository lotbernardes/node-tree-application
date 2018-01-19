package br.com.lotbernardes.tree.api.dto;

import br.com.lotbernardes.tree.api.dto.utils.JsonParser;
import br.com.lotbernardes.tree.api.exception.JsonParsingException;
import br.com.lotbernardes.tree.domain.Node;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.h2.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@JsonIgnoreProperties(ignoreUnknown = true)
public class NodeTransferObject {

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

  @JsonProperty("children")
  public List<NodeTransferObject> children;

  public NodeTransferObject() { }

  private NodeTransferObject(Long identifier, Long parent, String code, String description, String detail, List<NodeTransferObject> children) {
    this.identifier = identifier;
    this.parent = parent;
    this.code = code;
    this.description = description;
    this.detail = detail;
    this.children = children;
  }

  public String serialize() throws JsonParsingException {
    return JsonParser.serialize(this);
  }

  public static NodeTransferObject deserialize(String text) throws JsonParsingException {
    return JsonParser.deserialize(text, NodeTransferObject.class);
  }

  public static NodeTransferObject wrap(Node node) {
    List<NodeTransferObject> children = (Objects.nonNull(node.children))
      ? node.children.stream().map(NodeTransferObject::wrap).collect(Collectors.toList())
      : new ArrayList<>();

    Long parent = (Objects.nonNull(node.parent)) ? node.parent.identifier : null;
    return new NodeTransferObject(node.identifier, parent, node.code, node.description, node.detail, children);
  }
}
