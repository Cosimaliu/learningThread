package stream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class streamLearning {

    private static List<Apple> appleStore = new ArrayList<>();

    static {
        appleStore.add(new Apple(1,"red",500,"杭州"));
        appleStore.add(new Apple(2,"red",400,"湖南"));
        appleStore.add(new Apple(3,"green",300,"湖南"));
        appleStore.add(new Apple(4,"green",200,"天津"));
        appleStore.add(new Apple(5,"green",100,"天津"));

    }

    //找出红色苹果
    public void test1() {
        List<Apple> redList = appleStore.stream()
                .filter(a -> a.getColor().equals("red"))
                .collect(Collectors.toList());
    }

    //找出红色的苹果
    //重量
    //产地
    public List<Apple> test2(Predicate<? super Apple> pr){
        List<Apple> List = appleStore.stream()
                .filter(pr)
                .collect(Collectors.toList());
        return List;
    }
//    public static void main(String[] args) {
//        List<Apple> list = new ArrayList();
//        list= new streamLearning().test2(a->a.getColor().equals("red")&&a.getWeight()>300);
////        list.forEach(Apple p : list){}
//        for(Apple p:list){
//            System.out.println(p.toString());
//        }
//    }





    //求出每个颜色的平均重量

    //一、传统写法
    public void test3(){
        //1.基于颜色分组
        Map<String,List<Apple>> maps = new HashMap<>();
        for (Apple apple : appleStore) {
            List<Apple> list = maps.computeIfAbsent(apple.getColor(), a -> new ArrayList<>());
            list.add(apple);
        }
        //2.求出每个颜色的平均重量
        for (Map.Entry<String, List<Apple>> entry : maps.entrySet()) {
            int weights = 0;
            for (Apple apple : entry.getValue()) {
                weights+=apple.getWeight();
            }
            System.out.println(String.format("颜色%s,平均重量%s",entry.getKey(),weights/entry.getValue().size()));
        }
        System.out.println("==================");
    }

    //求出每个颜色的平均重量
    public static void main(String[] args) {
        new streamLearning().test3();

        new streamLearning().test4();
    }



    //求出每个颜色的平均重量

    //二、stream流写法【与第一种传统写法相比：大大简化了代码量】
    public void test4(){
        appleStore.stream().collect(Collectors.groupingBy(a -> a.getColor(),//基于颜色分组
                Collectors.averagingInt(a -> a.getWeight())))//统计平均重量
                .forEach((k,v)-> System.out.println(k+":"+v));//打印出来
    }
}
