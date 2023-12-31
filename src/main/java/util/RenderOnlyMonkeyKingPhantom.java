package util;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.vfx.TorchHeadFireEffect;
import com.megacrit.cardcrawl.vfx.combat.RedFireballEffect;
import monsters.MonkeyKingPhantom;

public class RenderOnlyMonkeyKingPhantom extends MonkeyKingPhantom {
    private float fireTimer;

    public RenderOnlyMonkeyKingPhantom() {
        super(0.0F, 0.0F);
        this.fireTimer = 0.0F;
        this.flipHorizontal = true;
    }

    public void renderHealth(SpriteBatch sb) {}

    public void renderPowerTips(SpriteBatch sb) {}

    public void update() {
        if (!this.isDying) {
            this.fireTimer -= Gdx.graphics.getDeltaTime();
            if (this.fireTimer < 0.0F) {
                this.fireTimer = 0.04F;
                AbstractDungeon.effectList.add(new TorchHeadFireEffect(this.skeleton.getX() + this.skeleton.findBone("fireslot").getX() + 10.0F * Settings.scale, this.skeleton.getY() + this.skeleton.findBone("fireslot").getY() + 110.0F * Settings.scale));
            }
        }
    }
}
