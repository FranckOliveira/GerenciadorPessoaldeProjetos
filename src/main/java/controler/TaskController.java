package controler;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Task;
import util.ConnectionFactory;

/**
 *
 * @author franc
 */
public class TaskController {

    public void save(Task task) {
        String sql = "INSERT INTO tasks (idProject,"
                + "name,"
                + "description,"
                + "notes,"
                + "completed,"
                + "deadline,"
                + "createdAt,"
                + "updatedAt) VALUES(?,?,?,?,?,?,?,?)";

                Connection connection = null;
                PreparedStatement statement = null;

        try {
             //Estabelecendo conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            
            //Preparando o SQL
            statement = connection.prepareStatement(sql);
            
            //Setando valores no statement
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setString(4, task.getNotes());
            statement.setBoolean(5, task.isIsCompleted());
            statement.setDate(6, new java.sql.Date(task.getDeadline().getTime()));
            statement.setDate(7, new java.sql.Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new java.sql.Date(task.getUpdatedAt().getTime()));
            
            //Executando o SQL
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao salvar a tarefa. "
                    + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);

        }

    }

    public void update(Task task) {

        String sql = "UPDATE tasks SET "
                + "idproject = ?,"
                + "name = ?,"
                + "description = ?,"
                + "notes = ?,"
                + "completed = ?,"
                + "deadline = ?,"
                + "createdAt = ?,"
                + "updatedAt = ?,"
                + "WHERE id = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            //Estabelecendo conexão com o banco de dados
            connection = ConnectionFactory.getConnection();
            
            //Preparando o SQL
            statement = connection.prepareStatement(sql);
            
            //Setando valores no statement
            statement.setInt(1, task.getIdProject());
            statement.setString(2, task.getName());
            statement.setString(3, task.getDescription());
            statement.setString(4, task.getNotes());
            statement.setBoolean(5, task.isIsCompleted());
            statement.setDate(6, new java.sql.Date(task.getDeadline().getTime()));
            statement.setDate(7, new java.sql.Date(task.getCreatedAt().getTime()));
            statement.setDate(8, new java.sql.Date(task.getUpdatedAt().getTime()));
            statement.setInt(9, task.getId());
            
            //Executando o SQL
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao atualizar a tarefa. "
                    + ex.getMessage(), ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }

    }

    public void removeById(int taskId) {
        String sql = "DELETE FROM TASKS WHERE ID = ?";

        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareStatement(sql);
            statement.setInt(1, taskId);
            statement.execute();
        } catch (Exception ex) {
            throw new RuntimeException("Erro ao deletar a tarefa. " + ex.getMessage(),ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement);
        }
    }

    public List<Task> getAll(int idProject) {
        String sql = "SELECT * FROM tasks WHERE IDPROJECT = ?";

        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultset = null;

        /*Lista de tarefas que será devolvida quando a 
        chamada do método acontecer.*/
        List<Task> tasks = new ArrayList<Task>();

        try {
            connection = ConnectionFactory.getConnection();
            statement = connection.prepareCall(sql);
            statement.setInt(1, idProject);
            resultset = statement.executeQuery();

            while (resultset.next()) {

                Task task = new Task();
                task.setId(resultset.getInt("id"));
                task.setIdProject(resultset.getInt("idProject"));
                task.setName(resultset.getString("name"));
                task.setDescription(resultset.getString("description"));
                task.setNotes(resultset.getString("notes"));
                task.setIsCompleted(resultset.getBoolean("completed"));
                task.setDeadline(resultset.getDate("deadline"));
                task.setCreatedAt(resultset.getDate("createdat"));
                task.setUpdatedAt(resultset.getDate("updatedat"));
                tasks.add(task);
            }

        } catch (Exception ex) {
            throw new RuntimeException("Erro ao inserir a tarefa"+ex.getMessage(),ex);
        } finally {
            ConnectionFactory.closeConnection(connection, statement, resultset);
        }
        //Lista de tarefas que foi criada e carregada do banco de dados
        return tasks;

    }
}
