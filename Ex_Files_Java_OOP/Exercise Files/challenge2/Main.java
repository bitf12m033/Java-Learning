public class Main {

    public static void main(String[] args) {
        BankAccount account = new BankAccount(1000, "1234567890", "John Doe");
        account.deposit(500);
        account.withdraw(2200);
        System.out.println("Account balance: " + account.getBalance());
    }

}
