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

        // 打印字符次数
        /*
        for (int i = 0; i < LettersFre.length / 2; i++) {
            if (LettersFre[i] != 0) {
                System.out.println((char) ('a' + i) + "的次数为：" + LettersFre[i]);
            }
        }
        for (int i = 0; i < LettersFre.length / 2; i++) {
            if (LettersFre[i + 26] != 0) {
                System.out.println((char) ('A' + i) + "的次数为：" + LettersFre[i + 26]);
            }
        }
        */

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
        char[] msgBin = HexToBin(msgChars);
        // 将密钥转成二进制
        printKeys(msgBin);
        String keyBin = keyHexToBin(key.toCharArray());


        // 开始生成16轮的子密钥
        char[][] subKeys = generateSubKeys(keyBin);

    }

    public char[] permute(char[] source, int[] permuteTable) {
        StringBuilder permutedKeySb = new StringBuilder(permuteTable.length);
        for (int i : permuteTable) {
            permutedKeySb.append(source[i - 1]);
        }
        return permutedKeySb.toString().toCharArray();

    }

    public char[][] generateSubKeys(String keyBin) {
        // 开始第一轮置换
        char[][] subKeys = new char[keyBin.length()][16];

        printKeys(keyBin.toCharArray());
        char[] keyBinChars = permute(keyBin.toCharArray(), constant.PC_1);
        printKeys(keyBinChars);

        /*
        for (int i = 0; i < 56; i++) {
            permutedKeySb.append(keyBinChars[constant.PC_1[i]-1]);
        }*/

        // 开始切割密钥
        char[] C0 = new char[keyBinChars.length/2];
        char[] D0 = new char[keyBinChars.length/2];
        System.arraycopy(keyBinChars, 0, C0, 0, 32);
        System.arraycopy(keyBinChars, 32, D0, 0, 32);
        printKeys(C0);
        printKeys(D0);
        System.out.println();
        // 生成16组子密钥
        for (int i = 0; i < 16; i++) {
            System.out.println("第"+(i+1)+"次左移，幅度为"+constant.SHIFT_SCHEDULE[i]);
            RotateLeftShift(C0, constant.SHIFT_SCHEDULE[i]);
            RotateLeftShift(D0, constant.SHIFT_SCHEDULE[i]);

            char[] C0D0Chars = new char[C0.length+D0.length];
            System.arraycopy(C0,0,C0D0Chars,0,C0.length);
            System.arraycopy(D0,0,C0D0Chars,C0.length,D0.length);
            System.out.println("C0D0");
            System.out.println(String.valueOf(C0D0Chars));
            subKeys[i] = permute(C0D0Chars,constant.PC_2);
            System.out.println(subKeys[i]);

        }
        return subKeys;
    }

    @Override
    public void RotateLeftShift(char[] source, int shift) {
        int sourceLen = source.length;
        char[] temp = new char[shift];
        System.arraycopy(source, 0, temp, 0, shift);
        System.out.println("---");
        System.out.println(String.valueOf(source));
        System.arraycopy(source, shift, source, 0, sourceLen - shift);
        System.arraycopy(temp, 0, source, sourceLen - shift, shift);
        System.out.println(String.valueOf(source));
        System.out.println();

        //return (new String(source, bits, source.length - bits) + new String(source, 0, bits)).toCharArray();


    }

    @Override
    public void DesDecry(char[] msgChars, String key) {

    }

    @Override
    public char[] HexToBin(char[] msgChars) {
        StringBuilder tempBinSb = new StringBuilder();
        for (char msgch : msgChars) {
            tempBinSb.append(String.format("%4s", Integer.toBinaryString(msgch)).replace(' ', '0'));
        }
        // 消息的二进制数组
        return tempBinSb.toString().toCharArray();
    }

    public String keyHexToBin(char[] keyChars) {
        StringBuilder tempBinSb = new StringBuilder();
        for (char key : keyChars) {
            tempBinSb.append(String.format("%4s", Integer.toBinaryString(Integer.parseInt(String.valueOf(key), 16))).replace(' ', '0'));
        }
        return tempBinSb.toString();
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
