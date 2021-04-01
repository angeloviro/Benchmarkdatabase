/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.benchmarkdatabase;
import Benchmark.Insert;
import Benchmark.Select;
import Databasemanagement.Databasemanagement;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
/**
 *
 * @author Acer
 */
public class Runbenchmark {
    public static void main(String[] args) throws RunnerException {
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
        Options opt = new OptionsBuilder()
                .include(Insert.class.getSimpleName())
                .warmupIterations(Integer.valueOf(p.getProperty("num_of_warmup_iterations_insert")))
                .measurementIterations(Integer.valueOf(p.getProperty("num_of_insert_iterations")))
                .forks(1)
                .build();
        System.out.println("Esecuzione benchmark inserimento");
        new Runner(opt).run();
        Options opt1 = new OptionsBuilder()
                .include(Select.class.getSimpleName())
                .warmupIterations(Integer.valueOf(p.getProperty("num_of_warmup_iterations_select")))
                .measurementIterations(Integer.valueOf(p.getProperty("num_of_select_iterations")))
                .forks(1)
                .build();
        System.out.println("Esecuzione benchmark selezione");
        new Runner(opt1).run();
    }

}
