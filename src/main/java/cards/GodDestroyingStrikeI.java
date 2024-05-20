package cards;

import Helpers.ModHelper;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.MakeTempCardInDrawPileAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import demoMod.MonkeyKingMod;
import pathes.AbstractCardEnum;

public class GodDestroyingStrikeI extends CustomCard {
    public static final String ID = "GodDestroyingStrikeI";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ModHelper.MakePath(ID));
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST =1;
    private static final int ATTACK_AMT = 3;
    private static final int UPGRADE_PLUS_ATTACK = 4;
    private static final int BLOCK_AMT = 0;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int MAGIC_AMT = 2;
    private static final int UPGRADE_PLUS_MAGIC_AMT = 1;
    public static final String IMG_PATH = "img/cards/god_destroying_strike_1.png";

    public GodDestroyingStrikeI() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, AbstractCardEnum.MonkeyKing_RED, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage=ATTACK_AMT;
        this.tags.add(MonkeyKingMod.BOXING);
        this.tags.add(CardTags.STRIKE);
        this.cardsToPreview = new GodDestroyingStrikeII();
        this.damage=this.baseDamage;

    }
    public void use(AbstractPlayer p, AbstractMonster m) {
        this.addToBot(new MakeTempCardInDrawPileAction(this.cardsToPreview.makeStatEquivalentCopy(), 1, true, true));
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }
    @Override
    public AbstractCard makeCopy() {
        return new GodDestroyingStrikeI();
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            upgradeDamage(UPGRADE_PLUS_ATTACK);
            this.upgradeBaseCost(0);
        }
    }
}
