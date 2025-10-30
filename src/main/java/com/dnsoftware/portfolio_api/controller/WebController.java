package com.dnsoftware.portfolio_api.controller;

import com.dnsoftware.portfolio_api.model.Pessoa;
import com.dnsoftware.portfolio_api.service.PessoaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;

@Controller
public class WebController {

    @Autowired
    private PessoaService pessoaService;

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


}
