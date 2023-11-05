package com.ksi.consultoria.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ksi.consultoria.models.Filme;
import com.ksi.consultoria.repository.FilmeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

@Controller
public class FilmeController {

    private static final String chave = "dca698fe";

    private static final String capaInterrogacao = "https://i.pinimg.com/736x/a6/ae/e5/a6aee56d93ce0b9ea9edea8140563adb.jpg";

    private static final Gson gson = new GsonBuilder().setDateFormat("dd/MM/yyyy HH:mm:ss").create();

    @Autowired
    FilmeRepository filmeRepository;

    @GetMapping("/")
    public String paginaHome(){
        return "Pesquisa";
    }

    @GetMapping("/tituloFilme")
    public Object exibirDadosFilme (Filme tituloFilme, RedirectAttributes redirectAtribute) {
        try {
            ModelAndView mv = new ModelAndView("FilmePesquisado");
            mv.addObject("filme", consultarFilme(tituloFilme.getTitle()));
            return mv;

        }catch (Exception e){
            redirectAtribute.addFlashAttribute("mensagemFilmeNaoEncontrado", "Filme não encontrado");
            return "redirect:/";
        }
    }

    @PostMapping("/favoritar")
    public String favoritarFilme (Filme filmeFavorito, RedirectAttributes redirectAtribute){

        Boolean checarFilme = filmeRepository.existsByTitulo(filmeFavorito.getTitle());

        if (checarFilme == true) {
            redirectAtribute.addFlashAttribute("mensagemJaFavoritado", "Filme já foi favoritado");
            return "redirect:/favorito";
        } else {
            filmeRepository.save(filmeFavorito);
            redirectAtribute.addFlashAttribute("mensagemFavorito", "Filme favoritado");
            return "redirect:/";
        }
    }

    @GetMapping(value = "/favorito")
    public ModelAndView filmesFavoritos() {
        ModelAndView mv = new ModelAndView("Favorito");
        List<Filme> filmes = filmeRepository.findAll();
        mv.addObject("filmes", filmes);
        return mv;
    }

    @GetMapping("/filme/editar/{id}")
    public ModelAndView paginaEditarFilme(@PathVariable Long id){
        ModelAndView mv = new ModelAndView("EditarFilme");
        Filme filme = filmeRepository.findById(id).get();

        mv.addObject("filme", filme);
        return mv;
    }

    @PostMapping("/filme/editar/{id}")
    public String editarFilme(@PathVariable Long id, Filme filme, RedirectAttributes redirectAtribute){

        Filme filmeExistente = filmeRepository.findById(id).get();

        filmeRepository.save(receberFilme(filme, filmeExistente));

        redirectAtribute.addFlashAttribute("mensagemEditado", "Filme editado com sucesso");
        return "redirect:/favorito";
    }


    @GetMapping("/filme/delete/{id}")
    public String removerFavorito(@PathVariable Long id, RedirectAttributes redirectAtribute){

        Filme filme = filmeRepository.findById(id).get();
        filmeRepository.delete(filme);

        redirectAtribute.addFlashAttribute("mensagemRemovido", "Filme removido da sua lista");
        return "redirect:/favorito";
    }

    public Filme consultarFilme (String tituloFilme) throws IOException, InterruptedException {

        String tituloFilmeFormatado = tituloFilme.replace(" ", "+");

        String URL = "https://www.omdbapi.com/?t=" + tituloFilmeFormatado + "&apikey=" + chave;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(URL)).build();

        HttpResponse<String> response = client
                .send(request, HttpResponse.BodyHandlers.ofString());

        Filme filme = gson.fromJson(response.body(), Filme.class);

        if(filme.getDirector().equals("N/A")){
            filme.setDirector("Nome não informado");
        }
        if(filme.getReleased().equals("N/A")){
            filme.setReleased("Não informado");
        }
        if(filme.getPoster().equals("N/A")){
            filme.setPoster(capaInterrogacao);
        }

        return filme;
    }

    public Filme receberFilme(Filme filme, Filme filmeExistente){

        filmeExistente.setTitle(filme.getTitle());
        filmeExistente.setDirector(filme.getDirector());
        filmeExistente.setPoster(filme.getPoster());
        filmeExistente.setReleased(filme.getReleased());

        return filmeExistente;

    }


}
