package com.jkstack.dsm.common.vo;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lifang
 * @since 2020/10/26
 */
public class TreeHelper {

    public static <T extends SimpleTreeDataVO> List<T> toTree(List<T> nodes) {
        List<T> roots = findRoots(nodes);
        Collection<T> notRoots = CollectionUtils.subtract(nodes, roots);

        for (T root : roots) {
            root.setChildren(findChildren(root, notRoots));
        }

        Collections.sort(roots, Comparator.comparingInt(SimpleTreeDataVO::getSort));
        return roots;
    }

    public static <T extends SimpleTreeDataVO> List<T> toList(List<T> treeList) {
        List<T> list = Lists.newArrayList();
        for (T t : treeList) {
            recursiveFindChildren(t, list);
        }
        return list;
    }

    private static <T extends SimpleTreeDataVO> void recursiveFindChildren(T t, List<T> list) {
        if (t == null) {
            return;
        }

        list.add(t);

        for (Object child : t.getChildren()) {
            recursiveFindChildren((T) child, list);
        }
    }

    private static <T extends SimpleTreeDataVO> List<T> findRoots(List<T> nodes) {
        List<T> results = new ArrayList<>();
        for (T node : nodes) {
            boolean isRoot = true;
            String fatherItemId = node.getParentId();
            for (SimpleTreeDataVO comparedOne : nodes) {
                if (StringUtils.equals(fatherItemId, comparedOne.getId())) {
                    isRoot = false;
                    break;
                }
            }
            if (isRoot) {
                results.add(node);
            }
        }
        return results;
    }

    private static <T extends SimpleTreeDataVO> List<T> findChildren(T root, Collection<T> nodes) {
        List<T> children = findChildrenNode(root, nodes);
        Collection<T> notChildren = CollectionUtils.subtract(nodes, children);
        for (T child : children) {
            List<T> tmpChildren = findChildren(child, notChildren);
            Collections.sort(tmpChildren, Comparator.comparingInt(SimpleTreeDataVO::getSort));
            child.setChildren(tmpChildren);
        }
        return children;
    }

    private static <T extends SimpleTreeDataVO> List<T> findChildrenNode(T root, Collection<T> nodes) {
        return nodes.stream()
                .filter(node -> StringUtils.equals(node.getParentId(), root.getId()))
                .peek(node -> {
                    node.setParentId(root.getId());
                    node.setDeep(root.getDeep() + 1);
                })
                .sorted(Comparator.comparing(SimpleTreeDataVO::getSort))
                .collect(Collectors.toList());
    }
}
