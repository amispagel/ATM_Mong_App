//Alan Mispagel

import java.text.DateFormat;
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
    };
    
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
                if(n.saveCheckingAccounts())
                fillaccountnumberComboBox();
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
                if(n.saveCheckingAccounts())
                fillaccountnumberComboBox();
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
            int result = 0;

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
                    if(fromca.transferTo(toca, Double.parseDouble(amount)) == 0)
                    {
                        JOptionPane.showMessageDialog(this,
                                "$"+ amount + " was transferred to " +toaccount 
                                ,"Transfer successful",
                                JOptionPane.PLAIN_MESSAGE);}
                    if(fromca.transferTo(toca, Double.parseDouble(amount)) == 1)
                    {
                        JOptionPane.showMessageDialog(this,
                                "$"+ amount + " was transferred to " +toaccount
                                + "\n$2.0 transfer fee was applied."
                                ,"Transfer successful",
                                JOptionPane.PLAIN_MESSAGE);}
                    if(fromca.transferTo(toca, Double.parseDouble(amount)) == -1)
                    {
                        JOptionPane.showMessageDialog(this,
                                "Transfer is unsuccessful because balance is "+
                                "less than transfer amount and transfer fee."
                                ,"Unsuccessful transfer",
                                JOptionPane.ERROR_MESSAGE);}
                    if(fromca.transferTo(toca, Double.parseDouble(amount)) == -2)
                    {
                        JOptionPane.showMessageDialog(this,
                                "Transfer is unsuccessful because balance is "+
                                "less than transfer amount" 
                                ,"Unsuccessful transfer",
                                JOptionPane.ERROR_MESSAGE);}
                }
              
                n.updateCheckingAccount(fromca);
                n.updateCheckingAccount(toca);
                if(n.saveCheckingAccounts())
                fillaccountnumberComboBox();
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
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        accountnumberComboBox = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        opendateTextField = new javax.swing.JTextField();
        customernameTextField = new javax.swing.JTextField();
        balanceTextField = new javax.swing.JTextField();
        depositButton = new javax.swing.JButton();
        withdrawButton = new javax.swing.JButton();
        transfertoButton = new javax.swing.JButton();
        exitButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Bank Account Application");
        setResizable(false);

        jLabel1.setText("Account Number:");

        accountnumberComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                accountnumberComboBoxActionPerformed(evt);
            }
        });

        jLabel2.setText("Open Date:");

        jLabel3.setText("Customer Name:");

        jLabel4.setText("Balance:");

        opendateTextField.setEditable(false);

        customernameTextField.setEditable(false);

        balanceTextField.setEditable(false);

        depositButton.setMnemonic('d');
        depositButton.setText("Deposit");

        depositButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                depositButtonActionPerformed(evt);

            }
        });

        withdrawButton.setMnemonic('w');
        withdrawButton.setText("Withdraw");
        withdrawButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                withdrawButtonActionPerformed(evt);
            }
        });

        transfertoButton.setMnemonic('t');
        transfertoButton.setText("Transfer To");
        transfertoButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                transfertoButtonActionPerformed(evt);
            }
        });

        exitButton.setMnemonic('x');
        exitButton.setText("Exit");
        exitButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                exitButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(13, 13, 13)
                        .addComponent(customernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(transfertoButton))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(52, 52, 52)
                        .addComponent(balanceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(6, 6, 6)
                        .addComponent(exitButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addGap(37, 37, 37)
                                .addComponent(opendateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(10, 10, 10)
                                .addComponent(accountnumberComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(6, 6, 6)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(depositButton)
                            .addComponent(withdrawButton))))
                .addContainerGap(24, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(11, 11, 11)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(accountnumberComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(depositButton))))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(opendateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(withdrawButton))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel3))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(customernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(transfertoButton))
                .addGap(3, 3, 3)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel4))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(balanceTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(exitButton)))
        );

        pack();
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
    private javax.swing.JComboBox accountnumberComboBox;
    private javax.swing.JTextField balanceTextField;
    private javax.swing.JTextField customernameTextField;
    private javax.swing.JButton depositButton;
    private javax.swing.JButton exitButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JTextField opendateTextField;
    private javax.swing.JButton transfertoButton;
    private javax.swing.JButton withdrawButton;
    // End of variables declaration//GEN-END:variables
}
