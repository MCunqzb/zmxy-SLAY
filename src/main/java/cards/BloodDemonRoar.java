package cards;

import Helpers.ModHelper;
import actions.SpecificTagNotExhaustCardAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.GainBlockAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.HemokinesisEffect;
import demoMod.MonkeyKingMod;
import pathes.AbstractCardEnum;

public class BloodDemonRoar extends CustomCard {
    public static final String ID = "BloodDemonRoar";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ModHelper.MakePath(ID));
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 2;
    private static final int ATTACK_AMT = 17;
    private static final int UPGRADE_PLUS_ATTACK = 5;
    private static final int BLOCK_AMT = 17;
    private static final int UPGRADE_PLUS_BLOCK = 5;
    private static final int MAGIC_AMT = 2;
    private static final int UPGRADE_PLUS_MAGIC_AMT = 1;
    public static final String IMG_PATH = "img/cards/blooddemon_roar.png";

    public BloodDemonRoar() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, AbstractCardEnum.MonkeyKing_RED, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseBlock=BLOCK_AMT;
        this.block=this.baseBlock;
        this.baseMagicNumber=MAGIC_AMT;
        this.magicNumber=this.baseMagicNumber;
        this.tags.add(MonkeyKingMod.BOXING);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            this.addToBot(new VFXAction(new HemokinesisEffect(p.hb.cX, p.hb.cY, p.hb.cX, p.hb.cY+10F), 0.1F));
        }
        this.addToBot(new LoseHPAction(p,p,this.magicNumber));
        this.addToBot(new SpecificTagNotExhaustCardAction(MonkeyKingMod.BOXING, AbstractDungeon.player.hand));
        this.addToBot(new GainBlockAction(p,this.block));

    }

    @Override
    public AbstractCard makeCopy() {
        return new BloodDemonRoar();
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBlock(UPGRADE_PLUS_BLOCK);
        }
    }
}
