package com.bcstech.learning;

/**
 * @ClassName SimpleExprInfo
 * @Description TODO
 * @Author zhangcq
 * @Date 2022/10/24
 **/
public class SimpleExprInfo {
    public String srcExpr;          // 原表达式
    public String simpleExpr;       // 简单表达式
    public int nextIndex;          // 原表达式开始位置（不包含括号).

    @Override
    public String toString() {
        return "simpleExpr="+simpleExpr+",startIndex="+nextIndex;
    }
}
