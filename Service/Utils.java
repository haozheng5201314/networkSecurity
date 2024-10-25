package Service;

public interface Utils {
    char[] GetMsg();
    void CaesarCipherEncry(char[] msgChars,int k);
    void CaesarCipherDecry(char[] msgChars, int k);
    void CharStats(char[] msgChars);
}
