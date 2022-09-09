import com.lycoris.domain.Tag;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lycoris
 * @version 1.0
 * @date 2022/8/25 22:27
 */
public class RedisTest {


    public static void main(String[] args) {
//        Jedis jedis = new Jedis("43.140.200.171",9637);
//        jedis.auth("Narcissus1234.");
//        jedis.set("username","my-test");
//        String username = jedis.get("username");
//        System.out.println("username = " + username);
//        String ping = jedis.ping();
//        System.out.println(ping);

        List<Tag> tags = new ArrayList<>();
        List<Tag> list = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Tag tag = new Tag();
            tag.setTagName("java"+i);
            tag.setTagDesc("java"+i);
            tags.add(tag);
        }
        List<String> tagNames = new ArrayList<>();
        List<String> listTagName = new ArrayList<>();
        tagNames.add("java1");
        tagNames.add("java3");
        tagNames.add("java4");
        List<Tag> collect = tags.stream().filter(item -> tagNames.contains(item.getTagName())).collect(Collectors.toList());
        System.out.println("collect = " + collect);
        for (Tag tag : tags) {
            String tagName1 = tag.getTagName();
            if (tagNames.contains(tagName1)) {
                list.add(tag);
                listTagName.add(tagName1);
            }
        }
        System.out.println("list = " + list);
        System.out.println("listTagName = " + listTagName);
//        差集
        tagNames.removeAll(listTagName);
        System.out.println("tagNames = " + tagNames);
//        并集
//        有重复并集(String )
        List<String> test1 = new ArrayList<>();
        test1.add("java1");
        test1.add("java2");
        test1.add("java3");
        test1.add("java4");
        test1.add("java5");
        test1.add("java6");
        List<String> test2 = new ArrayList<>();
        test2.add("java1");
        test2.add("java2");
        test2.add("java3");
        test2.add("java7");
        test1.addAll(test2);
        System.out.println("test1与2并集有重复值 = " + test1);
//        去重
        List<String> test3 = new ArrayList<>();
        test3.add("java1");
        test3.add("java2");
        test3.add("java3");
        test3.add("java4");
        test3.add("java5");
        test3.add("java6");

        test3.removeAll(test2);
        test2.addAll(test3);
        System.out.println("test2与3并集无重复 = " + test2);


    }

}
