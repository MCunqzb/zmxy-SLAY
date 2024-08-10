package cards;

import Helpers.ModHelper;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInHandAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pathes.AbstractCardEnum;

public class ExtraPhantom extends CustomCard {
    public static final String ID = "ExtraPhantom";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ModHelper.MakePath(ID));
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 1;
    private static final int ATTACK_AMT = 13;
    private static final int UPGRADE_PLUS_ATTACK = 3;
    private static final int BLOCK_AMT = 13;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int MAGIC_AMT = 1;
    private static final int UPGRADE_PLUS_MAGIC_AMT = 1;
    public static final String IMG_PATH = "img/cards/extra_legal_semblance.png";

    public ExtraPhantom() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, AbstractCardEnum.MonkeyKing_RED, CardRarity.UNCOMMON, CardTarget.SELF);
        this.exhaust = true;
        this.baseMagicNumber=MAGIC_AMT;
        this.magicNumber=this.baseMagicNumber;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractCard c = AbstractDungeon.returnTrulyRandomColorlessCardInCombat(AbstractDungeon.cardRandomRng).makeCopy();
        AbstractCard c1 = AbstractDungeon.returnTrulyRandomCardInCombat(CardType.ATTACK).makeCopy();
        c.setCostForTurn(0);
        c1.setCostForTurn(0);
        if (this.upgraded){
            this.addToBot(new MakeTempCardInHandAction(c, 1));
            this.addToBot(new MakeTempCardInHandAction(c1, 1));
        }else{
            this.addToBot(new MakeTempCardInHandAction(c1, 1));
        }
    }

    @Override
    public AbstractCard makeCopy() {
        return new ExtraPhantom();
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
