package com.eliasnogueira.credit.data.support;

import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Copied from https://needforjava.wordpress.com/2010/11/24/gerar-e-validar-cpf/
 */
public class CpfGenerator {

    public String generate() {
        Random r = new Random();
        String sbCpfNumber = IntStream.range(0, 9).mapToObj(i -> String.valueOf(r.nextInt(9)))
            .collect(Collectors.joining());

        return generateDigits(sbCpfNumber);
    }

    private String generateDigits(String digitsBase) {
        StringBuilder sbCpfNumber = new StringBuilder(digitsBase);
        int total = 0;
        int multiple = digitsBase.length() + 1;

        for (char digit : digitsBase.toCharArray()) {
            long partial = Integer.parseInt(String.valueOf(digit)) * (multiple--);
            total += partial;
        }

        int remainder = Integer.parseInt(String.valueOf(Math.abs(total % 11)));
        remainder = remainder < 2 ? 0 : 11 - remainder;

        sbCpfNumber.append(remainder);

        return sbCpfNumber.length() < 11 ? generateDigits(sbCpfNumber.toString()) : sbCpfNumber.toString();
    }
}