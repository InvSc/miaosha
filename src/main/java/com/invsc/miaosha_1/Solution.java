package com.invsc.miaosha_1;

import javax.xml.transform.Result;
import java.util.*;

class Solution {
    public List<List<Integer>> subsets(int[] nums) {
        int len = nums.length;
        List<List<List<Integer>>> dp = new ArrayList<>();
        ArrayList<Integer> list1 = new ArrayList<>();
        List<List<Integer>> list2 = new ArrayList<>();
        list1.add(nums[0]);
        list2.add(list1);
        dp.add(0, list2);
        for (int i = 1; i < nums.length; i++) {
            dp.add(i, dp.get(i - 1));
            int size = dp.get(i - 1).size();
            for (int j = 0; j < size; j++) {
                List<Integer> list3 = new ArrayList<>();
                list3.addAll(dp.get(i - 1).get(j));
                list3.add(nums[i]);
                dp.get(i).add(list3);
            }
        }
        return dp.get(len - 1);
    }
}

