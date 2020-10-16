public class Person {
    int age;
    String name;
    Person(int age, String name) {
        this.name = name;
        this.age = age;
    }

    public int getAge() {
        return this.age;
    }

    public String getName() {
        return this.name;
    }
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Person) {
            Person person = (Person)obj;
            return age == person.getAge() && name.equals(person.getName());
        }
        return false;
    }
}
