package group12.whosplayin;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        System.out.println("hi");
        assertEquals(4, 2 + 2);
    }
    @Test
    public void userFriendsList() throws Exception {
        User user = User.getInstance();
        user.authenticate("grant", "password");
        for (User u : user.getFriends(20))
        {
            System.out.println(u.toString());
        }
    }
}