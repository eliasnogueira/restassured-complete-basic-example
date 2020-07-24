/*
 * MIT License
 *
 * Copyright (c) 2020 Elias Nogueira
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
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