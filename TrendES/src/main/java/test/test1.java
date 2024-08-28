/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;
import org.jsoup.parser.Parser;

/**
 *
 * @author baghe
 */
public class test1 {
    public static void main(String[] args) {
        String s="سواحل جزء انفال است/ معرفی متصرفین سواحل به دستگاه قضایی&nbsp؛";
        String cleaned=Parser.unescapeEntities(s,false);
        System.out.println("s: " + s);
        System.out.println("c: " + cleaned);
    }
    
}
