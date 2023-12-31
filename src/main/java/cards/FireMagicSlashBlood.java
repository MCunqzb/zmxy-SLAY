package cards;

import Helpers.ModHelper;
import basemod.BaseMod;
import basemod.abstracts.CustomCard;
import com.badlogic.gdx.graphics.Color;
import com.megacrit.cardcrawl.actions.AbstractGameAction;
import com.megacrit.cardcrawl.actions.animations.VFXAction;
import com.megacrit.cardcrawl.actions.common.DamageAction;
import com.megacrit.cardcrawl.actions.common.DamageAllEnemiesAction;
import com.megacrit.cardcrawl.actions.common.LoseHPAction;
import com.megacrit.cardcrawl.actions.utility.SFXAction;
import com.megacrit.cardcrawl.cards.AbstractCard;
import com.megacrit.cardcrawl.cards.DamageInfo;
import com.megacrit.cardcrawl.cards.red.BloodForBlood;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.AbstractCreature;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.localization.CardStrings;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import com.megacrit.cardcrawl.vfx.combat.CleaveEffect;
import com.megacrit.cardcrawl.vfx.combat.HemokinesisEffect;
import com.megacrit.cardcrawl.vfx.combat.VerticalImpactEffect;
import com.megacrit.cardcrawl.vfx.combat.WhirlwindEffect;
import pathes.AbstractCardEnum;

import java.util.Iterator;

public class FireMagicSlashBlood extends CustomCard {
    public static final String ID = "FireMagicSlashBlood";
    private static final CardStrings cardStrings = CardCrawlGame.languagePack.getCardStrings(ModHelper.MakePath(ID));
    public static final String NAME = cardStrings.NAME;
    public static final String DESCRIPTION = cardStrings.DESCRIPTION;
    public static final String UPGRADE_DESCRIPTION = cardStrings.UPGRADE_DESCRIPTION;
    private static final int COST = 5;
    private static final int ATTACK_AMT = 3;
    private static final int UPGRADE_PLUS_ATTACK = 3;
    private static final int BLOCK_AMT = 0;
    private static final int UPGRADE_PLUS_BLOCK = 3;
    private static final int MAGIC_AMT = 2;
    private static final int UPGRADE_PLUS_MAGIC_AMT =1;
    private static final int ATTACK_TIME = 9;
    public static final String IMG_PATH = "img/cards/fire_magic_slash_blood.png";

    public FireMagicSlashBlood() {
        super(ID, NAME, IMG_PATH, COST, DESCRIPTION, CardType.ATTACK, AbstractCardEnum.MonkeyKing_RED, CardRarity.RARE, CardTarget.ENEMY);
        this.baseDamage=ATTACK_AMT;
        this.damage=this.baseDamage;
        this.baseMagicNumber=MAGIC_AMT;
        this.magicNumber=this.baseMagicNumber;

    }

    public void use(AbstractPlayer p, AbstractMonster m) {
        if (m != null) {
            this.addToBot(new VFXAction(new HemokinesisEffect(p.hb.cX, p.hb.cY, p.hb.cX, p.hb.cY+10F), 0.1F));
        }
        this.addToBot(new LoseHPAction(p,p,this.magicNumber));
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage/3, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage/3, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage/3, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_VERTICAL));

        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage/3, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage/3, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage/3, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_DIAGONAL));

        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage/3, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage/3, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage/3, this.damageTypeForTurn), AbstractGameAction.AttackEffect.SLASH_HEAVY));

        if (m != null) {
            this.addToBot(new VFXAction(new VerticalImpactEffect(m.hb.cX + m.hb.width / 4.0F, m.hb.cY - m.hb.height / 4.0F)));
        }
        this.addToBot(new DamageAction(m, new DamageInfo(p, this.damage*3, this.damageTypeForTurn), AbstractGameAction.AttackEffect.NONE));
    }

    public void tookDamage() {
        this.updateCost(-1);
    }

    @Override
    public AbstractCard makeCopy() {
        AbstractCard tmp = new FireMagicSlashBlood();
        if (AbstractDungeon.player != null) {
            tmp.updateCost(-AbstractDungeon.player.damagedThisCombat);
        }

        return tmp;
    }


    @Override
    public void upgrade() {
        if (!this.upgraded) {
            upgradeName();
            if (this.cost < 5) {
                this.upgradeBaseCost(this.cost - 1);
                if (this.cost < 0) {
                    this.cost = 0;
                }
            } else {
                this.upgradeBaseCost(4);
            }
            upgradeDamage(UPGRADE_PLUS_ATTACK);
            upgradeMagicNumber(UPGRADE_PLUS_MAGIC_AMT);
        }
    }
}
