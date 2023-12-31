package relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.LoseDexterityPower;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import demoMod.MonkeyKingMod;
import powers.TenacityPower;

public class EmergingBoxingGloves extends CustomRelic {
    public static final String ID = "dreaming_journey_to_the_west:EmergingBoxingGloves";
    private static final String IMG = "img/relics/EmergingBoxingGloves.png";
    private static final String IMG_OTL = "img/relics/outline/EmergingBoxingGloves_outline.png";

    public EmergingBoxingGloves() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.BOSS, AbstractRelic.LandingSound.CLINK);
    }

    @Override
    public void onUseCard(AbstractCard card, UseCardAction action) {

        if (card.hasTag(MonkeyKingMod.BOXING) && card.type == AbstractCard.CardType.ATTACK ) {
            flash();
            this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new TenacityPower(AbstractDungeon.player, 2), 2));
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));

        }
        else if (card.hasTag(MonkeyKingMod.BOXING) && card.type == AbstractCard.CardType.SKILL ) {
            flash();
            this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new VigorPower(AbstractDungeon.player, 2), 2));
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
        }
    }

    public boolean canSpawn() {
        return AbstractDungeon.player.hasRelic("dreaming_journey_to_the_west:Bracelets");
    }

    public void obtain() {
        if (AbstractDungeon.player.hasRelic("dreaming_journey_to_the_west:Bracelets")) {
            for (int i = 0; i < AbstractDungeon.player.relics.size(); i++) {
                if (((AbstractRelic)AbstractDungeon.player.relics.get(i)).relicId.equals("dreaming_journey_to_the_west:Bracelets")) {
                    instantObtain(AbstractDungeon.player, i, true);
                    break;
                }
            }
        } else {
            super.obtain();
        }
    }


    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return (AbstractRelic)new EmergingBoxingGloves();
    }
}
