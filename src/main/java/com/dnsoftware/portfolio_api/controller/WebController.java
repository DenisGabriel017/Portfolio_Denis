package com.dnsoftware.portfolio_api.controller;

import com.dnsoftware.portfolio_api.model.Pessoa;
import com.dnsoftware.portfolio_api.repository.PessoaRepository;
import com.dnsoftware.portfolio_api.service.PessoaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class WebController {

    @Autowired
    private PessoaService pessoaService;
    private final PessoaRepository pessoaRepository;
    private static final Long MEU_PERFIL_ID =1L;

    @Transactional
    @GetMapping("/")
    public String viewHomePage(Model model){
        Long meuPerfilId = 1L;
        Optional<Pessoa> perfilOpt = pessoaService.buscarPerfilCompleto(meuPerfilId);

        if (perfilOpt.isPresent()){
            Pessoa perfil =  perfilOpt.get();
            model.addAttribute("perfil", perfil);
            return "index";

        }else {
            return "perfil-nao-encontrado";
        }
    }

    @GetMapping("/perfil-nao-encontrado")
    public String perfilNaoEncontrado(){
        return "perfil-nao-encontrado";
    }

    @GetMapping("/admin/perfil/editar")
    public String exibirFormularioEdicaoPerfil(Model model){
        Optional<Pessoa> pessoaOpt = pessoaRepository.findById(MEU_PERFIL_ID);

        if (pessoaOpt.isPresent()){
            model.addAttribute("pessoa", pessoaOpt.get());
            return "admin/pessoa-form";
        }
        return "redirect:/perfil-nao-encontrado";
    }

    @PostMapping("/admin/perfil/atualizar")
    public String atualizarPerfil(@ModelAttribute Pessoa pessoaAtualizada){
        pessoaAtualizada.setId(MEU_PERFIL_ID);
        pessoaRepository.save(pessoaAtualizada);
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @GetMapping("/admin")
    public String indexAdmin() {
        return "admin/indexadmin";
    }

}
