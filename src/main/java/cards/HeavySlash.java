package cards;

import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import pathes.AbstractCardEnum;

public class HeavySlash extends CustomCard {
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings("dreaming_journey_to_the_west:HeavySlash");
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String IMG_PATH = "img/cards/heavy_slash.png";
    private static final int COST = 2;
    private static final int ATTACK_DMG = 11;
    private static final int MAGIC_AMT = 5;
    private static final int UPGRADE_NUM = 1;
    public static final String ID = "HeavySlash";
    private int TURN = 0;

    public HeavySlash(int upgrades){
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, AbstractCardEnum.MonkeyKing_RED, CardRarity.UNCOMMON, CardTarget.ENEMY);
        this.baseDamage = ATTACK_DMG;
        this.damage = this.baseDamage;
        this.baseMagicNumber = MAGIC_AMT;
        this.magicNumber = this.baseMagicNumber;
        this.selfRetain = true;
        this.timesUpgraded = upgrades;
    }

    public void onRetained() {
        this.upgradeDamage(this.magicNumber);
        TURN = TURN+1;
        this.name = cardStrings.NAME + "+" + (this.timesUpgraded+TURN);
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeDamage(UPGRADE_NUM + this.timesUpgraded);
            this.upgradeMagicNumber(UPGRADE_NUM);
            ++this.timesUpgraded;
            this.upgraded = true;
            this.name = cardStrings.NAME + "+" + (this.timesUpgraded+TURN);
            this.initializeTitle();
        }
    }


    @Override
    public void use(AbstractPlayer p, AbstractMonster m) {
        AbstractDungeon.actionManager.addToBottom((AbstractGameAction)new DamageAction((AbstractCreature)m, new DamageInfo((AbstractCreature)p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
        this.upgradeDamage(-this.magicNumber*TURN);
        TURN = 0;
    }

    @Override
    public AbstractCard makeCopy() {
        return (AbstractCard)new HeavySlash(this.timesUpgraded);
    }
}
