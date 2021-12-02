package repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import models.Curso;

public class CursoDao {
    
    /*
    private List<Curso> cursos;
    
    public CursoDao() {
        cursos = new ArrayList<>();
        
        Curso cursoOne = new Curso("C001", "Patrones de Diseño", 4);
        Curso cursoTwo = new Curso("C002", "Programación Paralela", 4); 
        Curso cursoThree = new Curso("C003", "Integración de Sistemas", 4);  
        
        this.cursos.add(cursoOne);
        this.cursos.add(cursoTwo);
        this.cursos.add(cursoThree);
    }

    public List<Curso> listar() {
        return this.cursos;
    }

    public Curso encontrar(Curso curso) {
        Curso response = new Curso();
        for (Curso c: this.cursos) {
            if(curso.getCodigo().equals(c.getCodigo())) {
                response = curso; 
            }
        }
        return response;
    }

    public int insertar(Curso curso) {
        this.cursos.add(curso);
        return 1;
    }
    
    
    public int actualizar(Curso curso) {
        Curso response = curso;
        
        for(Curso c: this.cursos) {
            if(c.getCodigo().equals(curso.getCodigo())) {
                this.cursos.remove(c);
            }
        }
        this.cursos.add(response);
        return 1;
    }
    
    public int eliminar (Curso curso) {
        this.cursos.remove(curso);
        return 1;
    }
    */
    
    private static final String SQL_SELECT = "SELECT codcur, nomcur, credito"
            + " FROM dbo.cursos";

    private static final String SQL_SELECT_BY_CODE = "SELECT codcur, nomcur, credito"
            + " FROM dbo.cursos WHERE codcur= ?";

    private static final String SQL_INSERT = "INSERT INTO dbo.cursos (codcur, nomcur, credito) "
            + "VALUES (?, ?, ?)";

    private static final String SQL_UPDATE = "UPDATE dbo.cursos "
            + "SET nomcur=?, credito=? WHERE codcur=?";
    
    private static final String SQL_DELETE = "DELETE FROM dbo.cursos WHERE codcur= ?";

    public List<Curso> listar() {

        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        List<Curso> cursos = new ArrayList<>();

        try {
            con = Conexion.getConnection();
            pstm = con.prepareStatement(SQL_SELECT);
            rs = pstm.executeQuery();

            while (rs.next()) {
                String codigo = rs.getString("codcur");
                String nombre = rs.getString("nomcur");
                int creditos = rs.getInt("credito");

                Curso ret = new Curso(codigo, nombre, creditos);
                cursos.add(ret);
            }
        } catch (SQLException ex) {
            ex.printStackTrace(System.out);
        } finally {

            Conexion.close(rs);
            Conexion.close(pstm);
            Conexion.close(con);
        }
        return cursos;
    }

    public Curso encontrar(Curso curso) {

        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {

            con = Conexion.getConnection();
            pstm = con.prepareStatement(SQL_SELECT_BY_CODE);

            //proporciono el primer parámetro entero
            pstm.setString(1, curso.getCodigo());
            rs = pstm.executeQuery();

            //me posiciono en el registro devuelto, de haberlo
            if(rs.next()) {
                String codigo = rs.getString("codcur");
                String nombre = rs.getString("nomcur");
                int creditos = rs.getInt("credito");

                curso.setCodigo(codigo);
                curso.setNombre(nombre);
                curso.setCreditos(creditos);
            }

        } catch (Exception ex) {

            ex.printStackTrace(System.out);
        } finally {

            Conexion.close(rs);
            Conexion.close(pstm);
            Conexion.close(con);
        }
        return curso;
    }

    public int insertar(Curso curso) {
        Connection con = null;
        PreparedStatement pstm = null;
        int rows=0;
        
        try {
            con = Conexion.getConnection();
            pstm = con.prepareStatement(SQL_INSERT);
            pstm.setString(1, curso.getCodigo());
            pstm.setString(2, curso.getNombre());
            pstm.setInt(3, curso.getCreditos());
            
            rows=pstm.executeUpdate();
        } catch (SQLException ex) {

            ex.printStackTrace(System.out);
        } finally {

            Conexion.close(pstm);
            Conexion.close(con);
        }
        return rows;
    }
    
    public int actualizar(Curso curso){
        Connection con = null;
        PreparedStatement pstm = null;
        int rows=0;
        
        try {
            con = Conexion.getConnection();
            pstm = con.prepareStatement(SQL_UPDATE);
            pstm.setString(1, curso.getNombre());
            pstm.setInt(2, curso.getCreditos());
            pstm.setString(3, curso.getCodigo());
            
            rows=pstm.executeUpdate();
        } catch (SQLException ex) {

            ex.printStackTrace(System.out);
        } finally {

            Conexion.close(pstm);
            Conexion.close(con);
        }
        return rows;
    }

    
    public int eliminar(Curso curso){
        
        Connection con = null;
        PreparedStatement pstm = null;
        int rows=0;
        
        try {

            con = Conexion.getConnection();
            pstm = con.prepareStatement(SQL_DELETE);
            pstm.setString(1, curso.getCodigo());
            
            rows=pstm.executeUpdate();
        } catch (SQLException ex) {

            ex.printStackTrace(System.out);
        } finally {

            Conexion.close(pstm);
            Conexion.close(con);
        }
        return rows;
    }

}
