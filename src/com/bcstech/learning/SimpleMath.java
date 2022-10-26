package com.bcstech.learning;

import java.util.Stack;

/**
 * @ClassName SimpleMath
 * @Description TODO
 * @Author zhangcq
 * @Date 2022/10/26
 **/
public class SimpleMath {

    protected String mathExpr;
    protected int exprLength;

    protected int startIndex;
    protected Stack<ItemInfo> itemStack;

    public SimpleMath( String expr ) {
        mathExpr = expr.replaceAll(" ", "");
        exprLength = mathExpr.length();

        startIndex = 0;
        itemStack = new Stack<ItemInfo>();

        LogUtils.log("开始计算>>>>",expr,", exprlen=",exprLength+"");
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
            LogUtils.log("pop&math:do nothing,size=", itemStack.size() + "");
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

    public double finalResult() {
        popAndMath();
        popAndMath();       //也可以在括号返回时进行检测是否直接计算，则不需要这里的计算。

        String result = itemStack.pop().getContent();
        return Double.parseDouble(result);
    }

    public double compute() {
        return 0;
    }
}
