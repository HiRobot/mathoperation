package test.bcstech.learning;

/**
 * @ClassName AssertUtils
 * @Description TODO
 * @Author zhangcq
 * @Date 2022/10/26
 **/
public class AssertUtils {
    public static boolean assertEqual( String s1, String s2 ) {
        if( s1 == null ) {
            throw new RuntimeException("参数异常。");
        }

        if ( !s1.equals(s2) ) {
            throw new RuntimeException("不相等："+s1+"!="+s2);
        }

        return true;
    }

    public static boolean assertEqual( int i, int j ) {
        if( i == j ) {
            return true;
        }

        throw new RuntimeException("不相等："+i+"!="+j);
    }

    public static boolean assertEqual( double i, double j ) {
        if( Math.abs(i - j) < 0.0001 ) {
            return true;
        }

        throw new RuntimeException("不相等："+i+"!="+j);
    }
}
