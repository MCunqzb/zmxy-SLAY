package vfx;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.math.MathUtils;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.vfx.AbstractGameEffect;

public class QuietSpecialSmokeBombEffect extends AbstractGameEffect {
    private float x;

    private float y;

    private float aV;

    private float startDur;

    private float targetScale;

    private TextureAtlas.AtlasRegion img;

    public QuietSpecialSmokeBombEffect(float x, float y,float r1, float r2,float g1,float g2,float b1,float b2) {
        this.color = new Color(0.0F, 0.0F, 0.0F, 1.0F);
        this.color.r = MathUtils.random(r1, r2);
        this.color.g = MathUtils.random(g1, g2);
        this.color.b = MathUtils.random(b1, b2);
        if (MathUtils.randomBoolean()) {
            this.img = ImageMaster.EXHAUST_L;
        } else {
            this.img = ImageMaster.EXHAUST_S;
        }
        this.duration = MathUtils.random(1.5F, 2.0F);
        this.targetScale = MathUtils.random(0.8F, 1.4F);
        this.startDur = this.duration;
        this.x = x - this.img.packedWidth / 2.0F;
        this.y = y - this.img.packedHeight / 2.0F;
        this.scale = 0.8F;
        this.rotation = MathUtils.random(360.0F);
        this.aV = MathUtils.random(-250.0F, 250.0F);
    }

    public void update() {
        this.duration -= Gdx.graphics.getDeltaTime();
        if (this.duration < 0.0F)
            this.isDone = true;
        this.x += MathUtils.random(-2.0F * Settings.scale, 2.0F * Settings.scale);
        this.y += MathUtils.random(-2.0F * Settings.scale, 2.0F * Settings.scale);
        this.rotation += this.aV * Gdx.graphics.getDeltaTime();
        this.scale = Interpolation.exp10Out.apply(0.8F, this.targetScale, 1.0F - this.duration / this.startDur);
        if (this.duration < 0.33F)
            this.color.a = this.duration * 3.0F;
    }

    public void render(SpriteBatch sb) {
        sb.setColor(this.color);
        sb.draw((TextureRegion)this.img, this.x, this.y, this.img.packedWidth / 2.0F, this.img.packedHeight / 2.0F, this.img.packedWidth, this.img.packedHeight, this.scale, this.scale, this.rotation);
    }

    public void dispose() {}
}
