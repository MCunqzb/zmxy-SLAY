package relics;

import basemod.BaseMod;
import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import demoMod.MonkeyKingMod;
import powers.ReboundDamagePower;
import powers.TenacityPower;

public class BloodSirenShell extends CustomRelic {
    public static final String ID = "dreaming_journey_to_the_west:BloodSirenShell";
    private static final String IMG = "img/relics/blood_siren_shell.png";
    private static final String IMG_OTL = "img/relics/outline/blood_siren_shell_outline.png";


    public BloodSirenShell() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.SPECIAL, AbstractRelic.LandingSound.SOLID);
    }

    public void onPlayCard(AbstractCard c, AbstractMonster m) {
        AbstractCreature p = AbstractDungeon.player;
        if (p.currentBlock>0  && c.baseBlock>0){
            this.flash();
            //BaseMod.logger.info("Blcok_Play>0");
            this.addToBot(new ApplyPowerAction(p,p,new ReboundDamagePower(p,1,true)));
        }
    }




    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return (AbstractRelic)new BloodSirenShell();
    }
}
