package com.dnsoftware.portfolio_api.controller;

import com.dnsoftware.portfolio_api.model.Experiencia;
import com.dnsoftware.portfolio_api.model.Pessoa;
import com.dnsoftware.portfolio_api.repository.ExperienciaRepository;
import com.dnsoftware.portfolio_api.repository.PessoaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Optional;

@Controller
@RequestMapping("/admin/experiencias")
public class ExperienciaWebController {

    private static final Long MEU_PERFIL_ID = 1L;

    @Autowired
    private ExperienciaRepository experienciaRepository;

    @Autowired
    private PessoaRepository pessoaRepository;

    @GetMapping("/nova")
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
                return "redirect:/";
            }
        return "redirect:/perfil-nao-encontrado";
    }
}
