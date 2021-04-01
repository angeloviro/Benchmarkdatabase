/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Benchmark;
import Databasemanagement.Databasemanagement;
import Importantfunctions.Importantfunctions;
import Importantfunctions.Importantfunctions;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
public class Insert extends GeneralBenchmark{
  int num_inserts_for_transaction; 
  int maximum_size;
  int maximum_value;
    @Setup(Level.Trial) 
    @Override
    public void init() throws SQLException{
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
    num_inserts_for_transaction=Integer.valueOf(p.getProperty("num_inserts_for_transaction"));
    maximum_size=Integer.valueOf(p.getProperty("maximum_size"));
    maximum_value=Integer.valueOf(p.getProperty("maximum_value"));
        b=new Databasemanagement(username,password,testdatabase,servername,portnumber,dbms);
            b.connect();
            int createTableResult = b.createtable();
            if(createTableResult != 0)
                return;  
            String insertTableSQL = "INSERT INTO Testtabella" 
                    + "(Varchartest, Integertest) VALUES"
                    + "(?,?)";
            stmt= b.getCon().prepareStatement(insertTableSQL);
            b.getCon().setAutoCommit(false);
    }
   @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
   public void runtest() throws SQLException{ 
         stmt.executeBatch();
            b.getCon().commit();
            }
      @TearDown(Level.Trial)  
    @Override
      public void end() throws SQLException
      {
          stmt.close();
          stmt=null;
        b.closeconnection();
      }
@Setup(Level.Invocation)
    @Override
    public void invocationsetup() throws SQLException {
      stmt.clearBatch();
            for(int i = 0; i < num_inserts_for_transaction; i++ )
            {
                int length=Importantfunctions.getRandomInt(maximum_size);
                stmt.setString(1,Importantfunctions.getRandomString(length));
                stmt.setInt(2,Importantfunctions.getRandomInt(maximum_value));
                stmt.addBatch();
            }
    }   
   }


