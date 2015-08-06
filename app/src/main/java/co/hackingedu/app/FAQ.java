package co.hackingedu.app;

/**
 * Created by brandonkelley on 8/5/15.
 */
public class FAQ {

	private String question;
	private String answer;

	public FAQ(String question, String answer) {
		this.question = question;
		this.answer = answer;
	}

	public String getQuestion() {
		return question;
	}

	public String getAnswer() {
		return answer;
	}

}
