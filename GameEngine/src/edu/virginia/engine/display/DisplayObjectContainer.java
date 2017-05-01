package edu.virginia.engine.display;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import edu.virginia.engine.controller.GamePad;

public class DisplayObjectContainer extends DisplayObject {
	
	private ArrayList<DisplayObject> children;
	
	public DisplayObjectContainer(String id) {
		super(id);
		children = new ArrayList<DisplayObject>();
	}

	public DisplayObjectContainer(String id, String fileName) {
		super(id, fileName);
		children = new ArrayList<DisplayObject>();
	}
	
	@Override
	public void draw(Graphics g) {
		super.draw(g);
		
		Graphics2D g2d = (Graphics2D) g;
		this.applyTransformations(g2d);
		for(DisplayObject child: children) {
			if (child != null) {
				child.draw(g);
			}
		}
		this.reverseTransformations(g2d);
	}
	
	@Override
	protected void update(ArrayList<String> pressedKeys, ArrayList<GamePad> controllers) {
		super.update(pressedKeys, controllers);
		for(DisplayObject child: children) {
			if (child != null) {
				child.update(pressedKeys, controllers);
			}
		}
	}
	
	public boolean contains(DisplayObject d) {
		return children.contains(d);
	}
	
	public ArrayList<DisplayObject> getChildren() {
		return children;
	}
	
	public void addChild(DisplayObject d) {
		children.add(d);
		if (d != null) {
			d.setParent(this);
		}
	}
	
	public void addChildAtIndex(DisplayObject d, int i) {
		children.add(i, d);
		d.setParent(this);
	}
	
	public boolean removeChild(DisplayObject d) {
		boolean ret = children.remove(d);
		return ret;
	}
	
	public boolean removeChildByID(String DisplayObjectID) {
		for(DisplayObject child : children) {
			if(child.getId().equals(DisplayObjectID)) {
				return children.remove(child);
			}
		}
		return false;
	}
	
	public void removeIndex(int i) {
		children.remove(i);
	}
	
	public boolean removeAll() {
		for(int i = 0; i < children.size(); i++) {
			if (children.get(i) != null) {
				((DisplayObjectContainer) children.get(i)).removeAll();
			}
		}
		children.clear();
		return children.isEmpty();
	}
	
	public String toString() {
		return this.getId();
	}

}
