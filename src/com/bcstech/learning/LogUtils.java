package com.bcstech.learning;

import java.io.PrintStream;

/**
 * @ClassName LogUtils
 * @Description 日志工具类
 * @Author zhangcq
 * @Date 2022/10/11
 */
public class LogUtils {

    private static boolean debug;

    public static void flog( String msg, String ...args ) {
        print( msg, System.out, args );
    }

    public static void log( String msg, String ...args ) {
        if ( !debug ) {
            return;
        }

        print( msg, System.out, args );
    }

    public static void logerr( String msg, String ...args ) {
        print( msg, System.err, args );
    }

    private static void print(String msg, PrintStream stream, String ...args ) {
        StringBuilder sbuilder = new StringBuilder();
        if( args == null ) {
            return;
        }

        sbuilder.append(msg+":");
        for ( String str1 : args )
        {
            sbuilder.append(str1);
        }

        stream.println("["+sbuilder.toString()+"]");
    }

    public static void main( String args[] ) {
        int i = 0;
        i = i++;
        log("i",i+"");
    }
}
