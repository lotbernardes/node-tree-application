package br.com.lotbernardes.tree.api;

import br.com.lotbernardes.tree.api.controller.DefaultNodeController;
import br.com.lotbernardes.tree.api.controller.NodeController;
import br.com.lotbernardes.tree.api.service.DefaultNodeTreeService;
import br.com.lotbernardes.tree.domain.service.NodeDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApiConfiguration {

  @Autowired
  private NodeDataService service;

  @Bean
  public NodeController nodeController() {
    return new DefaultNodeController(new DefaultNodeTreeService(this.service));
  }
}
