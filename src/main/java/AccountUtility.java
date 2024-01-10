//Alan Mispagel

import com.mongodb.client.*;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import java.util.GregorianCalendar;
import java.text.ParseException;
import java.util.*;

public class AccountUtility  {

    private ArrayList<CheckingAccount> accounts = null;
    // Replace the placeholder with your MongoDB deployment's connection string
    String uri = "mongodb+srv://user1:drobz4o3zFxf9CD3@cluster0.9trto2g.mongodb.net/?retryWrites=true&w=majority";


    public AccountUtility() throws ParseException
    {
        accounts = this.getCheckingAccounts();
    }

    public ArrayList<CheckingAccount> getCheckingAccounts()throws ParseException
    {   //copies file into an array list **MUST have exactly 4 parts**

        // if the accounts file has already been read, don't read it again
        if (accounts != null)
            return accounts;
        else
        {
            accounts = new ArrayList<>();

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
                    } }}}
        return accounts;
    }

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
            if (p.number.equals(toac)){
                is = true;
                break;}
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

    }

public boolean saveCheckingAccounts()
{    //outputs the arraylist to txt file
// Replace the placeholder with your MongoDB deployment's connection string
    String uri = "mongodb+srv://user1:drobz4o3zFxf9CD3@cluster0.9trto2g.mongodb.net/?retryWrites=true&w=majority";

    try (MongoClient mongoClient = MongoClients.create(uri)) {
        MongoDatabase database = mongoClient.getDatabase("ATM_DB");
        MongoCollection<Document> collection = database.getCollection("Customers");

        for (CheckingAccount p : accounts){
            //collection.updateOne(Filters.eq("id", p.number), Updates.set("amount", p.balance));

            // Define the update operation
            Document filter = new Document("id", Integer.parseInt(p.number)); // Replace with your filter criteria
            Document update = new Document("$set", new Document("amount", p.balance));

            // Perform the update operation and retrieve the UpdateResult
            UpdateResult updateResult = collection.updateOne(filter, update);

            // Access information from the UpdateResult
            System.out.println("Matched documents: " + updateResult.getMatchedCount());
            System.out.println("Modified documents: " + updateResult.getModifiedCount());
            System.out.println("Update acknowledged: " + updateResult.wasAcknowledged());

        }
        System.out.println("Collection updated successfully in DB.");

        return true;

    } catch (Exception e) {
        // Handle exceptions
        System.out.println("Document update failed, issue with updating Mongo.");
        e.printStackTrace();
        return false;
    }
}
}
