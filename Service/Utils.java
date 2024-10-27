package Service;

public interface Utils {
    char[] GetMsg();
    void CaesarCipherEncry(char[] msgChars,int k);
    void CaesarCipherDecry(char[] msgChars, int k);
    void CharStats(char[] msgChars);


    void DesEncry(char[] msgChars,String key);
    void DesDecry(char[] msgChars,String key);
    char[] msgHexToBin(char[] msgChars);
    char[] keyBinToHex(char[] msgChars);
    char[][] generateSubKeys(char[] keyBin);
    public void RotateLeftShift(char[] source, int bits);
}
