//Alan Mispagel

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import static java.util.Calendar.DAY_OF_MONTH;
import static java.util.Calendar.MONTH;
import static java.util.Calendar.YEAR;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;

public class AccountApp extends javax.swing.JFrame {

    /* Creates new form ProjectFrame*/
    public AccountApp() {
        initComponents();
        fillaccountnumberComboBox();
    }
    public static boolean isValidInteger(String s) {
        try {
            Integer.parseInt(s);
            int i = Integer.parseInt(s);
            return i > 0;
        }catch(NumberFormatException e) {
            return false;
        }
    }

    public void fillaccountnumberComboBox(){
        try{
            AccountUtility n = new AccountUtility();
            ArrayList<CheckingAccount> accounts =
                    new ArrayList<CheckingAccount>();
            accounts = n.getCheckingAccounts();
            fillform(accounts.get(0).number);
            for(CheckingAccount ca : accounts)
            {
                accountnumberComboBox.addItem(ca.number);
            }
        }catch (ParseException ex) {
            Logger.getLogger(AccountApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void fillform(String selectedaccount){
        try {
            AccountUtility n = new AccountUtility();
            CheckingAccount ca =  n.getCheckingAccount(selectedaccount);
            String formattedDate = ca.openDate.get(YEAR) + "/" +
                    (ca.openDate.get(MONTH)+1) + "/" +
                    ca.openDate.get(DAY_OF_MONTH);
            opendateTextField.setText(formattedDate);
            customernameTextField.setText(ca.name);
            NumberFormat currency = NumberFormat.getCurrencyInstance();
            balanceTextField.setText(currency.format(ca.balance));
        }catch (ParseException ex) {
            Logger.getLogger(AccountApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void makedeposit(String amount){

        try {
            String account = (String)accountnumberComboBox.getSelectedItem();
            AccountUtility n = new AccountUtility();
            CheckingAccount ca =  n.getCheckingAccount(account);
            if(ca.deposit(Double.parseDouble(amount))){
                n.updateCheckingAccount(ca);
                if(n.saveCheckingAccounts(ca)){
                    fillform(account);
                    JOptionPane.showMessageDialog(this, "Account(s) updated.",
                            "Successful transaction", JOptionPane.ERROR_MESSAGE);}
                else
                    JOptionPane.showMessageDialog(this, "There was a problem"
                                    + "with the stream.",
                            "Invalid deposit", JOptionPane.ERROR_MESSAGE);
            }
        }catch (ParseException ex) {
            Logger.getLogger(AccountApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void makewithdraw(String amount){

        try {
            String account = (String)accountnumberComboBox.getSelectedItem();
            AccountUtility n = new AccountUtility();
            CheckingAccount ca =  n.getCheckingAccount(account);
            if(ca.withdraw(Double.parseDouble(amount))){
                n.updateCheckingAccount(ca);
                if(n.saveCheckingAccounts(ca)){
                    fillform(account);
                    JOptionPane.showMessageDialog(this, "Account(s) updated.",
                            "Successful transaction", JOptionPane.ERROR_MESSAGE);}
                else
                    JOptionPane.showMessageDialog(this, "There was a problem"
                                    + "with the stream.",
                            "Invalid withdrawl", JOptionPane.ERROR_MESSAGE);
            }
            else
                JOptionPane.showMessageDialog(this, "Withdrawl was " +
                                "unsuccessful because balance is less than withdrawl " +
                                "amount",
                        "Unsuccessful Withdrawl", JOptionPane.ERROR_MESSAGE);
        }catch (ParseException ex) {
            Logger.getLogger(AccountApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public boolean isValidAccount(String toac)
    {
        boolean value = false;
        try {
            String account = (String)accountnumberComboBox.getSelectedItem();
            AccountUtility n = new AccountUtility();

            if(!toac.matches(account) && n.Accountexists(toac) )
                return true;

        }catch (ParseException ex) {
            Logger.getLogger(AccountApp.class.getName()).log(Level.SEVERE, null, ex);
        }return value;
    }
    public void maketransfer(String toaccount){

        try {
            String amount = "";
            String account = (String)accountnumberComboBox.getSelectedItem();
            AccountUtility n = new AccountUtility();
            CheckingAccount fromca =  n.getCheckingAccount(account);


            if(isValidAccount(toaccount))
            {
                CheckingAccount toca =  n.getCheckingAccount(toaccount);
                amount = JOptionPane.showInputDialog(this,
                        "Enter a deposit amount for account " + account,
                        "Deposit to " + account, JOptionPane.PLAIN_MESSAGE);
                if(amount.isEmpty() || !isValidInteger(amount)){
                    JOptionPane.showMessageDialog(this,
                            "Invalid transfer amount:" +
                                    amount, "Invalid transfer",
                            JOptionPane.ERROR_MESSAGE);}
                else{
                    int transfer = fromca.transferTo(toca, Double.parseDouble(amount));
                    if(transfer == 0)
                    {
                        JOptionPane.showMessageDialog(this,
                                "$"+ amount + " was transferred to " +toaccount
                                ,"Transfer successful",
                                JOptionPane.PLAIN_MESSAGE);}
                    if(transfer == 1)
                    {
                        JOptionPane.showMessageDialog(this,
                                "$"+ amount + " was transferred to " +toaccount
                                        + "\n$2 transfer fee was applied."
                                ,"Transfer successful",
                                JOptionPane.PLAIN_MESSAGE);}
                    if(transfer == -1)
                    {
                        JOptionPane.showMessageDialog(this,
                                "Transfer is unsuccessful because balance is "+
                                        "less than transfer amount and transfer fee."
                                ,"Unsuccessful transfer",
                                JOptionPane.ERROR_MESSAGE);}
                    if(transfer == -2)
                    {
                        JOptionPane.showMessageDialog(this,
                                "Transfer is unsuccessful because balance is "+
                                        "less than balance threshold"
                                ,"Unsuccessful transfer",
                                JOptionPane.ERROR_MESSAGE);}
                }

                n.updateCheckingAccount(fromca);
                n.updateCheckingAccount(toca);
                if(n.saveCheckingAccounts(fromca, toca))
                    fillform(account);
                else
                    JOptionPane.showMessageDialog(this, "There was a problem"
                                    + "with the stream.",
                            "Invalid withdrawl", JOptionPane.ERROR_MESSAGE);
            }
        }catch (ParseException ex) {
            Logger.getLogger(AccountApp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    // Generated using JFormDesigner Evaluation license - Megaman
    private void initComponents() {
        jLabel1 = new JLabel();
        accountnumberComboBox = new JComboBox<>();
        jLabel2 = new JLabel();
        jLabel3 = new JLabel();
        jLabel4 = new JLabel();
        opendateTextField = new JTextField();
        customernameTextField = new JTextField();
        balanceTextField = new JTextField();
        depositButton = new JButton();
        withdrawButton = new JButton();
        transfertoButton = new JButton();
        exitButton = new JButton();

        //======== this ========
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Bank Account Application");
        setResizable(false);
        var contentPane = getContentPane();

        //---- jLabel1 ----
        jLabel1.setText("Account Number:");

        //---- accountnumberComboBox ----
        accountnumberComboBox.setModel(new DefaultComboBoxModel<>(new String[] {

        }));
        accountnumberComboBox.addActionListener(e -> accountnumberComboBoxActionPerformed(e));

        //---- jLabel2 ----
        jLabel2.setText("Open Date:");

        //---- jLabel3 ----
        jLabel3.setText("Customer Name:");

        //---- jLabel4 ----
        jLabel4.setText("Balance:");

        //---- opendateTextField ----
        opendateTextField.setEditable(false);

        //---- customernameTextField ----
        customernameTextField.setEditable(false);

        //---- balanceTextField ----
        balanceTextField.setEditable(false);

        //---- depositButton ----
        depositButton.setMnemonic('d');
        depositButton.setText("Deposit");
        depositButton.addActionListener(e -> depositButtonActionPerformed(e));

        //---- withdrawButton ----
        withdrawButton.setMnemonic('w');
        withdrawButton.setText("Withdraw");
        withdrawButton.addActionListener(e -> withdrawButtonActionPerformed(e));

        //---- transfertoButton ----
        transfertoButton.setMnemonic('t');
        transfertoButton.setText("Transfer To");
        transfertoButton.addActionListener(e -> transfertoButtonActionPerformed(e));

        //---- exitButton ----
        exitButton.setMnemonic('x');
        exitButton.setText("Exit");
        exitButton.addActionListener(e -> exitButtonActionPerformed(e));

        GroupLayout contentPaneLayout = new GroupLayout(contentPane);
        contentPane.setLayout(contentPaneLayout);
        contentPaneLayout.setHorizontalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGap(10, 10, 10)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addGap(13, 13, 13)
                            .addComponent(customernameTextField, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
                            .addGap(6, 6, 6)
                            .addComponent(transfertoButton))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addGap(52, 52, 52)
                            .addComponent(balanceTextField, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE)
                            .addGap(6, 6, 6)
                            .addComponent(exitButton))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addComponent(jLabel2)
                                    .addGap(37, 37, 37)
                                    .addComponent(opendateTextField, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE))
                                .addGroup(contentPaneLayout.createSequentialGroup()
                                    .addComponent(jLabel1)
                                    .addGap(10, 10, 10)
                                    .addComponent(accountnumberComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
                            .addGap(6, 6, 6)
                            .addGroup(contentPaneLayout.createParallelGroup()
                                .addComponent(depositButton)
                                .addComponent(withdrawButton))))
                    .addContainerGap(24, Short.MAX_VALUE))
        );
        contentPaneLayout.setVerticalGroup(
            contentPaneLayout.createParallelGroup()
                .addGroup(contentPaneLayout.createSequentialGroup()
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(15, 15, 15)
                            .addComponent(jLabel1))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(11, 11, 11)
                            .addGroup(contentPaneLayout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                                .addComponent(accountnumberComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                                .addComponent(depositButton))))
                    .addGap(6, 6, 6)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(4, 4, 4)
                            .addComponent(jLabel2))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(1, 1, 1)
                            .addComponent(opendateTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addComponent(withdrawButton))
                    .addGap(6, 6, 6)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(4, 4, 4)
                            .addComponent(jLabel3))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(1, 1, 1)
                            .addComponent(customernameTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addComponent(transfertoButton))
                    .addGap(3, 3, 3)
                    .addGroup(contentPaneLayout.createParallelGroup()
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(4, 4, 4)
                            .addComponent(jLabel4))
                        .addGroup(contentPaneLayout.createSequentialGroup()
                            .addGap(1, 1, 1)
                            .addComponent(balanceTextField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
                        .addComponent(exitButton)))
        );
        pack();
        setLocationRelativeTo(getOwner());
    }// </editor-fold>//GEN-END:initComponents

    private void exitButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitButtonActionPerformed
        System.exit(0);
    }//GEN-LAST:event_exitButtonActionPerformed

    private void depositButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_depositButtonActionPerformed

        String account = (String)accountnumberComboBox.getSelectedItem();
        String amount = JOptionPane.showInputDialog(this,
                "Enter a deposit amount for account " + account,
                "Deposit to " + account, JOptionPane.PLAIN_MESSAGE);
        if(amount.isEmpty() || !isValidInteger(amount)){
            JOptionPane.showMessageDialog(this, "Invalid deposit amount:" +
                    amount, "Invalid deposit", JOptionPane.ERROR_MESSAGE);}
        else
            makedeposit(amount);
    }//GEN-LAST:event_depositButtonActionPerformed

    private void accountnumberComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_accountnumberComboBoxActionPerformed
        String account = (String)accountnumberComboBox.getSelectedItem();
        fillform(account);

    }//GEN-LAST:event_accountnumberComboBoxActionPerformed

    private void withdrawButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_withdrawButtonActionPerformed
        String account = (String)accountnumberComboBox.getSelectedItem();
        String amount = JOptionPane.showInputDialog(this,
                "Enter a withdrawl amount for account " + account,
                "Deposit to " + account, JOptionPane.PLAIN_MESSAGE);
        if(amount.isEmpty() || !isValidInteger(amount)){
            JOptionPane.showMessageDialog(this, "Invalid withdrawl amount:" +
                    amount, "Invalid withdrawl", JOptionPane.ERROR_MESSAGE);}
        else
            makewithdraw(amount);
    }//GEN-LAST:event_withdrawButtonActionPerformed

    private void transfertoButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_transfertoButtonActionPerformed
        String account = (String)accountnumberComboBox.getSelectedItem();
        String toca = JOptionPane.showInputDialog(this,
                "Transfer from account " + account,
                "Enter the beneficiary account number"
                        + account, JOptionPane.PLAIN_MESSAGE);
        if(toca.isEmpty() || !isValidInteger(toca)){
            JOptionPane.showMessageDialog(this,
                    "Invalid beneficiary account number:" +
                            toca, "Invalid transfer", JOptionPane.ERROR_MESSAGE);}
        else
            maketransfer(toca);
    }//GEN-LAST:event_transfertoButtonActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(AccountApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AccountApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AccountApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AccountApp.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {

                AccountApp frame = new AccountApp();
                frame.setVisible(true);
                frame.setLocationRelativeTo(null);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // Generated using JFormDesigner Evaluation license - Megaman
    private JLabel jLabel1;
    private JComboBox<String> accountnumberComboBox;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JTextField opendateTextField;
    private JTextField customernameTextField;
    private JTextField balanceTextField;
    private JButton depositButton;
    private JButton withdrawButton;
    private JButton transfertoButton;
    private JButton exitButton;
    // End of variables declaration//GEN-END:variables
}
