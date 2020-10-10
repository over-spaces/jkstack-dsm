package com.jkstack.dsm.common.utils;

import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author <a href='fz_du@people2000.net'>dufazuo</a>
 * @version 1.0
 * @Title：健康云平台
 * @Description：StringUtil 字符串工具类
 * @Time 2014年9月25日 下午4:00:22 create
 */
public final class StringUtil {

    public static boolean containsIgnoreCase(final CharSequence str, final CharSequence searchStr){
        return StringUtils.containsIgnoreCase(str, searchStr);
    }

    /**
     * Guava Splitter split
     *
     * @param separator
     * @param str
     * @return
     */
    public static String[] splitToArray(String separator, String str) {
        List<String> stringList = splitToList(separator, str);
        return stringList.toArray(new String[stringList.size()]);
    }

    /**
     * 首字母大写转换
     *
     * @param str
     * @return
     */
    public static String upperCase(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }

    /**
     * 首字母小写转换
     *
     * @param str
     * @return
     */
    public static String lowerCase(String str) {
        char[] chars = str.toCharArray();
        chars[0] += 32;
        return String.valueOf(chars);
    }

    public static String[] split(String separator, String str) {
        return splitToArray(separator, str);
    }

    public static String[] splitLimit(String separator, int limit, String str) {
        Iterable<String> iterable = Splitter.on(separator).limit(limit).split(str);
        List<String> stringList = Lists.newArrayList(iterable);
        return stringList.toArray(new String[stringList.size()]);
    }

    public static List<String> splitToList(String separator, String str) {
        Iterable<String> iterable = Splitter.on(separator).split(str);
        return Lists.newArrayList(iterable);
    }

    public static List<String> searchTemplates(Collection<Long> ids) {
        List<String> t_players = Lists.newArrayList();
        for (Long id : ids) {
            t_players.addAll(StringUtil.searchTemplates(id + ""));
        }
        return t_players;
    }

    public static List<String> searchTemplates(String playerId) {
        return ImmutableList.of(playerId, "%," + playerId, playerId + ",%", "%," + playerId + ",%");
    }

    /**
     * 去除字符串首尾空格(包括半角，全角的空格)
     *
     * @param source 原字符串
     * @return 去除空格的字符串
     */
    public static String trimSpace(String source) {
        return source == null ? null : source.replaceAll("^[\\s\\t　]*|[\\s\\t　]*$", "");
    }

    /**
     * 判断字符串是否为空
     *
     * @param str 要判断是否为空的源字符串
     * @return boolean 如果源字符串为空，则返回true;否则返回false
     * @Time 2014年9月25日 下午4:01:30 create
     * @author dufazuo
     */
    public static boolean isEmpty(String str) {
        return null == str || "".equals(trimSpace(str));
    }

    /**
     * 将整型数组转换为以“,”分隔的字符串
     *
     * @param vals 整型数组
     * @return String 由整型数组转换生成的以逗号分隔的字符串
     * @Time 2014年9月25日 下午4:03:43 create
     * @author dufazuo
     */
    public static String combString(Integer[] vals) {
        if (null == vals) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (Integer val : vals) {
            sb.append(",").append(val);
        }
        return sb.toString().replaceFirst(",", "");
    }

    /**
     * 将字符串数组转换为以“,”分隔的字符串
     *
     * @param vals 字符串数组
     * @return String 由字符串数组转换生成的以逗号分隔的字符串
     * @Time 2014年9月25日 下午4:05:28 create
     * @author dufazuo
     */
    public static String combString(String[] vals) {
        if (null == vals) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (String val : vals) {
            sb.append(",").append(val);
        }
        return sb.toString().replaceFirst(",", "");
    }

    /**
     * 将字符串数组转换为以“,”分隔的字符组成的字符串
     *
     * @param vals 源字符串数组
     * @return String 返回字符形式以“,”分隔的字符组成的字符串，例如'a','b'
     * @Time 2014年9月25日 下午4:16:44 create
     * @author dufazuo
     */
    public static String combCharString(String[] vals) {
        if (null == vals) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        for (String val : vals) {
            sb.append(",'").append(val).append("'");
        }
        return sb.toString().replaceFirst(",", "");
    }

    /**
     * 将对象集合转换为以“,”分隔的字符串
     *
     * @param vals 集合对象
     * @return String 返回以“,”分隔的字符串
     * @Time 2014年9月25日 下午4:19:12 create
     * @author dufazuo
     */
    @SuppressWarnings("rawtypes")
    public static String combString(Collection vals) {
        if (null == vals) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        for (Object val : vals) {
            sb.append(",'").append(val).append("'");
        }
        return sb.toString().replaceFirst(",", "");
    }

