package powers;

import Helpers.ModHelper;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;

import java.util.Iterator;

public class ConcentrateEnergyPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.MakePath("ConcentrateEnergy");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public ConcentrateEnergyPower(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = Amount;
        String path128 = "img/powers/phantom_kindle84.png";
        String path48 = "img/powers/phantom_kindle32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
        this.isTurnBased = true;
    }


    public void atEndOfTurn(boolean isPlayer) {
        if (this.owner.hasPower("Vigor")) {
            if (this.owner.getPower("Vigor").amount >= this.amount) {
                this.flash();
                this.addToBot(new ReducePowerAction(this.owner, this.owner, "Vigor", this.amount));
                this.addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, this.amount), this.amount));
            } else if (this.owner.getPower("Vigor").amount < this.amount && this.owner.getPower("Vigor").amount > 0) {
                this.flash();
                int num = this.owner.getPower("Vigor").amount;
                this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, "Vigor"));
                this.addToBot(new ApplyPowerAction(this.owner, this.owner, new StrengthPower(this.owner, num), num));
            }
        }
    }


    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],this.amount,this.amount);
    }
}
