package relics;

import basemod.abstracts.CustomRelic;
import com.evacipated.cardcrawl.mod.stslib.relics.OnReceivePowerRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.vfx.RainingGoldEffect;
import com.megacrit.cardcrawl.vfx.combat.SearingBlowEffect;

public class GreedRing extends CustomRelic implements OnReceivePowerRelic {
    public static final String ID = "dreaming_journey_to_the_west:GreedRing";
    private static final String IMG = "img/relics/GreedRing.png";
    private static final String IMG_OTL = "img/relics/outline/GreedRing_outline.png";

    public GreedRing() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.UNCOMMON, AbstractRelic.LandingSound.CLINK);
    }
    @Override
    public void onVictory() {
        this.flash();
        AbstractDungeon.effectList.add(new RainingGoldEffect(20));
        AbstractPlayer p = AbstractDungeon.player;
        p.gainGold(20);
    }


    @Override
    public boolean onReceivePower(AbstractPower var1, AbstractCreature var2) {
        if (var1.ID == "Weakened" || var1.ID == "Vulnerable" || var1.ID == "Frail") {
            var1.amount = Math.max(var1.amount + 1, 0);
            addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            flash();
            if (var1.amount != 0)
                return true;
            return false;
        }
        return true;
    }

    @Override
    public int onReceivePowerStacks(AbstractPower power, AbstractCreature source, int stackAmount) {
        if (power.ID.equals("Weakened") || power.ID.equals("Vulnerable") || power.ID.equals("Frail")) {
            addToTop(new RelicAboveCreatureAction((AbstractCreature)AbstractDungeon.player, (AbstractRelic)this));
            flash();
            return stackAmount + 1;
        }
        return stackAmount;
    }

    @Override
    public void onEquip() {
        AbstractDungeon.player.decreaseMaxHealth((int)(AbstractDungeon.player.maxHealth*0.3));
    }

    @Override
    public int changeRareCardRewardChance(int rareCardChance) {
        return rareCardChance * 2;
    }

    @Override
    public int changeUncommonCardRewardChance(int uncommonCardChance) {
        return uncommonCardChance * 3;}

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return (AbstractRelic)new GreedRing();
    }
}

