package com.bcstech.learning;

import java.util.Stack;

/**
 * @ClassName RecursiveMath
 * @Description 使用递归进行表达式计算
 * @Author zhangcq
 * @Date 2022/10/24
 **/
public class RecursiveMath {
    private String mathExpr;
    private int exprLength;

    private int startIndex;
    private Stack<ItemInfo> itemStack;

    private double cmpResult = 8-10+5*20*(20*(5+9*17))+2;
//    private double cmpResult = 5 + 9 * 17;
//    private double cmpResult = 10-8+5*20*(5+9*17)+2;

    public static void main(String args[]) {
        String expr = "8-10+5*20*(20*(5+9*17))+2=";
//        String expr = "8-10+5*20*(5+9*17)+2=";
//        String expr = "5 + 9 * 17";
        new RecursiveMath(expr).call();
    }

    public RecursiveMath( String expr ) {
        mathExpr = expr.replaceAll(" ", "");
        exprLength = mathExpr.length();

        startIndex = 0;
        itemStack = new Stack<ItemInfo>();

        LogUtils.log("开始计算>>>>",expr);
    }

    /**
     * @MethodName parse
     * @Description TODO
     * @Author zhangcq
     * @Date 2022/10/11
     */
    public double call() {
        while (true) {
            if ( startIndex >= exprLength - 1
                    || OperationUtils.endOrNot(mathExpr.charAt(startIndex)) ) {
                popAndMath();
                popAndMath();       //也可以在括号返回时进行检测是否直接计算，则不需要这里的计算。

                String result = itemStack.pop().getContent();
                LogUtils.log("计算结果1", mathExpr ,"=", result);
                LogUtils.log("计算结果2", cmpResult + "");
                return Double.parseDouble(result);
            }

            // 解析运算单元
            ItemInfo item = OperationUtils.nextItem( mathExpr, startIndex );

            // 默认下一个位置
            startIndex = item.getNextIndex();

            // 数字直接入栈
            String oper = item.getContent();
            if (item.getType() == ItemType.NUMBER) {
                push2Stack(item);
                continue;
            }

            // 如果括号则进行递归。
            if (OperationUtils.equalsAnyOf( item.getContent(), "(" ) ) {
                doSimpleExprMath();
                continue;
            }

            // 如果是运算符则如果：当前运算符优先级较高则尝试计算本次
            if (OperationUtils.equalsAnyOf(oper, "*", "/")) {

                //入栈当前符号
                push2Stack(new ItemInfo(oper, ItemType.OPERATION));

                // 获取下一个计算数
                ItemInfo itemNext = OperationUtils.nextItem(mathExpr, item.getNextIndex());
                startIndex = itemNext.getNextIndex();

                // 如果下一个是（则优先计算括号内的表达式，将本次入栈。
                if (OperationUtils.equalsAnyOf(itemNext.getContent(), "(")) {
                    doSimpleExprMath();
                    continue;
                }

                // 入栈后立马弹出进行统一的计算。
                push2Stack(itemNext);
                popAndMath();

                continue;
            } else if (OperationUtils.equalsAnyOf(oper, "+", "-")) {

                // 进行上一次的存储计算。
                popAndMath();

                // 将本次符号入栈
                push2Stack(item);
            }
        }
    }

    /**
     * @MethodName doSimpleExprMath
     * @Description 进行简单表达式计算，并将结果入栈。
     * @Author zhangcq
     * @Date 2022/10/25
     */
    public void doSimpleExprMath()
    {
        SimpleExprInfo se = OperationUtils.findSimpleExr(mathExpr, startIndex);
        double result = new RecursiveMath(se.simpleExpr).call();

        itemStack.push(new ItemInfo("" + result, ItemType.NUMBER));
        startIndex = se.startIndex;
    }

    /**
     * @return double
     * @MethodName popAndMath
     * @Description 弹出栈并进行运算，将结果压入栈。
     * @Author zhangcq
     * @Date 2022/10/11
     */
    public double popAndMath() {
        if (itemStack.size()  < 3 ) {
            LogUtils.log("popAndMath:do nothing,size=", itemStack.size() + "");
            return -1;
        }

        ItemInfo topInfo2 = itemStack.pop();
        ItemInfo topOper = itemStack.pop();
        ItemInfo topInfo1 = itemStack.pop();

        LogUtils.log("pop&math", topInfo1.getContent(), " " + topOper.getContent(), " " + topInfo2.getContent());
        double result = OperationUtils.math(topInfo1.getContent(), topInfo2.getContent(), topOper.getContent());

        // 将上次结果入栈
        itemStack.push( new ItemInfo(result + "", ItemType.NUMBER ) );

        return result;
    }

    /**
     * @MethodName push2Stack
     * @Description 入栈
     * @Author zhangcq
     * @Date 2022/10/25
     * @param item
     */
    public void push2Stack( ItemInfo item ) {
        LogUtils.log("push2Stack", item.toString() );
        itemStack.push(item);
    }

    public void push2Stack( double number, ItemType itemType ) {
        push2Stack(new ItemInfo(number + "", itemType));
    }

    public void push2Stack( String itemContent, ItemType itemType ) {
        push2Stack(new ItemInfo(itemContent, itemType));
    }
}
