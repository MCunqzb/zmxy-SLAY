package powers;

import Helpers.ModHelper;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.AttackDamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.DamageRandomEnemyAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.CardQueueItem;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.localization.PowerStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.PlatedArmorPower;
import demoMod.MonkeyKingMod;

public class FireApeStrikesPower extends AbstractPower {
    public static final String POWER_ID = ModHelper.MakePath("FireApeStrikesPower");
    private static final PowerStrings powerStrings = CardCrawlGame.languagePack.getPowerStrings(POWER_ID);
    private static final String NAME = powerStrings.NAME;
    private static final String[] DESCRIPTIONS = powerStrings.DESCRIPTIONS;

    public FireApeStrikesPower(AbstractCreature owner, int Amount) {
        this.name = NAME;
        this.ID = POWER_ID;
        this.owner = owner;
        this.amount = Amount;
        // 如果需要不能叠加的能力，只需将上面的Amount参数删掉，并把下面的Amount改成-1就行

        // 添加一大一小两张能力图
        String path128 = "img/powers/fire_ape_strikes_power_84.png";
        String path48 = "img/powers/fire_ape_strikes_power_32.png";
        this.region128 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path128), 0, 0, 84, 84);
        this.region48 = new TextureAtlas.AtlasRegion(ImageMaster.loadImage(path48), 0, 0, 32, 32);

        // 首次添加能力更新描述
        this.updateDescription();
        this.isTurnBased = true;
    }
    public void onUseCard(AbstractCard card, UseCardAction action) {
        if (card.type == AbstractCard.CardType.ATTACK && card.hasTag(MonkeyKingMod.BOXING) ) {
            this.flash();
            this.addToTop((AbstractGameAction)new DamageRandomEnemyAction(new DamageInfo(this.owner, this.amount*6, DamageInfo.DamageType.THORNS), AbstractGameAction.AttackEffect.FIRE));
        }
        else if (card.type == AbstractCard.CardType.SKILL && card.hasTag(MonkeyKingMod.BOXING)){
            this.flash();
            this.addToTop(new ApplyPowerAction(this.owner, this.owner, new PlatedArmorPower(this.owner, this.amount), this.amount));
        }

    }



    public void updateDescription() {
        this.description = String.format(DESCRIPTIONS[0], this.amount*6 , this.amount);
    }
}