    /**
     * 将以“，”分隔的字符串转换为字符形式以“,”分隔的字符串
     *
     * @param strs 源字符串
     * @return String 返回字符形式以“,”分隔的字符串，例如'1','2'
     * @Time 2014年9月25日 下午4:20:47 create
     * @author dufazuo
     */
    public static String combCharString(String strs) {
        if (null == strs) {
            return null;
        }
        String[] vals = StringUtil.splitToArray(",", strs);
        StringBuilder sb = new StringBuilder();
        for (String val : vals) {
            sb.append(",'").append(val.trim()).append("'");
        }
        return sb.toString().replaceFirst(",", "");
    }

    /**
     * 转换特殊字符,加转义字符，页面显示，特殊字符包括：&、<、>、'、\、\\\\、,
     *
     * @param str 需要转换特殊字符的字符串
     * @return String 返回转换后的字符串
     * @Time 2014年9月25日 下午4:22:47 create
     * @author dufazuo
     */
    public static String transSpecialChar(String str) {
        String myString = str;
        if (null != str) {
            myString = myString.replaceAll("&", "&amp;");
            myString = myString.replaceAll("<", "&lt;");
            myString = myString.replaceAll(">", "&gt;");
            myString = myString.replaceAll("'", "&prime;");
            myString = myString.replaceAll("\"", "&quot;");
            myString = myString.replaceAll("\\\\", "\\\\\\\\");
            myString = myString.replaceAll(",", "&sbquo;");
        }
        return myString;
    }

    /**
     * 关键字过滤条件不能包含全角或半角的单引号、双引号和百分号等，一般SQL语句用
     *
     * @param str SQL语句
     * @return String 转义后的SQL语句
     * @Time 2014年9月25日 下午4:23:33 create
     * @author dufazuo
     */
    public static String replaceStr(String str) {
        if (isEmpty(str)) {
            return "";
        }
        String replaceStr = str.replace("[", "[[]");// 此句一定要在最前面
        replaceStr = replaceStr.replace("_", "[_]");
        replaceStr = replaceStr.replace("%", "[%]");
        replaceStr = replaceStr.replace("'", "''");
        replaceStr = replaceStr.replace("‘", "\\‘");
        replaceStr = replaceStr.replace("’", "\\’");
        replaceStr = replaceStr.replace("{", "[{]");
        return replaceStr;
    }

    public static String replaceCRLF(String str) {
        if (isEmpty(str)) {
            return "";
        }
        String replaceStr = str.replaceAll("\\r\\n", "<br>");
        replaceStr = replaceStr.replaceAll("\\r", "<br>");
        replaceStr = replaceStr.replaceAll("\\n", "<br>");
        return replaceStr;
    }

    private static final String[] IEBrowserSignals = {"MSIE", "Trident", "Edge"};

    public static boolean isMSBrowser(String userAgent) {
        //String userAgent = request.getHeader("User-Agent");
        for (String signal : IEBrowserSignals) {
            if (userAgent.contains(signal)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 格式化
     *
     * @return
     * @author lizhgb
     * @Date 2015-10-14 下午1:17:35
     * @Modified 2017-04-28 下午8:55:35
     */
    public static String formatJson(Object obj) {
        String jsonStr = GsonUtil.toJson(obj);
        if (null == jsonStr || "".equals(jsonStr)) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        char last = '\0';
        char current = '\0';
        int indent = 0;
        boolean isInQuotationMarks = false;
        for (int i = 0; i < jsonStr.length(); i++) {
            last = current;
            current = jsonStr.charAt(i);
            switch (current) {
                case '"':
                    if (last != '\\') {
                        isInQuotationMarks = !isInQuotationMarks;
                    }
                    sb.append(current);
                    break;

                case '{':
                case '[':
                    sb.append(current);
                    if (!isInQuotationMarks) {
                        sb.append('\n');
                        indent++;
                        addIndentBlank(sb, indent);
                    }
                    break;

                case '}':
                case ']':
                    if (!isInQuotationMarks) {
                        sb.append('\n');
                        indent--;
                        addIndentBlank(sb, indent);
                    }
                    sb.append(current);
                    break;

                case ',':
                    sb.append(current);
                    if (last != '\\' && !isInQuotationMarks) {
                        sb.append('\n');
                        addIndentBlank(sb, indent);
                    }
                    break;

                default:
                    sb.append(current);
            }
        }

        return sb.toString();
    }

    /**
     * 添加space
     *
     * @param sb
     * @param indent
     * @author lizhgb
     * @Date 2015-10-14 上午10:38:04
     */
    private static void addIndentBlank(StringBuilder sb, int indent) {
        for (int i = 0; i < indent; i++) {
            sb.append('\t');
        }
    }

}
