package test.bcstech.learning;

/**
 * @ClassName DebugDemo
 * @Description TODO
 * @Author zhangcq
 * @Date 2022/10/25
 **/
public class DebugDemo {

     private String name;
     private String text;

    DebugDemo( String n, String t ) {
        this.name = n;
        this.text = t;
    }

    public static void main( String args[] ) {
        DebugDemo demo = new DebugDemo( "Cq", "debug demo");
        demo.loop();
    }

    private int loop()
    {
        int sum = 0;
        for( int i = 0; i < 100; ++i )
        {
            sum += i;
            System.out.println("i="+i);
        }
        System.out.println( "sum="+sum );

        printStr();

        return sum;
    }

    private void printStr()
    {
        String str1 = "this is a debugger string";
        for ( int i = 0; i < str1.length(); ++i )
        {
            char ch = str1.charAt(i);
            System.out.println("char at "+i + " is "+ch);
        }
    }
}
