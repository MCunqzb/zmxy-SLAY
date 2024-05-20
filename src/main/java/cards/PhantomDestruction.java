package cards;

import Helpers.ModHelper;
import actions.FlamingStormBloodAction;
import actions.PhantomDestructionAction;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.orbs.AbstractOrb;
import com.megacrit.cardcrawl.orbs.Lightning;
import demoMod.MonkeyKingMod;
import pathes.AbstractCardEnum;

import java.util.Iterator;

public class PhantomDestruction extends CustomCard {
    public static final String ID = "PhantomDestruction";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ModHelper.MakePath(ID));
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST =3;
    private static final int ATTACK_AMT = 9;
    private static final int UPGRADE_PLUS_ATTACK = 4;
    private static final int BLOCK_AMT = 0;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int MAGIC_AMT = 2;
    private static final int UPGRADE_PLUS_MAGIC_AMT = 1;
    public static final String IMG_PATH = "img/cards/phantom_destruction.png";

    public PhantomDestruction() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, AbstractCardEnum.MonkeyKing_RED, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage=ATTACK_AMT;
        this.damage=this.baseDamage;
        this.baseMagicNumber = 0;
        this.magicNumber = 0;
        this.exhaust=true;
    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new PhantomDestructionAction(m,new DamageInfo(p, this.damage, this.damageTypeForTurn)));
    }
    @Override
    public AbstractCard makeCopy() {
        return new PhantomDestruction();
    }

    public void applyPowers() {
        super.applyPowers();
        this.baseMagicNumber = 0;
        this.magicNumber = 0;

        Iterator var1 = AbstractDungeon.player.hand.group.iterator();
        AbstractCard c;
        while(var1.hasNext()) {
            c = (AbstractCard)var1.next();
            if (c.rarity == AbstractCard.CardRarity.SPECIAL ||
                    c.type == AbstractCard.CardType.STATUS ||
                    c.type == AbstractCard.CardType.CURSE ||
                    c.hasTag(MonkeyKingMod.BOXING)) {
                ++this.baseMagicNumber;
            }
        }

        if (this.baseMagicNumber > 0) {
            this.rawDescription = cardStrings.DESCRIPTION + cardStrings.EXTENDED_DESCRIPTION[0];
            this.initializeDescription();
        }

    }

    public void onMoveToDiscard() {
        this.rawDescription = cardStrings.DESCRIPTION;
        this.initializeDescription();
    }

    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeDamage(UPGRADE_PLUS_ATTACK);
        }
    }
}
