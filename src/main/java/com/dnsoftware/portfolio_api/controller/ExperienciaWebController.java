package com.dnsoftware.portfolio_api.controller;

import com.dnsoftware.portfolio_api.model.Experiencia;
import com.dnsoftware.portfolio_api.model.Pessoa;
import com.dnsoftware.portfolio_api.repository.ExperienciaRepository;
import com.dnsoftware.portfolio_api.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin/experiencias")
public class ExperienciaWebController {

    private static final Long MEU_PERFIL_ID = 1L;

    @Autowired
    private ExperienciaRepository experienciaRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @Transactional
    @GetMapping
    public String listarExperiencias(Model model){
        List<Experiencia> todasExperiencias = experienciaRepository.findAll();
        model.addAttribute("experiencias",todasExperiencias);
        return "admin/experiencia-admin";

    }

    @GetMapping("/novo")
    public String exibirFormularioCriacao(Model model){
        model.addAttribute("experiencia", new Experiencia());
        return "admin/experiencia-form";
    }

    @PostMapping("/salvar")
    public String salvarExperiencia(@ModelAttribute Experiencia experiencia){
        Optional<Pessoa> pessoaOpt = pessoaRepository.findById(MEU_PERFIL_ID);

        if (pessoaOpt.isPresent()){
            Pessoa pessoa = pessoaOpt.get();
                experiencia.setPessoa(pessoa);
                pessoa.getExperiencias().add(experiencia);
                pessoaRepository.save(pessoa);
                return "redirect:/admin/experiencias";
            }
        return "redirect:/perfil-nao-encontrado";
    }

    @Transactional
    @GetMapping("/editar/{id}")
    public String exibirFormularioEdicao(@PathVariable Long id, Model model){
        Optional<Experiencia> experienciaOpt = experienciaRepository.findById(id);

        if (experienciaOpt.isPresent()){
            model.addAttribute("experiencia", experienciaOpt.get());
            return "admin/experiencia-form";
        }
        return "redirect:/admin/experiencias";
    }

    @PostMapping("/atualizar")
    public String atualizarExperiencia(@ModelAttribute Experiencia experienciaAtualizada){
        Optional<Pessoa> pessoaOpt = pessoaRepository.findById(MEU_PERFIL_ID);
        if (pessoaOpt.isPresent()){
            Pessoa pessoa = pessoaOpt.get();
            experienciaAtualizada.setPessoa(pessoa);

            experienciaRepository.save(experienciaAtualizada);
            return "redirect:/admin/experiencias";
        }
        return "redirect:/perfil-nao-encontrado";
    }

    @PostMapping("/excluir/{id}")
    public String excluirExperiencia(@PathVariable Long id){
        experienciaRepository.deleteById(id);
        return "redirect:/admin/experiencias";
    }

}
