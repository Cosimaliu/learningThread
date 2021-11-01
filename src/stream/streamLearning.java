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
        appleStore.add(new Apple(1,"red",500,"杭州","分类一","分类二","分类三","分类四"));
        appleStore.add(new Apple(2,"red",400,"湖南","分类一","分类12","分类三","分类四"));
        appleStore.add(new Apple(3,"green",300,"湖南","分类2","分类12","分类三","分类四"));
        appleStore.add(new Apple(4,"green",200,"天津","分类4","分类5","分类6","分类7"));
        appleStore.add(new Apple(5,"green",100,"天津","分类5","分类5","分类6","分类7"));

    }

//    public void test4(){
//        appleStore.stream().collect(Collectors.groupingBy(a -> a.getColor(),//基于颜色分组
//                Collectors.averagingInt(a -> a.getWeight())))//统计平均重量
//                .forEach((k,v)-> System.out.println(k+":"+v));//打印出来
//
//        appleStore.stream().collect(Collectors.groupingBy(a ->a.getColor(),//基于颜色分组
//                Collectors.averagingInt(a->a.getWeight())))//统计平均重量
//                .forEach((k,v)-> System.out.println(k+":"+v));//打印出来
//    }

public static void main(String[] args) {
    //new streamLearning().test3();
    new streamLearning().test5();
    //new streamLearning().test4();
}
    //按四级分类分组，返回树形结构
    public List test5(){
        List categoryList = new ArrayList();
        Map<String, Map<String, Map<String, List<Apple>>>> categoryMap = appleStore.stream()
                .collect(Collectors.groupingBy(Apple::getFirstCategory, Collectors.groupingBy(Apple::getSecondCategory, Collectors.groupingBy(Apple::getThirdCategory))));

        System.out.println(categoryMap);

        categoryMap.forEach((k, v) -> {
            HashMap categoryChildrenMap = new HashMap();
            categoryChildrenMap.put("categoryName", "'"+k+"'");
            if (v.get("id") == null || "".equals(v.get("id"))) {
                ArrayList list = getChildren((HashMap) v);
                categoryChildrenMap.put("children", list);
            }
            categoryList.add(categoryChildrenMap);
        });

        System.out.println(categoryList);

        return categoryList;
    }


    public ArrayList  getChildren(HashMap<String,Object> map){
        ArrayList children = new ArrayList<>();
        map.forEach((k, v) -> {
            HashMap categoryChildrenMap = new HashMap();
            categoryChildrenMap.put("categoryName", "'"+k+"'");
            if (v instanceof Map && (((Map) v).get("id") == null || "".equals(((Map) v).get("id")))) {
                ArrayList list = getChildren((HashMap) v);
                categoryChildrenMap.put("children", list);
            } else {
                ArrayList listChildren = new ArrayList();
                for (int i = 0; i < ((ArrayList) v).size(); i++) {
                    Apple apple = (Apple) ((ArrayList) v).get(i);
                    HashMap fourCategoryChildrenMap = new HashMap();
                    fourCategoryChildrenMap.put("id", "'"+apple.id+"'");
                    fourCategoryChildrenMap.put("color", "'"+apple.color+"'");
                    fourCategoryChildrenMap.put("weight", "'"+apple.weight+"'");
                    fourCategoryChildrenMap.put("origin", "'"+apple.origin+"'");
                    fourCategoryChildrenMap.put("categoryName","'"+apple.fourCategory+"'");
                    listChildren.add(fourCategoryChildrenMap);
                }
                categoryChildrenMap.put("children", listChildren);
            }
            children.add(categoryChildrenMap);
        });
        return children;
    }

    //找出红色苹果
    public void test1() {
        List<Apple> redList = appleStore.stream()
                .filter(a -> a.getColor().equals("red"))
                .collect(Collectors.toList());

        List<Apple> redList2 = appleStore.stream()
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
//        List<Apple> list =  new ArrayList<>();
//        list  = new streamLearning().test2(a -> a.getColor().equals("red")&&a.getWeight()>300);
//        for (Apple apple : list) {
//            System.out.println(apple.toString());
//        }
//    }


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




    //求出每个颜色的平均重量

    //二、stream流写法【与第一种传统写法相比：大大简化了代码量】
    public void test4(){
        appleStore.stream().collect(Collectors.groupingBy(a -> a.getColor(),//基于颜色分组
                Collectors.averagingInt(a -> a.getWeight())))//统计平均重量
                .forEach((k,v)-> System.out.println(k+":"+v));//打印出来

        appleStore.stream().collect(Collectors.groupingBy(a ->a.getColor(),//基于颜色分组
                Collectors.averagingInt(a->a.getWeight())))//统计平均重量
                .forEach((k,v)-> System.out.println(k+":"+v));//打印出来
    }

}
