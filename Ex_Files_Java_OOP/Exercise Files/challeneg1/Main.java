
public class Main {

    public static void main(String[] args) {
        Employee employee1 = new Employee("John Doe", 30, 50000, "New York");
        Employee employee2 = new Employee("Jane Doe", 25, 40000, "Los Angeles");

        employee1.raiseSalary(1000);

        System.out.println(employee1.getName() + " has a salary of " + employee1.getSalary());
        System.out.println(employee2.getName() + " has a salary of " + employee2.getSalary());
    }
    
}
