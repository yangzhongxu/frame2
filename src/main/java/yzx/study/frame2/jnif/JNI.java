package yzx.study.frame2.jnif;

public class JNI {
    static {
        System.loadLibrary("yzx");
    }

    public static native String test();

}
