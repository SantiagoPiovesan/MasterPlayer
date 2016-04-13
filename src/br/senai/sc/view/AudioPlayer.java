package br.senai.sc.view;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public class AudioPlayer extends Thread {

	private String musicaAtual;
	private boolean loop;
	private AdvancedPlayer player;

	public AudioPlayer(String musicaAtual, boolean loop) {
		this.musicaAtual = musicaAtual;
		this.loop = loop;
	}

	@Override
	public void run() {

		FileInputStream fis;

		try {
			do {
				fis = new FileInputStream(musicaAtual); // Caminhho da musica
				player = new AdvancedPlayer(fis);
				player.play();
			} while (loop);
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (JavaLayerException e1) {
			e1.printStackTrace();
		}

	}

	public void close() {
		this.loop = false;
		this.player.close();
		this.interrupt();
	}

}
