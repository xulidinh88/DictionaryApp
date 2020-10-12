import com.sun.speech.freetts.*;

/* 
 * TO DO : Speech text from method: say();
 */
public class Voice {

	private String name;

	private com.sun.speech.freetts.Voice voice;

	public Voice(String name) {
		this.name = name;
		this.voice = VoiceManager.getInstance().getVoice(this.name);
		this.voice.allocate();
	}

	public void say(String something) {
		this.voice.speak(something);
	}
}
