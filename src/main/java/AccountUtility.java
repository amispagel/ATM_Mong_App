//Alan Mispagel

import com.mongodb.client.*;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import java.util.GregorianCalendar;

import java.io.*;
import java.nio.file.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;
import static java.util.Calendar.*;

public class AccountUtility  {

    private ArrayList<CheckingAccount> accounts = null;
    //private File accountsFile = null;
    //private final String FIELD_SEP = "<>";

    public AccountUtility() throws ParseException
    {
        // Replace the placeholder with your MongoDB deployment's connection string
        String uri = "mongodb+srv://user1:drobz4o3zFxf9CD3@cluster0.9trto2g.mongodb.net/?retryWrites=true&w=majority";

        //declares file
        //accountsFile = new File("accounts.txt");

        accounts = this.getCheckingAccounts();
    }

    public ArrayList<CheckingAccount> getCheckingAccounts()throws ParseException
    {   //copies file into an array list **MUST have exactly 4 parts**

        // if the accounts file has already been read, don't read it again
        if (accounts != null)
            return accounts;
        accounts = new ArrayList<>();
// Replace the placeholder with your MongoDB deployment's connection string
        String uri = "mongodb+srv://user1:drobz4o3zFxf9CD3@cluster0.9trto2g.mongodb.net/?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("ATM_DB");
            MongoCollection<Document> collection = database.getCollection("Customers");

            // Query to fetch all documents in the collection
            MongoCursor<Document> cursor = collection.find().iterator();

            // Iterate through the cursor to retrieve all documents
            if (!cursor.hasNext()) {
                System.out.println("Cursor is empty.");
            } else {

            while (cursor.hasNext()) {
                Document document = cursor.next(); //fetch row in the database
                // Read a particular element (field) from the document
                int num = document.getInteger("id"); // Replace "fieldName" with the actual field name
                String number = Integer.toString(num);
                String name = document.getString("name"); // Replace "fieldName" with the actual field name
                String dateString = document.getString("date"); // Replace "fieldName" with the actual field name
                String[] date = dateString.split("/");
                GregorianCalendar openDate = new GregorianCalendar(
                        Integer.parseInt(date[0]),
                        Integer.parseInt(date[1]) - 1,
                        Integer.parseInt(date[2]));
                Double balance = (Double) document.get("amount"); // Replace "fieldName" with the actual field name
                CheckingAccount ca = new CheckingAccount(
                        number, name, openDate, balance);
                accounts.add(ca);
            } }
        return accounts;
        /*
        //Reading from file ===DEPRECATED - now using MongoDB===
        if (accountsFile.isFile())  // prevent the FileNotFoundException
        {

            try (BufferedReader in =
                     new BufferedReader(
                     new FileReader(accountsFile)))
            {
                // read all accounts stored in the file
                // into the array list
                String line = in.readLine();
                while(line != null)
                {
                    String[] columns = line.split(FIELD_SEP);
                    String number = columns[0];
                    String name = columns[1];
                        String[] date = columns[2].split("/");
                        GregorianCalendar openDate = new GregorianCalendar(
                                        Integer.parseInt(date[0]),
                                        Integer.parseInt(date[1]) - 1,
                                        Integer.parseInt(date[2]));
                    String balance = columns[3];

                    CheckingAccount ca = new CheckingAccount(
                        number, name, openDate, Double.parseDouble(balance));

                    accounts.add(ca);

                    line = in.readLine(); //read the next line
                }
                in.close();
            }
            catch(IOException e)
            {
                System.out.println(e);
                return null;
            }*/

    }}

    public CheckingAccount getCheckingAccount(String code)
    { //access a particular account in the array list
        for (CheckingAccount p : accounts)
        {
            if (p.number.equals(code))
                return p;
        }
        return null;
    }
    public boolean Accountexists(String toac)
    {
        boolean is = false;
        for (CheckingAccount p : accounts)
        {
            if (p.number.equals(toac))
                is = true;
        }
        return is;
    }

    public void updateCheckingAccount(CheckingAccount newCheckingAccount) { // get the old product and remove it, as well as add the parameter
        CheckingAccount oldCheckingAccount =
                this.getCheckingAccount(newCheckingAccount.number);
        int i = accounts.indexOf(oldCheckingAccount);
        accounts.remove(i);

        // add the updated account
        accounts.add(i, newCheckingAccount);

// Replace the placeholder with your MongoDB deployment's connection string
        String uri = "mongodb+srv://user1:drobz4o3zFxf9CD3@cluster0.9trto2g.mongodb.net/?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("ATM_DB");
            MongoCollection<Document> collection = database.getCollection("Customers");
            UpdateResult updateResult = collection.updateOne(Filters.eq("id", newCheckingAccount.number),
            Updates.set("amount", newCheckingAccount.balance));
            // Update a single document in the collection
            //collection.updateOne(Filters.eq("id", newCheckingAccount.number), Updates.set("amount", newCheckingAccount.balance));
            System.out.println("Document updated successfully.");
        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
        }
    }
    public boolean saveCheckingAccounts()
    {    //outputs the arraylist to txt file
// Replace the placeholder with your MongoDB deployment's connection string
        String uri = "mongodb+srv://user1:drobz4o3zFxf9CD3@cluster0.9trto2g.mongodb.net/?retryWrites=true&w=majority";

        try (MongoClient mongoClient = MongoClients.create(uri)) {
            MongoDatabase database = mongoClient.getDatabase("ATM_DB");
            MongoCollection<Document> collection = database.getCollection("Customers");


            //public CheckingAccount(String number, String name,
            //GregorianCalendar openDate, double balance)
            for (CheckingAccount p : accounts){
                //collection.updateOne(Filters.eq("id", p.number), Updates.set("amount", p.balance));
                UpdateResult updateResult = collection.updateOne(Filters.eq("id", p.number),
                        Updates.set("amount", p.balance));
                System.out.println("Document updated successfully.");
            }
            return true;

        } catch (Exception e) {
            // Handle exceptions
            e.printStackTrace();
            return false;
        }
        /*Writes to file===DEPRECATED - now using MongoDB===

        try (PrintWriter out = new PrintWriter(
                           new BufferedWriter(
                           new FileWriter(accountsFile))))
        {
        for (CheckingAccount p : accounts){
            out.print(p.number + FIELD_SEP);
            out.print(p.name + FIELD_SEP);
            out.print(p.openDate.get(YEAR) + "/" +
                     (p.openDate.get(MONTH)+1) + "/" +
                      p.openDate.get(DAY_OF_MONTH) + FIELD_SEP);
            out.print(String.valueOf(p.balance));
            out.print("\n");}
            
        return true;
        }
        catch(IOException e)
        {
            System.out.println(e);
            return false;
        }

         */
    }
} 
