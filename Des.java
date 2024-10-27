import Service.impl.UtilsImpl;

public class Des {

    public static UtilsImpl utilsImpl = new UtilsImpl();
    public static void main(String[] args) {
        //获取明文
        // char[] msgChars = utilsImpl.GetMsg();
        char[] msgChars = "FEDCBA9876543210".toCharArray();
        //String key = "133457799BBCDFF1";
        String key = "0123456789ABCDEF";
        utilsImpl.DesEncry(msgChars,key);
    }
}
