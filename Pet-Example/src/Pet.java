public class Pet {
    private String type = "";
    private String name = "";
    private int age;
    private String color = "";

    public Pet(){}

    public Pet(String type, String name, int age, String color) {
        this.type = type;
        this.name = name;
        this.age = age;
        this.color = color;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String toString(){
        return "\n Pet Type: " + this.type + " \n Name: " + this.name + " \n Age: " + this.age + " \n Color: " + this.color;
    }
}
