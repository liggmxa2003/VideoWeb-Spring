package com.ligg.utils;

import java.math.BigInteger;
import java.util.UUID;

public class UUIDUtil {

    public static BigInteger getUUID() {
        // 生成一个全局唯一标识符UUID
        UUID uuid = UUID.randomUUID();

        // 获取UUID的最高有效位部分，并转换为BigInteger类型
        BigInteger mostSignificantBits = BigInteger.valueOf(uuid.getMostSignificantBits());

        // 获取UUID的最低有效位部分，并转换为BigInteger类型
        BigInteger leastSignificantBits = BigInteger.valueOf(uuid.getLeastSignificantBits());

        /**
         * 将UUID的最高有效位和最低有效位组合成一个更大的整数
         * 通过将最高有效位左移64位，然后与最低有效位进行按位或操作
         * 这样做是为了得到一个唯一且有序的整数标识
         */
        BigInteger combined = mostSignificantBits.shiftLeft(64).or(leastSignificantBits);

        // 获取组合后整数的绝对值，确保结果为非负数
        BigInteger abs = combined.abs();

        //计算10位数的最大值
        BigInteger maxTenDigitNumber = BigInteger.TEN.pow(10).subtract(BigInteger.ONE);

        // 对绝对值取模，确保结果是一个10位数的整数
        return abs.mod(maxTenDigitNumber);
    }
}
