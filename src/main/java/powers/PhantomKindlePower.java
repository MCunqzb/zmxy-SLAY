package powers;

import Helpers.ModHelper;
import cards.PhantomStab;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PoisonPower;

import java.util.Iterator;

public class PhantomKindlePower extends AbstractPower {
    public static final String POWER_ID = ModHelper.MakePath("PhantomKindlePower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public PhantomKindlePower(AbstractCreature owner, int Amount) {
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


    public void onAfterUseCard(AbstractCard card, UseCardAction action) {
        if (card.type== AbstractCard.CardType.ATTACK && (TempHPField.tempHp.get(this.owner))>0 && !AbstractDungeon.getMonsters().areMonstersBasicallyDead()){
            Iterator var1 = AbstractDungeon.getMonsters().monsters.iterator();
            while(var1.hasNext()) {
                AbstractMonster m1 = (AbstractMonster)var1.next();
                if (!m1.isDead && !m1.isDying) {
                    this.addToBot(new ApplyPowerAction(m1, this.owner, new ScorchPower(m1, this.owner, this.amount), this.amount, true));
                }
            }

        }
    }


    public void stackPower(int stackAmount) {
        this.fontScale = 8.0F;
        this.amount += stackAmount;
    }

    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0],this.amount);
    }
}
