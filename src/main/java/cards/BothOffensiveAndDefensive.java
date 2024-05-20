package cards;

import Helpers.ModHelper;
import actions.IncreaseCostAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.*;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.DexterityPower;
import com.megacrit.cardcrawl.powers.StrengthPower;
import pathes.AbstractCardEnum;

public class BothOffensiveAndDefensive extends CustomCard {
    public static final String ID = "BothOffensiveAndDefensive";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ModHelper.MakePath(ID));
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST =1;
    private static final int ATTACK_AMT = 4;
    private static final int UPGRADE_PLUS_ATTACK = 0;
    private static final int BLOCK_AMT = 4;
    private static final int UPGRADE_PLUS_BLOCK = 0;
    private static final int MAGIC_AMT = 1;
    private static final int UPGRADE_PLUS_MAGIC_AMT = 1;
    public static final String IMG_PATH = "img/cards/both_offensive_and_defensive.png";

    public BothOffensiveAndDefensive() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.POWER, AbstractCardEnum.MonkeyKing_RED, CardRarity.UNCOMMON, CardTarget.SELF);
        this.baseMagicNumber=MAGIC_AMT;
        this.magicNumber=this.baseMagicNumber;

        this.baseBlock=BLOCK_AMT;
        this.block=this.baseBlock;

        this.baseDamage=ATTACK_AMT;
        this.damage=this.baseDamage;

        this.exhaust=true;
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new ApplyPowerAction(p,p,new StrengthPower(p,this.magicNumber),this.magicNumber));
        this.addToBot(new ApplyPowerAction(p,p,new DexterityPower(p,this.magicNumber),this.magicNumber));
    }

    @Override
    public AbstractCard makeCopy() {
        return new BothOffensiveAndDefensive();
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_MAGIC_AMT);
        }
    }
}
