package nio;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class IO_NIO_비교 {
    public static void main(String[] args) throws Exception {
        useNormalIO();
        useFileChannel();
    }

    private static void useNormalIO() throws Exception {
        File file = new File("src/main/resources/Movie.mp4");
        File oFile = new File("src/main/resources/Movie2.mp4");

        long time1 = System.currentTimeMillis();
        try (InputStream is = new FileInputStream(file);
            FileOutputStream fos = new FileOutputStream(oFile)) {

            byte[] buf = new byte[64 * 1024];
            int len;
            while ((len = is.read(buf)) != -1) {
                fos.write(buf, 0, len);
            }

        }
        long time2 = System.currentTimeMillis();
        System.out.println("Time taken: "+(time2-time1)+" ms");
    }

    private static void useFileChannel() throws Exception {
        File file = new File("src/main/resources/Movie.mp4");
        File oFile = new File("src/main/resources/Movie2.mp4");

        long time1 = System.currentTimeMillis();
        FileInputStream is = new FileInputStream(file);
        FileOutputStream fos = new FileOutputStream(oFile);

        try (FileChannel f = is.getChannel();
             FileChannel f2 = fos.getChannel()) {

            ByteBuffer buf = ByteBuffer.allocateDirect(64 * 1024);

            while (f.read(buf) != -1) {
                buf.flip();
                f2.write(buf);
                buf.clear();
            }
        }

        long time2 = System.currentTimeMillis();
        System.out.println("Time taken: "+(time2-time1)+" ms");
    }
}