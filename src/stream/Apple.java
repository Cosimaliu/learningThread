package stream;

public class Apple {
    public Integer id;

    public String color;

    public Integer weight;

    public String origin;

    public String firstCategory;

    public String secondCategory;

    public String thirdCategory;

    public String fourCategory;

    public Apple(Integer id, String color, Integer weight, String origin, String firstCategory, String secondCategory, String thirdCategory, String fourCategory) {
        this.id = id;
        this.color = color;
        this.weight = weight;
        this.origin = origin;
        this.firstCategory = firstCategory;
        this.secondCategory = secondCategory;
        this.thirdCategory = thirdCategory;
        this.fourCategory = fourCategory;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getFirstCategory() {
        return firstCategory;
    }

    public void setFirstCategory(String firstCategory) {
        this.firstCategory = firstCategory;
    }

    public String getSecondCategory() {
        return secondCategory;
    }

    public void setSecondCategory(String secondCategory) {
        this.secondCategory = secondCategory;
    }

    public String getThirdCategory() {
        return thirdCategory;
    }

    public void setThirdCategory(String thirdCategory) {
        this.thirdCategory = thirdCategory;
    }

    public String getFourCategory() {
        return fourCategory;
    }

    public void setFourCategory(String fourCategory) {
        this.fourCategory = fourCategory;
    }


    @Override
    public String toString() {
        return "Apple{" +
                "id=" + id +
                ", color='" + color + '\'' +
                ", weight=" + weight +
                ", origin='" + origin + '\'' +
                ", firstCategory='" + firstCategory + '\'' +
                ", secondCategory='" + secondCategory + '\'' +
                ", thirdCategory='" + thirdCategory + '\'' +
                ", fourCategory='" + fourCategory + '\'' +
                '}';
    }
}
