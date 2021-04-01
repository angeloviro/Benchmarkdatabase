/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Benchmark;
import Databasemanagement.Databasemanagement;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
/**
 *
 * @author Acer
 */
@State(Scope.Benchmark)
public class Select extends GeneralBenchmark{
    private ResultSet resultSet;
    private int maxPrimaryKeyValue;
    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public void runtest() throws SQLException{
            resultSet = stmt.executeQuery(); 
    }
        @Setup(Level.Trial)
        @Override
        public void init() throws SQLException
        {
         FileReader reader=null;  
        try {
            reader = new FileReader("C:\\Users\\Acer\\Documents\\NetBeansProjects\\Benchmarkdatabase\\src\\main\\resource\\Configuration.properties");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Select.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
    Properties p=new Properties();  
        try {  
            p.load(reader);
        } catch (IOException ex) {
            Logger.getLogger(Select.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
     String username= p.getProperty("username");  
    String password= p.getProperty("password"); 
    String testdatabase=p.getProperty("testdatabase");
    String servername= p.getProperty("servername");  
    String portnumber= p.getProperty("portnumber"); 
    int dbms=Integer.valueOf(p.getProperty("dbms"));
        b=new Databasemanagement(username,password,testdatabase,servername,portnumber,dbms);     
            b.connect();
            if(stmt != null)
            stmt.close();
            stmt = null;
        try {
           String selectSQL="SELECT Chiaveprimariatest, Varchartest, Integertest FROM Testtabella WHERE chiaveprimariatest=?";
            stmt = this.b.getCon().prepareStatement(selectSQL);
        } catch (SQLException ex) {
           System.out.println(ex);
        }
         try {
            String selectSQL = "SELECT MAX(Chiaveprimariatest) FROM Testtabella";
            PreparedStatement selectMaxPrimaryKeyPreparedStatement = this.b.getCon().prepareStatement(selectSQL);
            this.b.getCon().setAutoCommit(true);
            ResultSet rs = selectMaxPrimaryKeyPreparedStatement.executeQuery(); 
            if(rs.next())
            {
                maxPrimaryKeyValue = rs.getInt(1);
            }
            rs.close();
            selectMaxPrimaryKeyPreparedStatement.close();  
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        }
@TearDown(Level.Trial)
      @Override  
        public void end() throws SQLException 
        {
            stmt.close();
            stmt = null;
            b.closeconnection();
        }
@TearDown(Level.Invocation)
        public void invocationTeardown() throws SQLException
        {
            if(resultSet != null)
                resultSet.close();
        }
@Setup(Level.Invocation)
    @Override
    public void invocationsetup() throws SQLException {
       int primaryKeyValue = 0;
            Random m=new Random();
            if(maxPrimaryKeyValue > 0)
              primaryKeyValue = m.nextInt(this.maxPrimaryKeyValue+1);
              b.getCon().setAutoCommit(true);
            stmt.setInt(1, primaryKeyValue);
    }
}
