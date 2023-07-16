package cards;

import Helpers.ModHelper;
import basemod.abstracts.CustomCard;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.powers.AbstractPower;
import com.megacrit.cardcrawl.vfx.combat.VerticalImpactEffect;
import demoMod.MonkeyKingMod;
import pathes.AbstractCardEnum;

public class HeavyBoxing extends CustomCard {
    public static final String ID = "HeavyBoxing";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ModHelper.MakePath(ID));
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 2;
    private static final int ATTACK_AMT = 14;
    private static final int UPGRADE_PLUS_ATTACK = 2;
    private static final int BLOCK_AMT = 0;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int MAGIC_AMT = 3;
    private static final int UPGRADE_PLUS_MAGIC_AMT = 2;
    public static final String IMG_PATH = "img/cards/heavy_boxing.png";

    public HeavyBoxing() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, AbstractCardEnum.MonkeyKing_RED, CardRarity.COMMON, CardTarget.ENEMY);
        this.baseDamage=ATTACK_AMT;
        this.damage=this.baseDamage;
        this.baseMagicNumber = MAGIC_AMT;
        this.magicNumber = this.baseMagicNumber;
        this.tags.add(MonkeyKingMod.BOXING);
    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            this.addToBot(new VFXAction(new VerticalImpactEffect(m.hb.cX + m.hb.width / 4.0F, m.hb.cY - m.hb.height / 4.0F)));
        }

        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage, this.damageTypeForTurn), AbstractGameAction.AttackEffect.BLUNT_HEAVY));
    }

    public void applyPowers() {
        AbstractPower strength = AbstractDungeon.player.getPower("Strength");
        AbstractPower vigor = AbstractDungeon.player.getPower("Vigor");
        if (strength != null) {
            strength.amount *= this.magicNumber;
        }
        if (vigor != null) {
            vigor.amount *= this.magicNumber;
        }

        super.applyPowers();
        if (strength != null) {
            strength.amount /= this.magicNumber;
        }
        if (vigor != null) {
            vigor.amount /= this.magicNumber;
        }

    }

    public void calculateCardDamage(AbstractMonster mo) {
        AbstractPower strength = AbstractDungeon.player.getPower("Strength");
        AbstractPower vigor = AbstractDungeon.player.getPower("Vigor");
        if (strength != null) {
            strength.amount *= this.magicNumber;
        }
        if (vigor != null) {
            vigor.amount *= this.magicNumber;
        }

        super.calculateCardDamage(mo);
        if (strength != null) {
            strength.amount /= this.magicNumber;
        }
        if (vigor != null) {
            vigor.amount /= this.magicNumber;
        }

    }

    @Override
    public AbstractCard makeCopy() {
        return new HeavyBoxing();
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            this.upgradeName();
            this.upgradeMagicNumber(UPGRADE_PLUS_MAGIC_AMT);

        }
    }
}
