import org.omg.CORBA.PUBLIC_MEMBER;
import thecodinglog.string.StringHelper;
import org.junit.Test;
import org.hamcrest.core.IsNull;
import org.junit.Assert;
import org.junit.Test;

import java.io.UnsupportedEncodingException;

import static org.hamcrest.CoreMatchers.is;
import static thecodinglog.string.StringHelper.getByteLengthOfChar;

/**
 * @author Jeongjin Kim
 * @since 2017-07-25
 */
public class GetStringIndexTest {
    String data = "你好 中国 hey";
    String data2 = "a한국b";
    String data3 = "ab한국";
    String data4 = "한ab국";
    public static int getStartIndex(String str, int position) throws IndexOutOfBoundsException {
        int accByte = 0;
        int startIndex = -1;
        boolean beginHalf = false;
        if (position >= 0) {
            for (int i = 0; i < str.length(); i++) {
                if (position - 1 == accByte) {
                    startIndex = i;
                    accByte = accByte + getByteLengthOfChar(str.charAt(i));
                    break;
                } else if (position == accByte) {
                    beginHalf = true;
                    startIndex = i;
                    accByte = accByte + getByteLengthOfChar(str.charAt(i));
                    break;
                } else if (accByte + 2 == position && i == str.length() - 1) {
                    beginHalf = true;
                    accByte = accByte + getByteLengthOfChar(str.charAt(i));
                    break;
                }
                accByte = accByte + getByteLengthOfChar(str.charAt(i));
            }
        } else {
            position = position * -1;

            for (int i = str.length() - 1; i >= 0; i--) {

                accByte = accByte + getByteLengthOfChar(str.charAt(i));

                if (position == accByte) {
                    startIndex = i;
                    break;
                } else if (position == accByte - 1) {
                    beginHalf = true;
                    if (i != str.length() - 1) {
                        startIndex = i + 1;
                    }
                    break;
                }
            }
        }

        if (accByte < position) {
            throw new IndexOutOfBoundsException();
        }

        return startIndex;
    }
    @Test
    public void forwardSearchSpacing(){
        Assert.assertThat(getStartIndex(data2, 0), is(0));
        Assert.assertThat(getStartIndex(data2, 1), is(0));
        Assert.assertThat(getStartIndex(data2, 2), is(1));
        Assert.assertThat(getStartIndex(data2, 3), is(2));
        Assert.assertThat(getStartIndex(data2, 4), is(2));
        Assert.assertThat(getStartIndex(data2, 5), is(3));
        Assert.assertThat(getStartIndex(data2, 6), is(3));

        Assert.assertThat(getStartIndex(data3, 0), is(0));
        Assert.assertThat(getStartIndex(data3, 1), is(0));
        Assert.assertThat(getStartIndex(data3, 2), is(1));
        Assert.assertThat(getStartIndex(data3, 3), is(2));
        Assert.assertThat(getStartIndex(data3, 4), is(3));
        Assert.assertThat(getStartIndex(data3, 5), is(3));
        Assert.assertThat(getStartIndex(data3, 6), is(-1));

        Assert.assertThat(getStartIndex(data4, 0), is(0));
        Assert.assertThat(getStartIndex(data4, 1), is(0));
        Assert.assertThat(getStartIndex(data4, 2), is(1));
        Assert.assertThat(getStartIndex(data4, 3), is(1));
        Assert.assertThat(getStartIndex(data4, 4), is(2));
        Assert.assertThat(getStartIndex(data4, 5), is(3));
        Assert.assertThat(getStartIndex(data4, 6), is(-1));
    }
    @Test
    public void backwardSearchSpacing(){
        Assert.assertThat(getStartIndex(data2, -1), is(3));
        Assert.assertThat(getStartIndex(data2, -2), is(3));
        Assert.assertThat(getStartIndex(data2, -3), is(2));
        Assert.assertThat(getStartIndex(data2, -4), is(2));
        Assert.assertThat(getStartIndex(data2, -5), is(1));
        Assert.assertThat(getStartIndex(data2, -6), is(0));

        Assert.assertThat(getStartIndex(data3, -1), is(-1));
        Assert.assertThat(getStartIndex(data3, -2), is(3));
        Assert.assertThat(getStartIndex(data3, -3), is(3));
        Assert.assertThat(getStartIndex(data3, -4), is(2));
        Assert.assertThat(getStartIndex(data3, -5), is(1));
        Assert.assertThat(getStartIndex(data3, -6), is(0));


        //"한ab국"
        Assert.assertThat(getStartIndex(data4, -1), is(-1));
        Assert.assertThat(getStartIndex(data4, -2), is(3));
        Assert.assertThat(getStartIndex(data4, -3), is(2));
        Assert.assertThat(getStartIndex(data4, -4), is(1));
        Assert.assertThat(getStartIndex(data4, -5), is(1));
        Assert.assertThat(getStartIndex(data4, -6), is(0));



    }


    @Test(expected = IndexOutOfBoundsException.class)
    public void outboundExceptionPositive1() {
        getStartIndex(data2, 7);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void outboundExceptionPositive2() {
        getStartIndex(data3, 7);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void outboundExceptionPositive3() {
        getStartIndex(data4, 7);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void outboundExceptionNegative4() {
        getStartIndex(data2, -7);
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void outboundExceptionNegative5() {
        getStartIndex(data3, -7);
    }
    @Test(expected = IndexOutOfBoundsException.class)
    public void outboundExceptionNegative6() {
        getStartIndex(data4, -7);
    }
}
