/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package amhs.aftn.testtool.highlevel.Message;

import java.io.File;
import java.util.List;

/**
 *
 * @author andh
 */
public class Out {
    public static final String output = "D:\\Projects\\AMHS\\output";
    
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";
    
    private static final String success = ANSI_GREEN + "[V]" + ANSI_RESET;
    private static final String failure = ANSI_RED + "[X]" + ANSI_RESET;
    
    public static void print(String action, boolean result ) {
        String format = String.format("%-60s%s", action, result ? success : failure);
        System.out.println(format);
    }
    
    public static void print(String action) {
        String format = String.format("%-60s", action);
        System.out.print(format);
    }
    
    public static void print(boolean result) {
        System.out.println(result ? success : failure);
    }
    
    public static void printHeader(String caseNo) {
        System.out.println();
        System.out.println();
        System.out.println(caseNo);
        System.out.println("----------------------------------------------------------------------------------");
    }
    
    public static void printResult(String caseNo, boolean result) {
        System.out.println("----------------------------------------------------------------------------------");
        System.out.print(result ? ANSI_GREEN : ANSI_RED);
        String format = String.format("%-60s%s", caseNo, result ? success : failure);
        System.out.print(format);
        System.out.println(ANSI_RESET);
    }
    
    public static void cleanOutputFolder() {
        File folder = new File(output);
        File[] files = folder.listFiles();
        for (File file : files) {
            file.delete();
        }
    }
}
