package cards;

import Helpers.ModHelper;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.watcher.MasterRealityPower;
import pathes.AbstractCardEnum;
import powers.ControlPhantomPower;


public class ControlPhantom extends CustomCard {
    public static final String ID = "ControlPhantom";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ModHelper.MakePath(ID));
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST =2;
    private static final int ATTACK_AMT = 12;
    private static final int UPGRADE_PLUS_ATTACK = 4;
    private static final int BLOCK_AMT = 0;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int MAGIC_AMT = 1;
    private static final int UPGRADE_PLUS_MAGIC_AMT = 1;
    public static final String IMG_PATH = "img/cards/control_phantom.png";

    public ControlPhantom() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.POWER, AbstractCardEnum.MonkeyKing_RED, CardRarity.RARE, CardTarget.SELF);
        this.baseMagicNumber=MAGIC_AMT;
        this.magicNumber=this.baseMagicNumber;
        this.isInnate = false;

    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p, p, new ControlPhantomPower(p)));
    }
    @Override
    public AbstractCard makeCopy() {
        return new ControlPhantom();
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeBaseCost(1);
        }
    }
}
