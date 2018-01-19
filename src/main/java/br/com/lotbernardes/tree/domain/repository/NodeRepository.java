package br.com.lotbernardes.tree.domain.repository;

import br.com.lotbernardes.tree.domain.Node;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface NodeRepository extends CrudRepository<Node, Long> {
  Optional<Node> findOptionalByIdentifier(Long identifier);
  Optional<Node> findOptionalByParent_IdentifierIsNull();
}
