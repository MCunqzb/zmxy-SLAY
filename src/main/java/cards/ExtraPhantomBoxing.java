package cards;

import Helpers.ModHelper;
import basemod.abstracts.CustomCard;
import com.evacipated.cardcrawl.mod.stslib.actions.tempHp.AddTemporaryHPAction;
import com.evacipated.cardcrawl.mod.stslib.patches.core.AbstractCreature.TempHPField;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.tempCards.Shiv;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import demoMod.MonkeyKingMod;
import pathes.AbstractCardEnum;

public class ExtraPhantomBoxing extends CustomCard {
    public static final String ID = "ExtraPhantomBoxing";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ModHelper.MakePath(ID));
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST =1;
    private static final int ATTACK_AMT = 12;
    private static final int UPGRADE_PLUS_ATTACK = 4;
    private static final int BLOCK_AMT = 4;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int MAGIC_AMT = 2;
    private static final int UPGRADE_PLUS_MAGIC_AMT = 1;
    public static final String IMG_PATH = "img/cards/extra_phantom_boxing.png";

    public ExtraPhantomBoxing() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, AbstractCardEnum.MonkeyKing_RED, CardRarity.COMMON, CardTarget.SELF);
        this.baseMagicNumber=MAGIC_AMT;
        this.magicNumber=this.baseMagicNumber;
        this.tags.add(MonkeyKingMod.BOXING);
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new MakeTempCardInHandAction(new PhantomBoxing(), this.magicNumber));
    }
    @Override
    public AbstractCard makeCopy() {
        return new ExtraPhantomBoxing();
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_MAGIC_AMT);

        }
    }
}
