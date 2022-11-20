package br.com.capflix.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.capflix.model.dto.ImagemEntradaDto;
import br.com.capflix.service.ImagemService;
import lombok.extern.log4j.Log4j2;

@RestController
@RequestMapping("imagem")
@Log4j2
@Validated
public class ImagemController {

	@Autowired
	private ImagemService service;
	
	@ResponseStatus(code = HttpStatus.OK)
	@PostMapping
	public void salvar(@Valid @RequestBody ImagemEntradaDto entradaDto) {
		log.info("salvar: {}", entradaDto);

		service.salvar(entradaDto);
	}

	@ResponseStatus(code = HttpStatus.OK)
	@DeleteMapping("id/{id}")
	public void excluir(@PathVariable("id") Long id) {
		log.info("excluir: {}", id);
		
		service.excluir(id);
	}
	
	@GetMapping(value="id/{id}", produces=MediaType.IMAGE_PNG_VALUE)
	public  byte[] pagarUm(@PathVariable("id") Long id) {
		log.info("pagarUm: {}", id);
		
		return service.pagarUm(id);
	}
}
