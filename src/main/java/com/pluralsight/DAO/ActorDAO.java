package com.pluralsight.DAO;
//we have to import this because it's in a different package
import com.pluralsight.models.Actor;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Locale;

public class ActorDAO {

    //we need a DAO so we know how to connect to the DB and get connections from the pool
    private DataSource dataSource;

    //constructor so when we create a dao, it has the datasource passed in so it knows how to connect to the DB
    public ActorDAO(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    //this method gets all the actors from the DB
    public ArrayList<Actor> getAllActors(){

        //create an empty list of actors
        ArrayList<Actor> actors = new ArrayList<>();

        String sql = """
                SELECT
                    first_name,
                    last_name
                FROM
                    actor
                """;
        try(
                //get a connection from the pool
                Connection connection = this.dataSource.getConnection();
                //create prepared statement using the passed in connection
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ){
            try(ResultSet results = preparedStatement.executeQuery()){

                while(results.next()){
                    //create the new product from the results returned from the DB
                    Actor newActor = new Actor(
                            results.getString("first_name"),
                            results.getString("last_name")
                    );

                    //add the actor to the list
                    actors.add(newActor);
                }
            }

        }catch(SQLException e){
            System.out.println("Could not retrieve data" + e);
        }

        //return the list
        return actors;

    }
}
