package com.rorkien.opsanta;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;

import com.rorkien.opsanta.Graphics.Font;
import com.rorkien.opsanta.Graphics.Image;
import com.rorkien.opsanta.Graphics.Renderer;
import com.rorkien.opsanta.Screen.IntroScreen;
import com.rorkien.opsanta.Screen.Screen;
import com.rorkien.opsanta.Screen.Level.Levels;

public class Game extends Canvas implements Runnable {
	private static final long serialVersionUID = 1L;
	public static final String name = "Operation Santa";
	public static final String version = "build 0";
	public static final int WIDTH = 640, HEIGHT = 480, SCALE = 1;
	public boolean running = true;
	
	public int frames = 0, ticks = 0;
	public int lastTickCount;
	
	public BufferedImage image = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
	public Renderer raster = new Renderer(image);
	public Screen screen;
	
	public static Game instance;	
	public Input input;	
	public Sounds sounds;
	
	public void init() {		
		instance = this;
		input = new Input(this);
		sounds = new Sounds(this);
		screen = new IntroScreen();
		Levels.init();
	}
	
	public void start() {
		new Thread(this).start();
	}
	
	public void stop() {
		running = false;
	}
	
	public void run() {	
		init();
		
		final int frameCap = 60;
		final double nsPerFrame = 1000000000 / frameCap;
		double nextTick = System.nanoTime();
		long fpstimer = System.currentTimeMillis();
		
		while(running) {	
			for(long now = System.nanoTime(); now > nextTick; nextTick += nsPerFrame) {
				this.tick();
				this.render();
			}

			if (System.currentTimeMillis() - fpstimer > 1000) {
				fpstimer += 1000;
				System.out.println("Deviance: " + Math.abs((60D / 100D) * (this.ticks - lastTickCount - frameCap)) + "% (" + (this.ticks - lastTickCount) + "/" + this.frames + ")" );
				lastTickCount = this.ticks;
				this.frames = 0;
			}
			
			long sleepTime = 2;
			long now = System.nanoTime(), diff;
			while((diff = System.nanoTime()-now) < sleepTime) {
				if(diff < sleepTime*0.8) try { Thread.sleep(1); } catch(Exception exc) {}
				else Thread.yield();
			}

		}
	}
	
	public void tick() {
		//long start = System.nanoTime();
		this.ticks++;		
		input.tick();
		screen.tick();
		//1 million nanos = 1 ms
		//System.out.println((System.nanoTime() - start) / 1000 / 1000D);
	}
	
	public void render() {
		//long start = System.nanoTime();
		this.frames++;	
		BufferStrategy bs = getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(3);
			return;
		}	
		
		Graphics g = bs.getDrawGraphics();
		g.drawImage(image, 0, 0, WIDTH * SCALE, HEIGHT * SCALE, null);

		screen.render();
		
		if (!this.hasFocus()) {
			raster.blendTo(0x0, (int) (256 * 0.2));
			if (this.ticks % 60 > 15) Font.write("Click to activate!", (WIDTH / 2) - (("Click to activate!".length() * Image.font_16[0][0].width) / 2), (HEIGHT / 2) - (Image.font_16[0][0].height / 2), Image.font_16);
		}

		g.dispose();
		bs.show();
		//System.out.println((System.nanoTime() - start) / 1000 / 1000D);
	}	
	
	public static void main(String[] args) {
		Game game = new Game();
		JFrame gameFrame = new JFrame(name);
		Dimension size = new Dimension(WIDTH * SCALE, HEIGHT * SCALE);
		Dimension pos = Toolkit.getDefaultToolkit().getScreenSize();
		gameFrame.setLocation((pos.width / 2) - (size.width / 2), (pos.height / 2) - (size.height / 2));
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.getContentPane().setPreferredSize(size);
		gameFrame.setResizable(false);
		gameFrame.setVisible(true);
		gameFrame.add(game);
		gameFrame.pack();
		gameFrame.setVisible(true);	
		
		game.start();
	}
}
