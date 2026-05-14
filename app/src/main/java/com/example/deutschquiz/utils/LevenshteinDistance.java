package com.example.deutschquiz.utils;

import static java.lang.Math.min;

import android.util.Log;

import java.util.Arrays;

public class LevenshteinDistance {

    private static int recursive(String str1, String str2) {

        if (str1.isEmpty()) {
            return str2.length();
        }

        if (str2.isEmpty()) {
            return str1.length();
        }

        int substitution = recursive(str1.substring(1),str2.substring(1)) + (str1.charAt(0)==str2.charAt(0)?0:1);
        int insertion = recursive(str1,str2.substring(1)) +1;
        int deletion = recursive(str1.substring(1),str2)+1;
        return min(substitution,min(insertion,deletion));
    }


    public static int iterative(String str1, String str2) {
        int[][] matrix = new int[str1.length()+1][str2.length()+1];
        for (int i = 0; i<=str1.length(); i++) {
            for (int j = 0; j<=str2.length();j++) {
                if (i==0) {
                    matrix[i][j] = j;
                } else if (j==0) {
                    matrix[i][j] =i;
                } else {
                    matrix[i][j] = min(
                            matrix[i-1][j-1]+(str1.charAt(i-1)==str2.charAt(j-1)?0:1)
                            ,min(matrix[i-1][j]+1,matrix[i][j-1]+1));
                }
            }
        }

        return matrix[str1.length()][str2.length()];
    }


}
