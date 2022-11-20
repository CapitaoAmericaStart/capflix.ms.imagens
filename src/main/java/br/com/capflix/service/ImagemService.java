package br.com.capflix.service;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URL;

import javax.imageio.ImageIO;

import org.springframework.stereotype.Service;

import br.com.capflix.exception.NegocioException;
import br.com.capflix.exception.TabelaDeErros;
import br.com.capflix.model.dto.ImagemEntradaDto;
import lombok.extern.log4j.Log4j2;

@Service
@Log4j2
public class ImagemService {
	
	public static final  String DIRETORIO = "imagens";
	public static final  String EXTENSAO = "png";
	
	public void salvar(ImagemEntradaDto entradaDto) {
		try {
			validarDiretorio();
			
			BufferedImage img = ImageIO.read(new URL(entradaDto.getUrlImagem()));
			File file = new File(nomeArquivoImagem(entradaDto.getId()));
			ImageIO.write(img, EXTENSAO, file);
		} catch (IllegalArgumentException e) {
			TabelaDeErros tabela = TabelaDeErros.ERRO_NA_URL_DA_IMAGEM;
			
			log.error("erro de negócio: codigoDeErro={}, mensagem={}", tabela.getCodigoDeErro(), tabela.getMensagem());
			
			throw new NegocioException(tabela);
		} catch (Exception e) {
			throw negocioException(e);
		} 
	}

	public byte[] pagarUm(Long id) {
		try {
			validarDiretorio();
		} catch (NegocioException e) {
			throw negocioException(e);
		}
		
		try (InputStream baos = new FileInputStream(nomeArquivoImagem(id))){
			byte [] bytes = new byte [baos.available()];
			baos.read(bytes);
			
			return bytes;
		} catch (FileNotFoundException e) {
			TabelaDeErros tabela = TabelaDeErros.IMAGEM_NAO_ENCONTRADA;
			
			log.error("erro de negócio: codigoDeErro={}, mensagem={}", tabela.getCodigoDeErro(), tabela.getMensagem());
			
			throw new NegocioException(tabela);
		} catch (Exception e) {
			throw negocioException(e);
		}	
	}

	public void excluir(Long id) {
		try {
			validarDiretorio();
			
			boolean excluiu = new File(nomeArquivoImagem(id)).delete();
			if(!excluiu) {
				throw new NegocioException(TabelaDeErros.IMAGEM_NAO_ENCONTRADA);
			}
		} catch (Exception e) {
			throw negocioException(e);
		}		
	}
	
	private String nomeArquivoImagem(Long id) {
		return DIRETORIO + "/" + id + "." + EXTENSAO;
	}
	
	private void validarDiretorio() throws NegocioException{
		File diretorio = new File(DIRETORIO);
		
		if(!diretorio.exists() || !diretorio.isDirectory()) {
			throw new NegocioException(TabelaDeErros.NO_DIRETORIO_DA_IMAGEM);
		}
	}
	
	private NegocioException negocioException(Exception e) {
		if (e instanceof NegocioException) {
			NegocioException ne = (NegocioException) e;
			
			log.error("erro de negócio: codigoDeErro={}, mensagem={}", ne.getCodigoDeErro(), ne.getMensagem());
			return ne;
		}
		
		log.error("erro genérico: ", e);
		
		throw new NegocioException(TabelaDeErros.SISTEMA);
	}
}
