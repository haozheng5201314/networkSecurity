package Service.impl;

import Service.Utils;

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

    public void CaesarCipherBFA(char[] msgCharsBFA) {
        for (int i = 0; i < 26; i++) {
            traverseMsgsChars(msgCharsBFA, 1);
            System.out.println("暴力破解" + (i + 1) + "，破解结果为：" + String.valueOf(msgCharsBFA));
        }
    }


}
