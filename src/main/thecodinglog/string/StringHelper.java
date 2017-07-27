package thecodinglog.string;

public class StringHelper {

    public static String substrb2(String sourceString, Number beginByte, Number byteLength, String leftPadding, String rightPadding) {
        if (sourceString == null || sourceString.isEmpty()) {
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

        int position = beginByte.intValue();
        if (position == 0) position = 1;

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

        if (position >= 0) {
            for (int i = 0; i < sourceString.length(); i++) {
                if (position - 1 == accByte) {
                    startIndex = i;
                    accByte = accByte + getByteLengthOfChar(sourceString.charAt(i));
                    break;
                } else if (position == accByte) {
                    beginHalf = true;
                    startIndex = i;
                    accByte = accByte + getByteLengthOfChar(sourceString.charAt(i));
                    break;
                } else if (accByte + 2 == position && i == sourceString.length() - 1) {
                    beginHalf = true;
                    accByte = accByte + getByteLengthOfChar(sourceString.charAt(i));
                    break;
                }
                accByte = accByte + getByteLengthOfChar(sourceString.charAt(i));
            }
        } else {
            position = position * -1;

            for (int i = sourceString.length() - 1; i >= 0; i--) {

                accByte = accByte + getByteLengthOfChar(sourceString.charAt(i));

                if (position == accByte) {
                    startIndex = i;
                    break;
                } else if (position == accByte - 1) {
                    beginHalf = true;
                    if (i != sourceString.length() - 1) {
                        startIndex = i + 1;
                    }
                    break;
                }
            }
        }


        if (accByte < position) {
            throw new IndexOutOfBoundsException("The start position is larger than the length of the original string.");
        }

        StringBuilder stringBuilder = new StringBuilder();
        int accSubstrLength = 0;

        if (beginHalf) {
            stringBuilder.append(" ");
            accSubstrLength++;
        }
        if (byteLength == null) {
            stringBuilder.append(sourceString.substring(startIndex));
            return new String(stringBuilder);
        }

        for (int i = startIndex; i < sourceString.length(); i++) {
            accSubstrLength = accSubstrLength + getByteLengthOfChar(sourceString.charAt(i));
            if (accSubstrLength == length) {
                stringBuilder.append(sourceString.charAt(i));
                break;
            } else if (accSubstrLength - 1 == length) {
                if (beginByte.intValue() >= 0)
                    stringBuilder.append(" ");
                break;
            } else if (accSubstrLength - 1 > length) {

                break;
            }
            stringBuilder.append(sourceString.charAt(i));
        }

        if (leftPadding != null) {
            int diffLength = length - accSubstrLength;
            StringBuilder padding = new StringBuilder();
            for (int i = 0; i < diffLength; i++) {
                padding.append(leftPadding);
            }
            stringBuilder.insert(0, padding);
        }

        if (rightPadding != null) {
            int diffLength = length - accSubstrLength;
            StringBuilder padding = new StringBuilder();
            for (int i = 0; i < diffLength; i++) {
                padding.append(rightPadding);
            }
            stringBuilder.append(padding);
        }


        return new String(stringBuilder);
    }

    public static String substrb2(String str, Number beginByte, Number byteLength) {
        return substrb2(str, beginByte, byteLength, null, null);
    }

    public static String substrb2(String string, Number position) {
        return substrb2(string, position, null, null, null);
    }

    public static String substrb2(String string, Number position, String leftPadding, String rightPadding) {
        return substrb2(string, position, null, leftPadding, rightPadding);
    }

    public static int getByteLengthOfChar(char c) {
        if ((int) c < 128) {
            return 1;
        } else {
            return 2;
        }
    }
}
