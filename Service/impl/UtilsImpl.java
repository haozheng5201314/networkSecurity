package Service.impl;

import Service.Utils;
import Service.constant;

import java.math.BigInteger;
import java.nio.CharBuffer;
import java.util.Arrays;
import java.util.Scanner;

public class UtilsImpl implements Utils {

    @Override
    public char[] GetMsg() {
        Scanner sc = new Scanner(System.in);
        // 输入待加密或解密的信息
        System.out.println("请输入待加密或解密的信息（全为字母）");
        String msg;
        msg = sc.nextLine().trim();
        while (msg.isEmpty()) {
            System.out.println("请正确输入信息！");
            msg = sc.nextLine().trim();
        }
        return msg.toCharArray();
    }

    public void traverseMsgsChars(char[] msgChars, int k) {
        for (int i = 0; i < msgChars.length; i++) {
            if (msgChars[i] >= 'a' && msgChars[i] <= 'z') {
                msgChars[i] = (char) ((msgChars[i] - 'a' + k) % 26 + 'a');
            }
            else if (msgChars[i] >= 'A' && msgChars[i] <= 'Z') {
                msgChars[i] = (char) ((msgChars[i] - 'A' + k) % 26 + 'A');
            }
        }
    }

    @Override
    public char[] CaesarCipherEncry(char[] msgChars, int k) {
        char[] msgs = msgChars.clone();
        System.out.println("加密中……");
        traverseMsgsChars(msgChars, k);
        System.out.println("加密成功！加密结果为：" + String.valueOf(msgChars));
        return msgs;
    }

    @Override
    public char[] CaesarCipherDecry(char[] msgChars, int k) {
        char[] msgs = msgChars.clone();
        System.out.println("解密中……");
        traverseMsgsChars(msgs, 26 - k);
        System.out.println("解密成功！解密结果为：" + String.valueOf(msgChars));
        return msgs;
    }

    @Override
    public void CharStats(char[] msgChars) {
        int[] LettersFre = new int[52];
        Arrays.fill(LettersFre, 0);
        // 统计字符次数
        for (char msgChar : msgChars) {
            if (msgChar >= 'a' && msgChar <= 'z') {
                LettersFre[msgChar - 'a']++;
            }
            else if (msgChar >= 'A' && msgChar <= 'Z') {
                LettersFre[msgChar - 'A' + 26]++;
            }
        }

        for (int i = 0; i < LettersFre.length / 2; i++) {
            if (LettersFre[i] != 0) {
                System.out.print((char) ('a' + i) + "\t");
            }
            if (i + 1 >= LettersFre.length / 2) {
                System.out.println();
            }
        }
        for (int i = 0; i < LettersFre.length / 2; i++) {
            if (LettersFre[i] != 0) {
                System.out.print(LettersFre[i] + "\t");
            }
            if (i + 1 >= LettersFre.length / 2) {
                System.out.println();
            }
        }

        for (int i = 0; i < LettersFre.length / 2; i++) {
            if (LettersFre[i + 26] != 0) {
                System.out.print((char) ('A' + i) + "\t");
            }
            if (i + 1 >= LettersFre.length / 2) {
                System.out.println();
            }
        }
        for (int i = 0; i < LettersFre.length / 2; i++) {
            if (LettersFre[i + 26] != 0) {
                System.out.print(LettersFre[i + 26] + "\t");
            }
            if (i + 1 >= LettersFre.length / 2) {
                System.out.println();
            }
        }
    }

    // 消息拆分拓展
    public char[][] MsgsSplit(char[] msgs) {
        int row = msgs.length / 16;
        if (msgs.length%16!=0){
            row++;
        }
        System.out.println(row);
        char[][] result = new char[row][16];
        for (int i = 0; i < row - 1; i++) {
            System.arraycopy(msgs, i * 16, result[i], 0, 16);
        }

        if (row * 16 - msgs.length == 0) {
            // 刚好填满
            System.arraycopy(msgs, (row - 1) * 16, result[row - 1], 0, 16);
        }
        else {
            System.arraycopy(msgs, (row - 1) * 16, result[row - 1], 0, msgs.length - ((row - 1) * 16));
            Arrays.fill(result[row-1],msgs.length - ((row - 1) * 16), 16,'0');
        }
        for (char[] msg : result) {
            printKeys(msg);
        }

        return result;
    }

    // 对明文进行拓展
    public char[] pkcs5Padding(char[] msgs, int bits) {
        char[] result = new char[bits];


        return result;
    }

