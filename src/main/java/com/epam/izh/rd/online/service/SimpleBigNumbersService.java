package com.epam.izh.rd.online.service;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class SimpleBigNumbersService implements BigNumbersService {

    /**
     * Метод делит первое число на второе с заданной точностью
     * Например 1/3 с точностью 2 = 0.33
     *
     * @param range точность
     * @return результат
     */
    @Override
    public BigDecimal getPrecisionNumber(int a, int b, int range) {
        return new BigDecimal(a).divide(new BigDecimal(b), range, RoundingMode.HALF_UP);
    }

    /**
     * Метод находит простое число по номеру
     *
     * @param range номер числа, считая с числа 2
     * @return простое число
     */
    @Override
    public BigInteger getPrimaryNumber(int range) {
        BigInteger k = new BigInteger("2");
        while (range > 0) {
            BigInteger i = new BigInteger("2");
            k = k.add(BigInteger.ONE);
            while (i.multiply(i).compareTo(k) < 1) {
                if (k.remainder(i).compareTo(BigInteger.ZERO) == 0) {
                    break;
                }
                i = i.add(new BigInteger("1"));
            }
            if (i.multiply(i).compareTo(k) > 0) {
                --range;
            }
        }
        return k;
    }
}

