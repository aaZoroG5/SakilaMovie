package com.pluralsight;

import com.mysql.cj.protocol.Resultset;
import org.apache.commons.dbcp2.BasicDataSource;

import javax.xml.transform.Result;
import java.sql.*;
import java.util.Scanner;

public class App {

    public static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        if(args.length != 2){
            System.out.println("Application needs two arguments to run: A username and a password for the DB");
            System.exit(1);//You have to edit App configuration and add the name of the DB and password you used
        }

        //get the username and password from args[]
        String username = args[0];
        String password = args[1];

        //create a try/catch statement using resources
        try (BasicDataSource basicDataSource = new BasicDataSource();) {
            // connect to the DB by getting server location, username, and password
            //sets it's configuration
            basicDataSource.setUrl("jdbc:mysql://localhost:3306/sakila");
            basicDataSource.setUsername(username);
            basicDataSource.setPassword(password);

            while(true){
                System.out.println("""
                Search Options
                1) First Name
                2) Last Name
                3) Movies by Actor
                4) Exit
                """);

                System.out.print("Choose an option: ");
                int input = scanner.nextInt();
                scanner.nextLine();

                switch(input){
                    case 1:
                        searchByFirstName(basicDataSource);
                        break;
                    case 2:
                        searchByLastName(basicDataSource);
                        break;
                    case 3:
                        moviesByFullName(basicDataSource);
                        break;
                    case 4:
                        System.out.println("Goodbye!");
                        System.exit(0);
                    default:
                        System.out.println("Invalid choice");
                }
            }

        }catch(SQLException e){
            System.out.println("Could not connect to DB");
            System.exit(1);
        }

    }

    public static void displayAll()
    public static void searchByFirstName(BasicDataSource basicDataSource){

        //prompt for user input
        System.out.print("Enter an actor's first name: ");
        String firstName = scanner.nextLine().trim();

        //create query for first names
        String sql = """
                SELECT
                    first_name,
                    last_name
                FROM
                    actor
                WHERE
                    first_name = ?
                """;
        try(
                //get connection from the pool
                Connection connection = basicDataSource.getConnection();
                //call the query
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ){
            //sets the name for the ? in the query
            preparedStatement.setString(1, firstName);

            //this works the same as clicking run on SQL workbench
            try( ResultSet results = preparedStatement.executeQuery()){
                if(!results.isBeforeFirst()){//this method checks if cursor is currently positioned BEFORE the first row of the result set
                    System.out.println("No actors found with that first name");
                    return;
                }//NOTE: we don't need a catch statement because it's nested inside another try/catch statement
                printResults(results);
            }

        }catch(SQLException e){
            System.out.println("Could not retrieve data");
            System.exit(1);
        }

    }

    public static void searchByLastName(BasicDataSource basicDataSource){

        //prompt for user input
        //create the query for last names
        //create try/catch
        //get connection from pool
        //call the query with prepared statement
        //set the name for the ? value
        //created nested try/catch that runs resultSet
        System.out.print("Enter an actor's last name: ");
        String lastName = scanner.nextLine().trim();

        String sql = """
                SELECT
                    first_name,
                    last_name
                FROM
                    actor
                WHERE
                    last_name = ?
                """;
        try(
                Connection connection = basicDataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ){

            preparedStatement.setString(1, lastName);

            try(ResultSet results = preparedStatement.executeQuery()){
                if(!results.isBeforeFirst()){//this method checks if cursor is currently positioned BEFORE the first row of the result set
                    System.out.println("No actors found with that last name");
                    return;
                }
                printResults(results);
            }

        }catch(SQLException e){
            System.out.println("Could not retrieve data");
            System.exit(1);
        }

    }

    public static void moviesByFullName(BasicDataSource basicDataSource){

        //prompt for user input
        //create the query for last names
        //create try/catch
        //get connection from pool
        //call the query with prepared statement
        //set the name for the ? value
        //created nested try/catch that runs resultSet
        System.out.print("Enter the actors first name: ");
        String firstName = scanner.nextLine().trim();
        System.out.print("Enter the actors last name: ");
        String lastName = scanner.nextLine().trim();

        String sql = """
                SELECT
                    A.first_name,
                    A.last_name,
                    F.title
                FROM
                    film F
                    JOIN film_actor FA ON (F.film_id = FA.actor_id)
                    JOIN actor A ON (FA.film_id = A.actor_id)
                WHERE
                    A.first_name = ? AND A.last_name = ?
                """;

        try(
                Connection connection = basicDataSource.getConnection();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

                ){
            preparedStatement.setString(1, firstName);
            preparedStatement.setString(2, lastName);

            try(ResultSet results = preparedStatement.executeQuery()){
                if(!results.isBeforeFirst()){//this method checks if cursor is currently positioned BEFORE the first row of the result set
                    System.out.println("No movies found with that actor");
                    return;
                }
                printResults(results);
            }

        }catch(SQLException e){
            System.out.println("Could not retrieve data");
        }


    }
    //this method will be used in the display methods to print the results to the screen
    public static void printResults(ResultSet results) throws SQLException {//have to add a throw statement for the .getMetaData()
        //get the meta data so we have access to the field names
        ResultSetMetaData metaData = results.getMetaData();

        //get the number of the rows returned
        int columnCount = metaData.getColumnCount();

        //this is looping over all the results from the DB
        while(results.next()){

            //loop over each column in the rwo and display the data
            for (int i = 1; i <= columnCount; i++) {
                //gets the column name for the current record
                String columnName = metaData.getColumnName(i);
                //get the current column value
                String value = results.getString(i);
                //print out the column name and column value
                System.out.println(columnName + ": " + value + " ");
            }

            System.out.println();

        }

    }

}
