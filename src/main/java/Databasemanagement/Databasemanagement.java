/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Databasemanagement;
import Benchmark.Select;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Logger;
/**
 *
 * @author Acer
 */
   public class Databasemanagement{
    private String username;
    private String password;
    private String database;
    private String servername;
    private String portnumber;
    private int dbms;
    private Connection con;
    public Databasemanagement(String username, String password, String database, String server,String portnumber,int dbms) {
        this.username = username;
        this.password = password;
        this.database = database;
        this.servername=server;
        this.portnumber=portnumber;
        this.dbms=dbms;
        this.con=null;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public String getDatabase() {
        return database;
    }
    public int getDbms() {
        return dbms;
    }
    public String getServername() {
        return servername;
    }
    public String getPortnumber() {
        return portnumber;
    }
    public Connection getCon() {
        return con;
    }
    public void setNomeutente(String username) {
        this.username= username;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setDatabase(String database) {
        this.database = database;
    }
    public void setDbms(int dbms) {
        this.dbms = dbms;
    }
    public void connect() {
        String connectionUrl="";
        String driver="";
        switch(this.dbms) {
                   case 0:
                       connectionUrl="jdbc:postgresql://" + this.servername + ":" + this.portnumber + "/" + this.database;

                       driver="org.postgresql.Driver";     
                       break;
                    case 1: 
                           connectionUrl = "jdbc:sqlserver://" + this.servername + ":" + this.portnumber + ";databaseName=" + this.database + ";user=" + this.username + ";password=" + password;
           
                           driver="com.microsoft.sqlserver.jdbc.SQLServerDriver";  
                          break;
                 default:
                         connectionUrl=" ";
                         driver=" ";
                         break;
}
        try {
            // Create a variable for the connection string.
            
            // Establish the connection.
            Class.forName(driver);  
            con=DriverManager.getConnection(connectionUrl,this.username,this.password);
        } catch (ClassNotFoundException | SQLException ex) {
            System.out.println(ex);    
        }
    } 
    public int createtable() {
        int result=0;
        String dropTableSQL="";
        String createTableSQL="";
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
    
    int maximum_size=Integer.valueOf(p.getProperty("maximum_size"));
        switch(this.dbms){
            case 0: 
                   dropTableSQL = "  DROP TABLE IF EXISTS Testtabella";
                   createTableSQL = "CREATE TABLE Testtabella ("
                    + "Chiaveprimariatest SERIAL PRIMARY KEY, "
                    + "Varchartest VARCHAR("+maximum_size+") NOT NULL, "
                    + "Integertest INTEGER NOT NULL "
                    
                    + ")";
            break;
            case 1:
            dropTableSQL = "  IF EXISTS(select * from sysobjects where name=?) drop table Testtabella";
            createTableSQL = "CREATE TABLE Testtabella("
                    + "Chiaveprimariatest INT NOT NULL IDENTITY(1,1), "
                    + "Varchartest VARCHAR("+maximum_size+") NOT NULL, "
                    + "Integertest INT NOT NULL, "
                    + "PRIMARY KEY (Chiaveprimariatest) "
                    + ")";
         break;
            default: 
                dropTableSQL="";
                createTableSQL="";
                return -1;
        }
       
        try (PreparedStatement dropTablePreparedStatement = con.prepareStatement(dropTableSQL)) {

            con.setAutoCommit(true);
            // execute drop SQL stetement
            if(dbms==1)
            dropTablePreparedStatement.setString(1, "Testtabella");

            dropTablePreparedStatement.executeUpdate();
            
        } catch (SQLException  ex) {
           System.out.println(ex);
            result = -1;
        }
        
        if(result < 0)
            return result; 
        try (PreparedStatement createTablePreparedStatement = con.prepareStatement(createTableSQL)) {
            createTablePreparedStatement.executeUpdate(); 
        } catch (SQLException ex) {
            System.out.println(ex);
            result = -1;
        }
        return result;
    } 
    public void closeconnection(){
    try {
          if(con!=null){
            con.close();
            con=null;
            System.out.println("Connection closed");
          }
        } catch (SQLException ex) {
            System.out.println(ex);
        }
    }
}
 

