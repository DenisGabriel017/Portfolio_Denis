package com.dnsoftware.portfolio_api.controller;

import com.dnsoftware.portfolio_api.model.Habilidade;
import com.dnsoftware.portfolio_api.repository.HabilidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/admin/habilidades")
public class HabilidadeWebController {

    private final HabilidadeRepository habilidadeRepository;

    public HabilidadeWebController(HabilidadeRepository habilidadeRepository) {
        this.habilidadeRepository = habilidadeRepository;
    }

    @Transactional
    @PostMapping("/salvar")
    public  String salvarHabilidade(@ModelAttribute Habilidade habilidade){
        habilidadeRepository.save(habilidade);
        return "redirect:/admin/habilidades";
    }

    @GetMapping
    public String listarHabilidades(Model model) {
        List<Habilidade> todasHabilidades = habilidadeRepository.findAll();
        model.addAttribute("habilidade", todasHabilidades);
        model.addAttribute("novaHabilidade", new Habilidade());
        return "admin/habilidade-admin";
    }

    @PostMapping("/excluir/{id}")
    public String excluirHAbilidade(@PathVariable Long id){
        habilidadeRepository.deleteById(id);
        return "redirect:/admin/habilidades";
    }
}

