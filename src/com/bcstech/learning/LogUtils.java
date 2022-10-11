package com.bcstech.learning;

import java.io.PrintStream;

public class LogUtils {

    public static void log( String msg, String ...args ) {
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
}