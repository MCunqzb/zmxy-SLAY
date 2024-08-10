package powers;

import Helpers.ModHelper;
import basemod.BaseMod;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class BloodThirstyPower extends AbstractPower  {
    public static final String POWER_ID = ModHelper.MakePath("BloodThirstyPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public BloodThirstyPower(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = Amount;

        String path128 = "img/powers/blood_thirsty84.png";
        String path48 = "img/powers/blood_thirsty32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        this.updateDescription();
        this.isTurnBased = true;
    }

    public void onAttack(DamageInfo info, int damageAmount, AbstractCreature target) {
        int heal_amount =Math.min(damageAmount, target.currentHealth);
        if (heal_amount/3.0 >= 1 && target != this.owner && info.type == DamageInfo.DamageType.NORMAL) {
            this.flash();
            //BaseMod.logger.info("AMT"+this.amount/3*damageAmount);
            this.addToTop(new HealAction(this.owner, this.owner, (int)(heal_amount*0.334) ));
        }

    }

    public void atEndOfRound() {
        if (this.amount == 0) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID ));
        } else {
            this.addToBot(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }
    }




    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount);
    }


}
