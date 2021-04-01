/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Benchmark;
import Databasemanagement.Databasemanagement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
/**
 *
 * @author Acer
 */
public abstract class GeneralBenchmark {
    protected PreparedStatement stmt;
    protected Databasemanagement b;
    public abstract void init() throws SQLException;
    public abstract void invocationsetup() throws SQLException;
    public abstract void end()throws SQLException;
    public abstract void runtest() throws SQLException;
}
