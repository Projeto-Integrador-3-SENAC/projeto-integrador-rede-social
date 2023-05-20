package br.com.projetopi.redesocial.dao;

import br.com.projetopi.redesocial.interfaces.Dao;
import br.com.projetopi.redesocial.model.Postagem;
import br.com.projetopi.redesocial.repository.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;

public class PostagemDao implements Dao<Postagem> {

    private Connection conexao;

    public PostagemDao(){
        this.conexao = ConnectionFactory.getConnectionH2();
    }

    @Override
    public void add(Postagem postagem) {
        String sqlQuery = "insert into postagem " +
                "(conteudo, foto_id, conta_id, data_postagem)" +
                "values (?,?,?,?)";

    try (PreparedStatement ps = conexao.prepareStatement(sqlQuery)){

        ps.setString(1, postagem.getConteudo());
        ps.setInt(2, postagem.getFoto_id());
        ps.setInt(3, postagem.getConta_id());
        ps.setDate(4, postagem.getData_postagem());
        ps.execute();

    }catch (SQLException e){
        System.out.println(e.getMessage());
        e.printStackTrace();
    }
    }

    @Override
    public boolean update(Postagem postagem) {
        return false;
    }

    @Override
    public boolean remove(int id) {
        String sqlQuery = "delete from postagem where id = ?";
        try(PreparedStatement ps =  conexao.prepareStatement(sqlQuery)){
            ps.setInt(1, id);
            ps.execute();
        }catch(SQLException e){
            e.printStackTrace();
        }
        return true;
    }

    @Override
    public ArrayList findAllPageable(int qtd_elementos, int num_inicio) {
        return null;
    }

    public ArrayList findAllPageableByInstituicao(int instituicao_id, int qtd_elementos, int num_inicio) {

        ArrayList<Postagem> postagens = new ArrayList<>();

        String sqlQuery = """ 
                select * from postagem\s
                inner join conta on conta.id = postagem.conta_id
                inner join instituicao  i on i.id = conta.instituicao_id\s
                where instituicao_id = ?
                order by  data_postagem desc\s 
                LIMIT ? OFFSET ?;"""
                ;

        try (PreparedStatement ps = conexao.prepareStatement(sqlQuery)){

            ps.setInt(1, instituicao_id);
            ps.setInt(2, qtd_elementos);
            ps.setInt(3, num_inicio);

            ResultSet result = ps.executeQuery();

            while(result.next()){

                Postagem postagem = new Postagem();


                postagem.setConteudo(result.getString("conteudo"));
                postagem.setConta_id(result.getInt("conta_id"));
                postagem.setFoto_id(result.getInt("foto_id"));
                postagem.setData_postagem(result.getDate("data_postagem"));
                postagens.add(postagem);
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return postagens;
    }

    public ArrayList findByAccountPageable(int conta_id, int qtd_elementos, int num_inicio) {

        ArrayList<Postagem> postagens = new ArrayList<>();

        String sqlQuery = "select * from postagem where conta_id = ? LIMIT ? OFFSET ?;";

        try (PreparedStatement ps = conexao.prepareStatement(sqlQuery)){

            ps.setInt(1, conta_id);
            ps.setInt(2, qtd_elementos);
            ps.setInt(3, num_inicio);

            ResultSet result = ps.executeQuery();

            while(result.next()){

                Postagem postagem = new Postagem();
                postagem.setId(result.getInt("id"));
                postagem.setConteudo(result.getString("conteudo"));
                postagem.setConta_id(result.getInt("conta_id"));
                postagem.setFoto_id(result.getInt("foto_id"));
                postagem.setData_postagem(result.getDate("data_postagem"));
                postagens.add(postagem);
            }

        }catch (SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }

        return postagens;
    }

}
