package com.aerohide.structureadvancements.common;
public class SmartStringBuilder {
    public static String capitalize(String name) {
        if (name == null || name.isEmpty()) return "";
        String[] words = name.split("_");
        StringBuilder sb = new StringBuilder();
        for (String w : words) {
            if (sb.length() > 0) sb.append(" ");
            sb.append(Character.toUpperCase(w.charAt(0))).append(w.substring(1));
        }
        return sb.toString();
    }
    public static String buildDescription(String action, String structureName) {
        if (structureName == null || structureName.isEmpty()) return "";
        char firstChar = Character.toLowerCase(structureName.charAt(0));
        boolean isVowel = (firstChar == 'a' || firstChar == 'e' || firstChar == 'i' || firstChar == 'o' || firstChar == 'u');
        String article = isVowel ? "an" : "a";
        return action + " " + article + " " + structureName;
    }
}
