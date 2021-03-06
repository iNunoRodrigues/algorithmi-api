package algorithmi.models;

import Utils.utils;
import algorithmi.Main;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.logging.Level;
import java.util.logging.Logger;
import spark.Response;

/**
 *
 * @author David
 */
public class Categories {

    private int id;
    private String description;

    //--------------------------------------------------------------------------------------
    //-------------------------------- Listar Categorias -----------------------------------
    //--------------------------------------------------------------------------------------
    public static String getAll(Response response) {
        try {
            String query = "SELECT * from tblCategories";
            //Devolve 'Ok'
            response.status(200);
            //E a lista de instituicoes
            return utils.executeSelectCommand(query).toString();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            response.status(400);
            return "{\"text\":\"N�o foi poss�vel obter as Categorias.\"}";
        }
    }

    //--------------------------------------------------------------------------------------
    //-----------------------*--- Obter  dados de uma Categoria -----------*----------------
    //--------------------------------------------------------------------------------------   
    public static String getCategoryData(Response response, String id) {

        try {
            //Obtem a instituicao
            String queryCategory = "select * from tblCategories where id=" + id;
            JsonObject category = utils.executeSelectCommand(queryCategory).get(0).getAsJsonObject();
            //Devolve 'OK'
            response.status(200);
            //E uma mensagem
            return category.toString();
        } catch (Exception ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            response.status(400);
            return "{\"text\":\"N�o foi poss�vel obter a Categoria com o id:" + id + ".\"}";
        }
    }

    //--------------------------------------------------------------------------------------
    //------------------------------- Inserir Categoria ---------------------------------
    //--------------------------------------------------------------------------------------  
    public String insert(Response response) {

        try {
            //Obt�m o ultimo ID
            this.id = utils.getLastID("tblCategories") + 1;
            boolean existErro = false;
            String[] erros = validateData();
            for (int i = 0; i < erros.length; i++) {
                if (erros[i] == null);
                {
                    existErro = existErro || false;
                }
            }
            if (!existErro) {
                String insert = "INSERT INTO tblCategories values(" + id + ",'" + description + "')";
                //Insere, devolve o estado
                response.status(utils.executeIUDCommand(insert));
                // E uma mensagem
                return "{\"text\":\"Categoria inserida com sucesso!\"}";
            }

        } catch (Exception ex) {
            Logger.getLogger(Institutions.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.status(400);
        return "{\"text\":\"N�o foi poss�vel inserir a Categoria.\"}";
    }

    //--------------------------------------------------------------------------------------
    //------------------------------- Update a Categoria ---------------------------------
    //--------------------------------------------------------------------------------------   
    public String updateCategory(Response response) {
        try {
            String update = "UPDATE tblCategories SET description='" + description + "' where id=" + id;
            response.status(utils.executeIUDCommand(update));
            return "{\"text\":\"Categoria alterada com sucesso!\"}";
        } catch (Exception ex) {
            Logger.getLogger(Institutions.class.getName()).log(Level.SEVERE, null, ex);
            response.status(400);
            return "{\"text\":\"N�o foi poss�vel alterar a Categoria.\"}";
        }
    }

    //--------------------------------------------------------------------------------------
    //------------------------------- Apagar Institui��o -----------------------------------
    //--------------------------------------------------------------------------------------
    public static String delete(Response response, int id) {
        try {

            String deleted = utils.deleteRegist(id, "tblCategories");
            response.status(utils.executeIUDCommand(deleted));
            return "{\"text\":\"Categoria apagada com sucesso.\"}";

        } catch (Exception ex) {
            Logger.getLogger(Institutions.class.getName()).log(Level.SEVERE, null, ex);
        }
        response.status(400);
        return "{\"text\":\"N�o foi poss�vel apagar a Categoria.\"}";

    }

    // converts a java object to JSON format,
    // and returned as JSON formatted string
    @Override
    public String toString() {
        Gson gson = new Gson();

        String json = gson.toJson(this);
        System.out.println("json \n" + json);
        return json;
    }

    //--------------------------------------------------------------------------------------
    //------------------------------- Validar Dados ----------------------------------------
    //--------------------------------------------------------------------------------------
    private String[] validateData() {

        String respostasErro[] = new String[3];
        boolean valid = false;
        boolean nameValid = utils.isString(description, true);//1
        valid = nameValid;
        if (!valid) {
            if (!nameValid) {
                respostasErro[0] = "Nome da categoria inv�lido";
            }
        }
        return respostasErro;
    }

}
