package powers;

import Helpers.ModHelper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import vfx.ColourfulHemokinesisEffect;

public class TenacityPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.MakePath("TenacityPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public TenacityPower(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = Amount;
        String path128 = "img/powers/tenacity84.png";
        String path48 = "img/powers/tenacity32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
        this.isTurnBased = true;
    }

    public float modifyBlock(float blockAmount, AbstractCard card) {
        return (blockAmount += (float)this.amount) < 0.0F ? 0.0F : blockAmount;
    }


    public void onUseCard(AbstractCard card, UseCardAction action) {
        if ( card.baseBlock > 0) {
            this.flash();
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }

    }
   //public void atEndOfTurn(boolean isPlayer) {
   //    if (isPlayer) {
   //        this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
   //    }
   //}

    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],this.amount);
    }
}
