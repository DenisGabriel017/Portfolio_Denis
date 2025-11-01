package com.dnsoftware.portfolio_api.controller;

import com.dnsoftware.portfolio_api.model.Habilidade;
import com.dnsoftware.portfolio_api.model.Pessoa;
import com.dnsoftware.portfolio_api.model.Projeto;
import com.dnsoftware.portfolio_api.repository.HabilidadeRepository;
import com.dnsoftware.portfolio_api.repository.PessoaRepository;
import com.dnsoftware.portfolio_api.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Slf4j
@Controller
@RequestMapping("/admin/projetos")
public class ProjetoWebController {

    private static final Long MEU_PERFIL_ID = 1L;

    @Autowired
    private ProjetoRepository projetoRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Autowired
    private HabilidadeRepository habilidadeRepository;

    @GetMapping("/novo")
    public String exibirFormularioCriacao(Model model){
        model.addAttribute("projeto", new Projeto());
        List<Habilidade> todasHabilidades = habilidadeRepository.findAll();
        model.addAttribute("todasHabilidades", todasHabilidades);
        return "/admin/projeto-form";
    }

    @PostMapping("/salvar")
    public String salvarProjeto(
            @ModelAttribute Projeto projeto,
            @RequestParam(value = "habilidadeIds", required = false)
            List<Long> habilidadeIds
    ){
        Optional<Pessoa> pessoaOpt =  pessoaRepository.findById(MEU_PERFIL_ID);

        if (pessoaOpt.isPresent()){
            Pessoa pessoa = pessoaOpt.get();

            projeto.setPessoa(pessoa);
            if (habilidadeIds != null && !habilidadeIds.isEmpty()){
                List<Habilidade> habilidades = habilidadeRepository.findAllById(habilidadeIds);
                projeto.setHabilidades(new HashSet<>(habilidades));
            }else {
                projeto.setHabilidades(new HashSet<>());
            }
            pessoa.getProjetos().add(projeto);
            projetoRepository.save(projeto);
            return "redirect:/admin/projetos";
        }
        return "redirect:/perfil-nao-encontrado";
    }

    @Transactional
    @GetMapping
      public String listarProjetos(Model model){
        List<Projeto> todosProjetos = projetoRepository.findAll();
        model.addAttribute("projetos", todosProjetos);
        return "admin/projeto-admin";

     }


    @PostMapping("/excluir/{id}")
    public String excluirProjeto(@PathVariable Long id, RedirectAttributes attributes) {
        projetoRepository.deleteById(id);
        attributes.addFlashAttribute("mensagem", "Projeto excluído com sucesso.");
        return "redirect:/admin/projetos";
    }

    @Transactional
    @GetMapping("/editar/{id}")
    public String exibirFormularioEdicao(@PathVariable Long id, Model model){
        Optional<Projeto> projetoOpt = projetoRepository.findById(id);

        if (projetoOpt.isPresent()){
            Projeto projeto = projetoOpt.get();
            model.addAttribute("projeto", projeto);
            List<Habilidade> todasHabilidades = habilidadeRepository.findAll();
            model.addAttribute("todasHabilidades", todasHabilidades);

            return "admin/projeto-form";
        }
        return "redirect:/admin/projetos";
    }

    @Transactional
    @PostMapping("/atualizar")
    public String atualizarProjeto(
            @ModelAttribute Projeto projetoDadosFormulario, // O projeto do formulário (com ID)
            @RequestParam(value = "habilidadesIds", required = false) List<Long> habilidadesIds) {

        Optional<Projeto> projetoGerenciadoOpt = projetoRepository.findById(projetoDadosFormulario.getId());

        if (projetoGerenciadoOpt.isEmpty()) {
            return "redirect:/admin/projetos";
        }

        Projeto projetoGerenciado = projetoGerenciadoOpt.get();

        projetoGerenciado.setTitulo(projetoDadosFormulario.getTitulo());
        projetoGerenciado.setDescricao(projetoDadosFormulario.getDescricao());
        projetoGerenciado.setDataConclusao(projetoDadosFormulario.getDataConclusao());
        projetoGerenciado.setLinkRepositorio(projetoDadosFormulario.getLinkRepositorio());

        Pessoa pessoa = pessoaRepository.findById(MEU_PERFIL_ID).orElse(null);
        if (pessoa != null) {
            projetoGerenciado.setPessoa(pessoa);
        }

        Set<Habilidade> novasHabilidades = new HashSet<>();
        if (habilidadesIds != null && !habilidadesIds.isEmpty()) {
            novasHabilidades.addAll(habilidadeRepository.findAllById(habilidadesIds));
        }

        projetoGerenciado.getHabilidades().clear();
        projetoGerenciado.getHabilidades().addAll(novasHabilidades);

        projetoRepository.save(projetoGerenciado);

        return "redirect:/admin/projetos";
    }



}
