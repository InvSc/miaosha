package com.invsc.miaosha_1.JVM.GC;

public class HelloGC {
    public static void main(String[] args) throws InterruptedException {
//        long totalMemory= Runtime.getRuntime().totalMemory();//返回Java报机中的内存总量。
//        long maxMemory= Runtime.getRuntime().maxMemory();//返回Java虚拟机试图使用的最大内存量 约为总内存1/4
//        System.out.println("TOTAL_MEMORY(-Xms) = "+ totalMemory +"(字节)、" + (totalMemory / (double)1024/1024)+"MB");
//        System.out.println("MAX_MEMORY(-Xmx)=" + maxMemory+"(字节)、" + (maxMemory/(double)1024/1024)+"MB");
//        byte[] arr = new byte[50 * 1024 * 1024];
        System.out.println("*****HelloGC*****");
        Thread.sleep(Integer.MAX_VALUE);
    }
    /**
     * 程序启动后 VM Options:-XX:+PrintGCDetails
     * 1 jps -l
     *      找到 3580 com.invsc.miaosha_1.JVM.GC.HelloGC
     * 2 jinfo -flag PrintGCDetails 3580
     *      显示 -XX:+PrintGCDetails
     *      打个比方查出来xss初始线程栈空间为0代表为初始值
     */
    /**
     * java -XX:+PrintFlagsInitial -version(无需启动程序)
     *      查看默认参数
     * java -XX:+PrintFlagsFinal -version
     *      查看当前参数
     * java -XX:+PrintCommandLineFlags -version
     *      = 参数无冒号 初始值
     *      := 有冒号 被jvm加载时修改 或者人为修改
     */
}
