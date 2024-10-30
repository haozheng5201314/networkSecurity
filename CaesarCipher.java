import Service.impl.UtilsImpl;

import java.util.Scanner;


public class CaesarCipher {
    private static final UtilsImpl utilsImpl = new UtilsImpl();
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
//        char[] msgChars = utilsImpl.GetMsg();
        char[] msgChars = "abcxyzABCXYZ".toCharArray();
        // 输入偏移量k
        System.out.println("请输入偏移量k");

        int k = sc.nextInt() % 26;
        utilsImpl.CharStats(msgChars);
        msgChars = utilsImpl.CaesarCipherEncry(msgChars, k);
        utilsImpl.CharStats(msgChars);
        utilsImpl.CaesarCipherBFA(msgChars.clone());
        utilsImpl.CaesarCipherDecry(msgChars, k);
        /*
        // 操作选择
        System.out.println("请输入你的操作（序号）");
        System.out.println("1. 加密");
        System.out.println("2. 解密");
        System.out.println("3. 退出");
        int wayChoice = sc.nextInt();
        while (wayChoice < 1 || wayChoice >= 3) {
            System.out.println("请输入的操作！");
            wayChoice = sc.nextInt();
        }
        // 开始加密或解密
        switch (wayChoice) {
            case 1:
                utilsImpl.CaesarCipherEncry(msgChars, k);

                break;
            case 2:
                utilsImpl.CaesarCipherDecry(msgChars, k);
                break;
        }
        */
    }
}
