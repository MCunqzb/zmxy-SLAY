package cards;

import Helpers.ModHelper;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DrawCardAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.common.RemoveAllBlockAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.VulnerablePower;
import pathes.AbstractCardEnum;

public class HelpfulHairsDrill extends CustomCard {
    public static final String ID = "HelpfulHairsDrill";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ModHelper.MakePath(ID));
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    private static final int COST = 1;
    private static final int MAGIC_AMT = 2;
    public static final String IMG_PATH = "img/cards/helpful_hairs_drill.png";


    public HelpfulHairsDrill() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.SKILL, AbstractCardEnum.MonkeyKing_RED, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseMagicNumber = MAGIC_AMT;
        this.magicNumber = this.baseMagicNumber;
        this.selfRetain = true;

    }

    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new LoseHPAction(p, p, this.magicNumber));
        this.addToBot(new RemoveAllBlockAction(m, p));
        this.addToBot(new ApplyPowerAction(m, p, new VulnerablePower(m, 2, false), 2));
        this.upgradeMagicNumber(this.baseMagicNumber);
    }

    @Override
    public AbstractCard makeCopy() {
        return (AbstractCard)new HelpfulHairsDrill();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            this.upgradeMagicNumber(-1);
            this.rawDescription = cardStrings.UPGRADE_DESCRIPTION;
            this.initializeDescription();
        }
    }
}
