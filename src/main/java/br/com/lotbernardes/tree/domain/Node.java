package br.com.lotbernardes.tree.domain;

import org.hibernate.validator.constraints.NotEmpty;
import javax.persistence.*;
import java.util.List;

@Entity
@Table(name= "NODE")
public class Node {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Long identifier;

  @NotEmpty
  public String code;

  @NotEmpty
  public String description;

  @NotEmpty
  public String detail;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  public Node parent;

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "parent")
  public List<Node> children;

  public Node() { }

  public Node(String code, String description, String detail, Node parent) {
    this.code = code;
    this.description = description;
    this.detail = detail;
    this.parent = parent;
  }

  public Node(Long identifier, String code, String description, String detail, Node parent, List<Node> children) {
    this.identifier = identifier;
    this.code = code;
    this.description = description;
    this.detail = detail;
    this.parent = parent;
    this.children = children;
  }
}
