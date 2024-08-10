package powers;

import Helpers.ModHelper;
import basemod.BaseMod;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.vfx.combat.VerticalAuraEffect;

public class GearAttackPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.MakePath("GearAttackPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public boolean left;

    public GearAttackPower(AbstractCreature owner,boolean face_left) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = -1;
        this.left =face_left;
        String path128 = "img/powers/attack_left84.png";;
        String path48 = "img/powers/attack_left32.png";
        if (this.left) {
            path128 = "img/powers/attack_left84.png";
            path48 = "img/powers/attack_left32.png";

        } else {
            path128 = "img/powers/attack_right84.png";
            path48 = "img/powers/attack_right32.png";
        }
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
        this.isTurnBased = true;
    }




    public void updateDescription() {
        this.description = DESCRIPTIONS[0];
    }
}
