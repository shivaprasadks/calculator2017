package com.citizen.calculator2017.utils;

public class HtmlFormatter {
    public static String htmlNewLine() {
        return "<br/>";
    }

    public static String htmlColorln(String str, String clr) {
        return "<font color=\"#" + clr + "\">" + str + "</font><br />";
    }

    public static String htmlhrLine() {
        return "<hr>";
    }

    public static String htmlBold(String str) {
        return "<b>" + str + "</b>";
    }

    public static String htmlBoldln(String str) {
        return "<b>" + str + "</b><br/>";
    }

    public static String htmlcolor(String str, String clr) {
        return "<font color=\"#" + clr + "\">" + str + "</font>";
    }
}
