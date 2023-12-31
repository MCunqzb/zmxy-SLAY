package cards;

import Helpers.ModHelper;
import actions.ScorchLoseHpAction;
import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Frost;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.powers.WeakPower;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.vfx.combat.RedFireballEffect;
import com.megacrit.cardcrawl.vfx.combat.ScreenOnFireEffect;
import pathes.AbstractCardEnum;
import powers.ScorchPower;

import java.util.Iterator;

public class FlameCyclone extends CustomCard {
    public static final String ID = "FlameCyclone";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ModHelper.MakePath(ID));
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST =3;
    private static final int ATTACK_AMT = 0;
    private static final int UPGRADE_PLUS_ATTACK = 0;
    private static final int BLOCK_AMT = 0;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int MAGIC_AMT = 20;
    private static final int UPGRADE_PLUS_MAGIC_AMT = 1;
    public static final String IMG_PATH = "img/cards/flame_cyclone.png";

    public FlameCyclone() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, AbstractCardEnum.MonkeyKing_RED, CardRarity.RARE, CardTarget.SELF);
        this.baseMagicNumber=MAGIC_AMT;
        this.magicNumber=this.baseMagicNumber;
        this.exhaust = true;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
    //
    //    if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
    //        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
    //            if (!mo.isDead && !mo.isDeadOrEscaped() && mo.hasPower(ScorchPower.POWER_ID)) {
    //                AbstractPower power = mo.getPower("dreaming_journey_to_the_west:ScorchPower");
    //                AbstractPower abstractPower = power;
    //                //BaseMod.logger.info("amt"+abstractPower.amount);
    //                int DAMAGE = (abstractPower.amount+1)*abstractPower.amount/2;
    //                this.baseDamage = DAMAGE;
    //                this.addToBot(new ScorchLoseHpAction(mo, p, DAMAGE ,AbstractGameAction.AttackEffect.FIRE));
    //            }
    //        }
    //    }
    //    if ((AbstractDungeon.getCurrRoom()).monsters != null)
    //        for (AbstractMonster m2 : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
    //            if (!m2.isDeadOrEscaped() && m2.hasPower(ScorchPower.POWER_ID))
    //                this.addToBot(new RemoveSpecificPowerAction(m2, m2, m2.getPower(ScorchPower.POWER_ID)));
    //        }
    //    this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
    //    this.initializeDescription();
        this.addToBot(new VFXAction(p, new ScreenOnFireEffect(), 1.0F));
        if (!AbstractDungeon.getMonsters().areMonstersBasicallyDead()) {
            this.flash();
            Iterator var3 = AbstractDungeon.getMonsters().monsters.iterator();

            while(var3.hasNext()) {
                AbstractMonster monster = (AbstractMonster)var3.next();
                if (!monster.isDead && !monster.isDying) {
                    this.addToBot(new VFXAction(new RedFireballEffect(p.hb.cX, p.hb.cY, monster.hb.cX, monster.hb.cY,1), 0.1F));
                    this.addToBot(new ApplyPowerAction(monster, p, new ScorchPower(monster, p, this.magicNumber), this.magicNumber));
                }
            }
        }

    }

    @Override
    public AbstractCard makeCopy() {
        return new FlameCyclone();
    }
    //@Override
    //public void onMoveToDiscard() {
    //    this.rawDescription = cardStrings.DESCRIPTION;
    //    this.initializeDescription();
    //}
    //@Override
    //public void applyPowers() {
    //    int DAMAGE = 0;
    //    this.baseMagicNumber = 0;
    //    this.magicNumber = 0;
    //    if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
    //        for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
    //            if (!mo.isDead && !mo.isDeadOrEscaped() && mo.hasPower(ScorchPower.POWER_ID)) {
    //                AbstractPower power = mo.getPower("dreaming_journey_to_the_west:ScorchPower");
    //                AbstractPower abstractPower = power;
    //                //BaseMod.logger.info("amt"+abstractPower.amount);
    //                DAMAGE = (abstractPower.amount+1)*abstractPower.amount/2;
    //            }
    //        }
    //    }
//
    //    if (DAMAGE > 0) {
    //        this.baseMagicNumber = DAMAGE;
    //        super.applyPowers();
    //        this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
    //        this.initializeDescription();
    //    }
//
    //}
    //@Override
    //public void calculateCardDamage(AbstractMonster mo) {
    //    super.calculateCardDamage(mo);
    //    this.rawDescription = cardStrings.DESCRIPTION;
    //    this.rawDescription = this.rawDescription + cardStrings.EXTENDED_DESCRIPTION[0];
    //    this.initializeDescription();
    //}
    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.selfRetain = true;
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
