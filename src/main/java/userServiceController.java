import org.omg.CORBA.UserException;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class userServiceController implements userService {

    static List<User> Users = new ArrayList<>();

    @Override
    public  void addUser(User user) {
        Users.add(user);
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
        return editeUser(user , id);
    }

    @Override
    public void deleteUser(String id) {
        Users.remove(findById(id));
    }

    @Override
    public boolean userExist(String id) {
        return findById(id) == null ? false : true ;
    }


    public User findById(String id){
        User user = null;
        for (int i = 0; i < Users.size() ; i++) {
                if (Users.get(i).getId().equals(id)){
                    user = Users.get(i);
                }
        }
        return user;
    }


    public User editeUser(User user , String id){
        User edited = null;
        for (int i = 0; i < Users.size() ; i++) {
                if (Users.get(i).getId().equals(id)){
                    System.out.println("in here");
                    Users.remove(i);
                    Users.add(i , user);
                    edited = user;
                }
        }
        return edited;
    }



}
