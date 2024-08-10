package powers;

import Helpers.ModHelper;
import cards.BleedCard;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDiscardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.powers.AbstractPower;

public class ThreeEyesSealPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.MakePath("ThreeEyesSealPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;
    public boolean left;

    public ThreeEyesSealPower(AbstractCreature owner,int amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = amount;
        this.type = PowerType.DEBUFF;
        String path128 = "img/powers/three_eyes_seal84.png";;
        String path48 = "img/powers/three_eyes_seal32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);
        this.updateDescription();
        this.isTurnBased = true;
    }

    @Override
    public int onHeal(int healAmount) {
        return (int)(healAmount*0.5f);
    }


    @Override
    public boolean canPlayCard(AbstractCard card) {
        if ( this.amount >= 3 && card.type == AbstractCard.CardType.POWER) {
            return false;
        }else if (this.amount >= 6 && card.type == AbstractCard.CardType.SKILL){
            return false;
        }
        return true;
    }

    public float modifyBlock(float blockAmount, AbstractCard card) {
        if (this.amount>=2) {
            return (blockAmount =(int)(blockAmount* (1.25-0.25*this.amount))) < 0.0F ? 0.0F : blockAmount;
        }
        return blockAmount;
    }

    public float atDamageGive(float damage, DamageInfo.DamageType type) {
        if (this.amount>=2 && type == DamageInfo.DamageType.NORMAL) {
            return damage * (float)(1.25-0.25*this.amount);
        } else {
            return damage;
        }
    }



    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], (int)(100*(1-(1.25-0.25*this.amount))), (int)(100*(1-(1.25-0.25*this.amount))));
    }









}
