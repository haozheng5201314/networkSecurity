package Service;

public interface Utils {
    char[] GetMsg();
    char[] CaesarCipherEncry(char[] msgChars,int k);
    char[] CaesarCipherDecry(char[] msgChars, int k);
    void CharStats(char[] msgChars);


    char[] DesEncry(char[] msgChars,String key);
    void DesDecry(char[] msgChars,String key);
    char[] msgHexToBin(char[] msgChars);
    char[] keyBinToHex(char[] msgChars);
    char[][] generateSubKeys(char[] keyBin);
    public void RotateLeftShift(char[] source, int bits);
}
