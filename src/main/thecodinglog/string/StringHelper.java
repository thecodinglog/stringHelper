package thecodinglog.string;

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
     *
     * Returns the substring of the String.
     * It returns a string as specified length and byte position.
     * You can pad characters left or right when there is a specified length.
     * It distinguishes between 1 byte character and 2 byte character and returns it exactly as specified byte length.
     * If the start position or the specified length causes a 2-byte character to be truncated in the middle,
     * it will be converted to Space.
     * You can specify either left or right padding.
     *
     * If beginByte is 0, it is changed to 1 and processed.
     * If beginByte is less than 0, the string is searched for from right to left.
     * If beginByte or byteLength is a real number, the decimal point is discarded.
     * If you do not specify a length, returns everything from the starting position to the right-end string.
     *
     * Examples:
     * <blockquote><pre>
     *     StringHelper.substrb2("a好호b", 1, 10, null, "|") returns "a好호b||||"
     *     StringHelper.substrb2("ab한글", 4, 2) returns "  "
     *     StringHelper.substrb2("한a글", -3, 2) returns "a "
     *     StringHelper.substrb2("abcde한글이han gul다ykd", 7) returns " 글이han gul다ykd"
     * </pre></blockquote>
     *
     * @param str a string to substring
     * @param beginByte the beginning byte
     * @param byteLength length of bytes
     * @param leftPadding a character for padding. It must be 1 byte character.
     * @param rightPadding a character for padding. It must be 1 byte character.
     * @return a substring
     */
    public static String substrb2(String str, Number beginByte, Number byteLength, String leftPadding, String rightPadding) {
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

        int beginPosition = beginByte.intValue();
        if (beginPosition == 0) beginPosition = 1;

        int length;
        if (byteLength != null) {
            length = byteLength.intValue();
            if (length < 0) {
                return null;
            }
        } else {
            length = -1;
        }

        if (length == 0)
            return null;

        boolean beginHalf = false;
        int accByte = 0;
        int startIndex = -1;

        if (beginPosition >= 0) {
            for (int i = 0; i < str.length(); i++) {
                if (beginPosition - 1 == accByte) {
                    startIndex = i;
                    accByte = accByte + getByteLengthOfChar(str.charAt(i));
                    break;
                } else if (beginPosition == accByte) {
                    beginHalf = true;
                    startIndex = i;
                    accByte = accByte + getByteLengthOfChar(str.charAt(i));
                    break;
                } else if (accByte + 2 == beginPosition && i == str.length() - 1) {
                    beginHalf = true;
                    accByte = accByte + getByteLengthOfChar(str.charAt(i));
                    break;
                }
                accByte = accByte + getByteLengthOfChar(str.charAt(i));
            }
        } else {
            beginPosition = beginPosition * -1;
            if(length > beginPosition){
                length = beginPosition;
            }

            for (int i = str.length() - 1; i >= 0; i--) {

                accByte = accByte + getByteLengthOfChar(str.charAt(i));

                if (i == str.length() - 1) {
                    if (getByteLengthOfChar(str.charAt(i)) == 1) {
                        if (beginPosition == accByte) {
                            startIndex = i;
                            break;
                        }
                    } else {
                        if (beginPosition == accByte) {
                            if (length > 1) {
                                startIndex = i;
                                break;
                            } else {
                                beginHalf = true;
                                break;
                            }
                        }else if(beginPosition == accByte - 1){
                            if(length == 1){
                                beginHalf = true;
                                break;
                            }
                        }
                    }
                } else {
                    if (getByteLengthOfChar(str.charAt(i)) == 1) {
                        if (beginPosition == accByte) {
                            startIndex = i;
                            break;
                        }
                    } else {
                        if (beginPosition == accByte) {
                            if (length > 1) {
                                startIndex = i;
                                break;
                            } else {
                                beginHalf = true;
                                break;
                            }

                        } else if(beginPosition == accByte - 1) {
                            if(length > 1){
                                startIndex = i + 1;
                            }
                            beginHalf = true;
                            break;

                        }
                    }

                }
            }
        }


        if (accByte < beginPosition) {
            throw new IndexOutOfBoundsException("The start position is larger than the length of the original string.");
        }


        StringBuilder stringBuilder = new StringBuilder();
        int accSubstrLength = 0;

        if (beginHalf) {
            stringBuilder.append(" ");
            accSubstrLength++;
        }

/*        if (startIndex == -1 && accSubstrLength > 0) {
            return new String(stringBuilder);
        }*/

        if (byteLength == null) {
            stringBuilder.append(str.substring(startIndex));
            return new String(stringBuilder);
        }


        for (int i = startIndex; i < str.length() && startIndex >= 0; i++) {
            accSubstrLength = accSubstrLength + getByteLengthOfChar(str.charAt(i));
            if (accSubstrLength == length) {
                stringBuilder.append(str.charAt(i));
                break;
            } else if (accSubstrLength - 1 == length) {
                    stringBuilder.append(" ");
                break;
            } else if (accSubstrLength - 1 > length) {

                break;
            }
            stringBuilder.append(str.charAt(i));
        }

        if (leftPadding != null) {
            int diffLength = byteLength.intValue() - accSubstrLength;
            StringBuilder padding = new StringBuilder();
            for (int i = 0; i < diffLength; i++) {
                padding.append(leftPadding);
            }
            stringBuilder.insert(0, padding);
        }

        if (rightPadding != null) {
            int diffLength = byteLength.intValue() - accSubstrLength;
            StringBuilder padding = new StringBuilder();
            for (int i = 0; i < diffLength; i++) {
                padding.append(rightPadding);
            }
            stringBuilder.append(padding);
        }


        return new String(stringBuilder);
    }

    private static int getByteLengthOfChar(char c) {
        if ((int) c < 128) {
            return 1;
        } else {
            return 2;
        }
    }


}
