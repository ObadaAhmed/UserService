import org.omg.CORBA.UserException;

import java.util.Collection;

public interface userService {

        public  void addUser (User user);
        public Collection<User> getUsers ();
        public User getUser (String id);
        public User editUser (User user , String id)
                throws UserException;
        public void deleteUser (String id);
        public boolean userExist (String id);

    }

