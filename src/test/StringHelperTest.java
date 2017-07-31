import org.hamcrest.core.IsNull;
import org.junit.Assert;
import org.junit.Test;
import thecodinglog.string.StringHelper;

import static org.hamcrest.CoreMatchers.is;

/**
 * @author Jeongjin Kim
 * @since 2017-07-24
 */
public class StringHelperTest {
    String testDataString = "abcde한글이han gul다ykd";


    @Test
    public void padding() {
        String data1 = "a好호b";
        String data2 = "好호ab";
        String data3 = "ab好호";

        Assert.assertThat(StringHelper.substrb2(data1, 1, 10, null, "|"), is("a好호b||||"));
        Assert.assertThat(StringHelper.substrb2(data1, 3, 10, null, "|"), is(" 호b||||||"));
        Assert.assertThat(StringHelper.substrb2(data1, 3, 10, "|", null), is("|||||| 호b"));

        Assert.assertThat(StringHelper.substrb2(data2, 1, 10, "|", null), is("||||好호ab"));
        Assert.assertThat(StringHelper.substrb2(data2, 2, 10, "|", null), is("||||| 호ab"));
        Assert.assertThat(StringHelper.substrb2(data2, 5, 10, "|", null), is("||||||||ab"));
        Assert.assertThat(StringHelper.substrb2(data2, 6, 10, "|", null), is("|||||||||b"));

        Assert.assertThat(StringHelper.substrb2(data2, -5, 10, "|", null), is("||||| 호ab"));
        Assert.assertThat(StringHelper.substrb2(data3, -1, 10, "|", null), is("||||||||| "));
        Assert.assertThat(StringHelper.substrb2(data3, -2, 10, "|", null), is("||||||||호"));
        Assert.assertThat(StringHelper.substrb2(data2, -5, 10, null, "|"), is(" 호ab|||||"));
        Assert.assertThat(StringHelper.substrb2(data3, -1, 10, null, "|"), is(" |||||||||"));
        Assert.assertThat(StringHelper.substrb2(data3, -2, 10, null, "|"), is("호||||||||"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void inputStringCheck() {
        StringHelper.substrb2(null, 0, 0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void IndexOutOfBounds() {
        StringHelper.substrb2("a", 2, 2);
    }

    @Test(expected = IllegalArgumentException.class)
    public void illegalArgument(){
        StringHelper.substrb2("ddd",1, 3,"a","b");
    }

    /**
     * position이 0이면 1로 인식하게 한다.
     * position이 1이면 1로 인식하게 한다.
     */
    @Test
    public void ifPositionIsZeroTreatAsOne() {

        Assert.assertThat(StringHelper.substrb2(testDataString, 0, 1), is("a"));
        Assert.assertThat(StringHelper.substrb2(testDataString, 1, 1), is("a"));
    }

    /**
     * 위치가 양수일 때 위치부터 길이 만큼 잘라 리턴한다.
     */
    @Test
    public void ifPositionIsPositive() {
        Assert.assertThat(StringHelper.substrb2("ab한글", 4, 2), is("  "));
        Assert.assertThat(StringHelper.substrb2("ab한글", 5, 2), is("글"));
        Assert.assertThat(StringHelper.substrb2("ab한글", 6, 2), is(" "));
        Assert.assertThat(StringHelper.substrb2("ab한글", 6, 2), is(" "));

        Assert.assertThat(StringHelper.substrb2("한글ab", 0, 1), is(" "));
        Assert.assertThat(StringHelper.substrb2("한글ab", 1, 1), is(" "));
        Assert.assertThat(StringHelper.substrb2("한글ab", 1, 2), is("한"));
        Assert.assertThat(StringHelper.substrb2("한글ab", 2, 1), is(" "));
        Assert.assertThat(StringHelper.substrb2("한글ab", 2, 2), is("  "));
        Assert.assertThat(StringHelper.substrb2("한글ab", 2, 3), is(" 글"));



        Assert.assertThat(StringHelper.substrb2(testDataString, 2, 2), is("bc"));
        Assert.assertThat(StringHelper.substrb2(testDataString, 5, 1), is("e"));
        Assert.assertThat(StringHelper.substrb2(testDataString, 5, 2), is("e "));
        Assert.assertThat(StringHelper.substrb2(testDataString, 5, 3), is("e한"));
        Assert.assertThat(StringHelper.substrb2(testDataString, 6, 1), is(" "));
        Assert.assertThat(StringHelper.substrb2(testDataString, 6, 2), is("한"));
        Assert.assertThat(StringHelper.substrb2(testDataString, 6, 3), is("한 "));
    }

    /*String testDataString = "abcde한글이han gul다ykd";*/

    /**
     * 위치가 2바이트 글자 사이에 위치하는 경우 Space로 처리된다.
     */
    @Test
    public void ifPositionIsMiddleOf2btyeCharacter() {
        //position이 2바이트 글자 사이에 위치하는 경우
        Assert.assertThat(StringHelper.substrb2(testDataString, 7, 1), is(" "));
        Assert.assertThat(StringHelper.substrb2(testDataString, 7, 2), is("  "));
        Assert.assertThat(StringHelper.substrb2(testDataString, 7, 3), is(" 글"));
        Assert.assertThat(StringHelper.substrb2(testDataString, 7, 4), is(" 글 "));
        Assert.assertThat(StringHelper.substrb2(testDataString, 7, 5), is(" 글이"));
    }

    /**
     * 위치가 실수인 경우 소수점 자리는 버린다.
     */
    @Test
    public void ifPositionIsNotInteger() {
        Assert.assertThat(StringHelper.substrb2(testDataString, 0.2, 1), is("a"));
        Assert.assertThat(StringHelper.substrb2(testDataString, 1.2, 1), is("a"));
        Assert.assertThat(StringHelper.substrb2(testDataString, 1.8, 1), is("a"));
        Assert.assertThat(StringHelper.substrb2(testDataString, 2.8, 1), is("b"));

    }


    //String testDataString = "abcde한글이hangul다ykd";

    /**
     * 위치가 음수일 때 뒤에서 부터 위치를 카운트하여 길이 만큼 잘라 리턴한다.
     * 길이가 전체 길이보다 길때 가능한 글자수만큼만 리턴한다.
     */
    @Test
    public void ifPositionIsNegative() {
        Assert.assertThat(StringHelper.substrb2(testDataString, -1, 1), is("d"));

        Assert.assertThat(StringHelper.substrb2("a한b글", -1, 1), is(" " ));
        Assert.assertThat(StringHelper.substrb2("a한b글", -2, 1), is(" " ));
        Assert.assertThat(StringHelper.substrb2("한글", -1, 1), is(" " ));
        Assert.assertThat(StringHelper.substrb2("한글", -2, 1), is(" " ));
        Assert.assertThat(StringHelper.substrb2("한a글", -2, 1), is(" " ));
        Assert.assertThat(StringHelper.substrb2("한a글", -3, 1), is("a" ));
        Assert.assertThat(StringHelper.substrb2("한a글", -4, 1), is(" " ));
        Assert.assertThat(StringHelper.substrb2("한a글", -5, 1), is(" " ));

        Assert.assertThat(StringHelper.substrb2("한a글", -1, 2), is(" " ));
        Assert.assertThat(StringHelper.substrb2("한a글", -2, 2), is("글" ));
        Assert.assertThat(StringHelper.substrb2("한a글", -3, 2), is("a " ));
        Assert.assertThat(StringHelper.substrb2("한a글", -4, 2), is(" a" ));
        Assert.assertThat(StringHelper.substrb2("한a글", -5, 2), is("한" ));

        Assert.assertThat(StringHelper.substrb2("한a글", -1, 3), is(" " ));
        Assert.assertThat(StringHelper.substrb2("한a글", -2, 3), is("글" ));
        Assert.assertThat(StringHelper.substrb2("한a글", -3, 3), is("a글" ));
        Assert.assertThat(StringHelper.substrb2("한a글", -4, 3), is(" a " ));
        Assert.assertThat(StringHelper.substrb2("한a글", -5, 3), is("한a" ));


        Assert.assertThat(StringHelper.substrb2(testDataString, -3, 3), is("ykd"));

        Assert.assertThat(StringHelper.substrb2(testDataString, -4, 3), is(" yk"));
        Assert.assertThat(StringHelper.substrb2(testDataString, -4, 2), is(" y"));
        Assert.assertThat(StringHelper.substrb2(testDataString, -4, 1), is(" "));


    }


    /**
     * 길이가 음수이거나 0인 경우 null 리턴한다.
     */
    @Test
    public void ifLengthIsNegative() {
        Assert.assertThat(StringHelper.substrb2(testDataString, 2, 0), IsNull.nullValue());

        Assert.assertThat(StringHelper.substrb2(testDataString, 1.2, -1), IsNull.nullValue());
        Assert.assertThat(StringHelper.substrb2(testDataString, 1.8, -1), IsNull.nullValue());
        Assert.assertThat(StringHelper.substrb2(testDataString, 2.8, -1), IsNull.nullValue());
    }

    //String testDataString = "abcde한글이han gul다ykd";

    /**
     * 길이가 생략된 경우 시작위치에서 끝까지 모든 문자를 리턴한다.
     */
    @Test
    public void ifLengthOmmited() {
        Assert.assertThat(StringHelper.substrb2(testDataString, 3), is("cde한글이han gul다ykd"));
        Assert.assertThat(StringHelper.substrb2(testDataString, 5), is("e한글이han gul다ykd"));
        Assert.assertThat(StringHelper.substrb2(testDataString, 6), is("한글이han gul다ykd"));
        Assert.assertThat(StringHelper.substrb2(testDataString, 7), is(" 글이han gul다ykd"));
        Assert.assertThat(StringHelper.substrb2(testDataString, 8), is("글이han gul다ykd"));
        Assert.assertThat(StringHelper.substrb2(testDataString, 9), is(" 이han gul다ykd"));

    }

    /*String testDataString = "abcde한글이han gul다ykd";*/

    /**
     * 길이가 전체 길이보다 긴 경우 시작위치에서 끝까지 모든 문자를 리턴한다.
     */
    @Test
    public void ifLengthIsLonggerThenTestString() {
        Assert.assertThat(StringHelper.substrb2(testDataString, 3, 100), is("cde한글이han gul다ykd"));
        Assert.assertThat(StringHelper.substrb2(testDataString, 5, 100), is("e한글이han gul다ykd"));
        Assert.assertThat(StringHelper.substrb2(testDataString, 6, 100), is("한글이han gul다ykd"));
        Assert.assertThat(StringHelper.substrb2(testDataString, 7, 100), is(" 글이han gul다ykd"));
        Assert.assertThat(StringHelper.substrb2(testDataString, 8, 100), is("글이han gul다ykd"));
        Assert.assertThat(StringHelper.substrb2(testDataString, 9, 100), is(" 이han gul다ykd"));
        Assert.assertThat(StringHelper.substrb2(testDataString, -4, 10), is(" ykd"));
    }


}
