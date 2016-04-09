
public class WhosPlayinAPITest {

	public static void main(String[] args) {
		try {
			User.createUser("Test", "password", "TestJav", 33, "Non-binary lobsteR", "lel@topkek.li", "515-666-3324");
		}
		catch (Exception e)
		{
			System.out.println(e.getMessage());
		}
	}
}
