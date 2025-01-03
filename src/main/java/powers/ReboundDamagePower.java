package powers;

import Helpers.ModHelper;
import basemod.BaseMod;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import monsters.act1.DrakeDemonKing;

public class ReboundDamagePower extends AbstractPower {
    public static final String POWER_ID = ModHelper.MakePath("ReboundDamagePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    private boolean justApplied;

    public ReboundDamagePower(AbstractCreature owner, int Amount, boolean justApplied) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = Amount;
        this.justApplied = justApplied;
        String path128 = "img/powers/blood_siren_shell84.png";
        String path48 = "img/powers/blood_siren_shell32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
    }

    public int onAttacked(DamageInfo info, int damageAmount) {
        if (info.type != DamageInfo.DamageType.THORNS && info.type != DamageInfo.DamageType.HP_LOSS && info.owner != null && info.owner != this.owner && damageAmount <= 0 && info.output > 0) {
            flash();
            this.addToTop(new DamageAction(info.owner, new DamageInfo(this.owner, info.output, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.SLASH_HORIZONTAL, true));
            BaseMod.logger.info(info);
            BaseMod.logger.info(info.owner);
            BaseMod.logger.info(this.owner);
        }
        return damageAmount;
    }

    public void atEndOfRound() {
        if (this.justApplied) {
            this.justApplied = false;
            return;
        }
        if (this.amount == 0) {
            this.addToBot(new RemoveSpecificPowerAction(this.owner, this.owner, this.ID));
        } else {
            this.addToBot(new ReducePowerAction(this.owner, this.owner, this.ID, 1));
        }
        if (this.owner != null && this.owner instanceof DrakeDemonKing){
             if (this.owner.currentBlock > this.amount*5){
                 int dec_b = this.owner.currentBlock - this.amount*5;
                 this.owner.loseBlock(dec_b);
             }
        }
    }


    public void updateDescription() {
        if (this.owner != null && this.owner instanceof DrakeDemonKing) {
            this.description = String.format(DESCRIPTIONS[1], this.amount,this.amount*5);
        }else {
            this.description = String.format(DESCRIPTIONS[0], this.amount);
        }
    }


}
