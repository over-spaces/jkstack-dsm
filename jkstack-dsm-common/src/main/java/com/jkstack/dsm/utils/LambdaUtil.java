package com.jkstack.dsm.utils;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by denni on 2016/8/8.
 */
public class LambdaUtil {

    //string to integer list
    public static List<Integer> str2ints(String str, String splitChar) {
        return (List<Integer>) str2numbersOfList(str, splitChar, NumberUtils::toInt);
    }

    //string to strs list
    public static List<String> str2strs(String str, String splitChar) {
        if (str == null || str.length() == 0) {
            return Lists.newArrayList();
        }
        List<String> strings = StringUtil.splitToList(splitChar, str);
        if (strings.size() > 0) {
            return strings.stream().collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    //string to long list
    public static List<Long> str2longs(String str, String splitChar) {
        return (List<Long>) str2numbersOfList(str, splitChar, NumberUtils::toLong);
    }

    //string to long set
    public static Collection<Long> str2longsOfSet(String str, String splitChar) {
        return (Collection<Long>) str2numbersOfSet(str, splitChar, NumberUtils::toLong);
    }

    private static List<? extends Number> str2numbersOfList(String str, String splitChar, Function<String, ? extends Number> f) {
        if (str == null || str.length() == 0) {
            return Lists.newArrayList();
        }
        List<String> strings = StringUtil.splitToList(splitChar, str);
        if (strings.size() > 0) {
            return strings.stream().map(f).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    private static Collection<? extends Number> str2numbersOfSet(String str, String splitChar, Function<String, ? extends Number> f) {
        if (str == null || str.length() == 0) {
            return Sets.newLinkedHashSet();
        }
        List<String> strings = StringUtil.splitToList(splitChar, str);
        if (strings.size() > 0) {
            return strings.stream().map(f).collect(Collectors.toCollection(LinkedHashSet::new));
        }
        return Sets.newLinkedHashSet();
    }

    //filter list to list
    public static <T> List<T> filterToList(Collection<T> from, Predicate<T> predicate) {
        if (CollectionUtils.isEmpty(from)) {
            return Lists.newArrayList();
        }
        return from.stream().filter(predicate).collect(Collectors.toList());
    }

    //filter list to set
    public static <T> Set<T> filterToSet(Collection<T> from, Predicate<T> predicate) {
        if (CollectionUtils.isEmpty(from)) {
            return Sets.newLinkedHashSet();
        }
        return from.stream().filter(predicate).collect(Collectors.toSet());
    }

    private static List<? extends Number> str2numbers(String str, String splitChar, Function<String, ? extends Number> f) {
        if (str == null || str.length() == 0) {
            return Lists.newArrayList();
        }
        List<String> strings = StringUtil.splitToList(splitChar, str);
        if (strings.size() > 0) {
            return strings.stream().map(f).collect(Collectors.toList());
        }
        return Lists.newArrayList();
    }

    //list to string
    public static <T> String list2str(Collection<T> list, String splitChar, Function<T, Object> function) {
        StringBuilder sb = new StringBuilder();
        T lastT = null;
        for (T t : list) {
            if (sb.length() > 0 && lastT != null) {
                sb.append(splitChar);
            }
            if (t != null) {
                Object obj = function.apply(t);
                if (obj != null) {
                    sb.append(obj);
                    lastT = t;
                }
            }
        }
        return sb.toString();
    }

    public static <T> String list2strStrengthen(Collection<T> list, String splitChar, Function<T, Object> function) {
        return CollectionUtils.isEmpty(list) ? "" : list2str(list, splitChar, function);
    }

    ;

    //array to string
    public static <T> String array2str(T[] ts, String splitChar, Function<T, Object> function) {
        return list2str(Arrays.asList(ts), splitChar, function);
    }

    //for map to set
    public static <K extends Comparable, V, R> Collection<R> map2KeySet(Map<K, V> map, Function<K, R> func) {
        return map.entrySet().stream().map(kvEntry -> func.apply(kvEntry.getKey())).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    //for map to list
    public static <K extends Comparable, V, R> List<R> map2List(Map<K, V> map, BiFunction<K, V, R> func) {
        return map.entrySet().stream().map(kvEntry -> func.apply(kvEntry.getKey(), kvEntry.getValue())).collect(Collectors.toList());
    }

    //for map to map, same key
    public static <K, V, R> Map<K, R> map2map(Map<K, V> map, Function<V, R> funcValue) {
        Map<K, R> resultMap = Maps.newLinkedHashMap();
        map.forEach((k, v) -> {
            R r = funcValue.apply(v);
            resultMap.put(k, r);
        });
        return resultMap;
        //return map.entrySet().stream()
        //.collect(Collectors.toMap(Map.Entry::getKey, e -> funcValue.apply(e.getValue())));
    }

    //for map to list
    public static <K extends Comparable, V, R> List<R> map2ValueList(Map<K, V> map, Function<V, R> func) {
        return map2List(map, (k, v) -> func.apply(v));
    }

    //for list to map
    public static <K, R> Map<K, R> list2map(Collection<R> list, Function<R, K> func) {
        return list.stream().collect(Collectors.toMap(func, Function.identity(), (v1, v2) -> v1, LinkedHashMap::new));
    }

    //for list to mapByList
    public static <K, R> Map<K, List<R>> list2mapByList(Collection<R> list, Function<R, K> func) {
        return list.stream().collect(Collectors.groupingBy(func));
    }

    //for list to map
    public static <K, V, R> Map<K, V> list2map(Collection<R> list, Function<R, K> funcKey, Function<R, V> funcValue) {
        return list.stream().collect(Collectors.toMap(funcKey, funcValue, (v1, v2) -> v1, LinkedHashMap::new));
    }

    //for list to list
    public static <T, U> List<U> list2list(Collection<T> from, Function<T, U> func) {
        return from.stream().map(func).collect(Collectors.toList());
    }

    public static <T, U> List<U> list2listStrengthen(Collection<T> from, Predicate<T> predicate, Function<T, U> func) {
        if (CollectionUtils.isEmpty(from)) {
            return Lists.newArrayList();
        }
        return from.stream().filter(predicate).map(func).collect(Collectors.toList());
    }

    public static <T, U> List<U> list2listStrengthen(Collection<T> from, Function<T, U> func) {
        if (CollectionUtils.isEmpty(from)) {
            return Lists.newArrayList();
        }
        return from.stream().map(func).collect(Collectors.toList());
    }

    //for list to list
    public static <T, U> Set<U> list2set(Collection<T> from, Predicate<T> predicate, Function<T, U> func) {
        return from.stream().filter(predicate).map(func).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    //for list to list
    public static <T, U> Set<U> list2set(Collection<T> from, Function<T, U> func) {
        return from.stream().map(func).collect(Collectors.toCollection(LinkedHashSet::new));
    }

    //for array to list
    public static <T, U> List<U> array2list(T[] from, Function<T, U> func) {
        return Arrays.stream(from).map(func).collect(Collectors.toList());
    }

    //find max value
    public static <T> T maxValue(Collection<T> list, Comparator<T> comparator) {
        return maxValue(list.stream(), comparator);
    }


    //find max value
    public static <T> T maxValue(Stream<T> stream, Comparator<T> comparator) {
        Optional<T> optional = stream.max(comparator);
        return optional.orElse(null);
    }

    //find min value
    public static <T> T minValue(Collection<T> list, Comparator<T> comparator) {
        return minValue(list.stream(), comparator);
    }

    //find min value
    public static <T> T minValue(Stream<T> stream, Comparator<T> comparator) {
        Optional<T> optional = stream.min(comparator);
        return optional.orElse(null);
    }

    //calc sum value
    public static <T extends Number> long sumValue(List<T> list) {
        return list.stream().mapToLong(Number::longValue).sum();
    }

    public static <T> T getOne(List<T> list, Predicate<T> predicate) {

        List<T> filter = Optional.ofNullable(list)
                .orElse(Lists.newArrayList())
                .stream()
                .filter(predicate)
                .collect(Collectors.toList());

        if (CollectionUtils.isNotEmpty(filter)) {
            return filter.get(0);
        } else {
            return null;
        }
    }
}
