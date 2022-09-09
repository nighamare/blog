package com.lycoris.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.apache.commons.collections4.SetUtils;

/**
 * 参考链接：https://zhuanlan.zhihu.com/p/91645988
 * csdn：https://blog.csdn.net/jj89929665/article/details/115915275?spm=1001.2101.3001.6650.5&utm_medium=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7ERate-5-115915275-blog-114045142.t0_layer_searchtargeting_sa&depth_1-utm_source=distribute.pc_relevant.none-task-blog-2%7Edefault%7EBlogCommendFromBaidu%7ERate-5-115915275-blog-114045142.t0_layer_searchtargeting_sa
 * 文本相似度相关判断方法
 * @author lycoris
 * @version 1.0
 * @date 2022/8/16 22:36
 */
public class SimilarityUtil {
    /**
     * 汉明距离
     * @param a 字符串a
     * @param b 字符串b
     * @return 汉明距离float
     */
    public static float hamming(String a, String b) {
        if (a == null || b == null) {
            return 0f;
        }
        if (a.length() != b.length()) {
            return 0f;
        }

        int disCount = 0;
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                disCount++;
            }
        }
        return (float) disCount / (float) a.length();
    }
    /**
     * 余弦相似性
     * @param a 字符串a
     * @param b 字符串b
     * @return 余弦相似性float
     */
    public static float cos(String a, String b) {
        if (a == null || b == null) {
            return 0F;
        }
        Set<Integer> aChar = a.chars().boxed().collect(Collectors.toSet());
        Set<Integer> bChar = b.chars().boxed().collect(Collectors.toSet());

        // 统计字频
        Map<Integer, Integer> aMap = new HashMap<>();
        Map<Integer, Integer> bMap = new HashMap<>();
        for (Integer a1 : aChar) {
            aMap.put(a1, aMap.getOrDefault(a1, 0) + 1);
        }
        for (Integer b1 : bChar) {
            bMap.put(b1, bMap.getOrDefault(b1, 0) + 1);
        }

        // 向量化
        Set<Integer> union = SetUtils.union(aChar, bChar);
        int[] aVec = new int[union.size()];
        int[] bVec = new int[union.size()];
        List<Integer> collect = new ArrayList<>(union);
        for (int i = 0; i < collect.size(); i++) {
            aVec[i] = aMap.getOrDefault(collect.get(i), 0);
            bVec[i] = bMap.getOrDefault(collect.get(i), 0);
        }

        // 分别计算三个参数
        int p1 = 0;
        for (int i = 0; i < aVec.length; i++) {
            p1 += (aVec[i] * bVec[i]);
        }

        float p2 = 0f;
        for (int i : aVec) {
            p2 += (i * i);
        }
        p2 = (float) Math.sqrt(p2);

        float p3 = 0f;
        for (int i : bVec) {
            p3 += (i * i);
        }
        p3 = (float) Math.sqrt(p3);

        return ((float) p1) / (p2 * p3);
    }
    /**
     * 莱文斯坦距离
     * @param a 字符串a
     * @param b 字符串b
     * @return 莱文斯坦距离float
     */
    public static float levenshtein(String a, String b) {
        if (a == null && b == null) {
            return 1f;
        }
        if (a == null || b == null) {
            return 0F;
        }
        int editDistance = editDis(a, b);
        return 1 - ((float) editDistance / Math.max(a.length(), b.length()));
    }

    private static int editDis(String a, String b) {

        int aLen = a.length();
        int bLen = b.length();

        if (aLen == 0) {
            return aLen;
        }
        if (bLen == 0) {
            return bLen;
        }

        int[][] v = new int[aLen + 1][bLen + 1];
        for (int i = 0; i <= aLen; ++i) {
            for (int j = 0; j <= bLen; ++j) {
                if (i == 0) {
                    v[i][j] = j;
                } else if (j == 0) {
                    v[i][j] = i;
                } else if (a.charAt(i - 1) == b.charAt(j - 1)) {
                    v[i][j] = v[i - 1][j - 1];
                } else {
                    v[i][j] = 1 + Math.min(v[i - 1][j - 1], Math.min(v[i][j - 1], v[i - 1][j]));
                }
            }
        }
        return v[aLen][bLen];
    }
    /**
     * Jaccard 相似度
     * @param a 字符串a
     * @param b 字符串b
     * @return Jaccard 相似度float
     */
    public static float jaccard(String a, String b) {
        if (a == null && b == null) {
            return 1f;
        }
        // 都为空相似度为 1
        if (a == null || b == null) {
            return 0f;
        }
        Set<Integer> aChar = a.chars().boxed().collect(Collectors.toSet());
        Set<Integer> bChar = b.chars().boxed().collect(Collectors.toSet());
        // 交集数量
        int intersection = SetUtils.intersection(aChar, bChar).size();
        if (intersection == 0) {
            return 0;
        }
        // 并集数量
        int union = SetUtils.union(aChar, bChar).size();
        return ((float) intersection) / (float)union;
    }
    /**
     * Sorensen Dice 相似度系数
     * @param a 字符串a
     * @param b 字符串b
     * @return Sorensen Dice 相似度系数float
     */
    public static float sorensenDice(String a, String b) {
        if (a == null && b == null) {
            return 1f;
        }
        if (a == null || b == null) {
            return 0F;
        }
        Set<Integer> aChars = a.chars().boxed().collect(Collectors.toSet());
        Set<Integer> bChars = b.chars().boxed().collect(Collectors.toSet());
        // 求交集数量
        int intersect = SetUtils.intersection(aChars, bChars).size();
        if (intersect == 0) {
            return 0F;
        }
        // 全集，两个集合直接加起来
        int aSize = aChars.size();
        int bSize = bChars.size();
        return (2 * (float) intersect) / ((float) (aSize + bSize));
    }

}


