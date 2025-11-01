package com.dnsoftware.portfolio_api.controller;


import com.dnsoftware.portfolio_api.model.Imagem;
import com.dnsoftware.portfolio_api.model.Projeto;
import com.dnsoftware.portfolio_api.repository.ImagemRepository;
import com.dnsoftware.portfolio_api.repository.ProjetoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
@RequestMapping("/admin/imagens")
@RequiredArgsConstructor
public class ImagemWebController {

    private final ImagemRepository imagemRepository;
    private final ProjetoRepository projetoRepository;

    @PostMapping("/salvar/{projetoId}")
    public String adicionarImagem(
            @PathVariable Long projetoId,
            @RequestParam("url") String url,
            RedirectAttributes redirectAttributes){

        Optional<Projeto> projetoOtp = projetoRepository.findById(projetoId);
        if (projetoOtp.isPresent()){
            Projeto projeto = projetoOtp.get();
            Imagem novaImagem = new Imagem();
            novaImagem.setUrl(url);
            novaImagem.setProjeto(projeto);
            novaImagem.setNomeArquivo(url.substring(url.lastIndexOf('/') + 1));

            imagemRepository.save(novaImagem);
            redirectAttributes.addFlashAttribute("mensagem", "Imagem adicionada com suscesso!");
        }else {
            redirectAttributes.addFlashAttribute("erro", "Projeto não encontrado.");
        }
        return "redirect:/admin/projetos/editar/" + projetoId;
    }

    @PostMapping("/excluir/{id}")
    public String excluirImagem(@PathVariable Long id, RedirectAttributes redirectAttributes){
        Optional<Imagem> imagemOpt = imagemRepository.findById(id);

        if (imagemOpt.isPresent()){
            Long projetoId = imagemOpt.get().getProjeto().getId();
            imagemRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensagem", "Imagem removida com sucesso.");

            return "redirect:/admin/projetos/editar/" + projetoId;
        }
        redirectAttributes.addFlashAttribute("erro", "Imagem não encontrada.");
        return "redirect:/admin/projetos";
    }



}
