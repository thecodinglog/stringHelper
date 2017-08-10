package thecodinglog.string;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CodingErrorAction;
import java.util.Arrays;

/**
 * @author Jeongjin Kim
 * @since 1.0
 */
public class StringHelper {

    public static String substrb2(String str, Number beginByte) {
        return substrb2(str, beginByte, null, null, null);
    }

    public static String substrb2(String str, Number beginByte, Number byteLength) {
        return substrb2(str, beginByte, byteLength, null, null);
    }

    /**
     * String의 substring을 리턴합니다. substring은 명시된 바이트 위치에서 명시한 길이 만큼 자릅니다.
     * 명시된 길이가 있을 때 왼쪽 또는 오른쪽으로 1 문자로 된 character를 padding 시킬 수 있습니다.
     * 1 byte 문자와 2 byte 문자를 구분하여, 명시된 byte길이 만큼 정확하게 리턴합니다.
     * 시작위치 또는 지정된 길이로 인해 2 byte 문자가 중간에 잘리는 경우 Space로 변환됩니다.
     * 왼쪽 또는 오른쪽 패딩 둘 중 하나만 지정 할 수 있습니다. 지정한 반대쪽 위치는 null로 인자를 넣어 주면 됩니다.
     * beginByte가 0이면 1로 변경하여 처리합니다.
     * beginByte가 0보다 작으면 문자열 오른쪽에서 왼쪽으로 위치를 검색합니다.
     * beginByte 또는 byteLength가 실수이면 소수점은 버립니다.
     * 길이를 명시하지 않은 경우 시작위치에서 오른쪽 끝 문자열까지 모두를 반환합니다.
     * <p>
     * Returns the substring of the String.
     * It returns a string as specified length and byte position.
     * You can pad characters left or right when there is a specified length.
     * It distinguishes between 1 byte character and 2 byte character and returns it exactly as specified byte length.
     * If the start position or the specified length causes a 2-byte character to be truncated in the middle,
     * it will be converted to Space.
     * You can specify either left or right padding.
     * <p>
     * If beginByte is 0, it is changed to 1 and processed.
     * If beginByte is less than 0, the string is searched for from right to left.
     * If beginByte or byteLength is a real number, the decimal point is discarded.
     * If you do not specify a length, returns everything from the starting position to the right-end string.
     * <p>
     * Examples:
     * <blockquote><pre>
     *     StringHelper.substrb2("a好호b", 1, 10, null, "|") returns "a好호b||||"
     *     StringHelper.substrb2("ab한글", 4, 2) returns "  "
     *     StringHelper.substrb2("한a글", -3, 2) returns "a "
     *     StringHelper.substrb2("abcde한글이han gul다ykd", 7) returns " 글이han gul다ykd"
     * </pre></blockquote>
     *
     * @param str          a string to substring
     * @param beginByte    the beginning byte
     * @param byteLength   length of bytes
     * @param leftPadding  a character for padding. It must be 1 byte character.
     * @param rightPadding a character for padding. It must be 1 byte character.
     * @return a substring
     */
    public static String substrb2(String str, Number beginByte, Number byteLength, String leftPadding, String rightPadding) {
        validateInput(str, leftPadding, rightPadding);
        int beginByteInt = (int) beginByte;
        int byteLengthInt = (int) byteLength;

        int fromIndex = beginByteInt == 0 ? 0 : beginByteInt - 1;
        int toIndex = beginByteInt + byteLengthInt;

        Charset charset = Charset.forName("EUC-KR");

        ByteBuffer byteBuffer = charset.encode(str);

        byte[] newone = Arrays.copyOfRange(byteBuffer.array(), fromIndex, toIndex);

        CharsetDecoder charsetDecoder = charset.newDecoder()
                .replaceWith("*")
                .onMalformedInput(CodingErrorAction.REPLACE)
                .onUnmappableCharacter(CodingErrorAction.REPLACE);

        CharBuffer charBuffer = null;
        try {
            charBuffer = charsetDecoder.decode(ByteBuffer.wrap(newone));
        } catch (CharacterCodingException e) {
            e.printStackTrace();
        }
        return charBuffer.toString();
    }

    private static void validateInput(String str, String leftPadding, String rightPadding) {
        if (str == null || str.equals("")) {
            throw new IllegalArgumentException("The source string can not be an empty string or null.");
        }

        if (leftPadding != null && rightPadding != null) {
            throw new IllegalArgumentException("Left padding, right padding Either of two must be null.");
        }

        if (leftPadding != null) {
            if (leftPadding.length() != 1) {
                throw new IllegalArgumentException("The length of the padding string must be one.");
            }
            if (getByteLengthOfChar(leftPadding.charAt(0)) != 1) {
                throw new IllegalArgumentException("The padding string must be 1 Byte character.");
            }
        }

        if (rightPadding != null) {
            if (rightPadding.length() != 1) {
                throw new IllegalArgumentException("The length of the padding string must be one.");
            }
            if (getByteLengthOfChar(rightPadding.charAt(0)) != 1) {
                throw new IllegalArgumentException("The padding string must be 1 Byte character.");
            }
        }
    }

    private static int getByteLengthOfChar(char c) {
        if ((int) c < 128) {
            return 1;
        } else {
            return 2;
        }
    }


}
