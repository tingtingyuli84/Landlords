package com.game.landlords.core;

public abstract class Screen {
	public final static int MAIN_MENU = 1;
	public final static int GAME = 2;
	public final static int HELP = 3;
	public final static int CHOOSE_LEVEL = 4;
	public final static int LOADING = 5;
	public final static int POINTS = 6;
	
    protected final Game game;
    protected int screenType;

    public Screen(Game game) {
        this.game = game;
    }
    
    public int getScreenType()
    {
    	return screenType;
    }

    public abstract void update(float deltaTime);

    public abstract void present(float deltaTime);

    public abstract void pause();

    public abstract void resume();

    public abstract void dispose();
}
