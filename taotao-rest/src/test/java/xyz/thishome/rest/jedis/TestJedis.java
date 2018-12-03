package xyz.thishome.rest.jedis;

import org.junit.Test;

public class TestJedis {

    @Test
    public void testJedis() {
        String srcStr = "i love java jajavava wodeai java!";
        String delStr = "java";
        System.out.println(srcStr);
        System.out.println(delStr);
        int num = 0;
        boolean flag = true;
        //如果有一轮截取了字符串，需要判断截取后重组的字符串是否依然包含需要删除单词
        for (; flag; ) {
            flag = false;
            for (int i = 0; i < srcStr.length(); i++) {
                //如果某个字母和需要删除单词的首字母相同，则判断后面连续字符是否也相同
                if (srcStr.charAt(i) == delStr.charAt(0)) {
                    //遍历需要删除的字符串的每一个字母
                    for (int j = 1, temp = i + 1; j < delStr.length() && temp < srcStr.length(); j++, temp++) {
                        if (srcStr.charAt(temp) != delStr.charAt(j)) {
                            break;
                        }
                        //如果包含整个需要删除的单词，截取字符串
                        if (j + 1 == delStr.length()) {
                            srcStr = srcStr.substring(0, i) + srcStr.substring(i + delStr.length(), srcStr.length());
                            i = i - 1;
                            //本轮进行了截取，需要进行下一轮
                            flag = true;
                            num++;
                        }
                    }
                }
            }
        }
        System.out.println(srcStr);
        System.out.println(num);

    }

}