    // Des加密函数
    @Override
    public char[] DesEncry(char[] msgChars, String key) {
        // 将明文转成二进制形式
        char[] msgBin = keyHexToBin(msgChars);

        // 将密钥转成二进制
        char[] keyBin = keyHexToBin(key.toCharArray());
        // 开始生成16轮的子密钥
        char[][] subKeys = generateSubKeys(keyBin);
        // 开始加密
        // 对明文进行初始置换（假设明文为64位）
        System.out.println("初始密文（置换前）：" + String.valueOf(msgBin));
        // 此时无论置换前密文多长，最终只会置换出64位
        char[] permutedMsg = permute(msgBin, constant.IP);
        System.out.println("初始密文（置换）：" + String.valueOf(permutedMsg));
        char[] L = new char[permutedMsg.length / 2];
        char[] R = new char[permutedMsg.length / 2];
        keySplit(L, R, permutedMsg);
        System.out.println("permuteMsg = " + String.valueOf(permutedMsg));
        System.out.println("L = " + String.valueOf(L));
        System.out.println("R = " + String.valueOf(R));

        // 16次循环
        for (int i = 0; i < 16; i++) {
            // right部分经过f轮询函数处理
            char[] fR = fFunc(R, subKeys[i]);
            System.out.println("经过f轮询函数后的结果为：");
            printKeys(fR);
            // 将L与fR进行异或
            StringBuilder newR = new StringBuilder(32);
            for (int j = 0; j < 32; j++) {
                newR.append((char) ((L[j] - '0') ^ (fR[j] - '0') + '0'));
            }
            // 交换left和right部分
            char[] temp = R.clone();
            R = newR.toString().toCharArray();
            L = temp;

            char[] LR = CharBuffer.allocate(R.length + L.length).put(R).put(L).array();
            System.out.println("加密后的密文：");
            printKeys(keyBinToHex(LR));
        }

        // 最后，将加密后的密文逆初始置换
        char[] result = CharBuffer.allocate(R.length + L.length).put(R).put(L).array();
        result = permute(result, constant.IP_INV);
        System.out.println("最终结果：");
        printKeys(keyBinToHex(result));
        printKeys(result);
        return result;
    }

    public void keySplit(char[] keys1, char[] keys2, char[] keys) {
        System.arraycopy(keys, 0, keys1, 0, keys.length / 2);
        System.arraycopy(keys, keys.length / 2, keys2, 0, keys.length / 2);
    }


    // F轮询函数
    public char[] fFunc(char[] R_32bits, char[] key) {
        // 首先对R进行e拓展至48位
        char[] R_48bits = expansion(R_32bits);
        System.out.println("R48：");
        printKeys(R_48bits);

        StringBuilder R_48bitsXorKey = new StringBuilder(R_48bits.length);

        // 开始异或操作
        for (int i = 0; i < R_48bits.length; i++) {
            R_48bitsXorKey.append(
                    // 因为存储的是字符，所以将字符'0'减去'0'得到整数0
                    (char) ((R_48bits[i] - '0') ^ (key[i] - '0') + '0')
            );
        }
        // 通过S盒替换
        char[] sboxOutPut = sBoxSub(R_48bitsXorKey.toString().toCharArray());
        System.out.println("sboxOutPut");
        printKeys(sboxOutPut);

        // 通过P置换后返回
        return permute(sboxOutPut, constant.P_BOX);
    }

    public char[] expansion(char[] data) {
        return permute(data, constant.E);
    }

