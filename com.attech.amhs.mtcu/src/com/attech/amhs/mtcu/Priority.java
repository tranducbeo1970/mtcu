/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.attech.amhs.mtcu;

/**
 *
 * @author andh
 */
public enum Priority {
    
    SS("SS", 0, 107, 2), DD("DD", 1, 71, 0), FF("FF", 2, 57, 0), GG("GG", 3, 28, 1), KK("KK", 4, 14, 1);
    
    
    private final int id;
    private final String name;
    private final int precedenceId;
    private final int priority;
    
    Priority(String name, int id, int precedecenId, int priority) { this.id = id; this.name = name; this.precedenceId = precedecenId; this.priority = priority;}

    public int getValue() { return id; }
    public String getName() { return name; }
    public int getPrecedenceId() { return precedenceId; }
    public int getPriority() {  return priority; }
    
//    public Priority parse(String name) {
//        switch (name) {
//            case "SS":
//                return SS;
//            case "DD":
//                return DD;
//            case "FF":
//                return FF;
//            case "GG":
//                return GG;
//            case "KK":
//                return KK;
//        }
//        return null;
//    }
//    
//    public Priority parse(int value) {
//        switch (value) {
//            case 0:
//                return SS;
//            case 107:
//                return SS;
//            case 1:
//                return DD;
//            case 71:
//                return DD;
//            case 2:
//                return FF;
//            case 57:
//                return FF;
//            case 3:
//                return GG;
//            case 28:
//                return GG;
//            case 4:
//                return KK;
//            case 14:
//                return KK;
//
//        }
//
//        return null;
//    }


}
