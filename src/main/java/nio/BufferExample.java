package nio;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.GatheringByteChannel;
import java.nio.channels.ScatteringByteChannel;

public class BufferExample {
    public static void main(String[] args) {
        String aFile = "src/main/resources/test.txt";
        String bFile = "src/main/resources/test2.txt";

        // ByteBuffer크기를 20으로 지정함. -> 최대 20Byte저장가능.
        ByteBuffer buffer = ByteBuffer.allocate(20); // 읽기, 쓰기 용도의 버퍼

        try {
            // 파일에 버퍼의 데이터를 read할때,
            ScatteringByteChannel inChannel = (new FileInputStream(aFile)).getChannel();

            // 파일에 버퍼의 데이터를 write할때
            GatheringByteChannel outChannel = (new FileOutputStream(bFile)).getChannel();

            // A.txt 파일의 내용을 읽어와서 Buffer에 저장 이때 position은 읽어들인 바이트 수 만큼 위치함.
            int readCnt = inChannel.read(buffer);
            System.out.println("read Count : " + readCnt);
            inChannel.close();

            // 읽어온 test.txt의 내용을 콘솔로 출력해보기. byte배열을 사용하여 저장한뒤 출력함.
            // position 위치를 0으로 수정함.
            buffer.flip();
            byte[] rByte = new byte[20];
            buffer.get(rByte); // 이때 position값은 rByte에 저장한 만큼 이동하게 된다.

            System.out.println("test.txt : " + new String(rByte)); // A.txt파일 내용을 콘솔에 출력

            // 다시 버퍼의 position값을 0으로 변경.(test2.txt파일에 write해야하므로)
            buffer.flip();

            // test2.txt에 읽어온 바이트 버퍼를 write함. 이때 버퍼의 position은 write한 만큼 이동함.
            outChannel.write(buffer); //
            outChannel.close();
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

}
