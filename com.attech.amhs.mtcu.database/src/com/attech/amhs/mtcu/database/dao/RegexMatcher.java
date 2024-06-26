/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.attech.amhs.mtcu.database.dao;

/**
 *
 * @author PC
 */
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexMatcher {

    public static void main(String[] args) {
        // List of strings to search within

        String add = "VVTS";

        // Regex pattern to match string1 or string2
        String regex = "^VVNB|^VVTS";

        // Compile the regex pattern
        Pattern pattern = Pattern.compile(regex);

        // Iterate over the list and find matches
        Matcher matcher = pattern.matcher(add);
        if (matcher.find()) {
            System.out.println("Found a match in: " + add);
        } else {
            System.out.println("No match in: " + add);
        }

    }
}