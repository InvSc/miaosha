package com.invsc.miaosha_1.thread;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public class TestTransferValue {
    @AllArgsConstructor
    @Getter
    @Setter
    static class Person {
        String personName;
    }

    public void changeValue1(int age) {
        age = 30;
    }
    public void changeValue2(Person person) {
        person.setPersonName("xxx");
    }
    public void changeValue3(String str) {
        str = "xxx";
    }
    public static void main(String[] args) {
        TestTransferValue test = new TestTransferValue();

        int age = 20;
        test.changeValue1(age);
        System.out.println("age------" + age);

        Person person = new Person("abc");
        test.changeValue2(person);
        System.out.println("personName------" + person.getPersonName());

        String str = "abc";
        test.changeValue3(str);
        System.out.println("String------" + str);
    }
    /**
     *  1 test.changeValue1(age)解释
     *      int作为基本数据类型放在虚拟机栈中
     *      int age = 20 位于main栈帧
     *      int age = 30 位于changeValue1栈帧
     *  2 他们引用了堆中的同一个对象
     *  3 String的特殊性 采用"abc"这种字面量形式声明时
     *      它会放在运行期常量池中
     */
}
