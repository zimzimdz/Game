package fr.zimzim.render;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JComponent;

import fr.zimzim.meshe.GameItem;
import fr.zimzim.model.GameEngine;
import fr.zimzim.util.Settings;

public class NextItemDisplayer extends JComponent implements Observer{
	
	/**
	 * 
	 */
	private List<GameItem> items;
	private Image[] images;
	private Image cadre;
	private static final long serialVersionUID = 7326520990733045489L;
	
	public NextItemDisplayer() {
		this.items = new ArrayList<GameItem>();
		this.cadre = getToolkit().getImage(this.getClass().getResource(Settings.IMG_SMALL_CADRE));
		this.images = new Image[4];
        this.images[Settings.PUYO_GREEN] = getToolkit().getImage(this.getClass().getResource(Settings.IMG_PUYO_GREEN));
        this.images[Settings.PUYO_YELLOW] = getToolkit().getImage(this.getClass().getResource(Settings.IMG_PUYO_YELLOW));
        this.images[Settings.PUYO_RED] = getToolkit().getImage(this.getClass().getResource(Settings.IMG_PUYO_RED));
        this.images[Settings.PUYO_BLUE] = getToolkit().getImage(this.getClass().getResource(Settings.IMG_PUYO_BLUE));
        this.setPreferredSize(new Dimension((Settings.TAILLE_PIXELS*2)+(Settings.LEFT_RIM_CADRE*2),
        		(Settings.TAILLE_PIXELS)+Settings.TOP_RIM_CADRE*2));
        repaint();
	}

	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(this.cadre,
				0,
				0,
				null);
		if(items.size()>0){
			for(int i=0; i< items.size(); i++) {
				g.drawImage(this.images[this.items.get(i).getType()],
						(i*Settings.TAILLE_PIXELS)+Settings.LEFT_RIM_CADRE,
						Settings.TOP_RIM_CADRE,
						null);
			}
		}
		
	}

	@Override
	public void update(Observable arg0, Object arg1) {
		this.items.clear();
		this.items.addAll(((GameEngine) arg1).getNextItems());	
		repaint();
	}

}
