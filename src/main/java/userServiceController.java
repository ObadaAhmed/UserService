import org.omg.CORBA.UserException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class userServiceController implements userService {

    static List<User> Users = new ArrayList<>();
    DB_Connection DB = new DB_Connection();

    @Override
    public  boolean addUser(User user) {

       try {
           DB_Operations DbController = new DB_Operations();
           DbController.addNewUser(user);
           return true;
       }catch (Exception e){
           System.out.println(e.getMessage());
       }
            return false;
    }

    @Override
    public  Collection<User> getUsers() {
        return Users;
    }

    @Override
    public User getUser(String id) {
        User user = findById(id);
        return user;
    }

    public User getUser2(String id) {
        System.out.println(Users.iterator());
        return null;
    }

    @Override
    public User editUser(User user , String id) throws UserException {
        //return editeUser(user , id);
        return null;
    }

    @Override
    public boolean deleteUser(String id) {
            if (findById(id) != null){
                Users.remove(findById(id));
                return true;
            }else {
                return false;
            }
    }

    @Override
    public boolean userExist(String id) {
        return findById(id) == null ? false : true ;
    }


    public User findById(String id) {

        User user = null;
        for (int i = 0; i < Users.size() ; i++) {
                if (Users.get(i).getId() == Integer.parseInt(id)){
                    user = Users.get(i);
                }
        }
        return user;
    }


//    public User editeUser(User user , String id){
//        User edited = null;
//        for (int i = 0; i < Users.size() ; i++) {
//                if (Users.get(i).getId().equals(id)){
//                    System.out.println("in here");
//                    Users.remove(i);
//                    Users.add(i , user);
//                    edited = user;
//                }
//        }
//        return edited;
//    }


    public void printSomeThing(){

        System.out.println("obada");

    }



}
