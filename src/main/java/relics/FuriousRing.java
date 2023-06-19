package relics;

import basemod.abstracts.CustomRelic;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.RelicAboveCreatureAction;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.ImageMaster;
import com.megacrit.cardcrawl.powers.FrailPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import com.megacrit.cardcrawl.relics.AbstractRelic;

import com.megacrit.cardcrawl.rooms.AbstractRoom;
import powers.IndignationPower;

public class FuriousRing extends CustomRelic {
    public static final String ID = "dreaming_journey_to_the_west:FuriousRing";
    private static final String IMG = "img/relics/FuriousRing.png";
    private static final String IMG_OTL = "img/relics/outline/FuriousRing_outline.png";
    private boolean isActive = false;

    public FuriousRing() {
        super(ID, ImageMaster.loadImage(IMG), ImageMaster.loadImage(IMG_OTL), RelicTier.UNCOMMON, AbstractRelic.LandingSound.CLINK);
    }

    public void atBattleStart() {
        this.flash();
        this.addToTop(new ApplyPowerAction(AbstractDungeon.player, AbstractDungeon.player, new StrengthPower(AbstractDungeon.player, 2), 2));
        this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));

        this.isActive = false;
        this.addToBot(new AbstractGameAction() {
            public void update() {
                if (!FuriousRing.this.isActive && AbstractDungeon.player.isBloodied) {
                    FuriousRing.this.flash();
                    FuriousRing.this.pulse = true;
                    AbstractDungeon.player.addPower(new IndignationPower(AbstractDungeon.player, 1));
                    this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, FuriousRing.this));
                    FuriousRing.this.isActive = true;
                    AbstractDungeon.onModifyPower();
                }
                this.isDone = true;
            }
        });
    }
    
    

    public void onBloodied() {
        this.flash();
        this.pulse = true;
        if (!this.isActive && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractPlayer p = AbstractDungeon.player;
            this.addToTop(new ApplyPowerAction(p, p, new IndignationPower(p, 1), 1));
            this.addToTop(new RelicAboveCreatureAction(AbstractDungeon.player, this));
            this.isActive = true;
            AbstractDungeon.player.hand.applyPowers();
        }

    }

    public void onNotBloodied() {
        if (this.isActive && AbstractDungeon.getCurrRoom().phase == AbstractRoom.RoomPhase.COMBAT) {
            AbstractPlayer p = AbstractDungeon.player;
            this.addToTop(new ApplyPowerAction(p, p, new IndignationPower(p, -1), -1));
        }

        this.stopPulse();
        this.isActive = false;
        AbstractDungeon.player.hand.applyPowers();
    }

    public void onVictory() {
        this.pulse = false;
        this.isActive = false;
    }
    @Override
    public String getUpdatedDescription() {
        return this.DESCRIPTIONS[0];
    }

    @Override
    public AbstractRelic makeCopy() {
        return (AbstractRelic)new FuriousRing();
    }
}
