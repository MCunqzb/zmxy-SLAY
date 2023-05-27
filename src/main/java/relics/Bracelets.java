package relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.HealAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.LoseStrengthPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import demoMod.MonkeyKingMod;

public class Bracelets extends CustomRelic {
    public static final String ID = "dreaming_journey_to_the_west:Bracelets";
    private static final String IMG = "img/relics/Bracelets.png";
    private static final String IMG_OTL = "img/relics/outline/Bracelets_outline.png";

    public Bracelets() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.STARTER, AbstractRelic.LandingSound.CLINK);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {

        if (card.hasTag(MonkeyKingMod.BOXING) && card.type == AbstractCard.CardType.ATTACK ) {
            flash();
            this.addToTop(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new DexterityPower(AbstractDungeon.player,1),1));
            this.addToTop(new ApplyPowerAction(AbstractDungeon.player,AbstractDungeon.player,new LoseDexterityPower(AbstractDungeon.player,1),1));
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));

        }
        else if (card.hasTag(MonkeyKingMod.BOXING) && card.type == AbstractCard.CardType.SKILL ) {
            flash();
            this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VigorPower(AbstractDungeon.player, 1), 1));
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            }
    }


    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return (AbstractRelic)new Bracelets();
    }
}
