
abstract class Animal {

    private String name;
    private int age;


    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

 //getter method
    public String getName() {
        return name;
    }
//setter method
    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    // Abstract method
    public abstract void makeSound();

    public void eat() {
        System.out.println(name + " is eating.");
    }
}


class Dog extends Animal {
    private String breed;


    public Dog(String name, int age) {
        super(name, age);
        this.breed = "Unknown";
    }

    public Dog(String name, int age, String breed) {
        super(name, age);
        this.breed = breed;
    }

    @Override
    public void makeSound() {
        System.out.println(getName() + " says: Woof! Woof!");
    }

    public void fetch() {
        System.out.println(getName() + " is fetching the ball.");
    }

    // Getter
    public String getBreed() {
        return breed;
    }
}

class Cat extends Animal {
    private String color;

    // Constructor
    public Cat(String name, int age, String color) {
        super(name, age);
        this.color = color;
    }

    @Override
    public void makeSound() {
        System.out.println(getName() + " says: Meow!");
    }

    public void scratch() {
        System.out.println(getName() + " is scratching the furniture.");
    }

    // Getter
    public String getColor() {
        return color;
    }
}


public class task1classhiearchy {
    public static void main(String[] args) {
        // Creating objects (Object creation)
        Dog dog1 = new Dog("Buddy", 3, "Golden Retriever");
        Cat cat1 = new Cat("Whiskers", 2, "White");

        // Demonstrating polymorphism
        Animal a1 = dog1; 
        Animal a2 = cat1;

        // Calling overridden methods (Runtime Polymorphism)
        a1.makeSound();
        a2.makeSound();

        
        dog1.fetch();
        cat1.scratch();

        System.out.println(dog1.getName() + " is a " + dog1.getBreed() + ".");
        System.out.println(cat1.getName() + " is " + cat1.getColor() + " in color.");

        a1.eat();
        a2.eat();
    }
}
