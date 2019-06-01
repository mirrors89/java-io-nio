package nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

public class ChannelExample {
    public static void main(String[] args) {

        try {
            write();
            read();

        } catch( IOException e ) {
            System.out.println(e);
        }

    }

    //NIO 함수를 이용해 파일 쓰기
    private static void write() throws IOException {
        FileOutputStream fo = new FileOutputStream("src/main/resources/test.txt");
        GatheringByteChannel gchannel = fo.getChannel();

        ByteBuffer wheader = ByteBuffer.allocate(30);
        ByteBuffer wbody = ByteBuffer.allocate(30);
        ByteBuffer[] wbuffers = { wheader, wbody };

        wheader.put("header is hello world".getBytes());
        wbody.put("body is welcome to channel".getBytes());

        wheader.flip();
        wbody.flip();

        gchannel.write(wbuffers);
        gchannel.close();
    }

    //NIO 함수를 이용해 파일 읽기
    private static void read() throws IOException {
        FileInputStream fin = new FileInputStream("src/main/resources/test.txt");

        ScatteringByteChannel schannel = fin.getChannel();

        ByteBuffer rheader = ByteBuffer.allocateDirect(10);
        ByteBuffer rbody = ByteBuffer.allocateDirect(10);
        ByteBuffer[] rbuffers = { rheader, rbody };

        int readCount = (int) schannel.read(rbuffers);

        System.out.println("cnt : " + readCount );

        schannel.close();
        rheader.flip();
        rbody.flip();

        byte[] byteHeaderBuffer = new byte[10];
        byte[] byteBodyBuffer = new byte[10];

        rheader.get(byteHeaderBuffer);
        rbody.get(byteBodyBuffer);


        System.out.println("header : " + new String(byteHeaderBuffer));
        System.out.println("body : " + new String(byteBodyBuffer));
    }

}
