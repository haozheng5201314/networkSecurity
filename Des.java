import Service.impl.UtilsImpl;

public class Des {

    public static UtilsImpl utilsImpl = new UtilsImpl();

    public static void main(String[] args) {
        //获取明文
        // char[] msgChars = utilsImpl.GetMsg();
        char[] msgChars = "0123456789ABCDEF0123456789ABCDEF0123456789ABCDE1".toCharArray();
        String key = "133457799BBCDFF1";

        char[][] msgs = utilsImpl.MsgsSplit(msgChars);
        char[][] Cmsgs = new char[msgs.length][];
        for (int i = 0; i < msgs.length; i++) {
            Cmsgs[i] = utilsImpl.DesEncry(msgs[i], key);
        }
        for (char[] cmsgs :
                Cmsgs) {
            utilsImpl.printKeys(cmsgs);
        }

        //char[] msgs = utilsImpl.DesEncry(msgChars,key);

    }
}
