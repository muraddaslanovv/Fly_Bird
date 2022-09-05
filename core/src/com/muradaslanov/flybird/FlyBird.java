package com.muradaslanov.flybird;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Circle;
import com.badlogic.gdx.math.Intersector;
import com.badlogic.gdx.utils.ScreenUtils;

import org.w3c.dom.Text;

import java.util.Random;

public class FlyBird extends ApplicationAdapter {

	SpriteBatch batch;
	Texture background;
	Texture bird;
	Texture bad;
	Texture bad1;
	Texture bad2;
	float birdx = 0;
	float birdy = 0;
	int gameState = 0;
	float velocity = 0;
	float badVel = 7;
	float gravity = 0.4f;
	Random random;
	int result = 0;
	int resultEnemy = 0;
	BitmapFont font;
	BitmapFont overFont;

	Circle birdCircle;

	ShapeRenderer shapeRenderer;

	int badNumber = 3;
	float[] badx = new float[badNumber];
	float[] badOffSet = new float[badNumber];
	float[] badOffSet1 = new float[badNumber];
	float[] badOffSet2 = new float[badNumber];
	float distance = 0;

	Circle[] badCircles;
	Circle[] badCircles1;
	Circle[] badCircles2;

	@Override
	public void create () {
		batch = new SpriteBatch();
		background = new Texture("Background.png");
		bird = new Texture("Bird.png");
		bad = new Texture("badguy.png");
		bad1 = new Texture("badguy.png");
		bad2 = new Texture("badguy.png");

		distance = Gdx.graphics.getWidth()/2;
		random = new Random();

		birdx = Gdx.graphics.getWidth()/2 - bird.getHeight() / 2;
		birdy = Gdx.graphics.getHeight()/2;

		shapeRenderer = new ShapeRenderer();

		birdCircle = new Circle();

		badCircles = new Circle[badNumber];
		badCircles1 = new Circle[badNumber];
		badCircles2 = new Circle[badNumber];

		font = new BitmapFont();
		font.setColor(Color.WHITE);
		font.getData().setScale(7);

		overFont = new BitmapFont();
		overFont.setColor(Color.WHITE);
		overFont.getData().setScale(8);

		for (int i = 0; i < badNumber; i++) {

			badOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			badOffSet1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
			badOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

			badx[i] = Gdx.graphics.getWidth() - bad.getWidth()/2 + i * distance;

			badCircles[i] = new Circle();
			badCircles1[i] = new Circle();
			badCircles2[i] = new Circle();

		}
	}

	@Override
	public void render () {

		batch.begin();
		batch.draw(background,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

		if(gameState == 1){

			if(badx[resultEnemy] < birdx){
				result++;

				if(resultEnemy < badNumber - 1){
					resultEnemy++;
				}else{
					resultEnemy = 0;
				}
			}

			if(Gdx.input.justTouched()){
				velocity = -10;
			}

			for (int i = 0; i < badNumber; i++) {

				if(badx[i] < 0){
					badx[i] = badx[i] + badNumber * distance;

					badOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					badOffSet1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					badOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
				}else{
					badx[i] = badx[i] - badVel;
				}

//				badx[i] = badx[i] - badVel;

				batch.draw(bad,badx[i],Gdx.graphics.getHeight()/2 + badOffSet[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/9);
				batch.draw(bad1,badx[i],Gdx.graphics.getHeight()/2 + badOffSet1[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/9);
				batch.draw(bad2,badx[i],Gdx.graphics.getHeight()/2 + badOffSet2[i],Gdx.graphics.getWidth()/15,Gdx.graphics.getHeight()/9);

				badCircles[i] = new Circle(badx[i] + Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight()/2 + badOffSet[i] + Gdx.graphics.getHeight()/18, Gdx.graphics.getWidth()/30);
				badCircles1[i] = new Circle(badx[i] + Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight()/2 + badOffSet1[i] + Gdx.graphics.getHeight()/18, Gdx.graphics.getWidth()/30);
				badCircles2[i] = new Circle(badx[i] + Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight()/2 + badOffSet2[i] + Gdx.graphics.getHeight()/18, Gdx.graphics.getWidth()/30);

			}

//			badx -= 10;



			if(birdy > 0){
				velocity += gravity;
				birdy -= velocity;
			}else{
				gameState = 2;
			}

		}else if(gameState == 0){
			if(Gdx.input.justTouched()){
				gameState = 1;
			}
		}else if(gameState == 2){

			overFont.draw(batch,"Game Over! Tap To Restart The GAME",160,Gdx.graphics.getHeight()/3);

			if(Gdx.input.justTouched()){
				gameState = 1;

				birdy = Gdx.graphics.getHeight()/2;

				for (int i = 0; i < badNumber; i++) {

					badOffSet[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					badOffSet1[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);
					badOffSet2[i] = (random.nextFloat() - 0.5f) * (Gdx.graphics.getHeight() - 200);

					badx[i] = Gdx.graphics.getWidth() - bad.getWidth()/2 + i * distance;

					badCircles[i] = new Circle();
					badCircles1[i] = new Circle();
					badCircles2[i] = new Circle();

				}

				velocity = 0;
				resultEnemy = 0;
				result = 0;

			}
		}


		batch.draw(bird,birdx,birdy, Gdx.graphics.getWidth() / 15, Gdx.graphics.getHeight()/10);

		font.draw(batch,String.valueOf(result),130,230);

		batch.end();

		birdCircle.set(birdx + Gdx.graphics.getWidth()/30,birdy + Gdx.graphics.getHeight()/20,Gdx.graphics.getWidth() / 30);
//		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
//		shapeRenderer.setColor(Color.RED);
//		shapeRenderer.circle(birdCircle.x,birdCircle.y,birdCircle.radius);

		for (int i = 0; i < badNumber; i++) {
//			shapeRenderer.circle(badx[i] + Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight()/2 + badOffSet[i] + Gdx.graphics.getHeight()/18, Gdx.graphics.getWidth()/30);
//			shapeRenderer.circle(badx[i] + Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight()/2 + badOffSet1[i] + Gdx.graphics.getHeight()/18, Gdx.graphics.getWidth()/30);
//			shapeRenderer.circle(badx[i] + Gdx.graphics.getWidth()/30, Gdx.graphics.getHeight()/2 + badOffSet2[i] + Gdx.graphics.getHeight()/18, Gdx.graphics.getWidth()/30);

			if(Intersector.overlaps(birdCircle,badCircles[i]) || Intersector.overlaps(birdCircle,badCircles1[i]) || Intersector.overlaps(birdCircle,badCircles2[i])){
				gameState = 2;
			}
		}

		shapeRenderer.end();

	}

	@Override
	public void dispose () {

	}
}
