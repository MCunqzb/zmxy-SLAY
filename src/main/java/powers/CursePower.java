package powers;

import Helpers.ModHelper;
import basemod.interfaces.CloneablePowerInterface;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.powers.interfaces.HealthBarRenderPower;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import com.megacrit.cardcrawl.unlock.UnlockTracker;

public class CursePower extends AbstractPower implements HealthBarRenderPower, CloneablePowerInterface {
    public static final String POWER_ID = ModHelper.MakePath("CursePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean justApplied = false;

    public CursePower(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = Amount;
        this.type = PowerType.DEBUFF;
        String path128 = "img/powers/curse84.png";
        String path48 = "img/powers/curse32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
        this.isTurnBased = true;
        if (this.amount >= 5) {
            this.amount = 5;
        }
    }

    public void atEndOfTurn(boolean isPlayer) {
        AbstractCreature p = AbstractDungeon.player;
        if (this.amount>=5){
            this.addToBot(new DamageAction(p, new DamageInfo(p, 5, DamageInfo.DamageType.HP_LOSS)));
            this.addToBot(new RemoveSpecificPowerAction(p, p, this.ID));
        }
        if (this.amount == 1) {
            this.addToBot(new RemoveSpecificPowerAction(p, p, this.ID));
        } else {
            this.addToBot(new ReducePowerAction(p, p, this.ID, (int)(this.amount*0.5)));
        }
    }

    public void stackPower(int stackAmount) {
        this.amount += stackAmount;
        if (this.amount > 5) {
            this.amount = 5;
        }
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],this.amount);
    }

    public Color getColor() {
        return Color.GRAY;
    }

    public int getHealthBarAmount() {
        if (this.amount>=5) {
            return 10;
        }else
            return 1;
    }

    @Override
    public AbstractPower makeCopy() {
        return new CursePower(this.owner, this.amount);
    }
}