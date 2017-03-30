package edu.virginia.engine.events;

import java.util.ArrayList;

public class QuestManager implements IEventListener {
	private ArrayList<String> activeQuests;
	private int progress;
	public QuestManager() {
		activeQuests = new ArrayList<String>();
		activeQuests.add("Get a Coin");
		activeQuests.add("Move backwards");
		progress = 0;
	}
	
	@Override
	public void handleEvent(Event event) {
		String type = event.getEventType();
		if(type.equals(QuestEvent.COIN_PICKED_UP)) {
			activeQuests.remove("Get a Coin");
			event.getSource().removeEventListener(this, type);
			System.out.println("Quest Completed: " + event.getEventType() + "\n\t--Get a Coin!");
		}
		else if(type.equals(QuestEvent.MOVED_BACKWARDS)) {
			progress++;
			if(progress >= 120) {
				activeQuests.remove("Move backwards");
				event.getSource().removeEventListener(this, type);
				System.out.println("Quest Completed: " + event.getEventType() + "\n\t --move backwards for 2 seconds");
			}
		}
	}
	
	public ArrayList<String> getActiveQuests() {
		return activeQuests;
	}

}
