package br.com.projetopi.redesocial.controller.admin.action;

import br.com.projetopi.redesocial.interfaces.Action;
import br.com.projetopi.redesocial.model.Instituicao;
import br.com.projetopi.redesocial.service.InstituicaoService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

public class ExibirTelaInstituicao implements Action {

    private InstituicaoService instituicaoService;
    public ExibirTelaInstituicao(){
        this.instituicaoService = new InstituicaoService();
    }
    @Override
    public String executa(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        ArrayList<Instituicao> instituicoes = this.instituicaoService.getInstituicaoAll(50, 0);
        request.setAttribute("instituicoes", instituicoes);
        return "forward:instituicao.jsp";
    }
}
