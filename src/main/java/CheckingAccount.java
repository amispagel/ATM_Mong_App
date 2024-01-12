//Alan Mispagel

import java.util.GregorianCalendar;

public class CheckingAccount extends Account
{

    public CheckingAccount(String number, String name,
                           GregorianCalendar openDate, double balance)
    {
        super(number, name, openDate, balance);
    }
    public int transferTo(Account toac, double amount)
    {
        if(this.balance < CHECKING_BALANCE_THRESHOLD){
            if(this.withdraw(amount + TRANSFER_FEE)){
                toac.deposit(amount);
                return 1;}
            else
                return -1;}
        else{
            if(this.withdraw(amount)){
                toac.deposit(amount);
                return 0;}
            else
                return -2;
        }
    }

}
