
import java.util.GregorianCalendar;
public abstract class Account implements AccountConstants {
    public String number;
    public String name;
    public GregorianCalendar openDate;
    public double balance;

    public abstract int transferTo(Account account, double amount);

    public Account(String number, String name, GregorianCalendar openDate,
                   double balance)
    {
        this.number = number;
        this.name = name;
        this.openDate = openDate;
        this.balance = balance;
    }
    public boolean deposit(double amount)
    {
        balance += amount;
        return true;
    }
    public boolean withdraw(double amount)
    {
        if(balance < amount)
            return false;
        else
        {
            balance -= amount;
            return true;
        }
    }


}
