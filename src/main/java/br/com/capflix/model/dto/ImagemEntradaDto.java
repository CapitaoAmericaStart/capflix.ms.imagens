package br.com.capflix.model.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.URL;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class ImagemEntradaDto {
	
	@NotNull(message="obrigatório")
	private Long id;
	
	@NotBlank(message="obrigatório")
	@URL(message="inválido")
	private String urlImagem;
}
