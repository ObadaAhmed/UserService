import com.google.gson.Gson;
import spark.Request;
import spark.Response;
import spark.Spark.*;

import java.util.List;

import static spark.Spark.*;

public class UserApplication {

        static final int PORT = 3030;
    public static void main(String[] args) {
        port(PORT);
        options("/*",
                (request, response) -> {

                    String accessControlRequestHeaders = request
                            .headers("Access-Control-Request-Headers");
                    if (accessControlRequestHeaders != null) {
                        response.header("Access-Control-Allow-Headers",
                                accessControlRequestHeaders);
                    }

                    String accessControlRequestMethod = request
                            .headers("Access-Control-Request-Method");
                    if (accessControlRequestMethod != null) {
                        response.header("Access-Control-Allow-Methods",
                                accessControlRequestMethod);
                    }

                    return "OK";
                });
        before((request, response) -> response.header("Access-Control-Allow-Origin", "*"));
        path("/user" , ()-> {

            // this route gets all the user for you
            get("" , (req,res)-> {
                res.type("application/json");

                List<User> Users = DB_Operations.getAllUsers();
                if (Users != null){
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS , new Gson()
                    .toJsonTree(Users)));
                } else {
                return new Gson().toJson(new StandardResponse(StatusResponse.ERROR , "there is no users"));
                }
            });

            // this route get a user with specific id
            get("/:id" , (req,res) -> {
                res.type("application/json");
                User user = DB_Operations.getSingleUser(Integer.parseInt(req.params("id")));
                if ( user != null){
                    return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS , new Gson()
                            .toJsonTree(user)));
                }else{
                    return new Gson().toJson(new StandardResponse(StatusResponse.ERROR , "User not found"));
                }
            });

            //this route add new user to users
             post("" , (req,res)-> {
               res.type("application/json");
               User user = new Gson().fromJson(req.body() , User.class);
              if (DB_Operations.addNewUser(user)) {
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
              }else {
                  return new Gson().toJson(new StandardResponse(StatusResponse.ERROR , "id already exsits"));
              }

            }
            );

            //this route edite a particular user with given id
            put("/:id" , (Request req, Response res) -> {
                res.type("application/json");
                User toBeEdited = new Gson().fromJson(req.body() , User.class);
                boolean flag = DB_Operations.updateUser(toBeEdited , Integer.parseInt(req.params(":id")));

                if (flag){
                    return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS
                     , "user edited"));
                }else{
                    return new Gson().toJson(new StandardResponse(StatusResponse.ERROR ,
                            new Gson().toJsonTree("User with id " + req.params(":id") + " not found")));
                }

            });

            //this route checks if the user exsists with the given id
            options("/:id" , (req,res)-> {
                res.type("application/json");
                    if (DB_Operations.getSingleUser(Integer.parseInt(req.params("id"))) != null){
                        return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS , "User available"));
                    }else {
                        return new Gson().toJson(new StandardResponse(StatusResponse.ERROR , "User not available"));
                    }
                   });

            //this route deletes a user with the given  id
            delete("/:id" , (req,res)-> {
                res.type("application/json");

               if (DB_Operations.deleteUser(Integer.parseInt(req.params(":id")))){
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS ,
                        "user deleted"));
               }else{
                   return new Gson().toJson(new StandardResponse(StatusResponse.ERROR ,
                           "User with id not found"));
               }
            });
        });
    }





}
