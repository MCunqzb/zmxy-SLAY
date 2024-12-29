package vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;
import com.megacrit.cardcrawl.vfx.combat.SmokeBlurEffect;

public class ColorfulSmokeBombEffect extends AbstractGameEffect {
    private float x;
    private float y;
    private float r1;
    private float r2;
    private float g1;
    private float g2;
    private float b1;
    private float b2;


    public ColorfulSmokeBombEffect(float x, float y,float r1, float r2,float g1,float g2,float b1,float b2) {
        this.x = x;
        this.y = y;
        this.r1=r1;
        this.r2=r2;
        this.g1=g1;
        this.g2=g2;
        this.b1=b1;
        this.b2=b2;
        this.duration = 0.2F;
    }

    public void update() {
        if (this.duration == 0.2F) {
            CardCrawlGame.sound.play("ATTACK_WHIFF_2");

            for(int i = 0; i < 90; ++i) {
                AbstractDungeon.effectsQueue.add(new ColourfulSmokeBlurEffect(this.x, this.y,this.r1,this.r2,this.g1,this.g2,this.b1,this.b2));
            }
        }

        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F) {
            CardCrawlGame.sound.play("APPEAR");
            this.isDone = true;
        }

    }

    public void render(SpriteBatch sb) {
    }

    public void dispose() {
    }
}