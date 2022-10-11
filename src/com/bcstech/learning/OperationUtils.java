package com.bcstech.learning;

import java.util.Stack;

/**
 * @ClassName OperationUtils
 * @Description 工具类
 * @Author zhangcq
 * @Date 2022/10/11
 */
public class OperationUtils {

    public static double math( String num1,  String num2, String oper ) {
        double d1 = Double.parseDouble(num1);
        double d2 = Double.parseDouble(num2);
        LogUtils.log("math", num1, oper, num2 );
        switch ( oper.charAt(0) ) {
            case '+':
                return d1 + d2;
            case '-':
                return d1 - d2;
            case '*':
                return d1 * d2;
            case '/':
                return d1 / d2;
            default:
                LogUtils.logerr("运算出错:");
                throw new RuntimeException("运算符错误");
        }
    }

    public static boolean equalsAnyOf( String str, String ...anyStrs ) {
        if ( anyStrs == null || str == null ) {
            return false;
        }

        for ( String someStr :  anyStrs ) {
            if ( str.equals(someStr) ) {
                return  true;
            }
        }

        return false;
    }

    public static void printStack( Stack<ItemInfo> itemStack )
    {
        StringBuilder stringBuilder = new StringBuilder();
        for ( int i = itemStack.size() - 1; i >= 0; --i )
        {
            ItemInfo operItem = itemStack.elementAt(i);
            stringBuilder.append(itemStack.elementAt(i)).append(",");
        }
        LogUtils.log("STACK",stringBuilder.toString());
    }

    public static boolean  endOrNot( char ch ) {
        return ch == '=' || ch =='\n' ? true : false;
    }

    public static boolean numberOrNot( char expChar ) {
        return ( expChar <= '9' && expChar >= '0' ) || expChar == '.' ? true : false;
    }

    public static boolean blankCharOrNot( char ch ) {
        return  ch ==' ' || ch == '\t' ? true : false;
    }
}

