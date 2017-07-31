# stringHelper
String Helper Class for Java

## purpose
 If you are dealing with strings, there are times when you need to cut to the correct byte.
 In particular, CJK languages are two bytes long, and since the usual substring methods are truncated on a character-by-character basis,
they are not as easy to fetch as necessary.
 For example, when defining a protocol between two systems connected by Socket,
 the maximum number of bytes for id, name, etc. can be defined.
 If a character other than 1 byte should come, length overflow will occur.
 So we need Substring method considering 2 byte writing characters.

## Usage
 Returns the substring of the String. It returns a string as specified length and byte position.
You can pad characters left or right when there is a specified length. It distinguishes between 1 byte character and 2 byte character and returns it exactly as specified byte length.
If the start position or the specified length causes a 2-byte character to be truncated in the middle, it will be converted to Space.
You can specify either left or right padding.

If beginByte is 0, it is changed to 1 and processed.
If beginByte is less than 0, the string is searched for from right to left.
If beginByte or byteLength is a real number, the decimal point is discarded.
If you do not specify a length, returns everything from the starting position to the right-end string.

## Examples
* StringHelper.substrb2("a好호b", 1, 10, null, "|") returns "a好호b||||"
* StringHelper.substrb2("ab한글", 4, 2) returns "  "
* StringHelper.substrb2("한a글", -3, 2) returns "a "
* StringHelper.substrb2("abcde한글이han gul다ykd", 7) returns " 글이han gul다ykd"
