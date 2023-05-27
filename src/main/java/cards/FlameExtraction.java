package cards;

import Helpers.ModHelper;
import actions.ScorchLoseHpAction;
import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.RemoveSpecificPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import pathes.AbstractCardEnum;
import powers.FireBarrierPower;
import powers.ScorchPower;

public class FlameExtraction extends CustomCard {
    public static final String ID = "FlameExtraction";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ModHelper.MakePath(ID));
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;

    private static final int COST = 2;
    private static final int ATTACK_AMT = 0;
    private static final int UPGRADE_PLUS_ATTACK = 0;
    private static final int BLOCK_AMT = 0;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int MAGIC_AMT = 2;
    private static final int UPGRADE_PLUS_MAGIC_AMT = 1;
    public static final String IMG_PATH = "img/cards/flame_extraction.png";
//hear shield
    //hexaghost
    public FlameExtraction() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, AbstractCardEnum.MonkeyKing_RED, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseBlock = BLOCK_AMT;
        this.baseMagicNumber = MAGIC_AMT;
        this.magicNumber= this.baseMagicNumber;
    }
    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        if ((AbstractDungeon.getCurrRoom()).phase == AbstractRoom.RoomPhase.COMBAT) {
            for (AbstractMonster mo : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                if (!mo.isDead && !mo.isDeadOrEscaped() && mo.hasPower(ScorchPower.POWER_ID)) {
                    this.baseBlock += (mo.getPower(ScorchPower.POWER_ID)).amount;
                    this.block=this.baseBlock;
                    //BaseMod.logger.info("");
                    if (this.upgraded)
                    this.addToBot(new ScorchLoseHpAction(mo,p, (mo.getPower(ScorchPower.POWER_ID)).amount*2-1, AbstractGameAction.AttackEffect.FIRE));
                }
                this.isBlockModified = (this.block != this.baseBlock);
            }
        }
        else {
            this.baseBlock = this.block = 0;
            this.isBlockModified = false;
        }

        AbstractDungeon.actionManager.addToBottom(new GainBlockAction(p, p, this.block * 2 ));
        if ((AbstractDungeon.getCurrRoom()).monsters != null)
            for (AbstractMonster m2 : (AbstractDungeon.getCurrRoom()).monsters.monsters) {
                if (!m2.isDeadOrEscaped() && m2.hasPower(ScorchPower.POWER_ID))
                    this.addToBot(new RemoveSpecificPowerAction(m2, m2, m2.getPower(ScorchPower.POWER_ID)));
            }

    }

    @Override
    public AbstractCard makeCopy() {
        return new FlameExtraction();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
