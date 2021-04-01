/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Importantfunctions;
import java.util.Random;
/**
 *
 * @author Acer
 */
public class Importantfunctions {
    public static String getRandomString(int size)
    {
        String randomString;
        
        char[] chars = "abcdefghijklmnopqrstuvwxyz0123456789".toCharArray();
        StringBuilder sb = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            char c = chars[random.nextInt(chars.length)];
            sb.append(c);
        }
        randomString = sb.toString();
        return randomString;
    } 
    public static int getRandomInt(int maxValue)
    {
        Random generator = new Random();
        int i = generator.nextInt(maxValue);   
        return i;
    }
}
