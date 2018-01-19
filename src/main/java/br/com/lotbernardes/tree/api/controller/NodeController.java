package br.com.lotbernardes.tree.api.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = {"v1/tree", ""})
public interface NodeController {

  @ResponseBody
  @RequestMapping(
    value = { "/nodes", "/node" },
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_UTF8_VALUE
  )
  ResponseEntity<String> all(@RequestHeader HttpHeaders headers);

  @ResponseBody
  @RequestMapping(
    value = { "/nodes/{node}" },
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_UTF8_VALUE
  )
  ResponseEntity<String> get(@PathVariable Long node, @RequestHeader HttpHeaders headers);

  @ResponseBody
  @RequestMapping(
    value = { "/node/{node}", "/nodes/{node}/linear" },
    method = RequestMethod.GET,
    produces = MediaType.APPLICATION_JSON_UTF8_VALUE
  )
  ResponseEntity<String> getLinearView(@PathVariable Long node, @RequestHeader HttpHeaders headers);

  @ResponseBody
  @RequestMapping(
    value = { "/nodes", "/node" },
    method = RequestMethod.POST,
    produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
  )
  ResponseEntity<String> create(@RequestBody String body, @RequestHeader HttpHeaders headers);

  @ResponseBody
  @RequestMapping(
    value = { "/nodes/{node}" },
    method = RequestMethod.PUT,
    produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
  )
  ResponseEntity<String> update(@PathVariable Long node, @RequestBody String body, @RequestHeader HttpHeaders headers);

  @ResponseBody
  @RequestMapping(
    value = { "/node" },
    method = RequestMethod.PUT,
    produces = MediaType.APPLICATION_JSON_UTF8_VALUE,
    consumes = MediaType.APPLICATION_JSON_UTF8_VALUE
  )
  ResponseEntity<String> updateWithoutIdentifierOnUrl(@RequestBody String body, @RequestHeader HttpHeaders headers);
}
