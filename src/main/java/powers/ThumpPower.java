package powers;

import Helpers.ModHelper;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.ReducePowerAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import demoMod.MonkeyKingMod;

import java.util.Iterator;

public class ThumpPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.MakePath("ThumpPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;


    public ThumpPower(AbstractCreature owner,int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = Amount;
        this.type = PowerType.DEBUFF;
        String path128 = "img/powers/thump84.png";
        String path48 = "img/powers/thump32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
    }

    public void onApplyPower(AbstractPower power, AbstractCreature target, AbstractCreature source) {
        this.flash();
        //this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, power.ID));
        this.addToBot(new ReducePowerAction(this.owner, this.owner, power.ID, power.amount));
    }

    public void atEndOfTurn(boolean isPlayer) {
        if (this.amount <= 1) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, POWER_ID));
        }else {
            this.addToBot(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }
    }


    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],this.amount);
    }





}