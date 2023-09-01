package org.example;

import com.mongodb.client.*;
import org.bson.Document;

import javax.print.Doc;
import java.util.*;


public class Main
{
    public static void main(String[] args)
    {
        MongoClient client = MongoClients.create("mongodb+srv://Abel:Abel@cluster0.hkrmaa1.mongodb.net/?retryWrites=true&w=majority");
        MongoDatabase db = client.getDatabase("userDB");
        Scanner input = new Scanner(System.in);
        MongoCollection col = db.getCollection("users");

        while (true)
        {
            System.out.println(
                    "\nWelcome to DALL.E\n" +
                            "What would you like to do?\n" +
                            "Enter '1': Signup\n" +
                            "Enter '2': View your details\n" +
                            "Enter '3': Update your address\n" +
                            "Enter '4': Delete your user\n");

            String choice = input.nextLine();
            if (choice.equals("1"))
            {
                Scanner userDeets = new Scanner(System.in);
                System.out.println("Welcome, enter your name: ");
                String name = userDeets.nextLine();
                System.out.println("What is your MINDS ID? ");
                String MindsID = userDeets.nextLine();
                System.out.println("What is your phone number? ");
                String phoneNumber = userDeets.nextLine();
                System.out.println("What is your age? ");
                String age = userDeets.nextLine();
                System.out.println("What is your special power? ");
                String disability = userDeets.nextLine();
                System.out.println("What is your address? ");
                String address = userDeets.nextLine();
                System.out.println("What password for the account? ");
                String pw = userDeets.nextLine();

                Document userDetails = new Document("_id", MindsID).append("Name", name).append("Number", phoneNumber).append("Age", age).append("Disability", disability).append("Address", address).append("Password", pw);

                col.insertOne(userDetails);
                System.out.println("Signup successful\n");
            }

            if (choice.equals("2"))
            {
                Scanner viewInput = new Scanner(System.in);
                System.out.println("What is your MindsID? ");
                String user_id = viewInput.nextLine();
                Document query = new Document("_id", user_id);
                MongoCursor<Document> cursor = col.find(query).cursor();
                while (cursor.hasNext())
                {
                    Document document = cursor.next();
                    String uName = document.getString("Name");
                    String pNumber = document.getString("Number");
                    String age = document.getString("Age");
                    String disable = document.getString("Disability");
                    String homeAddr = document.getString("Address");
                    System.out.println("\nName is: " + uName);
                    System.out.println("Phone number is: " + pNumber);
                    System.out.println("Age: " + age);
                    System.out.println("Super power is: " + disable);
                    System.out.println("Address is: " + homeAddr + "\n");

                }
            }

            if (choice.equals("3"))
            {
                Scanner userUpdt = new Scanner(System.in);
                System.out.println("What is your MindsID? ");
                String MindsID = userUpdt.nextLine();
                Document filter = new Document("_id", MindsID);
                System.out.println("What is your new address? ");
                String new_addr = userUpdt.nextLine();
                Document update = new Document("$set", new Document("Address", new_addr));
                col.updateOne(filter, update);
                System.out.println("\nSuccessful update\n");
            }

            if (choice.equals("4"))
            {
                Scanner userRmv = new Scanner(System.in);
                System.out.println("Enter your Minds ID");
                String RmvUser = userRmv.nextLine();
                Document deleteFilter = new Document("_id", RmvUser);
                col.deleteOne(deleteFilter);
                System.out.println("Successfully deleted Minds user " + RmvUser);
            }
        }
    }
}