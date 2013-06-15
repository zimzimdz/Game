package fr.zimzim.game;

import fr.zimzim.frame.MainFrame;
import fr.zimzim.input.InputEngine;
import fr.zimzim.model.GameEngine;
import fr.zimzim.render.RenderEngine;
import fr.zimzim.sound.SoundEngine;

public class PuyoGame implements IGame, Runnable {
	
	private static int DIFFICULTY_RANGE = 10;
	private int delay;
	private GameEngine engine;
	private RenderEngine render;
	private InputEngine input;
	private MainFrame frame;
	private Thread gameThread;
	private boolean isRunning;
	private boolean pause = false;


	@Override
	public void init() {
		this.engine = new GameEngine();
		this.render = new RenderEngine(engine);
		this.input = new InputEngine(engine, this);	
		this.frame = new MainFrame(render);
		

		frame.addKeyListener(input);


	}

	@Override
	public void start() {
		frame.setVisible(true);
		isRunning = true;
		this.delay = 300;
		SoundEngine.volume = SoundEngine.Volume.LOW;
		SoundEngine.AMBIANCE.play();
		gameThread = new Thread(this);
		gameThread.start();
	}

	@Override
	public void pause() {
		pause = !pause;
		if(pause) {
			SoundEngine.AMBIANCE.pause();
			SoundEngine.PAUSE.play();
		}
		else {
			SoundEngine.PAUSE.play();
			SoundEngine.AMBIANCE.pause();
			
		}
	}

	@Override
	public void resume() {
	}

	@Override
	public void stop() {
		this.isRunning = false;

	}

	@Override
	public void exit() {
		this.frame.dispose();
		System.exit(0);

	}

	@Override
	public void run() {
		engine.addActiveItems();
		while(isRunning) {
			
			if(!pause) {
				render.repaint();
				sleep(delay);
				//render.repaint();
				if(engine.fall()) {
					if(engine.checkMap()) increaseDifficulty();
					if(!engine.addActiveItems()){
						isRunning = false;
					}
					
				}
			}
		}

	}
	
	private void increaseDifficulty() {
		int value = (this.delay*DIFFICULTY_RANGE)/100;
		this.delay = this.delay-value;
		// TODO Auto-generated method stub
		
	}

	public void sleep(int time) {
		try {
			gameThread.sleep(time);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}




}
