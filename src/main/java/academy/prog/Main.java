package academy.prog;

import java.io.IOException;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);
		try {
			System.out.println("Enter your login: ");
			String login = scanner.nextLine();
	
			Thread th = new Thread(new GetThread(login));
			th.setDaemon(true);
			th.start();

            System.out.println("Enter your message: ");
			while (true) {
				String text = scanner.nextLine();
				if (text.isEmpty()) break;
				String to = "";

				//To send private message type @ before nickname (example: @user1 hello!).
				if (text.startsWith("@")) {
					String resultStr = text.substring(text.indexOf("@") + 1, text.indexOf(" "));
					to = resultStr;
					text = text.substring(text.indexOf(" "));
				} else {
					to = "Send to all";
				}

				Message m = new Message(login, text, to);
				int res = m.send(Utils.getURL() + "/add");

				if (res != 200) { // 200 OK
					System.out.println("HTTP error ocurred: " + res);
					return;
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			scanner.close();
		}
	}
}
