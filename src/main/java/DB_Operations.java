import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DB_Operations {

   static DB_Connection DB = new DB_Connection();
        // this method add a new user to the database and take the user to added
    public static boolean addNewUser(User user){
        if (getSingleUser(user.getId()) != null ){
            return false;
        }else {
        try {
        String sqly = "INSERT INTO public.\"User\"( " +
                "id, firstname, lastname, email) " +
                " VALUES (?, ?, ?, ?);";
        PreparedStatement preparedStatement = DB.connect().prepareStatement(sqly);
            preparedStatement.setInt(1,user.getId());
            preparedStatement.setString(2,user.getFirstName());
            preparedStatement.setString(3,user.getLastName());
            preparedStatement.setString(4,user.getEmail());
            preparedStatement.execute();
            System.out.println("inserted");
            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
        }
    }
        // this method get  all the records from the database
    public static List<User> getAllUsers(){
            try {
                String sqly = "SELECT * FROM public.\"User\" ";
                List<User> Users = new ArrayList<>();
                PreparedStatement preparedStatement = DB.connect().prepareStatement(sqly);
                ResultSet rs = preparedStatement.executeQuery();
                while (rs.next()){
                    User user = new User(rs.getInt("id") , rs.getString("firstName") ,
                            rs.getString("lastName") , rs.getString("email"));
                    Users.add(user);
                }
                return Users;
            }catch (Exception e){
                e.printStackTrace();
            }
        return null;
    }

        // this method takes the id of the user and then return the user info from the database
    public static User getSingleUser(int id){
        try {
            String sqly = "SELECT * FROM public.\"User\" " +
            "WHERE id = " + id ;
            PreparedStatement preparedStatement = DB.connect().prepareStatement(sqly);
          ResultSet rs =  preparedStatement.executeQuery();
            System.out.println(rs.next());
            User user = new User(rs.getInt("id") , rs.getString("firstname") , rs.getString("lastname") , rs.getString("email"));
            return user;

        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
            // this method takes an id argument and then get the user using getSingle user method if its
            // not null then its available otherwise its not found on db
    public static boolean deleteUser(int id){
            User user = getSingleUser(id);
            if (user != null){
                String sqly = "DELETE FROM public.\"User\" WHERE id = " + id;
                try{
                PreparedStatement preparedStatement = DB.connect().prepareStatement(sqly);
                    preparedStatement.executeUpdate();
                    return true;

                }catch (Exception e){
                    e.printStackTrace();
                }
            }else{
                return false;
            }
        return false;
    }

        // this method takes 2 argument the info that is used to update the record and the user id
    public static  boolean updateUser(User user , int id){
            User dbUser = getSingleUser(id);
            if (dbUser != null){
                String sqly = "UPDATE public.\"User\" " +
               " SET id=?, firstname=?, lastname=?, email=? " +
                "WHERE id =" + id;
                        try {
                            PreparedStatement preparedStatement = DB.connect().prepareStatement(sqly);
                                preparedStatement.setInt(1, user.getId());
                                preparedStatement.setString(2,user.getFirstName());
                                preparedStatement.setString(3 , user.getLastName());
                                preparedStatement.setString(4 , user.getEmail());
                                preparedStatement.executeUpdate();
                                return true;
                        }catch (Exception e){
                            e.printStackTrace();
                        }
            }else {
                return false;
            }
        return false;
    }


}