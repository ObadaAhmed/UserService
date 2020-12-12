import com.google.gson.Gson;
import spark.Spark.*;

import static spark.Spark.*;

public class UserApplication {

        static final int PORT = 3030;
        static userServiceController Controller = new userServiceController();
    public static void main(String[] args) {
        port(PORT);

        path("/user" , ()-> {
            // this route gets all the user for you
            get("" , (req,res)-> {
                res.type("application/json");
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS , new Gson()
                    .toJsonTree(Controller.getUsers())));
            });

            // this route get a user with specific id
            get("/:id" , (req,res) -> {
                res.type("application/json");
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS , new Gson()
                .toJsonTree(Controller.getUser(req.params(":id")))));
            });

            //this route add new user to users
            post("" , (req,res)-> {
               res.type("application/json");
               User user = new Gson().fromJson(req.body() , User.class);
                Controller.addUser(user);
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS));
            }
            );

            //this route edite a particular user with given id
            put("/:id" , (req,res)-> {
                res.type("application/json");
                User toBeEdited = new Gson().fromJson(req.body() , User.class);
                User editedUser = Controller.editUser(toBeEdited , req.params(":id"));

                if (editedUser != null){
                    return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS
                     , new Gson().toJsonTree(editedUser)));
                }else{
                    return new Gson().toJson(new StandardResponse(StatusResponse.ERROR ,
                            new Gson().toJsonTree("User with id " + req.params(":id") + " not found")));
                }

            });

            //this route checks if the user exsists with the given id
            options("/:id" , (req,res)-> {
                res.type("application/json");
                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS ,
                        Controller.userExist(req.params("id")) ? "user available" : "user doesnt exsist" ));
            });

            //this route deletes a user with the given  id
            delete("/:id" , (req,res)-> {
                res.type("application/json");

                Controller.deleteUser(req.params(":id"));

                return new Gson().toJson(new StandardResponse(StatusResponse.SUCCESS ,
                        "user deleted"));

            });
        });
    }





}
