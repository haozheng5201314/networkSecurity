package Service.impl;

import Service.Utils;
import Service.constant;

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
    public void CaesarCipherEncry(char[] msgChars, int k) {
        System.out.println("加密中……");
        traverseMsgsChars(msgChars, k);
        System.out.println("加密成功！加密结果为：" + String.valueOf(msgChars));
    }

    @Override
    public void CaesarCipherDecry(char[] msgChars, int k) {
        System.out.println("解密中……");
        traverseMsgsChars(msgChars, 26 - k);
        System.out.println("解密成功！解密结果为：" + String.valueOf(msgChars));
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

    // Des加密函数
    @Override
    public void DesEncry(char[] msgChars, String key) {
        // 将明文转成二进制形式
        char[] msgBin = msgHexToBin(msgChars);
        // 将密钥转成二进制
        char[] keyBin = keyHexToBin(key.toCharArray());
        // 开始生成16轮的子密钥
        char[][] subKeys = generateSubKeys(keyBin);
        // 开始加密
        // 对明文进行初始置换（假设明文为64位）
        char[] permutedMsg = permute(msgBin, constant.IP);
        char[] L = new char[permutedMsg.length / 2];
        char[] R = new char[permutedMsg.length / 2];
        System.arraycopy(permutedMsg, 0, L, 0, permutedMsg.length / 2);
        System.arraycopy(permutedMsg, permutedMsg.length / 2, R, 0, permutedMsg.length / 2);
        System.out.println("permuteMsg = " + String.valueOf(permutedMsg));
        System.out.println("L = " + String.valueOf(L));
        System.out.println("R = " + String.valueOf(R));

        // 16次循环
        for (int i = 0; i < 16; i++) {
            char[] fRight = fFunc(R, subKeys[i]);

        }


    }

    // F轮询函数
    public char[] fFunc(char[] R, char[] key) {
        return null;
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
        System.out.print("二进制密文：");
        printKeys(keyBin);
        // 经过PC1置换后的结果
        char[] keyBinChars = permute(keyBin, constant.PC_1);
        // 开始切割密钥
        char[] C0 = new char[keyBinChars.length / 2];
        char[] D0 = new char[keyBinChars.length / 2];
        System.arraycopy(keyBinChars, 0, C0, 0, keyBinChars.length / 2);
        System.arraycopy(keyBinChars, keyBinChars.length / 2, D0, 0, keyBinChars.length / 2);
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
        return Long.toHexString(Long.parseLong(new String(keyChars), 2)).toUpperCase().toCharArray();
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
