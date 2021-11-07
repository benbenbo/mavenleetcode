package com.zzb.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

/**
 * @author XBird
 * @date 2021/10/31
 **/
@RunWith(JUnit4.class)
public class LeetCode05 {
    /**
     * 测试方法
     */
    @Test
    public void testAll() {
        String s = "aba453aba";
        String max = test03Opt1(s);
        System.out.println("最大回文子串为：" + max);
    }

    /**
     * 暴力解法-主程序
     *
     * @param s
     * @return
     */
    public String test01(String s) {
        int max = 0;
        String maxSub = "";
        //这里具有n平方的时间复杂度，isReverStr又有n的时间复杂度
        //空间复杂度为常数项
        for (int i = 0; i < s.length(); i++) {
            for (int j = i + 1; j <= s.length(); j++) {
                String sub = s.substring(i, j);
                if (isReverStr(sub)) {
                    if (sub.length() > max) {
                        maxSub = sub;
                        max = sub.length();
                    }
                }
            }
        }
        return maxSub;
    }

    /**
     * 暴力解法-判断是否为回文串
     *
     * @param s
     * @return
     */
    public boolean isReverStr(String s) {
        int len = s.length();
        for (int i = 0; i < len / 2; i++) {
            if (s.charAt(i) != s.charAt(len - i - 1)) {
                return false;
            }
        }
        return true;
    }


    /**
     * 动态规划法解决
     */
    public String test02(String s) {
        int len = s.length();
        int maxLen = 0;
        int maxEnd = 0;
        String s2 = new StringBuilder(s).reverse().toString();
        //动态规划数组
        int[][] arr = new int[len][len];
        //填充数组
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < len; j++) {
                if (s.charAt(i) == s2.charAt(j)) {
                    if (i == 0 || j == 0) {
                        arr[i][j] = 1;
                    } else {
                        arr[i][j] = arr[i - 1][j - 1] + 1;
                    }
                }
                if (arr[i][j] > maxLen) {
                    int i1 = len - 1 - i;
                    int i2 = i1 + arr[i][j] - 1;
                    if (i2 == j) {
                        maxLen = arr[i][j];
                        maxEnd = i;
                    }
                }
            }
        }
        return s.substring(maxEnd - maxLen + 1, maxEnd + 1);
    }

    /**
     * 动态规划法-优化空间复杂度
     *
     * @return
     */
    public String test02Opt(String s) {
        int len = s.length();
        int maxLen = 0;
        int maxEnd = 0;
        String s2 = new StringBuilder(s).reverse().toString();
        //动态规划数组
        int[] arr = new int[len];
        //填充数组
        for (int i = 0; i < len; i++) {
            for (int j = len - 1; j > -1; j--) {
                if (s.charAt(i) == s2.charAt(j)) {
                    if (i == 0 || j == 0) {
                        arr[j] = 1;
                    } else {
                        arr[j] = arr[j - 1] + 1;
                    }
                } else {
                    arr[j] = 0;
                }
                if (arr[j] > maxLen) {
                    int i1 = len - 1 - i;
                    int i2 = i1 + arr[j] - 1;
                    if (i2 == j) {
                        maxLen = arr[j];
                        maxEnd = i;
                    }
                }
            }
        }
        return s.substring(maxEnd - maxLen + 1, maxEnd + 1);
    }

    /**
     * 暴力破解法-优化
     *
     * @param s
     * @return
     */
    public String test03(String s) {
        int len = s.length();
        int maxLen = 0;
        String maxStr = "";
        boolean[][] P = new boolean[len][len];
        for (int i = 1; i <= len; i++) {
            for (int start = 0; start < len; start++) {
                int end = start + i - 1;
                if (end >= len) {
                    //数组越界
                    break;
                }
                P[start][end] = (i == 1 || i == 2 || P[start + 1][end - 1]) && s.charAt(start) == s.charAt(end);
                if (P[start][end] && i > maxLen) {
                    maxStr = s.substring(start, end + 1);
                }
            }
        }
        return maxStr;
    }

    /**
     * 暴力破解法-优化
     * 二阶优化-将遍历改为按照数组遍历，而不是长度+起始下标
     *
     * @return
     */
    public String test03Opt1(String s) {
        int len = s.length();
        int maxLen = 0;
        String maxStr = "";
        boolean[][] P = new boolean[len][len];
        for (int i = len - 1; i > -1; i--) {
            for (int j = i; j < len; j++) {
                P[i][j] = ((j - i + 1 < 3) || P[i + 1][j - 1]) && s.charAt(i) == s.charAt(j);
                if (P[i][j] && j - i + 1 > maxLen) {
                    maxStr = s.substring(i, j + 1);
                }
            }
        }
        return maxStr;
    }
}
