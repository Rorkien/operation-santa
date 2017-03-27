package com.rorkien.opsanta;

import java.util.ArrayList;
import java.util.List;

import kuusisto.tinysound.Music;
import kuusisto.tinysound.Sound;
import kuusisto.tinysound.TinySound;


public class Sounds {
	public List<Music> musics = new ArrayList<Music>();
	public Game game;
	public static Music test;
	public static Music soundtrack[] = new Music[3];
	public static int sizeInTicks[] = {5160, 4020, 4140};
	
	public static Sound pickupGift, pickupMilk, pickupCookie, pickupSnowball1, pickupSnowball2;
	public static Sound tuck, death, edeath, door, hit, snowstep;
	public static Sound[] truck = new Sound[3];
	
	public static Music loadMusic(String filename) {
		return TinySound.loadMusic("/sounds/" + filename);
	}
	
	public static Sound loadSound(String filename) {
		return TinySound.loadSound("/sounds/" + filename);
	}
	
	public Sounds(Game game) {
		this.game = game;
		TinySound.init();
		
		test = loadMusic("pickup1.ogg");
		
		for (int i = 0; i < soundtrack.length; i++) soundtrack[i] = loadMusic("music" + (i + 1) + ".ogg");
		for (int i = 0; i < truck.length; i++) truck[i] = loadSound("truck" + (i + 1) + ".ogg");
		
		pickupGift = loadSound("pickup1.ogg");
		pickupMilk = loadSound("pickup2.ogg");
		pickupCookie = loadSound("pickup3.ogg");
		pickupSnowball1 = loadSound("pickup4.ogg");
		pickupSnowball2 = loadSound("pickup5.ogg");
		tuck = loadSound("tuck.ogg");
		edeath = loadSound("edeath.ogg");
		door = loadSound("door.ogg");
		hit = loadSound("hit.ogg");
		
	}
	
	public void play(Music music, double volume, boolean loop) {
			music.setLoop(loop);
			music.setVolume(volume);
			music.play(true);
			musics.add(music);
			music.pause();
	}
	
	public void play(Sound sound) {
		sound.play();
	}
	
	public void stop(Music music) {
		music.stop();
	}
	
	public void resumeAllMusic() {
		for (Music m : musics) m.resume();
	}
	
	public void pauseAllMusic() {
		for (Music m : musics) m.pause();
	}
	
	public void stopAllMusic() {
		for (Music m : musics) m.stop();
	}
}