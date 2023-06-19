package relics;

import basemod.abstracts.CustomRelic;
import basemod.devcommands.power.Power;
import com.evacipated.cardcrawl.mod.stslib.relics.OnReceivePowerRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.actions.unique.IncreaseMaxHpAction;
import com.megacrit.cardcrawl.actions.utility.UseCardAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.potions.AbstractPotion;
import com.megacrit.cardcrawl.powers.*;
import com.megacrit.cardcrawl.powers.watcher.VigorPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import demoMod.MonkeyKingMod;

import java.util.Iterator;

public class ObsessionRing extends CustomRelic implements OnReceivePowerRelic {
    public static final String ID = "dreaming_journey_to_the_west:ObsessionRing";
    private static final String IMG = "img/relics/ObsessionRing.png";
    private static final String IMG_OTL = "img/relics/outline/ObsessionRing_outline.png";

    public ObsessionRing() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.UNCOMMON, AbstractRelic.LandingSound.CLINK);
    }

    @Override
    public void atBattleStart() {
        this.flash();
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new MetallicizePower(AbstractDungeon.player, 6), 6));
    }


    public void onEquip() {
        --AbstractDungeon.player.energy.energyMaster;

    }


    public void onUnequip() {
        ++AbstractDungeon.player.energy.energyMaster;
    }

    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return (AbstractRelic)new ObsessionRing();
    }

    @Override
    public boolean onReceivePower(AbstractPower abstractPower, AbstractCreature abstractCreature) {
        return true;
    }

    @Override
    public int onReceivePowerStacks(AbstractPower power, AbstractCreature source, int stackAmount) {
        if (power.type== AbstractPower.PowerType.DEBUFF) {
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            giveRandomBuff(AbstractDungeon.player, 1);
            flash();
            return stackAmount;
        }
        return stackAmount;
    }

    private void giveRandomBuff(AbstractPlayer p, int amt) {
        p =AbstractDungeon.player;
        switch (AbstractDungeon.miscRng.random(12)) {
            case 0:
                this.addToTop(new ApplyPowerAction(p,p,new ArtifactPower(p,amt),amt));
                break;
            case 1:
                this.addToTop(new ApplyPowerAction(p,p,new PlatedArmorPower(p,amt),amt));
                break;
            case 2:
                this.addToTop(new ApplyPowerAction(p,p,new MetallicizePower(p,amt),amt));
                break;
            case 3:
                this.addToTop(new ApplyPowerAction(p,p,new StrengthPower(p,amt),amt));
                break;
            case 4:
                this.addToTop(new ApplyPowerAction(p,p,new ThornsPower(p,amt),amt));
                break;
            case 5:
                this.addToTop(new ApplyPowerAction(p,p,new IntangiblePower(p,amt),amt));
                break;
            case 6:
                this.addToTop(new ApplyPowerAction(p,p,new RitualPower(p,amt,true),amt));
                break;
            case 7:
                this.addToTop(new ApplyPowerAction(p,p,new DexterityPower(p,amt),amt));
                break;
            case 8:
                this.addToTop(new ApplyPowerAction(p,p,new NextTurnBlockPower(p,amt),amt));
                break;
            case 9:
                this.addToTop(new ApplyPowerAction(p,p,new RegenPower(p,amt),amt));
                break;
            case 10:
                this.addToTop(new ApplyPowerAction(p,p,new BufferPower(p,amt),amt));
                break;
            case 11:
                this.addToTop(new ApplyPowerAction(p,p,new BlurPower(p,amt),amt));
                break;
            case 12:
                this.addToTop(new ApplyPowerAction(p,p,new EnergizedPower(p,amt),amt));
                break;



        }
    }
}

