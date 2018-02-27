package bookstore;

public class Program {

	public static void main(String[] args) {
		(new Store()).start();
	}

	public static String rightPad(String str, int len) {
		str = "                   " + str;
		return str.substring(str.length() - len, str.length());
	}

}