    public char[] sBoxSub(char[] data) {
        // 设1组输入为：101011
        StringBuilder sBoxSubResult = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            // 行：每组（一组有6位）的第1位和第6位组合的二进制数，即row = 11 Bin = 2*1+1*1 =3 Dec
            int row = 2 * (data[(6 * i)] - '0') + (data[(6 * i + 5)] - '0');
            // 列：每组的中间四位组成的二进制数，即clo = 0101 Bin = 0*8+1*4+0*2+1*1 = 5
            int clo = 8 * (data[(6 * i + 1)] - '0') + 4 * (data[(6 * i + 2)] - '0')
                    + 2 * (data[(6 * i + 3)] - '0') + (data[(6 * i + 4)] - '0');
            // 从S盒中去除第i盒，第row行，第clo列的数据，追加到sBoxSubResult中
            sBoxSubResult.append(String.format("%4s", Integer.toBinaryString(constant.S_BOX[i][row][clo])).replace(' ', '0'));
        }
        return sBoxSubResult.toString().toCharArray();
    }


    public char[] permute(char[] source, int[] permuteTable) {
        StringBuilder permutedKeySb = new StringBuilder(permuteTable.length);
        for (int i : permuteTable) {
            permutedKeySb.append(source[i - 1]);
        }
        return permutedKeySb.toString().toCharArray();

    }

    public char[][] generateSubKeys(char[] keyBin) {
        // 开始第一轮置换
        char[][] subKeys = new char[16][keyBin.length];
        System.out.print("二进制密密钥：");
        printKeys(keyBin);
        // 经过PC1置换后的结果
        char[] keyBinChars = permute(keyBin, constant.PC_1);
        // 开始切割密钥
        char[] C0 = new char[keyBinChars.length / 2];
        char[] D0 = new char[keyBinChars.length / 2];
        keySplit(C0, D0, keyBinChars);
        System.out.print("C0：");
        printKeys(C0);
        System.out.print("D0：");
        printKeys(D0);
        System.out.println();
        // 生成16组子密钥
        for (int i = 0; i < 16; i++) {
            System.out.println("第 " + (i + 1) + " 次左移，shift = " + constant.SHIFT_SCHEDULE[i]);
            RotateLeftShift(C0, constant.SHIFT_SCHEDULE[i]);
            RotateLeftShift(D0, constant.SHIFT_SCHEDULE[i]);
            System.out.println("左移后");
            System.out.print("C0：");
            printKeys(C0);
            System.out.print("D0：");
            printKeys(D0);

            char[] C0D0Chars = new char[C0.length + D0.length];
            System.arraycopy(C0, 0, C0D0Chars, 0, C0.length);
            System.arraycopy(D0, 0, C0D0Chars, C0.length, D0.length);


            System.out.print("CD：");
            System.out.println(String.valueOf(C0D0Chars));
            // 经过PC2置换
            subKeys[i] = permute(C0D0Chars, constant.PC_2);
            System.out.print("PC2置换后的密钥：");
            printKeys(subKeys[i]);
            System.out.println("转成十六进制后的密钥：" + String.valueOf(keyBinToHex(subKeys[i])));
        }
        System.out.println();
        for (int i = 0; i < subKeys.length; i++) {
            System.out.println("第" + (i + 1) + "次的密钥：" + String.valueOf(keyBinToHex(subKeys[i])));
        }
        return subKeys;
    }


    @Override
    public void RotateLeftShift(char[] source, int shift) {
        int sourceLen = source.length;
        char[] temp = new char[shift];
        System.arraycopy(source, 0, temp, 0, shift);
        System.arraycopy(source, shift, source, 0, sourceLen - shift);
        System.arraycopy(temp, 0, source, sourceLen - shift, shift);
        //return (new String(source, bits, source.length - bits) + new String(source, 0, bits)).toCharArray();
    }

    @Override
    public void DesDecry(char[] msgChars, String key) {

    }

    @Override
    public char[] msgHexToBin(char[] msgChars) {
        StringBuilder tempBinSb = new StringBuilder();
        for (char msgch : msgChars) {
            tempBinSb.append(String.format("%8s", Integer.toBinaryString(msgch)).replace(' ', '0'));
        }
        // 消息的二进制数组
        return tempBinSb.toString().toCharArray();
    }


    public char[] keyHexToBin(char[] keyChars) {
        StringBuilder tempBinSb = new StringBuilder();
        for (char key : keyChars) {
            tempBinSb.append(String.format("%4s", Integer.toBinaryString(Integer.parseInt(String.valueOf(key), 16))).replace(' ', '0'));
        }
        return tempBinSb.toString().toCharArray();
    }

    @Override
    public char[] keyBinToHex(char[] keyChars) {
        return String.format("%" + keyChars.length / 4 + "s", new BigInteger(new String(keyChars), 2).toString(16)).replace(' ', '0').toUpperCase().toCharArray();
    }

    public void printKeys(char[] keys) {
        for (int i = 0; i < keys.length; i++) {
            if (i % 4 == 0 && i != 0) {
                System.out.print(" ");
            }
            System.out.print(keys[i]);
        }
        System.out.println();
    }


    public void CaesarCipherBFA(char[] msgCharsBFA) {
        for (int i = 0; i < 26; i++) {
            traverseMsgsChars(msgCharsBFA, 1);
            System.out.println("暴力破解" + (i + 1) + "，破解结果为：" + String.valueOf(msgCharsBFA));
        }
    }


}
