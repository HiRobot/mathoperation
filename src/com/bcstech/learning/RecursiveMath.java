package com.bcstech.learning;

import java.util.Stack;

/**
 * @ClassName RecursiveMath
 * @Description 使用递归进行表达式计算
 * @Author zhangcq
 * @Date 2022/10/24
 **/
public class RecursiveMath extends  SimpleMath {

    public RecursiveMath( String expr ) {
        super( expr );
    }

    /**
     * @MethodName call
     * @Description TODO
     * @Author zhangcq
     * @Date 2022/10/11
     */
    @Override
    public double compute() {
        while (true) {
            //解析完了最后一位或者遇到了=号则结束。
            if ( startIndex > exprLength - 1 || OperationUtils.endOrNot(mathExpr.charAt(startIndex)) ) {
                return finalResult();
            }

            // 解析单词
            ItemInfo item = OperationUtils.nextItem( mathExpr, startIndex );

            // 记录下一个开始解析的位置。
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
        double result = new RecursiveMath(se.simpleExpr).compute();

        itemStack.push(new ItemInfo("" + result, ItemType.NUMBER));
        startIndex = se.nextIndex;
    }
}
