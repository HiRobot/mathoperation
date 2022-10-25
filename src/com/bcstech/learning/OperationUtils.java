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

    /**
     * @MethodName nextItem
     * @Description 解析下一个符号
     * @Author zhangcq
     * @Date 2022/10/11
     * @param exprChars
     * @param startIndex
     * @return com.bcstech.learning.ItemInfo
     */
    public static ItemInfo nextItem( char[] exprChars, int startIndex ) {

        ItemInfo item = new ItemInfo();

        //操作符
        if( ! OperationUtils.numberOrNot(exprChars[startIndex]) ) {
            item.setType( ItemType.OPERATION );
            item.setContent( exprChars[startIndex]+"");
            item.setNextIndex(++startIndex);

            LogUtils.log("item:"+item.getContent() );
            return item;
        }

        //数字
        item.setType( ItemType.NUMBER );
        StringBuilder sbuilder = new StringBuilder();
        for ( int i = startIndex; i < exprChars.length; ++i ) {
            if( ! OperationUtils.numberOrNot(exprChars[i]) ) {
                item.setNextIndex(i);
                break;
            }

            sbuilder.append(exprChars[i]);
        }
        item.setContent( sbuilder.toString() );

        LogUtils.log("item:"+item.getContent() );
        return item;
    }

    public static ItemInfo nextItem( String expr, int startIndex ) {

        ItemInfo item = new ItemInfo();

        //操作符
        char ch = expr.charAt(startIndex);
        if( !OperationUtils.numberOrNot(ch) ) {
            item.setType( ItemType.OPERATION );
            item.setContent( ch+"");
            item.setNextIndex(++startIndex);

            LogUtils.log("item:"+item.getContent() );
            return item;
        }

        //数字
        int myStartIndex = startIndex;
        item.setType( ItemType.NUMBER );
        StringBuilder sbuilder = new StringBuilder();
        for ( int i = startIndex; i < expr.length(); ++i ) {

            myStartIndex = i;

            char chTemp = expr.charAt(i);
            if( ! OperationUtils.numberOrNot(chTemp) ) {
                break;
            }

            sbuilder.append(chTemp);
        }
        item.setNextIndex(myStartIndex);
        item.setContent( sbuilder.toString() );

        LogUtils.log("item:"+item.getContent() );
        return item;
    }

    public static SimpleExprInfo findSimpleExr( String expr, int startIndex )
    {
        StringBuilder maySimple = new StringBuilder();
        SimpleExprInfo seInfo = new SimpleExprInfo();
        int endIndex = expr.length();
        int bracketCnt = 1;     //0 表示找到了对应的)括号。

        for ( int i = startIndex; i < endIndex; ++i )
        {
            char ch = expr.charAt(i);
            if ( '('  == ch ) {
                bracketCnt = ++bracketCnt;
            }
            else if ( ')' == ch ) {
                bracketCnt = --bracketCnt;
            }

            if ( bracketCnt == 0 ) {
                seInfo.startIndex = i + 1;
                seInfo.simpleExpr = maySimple.toString();
                return seInfo;
            }

            maySimple.append(ch);
        }

        throw  new RuntimeException("括号不匹配,expr="+expr+" startIndex is "+startIndex);
    }

    public static void main( String args[] ) {
//        String expr = "1*(1+(2+3))+1";
        String expr = "10+5*20*(5+9*17)+2=";
        SimpleExprInfo se = findSimpleExr(expr,9);
        LogUtils.log(se.toString()," charAt is "+expr.charAt(se.startIndex));
    }
}

