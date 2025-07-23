package sample.app.todo.domain.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;
import java.util.UUID;


import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import lombok.RequiredArgsConstructor;
import sample.app.todo.domain.entity.Todo;

import java.util.ArrayList;
import java.util.Collections;
import sample.app.common.exception.TodoNotFoundException;

@Repository
@Primary
@RequiredArgsConstructor
public class JdbcTodoRepository implements TodoRepository {

  private final DataSource dataSource;

  @Override
  public Todo save(Todo todo) {
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;

    System.out.println("JdbcTodoRepository.save() 호출됨 - todo.getId(): " + todo.getId());

    // 해당 ID가 존재하는지 확인
    boolean exists = existsById(todo.getId());
    System.out.println("ID 존재 여부: " + exists);
    
    if (exists) {
      // UPDATE
      try {
        String sql = "UPDATE todo SET title = ?, completed = ? WHERE todo_id = ?";      
        conn = getConnection();
        pstmt = conn.prepareStatement(sql);

        pstmt.setString(1, todo.getTitle());
        pstmt.setBoolean(2, todo.isCompleted());
        pstmt.setObject(3, todo.getId());
        int updatedRows = pstmt.executeUpdate();
        System.out.println("UPDATE 실행됨 - 영향받은 행: " + updatedRows);
        
        return todo;
      } catch (SQLException e) {
        System.err.println("UPDATE 중 SQL 오류: " + e.getMessage());
        throw new IllegalStateException(e);
      } finally {
        close(conn, pstmt, rs);
      }
    } else {
        // INSERT
        String sql = "INSERT INTO todo (todo_id, title, completed) VALUES (?, ?, ?)";
        try {
          conn = getConnection();
          pstmt = conn.prepareStatement(sql);
          
          pstmt.setObject(1, todo.getId());
          pstmt.setString(2, todo.getTitle());
          pstmt.setBoolean(3, todo.isCompleted());
          
          int insertedRows = pstmt.executeUpdate();
          System.out.println("INSERT 실행됨 - 영향받은 행: " + insertedRows);
          
          return todo;
        } catch (SQLException e) {
          System.err.println("INSERT 중 SQL 오류: " + e.getMessage());
          throw new IllegalStateException(e);
        } finally {
          close(conn, pstmt, rs);
        }
    }
  }

  @Override
  public List<Todo> findAll() {
    String sql = "SELECT todo_id, title, completed, created_at, updated_at FROM todo";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      rs = pstmt.executeQuery();
      
      List<Todo> todos = new ArrayList<>();
      while (rs.next()) {
        Todo todo = new Todo();
        todo.setId((UUID) rs.getObject("todo_id"));
        todo.setTitle(rs.getString("title"));
        todo.setCompleted(rs.getBoolean("completed"));
        // createdAt과 updatedAt은 JPA가 관리하므로 여기서는 설정하지 않음
        todos.add(todo);
      }
      
      System.out.println("findAll - 조회된 Todo 개수: " + todos.size());
      return todos;
    } catch (SQLException e) {
      System.err.println("findAll 중 SQL 오류: " + e.getMessage());
      throw new IllegalStateException(e);
    } finally {
      close(conn, pstmt, rs);
    }
  }

  @Override
  public Optional<Todo> findById(UUID id) {
    String sql = "SELECT todo_id, title, completed FROM todo WHERE todo_id = ?";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setObject(1, id);
      rs = pstmt.executeQuery();
      
      if (rs.next()) {
        Todo todo = new Todo();
        todo.setId((UUID) rs.getObject("todo_id"));
        todo.setTitle(rs.getString("title"));
        todo.setCompleted(rs.getBoolean("completed"));
        return Optional.of(todo);
      }
      
      return Optional.empty();
    } catch (SQLException e) {
      throw new IllegalStateException(e);
    } finally {
      close(conn, pstmt, rs);
    }
  }

  @Override
  public void deleteById(UUID id) {
    String sql = "DELETE FROM todo WHERE todo_id = ?";
    Connection conn = null;
    PreparedStatement pstmt = null;
    
    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setObject(1, id);
      pstmt.executeUpdate();
    } catch (SQLException e) {
      throw new IllegalStateException(e);
    } finally {
      close(conn, pstmt, null);
    }
  }

  @Override
  public void deleteBulk(List<UUID> ids) {
    if (ids.isEmpty()) {
      return;
    }
    
    // 존재하지 않는 ID 체크
    for (UUID id : ids) {
      if (!existsById(id)) {
        throw new TodoNotFoundException(id.toString());
      }
    }
    
    String sql = "DELETE FROM todo WHERE todo_id IN (" + 
                 String.join(",", Collections.nCopies(ids.size(), "?")) + ")";
    Connection conn = null;
    PreparedStatement pstmt = null;
    
    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      
      for (int i = 0; i < ids.size(); i++) {
        pstmt.setObject(i + 1, ids.get(i));
      }
      
      int deletedRows = pstmt.executeUpdate();
      System.out.println("deleteBulk - 삭제된 행: " + deletedRows);
    } catch (SQLException e) {
      System.err.println("deleteBulk 중 SQL 오류: " + e.getMessage());
      throw new IllegalStateException(e);
    } finally {
      close(conn, pstmt, null);
    }
  }
  
  @Override
  public void clear() {
    String sql = "DELETE FROM todo";
    Connection conn = null;
    PreparedStatement pstmt = null;
    
    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      int deletedRows = pstmt.executeUpdate();
      System.out.println("clear - 삭제된 행: " + deletedRows);
    } catch (SQLException e) {
      System.err.println("clear 중 SQL 오류: " + e.getMessage());
      throw new IllegalStateException(e);
    } finally {
      close(conn, pstmt, null);
    }
  }
  
  private boolean existsById(UUID id) {
    String sql = "SELECT COUNT(*) FROM todo WHERE todo_id = ?";
    Connection conn = null;
    PreparedStatement pstmt = null;
    ResultSet rs = null;
    
    try {
      conn = getConnection();
      pstmt = conn.prepareStatement(sql);
      pstmt.setObject(1, id);
      rs = pstmt.executeQuery();
      
      if (rs.next()) {
        int count = rs.getInt(1);
        System.out.println("existsById - ID: " + id + ", COUNT: " + count);
        return count > 0;
      }
      
      return false;
    } catch (SQLException e) {
      System.err.println("existsById 중 SQL 오류: " + e.getMessage());
      throw new IllegalStateException(e);
    } finally {
      close(conn, pstmt, rs);
    }
  }

  private Connection getConnection() {
    return DataSourceUtils.getConnection(dataSource);
  }

  private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) {
    try {
      if (rs != null)  {
        rs.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      if (pstmt != null) {
        pstmt.close();
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    try {
      if (conn != null) {
        close(conn);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  private void close(Connection conn) throws SQLException {
    DataSourceUtils.releaseConnection(conn, dataSource);
  }
}
