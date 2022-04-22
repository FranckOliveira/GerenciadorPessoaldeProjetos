package controler;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Project;
import util.ConnectionFactory;

/*
 * @author franc
 */
public class ProjectController {

    public void save(Project project) {

        String sql = "INSERT INTO projects(name, "
                + "description, "
                + "createdAt, "
                + "updatedAt) "
                + "VALUES (?,?,?,?)";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            //Estabelecendo conexão com o banco de dados
            connection = ConnectionFactory.getConnection();

            //Preparando o SQL
            statement = connection.prepareStatement(sql);

            //Setando valores no statement
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));

            //Executando o SQL
            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao salvar o projeto. "
                    + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }

    public void update(Project project) {
        String sql = "UPDATE projects SET NAME = ?,"
                + "DESCRIPTION = ?,"
                + "CREATEDAT = ?"
                + "UPDATEDAT = ?"
                + "WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            //Estabelecendo conexão com o banco de dados
            connection = ConnectionFactory.getConnection();

            //Preparando o SQL
            statement = connection.prepareStatement(sql);

            //Setando valores no statement
            statement.setString(1, project.getName());
            statement.setString(2, project.getDescription());
            statement.setDate(3, new Date(project.getCreatedAt().getTime()));
            statement.setDate(4, new Date(project.getUpdatedAt().getTime()));
            statement.setInt(5, project.getId());

            //Executando o SQL
            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao atualizar o projeto"
                    + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }

    public List<Project> getAll() {
        String sql = "SELECT * FROM projects";

        List<Project> projects = new ArrayList<>();

        Connection connection = null;
        PreparedStatement statement = null;

        ResultSet resultset = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareCall(sql);

            resultset = statement.executeQuery();

            while (resultset.next()) {

                Project project = new Project();

                project.setId(resultset.getInt("id"));

                project.setName(resultset.getString("name"));
                project.setDescription(resultset.getString("description"));

                project.setCreatedAt(resultset.getDate("createdat"));
                project.setUpdatedAt(resultset.getDate("updatedat"));
                projects.add(project);
            }

        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao Buscar os projetos" + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultset);
        }
        //Lista de tarefas que foi criada e carregada do banco de dados
        return projects;

    }
    
    public void removeById(int idProject) {
        String sql = "DELETE FROM projects WHERE ID = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, idProject);
            statement.execute();
        } catch (SQLException ex) {
            throw new RuntimeException("Erro ao deletar o project" + ex.getMessage(),ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }

}
