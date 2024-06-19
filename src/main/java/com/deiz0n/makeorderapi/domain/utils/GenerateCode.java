package com.deiz0n.makeorderapi.domain.utils;

import com.deiz0n.makeorderapi.domain.exceptions.GenerateCodeException;

import java.util.Arrays;

public class GenerateCode {

    //Organiza o codigo gerado e o retorna
    public static String generate() {
        String[] code = new String[4];
        int x = 0;
        for (int y=0; y<=2; y++) {
            code[x] = sortLetter(index());
            code[y+1] = sortNumber();
            x=2;
        }
        return (Arrays.toString(code).replace(", ", "").substring(1, 5));
    }

    //Gera um número aleatorio
    private static String sortNumber() {
        var calc = Math.random() * 9;
        return String.format("%.0f", calc);
    }

    //Gera uma letra aleatoria
    private static String sortLetter(int index) {
        if (index < 1 || index > 26)
            throw new GenerateCodeException("Erro ao gerar código de recuperação");
        return String.valueOf((char)(index + 64));
    }

    private static int index() {
        return (int) (Math.random() * 26 + 1);
    }

}
