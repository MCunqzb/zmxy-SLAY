package relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import demoMod.MonkeyKingMod;
import powers.TenacityPower;

public class RuyiJingguBang extends CustomRelic {
    public static final String ID = "dreaming_journey_to_the_west:RuyiJingguBang";
    private static final String IMG = "img/relics/ruyi_jingu_bang.png";
    private static final String IMG_OTL = "img/relics/outline/ruyi_jingu_bang_outline.png";

    public RuyiJingguBang() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.RARE, LandingSound.HEAVY);
    }

    public float atDamageModify(float damage, AbstractCard c) {
        return c.hasTag(MonkeyKingMod.BOXING) ? damage - 3.0F : damage + 3.0F;
    }


    @Override
    public String getUpdatedDescription()  {
        return this.DESCRIPTIONS[0] + 3 + this.DESCRIPTIONS[1] + this.DESCRIPTIONS[2] + 3 + this.DESCRIPTIONS[3];
    }

    @Override
    public AbstractRelic makeCopy() {
        return (AbstractRelic)new RuyiJingguBang();
    }
}
