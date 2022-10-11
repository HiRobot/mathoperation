package com.bcstech.learning;

import java.util.Stack;

/**
 * @ClassName OperationParser：这个是常规实现，还有一种是逆波兰式，下次再写。
 * @Description 用于解析计算数学表达式:1-2+(3+2)*3+4*6*(5+8*2*3+1)/2=?
 * @Author zhangcq
 * @Date 2022/10/11
 **/
public class OperationParser {
    private String mathExpr;
    private int exprLength;

    private int nextIndex;
    private Stack<ItemInfo> itemStack;

    private double cmpResult = 1-2+((31+21*4)*3+4)*6*(5+8*2*3+1)/2;

    public static void main( String args[] ) {
        String expr = "1-2+((31+21*4)*3+4)*6*(5+8*2*3+1)/2=";
        new OperationParser( expr ).parse();
    }
    
    public OperationParser( String expr ) {
        mathExpr = expr.replaceAll(" ","");
        exprLength = mathExpr.length();

        nextIndex = 0;
        itemStack = new Stack<ItemInfo>();
    }
    

    /**
     * @MethodName parse
     * @Description TODO       
     * @Author zhangcq
     * @Date 2022/10/11  
     */
    public void parse() {
        parse( mathExpr.toCharArray(), nextIndex );
    }

    /**
     * @MethodName parse
     * @Description TODO       
     * @Author zhangcq
     * @Date 2022/10/11 
     * @param exprChars
     * @param startIndex
     */
    public void parse( char[] exprChars, int startIndex ) {

        while ( true ) {
            if ( OperationUtils.endOrNot(exprChars[startIndex])) {
                if ( itemStack.size() >=3 ) {
                    popAndMath();
//                    LogUtils.log("itemStackSize", itemStack.size()+"");
                    LogUtils.log("计算结果1", mathExpr+itemStack.pop().getContent());
                    LogUtils.log("计算结果2", cmpResult+"");
                }

                return;
            }

            // 解析符号
            ItemInfo item = nextItem(exprChars, startIndex);

            // 默认下一个位置
            startIndex = item.getNextIndex();

            // 数字或者括号（不是运算符）直接入栈
            String oper = item.getContent();
            if (item.getType() == ItemType.NUMBER
                                || "(".equals(oper) ) {
                itemStack.push(item);
                continue;
            }

            // 如果是运算符则如果：当前运算符优先级较高则尝试计算本次
            if ( OperationUtils.equalsAnyOf(oper, "*", "/")) {

                // 获取下一个计算数
                ItemInfo itemNext = nextItem(exprChars, item.getNextIndex());

                // 如果下一个是（则优先计算括号内的表达式，将本次入栈。
                if ( OperationUtils.equalsAnyOf(itemNext.getContent(), "(")) {
                    itemStack.push(item);
                    continue;
                }

                // 数字 右括号不会入库。
                ItemInfo maybeNumber = itemStack.pop();
                if (maybeNumber.getType() != ItemType.NUMBER
                        || itemNext.getType() != ItemType.NUMBER) {
                    LogUtils.logerr("表达式不正确位置：" + maybeNumber.getContent(), oper, itemNext.getContent());
                    return;
                }

                // 计算
                double result = OperationUtils.math(maybeNumber.getContent(), itemNext.getContent(), oper);

                startIndex = itemNext.getNextIndex();
                itemStack.push(new ItemInfo(result + "", ItemType.NUMBER));

                continue;
            }

            // 当前优先级较低则尝试计算上一个运算符
            if ( OperationUtils.equalsAnyOf(oper, "+", "-")) {

                ItemInfo subItem = findTopOperItem(itemStack);
                if (subItem == null ) {

                    itemStack.push(item);
                    continue;
                }

                // 如果上一个是（则优先计算括号内的表达式，将本次入栈。
                if ( OperationUtils.equalsAnyOf(subItem.getContent(), "", "(")) {
                    itemStack.push(item);
                    continue;
                }

                // 否则进行计算。
                if ( OperationUtils.equalsAnyOf(subItem.getContent(), "+", "-")) {
                    popAndMath();
                    // 将本次符号入栈
                    itemStack.push(item);
                    continue;
                } else {
                    LogUtils.logerr("符号错误？");
                }
            }

            // 右括号则要将剩余的计算完，直到遇到左括号。
            if ( OperationUtils.equalsAnyOf( oper, ")" ) )
            {
                popAndMath();

                // 获取括号内的值。
                ItemInfo sumItem = itemStack.pop();

                //获取括号
                ItemInfo braketItem = itemStack.pop();
                if ( !braketItem.getContent().equals("(") ) {
                    OperationUtils.printStack( itemStack );
                    LogUtils.logerr("括号计算错误。"+braketItem.getContent());
                    return;
                }

                itemStack.push(sumItem);

                //检查上一个是否是*/则直接进行计算。
                ItemInfo subItem = findTopOperItem(itemStack);
                if ( subItem == null ) {
                    continue;
                }
                else if (  OperationUtils.equalsAnyOf(subItem.getContent(),"*","/")) {
                    popAndMath();
                    continue;
                }
            }
        }
    }
    
    /**
     * @MethodName popAndMath
     * @Description 弹出栈并进行运算，将结果压入栈。
     * @Author zhangcq
     * @Date 2022/10/11  
     * @return double
     */
    public double popAndMath()
    {
        ItemInfo topInfo2 = itemStack.pop();
        ItemInfo topOper = itemStack.pop();
        ItemInfo topInfo1 = itemStack.pop();

        LogUtils.log("POP&Math", topInfo1.getContent(),  topOper.getContent(), topInfo2.getContent() );
        double result =  OperationUtils.math(topInfo1.getContent(), topInfo2.getContent(), topOper.getContent());

        // 将上次结果入栈
        itemStack.push(new ItemInfo(result + "", ItemType.NUMBER));

        return result;
    }

    /**
     * @MethodName findTopOperItem
     * @Description 搜索最近的一个操作符
     * @Author zhangcq
     * @Date 2022/10/11
     * @param itemStack
     * @return com.bcstech.learning.ItemInfo
     */
    public static ItemInfo findTopOperItem( Stack<ItemInfo> itemStack )
    {
        for ( int i = itemStack.size() - 1; i >= 0; --i )
        {
            ItemInfo operItem = itemStack.elementAt(i);
            if ( operItem.getType() == ItemType.OPERATION ) {
                return operItem;
            }
        }

        return null;
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
}
