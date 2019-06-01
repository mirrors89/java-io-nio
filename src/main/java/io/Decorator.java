package io;


import java.io.*;

public class Decorator {

    public static void main(String[] args) {

        try {
            // FileInputStream을 만들고 BufferedInputStream과 새로 만든 LowercaseInputStream으로 감싼다.
            InputStream in = new LowercaseInputStream(new BufferedInputStream(new FileInputStream("test.txt")));

            int c = in.read();
            while(c >= 0) {
                System.out.print((char)c);

                c = in.read();
            }

            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

class LowercaseInputStream extends FilterInputStream {

    protected LowercaseInputStream(InputStream in) {
        super(in);
    }

    @Override
    public int read() throws IOException {
        int c = super.read();
        return (c == -1 ? c : Character.toLowerCase((char)c));
    }

    @Override
    public int read(byte[] b, int off, int len) throws IOException {
        int result = super.read(b, off, len);
        for (int i = off; i < off + result; i++) {
            b[i] = (byte) Character.toLowerCase((char)b[i]);
        }
        return result;
    }

}

