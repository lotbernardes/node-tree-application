package br.com.lotbernardes.tree.domain;

import br.com.lotbernardes.tree.domain.repository.NodeRepository;
import br.com.lotbernardes.tree.domain.service.HibernateBasedNodeDataService;
import br.com.lotbernardes.tree.domain.service.NodeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DomainConfiguration {

  @Autowired
  private NodeRepository repository;

  @Bean
  public NodeDataService nodeDataService() {
    return new HibernateBasedNodeDataService(this.repository);
  }
}
