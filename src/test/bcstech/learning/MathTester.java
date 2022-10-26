package test.bcstech.learning;

import com.bcstech.learning.*;

/**
 * @ClassName UnitTest
 * @Description 用于功能的单元测试。
 * @Author zhangcq
 * @Date 2022/10/26
 **/
public class MathTester {
    public static void main(String args[]) {
//        testParser();
        testMath();
    }

    /**
     * @MethodName testMath
     * @Description 对结果进行测试。
     * @Author zhangcq
     * @Date 2022/10/26  
     */
    public static void testMath() {
        double cmpResult1 = 8-10+5*20*(5+9*17)+21+11;
        String expr1 = "8-10+5*20*(5+9*17)+21+11";

        double cmpResult2 = 1-2+((31+21*4)*3+4)*6*(5+8*2*3+1)/2;
        String expr2 = "1-2+((31+21*4)*3+4)*6*(5+8*2*3+1)/2";

        double result = new RecursiveMath(expr1).compute();
        AssertUtils.assertEqual( result, cmpResult1 );
        LogUtils.flog("计算结果", expr1, "=", result+"" );

        result = new RecursiveMath(expr2).compute();
        AssertUtils.assertEqual( result, cmpResult2 );
        LogUtils.flog("计算结果", expr2, "=", result+"" );

        result = new OperationMath(expr1).compute();
        AssertUtils.assertEqual( result, cmpResult1 );
        LogUtils.flog("计算结果", expr1, "=", result+"" );

        result = new OperationMath(expr2).compute();
        AssertUtils.assertEqual( result, cmpResult2 );
        LogUtils.flog("计算结果", expr2, "=", result+"" );
    }

    /**
     * @MethodName testParser
     * @Description 用于测试基础解析功能。
     * @Author zhangcq
     * @Date 2022/10/26  
     */
    public static void testParser()
    {
        // 测试表达式解析1
        String expr = "8 - 10 + 5 * 20 * ( 5 + 9 * 17 ) + 21 + 11";
        doReadItem( expr,expr.split(" "));

        //测试表达式解析2
        String expr2 = "18 - 10 + 5 * 20 * ( 5 + 9 * 17 ) + 21 + 1";
        doReadItem( expr2,expr2.split(" "));

        // 测试简单表达式解析
        String expr3 = "8-10+5*20*(5+9*17)+21+11";
        doSimpleExpression( expr3, expr3.indexOf('(')+1, "5+9*17", expr3.lastIndexOf(')')+1 );
    }

    public static void doSimpleExpression( String expr, int startIndex, String simpleExpr, int nexIndex ) {
        SimpleExprInfo sinfo = OperationUtils.findSimpleExr( expr, startIndex );
        AssertUtils.assertEqual(sinfo.simpleExpr, simpleExpr );
        AssertUtils.assertEqual(sinfo.nextIndex,nexIndex);
    }

    public static void doReadItem( String expr, String items[] ) {

        expr = expr.replaceAll(" ","");
        //解析完了最后一位或者遇到了=号则结束。
        int startIndex = 0;
        int count = 0;
        int exprLength = expr.length();
        while ( true ) {
            if ( startIndex > exprLength - 1 || OperationUtils.endOrNot(expr.charAt(startIndex)) ) {
                return;
            }

            ItemInfo item = OperationUtils.nextItem(expr, startIndex);
            startIndex = item.getNextIndex();
            AssertUtils.assertEqual(item.getContent(),items[count]);
            count=++count;
        }
    }
}
