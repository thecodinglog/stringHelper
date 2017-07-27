import org.hamcrest.core.IsNull;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.regex.Pattern;

/**
 * @author Jeongjin Kim
 * @since 2017-07-26
 */
public class LeaningTest {
    String testDataString = "abcde한글이han gul다ykd";

    @Test
    public void charRepeating() {

        int n = 10000;
        long startAt = System.currentTimeMillis();
        String.format("%0" + n + "d", 0).replace("0", "d");
        long endAt = System.currentTimeMillis();
        System.out.println(endAt - startAt);
        StringBuilder stringBuilder = new StringBuilder();

        long startAt1 = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            stringBuilder.append("d");
        }
        long endAt1 = System.currentTimeMillis();
        System.out.println(endAt1 - startAt1);
    }

    @Test
    public void binarySearch() {
        int[] accSize = new int[]{1, 3, 5, 6};


        Arrays.binarySearch(accSize, 3);

    }

    @Test
    public void substrByte() throws UnsupportedEncodingException {
        String testData = "하나abc";
        String str = new String(testData.getBytes("euc-kr"), 1, 5, "euc-kr");
        char[] cha = str.toCharArray();


        System.out.println(str);
    }

    @Test
    public void stringByteArray() throws UnsupportedEncodingException {
        String sample = "a好글b";
        byte[] euckr = sample.getBytes("euc-kr");
        byte[] utf8 = sample.getBytes("utf-8");

        String euckrStr = new String(euckr, "euc-kr");
        String utf8Str = new String(utf8, "utf-8");

    }

    @Test
    public void byteBinary() throws UnsupportedEncodingException {
        String sample = "a好글b";
        for (int i = 0; i < sample.length(); i++) {
            System.out.println(sample.codePointAt(i));
        }

        for (int i = 0; i < 7; i++) {

            String sam = new String(sample.getBytes("euc-kr"), 0, i, "euc-kr");
            System.out.println(sam);
        }

        String sample3 = "a好글b";
        for (int i = 0; i < 10; i++) {
            System.out.println(sample.codePointCount(0, i));
        }


    }


    @Test
    public void testOfTest() {
        Assert.assertThat("", IsNull.nullValue());

    }

    @Test
    public void charIter() {
        //String data = "`1234567890-=qwertyuiop[]\\asdfghjkl;'zxcvbnm,./~!@#$%^&*()_+QWERTYUIOP{}|ASDFGHJKL:\"?><MNBVCXZ";
        String data = "힣";

        for (int i = 0; i < data.length(); i++) {
            char d = data.charAt(i);
            System.out.println((int) data.charAt(i));
        }


    }

    @Test
    public void printAll2byteChar() {
        for (int i = 0; i <= 65535; i++) {
            System.out.print((char) i);
        }
    }
    /*@Test
    public void charAll(){
        for (int i=0;i< )
    }*/

    @Test
    public void charSetTest() throws UnsupportedEncodingException {

        String data = "a";

        byte[] defualtEncoding = data.getBytes();
        printByteAsBinaray(defualtEncoding);

        byte[] euckrEncoding = data.getBytes("euc-kr");
        printByteAsBinaray(euckrEncoding);

        byte[] utf8Encoding = data.getBytes("utf-8");
        printByteAsBinaray(utf8Encoding);

        byte[] ms949Encoding = data.getBytes("ms949");
        printByteAsBinaray(ms949Encoding);
    }

    private void printByteAsBinaray(byte[] data) {
        for (byte datum : data) {
            System.out.print(datum + " ");

        }
        System.out.println("");
        for (byte datum : data) {

            System.out.println(String.format("%8s", Integer.toBinaryString(datum & 0xFF)).replace(' ', '0'));
        }
        System.out.println("");
    }


}
