package com.dnsoftware.portfolio_api.controller;

import com.dnsoftware.portfolio_api.model.Habilidade;
import com.dnsoftware.portfolio_api.model.Pessoa;
import com.dnsoftware.portfolio_api.model.Projeto;
import com.dnsoftware.portfolio_api.repository.HabilidadeRepository;
import com.dnsoftware.portfolio_api.repository.PessoaRepository;
import com.dnsoftware.portfolio_api.repository.ProjetoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

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
            return "redirect:/";
        }
        return "redirect:/perfil-nao-encontrado";
    }

    @GetMapping
      public String listarProjetos(Model model){
        List<Projeto> todosProjetos = projetoRepository.findAll();
        model.addAttribute("projetos", todosProjetos);
        return "admin/projeto-admin";

     }


    @PostMapping("/excluir/{id}")
    public String excluirProjeto(@PathVariable Long id){
        projetoRepository.deleteById(id);
        return "redirect:/admin/projetos";
    }

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

    @PostMapping("/atualizar")
    public String atualizarProjeto(
            @ModelAttribute Projeto projetoAtualizado,
            @RequestParam(value = "habilidadesIds", required = false) List<Long> habilidadesIds){
        Optional<Pessoa> pessoaOpt = pessoaRepository.findById(MEU_PERFIL_ID);

        if (pessoaOpt.isPresent()){
            Pessoa pessoa = pessoaOpt.get();

            projetoAtualizado.setPessoa(pessoa);

            if (habilidadesIds != null && !habilidadesIds.isEmpty()){
                Set<Habilidade> habilidades = new HashSet<>(habilidadeRepository.findAllById(habilidadesIds));
                projetoAtualizado.setHabilidades(habilidades);
            }else {
                projetoAtualizado.setHabilidades(new HashSet<>());
            }
            projetoRepository.save(projetoAtualizado);
        }
        return "redirect:/admin/projetos";
    }



}
